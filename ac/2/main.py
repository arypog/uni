import subprocess
from pathlib import Path
import re

from openpyxl import Workbook


p = Path(".")


debug = False


def parse_test(lvl: int, text: str):
    pattern = re.compile(
        r"#\s*Params:\s*"
        r"N=(?P<N>\d+)\s+"
        r"REPEAT=(?P<REPEAT>\d+)\s+"
        r"STRIDE=(?P<STRIDE>\d+)\s+"
        r"MODE=(?P<MODE>\d+)\s*"
        r"(?:\r?\n)+Tempo\(s\):\s*(?P<TEMPO>[+-]?\d+(?:\.\d+)?(?:[eE][+-]?\d+)?)\s*"
        r"(?:\r?\n)+Checksum\(float\):\s*(?P<CHECKSUM>[+-]?\d+(?:\.\d+)?(?:[eE][+-]?\d+)?)"
    )

    m = pattern.search(text)

    if m:
        return {
            "Op": lvl,
            "N": int(m["N"]),
            "REPEAT": int(m["REPEAT"]),
            "STRIDE": int(m["STRIDE"]),
            "MODE": int(m["MODE"]),
            "Tempo_s": float(m["TEMPO"]),
            "Checksum": float(m["CHECKSUM"]),
        }

    else:

        return None


def run_tests(lvl: int):
    cmd = ["gcc", "./exe02_ativ1.c", f"-O{lvl}", "-o", "atv1", "-lm"]
    subprocess.run(cmd)

    if not debug:
        # Tamanho da matriz NxN.
        N = [2048, 4096, 8192]

        # Número de repetições do cálculo.
        REPEAT = 8

        # Passo de acesso à memória.
        STRIDE = [1, 2, 4, 8, 16, 32]

        # Versão do algoritmo
        MODE = [0, 1, 2]
    else:
        N = [2048]
        REPEAT = 2
        STRIDE = [1]
        MODE = [0, 1]

    out = []
    for n in N:
        for stride in STRIDE:
            for mode in MODE:
                cmd = f"./atv1 {n} {REPEAT} {stride} {mode}"
                out.append(subprocess.getoutput(cmd))

    return out


def create_xlsx_and_run_tests():
    wb = Workbook()
    ws = wb.active
    ws.title = "result"

    headers = ["Op", "N", "REPEAT", "STRIDE", "MODE", "Tempo_s", "Checksum"]
    ws.append(headers)

    all_results = {0: [], 2: []}
    for i in range(0, 3, 2):
        tests = run_tests(i)
        for test in tests:
            row = parse_test(i, test)
            if row:
                ws.append([row[h] for h in headers])
                all_results[i].append(row)

    ws_diff = wb.create_sheet("diffs")
    ws_diff.append(["N", "REPEAT", "STRIDE", "MODE", "O0_time", "O2_time", "Diff"])
    for row0, row2 in zip(all_results[0], all_results[2]):
        diff = row2["Tempo_s"] - row0["Tempo_s"]
        ws_diff.append(
            [
                row0["N"],
                row0["REPEAT"],
                row0["STRIDE"],
                row0["MODE"],
                row0["Tempo_s"],
                row2["Tempo_s"],
                diff,
            ]
        )

    wb.save("times.xlsx")


if __name__ == "__main__":
    create_xlsx_and_run_tests()
