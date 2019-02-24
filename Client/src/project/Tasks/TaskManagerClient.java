package project.Tasks;

import java.io.*;
import java.net.*;

public class TaskManagerClient {

    private static final int SERVER_PORT = 5000;
    private static final String SERVER_NAME = "localhost";
    private Socket clientSocket = null;
    private BufferedReader in;
    private PrintWriter out;
    private String[] tasks = null;
    private String[] tasksToSend = null;
    private String clientName;
    private String clientPassword;
    private String serverResponse;
    private String request;

    public TaskManagerClient(String login, String password, String request) {
        this.clientName = login;
        this.clientPassword = password;
        this.request = request;
        if (contactServer().equals("CONNECTED")) {
            requestsToServer();
            quitServer();
        }
    }

    public TaskManagerClient(String login, String request, String[] tasksToSend) {
        this.clientName = login;
        this.request = request;
        this.tasksToSend = tasksToSend;
        if (contactServer().equals("CONNECTED")) {
            requestsToServer();
            quitServer();
        }
    }

    public String getServerResponse() {
        return serverResponse;
    }

    public String[] getTasks() {
        return tasks;
    }

    private String contactServer() {
        String serverWelcome = null;
        try {
            clientSocket = new Socket(SERVER_NAME, SERVER_PORT);
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())), true);
            serverWelcome = in.readLine();
        } catch (IOException except) {
            System.err.println("Unknown host " + SERVER_NAME + ": " + except);
        }
        return serverWelcome;
    }

    private void requestsToServer() {
        if (connectOK()) {
            try {
                out.println(request);
                switch (request) {
                    case "enterAccount": {
                        out.println(clientName);
                        out.println(clientPassword);
                        if (in.readLine().equals("CORRECT")) {
                            serverResponse = "CORRECT";
                            int tasksCnt = Integer.parseInt(in.readLine());
                            tasks = new String[tasksCnt];
                            for (int i = 0; i < tasksCnt; i++) {
                                tasks[i] = in.readLine();
                            }
                        } else serverResponse = "INCORRECT";
                        break;
                    }
                    case "synchronizeTasks": {
                        out.println(clientName);
                        out.println(tasksToSend.length);
                        for (String taskToSend : tasksToSend) {
                            out.println(taskToSend);
                        }
                        break;
                    }
                }
            } catch (IOException except) {
                System.err.println("Failed I/O to" + SERVER_NAME + ": " + except);
            }
        }
    }

    private void quitServer() {
        try {
            out.close();
            in.close();
            clientSocket.close();
        } catch (IOException except) {
            System.err.println("Failed I/O to " + SERVER_NAME + ": " + except);
        }
    }

    private boolean connectOK() {
        return (in != null && out != null && clientSocket != null);
    }
}
