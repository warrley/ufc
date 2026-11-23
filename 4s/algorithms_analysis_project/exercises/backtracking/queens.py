def safety(tab: list, lin: int, col: int):
    for r in range(lin):
        if tab[r] == col or abs(tab[r] - col) == abs(r - lin):
            return False
    return True


def queens(n: int, lin: int, tab: list, sol: list = []) -> list:
    if n == lin:
        sol.append(tab[:])
        return sol

    for col in range(n):
        if safety(tab, lin, col):
            tab[lin] = col
            queens(n, lin + 1, tab, sol)

    return sol
