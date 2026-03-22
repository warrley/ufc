size_string = int(input())
strings = input().split(" ")
size_substring = int(input())
substrings = input().split(" ")

res = [0]*size_substring

for i in range(len(substrings)):
    for j in range(len(strings)):
        if substrings[i] == strings[j]: res[i] += 1
        # print(f"ws={substrings[i]} wt={strings[j]} c={res}")

print(*res)