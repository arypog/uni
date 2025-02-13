// cc ex03.c  -o ex03 && ./ex03

#include <stdio.h>
#include <stdlib.h>

int main() {
    FILE *arq;

    char c;
    int qntc = 0;

    arq = fopen("o.txt", "r");
    if (arq == NULL) return 1;

    while(1) {   
        char c = fgetc(arq);
        if (feof(arq)) break;
        if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) qntc++;
        printf("%c", c); 
    }
        

    printf("%d CARACTERES\n",qntc);

    fclose(arq);
    return 0;
}