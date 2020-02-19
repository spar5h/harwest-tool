
readInt :: IO Int
readInt = do
  line <- getLine
  return $ (map read $ words line) !! 0

readIntList :: IO [Int]
readIntList = do
  line <- getLine
  return $ map read $ words line

readStrList :: IO [String]
readStrList = do
  line <- getLine
  return $ words line

repReadInt :: Int -> IO [Int]
repReadInt 0 = return []
repReadInt q = do
  x <- readInt
  xs <- repReadInt (q - 1)
  return (x:xs)

printStrList :: [String] -> IO()
printStrList [] = return ()
printStrList (x:xs) = do
  putStrLn x
  printStrList xs

main = do
  [n, m] <- readIntList
  s <- readStrList
  t <- readStrList
  [q] <- readIntList
  query <- repReadInt q
  printStrList $ map (\x -> (s !! (mod x n)) ++ (t !! (mod x m))) 
                $ map (\x -> x - 1) query
  