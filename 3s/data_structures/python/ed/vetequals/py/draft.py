def equals(v1: list[int], v2: list[int], i: int = 0) -> bool:
    try: 
        # print(f"i={i} v1[i]={v1[i]} v2[i]={v2[i]}")
        if v1[i] != v2[i]: return False 
        return equals(v1, v2, i+1)
    except IndexError: 
        try:
            v1[i]
            return False
        except IndexError:
            try:
                v2[i]
                return False
            except IndexError:
                return True


def main() -> None:
    v1: list[int] = list(map(int, input().strip("[  ]").split()))
    v2: list[int] = list(map(int, input().strip("[  ]").split()))
    print("iguais" if equals(v1, v2) else "diferentes")

if __name__ == "__main__":
    main()