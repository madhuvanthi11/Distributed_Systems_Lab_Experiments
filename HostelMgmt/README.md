# Hostel Room Information Service - Java RMI

## Problem Description

This application provides a distributed hostel room information system where students can search for room details including room number, occupant names, and warden contact information. The system uses Java RMI (Remote Method Invocation) to enable distributed communication between the client and server.

## Communication Model Used

**Java RMI (Remote Method Invocation)**

Java RMI is a Java API that allows objects running in one Java Virtual Machine (JVM) to invoke methods on objects running in another JVM. This makes it ideal for distributed applications where different components run on different machines.

### Why RMI for this Module?

1. **Object-Oriented Approach**: RMI allows us to work with remote objects as if they were local, maintaining Java's object-oriented paradigm
2. **Type Safety**: Compile-time type checking ensures robustness
3. **Built-in Serialization**: Automatic serialization of objects for network transfer
4. **Transparent Distribution**: Client code looks similar to local method calls, hiding network complexity

## In-Memory Design Explanation

### Data Structure Choice: HashMap

The application uses a `HashMap<String, RoomInfo>` for in-memory storage because:

1. **Fast Lookup**: O(1) average time complexity for room searches by room number
2. **Natural Key-Value Model**: Room number serves as a unique key
3. **Memory Efficient**: Only stores active room data without database overhead
4. **Simple Management**: Easy to add, update, or remove rooms

### Why In-Memory is Suitable

In real distributed systems, not all data needs persistent storage. In-memory storage is ideal for this hostel module because:

1. **Session-Based Data**: Room information is relatively stable and doesn't change frequently during a session
2. **Quick Response Times**: No disk I/O means faster response to student queries
3. **Caching Pattern**: In production, this could serve as a cache layer with a database backend
4. **Live Service Model**: Data is refreshed when the service restarts, ensuring current information
5. **Low Data Volume**: Hostel room data is limited in size and easily fits in memory

## Architecture

```
┌─────────────────┐         RMI Registry          ┌─────────────────┐
│   RMI Client    │◄───────(Port 1099)──────────► │   RMI Server    │
│   (Swing UI)    │                               │                 │
│                 │    Remote Method Calls        │                 │
│  - Search Room  │◄─────────────────────────────►│ RoomServiceImpl │
│  - View All     │                               │                 │
└─────────────────┘                               │  In-Memory DB   │
                                                  │  (HashMap)      │
                                                  └─────────────────┘
```

### Component Breakdown

1. **RoomInfo.java**: Serializable data model containing room details
2. **RoomService.java**: Remote interface defining available operations
3. **RoomServiceImpl.java**: Implementation with in-memory HashMap storage
4. **RMIServer.java**: Server that registers the service with RMI registry
5. **RMIClient.java**: Swing-based UI client that invokes remote methods

## Steps to Run the Application

### Prerequisites

- Java Development Kit (JDK) 8 or higher
- Basic understanding of command line

### Step 1: Create Project Directory

```bash
mkdir HostelRoomService
cd HostelRoomService
mkdir src
```

### Step 2: Save All Java Files

Save all five Java files in the `src` directory:
- RoomInfo.java
- RoomService.java
- RoomServiceImpl.java
- RMIServer.java
- RMIClient.java

### Step 3: Compile All Files

Open terminal/command prompt in the project directory:

```bash
cd src
javac *.java
```

This will compile all Java files and generate `.class` files.

### Step 4: Start the RMI Server

In the first terminal window:

```bash
java RMIServer
```

You should see:
```
========================================
Hostel Room Information Service Started
========================================
Server is running on port 1099
Service name: RoomService
Waiting for client requests...
```

**Keep this terminal running!**

### Step 5: Start the Client Application

In a second terminal window (from the same src directory):

```bash
java RMIClient
```

A GUI window will open with the Hostel Room Information Service interface.

## Using the Application

### Search for a Room

1. **Method 1**: Type a room number (e.g., "101") in the text field and click "Search"
2. **Method 2**: Select a room from the dropdown menu (auto-fills the text field)
3. Press Enter or click the "Search" button

