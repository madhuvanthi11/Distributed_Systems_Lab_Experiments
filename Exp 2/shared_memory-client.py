import socket

server_ip = "SERVER_IP"

client = socket.socket()
client.connect((server_ip, 5000))

choice = input("Enter operation (read/update): ")

if choice == "read":
    client.send("read".encode())
    value = client.recv(1024).decode()
    print("Shared Value:", value)

elif choice == "update":
    client.send("read".encode())
    value = int(client.recv(1024).decode())

    new_value = value + 1
    client.close()

    client = socket.socket()
    client.connect((server_ip, 5000))
    client.send(f"write {new_value}".encode())

    print("Updated Value:", new_value)

client.close()
