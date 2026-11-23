def subsets(S: list, i: int, r: int, curr: list = [], res: list = []) -> list:
    if r == 0:
        res.append(curr)
        return res
    if (len(S) - i) < r:
        return res

    subsets(S, i + 1, r, curr + [S[i]], res)
    subsets(S, i + 1, r - 1, curr, res)

    return res
