n = int(input())
rocks = [0 for _ in range(n)]

for i in range(n):
    d = 0
    a, b = map(int, input().split());
    if a < 10 or b < 10:
        rocks[i] = -1
    else:
        rocks[i] = abs(a-b)

winner = 101
wvalue = 101

for i in range(n):
    if rocks[i] != -1 and rocks[i] < wvalue:
        winner = i
        wvalue = rocks[i]

if winner != 101:
    print(winner)
else:
    print("sem ganhador")

# pythonic version
# n = int(input())
# rocks = []

# for _ in range(n):
#     a, b = map(int, input().split())
#     rocks.append(-1 if a < 10 or b < 10 else abs(a - b))

# valid = [(i, v) for i, v in enumerate(rocks) if v != -1]

# if valid:
#     winner, _ = min(valid, key=lambda x: x[1])
#     print(winner)
# else:
#     print("sem ganhador")
