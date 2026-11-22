def dfs(graph, init):
    queue = deque([init])
    distances = {init: 0}
    predecessors = {init: None}

    while queue:
        current = queue.popleft()
        for neighbor in graph[current].keys():
            if neighbor not in distances:
                distances[neighbor] = distances[current] + 1
                predecessors[neighbor] = current
                queue.append(neighbor)

    return distances, predecessors
