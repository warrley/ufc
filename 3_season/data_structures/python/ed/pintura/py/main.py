from typing import List
def show(m: list[list[str]]):
	for row in m:
		print(*row)

def dfs(m: list[list[str]], i: int, j: int, bc: str, ac: str) -> None:
	if i < 0 or j < 0 or i >= len(m) or j >= len(m[0]) or m[i][j] != bc: return

	m[i][j] = ac
	# print("")
	# show(m)
	dfs(m, i+1, j, bc, ac)
	dfs(m, i-1, j, bc, ac)
	dfs(m, i, j+1, bc, ac)
	dfs(m, i, j-1, bc, ac)


def floodFill(image: List[List[int]], sr: int, sc: int, color: int) -> List[List[int]]:
	if color == image[sr][sc]: return image

	dfs(image, sr, sc, image[sr][sc], color)

	return image

# Não modifique a main
def main() -> None:
	nl, nc = map(int, input().split())
	image = [list(map(int, input().split())) for _ in range(nl)]
	sr, sc, color = map(int, input().split())
	result = floodFill(image, sr, sc, color)

	show(result)

## ALTERANDO O PROPRIO VETOR, SEM RETORNAR 

# def dfs(m: list[list[str]], i: int, j: int, bc: str, ac: str) -> None:
# 	if i < 0 or j < 0 or i >= len(m) or j >= len(m[0]) or m[i][j] != bc: return

# 	m[i][j] = ac
# 	# print("")
# 	# show(m)
# 	dfs(m, i+1, j, bc, ac)
# 	dfs(m, i-1, j, bc, ac)
# 	dfs(m, i, j+1, bc, ac)
# 	dfs(m, i, j-1, bc, ac)

# def main() -> None:
# 	nl, nc = map(int, input().split())
# 	image = [list(input().split()) for _ in range(nl)]
# 	sr, sc, color = map(int, input().split())

# 	if str(color) != image[sr][sc]: dfs(image, sr, sc, str(image[sr][sc]), str(color))
# 	show(image)


if __name__ == "__main__":
    main()
