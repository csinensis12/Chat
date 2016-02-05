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
        String msg = scan.nextLine().trim();
        return msg;
    }

    @Override
    public String getReceiver(){
        Scanner scan = new Scanner(System.in);
        System.out.println("== Enter Receiver: ");
        String receiver = scan.nextLine().trim();
        return receiver;
    }

    @Override
    public String getIp(){
        Scanner scan = new Scanner(System.in);
        System.out.println("== Enter Server IP ===\n");
        String Ip = scan.nextLine().trim();
        return Ip;
    }

    @Override
    public int getPort(){
        Scanner scan = new Scanner(System.in);
        System.out.println("== Enter Port ===\n");
        String port = scan.nextLine().trim();
        int intPort = Integer.getInteger(port);
        return intPort;
    }

    @Override
    public String getClientName(){
        Scanner scan = new Scanner(System.in);
        System.out.println("== Enter Server IP ===\n");
        String clientName = scan.nextLine().trim();
        return clientName;
    }
}
