def max_independent(u, childrens) -> int:
    if not childrens[u]:
        return 1

    include_u = 1
    for v in childrens[u]:
        for w in childrens[v]:
            include_u += max_independent(w, childrens)

    exclude_u = 0
    for v in childrens[u]:
        exclude_u += max_independent(v, childrens)

    return max(include_u, exclude_u)


def max_independent_memo(u: object, childrens: dict, memo: dict = {}) -> int:
    if not childrens[u]:
        return 1
    if memo[u]:
        return memo[u]

    include_u = 1
    for v in childrens[u]:
        for w in childrens[v]:
            include_u += max_independent_memo(w, childrens, memo)

    exclude_u = 0
    for v in childrens[u]:
        exclude_u += max_independent_memo(v, childrens, memo)

    memo[u] = max(include_u, exclude_u)
    return memo[u]
