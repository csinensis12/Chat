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

    private void waitForIncommingConnections() {
        //fixme: ugly while without break condition
        output.displayLog( "===waiting for connections===\n");
        ObjectOutputStream ostream;
        ObjectInputStream istream;

        try {
            socket = serverSocket.accept();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("socket couldnt accept connection\n");
            return;
        }

        try {
            istream = new ObjectInputStream(socket.getInputStream());
            ostream = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("couldnt create i/o streams\n");
            return;
        }


        String newUserName;
        do {
            Message msg;
            try {
                msg = (Message) istream.readObject();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("couldnt read object - ioexception\n");
                return;
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                System.out.println("couldnt read object - class not found exception\n");
                return;
            }

            if (!"server".equals(msg.getReceiver())) {
                System.out.println("receiver should be server, is: " + msg.getReceiver());
                continue;
            }
            if (!"hello".equals(msg.getMessage())) {
                System.out.println("receiver should say hello, said : " + msg.getMessage());
                continue;
            }

            if (clientsHashMap.get(msg.getTransmitter()) != null) {
                try {
                    ostream.writeObject(new Message("server",msg.getTransmitter(),"bad_name"));
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("problem with sending bad_name");
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
            e.printStackTrace();
            System.out.print("Sending hello to client failed");
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
            e.printStackTrace();
            System.out.print("Cannot create socket on port " + port);
        }
    }
}
