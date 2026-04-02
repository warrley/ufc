merge :: (Ord a) => [a] -> [a] -> [a]
merge [] v = v
merge u [] = u
merge (headu:tailu) (headv:tailv)
    | headu <= headv = [headu] ++ merge tailu (headv:tailv)
    | otherwise = [headv] ++ merge (headu:tailu) tailv

mergesort ::  (Ord a) => [a] -> [a]
mergesort [] = []
mergesort [u] = [u]
mergesort u = merge (mergesort left) (mergesort right)
    where 
        (left, right) = splitAt (length u `div` 2) u