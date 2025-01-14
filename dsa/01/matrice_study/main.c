#include <stdio.h>

#define MAX 10

void populate_matrix(int matrix[MAX][MAX], int n1,int n2) {
    for (int i = 0; i< n1;i++){
        for(int j = 0; j< n2; j++){
            printf("add value to pos-> row:%d col:%d: ",i,j);
            scanf("%d", &matrix[i][j]);
        }
        printf("\n");
    }
}

void print_matrix(int matrix[MAX][MAX], int n1,int n2) {
    for (int i = 0; i< n1;i++){
        for(int j = 0; j< n2; j++){
            printf("%d\t", matrix[i][j]);
        }
        printf("\n");
    }
}

int main() {
    int n1,n2;
    printf("write the matrix row and col: ");
    scanf("%d %d", &n1, &n2);
    int matrix[n1][n2];
    populate_matrix(matrix, n1, n2);
    print_matrix(matrix, n1, n2);
}