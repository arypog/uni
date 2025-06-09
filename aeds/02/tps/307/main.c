#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

#define MAX_LINE 1024
#define MAX_FIELDS 12
#define MAX_SHOWS 1369
#define QUEUE_SIZE 5

typedef struct {
  char* id;
  char* type;
  char* title;
  char* director;
  char** cast;
  int cast_size;
  char* country;
  char* date_added;
  int release_year;
  char* rating;
  char* duration;
  char** listed_in;
  int listed_in_size;
  char* description;
} Show;

void trim(char* str) {
  for (int i = 0; str[i]; i++) {
    if (str[i] == '\n' || str[i] == '\r') {
      str[i] = '\0';
      break;
    }
  }
}

int isEmpty(char* str) {
  return str == NULL || strlen(str) == 0;
}

char** split(char* str, char* delim, int* count) {
  char* temp = strdup(str);
  char* token = strtok(temp, delim);
  char** result = malloc(sizeof(char*) * 50);
  int i = 0;
  while (token != NULL) {
    while (*token == ' ') token++;
    result[i++] = strdup(token);
    token = strtok(NULL, delim);
  }
  *count = i;
  free(temp);
  return result;
}

void sortStrings(char** arr, int n) {
  for (int i = 0; i < n - 1; i++) {
    for (int j = 0; j < n - i - 1; j++) {
      if (strcmp(arr[j], arr[j + 1]) > 0) {
        char* tmp = arr[j];
        arr[j] = arr[j + 1];
        arr[j + 1] = tmp;
      }
    }
  }
}

void printStringArray(char** arr, int n) {
  printf("[");
  for (int i = 0; i < n; i++) {
    printf("%s", arr[i]);
    if (i < n - 1) printf(", ");
  }
  printf("]");
}

void parseCSVLine(char* line, char* parts[]) {
  int i = 0, j = 0, k = 0, inQuotes = 0;
  char buffer[MAX_LINE];
  while (line[i]) {
    if (line[i] == '\"') {
      inQuotes = !inQuotes;
    } else if (line[i] == ',' && !inQuotes) {
      buffer[j] = '\0';
      parts[k++] = strdup(buffer);
      j = 0;
    } else {
      buffer[j++] = line[i];
    }
    i++;
  }
  buffer[j] = '\0';
  parts[k++] = strdup(buffer);
}

void setShow(Show* show, char* parts[]) {
  show->id         = isEmpty(parts[0]) ? strdup("NaN") : strdup(parts[0]);
  show->type       = isEmpty(parts[1]) ? strdup("NaN") : strdup(parts[1]);
  show->title      = isEmpty(parts[2]) ? strdup("NaN") : strdup(parts[2]);
  show->director   = isEmpty(parts[3]) ? strdup("NaN") : strdup(parts[3]);

  if (isEmpty(parts[4])) {
    show->cast = malloc(sizeof(char*));
    show->cast[0] = strdup("NaN");
    show->cast_size = 1;
  } else {
    show->cast = split(parts[4], ",", &show->cast_size);
    sortStrings(show->cast, show->cast_size);
  }

  show->country    = isEmpty(parts[5]) ? strdup("NaN") : strdup(parts[5]);
  show->date_added = isEmpty(parts[6]) ? strdup("March 1, 1900") : strdup(parts[6]);
  show->release_year = isEmpty(parts[7]) ? -1 : atoi(parts[7]);
  show->rating     = isEmpty(parts[8]) ? strdup("NaN") : strdup(parts[8]);
  show->duration   = isEmpty(parts[9]) ? strdup("NaN") : strdup(parts[9]);

  if (isEmpty(parts[10])) {
    show->listed_in = malloc(sizeof(char*));
    show->listed_in[0] = strdup("NaN");
    show->listed_in_size = 1;
  } else {
    show->listed_in = split(parts[10], ",", &show->listed_in_size);
  }

  show->description = isEmpty(parts[11]) ? strdup("NaN") : strdup(parts[11]);
}

