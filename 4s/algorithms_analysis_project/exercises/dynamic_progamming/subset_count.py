def subset_count(n: int, r: int) -> int:
    if r == 0 or n == r:
        return 1
    return subset_count(n - 1, r) + subset_count(n - 1, r - 1)


def subset_count_memo(n: int, r: int, memo: dict) -> int:
    if memo[n][r]:
        return memo[n][r]
    if r == 0 or n == r:
        return 1
    memo[n][r] = subset_count(n - 1, r) + subset_count(n - 1, r - 1)
    return memo[n][r]


def subset_count_pd(n: int, r: int) -> int:
    tab = [[1] * (n + 1) for _ in range(r + 1)]

    for i in range(n):
        for j in range(r):
            tab[i][j] = tab[i - 1][j] + tab[i - 1][j]

    return tab[n][r]
