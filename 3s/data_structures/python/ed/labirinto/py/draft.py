def show(matrix: list[list[str]]) -> None:
    for row in matrix:
        print("".join(row))

def dfs(m: list[list[str]], i: int, j: int, end: tuple[int, int]) -> bool:
    if i < 0 or j < 0 or i >= len(m) or j >= len(m[0]) or m[i][j] != " ": return False
    if i == end[0] and j == end[1]: m[i][j] = "."; return True
    
    m[i][j] = "."
    # show(m)
    if dfs(m, i-1, j, end): return True
    if dfs(m, i+1, j, end): return True
    if dfs(m, i, j-1, end): return True
    if dfs(m, i, j+1, end): return True

    m[i][j] = "x"
    return False

def main() -> None:
    rows, cols = map(int, input().split())
    matrix: list[list[str]] = [list(input()) for _ in range(rows)]

    start: tuple[int, int] = ()
    end: tuple[int, int] = ()

    for i in range(rows):
        for j in range(cols):
            if matrix[i][j] == "I": start = (i, j); matrix[i][j] = " "
            if matrix[i][j] == "F": end = (i, j); matrix[i][j] = " "
    
    dfs(matrix, start[0], start[1], end)
    
    for i in range(rows):
        for j in range(cols):
            if matrix[i][j] == "x": matrix[i][j] = " "
    
    show(matrix)

if __name__ == "__main__":
    main()