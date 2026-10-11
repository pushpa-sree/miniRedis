# MiniRedis — A Simple In-Memory Key-Value Store in Java

## Overview

MiniRedis is a lightweight, Redis-inspired in-memory key-value store built using Java. It allows clients to connect to a server over TCP sockets and execute commands to store, retrieve, delete, and update data.

The project demonstrates core concepts of Java programming, client-server communication, socket programming, multithreading, command parsing, and concurrent data storage.

## Features

- **PING** — Check whether the server is responding.
- **SET** — Store a value against a key.
- **GET** — Retrieve the value associated with a key.
- **DEL** — Delete a key from the data store.
- **EXISTS** — Check whether a key exists.
- **INCR** — Increment a stored integer value by one.
- Multiple client connections handled using threads.
- In-memory data storage using `ConcurrentHashMap`.
- Basic command validation and error handling.

## Technologies Used

- Java
- TCP sockets (`ServerSocket` and `Socket`)
- Multithreading (`Thread` and `Runnable`)
- `ConcurrentHashMap` for in-memory storage
- VS Code for development

## Project Structure

```text
miniRedis/
├── src/
│   ├── Main.java
│   ├── TestClient.java
│   ├── client/
│   │   └── ClientHandler.java
│   ├── command/
│   │   ├── Command.java
│   │   ├── CommandParser.java
│   │   └── CommandExecutor.java
│   └── datastore/
│       └── DataStore.java
├── README.md
└── out/
```

The `out/` directory is generated when the project is compiled and contains compiled Java class files.

## Architecture

MiniRedis follows a simple client-server architecture.

1. **Main.java:** Starts the server on port `6379` and accepts incoming client connections.
2. **ClientHandler.java:** Handles communication with an individual client.
3. **CommandParser.java:** Parses the client's text input into a command.
4. **Command.java:** Represents a parsed command and its arguments.
5. **CommandExecutor.java:** Executes commands by calling the appropriate data store operations.
6. **DataStore.java:** Stores and manages key-value pairs in memory.
7. **TestClient.java:** Connects to the server and allows users to enter commands interactively.

## How to Run

### Prerequisites

- Java Development Kit (JDK) installed.
- A terminal or command prompt.
- The project source code.

### Step 1: Compile the server

Open a terminal in the project root directory and run:

```bash
javac -d out src/Main.java src/client/ClientHandler.java src/command/Command.java src/command/CommandParser.java src/command/CommandExecutor.java src/datastore/DataStore.java
```

### Step 2: Start the server

```bash
java -cp out Main
```

The server starts listening on port `6379`.

### Step 3: Compile the client

Open a second terminal in the project root directory and run:

```bash
javac -cp out -d out src/TestClient.java
```

### Step 4: Start the client

```bash
java -cp out TestClient
```

You can now enter commands and view the server's responses. Type `EXIT` to close the client.

## Supported Commands

| Command | Example | Description |
|---|---|---|
| PING | `PING` | Checks server responsiveness. |
| SET | `SET name Alice` | Stores a key-value pair. |
| GET | `GET name` | Retrieves a stored value. |
| DEL | `DEL name` | Deletes a key and reports whether it existed. |
| EXISTS | `EXISTS name` | Returns `1` if the key exists, otherwise `0`. |
| INCR | `INCR count` | Increments an integer value by one. |

## Example Session

```text
PING
Server: PONG

SET name Alice
Server: OK

GET name
Server: Alice

EXISTS name
Server: 1

DEL name
Server: 1

GET name
Server: nil

INCR count
Server: 1

INCR count
Server: 2

GET count
Server: 2
```

The exact error messages depend on the command and the error-handling implementation.

## Key Concepts Demonstrated

- **Socket programming:** Enables communication between the client and server over TCP.
- **Multithreading:** Allows the server to handle multiple client connections using separate threads.
- **Data structures:** Uses a key-value map for efficient data access.
- **Command parsing:** Separates user input into a command and its arguments.
- **Separation of concerns:** Divides connection handling, command parsing, command execution, and data storage into separate classes.
- **Exception handling:** Handles invalid numeric values and integer overflow during increment operations.

## Limitations

- Data is stored only in memory and is lost when the server stops.
- The project implements a simplified text-based command protocol rather than the full Redis protocol.
- Only the listed commands are supported.
- Authentication, persistence, expiration of keys, and advanced Redis features are not implemented.

## Future Improvements

- Add file-based persistence or snapshot support.
- Implement key expiration using TTL.
- Add more Redis-inspired commands.
- Improve automated testing.
- Implement a more complete Redis-compatible protocol.

## Conclusion

MiniRedis is a learning project that demonstrates how to build a basic in-memory key-value server in Java. It combines networking, concurrency, data structures, and modular software design in a small, practical application.
