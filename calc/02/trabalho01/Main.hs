import Text.Printf
-- ghc --make Main.hs -outputdir bin -o bin/Main && ./bin/Main

delta_x :: Double -> Double -> Double -> Double
delta_x a b n = (b - a) / n

intervals_list :: (Double, Double) -> Double -> [Double]
intervals_list (a, b) n = [a + i * dx | i <- [0..n]]
    where
        dx = delta_x a b n

main :: IO ()
main = do
    putStrLn "Enter a, b and n (space-separated):"
    input <- getLine
    let [a, b, n] = map read (words input) :: [Double]

    let intervals = intervals_list (a, b) n
    let prettyInterval = [printf "%7.2f" x | x <- intervals]
    putStrLn (unwords prettyInterval)

