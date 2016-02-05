package cs.chat.client;

/**
 * Created by Camellia Sinensis on 05/02/2016.
 */
public class StartClient {

    public static void main(String[] args){
        Input input = new ConsoleInput();
        Output output = new ConsoleOutput();

        Client client = new Client(input, output);
        client.startWorking();

        while(true){
            client.sendMessage();
            // fixme: cannot complete
        }
    }
}
