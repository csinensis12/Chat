package cs.chat.message;

import cs.chat.server.ClientConnectionHandler;

import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by Camellia Sinensis on 05/02/2016.
 */
public class MessageSender extends Thread{
    private final HashMap clientHashMap;
    private final Message msg;

    public MessageSender(HashMap clientHashMap, Message msg) {
        this.clientHashMap = clientHashMap;
        this.msg = msg;
    }

    @Override
    public void run(){
        ClientConnectionHandler client = (ClientConnectionHandler)clientHashMap.get(msg.getReceiver());
        if (client != null)
            client.send(msg);
        else {
            client = (ClientConnectionHandler) clientHashMap.get(msg.getTransmitter());
            client.send(new Message("server", msg.getTransmitter(), msg.getReceiver() + " doesn't exist :("));
        }
    }
}
