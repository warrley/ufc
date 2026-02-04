h, p, f, d = map(int, input().split())

if d == 1:
    dh = (h - f) % 16
    dp = (p - f) % 16
elif d == -1:
    dh = (f - h) % 16
    dp = (f - p) % 16

if dp < dh:
    print("N")
else:
    print("S")