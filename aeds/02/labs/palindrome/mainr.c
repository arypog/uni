#include <stdio.h>
#include <string.h>

// gcc -o bin/pr mainr.c && ./bin/pr < pub.in > my.out
int isPalindromerc(char* s, int i, int j) {
    if (i >= j) return 1;

    if (s[i] != s[j]) return 0;

    return isPalindromerc(s, i+1, j-1);
}

int main() {
    char str[500];

    while (fgets(str, sizeof(str), stdin)) {

        int len = 0;
        while (str[len] != '\0') {
            if (str[len] == '\n' || str[len] == '\r') {
                str[len] = '\0';
                break;
            }
            len++;
        }

        if (strcmp(str, "FIM") == 0) break;
        if (isPalindromerc(str, 0, len - 1)) printf("SIM\n");
        else printf("NAO\n");
    }

    return 0;
}