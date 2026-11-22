def inv(l: list) -> tuple[list[int], int]:
    if len(l) <= 1:
        return l, 0

    mid = len(l) // 2
    left, c_left = inv(l[:mid])
    right, c_right = inv(l[mid:])

    i = j = c_cross = 0
    sorted = []
    while i < len(left) and j < len(right):
        if left[i] < right[j]:
            sorted.append(left[i])
            i += 1
        else:
            sorted.append(right[j])
            c_cross += len(left) - i
            j += 1
    sorted.extend(left[i:])
    sorted.extend(right[j:])

    return sorted, (c_left + c_right + c_cross)
