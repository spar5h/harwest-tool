
count' :: (Eq a) => [a] -> a -> Int
count' [] _ = 0
count' (x:xs) k
  | (x == k) = 1 + count' xs k
  | otherwise = count' xs k

main :: IO()
main = do
  _ <- getLine
  line <- getLine
  print $ count' line 'L' + count' line 'R' + 1