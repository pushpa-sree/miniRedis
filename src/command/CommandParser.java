package command;

import java.util.Locale;

public class CommandParser {

    public Command parse(String message) {

        String[] parts = message.split(" ", 3);

        
        String commandName = parts[0].toUpperCase(Locale.ROOT);


        if (commandName.equals("PING")) {
            return new Command("PING", null, null);
        }

        if (commandName.equals("SET")) {

            if (parts.length < 3) {
                return null;
            }

            return new Command(
                    "SET",
                    parts[1],
                    parts[2]
            );
        }

        if (commandName.equals("GET")) {

            if (parts.length < 2) {
                return null;
            }

            return new Command(
                    "GET",
                    parts[1],
                    null
            );
        }

        if (commandName.equals("DEL")) {
                if (parts.length < 2) {
                    return null;
                }

                return new Command("DEL", parts[1], null);
            }

        
        if (commandName.equals("EXISTS")) {
            if (parts.length < 2) {
                return null;
            }

            return new Command("EXISTS", parts[1], null);
        }
        
        if (commandName.equals("INCR")) {
            if (parts.length < 2) {
                return null;
            }

            return new Command("INCR", parts[1], null);
        }

 
        return null;
    }
}