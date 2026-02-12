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
