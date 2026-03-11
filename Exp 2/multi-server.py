import socket
import threading

clients = []

def broadcast(msg):
    for c in clients:
        c.send(msg)

def handle_client(client):
    while True:
        try:
            msg = client.recv(1024)
            broadcast(msg)
        except:
            clients.remove(client)
            client.close()
            break

server = socket.socket()
server.bind(("0.0.0.0", 5000))
server.listen()

print("Server started...")

while True:
    client, addr = server.accept()
    print("Connected:", addr)
    clients.append(client)

    thread = threading.Thread(target=handle_client, args=(client,))
    thread.start()
