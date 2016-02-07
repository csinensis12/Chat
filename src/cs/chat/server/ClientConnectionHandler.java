package cs.chat.server;

import cs.chat.message.Message;
import cs.chat.message.MessageSender;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Camellia Sinensis on 06/02/2016.
 */
public class ClientConnectionHandler extends Thread{
    private final ObjectInputStream istream;
    private final ObjectOutputStream ostream;
    private final String userName;
    private final HashMap clientHashMap;

    public ClientConnectionHandler(ObjectInputStream istream, ObjectOutputStream ostream, String newUserName, HashMap clientsHashMap){
        this.istream = istream;
        this.ostream = ostream;
        this.userName = newUserName;
        this.clientHashMap = clientsHashMap;
    }

    @Override
    public void run() {
        System.out.println("Connected with " + userName);
        while (true) {
            Message msg;
            try {
                msg = (Message) istream.readObject();
            } catch (IOException e) {
                System.out.print("ObjectInputStream broken, probably client dropped connection\n");
                break;
            } catch (ClassNotFoundException e) {
                System.out.print("ClassNotFound in clientConnectionHandler\n");
                break;
            }

            if ("server".equals(msg.getReceiver())) {
                try {
                    ostream.writeObject(new Message("server", userName, serverResponse(msg.getMessage())));
                } catch (IOException e) {
                    System.out.print("Couldnt send server response, probably ObjectOutputStream is broken\n");
                    break;
                }
            } else {
                transferMessage(msg);
            }
        }
        clientHashMap.remove(userName);
        System.out.println("listening thread for "+userName+" stopped\n");
    }
    private void transferMessage(Message msg) {
        MessageSender ms = new MessageSender(clientHashMap,msg);
        ms.start();
    }

    public synchronized void send(Message msg){
        try {
            ostream.writeObject(msg);
        } catch (IOException ex) {
            System.out.println("IOException in send method in clientConnectionHandler\n");
        }
    }

    private String serverResponse(String msg) {
        switch(msg){
            case "~~GetConnectedClients":
                List myList = new ArrayList();
                myList.addAll((clientHashMap).keySet());
                return myList.toString();
            case "~~Help":
                return ("Help, GetConnectedClients");
            default:
                return ("Nieznana kmenda - uzyj ~~Help");
        }
    }
}
