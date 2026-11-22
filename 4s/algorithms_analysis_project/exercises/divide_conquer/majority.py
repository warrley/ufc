def majority(l: list) -> int | None:
    if len(l) <= 0:
        return None
    if len(l) == 1:
        return l[0]

    mid = len(l) // 2

    left = majority(l[:mid])
    right = majority(l[mid:])

    if left == right:
        return left

    c_left = c_right = 0
    for num in l:
        if num == left:
            c_left += 1
        if num == right:
            c_right += 1

    if c_left > mid:
        return left
    if c_right > mid:
        return right
    return None
