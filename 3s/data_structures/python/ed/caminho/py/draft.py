from collections import deque

def show(grid: list[list[str]]) -> None:
    for row in grid:
        print("".join(row))

def draw_path(grid: list[list[str]], start: tuple[int, int], path: dict[tuple[int, int], tuple[int, int]], current: tuple[int, int]) -> None:
    grid[current[0]][current[1]] = "."
    if current == start: return

    draw_path(grid, start, path, path[current])

def shortest(grid: list[list[str]], start, end):
    d: deque = deque()
    d.append(start)

    visited: dict[tuple[int, int], bool] = {}
    visited[start] = True

    path: dict[tuple[int, int], tuple[int, int]] = {}

    while d:
        cur: tuple[int, int] = d.popleft()
        if cur == end: break
        directions = [(0,1),(1,0),(0,-1),(-1,0)]
        for i, j in directions:
            row = i + cur[0]
            col = j + cur[1]
            if grid[row][col] != "#" and (row, col) not in visited:
                visited[(row, col)] = True
                d.append((row, col))
                path[(row, col)] = cur
    # for current, previous in path.items():
    #     print(f"c={current}, p={previous}", end=" |")
    # print(start, end)
    draw_path(grid, start, path, end)

def main() -> None:
    m, n = map(int, input().split())
    grid: list[list[str]] = [list(input()) for _ in range(m)]

    start: tuple[int, int] = ()
    end: tuple[int, int] = ()

    for i in range(m):
        for j in range(n): 
            if grid[i][j] == "I": start = (i, j); grid[i][j] = " "
            if grid[i][j] == "F": end = (i, j); grid[i][j] = " "

    shortest(grid, start, end)
    show(grid)

if __name__ == "__main__": main()