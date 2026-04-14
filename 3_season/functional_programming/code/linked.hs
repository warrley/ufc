data Linked a = End | Node Float (Linked a) deriving Show

addNode End x = Node x End
addNode lnk x = Node x (lnk)

addNodeList lnk [] = lnk
-- addNodeList lnk [a] = addNode lnk a
addNodeList lnk (a:xs) = addNodeList (addNode lnk a) xs

lnkStr End = "{"
lnkStr (Node x lnk) = (show x) ++ " " ++ lnkStr lnk
