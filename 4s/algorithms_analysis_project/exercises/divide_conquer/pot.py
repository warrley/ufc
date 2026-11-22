def pot(b: int, n: int) -> int:
    if n == 0:
        return 1

    half = pot(b, n // 2)

    if b % 2 == 0:
        return 2 * half
    else:
        return b * half
