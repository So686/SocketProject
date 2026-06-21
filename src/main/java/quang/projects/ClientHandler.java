package quang.projects;
import java.io.*;
import java.net.Socket;
public class ClientHandler implements Runnable {
    private Socket socket;
    private DataInputStream dis;
    private DataOutputStream dos;
    private String clientName;
    public ClientHandler(Socket socket) {
        this.socket = socket;

        try {
            dis = new DataInputStream(socket.getInputStream());
            dos = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        try {
            clientName = dis.readUTF();
            System.out.println(clientName + " connected");
            Server.broadcast(clientName + " joined the server");
            while (true) {
                String message = dis.readUTF();

                if (message.trim().startsWith("/dm")) {

                    String[] parts = message.split(" ", 3);

                    if (parts.length < 3) {
                        sendMessage("Usage: /dm <user> <message>");
                    }
                    else {
                        String recipient = parts[1];
                        String dm = parts[2];

                        Server.directMessage(
                                recipient,
                                "[DM from " + clientName + "]: " + dm
                        );
                    }

                } else {
                    Server.broadcast(clientName + ": " + message);
                }
            }
        } catch (IOException e) {
            System.out.println(clientName + " disconnected");

            Server.removeClient(this);

            Server.broadcast(clientName + " left the server");

            closeEverything();
        }
    }

    public void sendMessage(String message) {
        try {
            dos.writeUTF(message);
            dos.flush();
        } catch (IOException e) {
            closeEverything();
        }
    }

    private void closeEverything() {
        try {
            if (dis != null) dis.close();
            if (dos != null) dos.close();
            if (socket != null) socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getClientName() {
        return clientName;
    }

}
