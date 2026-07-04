def printv(v):
    print("[ ", end="")
    print(*v, end="")
    print(" ]")

def sum_neighbors(v, i):
    if len(v)-1 == i: return []
    sum = v[i] + v[i+1]
    return [sum] + sum_neighbors(v, i+1)

def triangle(vec):
    if len(vec) == 1: 
        printv(vec)
        return
    vecb = sum_neighbors(vec, 0)
    triangle(vecb)
    printv(vec)

def main():
    vec = list(map(int, input().split()))
    triangle(vec)

if __name__ == "__main__":
    main()