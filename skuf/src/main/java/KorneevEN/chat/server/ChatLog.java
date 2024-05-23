package KorneevEN.chat.server;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChatLog {
    private final List<String> chatHistory = new ArrayList<>();
    private int pointer = 0;

    public void put(String message, ClientHandler clientSender) throws IOException {
        chatHistory.add(message);
        update(clientSender);
        pointer++;
    }

    private void update(ClientHandler clientSender) throws IOException {
        for (ClientHandler client : ServerListener.getClients()) {
            if (client != clientSender) {
                client.sendMessageToClient(chatHistory.get(pointer));
            }
        }
    }
}



