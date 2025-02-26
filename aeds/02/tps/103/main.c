#include <stdio.h>
#include <wchar.h>
#include <locale.h>
#include <wctype.h>

#define MAX_LEN 1000

// gcc -o ../bin/a main.c && ../bin/a 
void cipher(wchar_t* s, int k) {
    for (int i = 0; s[i] != '\0'; i++) {
        unsigned char c = s[i];

        if ((c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z')) {
            char base = (c >= 'a') ? 'a' : 'A';
            s[i] = base + (c - base + k) % 26;
        } // else if 
    }
}

int main() {
    setlocale(LC_ALL, "");
    wchar_t str[MAX_LEN];

    while (fgetws(str, sizeof(str) / sizeof(wchar_t), stdin)) {

        int len = 0;
        while (str[len] != L'\0') {
            if (str[len] == L'\n' || str[len] == L'\r') {
                str[len] = L'\0';
                break;
            }
            len++;
        }

        if(wcscmp(str, L"FIM") == 0) break;
        cipher(str, 3); 
        wprintf(L"%ls\n", str);
    }
    
    return 0;
}