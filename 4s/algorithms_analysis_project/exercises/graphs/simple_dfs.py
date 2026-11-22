def dfs(graph, init):
    stack = [(init, iter(graph[init]))]
    visited = {init}

    while stack:
        u, neighbors = stack[-1]
        try:
            v = next(neighbors)
            if v not in visited:
                stack.append((v, iter(graph[v])))
                visited.add(v)
        except StopIteration:
            stack.pop()

    return visited
