data Semana = Domingo | Segunda | Terca | Quarta | Quinta | Sexta | Sabado


whatsday :: Semana -> Int
whatsday Domingo = 0
whatsday Segunda = 1
whatsday Terca = 2
whatsday Quarta = 3
whatsday Quinta = 4
whatsday Sexta = 5
whatsday Sabado = 6

whatsday _ = -1
