from collections import deque

def maximumDetonation(bombs: list[list[int]]) -> int:
	can_explode_these: dict[int, list[int]] = {}
	for i in range(len(bombs)): can_explode_these[i] = []	
	# or => can_explode_these = defaultdict(list)
	for i in range(len(bombs)):
		for j in range(len(bombs)):
			if i == j: continue
			x1, y1, r1 = bombs[i]
			x2, y2, _ = bombs[j]

			distance_squared = (x2-x1)**2 + (y2-y1)**2
			if distance_squared <= r1**2: can_explode_these[i].append(j)
	

	def bsf(start: int) -> int:
		q = deque()
		q.append(start)

		visited = set()
		visited.add(start)

		while len(q) > 0:
			current = q.popleft()
			for neig in can_explode_these[current]:
				if neig not in visited:
					visited.add(neig)
					q.append(neig)

		return len(visited)

	max_detons = 0

	for bomb in range(len(can_explode_these)):
		bomb_detons = bsf(bomb)
		max_detons = max(max_detons, bomb_detons)

	return max_detons
	# print(can_explode_these)

# Não modifique a main
def main() -> None:
	n, _ = map(int, input().split())
	bombs = [list(map(int, input().split())) for _ in range(n)]
	print(maximumDetonation(bombs))

if __name__ == "__main__": main()