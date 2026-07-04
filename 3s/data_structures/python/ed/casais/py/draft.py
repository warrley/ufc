n = int(input())
inputs = list(map(int, input().split()))
animals = [0 for _ in range(n)]
couples = 0

for i in range(n):
    flag = False
    for j in range(n):
        if (inputs[i] + animals[j]) == 0:
           couples += 1
           animals[j] = 0
           flag = True
           break
    if not flag: animals[i] = inputs[i] 

print(couples)

# pythonic
# n = int(input())
# inputs = list(map(int, input().split()))

# animals = [0] * n
# couples = 0

# for i, x in enumerate(inputs):
#     for j, y in enumerate(animals):
#         if x + y == 0:
#             couples += 1
#             animals[j] = 0
#             break
#     else:  # executa só se NÃO houve break
#         animals[i] = x

# print(couples)
