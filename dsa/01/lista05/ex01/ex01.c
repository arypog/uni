// cc ex01.c  -o ex01 && ./ex01

#include <stdio.h>

int main() {
    FILE *arq;

    arq = fopen("o.txt", "w");

    if (arq == NULL) return 1;

    for(int i = 1; i < 11; i++) {
        fprintf(arq, "%d \n", i);
    }

    fclose(arq);
    return 0;
}