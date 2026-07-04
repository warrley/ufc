-- identificação
nome = "Guilherme Warley Brito Farias"
matrícula = "582110"


--1
-- Implementar função que calcule a soma dos dígitos de um inteiro não negativo


soma :: (Integral a) => a -> a
soma n = foldl (\acc x -> acc + x) 0 (digs n)

digs n = if n < 10 then [n] else digs (div n 10) ++ [mod n 10]


-- 2
-- seja a série S(x de inteiros ) que inicia no inteiro não nulo x e os demais são formados   pela soma dos dígitos do termo anterior. Exemplos,


-- S(99) = 99 > 18 > 9
-- S(829) = 829 > 19 > 10 > 1
-- S(3586) = 3586 > 22 > 4
-- S(0) = 0 > 0


-- Tais séries sempre convergem para um dígito. implementar função que dado x retorne o último dígito de S(x).

último :: (Integral a) => a -> a
último x
    | x < 10    = x
    | otherwise = último (soma x)

-- 3
-- Seja D uma lista F cujos elementos são dígitos de 0 a 9. Reduza D de modo que F seja constituída  por tuplas (d,f) onde d é o dígito e f quantas vezes o dígito se repete em D. exemplo,


-- [1,1,5,9,9,3,9,7,5,7,6,


-- gera,


-- [(0,0),(1,2),(2,0),(3,1),(4,0),(5,2),(6,2),(7,2),(8,0),(9,3)]


-- Implemente função que receba uma lista e gere sua resução conforme ilustrado.


reduzir :: (Integral a) => [a] -> [(a, a)]
reduzir s = zip [0..9] (map (\x -> count x s) [0..9])
  where
    count c xs = fromIntegral (length [x | x <- xs, x == c])


-- 4
-- Implementar função que retorne a versão maiúscula de um caractere utf-8
maiúscula :: Char -> Char
maiúscula c 
  | isValid c ['a'..'z'] == False = c
  | otherwise = head (drop (26 - (howMany c)) ['A'..'Z'])

isValid _ [] = False
isValid x (l:ls) 
  | x == l = True
  | otherwise = isValid x ls

howMany l = length [l..'z']

-- 5
-- Implementar função que receba uma string e retorne sua versão capitalizada, ou seja, cada palavra é repassada para a saída apenas com a primeira letra em maiúsculo. Exemplo,
-- "a casa caiu" > "A Casa Caiu"

capitalizar :: String -> String
capitalizar s = unwords (map capWord (words s))

capWord [] = ""
capWord (x:xs) = [maiúscula x] ++ xs
