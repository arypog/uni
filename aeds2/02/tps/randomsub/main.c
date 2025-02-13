#include <stdio.h>
#include <stdlib.h>
#include <string.h>
//#include <time.h>

#define MAX_LEN 1000

// gcc -o ../bin/a main.c && ../bin/a 
int remove_newline(char* s) {
    int len = 0;
    while (s[len] != '\0') {
        if (s[len] == '\n' || s[len] == '\r') {
            s[len] = '\0';
            break;
        }
        len++;
    }
    
    return len;
}

unsigned int lcg_rand(unsigned int *seed) {
    *seed = (*seed * 1664525 + 1013904223) % 4294967296; 
    return *seed;
}

void subs(char* s, int n, char a, char b) {
    for (int i = 0; i <= n; i++) {
        if (s[i] == a) {
            s[i] = b;
        }
    } 
}

int main() {
    char str[MAX_LEN];

    while (fgets(str, sizeof(str), stdin)) {
        int len = remove_newline(str);
        if (strcmp(str, "FIM") == 0) break; 

        //srand(4);
        //char a = 'a' + (abs(rand()) % 26);
        //char b = 'a' + (abs(rand()) % 26); 
        unsigned int seed = 42;
        char a = 'a' + (abs(lcg_rand(&seed)) % 26);
        char b = 'a' + (abs(lcg_rand(&seed)) % 26); 
        //printf("%c %c\n", a, b);

        subs(str, len, a, b);

        printf("%s\n", str);
    }
    
    return 0;
}