import socket

shared_value = 0

server = socket.socket()
server.bind(("0.0.0.0", 5000))
server.listen()

print("Shared Memory Server Started...")

while True:
    conn, addr = server.accept()
    data = conn.recv(1024).decode()

    if data == "read":
        conn.send(str(shared_value).encode())

    elif data.startswith("write"):
        shared_value = int(data.split()[1])
        conn.send("Value Updated".encode())

    conn.close()
