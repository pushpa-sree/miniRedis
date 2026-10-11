package datastore;

import java.util.concurrent.ConcurrentHashMap;
public class DataStore {
    private final ConcurrentHashMap<String , String> data = new ConcurrentHashMap<>();
    public void set(String key , String value){
        data.put(key , value);
    }
    public String get(String key){
        return data.get(key); 
    }
    public int delete(String key) {
        String removedValue = data.remove(key);

        if (removedValue != null) {
            return 1;
        }

        return 0;
    }
    
    public boolean exists(String key) {
        return data.containsKey(key);
    }
    
    public synchronized int increment(String key) {
        String value = get(key);

        int current = (value == null) ? 0 : Integer.parseInt(value);
        int updated = Math.addExact(current, 1);

        set(key, String.valueOf(updated));

        return updated;
    }


}
