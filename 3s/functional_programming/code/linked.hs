data Linked a = End | Node a (Linked a)

addNode End x = Node x End
addNode lnk x = Node x (lnk)

addNodeList lnk [] = lnk
addNodeList lnk (a:xs) = addNodeList (addNode lnk a) xs

toStr End = ""
toStr (Node x lnk) = show x ++ " " ++ toStr lnk

instance Show a => Show (Linked a) where
  show lnk = "{ " ++ toStr lnk ++ "}"
