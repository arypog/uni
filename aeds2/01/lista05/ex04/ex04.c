// cc ex04.c  -o ex04 && ./ex04

#include <stdio.h>
#include <stdlib.h>

#define  MAX  100

int main() {
    FILE *arq;

    char *str;
    uint qntdstr = 0;

    arq = fopen("o.txt", "r");
    if (arq == NULL) return 1;

    while(1) {  
        if (feof(arq)) break ;

        str = fgets(str, MAX, arq);
        printf("%s", str); 
        qntdstr++;
    }
        

    printf("\n%d LINHAS\n",qntdstr);

    fclose(arq);
    return 0;
}