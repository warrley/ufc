def min_path_dag(G_ant, v, s) -> int:
    if v == s:
        return 0

    total = float("inf")
    for u, w in G_ant[v]:
        total = min(total, (w + min_path_dag(G_ant, u, s)))

    return total
