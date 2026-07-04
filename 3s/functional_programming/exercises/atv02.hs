nome = "Guilherme Warley Brito Farias"
matricula = "582110"


-- atividade 01


-- (1) criar função que dado um número inteiro gere sua fatoração na forma de uma lista de duplas. Por exemplo,


-- 72 = 2^3  .  3^2


-- de onde a lista deve ser,


-- [(2,3),  , (3,2)]


-- construa função com o cabeçalho,

fprimos :: Int -> [(Int, Int)]
fprimos n = zip primeFactors exponents
  where
    primeFactors = [d | d <- [2..n], mod n d == 0, ehPrimo d]
    exponents = [countExp f n | f <- primeFactors]
    countExp f m 
      | mod m f /= 0 = 0
      | otherwise = 1 + countExp f (m `div` f)

    ehPrimo x = [d | d <- [1..x], mod x d == 0] == [1, x]


-- (2) Seja uma strings s da qual se deseja construir a lista das frequências dos CARACTERES. Cada frequência é uma dupla formada pelo caractere e o total de vezes que ele acontece. Por exemplo,


-- s = "aaabb222"


-- deve gerar a lista,


-- [('a',3), ('b',2), ('2',3)] 


-- construa função com o cabeçalho,
freq :: String -> [(Char, Int)]
freq s = zip u (map (\x -> count x s) u)
  where
    u = unique s
    count c s = length [c' | c' <- s, c' == c]
    unique [] = []
    unique (c:rs) = c : unique (filter (/= c) rs)
