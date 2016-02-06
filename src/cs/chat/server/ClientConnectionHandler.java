package cs.chat.server;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

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
    public void run(){
        System.out.println("Connected with " + userName);
    }
}
