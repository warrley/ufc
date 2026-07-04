break' [] _ acc = (acc, [])
break' (c:ls) ch acc
    | c == ch = (acc,ls)
    | otherwise = break' ls ch (acc++[c])
