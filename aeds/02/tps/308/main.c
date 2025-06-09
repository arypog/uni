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
//

typedef struct Node {
  Show* show;
  struct Node* prev;
  struct Node* next;
} Node;

Node* createNode(Show* show) {
  Node* node = malloc(sizeof(Node));
  node->show = show;
  node->prev = node->next = NULL;
  return node;
}

void append(Node** head_ref, Show* show) {
  Node* newNode = createNode(show);
  if (*head_ref == NULL) {
    *head_ref = newNode;
    return;
  }

  Node* last = *head_ref;
  while (last->next != NULL) last = last->next;

  last->next = newNode;
  newNode->prev = last;
}

Node* lastNode(Node* head) {
  while (head && head->next) head = head->next;
  return head;
}

// ========================================================================== //
//

int monthToNumber(char *month) {
  const char *months[] = {
    "January", "February", "March", "April", "May", "June",
    "July", "August", "September", "October", "November", "December"
  };
  for (int i = 0; i < 12; i++) {
    if (strcasecmp(month, months[i]) == 0)
      return i + 1;
  }

  return -1;
}

int parseDate(char *date) {
  if (date == NULL || strcmp(date, "[NaN]") == 0) {
    return -1; 
  }

  char month[20];
  int day, year;
  if (sscanf(date, "%19s %d, %d", month, &day, &year) == 3) {
    int monthNum = monthToNumber(month);
    if (monthNum == -1) return -1;
    return year * 10000 + monthNum * 100 + day;
  }

  return -1;
}

int compareUwU(Show *a, Show *b) {
  int dateA = parseDate(a->date_added);
  int dateB = parseDate(b->date_added);

  if (dateA < dateB) return -1;
  if (dateA > dateB) return 1;

  return strcmp(a->title, b->title);
}

// ========================================================================== //
//


void swap(Show** a, Show** b) {
  Show* temp = *a;
  *a = *b;
  *b = temp;
}

// lomuto
Node* partition(Node* low, Node* high) {
  Show* pivot = high->show;
  Node* i = low->prev;

  for (Node* j = low; j != high; j = j->next) {
    if (compareUwU(j->show, pivot) < 0) {
      i = (i == NULL) ? low : i->next;
      swap(&i->show, &j->show);
    }
  }

  i = (i == NULL) ? low : i->next;
  swap(&i->show, &high->show);
  return i;
}

void quickSortRec(Node* low, Node* high) {
  if (high != NULL && low != high && low != high->next) {
    Node* p = partition(low, high);
    quickSortRec(low, p->prev);
    quickSortRec(p->next, high);
  }
}

void quickSort(Node* head) {
  Node* h = lastNode(head);
  quickSortRec(head, h);
}


int main() {
  FILE *f = fopen("/tmp/disneyplus.csv", "r");
  if (f == NULL) {
    return -1;
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

  char inputId[100];
  Node* selected = NULL;
  while (fgets(inputId, sizeof(inputId), stdin)) {
    trim(inputId);

    if (strcmp(inputId, "FIM") == 0) break;

    for (int i = 0; i < count; i++) {
      if (strcmp(shows[i]->id, inputId) == 0) {
        append(&selected, shows[i]);
        break;
      }
    }
  }

  quickSort(selected); 

  for (Node* curr = selected; curr != NULL; curr = curr->next) {
    printShow(curr->show);
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

  fclose(f);
  return 0;
}
