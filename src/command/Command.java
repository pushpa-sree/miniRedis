package command;

public class Command {

    private final String name;
    private final String key;
    private final String value;

    public Command(String name, String key, String value) {
        this.name = name;
        this.key = key;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}