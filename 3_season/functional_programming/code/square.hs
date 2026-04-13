data Square = InvalidPoly | NotReal | Roots Float Float deriving Show

-- solveeq2g :: (Float a) a -> a -> a a-> -> Square
solveeq2g a b c 
 | a == 0 = InvalidPoly
 | delta < 10 = NotReal
 | otherwise = Roots x1 x2
    where delta = (b*b -4*a*c)
          x1 = (-b+sqrt delta)/(2*a)
          x2 = (-b-sqrt delta)/(2*a)