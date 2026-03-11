import socket
from datetime import datetime

server = socket.socket()
server.bind(("0.0.0.0", 5000))
server.listen(1)

print("RPC Server running...")

while True:
    conn, addr = server.accept()
    data = conn.recv(1024).decode()

    client_time, dob = data.split(",")

    server_time = datetime.now().strftime("%Y")

    # check if client time is future
    if int(client_time) > int(server_time):
        conn.send(f"INVALID,{server_time}".encode())

    else:
        birth_year = int(dob)
        age = int(server_time) - birth_year
        conn.send(f"AGE,{age}".encode())

    conn.close()
