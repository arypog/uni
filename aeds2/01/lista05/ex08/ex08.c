// cc ex08.c  -o ex08 && ./ex08

#include <stdio.h>

int main() {

    FILE *arq = fopen("o.txt", "w");
    if (arq == NULL) return 1;

    int qntv;
    double va;
    double fa, mm, ma;

    scanf("%d %lf", &qntv, &va);

    fa = (qntv / 3.0 * 12) * va;
    mm = (qntv / 10.0) * va * 0.2;
    ma = qntv * 0.02 * 600.0;

    printf("%.2lf\n", fa);
    printf("%.2lf\n", mm);
    printf("%.2lf\n", ma);

    fprintf(arq, "%.2lf\n", fa);
    fprintf(arq, "%.2lf\n", mm);
    fprintf(arq, "%.2lf\n", ma);
    // matematica tá errado + funciona

    fclose(arq);

    return 0;
}
