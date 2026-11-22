def dfs(graph, init):
    predecessors = {init: None}
    d, f, time = {}, {}, 0
    d[init] = 0
    stack = [(init, iter(graph[init]))]

    while stack:
        u, neighbors = stack[-1]
        try:
            v = next(neighbors)
            if v not in d:
                stack.append((v, iter(graph[v])))
                predecessors[v] = u
                d[v] = time
                time += 1
        except StopIteration:
            stack.pop()
            f[u] = time
            time += 1

    return predecessors, d, f
