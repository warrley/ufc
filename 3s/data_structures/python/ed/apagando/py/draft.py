n = int(input())
queue = list(map(int, input().split()))
m = int(input())
leave = set(map(int, input().split()))

result = []

for person in queue:
    if person not in leave:
        result.append(person)

for r in result:
    print(r, end=" ")
print()

# # pythonic 

# n = int(input())
# queue = list(map(int, input().split()))
# m = int(input())
# leave = set(map(int, input().split()))

# result = [person for person in queue if person not in leave]

# print(*result)

