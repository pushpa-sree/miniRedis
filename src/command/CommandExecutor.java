package command;
import datastore.DataStore;

public class CommandExecutor {
     private final DataStore datastore;

    public CommandExecutor(DataStore datastore) {
        this.datastore = datastore;
    }

    public String execute(Command command) {

        if (command.getName().equals("PING")) {
            return "PONG";
        }

        if (command.getName().equals("SET")) {

            datastore.set(command.getKey(), command.getValue());

            return "OK";
        }

        if (command.getName().equals("GET")) {

            String value = datastore.get(command.getKey());

            if (value != null) {
                return value;
            }

            return "nil";
        }
        if (command.getName().equals("DEL")) {
                int deleted = datastore.delete(command.getKey());
                return String.valueOf(deleted);
            }
       
        if (command.getName().equals("EXISTS")) {
            boolean found = datastore.exists(command.getKey());
            return found ? "1" : "0";
        }
        
        if (command.getName().equals("INCR")) {
            try {
                int value = datastore.increment(command.getKey());
                return String.valueOf(value);
            } catch (NumberFormatException e) {
                return "ERR value is not an integer";
            } catch (ArithmeticException e) {
                return "ERR increment would overflow";
            }
        }

       
            return "ERR unknown command";
    }
}