void printShow(Show* show) {
  printf("=> %s ## %s ## %s ## %s ## ", show->id, show->title, show->type, show->director);
  printStringArray(show->cast, show->cast_size);
  printf(" ## %s ## %s ## %d ## %s ## %s ## ", 
         show->country, show->date_added, show->release_year, show->rating, show->duration);
  printStringArray(show->listed_in, show->listed_in_size);
  printf(" ##\n");
}

typedef struct Node {
  Show* show;
  struct Node* next;
} Node;

typedef struct {
  Node* head;
  Node* tail;
  int size;
} LinkedQueue;

void initQueue(LinkedQueue* q) {
  q->head = NULL;
  q->tail = NULL;
  q->size = 0;
}

int isQueueEmpty(LinkedQueue* q) {
  return q->size == 0;
}

Show* dequeue(LinkedQueue* q) {
  if (isQueueEmpty(q)) return NULL;

  Node* tmp = q->head;
  Show* removedShow = tmp->show;

  q->head = q->head->next;
  if (q->head == NULL) q->tail = NULL;
  q->size--;

  free(tmp);
  return removedShow;
}

void enqueue(LinkedQueue* q, Show* show) {
  if (q->size == QUEUE_SIZE) {
    dequeue(q);
  }

  Node* newNode = malloc(sizeof(Node));
  newNode->show = show;
  newNode->next = NULL;

  if (isQueueEmpty(q)) {
    q->head = q->tail = newNode;
  } else {
    q->tail->next = newNode;
    q->tail = newNode;
  }
  q->size++;

  int sum = 0;
  Node* cur = q->head;
  while (cur != NULL) {
    sum += cur->show->release_year;
    cur = cur->next;
  }
  printf("[Media] %d\n", sum / q->size);
}

int main() {
  FILE* f = fopen("/tmp/disneyplus.csv", "r");
  if (!f) {
    perror("Error opening CSV file");
    return 1;
  }

  char line[MAX_LINE];
  Show* shows[MAX_SHOWS];
  int count = 0;

  fgets(line, sizeof(line), f);
  while (fgets(line, sizeof(line), f) && count < MAX_SHOWS) {
    trim(line);
    char* parts[MAX_FIELDS];
    parseCSVLine(line, parts);
    shows[count] = malloc(sizeof(Show));
    setShow(shows[count], parts);
    for (int i = 0; i < MAX_FIELDS; i++) free(parts[i]);
    count++;
  }
  fclose(f);

  LinkedQueue queue;
  initQueue(&queue);

  while (fgets(line, sizeof(line), stdin)) {
    trim(line);
    if (strcmp(line, "FIM") == 0) break;
    for (int i = 0; i < count; i++) {
      if (strcmp(shows[i]->id, line) == 0) {
        enqueue(&queue, shows[i]);
        break;
      }
    }
  }

  int n;
  scanf("%d\n", &n);
  for (int i = 0; i < n; i++) {
    fgets(line, sizeof(line), stdin);
    trim(line);
    if (line[0] == 'I') {
      char id[50];
      sscanf(line + 2, "%s", id);
      for (int j = 0; j < count; j++) {
        if (strcmp(shows[j]->id, id) == 0) {
          enqueue(&queue, shows[j]);
          break;
        }
      }
    } else if (line[0] == 'R') {
      Show* removed = dequeue(&queue);
      if (removed) printf("(R) %s\n", removed->title);
    }
  }

  int index = 0;
  for (Node* cur = queue.head; cur != NULL; cur = cur->next) {
    printf("[%d] ", index++);
    printShow(cur->show);
  }

  while (!isQueueEmpty(&queue)) {
    dequeue(&queue);
  }

  for (int i = 0; i < count; i++) {
    free(shows[i]->id);
    free(shows[i]->type);
    free(shows[i]->title);
    free(shows[i]->director);
    for (int j = 0; j < shows[i]->cast_size; j++) free(shows[i]->cast[j]);
    free(shows[i]->cast);
    free(shows[i]->country);
    free(shows[i]->date_added);
    free(shows[i]->rating);
    free(shows[i]->duration);
    for (int j = 0; j < shows[i]->listed_in_size; j++) free(shows[i]->listed_in[j]);
    free(shows[i]->listed_in);
    free(shows[i]->description);
    free(shows[i]);
  }

  return 0;
}
