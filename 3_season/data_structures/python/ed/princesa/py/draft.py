size, sword = map(int, input().split()); sword -= 1
vector = [i for i in range(1, size+1)]

def printv():
    print("[ ", end="")
    for i in range(size):
        print(vector[i], end="")
        if i == sword:
            print(">", end="")
        print(" ", end="")
    print("]")

while size >= 1:
    printv()

    dead = (sword+1) % size
    for i in range(dead, size-1):
        vector[i] = vector[i+1]

    vector.pop()
    size -= 1

    if size != 0: sword = dead % size

# pythonic
# size, sword = map(int, input().split())
# vector = list(range(1, size + 1))
# sword -= 1  # Adjust to 0-based indexing

# while vector:
#     # 1. Visualization using a Join expression
#     display = [f"{v}>" if i == sword else str(v) for i, v in enumerate(vector)]
#     print(f"[ {' '.join(display)} ]")

#     # 2. Identify the target (the person to the right of the sword)
#     kill_idx = (sword + 1) % len(vector)
    
#     # 3. Remove the target
#     vector.pop(kill_idx)
    
#     # 4. Update sword position
#     if vector:
#         # If the person at the end was killed, the sword wraps to the start
#         sword = kill_idx % len(vector)