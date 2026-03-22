from typing import List

def dfs(m: List[List[str]], i: int, j: int) -> None:
	if i < 0 or j < 0 or i >= len(m) or j >= len(m[0]) or m[i][j] != "X": return 

	m[i][j] = "x"
	dfs(m, i+1, j)
	dfs(m, i-1, j)
	dfs(m, i, j+1)
	dfs(m, i, j-1)

def countBattleships(board: List[List[str]]) -> int:
	count: int = 0
	for i in range(len(board)):
		for j in range(len(board[0])):
			if board[i][j] == "X":
				dfs(board, i, j)
				count += 1

	return count
# Não modifique a main
def main() -> None:
	nl, nc = map(int, input().split())
	board: List[List[str]] = [list(input()) for _ in range(nl)]
	result: int = countBattleships(board)
	print(result)

if __name__ == "__main__": main()
