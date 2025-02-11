#include <stdio.h>

#define INF 100
/*
 * Simple search, idea is to iterate trough the whole array and check if the i 
 * element is the target.
 * Best case:           O(1)
 * Worst, average case: O(n)
 * Big O:               n
*/
int inArray(int* a, int l, int k) {
    int i = 0;
    for (; i < l; i++) {            // l-1
        if (a[i] == k) return 1;    // 1/l
    }
    return 0;
}

/*
 * Binary search, idea is to repeately divide the array in half and check if the
 * middle element is the target. Only for sorted arrays.
 * After k iterations, the search space becames l / 2^k.
 * Best case:           O(1)
 * Worst, average case: O(log n)
 * Big O:               log l
 */
int inArray2(int* a, int l, int k) {
    int q = 0, p = l - 1;
    while (q <= p) {                    
        int m = q + (p - q) / 2;

        if (a[m] == k) return 1;
        if (a[m] > k) p = m + 1;
        else q = m + 1;
    }
    return 0;
}

/* number of comparisons: 2l */
void showMinMax(int *a, int l) {
    int min = INF, max = 0;
    for (int i = 0; i < l; i++) {         
        if (a[i] < min) min = a[i];        
        else if (a[i] > max) max = a[i];   
    }
    printf("MIN: %d, MAX: %d\n", min, max);
}

/* Number of comparisons:
 * even: 2l
 * odd:  2l - 1
*/
void showMinMax2(int *a, int l) {
    int min = INF, max = 0;
    if (l % 2 == 1) {
        min = max = a[0];
        a++;
        l--;
    }

    for (int i = 0; i < l; i+=2) {
        int x = a[i], y = a[i+1];
        if (x < y) {
            if (x < min) min = x;
            if (y > max) max = y;
        } else {
            if (y < min) min = y;
            if (x > max) max = x;
        }
    }

    printf("MIN: %d, MAX: %d\n", min, max);
}

int main() {
    int a[] = {1, 2, 3};
    int l = sizeof(a) / sizeof(a)[0];
    int k = 2;

    printf("1(true), 0(false): %d\n", inArray(a, l, k));
    printf("1(true), 0(false): %d\n", inArray2(a, l, k));
    showMinMax(a, l);
    showMinMax2(a, l);
}