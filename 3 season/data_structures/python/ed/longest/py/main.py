def dfs(m: list[list[int]], lens: list[list[int]], prev: int, i: int, j: int) -> int:
	if i < 0 or j < 0 or i >= len(m) or j >= len(m[0]) or m[i][j] <= prev: return 0
	if lens[i][j] != 0: return lens[i][j]

	d = dfs(m, lens, m[i][j], i+1, j)
	u = dfs(m, lens, m[i][j], i-1, j)
	l = dfs(m, lens, m[i][j], i, j-1)
	r = dfs(m, lens, m[i][j], i, j+1)

	max_len: int = 1 + max(d, u, l, r)
	lens[i][j] = max_len
	return max_len


def longestIncreasingPath(matrix: list[list[int]]) -> int: 
	lens: list[list[int]] = [[0] * len(matrix[0]) for _ in range(len(matrix))]
	max_len: int = 0
	for i in range(len(matrix)):
		for j in range(len(matrix[0])):
			max_len = max(max_len, dfs(matrix, lens, -1, i, j))
	
	# print(lens)
	return max_len

# Não modifique a main
def main() -> None:
		
	nl, nc = map(int, input().split())
	matrix = [list(map(int, input().split())) for _ in range(nl)]
	result = longestIncreasingPath(matrix)
	print(result)

if __name__ == "__main__": main()