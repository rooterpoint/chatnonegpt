package KorneevEN.chat.server;

import java.io.IOException;

public class ChatServer {
    public static void main(String[] args) {
        try {
            ServerListener server = new ServerListener();
            server.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
