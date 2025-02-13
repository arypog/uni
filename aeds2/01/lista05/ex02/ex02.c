// cc ex02.c  -o ex02 && ./ex02

#include <stdio.h>
#include <stdlib.h>

int main() {
    FILE *arq;

    char *str;
    uint size = 0;

    arq = fopen("o.txt", "w");
    if (arq == NULL) return 1;

    getline(&str, &size, stdin);
    fprintf(arq, "%s \n", str);

    free(str);
    fclose(arq);
    return 0;
}