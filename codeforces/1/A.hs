
main :: IO()
main = do
  line <- getLine
  let [n, m, a] = map read (words line) :: [Integer]
  print $ (div (n - 1) a + 1) * (div (m - 1) a + 1)