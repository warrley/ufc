from collections import deque

def show(g: list[list[int]]) -> None:
	for row in g:
		print(*row)

def orangesRotting(grid: list[list[int]]) -> int:
	d: deque = deque()
	time, freshs = 0, 0

	for i in range(len(grid)):
		for j in range(len(grid[0])):
			if grid[i][j] == 1: freshs += 1
			if grid[i][j] == 2: d.append((i, j))
		
	while len(d) > 0 and freshs > 0:
		# print(f"\nt={time}")
		# show(grid)
		# print(d)
		time += 1
		for _ in range(len(d)):
			i, j = d.popleft()
			# print(freshs, time, d, i, j)
			if i+1 < len(grid) and grid[i+1][j] == 1:
				grid[i+1][j] = 2
				freshs -= 1
				d.append((i+1, j))
			if i-1 >= 0 and grid[i-1][j] == 1:
				grid[i-1][j] = 2
				freshs -= 1
				d.append((i-1, j))
			if j+1 < len(grid[0]) and grid[i][j+1] == 1:
				grid[i][j+1] = 2
				freshs -= 1
				d.append((i, j+1))
			if j-1 >= 0 and grid[i][j-1] == 1:
				grid[i][j-1] = 2
				freshs -= 1
				d.append((i, j-1))

			# # with loop in directions 
			# directions = [(0,1), (1,0), (0,-1), (-1,0)]
			# for r, c in directions:
			# 	row = r+i
			# 	col = c+j
			# 	if row >= 0 and col >= 0 and row < len(grid) and col < len(grid[0]) and grid[row][col] == 1:
			# 		grid[row][col] = 2
			# 		freshs -= 1
			# 		d.append((row, col))
	return time if freshs == 0 else -1

# Não modifique a main
def main() -> None:
	nl, nc = map(int, input().split())
	grid = [list(map(int, input().split())) for _ in range(nl)]
	print(orangesRotting(grid))

if __name__ == "__main__": main()