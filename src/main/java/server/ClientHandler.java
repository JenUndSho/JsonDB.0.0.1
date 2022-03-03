package server;

public class ClientHandler extends Thread {
    private JsonDataBase js;
    private String line;
    private String response;

    public ClientHandler(JsonDataBase js, String line) {
        this.js = js;
        this.line = line;
        response = "";
    }

    @Override
    public void run() {
        response = js.executeProgram(line);
    }

    public String getResponse() {
        return response;
    }
}
