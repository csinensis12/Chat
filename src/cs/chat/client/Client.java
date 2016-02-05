package cs.chat.client;

/**
 * Created by Camellia Sinensis on 05/02/2016.
 */
public class Client {

    private Object connection;

    public Client (){
        // anything to do here?
    }

    public void start() {
        establishConnection();
    }

    private void establishConnection() {
        // create socket
        // talk to server
        // hello messages, introduce yourself etc
    }
}
