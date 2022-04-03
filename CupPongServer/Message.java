import java.io.Serializable;
import java.util.HashMap;

public class Message implements Serializable {
    private String type;
    private HashMap<String, String> params = new HashMap<>();

    public Message(String input) {
        String[] split = input.split(",");
        type = split[0];
        for (int i = 1; i < split.length; i++) {
            String s = split[i];
            String key = s.split(":")[0];
            String value = s.split(":")[1];
            params.put(key, value);
        }
    }

    public String getType() {
        return type;
    }

    public String getParam(String key) {
        return params.get(key);
    }

    @Override
    public String toString() {
        return "Type: " + type + "\n" +
                "Params:\n" + params.toString();
    }
}
