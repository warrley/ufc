name = input()
age = int(input())

if age < 12:
    print(f"{name} eh crianca")
elif age < 18:
    print(f"{name} eh jovem")
elif age < 65:
    print(f"{name} eh adulto")
elif age < 1000:
    print(f"{name} eh idoso")
else:
    print(f"{name} eh mumia")