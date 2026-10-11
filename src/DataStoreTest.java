
import datastore.DataStore;

public class DataStoreTest {
    public static void main(String[] args) {
        DataStore store = new DataStore();

        // Test 1: Store and retrieve a value
        store.set("name", "Alice");
        assert "Alice".equals(store.get("name"))
                : "GET should return the stored value";

        // Test 2: A missing key should return null
        assert store.get("missing") == null
                : "A missing key should return null";

        // Test 3: Check whether a key exists
        assert store.exists("name")
                : "EXISTS should return true for an existing key";

        assert !store.exists("missing")
                : "EXISTS should return false for a missing key";

        // Test 4: Delete an existing key
        assert store.delete("name") == 1
                : "DEL should return 1 when a key is deleted";

        assert store.get("name") == null
                : "Deleted key should no longer exist";

        // Test 5: Deleting a missing key
        assert store.delete("missing") == 0
                : "DEL should return 0 for a missing key";
        // Test 6: Increment a missing key
        assert store.increment("score") == 1
                : "INCR on a missing key should return 1";

        // Test 7: Increment an existing numeric value
        assert store.increment("score") == 2
                : "INCR should increase the value by 1";

        // Test 8: Increment a negative number
        store.set("negative", "-5");
        assert store.increment("negative") == -4
                : "INCR should correctly increment a negative number";

        // Test 9: Reject a non-numeric value
        store.set("text", "hello");
        boolean invalidNumberThrown = false;

        try {
            store.increment("text");
        } catch (NumberFormatException e) {
            invalidNumberThrown = true;
        }

        assert invalidNumberThrown
                : "INCR should reject non-numeric values";

        // Test 10: Reject integer overflow
        store.set("large", String.valueOf(Integer.MAX_VALUE));
        boolean overflowThrown = false;

        try {
            store.increment("large");
        } catch (ArithmeticException e) {
            overflowThrown = true;
        }

        assert overflowThrown
                : "INCR should reject values beyond the integer limit";
                System.out.println("All DataStore tests passed!");
            }
        }

