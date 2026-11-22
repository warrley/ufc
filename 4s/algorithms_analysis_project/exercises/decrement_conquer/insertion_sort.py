def insertion_sort(L: list, n: int):
    if n <= 1:
        return

    insertion_sort(L, n - 1)
    last = L[n - 1]
    j = n - 2

    while j >= 0 and L[j] > last:
        L[j + 1] = L[j]
        j -= 1
    L[j + 1] = last


def insertion_sort(L: list, n: int):
    N = len(L)
    for n in range(2, N + 1):
        last = L[n - 1]
        j = n - 1
        while j >= 0 and L[j] > last:
            L[j + 1] = L[j]
            j -= 1
        L[j + 1] = last
