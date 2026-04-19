-- atividade 5
-- --1.  sejam listas de tuplas formadas cada uma por um Float e um char. A função a seguir deve ler listas desse tipo e retornar para um dado caractere de entrada  a soma de seus floats associados. Os caracteres devem ser um deeses: 'abceABCD. caractees inválidos devem retornar zero. Use casamento de padrão.
-- Aluno: Guilherme Warley Brito Farias 582110

chip'sum :: [(Char, Float)] -> Char -> Float
chip'sum [] _ = 0.0
chip'sum ((c, f):rest) target
 | not (valid target "abceABCD") = 0.0
 | c == target = f + chip'sum rest target
 | otherwise = chip'sum rest target


chip'sum' :: [(Char, Float)] -> Char -> Float
chip'sum' [] _ = 0.0
chip'sum' xs target = 
  if not (valid target "abceABCD") then 0.0 
  else foldl(\acc (c, f) -> 
    if c == target then acc + f
    else acc
    ) 0.0 xs

words' :: String -> [String]
words' [] = []
words' (x:xs)
  | x == ' '  = words' xs
  | otherwise =
      let word = x : takeWhile (/= ' ') xs
          rest = dropWhile (/= ' ') xs
      in word : words' rest

valid :: Char -> [Char] -> Bool
valid _ [] = False
valid c (x:xs) 
  | c == x = True
  | otherwise = valid c xs
