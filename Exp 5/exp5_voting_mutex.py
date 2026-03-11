import threading, time, random

N = 5
votes = [True]*N
lock = threading.Lock()

def request_vote():
    with lock:
        granted = sum(votes)
        if granted > N//2:
            for i in range(N):
                votes[i] = False
        return granted

def release_votes():
    with lock:
        print("CS Completed")
        for i in range(N):
            votes[i] = True

def process(pid):
    time.sleep(random.uniform(0.1,0.5))
    print(f"P{pid} requesting votes")

    if request_vote() > N//2:
        print(f"P{pid} ENTER CS")
        time.sleep(1)
        print(f"P{pid} EXIT CS")
    else:
        print(f"P{pid} denied")

    release_votes()

threads=[threading.Thread(target=process,args=(i,)) for i in range(N)]

for t in threads: t.start()
for t in threads: t.join()
