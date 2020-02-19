
f :: String -> String
f x 
  | l <= 10 = x
  | otherwise = [head x] ++ (show (l - 2)) ++ [x !! (l - 1)]
  where l = length x

main = do
  n <- readInt
  a <- readStrN n
  printStrList $ map f a

-- Int from line
readInt :: IO Int
readInt = do
  line <- getLine
  return $ read line

-- Int from N lines
readStrN :: Int -> IO [String]
readStrN 0 = return []
readStrN n = do
  x  <- getLine
  xs <- readStrN (n - 1)
  return (x:xs)

printStrList :: [String] -> IO()
printStrList [] = return ()
printStrList (x:xs) = do
  putStrLn x
  printStrList xs