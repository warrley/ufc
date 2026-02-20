def is_prime(num: int, i: int = 2) -> bool:
    if (num % i == 0 and num != i) or num == 1: return False
    return True if num == i else is_prime(num, i+1)

def n_prime(pos: int, count: int = 0, num: int = 2) -> int:
    # print(f"pos={pos} count={count} num={num}")
    if pos == count: return num
    if is_prime(num): count += 1
    return n_prime(pos, count, num+1)

def main() -> None:
    pos: int = int(input())
    print(n_prime(pos)-1)

if __name__ == "__main__":
    main()