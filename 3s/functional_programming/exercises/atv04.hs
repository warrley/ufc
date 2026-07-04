atividade = "04"
nome = "Guilherme Warley Brito Farias"
matricula = "582110"

-- 1
replace :: [Char] -> [Char] -> [Char] -> [Char]
replace str "" _ = str
replace [] _ _ = []
replace (s:str) from to 
  | take (length from) (s:str) == from = to ++ replace (drop (length from) (s:str)) from to 
  | otherwise = [s] ++ replace str from to


-- 2
lsSplit :: [Int] -> ([Int], Int, [Int])
lsSplit xs = (before xs, m, after xs)
  where 
    m = maximum xs 
    
    before [] = [] 
    before (y:ys)
      | y == m = []
      | otherwise = [y] ++ before ys

    after [] = []
    after (w:ws)
      | w == m = ws
      | otherwise = [w] ++ after ws


-- 3
selectionSort :: [Int] -> [Int]
selectionSort [] = []
selectionSort list = [maximum list] ++ selectionSort (remove (maximum list) list)
  where 
    remove _ [] = []
    remove n (x:xs) 
      | n == x = xs 
      | otherwise = [x] ++ remove n xs 
