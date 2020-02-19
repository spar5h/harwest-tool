
import Data.Char

vowels = "aeiouy"

f1 :: String -> String
f1 [] = []
f1 (x:xs)
  | x `elem` vowels = f1 xs
  | otherwise       = (x:f1 xs)

f2 :: String -> String
f2 [] = []
f2 (x:xs) = ('.':x:f2 xs)

main = do
  a <- getLine
  putStrLn $ f2 $ f1 $ map toLower a