package KorneevEN.chat.client;

import java.io.*;
import java.net.*;

public class ChatClient {
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;

    public ChatClient(String serverAddress, int port) throws IOException {
        socket = new Socket(serverAddress, port);
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    public void start() {
        Thread inputThread = new Thread(new InputHandler());
        inputThread.start();

        try {
            String message;
            while ((message = reader.readLine()) != null) {
                System.out.println(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(String message) throws IOException {
        writer.write(message);
        writer.newLine();
        writer.flush();
    }

    private class InputHandler implements Runnable {
        @Override
        public void run() {
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            try {
                String input;
                while ((input = userInput.readLine()) != null) {
                    sendMessage(input);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        try {
            ChatClient client = new ChatClient("localhost", 27015);
            client.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
