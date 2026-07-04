imc m h
  let r = m/(h*h) in
    | r < 15 = "morreu"
    | r < 25 = "magro"
    | r < 29 = "normal"
    | r < 35 = "gordo"
    | otherwise = "obeso"
      where 
        r = m/(h*h)
