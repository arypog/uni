-- ghc --make Main.hs -outputdir bin -o bin/Main && ./bin/Main

factorial :: Integer -> Integer
factorial 0 = 1
factorial n = n * factorial (n-1)

eX :: Double -> Integer -> Double
eX x n = 1 + sum [ x ^^ k / fromIntegral (factorial k) | k <- [1..n]]

arctan :: Double -> Integer -> Double
arctan z n = z - sum [ x k * z ^^ fromIntegral (2 * k + 1) / fromIntegral (2 * k + 1) | k <- [1..n]]
    where
        x k = if (k `mod` 2 == 0) then -1 else 1

chudnovsky_term :: Integer -> Double
chudnovsky_term k =
        let num = fromIntegral (factorial (4 * k)) * (1103 + 26390 * fromIntegral k)
            den = fromIntegral ((factorial k) ^ 4 * (396 ^ (4 * k)))
        in num / den

chudnovsky_formula :: Integer -> Double
chudnovsky_formula n = 
        (2 * sqrt 2 / 9801) * sum [chudnovsky_term k | k <- [1..fromIntegral n]]

main :: IO()
main = do
    let e_eX = eX 1 50
    let pi_arctan = (arctan 1 1_000_000) * 4
    let pi_convergent = 1 / (chudnovsky_formula 5)
    print (e_eX)
    print (pi_arctan)