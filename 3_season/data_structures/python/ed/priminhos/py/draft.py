from math import sqrt

def is_prime(num: int, i: int = 2) -> bool | None:
    # print(num, i)
    if (num % i == 0 and num != i) or num == 1: return False
    return True if num == i else is_prime(num, i+1)

def primes(qt: int, count = 0, num: int = 2) -> list[int]:
    # print(qt, count, num)
    num_is_prime = is_prime(num)
    if count == qt: return []
    if num_is_prime: count +=1
    return [num] + primes(qt, count, num+1) if num_is_prime else primes(qt, count, num+1)

def main() -> None:
    num: int = int(input())
    print(primes(num))
    # print(is_prime(num))
if __name__ == "__main__":
    main()