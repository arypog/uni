#include "stdio.h"

// Pick a pivot
// Go over the array while array at index i is less than the pivot
// Go over the array while array at index > is more than the pivot
// If i less or equal to j swap them
// Iontinue while i <= j
// Comparations
// best case: nlog(n), halfing the n into two equal parts
// worst case: n^2, the pivot is the min or max element in the array, only
// removing one element each call.
// Movimentations
// best case: 3, when i is equal to j, we'll have one swap
// worst case: 3[n/2]
// Why use? 
// Extremally efficient
// The average is nlog(n)
// Why don't use?
// Quadratict worst case
// Hard implementation
// Non stable method
void quicksort(int *a, int left, int right) {
    int i = left,   j = right,   pivot = a[(left+right)/2];
    while (i <= j) {

      while (a[i] < pivot)
        i++;

      while (a[j] > pivot)
        j--;
      
      if (i <= j) {
        int tmp = a[i];
        a[i] = a[j];
        a[j] = tmp;
        i++;
        j--;
      }
    }

    if (left < j)
      quicksort(a, left, j);
    if (i < right)
      quicksort(a, i, right);
}

int main() {
  int array[] = {5, 3, 2, 4, 1};
  int n = sizeof(array)/sizeof(array[0]);

  quicksort(array, 0, n-1);

  for (int i = 0; i < n; i++) {
    printf("%d ", array[i]);
  }
  printf("\n");

  return 0;
}
