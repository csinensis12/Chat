package cs.chat.client;

import cs.chat.message.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by Camellia Sinensis on 05/02/2016.
 */
public class Client extends Thread{

    volatile boolean listenerAlive;
    Socket socket;
    Input input;
    Output output;
    ObjectOutputStream ostream;
    ObjectInputStream istream;
    String clientName;

    public Client(Input input, Output output){
        this.input = input;
        this.output = output;
    }

    public boolean startWorking() {

        if (!establishConnection())
            return false;

        if (!subscribeToServer())
            return false;

        startListeningThread();
        return true;
    }

    private void startListeningThread() {
        listenerAlive = true;
        start();
    }

    private boolean subscribeToServer() {

            clientName = input.getClientName();
            try {
                ostream.writeObject(new Message(clientName, "server", "hello"));
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("***Cannot send \"hello\" message - IOException");
                return false;
            } catch (NullPointerException e){
                System.out.println("Cannot send \"hello\" message - ObjectOutputStream is NULL");
                System.out.println("***Don't run subscriveToServer() before completion of establishConnection()");
                return false;
            }

            Message msg;
            try {
                 msg = (Message)istream.readObject();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("***Problem with receiving \"hello\" reply - IOException");
                return false;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                System.out.println("***Problem with receiving \"hello\" reply - CNFException");
                return false;
            }

            if ( !"server".equals(msg.getTransmitter())){
                System.out.println("Unknown Transmitter\n");
                return false;
            }

            if ( "hello".equals(msg.getMessage())){
                System.out.println("hello " + msg.getReceiver());
                return true;
            }

            if ("bad_name".equals(msg.getMessage())){
                System.out.println("Bad name, chose another name.\n");
                return false;
            }

            System.out.println("Unhanded error in sayHelloToServer.\n");
            return false;


    }

    private boolean establishConnection() {

        String ip = input.getIp();
        int port = input.getPort();

        // Create socket
        try {
            socket = new Socket(ip, port);
        } catch (IOException e) {
            System.out.println(String.format("***Cannot create socket, ip = %s, port = %d\n" ,  ip, port));
            return false;
        }

        // Create streams
        try {
            ostream = new ObjectOutputStream(socket.getOutputStream());
            istream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("***Cannot create input/output stream \n***(probably second connection, there cannot be 2 connections on 1 socket)\n");
            return false;
        }

        return true;
    }

    public boolean sendMessage(){
        Message msg = new Message(clientName, input.getReceiver(), input.getMessage());
        try {
            ostream.writeObject(msg);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("***Cannot send message");
            return false;
        }
        return true;
    }

    @Override
    public void run(){
        while(listenerAlive == true){
            Message msg;
            try {
                msg = (Message)istream.readObject();
            } catch (IOException e) {
                System.out.print("***IOException in listener thread\n");
                listenerAlive = false;
                break;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                System.out.print("***Problem in message receiver - ClassNotFound\n");
                listenerAlive = false;
                break;
            } catch (NullPointerException e){
                System.out.println("***Don't run start() before completion of subscriveToServer()\n");
                listenerAlive = false;
                break;
            }

            output.displayMessage(msg);
        }
        System.out.println("***Listening thread finished, you need to restart connection");
    }
}
