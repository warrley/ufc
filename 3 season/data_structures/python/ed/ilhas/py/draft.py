from typing import List

def show(m):
    for row in m:
        print(*row)

def search(m: List[List[str]], i: int, j: int) -> None:
    if i < 0 or i >= len(m) or j < 0 or j >= len(m[0]) or m[i][j] == "0" or m[i][j] == "x": return 
        
    m[i][j] = "x"
    # show(m)
    # print("")
    search(m, i+1, j)
    search(m, i-1, j)
    search(m, i, j-1)
    search(m, i, j+1)

def main() -> None: 
    rows, columns = map(int, input().split())
    matrix: List[List[str]] = [list(input()) for _ in range(rows)]

    count: int = 0
    for i in range(rows):
        for j in range(columns):
            if matrix[i][j] == "1":
                search(matrix, i, j)
                count += 1

    
    print(count)

if __name__ == "__main__":
    main()