def permutations(
    S: list, r: int, used: list[bool], curr: list = [], res: list = []
) -> list:
    if r == 0:
        res.append(curr)
        return res

    for i in range(len(S)):
        if not used[i]:
            used[i] = True
            permutations(S, r - 1, used, curr + [S[i]], res)
            used[i] = False
    return res
