def delta_x(a, b, n):
    return (b - a) / n


def list_of_intervals(a, b, n):
    dx = delta_x(a, b, n)

    return [a + i*dx for i in range(n + 1)]


def print_intervals(intervals):
    line = ''.join(f"{num:7.2f}" for num in intervals)
    print(''.join(line))


def main():
    a, b, n = map(int, input("Digite o intervalo e quantas partes a dividir (a b n): ").split())  

    intervals = list_of_intervals(a, b, n)
    print_intervals(intervals)

main()    