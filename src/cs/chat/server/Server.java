package cs.chat.server;

import cs.chat.message.Message;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;

/**
 * Created by Camellia Sinensis on 05/02/2016.
 */
public class Server extends Thread{

    Input input;
    Output output;
    Socket socket;
    ServerSocket serverSocket;
    HashMap clientsHashMap;

    public Server(Input input, Output output) {
        this.input = input;
        this.output = output;
        clientsHashMap = new HashMap();
    }

    public void startWorking() {
        createServerSocket();
        while(true) {
            waitForIncommingConnections();
        }
    }

    public void stopWorking(){
        //TODO
        System.out.println("unsupported yet");
    }

    private void waitForIncommingConnections() {
        output.displayLog( "===waiting for connections===");
        ObjectOutputStream ostream;
        ObjectInputStream istream;

        try {
            socket = serverSocket.accept();
        } catch (IOException e) {
            output.displayLog("Socket couldn't accept connection");
            return;
        }

        try {
            istream = new ObjectInputStream(socket.getInputStream());
            ostream = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            output.displayLog("Couldnt create i/o streams");
            return;
        }


        String newUserName;
        do {
            Message msg;
            try {
                msg = (Message) istream.readObject();
            } catch (IOException e) {
                output.displayLog("couldnt read object - ioexception");
                return;
            } catch (ClassNotFoundException e) {
                output.displayLog("couldnt read object - class not found exception");
                continue;
            }

            if (!"server".equals(msg.getReceiver())) {
                output.displayLog("receiver should be server, is: " + msg.getReceiver());
                continue;
            }
            if (!"hello".equals(msg.getMessage())) {
                output.displayLog("receiver should say hello, said : " + msg.getMessage());
                continue;
            }

            if (clientsHashMap.get(msg.getTransmitter()) != null) {
                try {
                    ostream.writeObject(new Message("server",msg.getTransmitter(),"bad_name"));
                } catch (IOException e) {
                    output.displayLog("Couldn't send 'bad_name', probably ObjectOutputStream destroyed");
                    return;
                }
            }else{
                newUserName = msg.getTransmitter();
                break;
            }
        }while(true);

        if (newUserName == null){
            System.out.println("username = null, something went wrong (shouldne ever happen)\n");
            return;
        }

        try {
            ostream.writeObject(new Message("server", newUserName, "hello" ));
        } catch (IOException e) {
            output.displayLog("Sending hello to client failed, probably ObjectOutputStream destroyed");
            return;
        }

        ClientConnectionHandler cch = new ClientConnectionHandler(istream,ostream,newUserName,clientsHashMap);
        clientsHashMap.put(newUserName,cch);
        cch.start();

    }

    private void createServerSocket() {
        int port = input.getPort();
        try {
            serverSocket = new ServerSocket( port );
        } catch (IOException e) {
            output.displayLog("Cannot create socket on port " + port);
        }
    }
}
