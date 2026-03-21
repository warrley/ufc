def minv(v: list[int], i: int) -> int:
    if len(v) == 0: return -1
    elif len(v)-1 == i: return i
    i_min_rest = minv(v, i+1)
    return i_min_rest if v[i_min_rest] < v[i] else i

# nao entendi essa funcao, 4*5*6 = 44???
def mult(v: list[int], i: int) -> int:
    if i == len(v): return 1
    return v[i] * sum(v, i+1)

def sum(v: list[int], i: int) -> int:
    if i == len(v): return 0
    return v[i] + sum(v, i+1)

def reverse(v: list[int], i: int, j:int) -> None:
    if i >= j: return
    aux: int = v[i]
    v[i] = v[j]
    v[j] = aux
    torev(v, i+1, j-1)

def torev(v: list[int], i: int, j: int) -> None:
    if i >= j: return
    aux: int = v[i]
    v[i] = v[j]
    v[j] = aux
    torev(v, i+1, j-1)

# torev returning a new vector
# def torev2(v: list[int], i: int) -> list[int]:
#     if i == len(v): return []
#     last = v[len(v)-1-i]
#     return [last] + torev2(v, i+1)

def main() -> None:
    vector: list[int] = []
    while True:    
        line: str = input()
        print("$"+line)
        args: list[str] = line.split(maxsplit=1)
        match args[0]:
            case "end": break
            case "read": vector = list(map(int, args[1].split() if len(args)>1 else []))
            case "tostr": print(vector)
            case "torev": torev(vector, 0, len(vector)-1); print(vector)
            case "reverse": reverse(vector, 0, len(vector)-1)
            case "sum": print(sum(vector, 0))
            case "mult": print(mult(vector, 0))
            case "min": print(minv(vector, 0))

        

if __name__ == "__main__":
    main()
