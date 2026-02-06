q, d = input().split(); q = int(q)
coords = []
for _ in range(q):
    x, y = map(int, input().split())
    coords.append((x, y))

for i in range(q-1, 0, -1):
    coords[i] = coords[i-1]
if d == "U":
    coords[0] = (coords[0][0], coords[0][1] - 1)
elif d == "D":
    coords[0] = (coords[0][0], coords[0][1] + 1)
elif d == "R":
    coords[0] = (coords[0][0] + 1, coords[0][1])
elif d == "L":
    coords[0] = (coords[0][0] - 1, coords[0][1])

for pos in coords:
    print(pos[0], pos[1])

q, d = input().split()
q = int(q)

# pythonic

# coords = [tuple(map(int, input().split())) for _ in range(q)]

# moves = {
#     "U": (0, -1),
#     "D": (0, 1),
#     "L": (-1, 0),
#     "R": (1, 0),
# }

# dx, dy = moves[d]

# coords = [
#     (coords[0][0] + dx, coords[0][1] + dy),
#     *coords[:-1]
# ]

# for x, y in coords:
#     print(x, y)
