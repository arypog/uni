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
  Node* top;
} Stack;

void initStack(Stack* s) {
  s->top = NULL;
}

int isEmptyS(Stack* s) {
  return s->top == NULL;
}

void push(Stack* s, Show* show) {
  Node* newNode = (Node*)malloc(sizeof(Node));
  newNode->show = show;
  newNode->next = s->top;
  s->top = newNode;
}

Show* pop(Stack* s) {
  if (isEmptyS(s)) return NULL;
  Node* temp = s->top;
  Show* show = temp->show;
  s->top = s->top->next;
  free(temp);
  return show;
}

void printStack(Stack* s) {
  Node* current = s->top;
  int index = 0;

  Node* tmp = current;
  int size = 0;
  while (tmp) {
    size++;
    tmp = tmp->next;
  }

  Node** arr = malloc(sizeof(Node*) * size);
  tmp = current;
  for (int i = 0; i < size; i++) {
    arr[i] = tmp;
    tmp = tmp->next;
  }

  for (int i = 0; i <= size - 1; i++) {
    printf("[%d] ", size - i - 1);
    printShow(arr[i]->show);
  }

  free(arr);
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
  Stack pilha;
  initStack(&pilha);

  char inputId[100];
  while (fgets(inputId, sizeof(inputId), stdin)) {
    trim(inputId);
    if (strcmp(inputId, "FIM") == 0) break;

    for (int i = 0; i < count; i++) {
      if (strcmp(shows[i]->id, inputId) == 0) {
        push(&pilha, shows[i]);
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
          push(&pilha, shows[j]);
          break;
        }
      }
    } else if (line[0] == 'R') {
      Show* rem = pop(&pilha);
      if (rem) printf("(R) %s\n", rem->title);
    }
  } 

  printStack(&pilha); 

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

