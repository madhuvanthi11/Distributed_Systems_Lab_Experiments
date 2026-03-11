import threading, time, random

lock = threading.Lock()
queue = []

def process(pid):
    time.sleep(random.uniform(0.1,0.5))
    ts = time.time()
    print(f"P{pid} requesting CS at {ts}")

    with lock:
        queue.append((ts,pid))
        queue.sort()

    while True:
        with lock:
            if queue[0][1] == pid:
                break
        time.sleep(0.01)

    print(f"P{pid} ENTER CS")
    time.sleep(1)
    print(f"P{pid} EXIT CS")

    with lock:
        queue.pop(0)

threads=[threading.Thread(target=process,args=(i,)) for i in range(5)]

for t in threads: t.start()
for t in threads: t.join()
