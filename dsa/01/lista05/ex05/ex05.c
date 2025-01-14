// cc ex05.c  -o ex05 && ./ex05

#include <stdio.h>
#include <stdlib.h>

#define  MAX  100

int main() {
    FILE *arq1;
    FILE *arq2;
    FILE *oarq;

    char str[MAX];

    arq1 = fopen("a.txt", "r");
    arq2 = fopen("b.txt", "r");
    oarq = fopen("o.txt", "w");

    if (arq1 == NULL) return 1;
    if (arq2 == NULL) return 2;
    if (oarq == NULL) return 3;

    while(1) {  
        if (feof(arq1)) break ;
        fgets(str, MAX, arq1);
        fprintf(oarq,"%s", str); 
    }
    fprintf(oarq,"\n");
    while(1) {  
        if (feof(arq2)) break ;
        fgets(str, MAX, arq2);
        fprintf(oarq,"%s", str); 
    }

    fclose(arq1);
    fclose(arq2);
    fclose(oarq);
    return 0;
}