-- Universidade Federal do Ceará
-- Campus de Quixadá
-- Programaçãop Funcional
-- Atividade 01
-- Professor Ricardo Reis

-- identificação
--nome = "Guilherme Warley Brito Farias"
--matricula = "582110"

digs :: Int -> [Int]
digs n = if n < 10 then [n] else digs (div n 10) ++ [mod n 10]

sabs :: [Int] -> Int
sabs [] = 0
sabs (x:rest) = abs x + sabs rest

maximum' :: [Int] -> Int
maximum' [x] = abs x
maximum' (x:rest) = if (abs x) > maxRest then abs x else maxRest
    where maxRest = maximum' rest

freq :: String -> Char -> Int
freq [] _ = 0 
freq (x:rest) ch = if ch == x then 1 + freq rest ch else freq rest ch