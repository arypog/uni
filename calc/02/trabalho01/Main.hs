import Text.Printf
-- ghc --make Main.hs -outputdir bin -o bin/Main && ./bin/Main

delta_x :: Double -> Double -> Int -> Double
delta_x a b n = (b - a) / fromIntegral n

intervals_list :: (Double, Double) -> Int -> [Double]
intervals_list (a, b) n = [dtx * fromIntegral i | i <- [0..n]]
    where
        dtx = delta_x a b n

main :: IO ()
main = do
    let interval = (0, 1)
    let parts = 2

    let intervals = intervals_list interval parts
    let prettyInterval = [printf "%7.2f" x | x <- intervals]
    putStrLn (unwords prettyInterval)

