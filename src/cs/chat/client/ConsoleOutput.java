package cs.chat.client;

import cs.chat.message.Message;

/**
 * Created by Camellia Sinensis on 05/02/2016.
 */
public class ConsoleOutput implements Output {

    @Override
    public void displayMessage(Message msg){
        System.out.println(msg.getTransmitter()+": "+msg.getMessage());
    }
}
