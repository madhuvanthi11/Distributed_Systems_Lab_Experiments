import socket
from datetime import datetime

server_ip = "SERVER_IP"

dob = input("Enter birth year: ")

# send dummy future time intentionally
client_time = str(datetime.now().year + 5)

while True:

    client = socket.socket()
    client.connect((server_ip, 5000))

    client.send(f"{client_time},{dob}".encode())

    response = client.recv(1024).decode()

    status, value = response.split(",")

    if status == "INVALID":
        print("Client time incorrect")
        print("Correct server time:", value)

        client_time = value  # update time

    else:
        print("Your Age:", value)
        break

    client.close()

client.close()
