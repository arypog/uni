#include <stdio.h>
#include <stdlib.h>

typedef struct Node {
  int elem;
  struct Node *next;
} Node;

typedef struct Queue {
  Node *first;
  Node *last;
} Queue;

Queue* init() {
  Queue *queue = (Queue*) malloc(sizeof(Queue));
  queue->first = (Node*) malloc(sizeof(Node));;
  queue->first->next = NULL;
  queue->last = queue->first;
  return queue;
}

void insertLast(Queue* queue, int newElem) {
  queue->last->elem = newElem;
  queue->last->next = (Node*) malloc(sizeof(Node));
  queue->last = queue->last->next;
  queue->last->next = NULL;
}

void removeFirst(Queue* queue) {
  if (queue->first == queue->last) return;
  Node *tmp = queue->first;
  queue->first = queue->first->next;
  free(tmp);
}

int isEmpty(Queue* queue) {
  return queue->first == queue->last;
}

void printAndRemove(Queue* queue) {
  if (isEmpty(queue)) return;
  printf("A%d ", queue->first->elem);
  removeFirst(queue);
}

int main() {
  Queue *n = init();
  Queue *s = init();
  Queue *l = init();
  Queue *o = init();

  char input[5];
  int currDir = 0;

  scanf("%[^\n\r]", input);
  getchar();

  while (input[0] != '0') {
    int num = atoi(input + 1);

    if (input[0] == '-') { currDir = num; }
    else {
      switch (currDir) {
        case 1: insertLast(o, num); break; // oeste 
        case 2: insertLast(s, num); break; // sul
        case 3: insertLast(n, num); break; // norte 
        case 4: insertLast(l, num); break; // leste
      }
    }
    scanf("%[^\n\r]", input);
    getchar();
  }   
  
  while (!isEmpty(o) || !isEmpty(n) || !isEmpty(s) || !isEmpty(l)) {
    printAndRemove(o);
    printAndRemove(n);
    printAndRemove(s);
    printAndRemove(l);
  }
  printf("\n");

  free(n->last);
  free(n);
  free(s->last);
  free(s);
  free(l->last);
  free(l);
  free(o->last);
  free(o);

  return 0;
}
