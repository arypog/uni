#include "stdio.h"

// Divide the array until it can no more be divided
// sort each subarray individually using merge
// Time complexity
// for all cases is: nlog(n)
// use cases:
// sort large datasets
// preferred to sort linked lists
// can easily be parallelized
void merge(int *a, int left, int middle, int right) {
  int n1 = middle - left + 1;
  int n2 = right - middle;
  int L[n1], R[n2];
  for (int i = 0; i < n1; i++) L[i] = a[left + i];
  for (int i = 0; i < n2; i++) R[i] = a[middle + 1 + i];

  int i = 0,  j = 0,  k = left;
  while (i < n1 && j < n2) {
    if (L[i] <= R[j]) {
      a[k] = L[i];
      i++;
    } else {
      a[k] = R[j];
      j++;
    }
    k++;
  }

  while (i < n1) {
    a[k] = L[i];
    i++;
    k++;
  }

  while (j < n2) {
    a[k] = R[j];
    j++;
    k++;
  }
}

void mergesort(int *a, int left, int right) {
  if (left >= right)
    return;
  int mid = left + (right - left) / 2;
  mergesort(a, left, mid);
  mergesort(a, mid + 1, right);
  merge(a, left, mid, right);
}

int main() {
  int array[] = {5, 3, 2, 4, 1};
  int n = sizeof(array)/sizeof(array[0]);

  mergesort(array, 0, n-1);

  for (int i = 0; i < n; i++) {
    printf("%d ", array[i]);
  }
  printf("\n");

  return 0;
}
