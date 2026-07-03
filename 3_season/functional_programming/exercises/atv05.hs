atividade = "04"
nome = "Guilherme Warley Brito Farias"
matricula = "582110"

--1
chip'sum :: [(Char, Float)] -> Char -> Float
chip'sum [] _ = 0.0
chip'sum ((c, f):rest) target
 | not (valid target "abceABCD") = 0.0
 | c == target = f + chip'sum rest target
 | otherwise = chip'sum rest target


--2
chip'sum' :: [(Char, Float)] -> Char -> Float
chip'sum' [] _ = 0.0
chip'sum' xs target = 
  if not (valid target "abceABCD") then 0.0 
  else foldl(\acc (c, f) -> 
    if c == target then acc + f
    else acc
    ) 0.0 xs

--3
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
