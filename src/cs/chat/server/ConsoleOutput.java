package cs.chat.server;

/**
 * Created by Camellia Sinensis on 05/02/2016.
 */
public class ConsoleOutput implements Output {

    @Override
    public void displayLog(String str){
        System.out.println(str);
    }
}
