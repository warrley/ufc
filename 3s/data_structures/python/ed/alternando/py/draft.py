def printv(v, sword):
    vec_str = list(map(str, v))
    if int(vec_str[sword]) < 0: vec_str[sword] = "<" + vec_str[sword]
    elif int(vec_str[sword]) > 0: vec_str[sword] = vec_str[sword] + ">"
    print(f"[ {" ".join(vec_str)} ]")

def rem(v, i):
    for i in range(i, len(v)-1):
        v[i] = v[i+1]
    v.pop()


# program

size, sword, phase = map(int, input().split())
sword -= 1

vec = [0]*size

for i in range(size):
    if (phase == 1 and i % 2 == 0) or (phase == -1 and i % 2 != 0): vec[i] = i+1
    if (phase == 1 and i % 2 != 0) or (phase == -1 and i % 2 == 0): vec[i] = -(i+1)

while vec:
    printv(vec, sword)
    copy_len = len(vec)
    if vec[sword] > 0: 
        # print(sword, len(vec)-1)
        rem(vec, (sword+1)%len(vec))
        # print(f"s={sword} l={len(vec)} cl={copy_len}")
        if len(vec) > 0:
            if sword == len(vec): sword = (sword+1)%copy_len
            else: sword = (sword+1)%len(vec)
    elif vec[sword] < 0: 
        # print(sword, len(vec)-1)
        rem(vec, (sword-1)%len(vec))
        if len(vec) > 0:
            if  sword != 0: sword = (sword-2)%len(vec)
            else: sword = (sword-2)%copy_len
        

