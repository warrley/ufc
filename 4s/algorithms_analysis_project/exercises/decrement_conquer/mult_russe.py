def mult_russe(a: int, b: int) -> int:
    if b == 0:
        return 0
    return a + mult_russe(a, b - 1)
