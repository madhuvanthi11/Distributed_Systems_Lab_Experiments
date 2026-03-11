def time_to_minutes(time_str):
    h, m = map(int, time_str.split(":"))
    return h * 60 + m

def minutes_to_time(minutes):
    h = minutes // 60
    m = minutes % 60
    return f"{h:02d}:{m:02d}"

daemon_time = input("Enter Time Daemon (HH:MM): ")
n = int(input("Enter number of nodes: "))

node_times = []
for i in range(n):
    t = input(f"Enter Node {i+1} Time (HH:MM): ")
    node_times.append(t)

daemon_minutes = time_to_minutes(daemon_time)
node_minutes = [time_to_minutes(t) for t in node_times]

all_times = [daemon_minutes] + node_minutes
average_time = sum(all_times) // len(all_times)

print("\nAfter Synchronization...")
print(f"Time Daemon : {minutes_to_time(average_time)}")

for i, t in enumerate(node_minutes):
    correction = average_time - t
    sign = "+" if correction > 0 else ""
    print(f"Node {i+1} : {minutes_to_time(average_time)} "
          f"[Correction Value: {sign}{correction}]")
