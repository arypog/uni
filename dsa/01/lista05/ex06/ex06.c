// cc ex06.c  -o ex06 && ./ex06

#include <stdio.h>

int main() {
    int n, soma= 0;

    scanf("%d", &n);

    FILE *arq;
    arq = fopen("o.txt", "w");

    if (arq == NULL) return 1;

    for (int i = 1; i <= n; i++) {
        if (n % i == 0) {
            printf("%d\n", i);
            soma += i;
        }
    }

    fprintf(arq, "%d", soma);

    fclose(arq);
    return 0;
}