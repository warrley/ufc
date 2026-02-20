def break_num(num: int) -> None:
    # quotient: int = num//2
    # rest: int = num%2
    if num == 0: return
    break_num(num//2)
    print(num//2, num%2)

def main() -> None:
    num: int = int(input())
    break_num(num)

if __name__ == "__main__":
    main()