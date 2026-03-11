import socket
import threading

def receive():
    while True:
        try:
            msg = client.recv(1024).decode()
            print(msg)
        except:
            break

client = socket.socket()
client.connect(("SERVER_IP", 5000))

threading.Thread(target=receive).start()

while True:
    msg = input()
    if msg == "exit":
        break
    client.send(msg.encode())

client.close()
