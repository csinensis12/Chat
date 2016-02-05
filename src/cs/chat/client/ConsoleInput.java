package cs.chat.client;

import cs.chat.message.Message;

import java.util.Scanner;

/**
 * Created by Camellia Sinensis on 05/02/2016.
 */
public class ConsoleInput implements Input {

    @Override
    public String getMessage(){
        Scanner scan = new Scanner(System.in);
        System.out.println("== Enter Message: ");
        return scan.nextLine().trim();
    }

    @Override
    public String getReceiver(){
        Scanner scan = new Scanner(System.in);
        System.out.println("== Enter Receiver: ");
        return scan.nextLine().trim();
    }

    @Override
    public String getIp(){
        Scanner scan = new Scanner(System.in);
        System.out.println("== Enter Server IP ===\n");
        return scan.nextLine().trim();
    }

    @Override
    public int getPort(){
        Scanner scan = new Scanner(System.in);
        System.out.println("== Enter Port ===\n");
        String port = scan.nextLine().trim();
        return Integer.parseInt(port);
    }

    @Override
    public String getClientName(){
        Scanner scan = new Scanner(System.in);
        System.out.println("== Enter Server IP ===\n");
        return scan.nextLine().trim();
    }
}
