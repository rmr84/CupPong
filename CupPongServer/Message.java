import java.util.HashMap;

public class Message {
    private String type;
    private HashMap<String, String> params = new HashMap<>();

    public Message(String input) {
        String[] split = input.split(",");
        type = split[0];
        for (String s : split) {
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
