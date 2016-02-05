package cs.chat.client;

import cs.chat.message.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by Camellia Sinensis on 05/02/2016.
 */
public class Client {

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

    public void startWorking() {

        establishConnection();

        subscribeToServer();

    }

    private void subscribeToServer() {

        do {
            clientName = input.getClientName();
            try {
                ostream.writeObject(new Message(clientName, "server", "hello"));
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Cannot send \"hello\" message");
                continue;
            }

            Message msg;
            try {

                 msg = (Message)istream.readObject();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Problem with receiving \"hello\" reply - IOException");
                continue;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                System.out.println("Problem with receiving \"hello\" reply - CNFException");
                continue;
            }

            if ( !"server".equals(msg.getTransmitter())){
                System.out.println("Unknown Transmitter\n");
                continue;
            }

            if ( "hello".equals(msg.getMessage())){
                System.out.println("hello " + msg.getReceiver());
                break;
            }

            if ("bad_name".equals(msg.getMessage())){
                System.out.println("Bad name, chose another name.\n");
                break;
            }

            System.out.println("Unhanded error in sayHelloToServer.\n");

        } while(true);


    }

    private void establishConnection() {

        String ip = input.getIp();
        int port = input.getPort();

        // Create socket
        try {
            socket = new Socket(ip, port);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(String.format("Cannot create socket, ip = %s, port = %d\n" ,  ip, port));
        }

        // Create streams
        try {
            ostream = new ObjectOutputStream(socket.getOutputStream());
            istream = new ObjectInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Cannot create input/output stream");
        }

    }

    public boolean sendMessage(){
        Message msg = new Message(clientName, input.getReceiver(), input.getMessage());
        try {
            ostream.writeObject(msg);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Cannot send message");
            return false;
        }
        return true;
    }
}
