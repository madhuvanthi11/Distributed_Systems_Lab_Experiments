n = int(input("Enter number of processes: "))
clocks = [0] * n

events = int(input("Enter number of events: "))

for _ in range(events):
    event = input("\nEnter event (internal/send/receive): ").lower()

    if event == "internal":
        p = int(input("Process ID: ")) - 1
        clocks[p] += 1
        print(f"Process P{p+1} internal event (Clock = {clocks[p]})")

    elif event == "send":
        sender = int(input("Sender Process ID: ")) - 1
        receiver = int(input("Receiver Process ID: ")) - 1
        clocks[sender] += 1
        msg_clock = clocks[sender]
        print(f"Process P{sender+1} sends message to P{receiver+1} "
              f"(Clock = {msg_clock})")

    elif event == "receive":
        receiver = int(input("Receiver Process ID: ")) - 1
        received_clock = int(input("Received Clock Value: "))
        clocks[receiver] = max(clocks[receiver], received_clock) + 1
        print(f"Process P{receiver+1} receives message "
              f"(Clock = {clocks[receiver]})")
