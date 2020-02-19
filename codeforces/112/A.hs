
import Data.Char

f1 :: String -> String -> Int
f1 [] [] = 0
f1 (x:_) [] = 1
f1 [] (x:_) = -1
f1 (x:xs) (y:ys)
  | x > y     = 1
  | x < y     = -1
  | otherwise = f1 xs ys

main = do
  s1 <- getLine
  s2 <- getLine
  putStrLn $ show $ f1 (map toLower s1) (map toLower s2)