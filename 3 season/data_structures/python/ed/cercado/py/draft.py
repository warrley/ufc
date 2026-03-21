def show(matrix: list[list[str]]) -> None:
    for row in matrix:
        print("".join(row))

def dfs(m: list[list[str]], i: int, j: int) -> None:
    # print(f"i={i} j={j} rows={len(m)} cols={len(m[0])}")
    if i < 0 or i >= len(m) or j < 0 or j >= len(m[0]) or m[i][j] != "O": return

    m[i][j] = "."

    dfs(m, i+1, j)
    dfs(m, i-1, j)
    dfs(m, i, j+1)
    dfs(m, i, j-1)
    # show(m)

def main() -> None:
    m, n = map(int, input().split())
    matrix: list[list[str]] = [list(input()) for _ in range(m)]

    # print(visited)
    # show(matrix)

    for i in range(m):
        for j in range(n):
            if ((i == 0 or i == m-1) or (j == 0 or j == n-1)) and matrix[i][j] == "O": 
                # matrix[i][j] = "p"
                dfs(matrix, i, j)
    
    for i in range(m):
        for j in range(n):
            if matrix[i][j] == "O": 
                matrix[i][j] = "X"
            if matrix[i][j] == ".":
                matrix[i][j] = "O"

    # print("")
    show(matrix)

if __name__ == "__main__":
    main()
