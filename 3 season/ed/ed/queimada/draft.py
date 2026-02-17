def printm(m):
    for row in m:
        print("".join(row))


def burn(m, i, j):
    if i<0 or i>=len(m) or j<0 or j>=len(m[0]) or  m[i][j] != "#": return
    m[i][j] = "o"
    burn(m, i+1, j)
    burn(m, i-1, j)
    burn(m, i, j-1)
    burn(m, i, j+1)

nl, nc, l, c = map(int, input().split())
m = []
for _ in range(nl):
    m.append(list(input()))

# pythonic
# m = [list(input()) for _ in range(nl)]

burn(m, l, c)
printm(m)