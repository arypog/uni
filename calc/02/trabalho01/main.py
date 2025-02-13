def delta_x(a, b, n):
    return (b - a) / n


def list_of_intervals(interval, n):
    a, b = interval
    dtx = delta_x(a, b, n)

    return [dtx * i for i in range(n + 1)]


def print_intervals(intervals):
    line = ''.join(f"{num:7.2f}" for num in intervals)
    print(''.join(line))


def main():
    interval = [0, 1]
    parts = 2

    intervals = list_of_intervals(interval, parts)
    print_intervals(intervals)

main()    