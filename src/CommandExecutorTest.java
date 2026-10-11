
import command.Command;
import command.CommandExecutor;
import datastore.DataStore;

public class CommandExecutorTest {

    public static void main(String[] args) {
        DataStore store = new DataStore();
        CommandExecutor executor = new CommandExecutor(store);

        // Test 1: PING
        assert "PONG".equals(
                executor.execute(new Command("PING", null, null)))
                : "PING should return PONG";

        // Test 2: SET
        assert "OK".equals(
                executor.execute(new Command("SET", "name", "Alice")))
                : "SET should return OK";

        // Test 3: GET an existing key
        assert "Alice".equals(
                executor.execute(new Command("GET", "name", null)))
                : "GET should return the stored value";

        // Test 4: GET a missing key
        assert "nil".equals(
                executor.execute(new Command("GET", "missing", null)))
                : "GET should return nil for a missing key";

        // Test 5: EXISTS for an existing key
        assert "1".equals(
                executor.execute(new Command("EXISTS", "name", null)))
                : "EXISTS should return 1 for an existing key";

        // Test 6: EXISTS for a missing key
        assert "0".equals(
                executor.execute(new Command("EXISTS", "missing", null)))
                : "EXISTS should return 0 for a missing key";

        // Test 7: DEL an existing key
        assert "1".equals(
                executor.execute(new Command("DEL", "name", null)))
                : "DEL should return 1 when deleting an existing key";

        // Test 8: GET after deletion
        assert "nil".equals(
                executor.execute(new Command("GET", "name", null)))
                : "GET should return nil after deletion";

        // Test 9: DEL a missing key
        assert "0".equals(
                executor.execute(new Command("DEL", "missing", null)))
                : "DEL should return 0 for a missing key";

        // Test 10: INCR a missing key
        assert "1".equals(
                executor.execute(new Command("INCR", "score", null)))
                : "INCR should initialize a missing key to 1";

        // Test 11: INCR again
        assert "2".equals(
                executor.execute(new Command("INCR", "score", null)))
                : "INCR should increment the value to 2";

        // Test 12: INCR a non-numeric value
        executor.execute(new Command("SET", "text", "hello"));
        assert "ERR value is not an integer".equals(
                executor.execute(new Command("INCR", "text", null)))
                : "INCR should return an error for non-numeric values";

        // Test 13: INCR beyond the integer limit
        executor.execute(new Command(
                "SET", "large", String.valueOf(Integer.MAX_VALUE)));

        assert "ERR increment would overflow".equals(
                executor.execute(new Command("INCR", "large", null)))
                : "INCR should return an error on integer overflow";

        // Test 14: Unknown command
        assert "ERR unknown command".equals(
                executor.execute(new Command("HELLO", null, null)))
                : "Unknown commands should return an error";

        System.out.println("All CommandExecutor tests passed!");
    }
}
