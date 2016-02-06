package cs.chat.server;

import java.util.Scanner;

/**
 * Created by Camellia Sinensis on 05/02/2016.
 */
public class ConsoleInput implements Input {

    @Override
    public int getPort(){
        Scanner scan = new Scanner(System.in);
        System.out.println("== Enter Port ===\n");
        String port = scan.nextLine().trim();
        return Integer.parseInt(port);
    }
}
