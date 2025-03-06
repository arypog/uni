#include <stdio.h>
#include <string.h>

#define MAX_LEN 256

void inverseR(char* s, int i, int n) {
    if(i >= n/2+1) return;

    char tmp = s[i];
    s[i] = s[n - 1 - i];
    s[n - 1 - i] = tmp;

    return inverseR(s, i+1, n);
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
        if (strcmp(str, "FIM") == 0) break; 
        inverseR(str, 0, len);
        printf("%s\n", str);
    }

}
