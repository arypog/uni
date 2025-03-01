#include <stdio.h>
#include <stdlib.h>

void writeFile(int n) {
    FILE *fptr;
    fptr = fopen("file.file", "wb");
    if (fptr == NULL) {
        return;
    }

    double num;
    for(int i = 0; i < n; i++) {
        scanf("%lf", &num);
        fwrite(&num, sizeof(double), 1, fptr);
    }

    fclose(fptr);
}

void readFile(int n) {
    FILE *fptr = fopen("file.file", "rb");
    if (fptr == NULL) {
        return;
    }

    fseek(fptr, 0, SEEK_END);
    long end = ftell(fptr);
    fseek(fptr, 0, SEEK_END);

    for(int i = 0; i < n; i++) {
        fseek(fptr, end - (i + 1) * sizeof(double), SEEK_SET);        

        double num;
        fread(&num, sizeof(double), 1, fptr);

        printf("%.15g\n", num);
    }

    fclose(fptr);
}

int main() {
    int n;
    scanf("%d", &n);

    writeFile(n);
    readFile(n);

    return 0;
}