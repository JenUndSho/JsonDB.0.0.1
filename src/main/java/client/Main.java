package client;

import com.beust.jcommander.JCommander;
import com.google.gson.JsonObject;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class Main {

    private static final String SERVER_ADDRESS = "127.0.0.1";
    private static final int SERVER_PORT = 8889;
    private static final Map<String, String> map = new HashMap<>();
    private static final JsonObject jso = new JsonObject();

    public static void main(String[] args) {
        System.out.println("Client started!");

        try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
             DataInputStream input = new DataInputStream(socket.getInputStream());
             DataOutputStream output = new DataOutputStream(socket.getOutputStream());
        ) {
            Args arguments = new Args();
            JCommander.newBuilder()
                    .addObject(arguments)
                    .build()
                    .parse(args);

            if (arguments.isInput()) {
                String inputStr = arguments.getInput();
                output.writeUTF(inputStr);
                System.out.println("Sent: " + inputStr);
            } else {
                jso.addProperty("type", arguments.getType());
                if (!arguments.isExit())
                    jso.addProperty("key", arguments.getKey());
                if (arguments.isSet())
                    jso.addProperty("value", arguments.getValue());

                output.writeUTF(jso.toString());
                System.out.println("Sent: " + jso);
            }



            String receivedMsg = input.readUTF();
            System.out.println("Received: " + receivedMsg);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}