### View All Rooms

Click the "Show All Rooms" button to see a list of all available rooms with occupancy information.

### Available Test Rooms

The system comes pre-loaded with these rooms:
- 101, 102, 103 (First Floor)
- 201, 202, 203 (Second Floor)
- 301, 302 (Third Floor)

## Design Choices Explained

### 1. Remote Interface Pattern

We separated the interface (`RoomService`) from implementation (`RoomServiceImpl`) following the Java RMI best practice. This allows:
- Clear contract definition
- Flexibility to change implementation
- Support for stub generation

### 2. Serializable Objects

`RoomInfo` implements `Serializable` because RMI needs to transfer objects over the network. This enables:
- Automatic marshalling and unmarshalling
- Complex data structure transfer
- Type safety across network boundaries

### 3. Exception Handling

All remote methods throw `RemoteException` because:
- Network failures can occur
- Client needs to handle communication errors
- Follows RMI specification requirements

### 4. Registry Port 1099

We use the default RMI registry port (1099) because:
- Standard port for RMI applications
- No firewall configuration needed in development
- Easy to remember and configure

### 5. Swing UI Choice

We chose Swing over console because:
- More user-friendly for students
- Better visual presentation of information
- Meets "runnable and demo-ready" requirement
- Professional appearance for lab demonstration

## Technical Highlights

### Two Remote Methods Implemented

1. **getRoomInfo(String roomNumber)**: Fetches specific room details
2. **getAllRoomNumbers()**: Lists all available rooms

Both demonstrate Remote Method Invocation with different return types (object vs. list).

### Distributed Systems Concepts Demonstrated

1. **Remote Method Invocation**: Client-server communication via method calls
2. **Object Serialization**: Data transfer across network boundaries
3. **Stub-Skeleton Interaction**: RMI handles proxy generation automatically
4. **Service Registry**: Centralized service discovery mechanism
5. **Location Transparency**: Clients don't need to know server location details

## Troubleshooting

### "Connection refused" Error

**Problem**: Client cannot connect to server

**Solutions**:
1. Ensure server is running first
2. Check if port 1099 is available: `netstat -an | grep 1099`
3. Verify firewall isn't blocking the port
4. Try running both on the same machine initially

### "Class not found" Error

**Problem**: RMI cannot find compiled classes

**Solutions**:
1. Ensure all files are compiled: `javac *.java`
2. Run from the directory containing `.class` files
3. Check CLASSPATH is set correctly

### Server Stops Responding

**Problem**: Server terminated unexpectedly

**Solutions**:
1. Check for runtime exceptions in server terminal
2. Restart the server application
3. Verify memory isn't exhausted

## Extending the Application

### Adding More Rooms

Edit the `initializeRoomData()` method in `RoomServiceImpl.java`:

```java
roomDatabase.put("104", new RoomInfo(
    "104",
    Arrays.asList("New Student"),
    "+91-9876543210"
));
```

### Adding New Remote Methods

1. Add method signature to `RoomService.java`
2. Implement in `RoomServiceImpl.java`
3. Add UI controls in `RMIClient.java`
4. Recompile all files

### Persistence Layer (Enhancement)

To add database support:
1. Keep HashMap as cache
2. Load initial data from database in constructor
3. Synchronize updates back to database
4. This maintains fast in-memory access with persistence

## Learning Outcomes

After completing this lab, you will understand:

1. How to design and implement RMI-based distributed applications
2. The role of registries in service discovery
3. Remote method invocation mechanics
4. In-memory data management strategies
5. Trade-offs between memory storage and persistent storage
6. Client-server architecture patterns
7. Serialization in distributed systems

## Conclusion

This application demonstrates a practical, usable distributed system for hostel management. The use of Java RMI showcases how distributed objects can communicate seamlessly, while in-memory storage ensures fast response times suitable for a live service environment.

The design prioritizes simplicity, usability, and clear demonstration of distributed systems concepts, making it ideal for educational purposes while remaining practical enough for real-world inspiration.

---
