#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <ctype.h>

#define MAX_LINE 1024
#define MAX_FIELDS 12
#define MAX_SHOWS 1369

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
  int i;
  for (i = 0; str[i]; i++) {
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
  char* tmp = strdup(str);
  char* token = strtok(tmp, delim);
  char** result = malloc(sizeof(char*) * 50);
  int i = 0;

  while (token != NULL) {
    while (*token == ' ') token++;
    result[i++] = strdup(token);
    token = strtok(NULL, delim);
  }

  *count = i;
  free(tmp);
  return result;
}

void sort(char** arr, int n) {
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

void printArray(char** arr, int n) {
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
    sort(show->cast, show->cast_size);
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
  printArray(show->cast, show->cast_size);
  printf(" ## %s ## %s ## %d ## %s ## %s ## ",
         show->country, show->date_added, show->release_year, show->rating, show->duration);
  printArray(show->listed_in, show->listed_in_size);
  printf(" ##\n");
}

// ========================================================================== //

typedef struct Node {
  Show* show;
  struct Node* next;
} Node;

typedef struct {
  Node* front;
  Node* rear;
  int size;
} ShowQueue;

void initQueue(ShowQueue* q) {
  q->front = NULL;
  q->rear = NULL;
  q->size = 0;
}

void enqueue(ShowQueue* q, Show* s) {
  Node* node = malloc(sizeof(Node));
  node->show = s;
  node->next = NULL;

  if (q->rear == NULL) {
    q->front = node;
    q->rear = node;
  } else {
    q->rear->next = node;
    q->rear = node;
  }
  q->size++;
}

Show* dequeue(ShowQueue* q) {
  if (q->front == NULL) return NULL;
  Node* temp = q->front;
  Show* s = temp->show;
  q->front = temp->next;
  if (q->front == NULL) q->rear = NULL;
  free(temp);
  q->size--;
  return s;
}

Show* removeAtPosition(ShowQueue* q, int pos) {
  if (pos < 0 || pos >= q->size) return NULL;
  Node* current = q->front;
  Node* prev = NULL;

  for (int i = 0; i < pos; i++) {
    prev = current;
    current = current->next;
  }

  if (prev == NULL) {
    q->front = current->next;
    if (q->front == NULL) q->rear = NULL;
  } else {
    prev->next = current->next;
    if (current == q->rear) q->rear = prev;
  }

  Show* s = current->show;
  free(current);
  q->size--;
  return s;
}

void insertAtPosition(ShowQueue* q, Show* s, int pos) {
  if (pos < 0 || pos > q->size) return;

  Node* node = malloc(sizeof(Node));
  node->show = s;
  node->next = NULL;

  if (pos == 0) {
    node->next = q->front;
    q->front = node;
    if (q->rear == NULL) q->rear = node;
  } else {
    Node* current = q->front;
    for (int i = 0; i < pos - 1; i++) {
      current = current->next;
    }
    node->next = current->next;
    current->next = node;
    if (node->next == NULL) q->rear = node;
  }
  q->size++;
}

void insertStart(ShowQueue* q, Show* s) {
  insertAtPosition(q, s, 0);
}

void insertEnd(ShowQueue* q, Show* s) {
  insertAtPosition(q, s, q->size);
}

Show* removeStart(ShowQueue* q) {
  return dequeue(q);
}

Show* removeEnd(ShowQueue* q) {
  return removeAtPosition(q, q->size - 1);
}

void printQueue(ShowQueue* q) {
  Node* current = q->front;
  int index = 0;
  while (current) {
    printShow(current->show);
    current = current->next;
  }
}

// ========================================================================== //

int main() {
  FILE* f = fopen("/tmp/disneyplus.csv", "r");
  if (!f) {
    perror("Erro ao abrir CSV");
    return 1;
  }

  char line[MAX_LINE];
  Show* shows[MAX_SHOWS];
  int count = 0;

  fgets(line, sizeof(line), f); // skip header

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

  // tp
  ShowQueue queue;
  initQueue(&queue);

  char inputId[100];
  while (fgets(inputId, sizeof(inputId), stdin)) {
    trim(inputId);
    if (strcmp(inputId, "FIM") == 0) break;

    for (int i = 0; i < count; i++) {
      if (strcmp(shows[i]->id, inputId) == 0) {
        insertEnd(&queue, shows[i]);
        break;
      }
    }
  }

  int n;
  scanf("%d\n", &n);
  for (int i = 0; i < n; i++) {
    fgets(line, sizeof(line), stdin);
    trim(line);

    if (strncmp(line, "II", 2) == 0) {
      char id[20];
      sscanf(line + 3, "%s", id);
      for (int j = 0; j < count; j++) {
        if (strcmp(shows[j]->id, id) == 0) {
          insertStart(&queue, shows[j]);
          break;
        }
      }
    } else if (strncmp(line, "IF", 2) == 0) {
      char id[20];
      sscanf(line + 3, "%s", id);
      for (int j = 0; j < count; j++) {
        if (strcmp(shows[j]->id, id) == 0) {
          insertEnd(&queue, shows[j]);
          break;
        }
      }
    } else if (strncmp(line, "I*", 2) == 0) {
      int pos;
      char id[20];
      sscanf(line + 3, "%d %s", &pos, id);
      for (int j = 0; j < count; j++) {
        if (strcmp(shows[j]->id, id) == 0) {
          insertAtPosition(&queue, shows[j], pos);
          break;
        }
      }
    } else if (strcmp(line, "RI") == 0) {
      Show* removed = removeStart(&queue);
      if (removed) printf("(R) %s\n", removed->title);
    } else if (strcmp(line, "RF") == 0) {
      Show* removed = removeEnd(&queue);
      if (removed) printf("(R) %s\n", removed->title);
    } else if (strncmp(line, "R*", 2) == 0) {
      int pos;
      sscanf(line + 3, "%d", &pos);
      Show* removed = removeAtPosition(&queue, pos);
      if (removed) printf("(R) %s\n", removed->title);
    }
  }

  printQueue(&queue);

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

  while (queue.front != NULL) {
    Node* temp = queue.front;
    queue.front = queue.front->next;
    free(temp);
  }

  return 0;
}
