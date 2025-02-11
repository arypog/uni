#include <stdio.h>
#include <wchar.h>
#include <locale.h>
#include <wctype.h>

// gcc -o bin/p main.c && ./bin/p < pub.in > my.out
int isPalindrome(wchar_t* s, int n) {
    for (int i = 0, j = n - 1; i <= j; i++, j--) {
        if (s[i] != s[j]) return 0;
    } 
    return 1;
}

int main() {
    setlocale(LC_ALL, "");
    wchar_t str[500];

    while (fgetws(str, sizeof(str) / sizeof(wchar_t), stdin)) {

        int len = 0;
        while (str[len] != L'\0') {
            if (str[len] == L'\n' || str[len] == L'\r') {
                str[len] = L'\0';
                break;
            }
            len++;
        }

        if (wcscmp(str, L"FIM") == 0) break;
        if (isPalindrome(str, len)) wprintf(L"SIM\n");
        else wprintf(L"NAO\n");
    }

    return 0;
}