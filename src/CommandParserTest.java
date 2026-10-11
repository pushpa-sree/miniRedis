
import command.Command;
import command.CommandParser;

public class CommandParserTest {

    public static void main(String[] args) {
        CommandParser parser = new CommandParser();

        // Test 1: Parse PING
        Command ping = parser.parse("PING");
        assert ping != null : "PING should produce a command";
        assert "PING".equals(ping.getName())
                : "Command name should be PING";

        // Test 2: Parse SET with a key and value
        Command set = parser.parse("SET name Alice");
        assert set != null : "SET should produce a command";
        assert "SET".equals(set.getName())
                : "Command name should be SET";
        assert "name".equals(set.getKey())
                : "SET key should be name";
        assert "Alice".equals(set.getValue())
                : "SET value should be Alice";

        // Test 3: Parse GET
        Command get = parser.parse("GET name");
        assert get != null : "GET should produce a command";
        assert "GET".equals(get.getName())
                : "Command name should be GET";
        assert "name".equals(get.getKey())
                : "GET key should be name";

        // Test 4: Command names should be case-insensitive
        Command lower = parser.parse("ping");
        assert lower != null : "Lowercase ping should be parsed";
        assert "PING".equals(lower.getName())
                : "Command name should be normalized to uppercase";

        // Test 5: SET should preserve spaces in its value
        Command spaced = parser.parse("SET greeting hello world");
        assert spaced != null : "SET with a multi-word value should parse";
        assert "hello world".equals(spaced.getValue())
                : "SET should preserve spaces inside the value";

        System.out.println("All CommandParser tests passed!");
    }
}
