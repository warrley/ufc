fibonacci :: Integer -> Integer
fibonacci 0 = 1
fibonacci 1 = 1
fibonacci n = fibonacci(n-1) + fibonacci(n-2)

fibonacciList :: Integer -> [Integer]
-- fibonacciList n = map fibonacci [0 .. n-1] or
fibonacciList n = [fibonacci x | x <- [0 .. n-1]]