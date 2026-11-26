def max_sum(a: list) -> float:
    maxs = [float("-inf")] * len(a)
    maxs[0] = a[0]

    for i in range(1, len(a)):
        maxs[i] = max(a[i], (a[i] + maxs[i - 1]))

    return max(maxs)
