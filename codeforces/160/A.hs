
f1 :: [Int] -> Int
f1 a = 
  let t = div (sum a) 2 + 1
  in 1 + (length $ filter (\x -> x < t) $ scanl1 (+) $ msort' a)


msort' :: [Int] -> [Int]
msort' [] = []
msort' [x] = [x]
msort' x = merge' left right
  where left  = msort' $ take m x
        right = msort' $ drop m x
        m = div (length x + 1) 2

merge' :: [Int] -> [Int] -> [Int]
merge' [] [] = []
merge' (x:xs) [] = (x:merge' xs [])
merge' [] (y:ys) = (y:merge' ys [])
merge' (x:xs) (y:ys) 
  | x >= y    = (x:merge' xs (y:ys))
  | otherwise = (y:merge' (x:xs) ys) 

main = do
  _ <- getLine
  a <- readIntList
  putStrLn $ show $ f1 a

-- [Int] from line
readIntList :: IO [Int]
readIntList = do
  line <- getLine
  return $ map read $ words line