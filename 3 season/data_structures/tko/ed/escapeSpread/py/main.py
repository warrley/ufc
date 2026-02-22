from collections import deque

def show(grid: list[list[int]]) -> None:
	for row in grid:
		print(*row)

# mapeia quanto tempo demora para o fogo chegar naquele espaco
def get_fire_time(grid: list[list[int]]) -> list[list[int]]:
	fire_time: list[list[int]] = [[float("inf")] * len(grid[0]) for _ in range(len(grid))]
	q: deque[int, int] = deque()
	
	for i in range(len(grid)):
		for j in range(len(grid[0])):
			if grid[i][j] == 1:
				fire_time[i][j] = 0
				q.append((i, j))

	while len(q) > 0:
		r, c = q.popleft()
		for dr, dc in [(0,1), (1,0), (-1,0), (0,-1)]:
			nr, nc = r+dr, c+dc
			if 0 <= nr < len(grid) and 0 <= nc < len(grid[0]) and grid[nr][nc] == 0:
				if fire_time[nr][nc] == float("inf"):
					fire_time[nr][nc] = fire_time[r][c]+1
					q.append((nr, nc))
	return fire_time

# mostra qual o caminho e possivel chegar
def can_escape(grid: list[list[int]], fire_time: list[list[int]], wait_time: int) -> bool:
	if wait_time >= fire_time[0][0]: return False

	q = deque()
	q.append((0, 0, wait_time))

	visited = set()
	visited.add((0,0))

	while len(q) > 0:
		r, c, t = q.popleft()
		if r == len(grid) -1 and c == len(grid[0])-1: return True

		for dr, dc in [(0,1), (1,0), (-1,0), (0,-1)]:
			nr, nc, nt = r+dr, c+dc, t+1
			if 0 <= nr < len(grid) and 0 <= nc < len(grid[0]) and grid[nr][nc] == 0 and (nr, nc) not in visited:
				# pode chegar no abrigo ao mesmo tempo do fogo
				if nr == len(grid)-1 and nc == len(grid[0])-1: 
					# < pq pode ser que seja infinito
					if nt <= fire_time[nr][nc]: return True
				elif nt < fire_time[nr][nc]:
					visited.add((nr, nc))
					q.append((nr, nc, nt))
	
	return False


def maximumMinutes(grid: list[list[int]]) -> int:
	fire_time: list[list[int]] = get_fire_time(grid)
	# print("")
	# show(grid)
	# show(fire_time)
	low, high, best_wait = 0, 10**9, -1
	# busca binaria
	while low <= high:
		mid = (low+high) // 2
		if can_escape(grid, fire_time, mid):
			best_wait = mid
			low = mid + 1
		else:
			high = mid - 1
		
	return best_wait

# Não modifique a main
def main() -> None:
	nl, _ = map(int, input().split())
	grid = [list(map(int, input().split())) for _ in range(nl)]
	print(maximumMinutes(grid))

if __name__ == "__main__": main()