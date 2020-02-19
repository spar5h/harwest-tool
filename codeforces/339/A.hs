
f1 :: String -> String
f1 [] = []
f1 (x:xs) = ('+':x:f1 xs)

f2 :: String -> String
f2 [] = []
f2 (x:xs) 
  | x == '+'  = f2 xs
  | otherwise = (x:f2 xs)

msort' :: String -> String
msort' [] = []
msort' [x] = [x]
msort' x = merge' left right
  where left  = msort' $ take m x
        right = msort' $ drop m x
        m = div (length x + 1) 2

merge' :: String -> String -> String
merge' [] [] = []
merge' (x:xs) [] = (x:merge' xs [])
merge' [] (y:ys) = (y:merge' ys [])
merge' (x:xs) (y:ys) 
  | x <= y    = (x:merge' xs (y:ys))
  | otherwise = (y:merge' (x:xs) ys) 

main = do
  a <- getLine
  putStrLn $ tail $ f1 $ msort' $ f2 a