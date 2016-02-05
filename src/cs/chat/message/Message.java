package cs.chat.message;

import java.io.Serializable;

/**
 * Created by Camellia Sinensis on 05/02/2016.
 */

public class Message implements Serializable{

    private final String message;
    private final String transmitter;
    private final String receiver;
    /*
        possible messages: hello, bad_name,
        possible rec/tran: server

     */

    public Message(String transmitter, String receiver, String message){
        this.transmitter = transmitter;
        this.receiver = receiver;
        this.message = message;
    }

    public String getMessage() { return message; }
    public String getTransmitter() { return transmitter; }
    public String getReceiver(){ return receiver; }
}
