// cc ex07.c  -o ex07 && ./ex07

#include <stdio.h>

#include <stdio.h>

int main() {
    int N;
    char l;
    int qntv = 0;

    scanf("%d", &N);

    FILE *arq = fopen("o.txt", "w");

    if (arq == NULL) return 1;

    for (int i = 0; i < N; i++) {
        scanf(" %c", &l);
        fprintf(arq, "%c\n", l);
    }

    fclose(arq);

    arq = fopen("o.txt", "r");
    if (arq == NULL) return 1;

    while (fscanf(arq, " %c", &l) != EOF) {
        if (l == 'A' || l == 'a' || l == 'E' || l == 'e' || l == 'I' || l == 'i' || l == 'O' || l == 'o' || l == 'U' || l == 'u') {
            qntv++;
        }
    }

    printf("%d\n", qntv);

    fclose(arq);

    return 0;
}
