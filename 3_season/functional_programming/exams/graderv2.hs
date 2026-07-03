testar :: (Eq a, Show a) => String -> a -> a -> String
testar nome esperado obtido =
    if esperado == obtido
    then "OK"
    else "Falhou em [" ++ nome ++ "] -> Esperava: " ++ show esperado ++ ", mas obteve: " ++ show obtido

runTests :: IO ()
runTests = do
    putStrLn "\n========================================"
    putStrLn "   CORRECAO DA SEGUNDA CHAMADA"
    putStrLn "========================================"
    
    rodarQuestao "Q1 (soma)" q1_tests
    rodarQuestao "Q2 (ultimo)" q2_tests
    rodarQuestao "Q3 (reduzir)" q3_tests
    rodarQuestao "Q4 (maiuscula)" q4_tests
    rodarQuestao "Q5 (capitalizar)" q5_tests
    
    putStrLn "========================================\n"

  where
    -- Testes Q1: soma
    q1_tests = [ testar "soma 99" 18 (soma 99)
               , testar "soma 829" 19 (soma 829)
               , testar "soma 3586" 22 (soma 3586)
               , testar "soma 0" 0 (soma 0)
               , testar "soma 5" 5 (soma 5)
               , testar "soma 123456789" 45 (soma 123456789)
               , testar "soma 10" 1 (soma 10)
               , testar "soma 100000" 1 (soma 100000)
               , testar "soma 999" 27 (soma 999)
               , testar "soma 9876" 30 (soma 9876)
               ]

    -- Testes Q2: ultimo
    q2_tests = [ testar "ultimo 99" 9 (último 99)
               , testar "ultimo 829" 1 (último 829)
               , testar "ultimo 3586" 4 (último 3586)
               , testar "ultimo 0" 0 (último 0)
               , testar "ultimo 5" 5 (último 5)
               , testar "ultimo 123456789" 9 (último 123456789)
               , testar "ultimo 10" 1 (último 10)
               , testar "ultimo 100000" 1 (último 100000)
               , testar "ultimo 999" 9 (último 999)
               , testar "ultimo 9876" 3 (último 9876)
               ]


    -- Testes Q3: reduzir
    q3_tests = [ testar "Exemplo professor" [(0,0),(1,2),(2,0),(3,1),(4,0),(5,2),(6,2),(7,2),(8,0),(9,3)] (reduzir [1,1,5,9,9,3,9,7,5,7,6,6])
               , testar "Lista vazia" (zip [0..9] (repeat 0)) (reduzir [])
               , testar "Apenas zeros" (zip [0..9] [1,0,0,0,0,0,0,0,0,0]) (reduzir [0])
               , testar "Um de cada" (zip [0..9] (repeat 1)) (reduzir [1,2,3,4,5,6,7,8,9,0])
               , testar "Apenas noves" (zip [0..9] [0,0,0,0,0,0,0,0,0,4]) (reduzir [9,9,9,9])
               , testar "Apenas cincos" (zip [0..9] [0,0,0,0,0,2,0,0,0,0]) (reduzir [5,5])
               , testar "Binario misto" (zip [0..9] [1,2,0,0,0,0,0,0,0,0]) (reduzir [1,0,1])
               , testar "Oitos e dois" (zip [0..9] [0,0,2,0,0,0,0,0,2,0]) (reduzir [8,2,8,2])
               , testar "Dez treses" (zip [0..9] [0,0,0,10,0,0,0,0,0,0]) (reduzir [3,3,3,3,3,3,3,3,3,3])
               , testar "Setes, uns e dois" (zip [0..9] [0,3,1,0,0,0,0,3,0,0]) (reduzir [7,7,7,1,1,1,2])
               ]

    -- Testes Q4: maiuscula
    q4_tests = [ testar "Letra 'a'" 'A' (maiúscula 'a')
               , testar "Letra 'z'" 'Z' (maiúscula 'z')
               , testar "Letra 'm'" 'M' (maiúscula 'm')
               , testar "Ja maiuscula 'A'" 'A' (maiúscula 'A')
               , testar "Ja maiuscula 'Z'" 'Z' (maiúscula 'Z')
               , testar "Numero '5'" '5' (maiúscula '5')
               , testar "Espaco ' '" ' ' (maiúscula ' ')
               , testar "Simbolo '@'" '@' (maiúscula '@')
               , testar "Caractere especial '#'" '#' (maiúscula '#')
               , testar "Letra 'q'" 'Q' (maiúscula 'q')
               ]

    -- Testes Q5: capitalizar
    q5_tests = [ testar "Exemplo professor" "A Casa Caiu" (capitalizar "a casa caiu")
               , testar "Palavra unica" "Haskell" (capitalizar "haskell")
               , testar "String vazia" "" (capitalizar "")
               , testar "Duas palavras" "Hello World" (capitalizar "hello world")
               , testar "Com numeros" "123 Test" (capitalizar "123 test")
               , testar "Letra unica" "A" (capitalizar "a")
               , testar "Duas letras" "A B" (capitalizar "a b")
               , testar "Ja capitalizado" "A B C" (capitalizar "A B C")
               , testar "Misto" "Mixed Case HERE" (capitalizar "mixed case HERE")
               , testar "Varias palavras" "One Two Three Four" (capitalizar "one two three four")
               ]

rodarQuestao :: String -> [String] -> IO ()
rodarQuestao nomeQuestao resultados = do
    putStrLn ("\n--- " ++ nomeQuestao ++ " ---")
    
    if acertos == total
       then putStrLn ("[ OK ] Passou em " ++ show total ++ "/" ++ show total ++ " testes.")
       else do
           putStrLn ("[FALHOU] Passou em " ++ show acertos ++ "/" ++ show total ++ " testes.")
           putStrLn "Detalhes dos erros:"
           imprimirErros resultados
  where
    total = length resultados
    acertos = length (filter (== "OK") resultados)

imprimirErros :: [String] -> IO ()
imprimirErros [] = return ()
imprimirErros (x:xs) = do
    if x /= "OK"
       then putStrLn ("  " ++ x)
       else return ()
    imprimirErros xs
