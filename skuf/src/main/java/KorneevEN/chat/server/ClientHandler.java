package KorneevEN.chat.server;

import java.io.*;
import java.net.Socket;
import java.util.Objects;

public class ClientHandler implements Runnable {
    private final Socket clientSocket;
    private BufferedReader in;
    private BufferedWriter out;
    private final ChatLog log;

    public ClientHandler(Socket clientSocket, ChatLog log) {
        this.clientSocket = clientSocket;
        this.log = log;
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
            String nickName = in.readLine();
            log.put(nickName + " connected to chat", this);
            while (!Thread.currentThread().isInterrupted()) {
                String message = in.readLine();
                if (Objects.isNull(message)) {
                    break;
                }
                System.out.println(nickName + ":" + message);
                log.put(nickName + ":" + message, this);
            }
            log.put(nickName + " disconnected from chat", this);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ServerListener.removeClient(this);
    }

    public void sendMessageToClient(String message) throws IOException {
        out.write(message);
        out.newLine();
        out.flush();
    }
}


