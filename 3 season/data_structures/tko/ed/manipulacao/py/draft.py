def get_men(v):
    res = [x for x in v if x > 0]
    print(res)

def get_calm_women(v):
    res = [x for x in v if x < 0 and x > -10]
    print(res)

def sort(v):
    for i in range(len(v)):
        for j in range(len(v)):
            if v[i] < v[j]:
                aux = v[i]
                v[i] = v[j]
                v[j] = aux
    print(v)

def sort_stress(v):
    for i in range(len(v)):
        for j in range(len(v)):
            if abs(v[i]) < abs(v[j]):
                aux = v[i]
                v[i] = v[j]
                v[j] = aux
    print(v)

def reverse(v):
    reversed = [v[i] for i in range(len(v)-1, -1, -1)]
    print(v)
    print(reversed)

def unique(v):
    # c style
    # res = []

    # for eli in v:
    #     exist = False
    #     for elj in res:
    #         if eli == elj:
    #             exist = True
        
    #     if not exist: res.append(eli)

    # pythonic
    dup = set()
    res = []
    for el in v:
        if el not in dup:
            res.append(el)
            dup.add(el)
    print(res)

def repeated(v):
    # c style
    # visited = [False]*1000
    # dup = []

    # for i in range(len(v)):
    #     if visited[v[i]]:
    #         dup.append(v[i])
    #     visited[v[i]] = True

    # print(dup)


    # pythonic
    vistos = set()
    res = [] # Usamos uma LISTA para permitir duplicatas no resultado
    
    for x in v:
        if x in vistos:
            res.append(x)
        else:
            vistos.add(x) 
    
    print(res)


while True:
    line = input()
    print("$" + line)

    args = line.split(" ")
    if args[0] == "end": break
    elif args[1] == "[]": print("[]\n[]")
    elif args[0] == "get_men": get_men(list(map(int, args[1].strip("[]").split(","))))
    elif args[0] == "get_calm_women": get_calm_women(list(map(int, args[1].strip("[]").split(","))))
    elif args[0] == "sort": sort(list(map(int, args[1].strip("[]").split(","))))
    elif args[0] == "sort_stress": sort_stress(list(map(int, args[1].strip("[]").split(","))))
    elif args[0] == "reverse": reverse(list(map(int, args[1].strip("[]").split(","))))
    elif args[0] == "unique": unique(list(map(int, args[1].strip("[]").split(","))))
    elif args[0] == "repeated": repeated(list(map(int, args[1].strip("[]").split(","))))
    else: print("invalid input"); break;