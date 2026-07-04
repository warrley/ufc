def prime(num: int, i: int = 2) -> bool | None:
    # print(num, i)
    if (num % i == 0 and num != i) or num == 1: return False
    return True if num == i else prime(num, i+1)

def main() -> None:
   num: int = int(input())
   res = prime(num)
   print("false" if not res else "true")

if __name__ == "__main__":
    main()