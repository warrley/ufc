def pot(b: int, n: int) -> float:
    if n == 0:
        return 1.0
    return b * pot(b, n - 1)


def pot(b: int, n: int) -> float:
    res: int = 1
    for _ in range(n):
        res *= b
    return res
