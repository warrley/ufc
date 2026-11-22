def dfs(graph, init):
    queue = deque([init])
    visited = {init}

    while queue:
        current = queue.popleft()
        for u in graph[current].keys():
            if u not in visited:
                visited.add(u)
                queue.append(u)

    return visited
