def order_tuples(v):
    for i in range(len(v)):
        for j in range(len(v)):
            if v[i][0] < v[j][0]:
                aux = v[i]
                v[i] = v[j]
                v[j] = aux
    return v
def occurr(v):
    uniques = set(abs(x) for x in v)
    res = []

    for u in uniques:
        count = 0
        for n in v:
            n = abs(n)
            if u == n: count += 1
        res.append((u,count))
    
    print(order_tuples(res))

def teams(v):
    absolutes = [abs(x) for x in v]
    res = []
    
    i = 0
    while i < len(absolutes):
        j = i
        count = 0
        while j < len(absolutes) and absolutes[i] == absolutes[j]:
            count += 1
            j += 1

        res.append((absolutes[i], count))
        i = j

    print(res)

def mnext(v):
    res = [0]*len(v)
    
    for i in range(len(res)):
        if v[i] < 0: continue
        count = 0
        if i > 0 and v[i-1] < 0: count += 1
        if i < len(res)-1 and v[i+1] < 0: count += 1
        # print(f"i={i} c={count}")
        res[i] = count

    print(res)

def alone(v):
    res = [0]*len(v)
    if len(v) != 1:
        for i in range(len(v)):
            if v[i] < 0: continue
            if i == 0 and v[i+1] > 0: res[i] = 1
            elif i == len(v)-1 and v[i-1] > 0: res[i] = 1
            elif v[i-1] > 0 and v[i+1] > 0: res[i] = 1
    else: res[0] = 1

    print(res)


def couple(v):
    count = 0
    for i in range(len(v)):
        if v[i] == 0: continue
        for j in range(i+1, len(v)):
            if v[j] == 0: continue
            if v[i] == -v[j]:
                v[i] = v[j] = 0
                count += 1
    print(count)

def subseq(values, subvalues):
    # print(values, subvalues)
    match = -1
    for i in range(len(values)):
        count = 0
        for j in range(len(subvalues)):
            if i+j < len(values) and values[i+j] == subvalues[j]: 
                count += 1
                # print(f"i={i} j={j} c={count} v[i+j]={values[i+j]} s[j]={subvalues[j]}")
        if count == len(subvalues): 
            match = i
            break
    
    print(match)

def clear(v, number):
    res = []
    for vx in v:
        if vx != number: res.append(vx)

    print(res)

def erase(vs, ids):
    ids = set(ids)
    res = []

    for i in range(len(vs)):
        if i not in ids: res.append(vs[i])

    print(res)

while True:
    line = input()
    print("$"+line)
    args = line.split(" ")

    if args[0] == "end": break
    elif args[1] == "[]": print("[]")
    elif args[0] == "occurr": occurr(list(map(int, args[1].strip("[]").split(","))))
    elif args[0] == "teams": teams(list(map(int, args[1].strip("[]").split(","))))
    elif args[0] == "mnext": mnext(list(map(int, args[1].strip("[]").split(","))))
    elif args[0] == "alone": alone(list(map(int, args[1].strip("[]").split(","))))
    elif args[0] == "couple": couple(list(map(int, args[1].strip("[]").split(","))))
    elif args[0] == "subseq": subseq(list(map(int, args[1].strip("[]").split(","))), list(map(int, args[2].strip("[]").split(","))))
    elif args[0] == "erase": erase(list(map(int, args[1].strip("[]").split(","))), list(map(int, args[2].strip("[]").split(","))))
    elif args[0] == "clear": clear(list(map(int, args[1].strip("[]").split(","))), int(args[2]))
    else: print("invalid input")