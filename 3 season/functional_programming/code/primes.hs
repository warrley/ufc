
prime :: Integer -> Bool
prime n 
    | n <= 1 = False
    | otherwise = all (\x -> n `mod` x /= 0) [2 .. n-1] -- all apply the condition each element in the list

-- prime num = checkPrime num 2
--     where 
--         checkPrime n i 
--             | n == 1 || (n /= i && n `mod` i == 0) = False
--             | n == i = True
--             | otherwise = checkPrime n (i+1)

primeList :: Int -> [Integer]
-- primeList n = [x | x <- [1 .. n-1], prime x] -- above n prime
primeList n = take n [x | x <- [2..], prime x]

infinitPrimeList = [x | x <- [2..], prime x]