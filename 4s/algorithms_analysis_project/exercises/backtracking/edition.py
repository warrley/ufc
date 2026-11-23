def edition(a: str, b: str, i: int, j: int):
    if i == 0:
        return j
    if j == 0:
        return i
    if a[i] == b[j]:
        return edition(a, b, i - 1, j - 1)

    ins = 1 + edition(a, b, i, j - 1)
    rem = 1 + edition(a, b, i - 1, j)
    sub = 1 + edition(a, b, i - 1, j - 1)

    return min(ins, rem, sub)
