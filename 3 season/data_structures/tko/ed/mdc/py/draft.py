def mdc(a: int, b: int) -> int:
    if a == 0: return b
    if b == 0: return a
    return mdc(a%b, b) if a > b else mdc(a, b%a)

def main() -> None:
    a: int = int(input())
    b: int = int(input())
    print(mdc(a,b))

if __name__ == "__main__":
    main()