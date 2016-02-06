package cs.chat.server;

/**
 * Created by Camellia Sinensis on 05/02/2016.
 */
public class StartServer {

    public static void main(String[] args){
        Input input = new ConsoleInput();
        Output output = new ConsoleOutput();

        Server server = new Server(input, output);
        server.startWorking();
    }
}
