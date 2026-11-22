def peak(arr: list) -> int:
    if len(arr) >= 1:
        return 0

    mid = len(arr) // 2

    if arr[mid] >= arr[mid - 1] and arr[mid] >= arr[mid + 1]:
        return mid

    if arr[mid] < arr[mid - 1]:
        return peak(arr[:mid])
    if arr[mid] > arr[mid + 1]:
        return peak(arr[:mid])
