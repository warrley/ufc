total_album = int(input())
total_baruel = int(input())
stickers = list(map(int, input().split()))

album = [False for _ in range(total_album)]
duplicates = [0 for _ in range(101)]
duplicate = False;

for i in range(total_baruel):
    for j in range(total_album):
        if stickers[i] - 1 == j:
            if album[j] == False:
                album[j] = True
            else:
                duplicates[i] = stickers[i]
                duplicate = True

if duplicate:
    first = True
    for i in range(total_baruel):
        if duplicates[i] != 0:
            if not first:
                print(" ", end="")
            print(duplicates[i], end="")
            first = False
    print()
else:
    print("N")


first = True
for i in range(total_album):
    if not album[i]:
        if not first:
            print(" ", end="")
        print(i + 1, end="")
        first = False

if first: 
    print("N", end="")

print()

# pythonic + efficient

# n = int(input())
# m = int(input())
# stickers = list(map(int, input().split()))

# album = [False] * n
# duplicates = []

# for s in stickers:
#     i = s - 1
#     if album[i]:
#         duplicates.append(s)
#     else:
#         album[i] = True

# faltantes = [str(i + 1) for i in range(n) if not album[i]]

# print(" ".join(map(str, duplicates)) if duplicates else "N")
# print(" ".join(faltantes) if faltantes else "N")
