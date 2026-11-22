def merge_sort(l: list) -> list:
    if len(l) <= 1:
        return l

    mid = len(l) // 2

    left = merge_sort(l[:mid])
    right = merge_sort(l[mid:])

    i = j = 0
    sorted = []
    while i < len(left) and j < len(right):
        if left[i] < right[j]:
            sorted.append(left[i])
            i += 1
        else:
            sorted.append(right[j])
            j += 1
    sorted.extend(left[i:])
    sorted.extend(right[j:])

    return sorted
