package quang.projects;
import java.io.*;
import java.net.*;
import java.util.*;
public class Client {
    public static void processClient() {
        try {
            Socket socket = new Socket("localhost", 8080);

            DataInputStream dis = new DataInputStream(socket.getInputStream());
            DataOutputStream dos = new DataOutputStream(socket.getOutputStream());

            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter your name: ");
            String name = scanner.nextLine();
            dos.writeUTF(name);

            dos.flush();

            Thread receiveThread = new Thread(() -> {
                try {
                    while (true) {
                        String message = dis.readUTF();
                        System.out.println(message);
                    }
                } catch (IOException e) {
                    System.out.println("Disconnected from server");
                }
            });

            receiveThread.start();

            while (true) {
                String message = scanner.nextLine();

                dos.writeUTF(message);
                dos.flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        processClient();
    }
}
