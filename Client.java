package chat;

import java.io.*;
import java.net.*;
import java.util.*;

public class Client {

    public static void main(String[] args) throws Exception {

        Scanner sc = new Scanner(System.in);
        System.out.println("NICKNAME: ");
        String nickname = sc.next();

        final Socket cs = new Socket("localhost", 7);
        final DataInputStream is = new DataInputStream(cs.getInputStream());
        final DataOutputStream os = new DataOutputStream(cs.getOutputStream());

        String fromKeyboard = null;

        Thread thread = new Thread(new Runnable() {

            public void run() {
                while (true) {
                    String fromServer = null;
                    try {
                        fromServer = is.readUTF();
                        System.out.println(fromServer);
                    } catch (IOException ex) {
                    }
                }
            }
        });

        thread.start();
        os.writeUTF(nickname);
        while (true) {
            fromKeyboard = sc.nextLine();
            os.writeUTF(fromKeyboard);
            if (fromKeyboard.equals("QUIT")) {
                is.close();
                os.close();
                cs.close();
                System.exit(1);
            }
        }
    }
}
