package cs.chat.client;

/**
 * Created by Camellia Sinensis on 05/02/2016.
 */
public class StartClient {

    public static void main(String[] args){
        Input input = new ConsoleInput();
        Output output = new ConsoleOutput();

        Client client = new Client(input, output);
        if (!client.startWorking()) {
            System.out.println("couldnt create connection");
            System.exit(0);
        }

        while(true){
            client.sendMessage();
        }
    }
}
