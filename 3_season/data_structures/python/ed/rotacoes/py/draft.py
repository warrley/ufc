size, rotation = map(int, input().split(" "))
vec = list(map(int, input().split(" ")))

res = [0]*size

for i in range(size):
    res[(i+rotation)%size] = vec[i]

print(f"[ {" ".join(map(str, res))} ]")