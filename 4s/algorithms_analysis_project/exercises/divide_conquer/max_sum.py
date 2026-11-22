def max_sum_cross(left: list, right: list) -> int:
    sum = 0
    s_left = float("-inf")
    for num in reversed(left):
        sum += num
        s_left = max(s_left, sum)

    sum = 0
    s_right = float("-inf")
    for num in right:
        sum += num
        s_right = max(s_right, sum)

    return s_left + s_right


def max_sum(arr: list) -> int:
    if len(arr) <= 1:
        return arr[0]
    mid = len(arr) // 2
    m_left, m_right, m_cross = (
        max_sum(arr[:mid]),
        max_sum(arr[mid:]),
        max_sum_cross(arr[:mid], arr[mid:]),
    )

    return max(m_left, m_right, m_cross)
