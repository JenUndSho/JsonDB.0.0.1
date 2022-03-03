package client;

import com.beust.jcommander.Parameter;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static server.Constants.CLIENT_DATA_PATH;

public class Args {

    @Parameter(names = "-t", description = "type of the request")
    private String type;

    @Parameter(names = "-k", description = "index of the cell")
    private String key;

    @Parameter(names = "-v", variableArity = true, description = "value to save in the database")
    private String value;

    @Parameter(names = "-in", description = "file to read the request")
    private String input;

    public String getValue() {
        return String.join(" ", value);
    }

    public String getKey() {
        return key;
    }

    public String getType() {
        return type;
    }

    public boolean isSet() {
        return "set".equals(type);
    }

    public boolean isExit() {
        return "exit".equals(type);
    }

    public boolean isInput() { return input != null;}

    public String getInput() {
        String request = "";
        try {
            request = new String(Files.readAllBytes(Paths.get(CLIENT_DATA_PATH + input)));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return request.trim();
    }
}
