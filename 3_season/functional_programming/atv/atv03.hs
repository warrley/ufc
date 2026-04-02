
nome = "Guilherme Warley Brito Farias"
matricula = "582110"

-- Fundir dois vetores ordenados num vetor ordenado maior.
-- use casamento de padrões.
-- não use meios externos de ordenação.
-- use recursão.

merge :: (Ord a) => [a] -> [a] -> [a]
merge [] v = v
merge u [] = u
merge (headu:tailu) (headv:tailv)
    | headu <= headv = [headu] ++ merge tailu (headv:tailv)
    | otherwise = [headv] ++ merge (headu:tailu) tailv

-- implemente mergesort para 
-- ordenação do vetor u.
-- Use a função anterior.

mergesort ::  (Ord a) => [a] -> [a]
mergesort [] = []
mergesort [u] = [u]
mergesort u = merge (mergesort left) (mergesort right)
    where 
        (left, right) = splitAt (length u `div` 2) u


-- usando fold implementar função que retorne 
-- a série de Fibonacci com n elementos.

fibo'list :: Int -> [Int]  
fibo'list m
    | m <= 0 = []
    | m == 1 = [0]
    | otherwise = foldl (\acc _ -> acc ++ [last acc + last (init acc)]) [0,1] [2..m-1]