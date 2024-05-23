package KorneevEN.chat.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerListener {
    private ServerSocket serverSocket;
    private static final List<ClientHandler> clients = new ArrayList<>();
    private final ExecutorService executorService = Executors.newFixedThreadPool(10);

    public void start() throws IOException {
        serverSocket = new ServerSocket(27015);
        ChatLog log = new ChatLog();
        while (true) {
            Socket incomingConnection = serverSocket.accept();
            ClientHandler client = new ClientHandler(incomingConnection, log);
            clients.add(client);
            executorService.execute(client);
        }
    }

    public static List<ClientHandler> getClients() {
        return clients;
    }

    public static void removeClient(ClientHandler clientHandler) {
        clients.remove(clientHandler);
    }
}


