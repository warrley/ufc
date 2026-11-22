def duplicatas(l: list, n: int) -> list:
    if n == 0:
        return []
    if n == 1:
        return [l[0]]

    last = l[n - 1]
    nl = duplicatas(l, n - 1)

    if nl[n - 2] != last:
        nl.append(last)

    # if nl[-1] != l[n-1]:
    #     nl.append(l[n-1])

    return nl
