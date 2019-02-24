
import java.io.*;
import java.net.*;

public class taskManagerServer {

    private static final int SERVER_PORT = 5000;
    private static final int MAX_CLIENTS = 3;
    private static final File FILE_PATH = new File("C:\\DataBase");
    private ServerSocket listenSocket;
    private boolean keepRunning = true;
    private File[] paths;

    taskManagerServer() {
        loadDataBase();
        try {
            listenSocket = new ServerSocket(SERVER_PORT, MAX_CLIENTS);
        } catch (IOException except) {
            System.err.println("Unable to listen on port " + SERVER_PORT);
            System.exit(1);
        }
    }

    private void loadDataBase() {
        paths = FILE_PATH.listFiles();
    }

    void startServer() {
        Socket clientSocket;
        try {
            while (keepRunning) {
                System.out.println("Waiting for the next client...");
                clientSocket = listenSocket.accept();
                new Thread(new clientHandler(clientSocket, paths)).start();
            }
            listenSocket.close();
        } catch (IOException e) {
            System.err.println("Failed I/O " + e);
        }
    }

    protected void stopServer() {
        if (keepRunning) {
            keepRunning = false;
        }
    }
}
