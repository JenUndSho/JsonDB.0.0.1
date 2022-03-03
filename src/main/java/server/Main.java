package server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class Main {
    private static final int PORT = 8889;

    public static void main(String[] args){
        int recordNum = 0;
        try (ServerSocket server = new ServerSocket(PORT)) {
            System.out.println("Server started!");
            JsonDataBase js = new JsonDataBase();
            String response = "";

            while (true) {
                try (Socket socket = server.accept();
                     DataInputStream input = new DataInputStream(socket.getInputStream());
                     DataOutputStream output = new DataOutputStream(socket.getOutputStream());
                ) {
                    String str = input.readUTF();
                    System.out.println(str);
                    Thread t = new ClientHandler(js, str);

                    t.start();
                    t.join();
                    response = ((ClientHandler) t).getResponse();

                    if (response.equals("exit")) {
                        output.writeUTF("{\"response\":\"OK\"}");
                        return;
                    }

                    output.writeUTF(response);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}