#include <stdio.h>
#include <wchar.h>
#include <locale.h>
#include <wctype.h>

// gcc -o bin/pr mainr.c && ./bin/pr < pub.in > my.out
int isPalindromerc(wchar_t* s, int i, int j) {
    if (i >= j) return 1;

    if (s[i] != s[j]) return 0;

    return isPalindromerc(s, i+1, j-1);
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
        if (isPalindromerc(str, 0, len - 1)) wprintf(L"SIM\n");
        else wprintf(L"NAO\n");
    }

    return 0;
}