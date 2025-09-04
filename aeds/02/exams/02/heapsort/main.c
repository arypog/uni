#include "stdio.h"

void heapsort() {}

int main() {
  int array[] = {5, 3, 2, 4, 1};
  int n = sizeof(array)/sizeof(array[0]);

  heapsort(array, 0, n-1);

  for (int i = 0; i < n; i++) {
    printf("%d ", array[i]);
  }
  printf("\n");

  return 0;
}
