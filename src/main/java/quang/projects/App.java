package quang.projects;

public class App {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage:");
            System.out.println("java quang.projects.App server");
            System.out.println("java quang.projects.App client");
            return;
        }

        String mode = args[0].toLowerCase();

        if (mode.equals("server")) {
            Server.processServer();
        }

        else if (mode.equals("client")) {
            Client.processClient();
        }

        else {
            System.out.println("Invalid mode: " + args[0]);
            System.out.println("Use either:");
            System.out.println("server");
            System.out.println("client");
        }
    }
}
