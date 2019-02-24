import java.io.*;
import java.net.*;

class clientHandler implements Runnable {

    private File[] paths;
    private File clientPath;
    private PrintWriter out;
    private BufferedReader in;
    private Socket clientSocket;
    private static final File FILE_PATH = new File("C:\\DataBase");
    clientHandler(Socket clientSocket, File[] paths) {
        this.clientSocket = clientSocket;
        this.paths = paths;
    }

    private String[] readTasks() {
        String fileLine;
        StringBuilder inputBuffer = new StringBuilder();
        int tasksCnt = 0;
        try {
            BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(clientPath + "\\" + "tasks.txt")));
            while ((fileLine = in.readLine()) != null) {
                inputBuffer.append(fileLine).append("\n");
                tasksCnt++;
            }
            in.close();
        } catch (FileNotFoundException except) {
            System.err.println("Unable to find file: " + except);
        } catch (IOException except) {
            System.err.println("I/O exception: " + except);
        }
        String[] tasks = new String[tasksCnt];
        String inputString = inputBuffer.toString();
        int stringStart = 0;
        int stringEnd;
        for (int index = 0; index < tasksCnt; index++) {
            stringEnd = inputString.indexOf("\n", stringStart);
            if (stringEnd == -1) {
                tasks[index] = inputString.substring(stringStart);
            } else {
                tasks[index] = inputString.substring(stringStart, stringEnd);
            }
            stringStart = stringEnd + 1;
        }
        return tasks;
    }

    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream())), true);
            out.println("CONNECTED");
            String recievedCommand = in.readLine();
            if (recievedCommand.indexOf("enterAccount") == 0) {
                String recievedName = in.readLine();
                String recievedPassword = in.readLine();
                for (File path : paths) {
                    if (path.toString().equals(FILE_PATH + "\\" + recievedName)) {
                        clientPath = path;
                        break;
                    }
                }
                BufferedReader input = new BufferedReader(new InputStreamReader(new FileInputStream(clientPath + "\\" + "info.txt")));
                String realPassword = input.readLine();
                input.close();
                if (realPassword.equals(recievedPassword)) {
                    out.println("CORRECT");
                    String[] tasks = readTasks();
                    out.println(tasks.length);
                    for (String task : tasks) {
                        out.println(task);
                    }
                } else {
                    out.println("INCORRECT");
                }
            }
            if (recievedCommand.indexOf("synchronizeTasks") == 0) {
                String clientName = in.readLine();
                int tasksCnt = Integer.parseInt(in.readLine());
                PrintWriter toDataBase = new PrintWriter(FILE_PATH + "\\" + clientName + "\\" + "tasks.txt");
                for(int i = 0; i < tasksCnt; i++){
                    toDataBase.println(in.readLine());
                }
                toDataBase.close();
            }
        } catch (IOException e) {
            System.err.println("Failed I/O " + e);
        } finally {
            try {
                out.close();
                in.close();
                clientSocket.close();
            } catch (IOException e) {
                System.err.println("Failed I/O " + e);
            }
        }
    }
}



























            /*while((nextCommand=clientReceive.readLine())!=null) {
                nextCommand=nextCommand.toUpperCase();
                if(nextCommand.indexOf("QUIT")==0) break;
                if(nextCommand.indexOf("GETALL")==0){
                    clientSend.println(tasks[0]);
                    clientSend.flush();
                    clientSend.println(tasks[1]);
                    clientSend.flush();
                }
                *//*else if(nextCommand.indexOf("STOCK: ")==0)
                {
                    quoteID=nextCommand.substring("STOCK: ".length());
                    quoteResponse=getQuote(quoteID);
                    clientSend.println(quoteResponse);
                    clientSend.flush();
                }*//*
                else {
                    clientSend.println("-ERR UNKNOWN COMMAND");
                    clientSend.flush();
                }
            }*/
            /*clientSend.println("+BUY");
            clientSend.flush();*/