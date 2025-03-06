#include <stdio.h>
#include <string.h>

#define MAX_LEN 256

int sumR(char* s, int i, int n) {
    if (i >= n) return 0;

    int sum = (int) (s[i] - '0') + sumR(s, i+1, n);

    return sum;
}

int main() {
    char str[MAX_LEN];
    while(fgets(str, sizeof(str), stdin)) {
        int len = 0;
        for (; str[len] != '\0'; len++) {
            if(str[len] == '\n' || str[len] == '\r') {
                str[len] = '\0';
                break;
            }
        }
        if(strcmp(str, "FIM") == 0) break;
        printf("%d\n", sumR(str, 0, len));
    }
   
    return 0;
}