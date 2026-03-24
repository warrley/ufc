quicksort ls = 
    if length ls < 2
        then ls
    else 
        x ++ [m] ++ y
        where
            m = ls !! 0
            rest = drop 1 ls
            x = quicksort [a | a <- rest, a <= m]
            y = quicksort [a | a <- rest, a > m]
-- quicksort ls = 
--     if length ls < 2
--         then ls
--     else 
--         x ++ [m] ++ y
--         where
--             m = ls !! 0
--             rest = drop 1 ls
--             x = quicksort (filter (< m) rest)
--             y = quicksort (filter (>= m) rest)