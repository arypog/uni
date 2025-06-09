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

// goat helpers
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

// whatahell
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

void sort(char** a, int n) {
  for (int i = 0; i < n - 1; i++) {
    for (int j = 0; j < n - i - 1; j++) {
      if (strcmp(a[j], a[j + 1]) > 0) {
        char* tmp = a[j];
        a[j] = a[j + 1];
        a[j + 1] = tmp;
      }
    }
  }
}

void printArray(char** a, int n) {
  printf("[");
  for (int i = 0; i < n; i++) {
    printf("%s", a[i]);
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

void sort2(Show *kaka, int n) {
  for (int i = 0; i < n - 1; i++) {
    for (int j = i; j < n - i - 1; j++) {
      if (strcmp(kaka[j].title, kaka[j + 1].title) > 0) {
        Show temp = kaka[j];
        kaka[j] = kaka[j + 1];
        kaka[j + 1] = temp;
      }
    }
  }
}

int getMinIdx(Show *kaka, int start, int end) {
  int min = start;
  for (int i = start + 1; i < end; i++) {
    if (strcmp(kaka[i].title, kaka[min].title) < 0) {
      min = i;
    }
  }
  return min;
}

void sort3r(Show *kaka, int s, int n) {
  if (s >= n - 1) return;

  int min = getMinIdx(kaka, s, n);
  Show temp = kaka[s];
  kaka[s] = kaka[min];
  kaka[min] = temp;

  sort3r(kaka, s + 1, n);
}

int b_search(Show *kaka, int n, char* target) {
  int left = 0, right = n - 1;

  while (left <= right) {
    int mid = left + (right - left) / 2;

    if (strcmp(kaka[mid].title, target) == 0)
      return 1;
    else if (strcmp(kaka[mid].title, target) < 0)
      left = mid + 1;
    else 
      right = mid - 1;
  }
  return 0;
}

void sort4(Show *kaka, int n) {
  for (int gap = n / 2; gap > 0; gap /= 2) {
    for (int i = gap; i < n; i++) {
      Show temp = kaka[i];
      int j = i;

      while (j >= gap) {
        int cmp = strcmp(kaka[j - gap].type, temp.type);
        if (cmp > 0 || (cmp == 0 && strcmp(kaka[j - gap].title, temp.title) > 0)) {
          kaka[j] = kaka[j - gap];
          j -= gap;
        } else {
          break;
        }
      }

      kaka[j] = temp;
    }
  }
}

// my sin forbeing lazy
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

void qsortUwU(Show *awaray, int leweft, int riwigth) {
  if (leweft >= riwigth) return;

  int piwiotIndex = leweft + (riwigth - leweft) / 2;
  Show piwiot = awaray[piwiotIndex];

  int i = leweft;
  int j = riwigth;

  while (i <= j) {
    while (compareUwU(&awaray[i], &piwiot) < 0) i++;
    while (compareUwU(&awaray[j], &piwiot) > 0) j--;

    if (i <= j) {
      Show temp = awaray[i];
      awaray[i] = awaray[j];
      awaray[j] = temp;
      i++;
      j--;
    }
  }

  if (leweft < j) qsortUwU(awaray, leweft, j);
  if (i < riwigth) qsortUwU(awaray, i, riwigth);
}

void boogiepop(Show *a, int n) {
  for (int i = 0; i < n - 1; i++) {
    for (int j = 0; j < n - i - 1; j++) {
      if(compareUwU(&a[j], &a[j + 1]) > 0) {
        Show temp = a[j];
        a[j] = a[j + 1];
        a[j + 1] = temp;
      }
    }
  }
}

int compareOwO(Show *a, Show *b) {
  if (a->release_year < b->release_year) return -1;
  if (a->release_year > b->release_year) return 1;
  return strcmp(a->title, b->title); 
}

void countingSort(Show *a, int n, int exp) {
  Show *output = (Show *)malloc(n * sizeof(Show));
  int count[10] = {0};

  for (int i = 0; i < n; i++)
    count[(a[i].release_year / exp) % 10]++;

  for (int i = 1; i < 10; i++)
    count[i] += count[i - 1];

  for (int i = n - 1; i >= 0; i--) {
    int idx = (a[i].release_year / exp) % 10;
    int pos = --count[idx];
    output[pos] = a[i];
  }

  for (int i = 0; i < n; i++)
    a[i] = output[i];

  free(output);
}

int getMaxYear(Show *a, int n) {
    int max = a[0].release_year;
    for (int i = 1; i < n; i++) {
        if (a[i].release_year > max)
            max = a[i].release_year;
    }
    return max;
}

/* ordena o array do menor digito para o maior ie a * 1 -> a * 10 -> a * 100
 * entao o array 211, 120, 115 seria ordenado primeiro usando o ultimo digito
 * dps o n-1 ate o 0 exemplo no i = 1 -> 120, 211, 115; i = 10 -> 211, 115, 120
 * ;i = 100 -> 115, 120, 211. o array esta ordenado
 */
void radixsort(Show *a, int n) {
  int max = getMaxYear(a, n);
  for (int exp = 1; max / exp > 0; exp *= 10)
    countingSort(a, n, exp);

  for (int i = 0; i < n - 1; i++) {
    for (int j = 0; j < n - i - 1; j++) {
      if (a[j].release_year == a[j + 1].release_year &&
        strcmp(a[j].title, a[j + 1].title) > 0) {
        Show temp = a[j];
        a[j] = a[j + 1];
        a[j + 1] = temp;
      }
    }
  }
}

int main() {
  FILE *f = fopen("/tmp/disneyplus.csv", "r");
//  FILE *f = fopen("202/disneyplus.csv", "r");
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

    for (int i = 0; i < MAX_FIELDS; i++) free(parts[i]); // frezzaria
    count++;
  }

  // LAZY WAYs
  char inputId[100];
  Show lol[500];
  int total = 0;
  while (fgets(inputId, sizeof(inputId), stdin)) {
    trim(inputId);

    if (strcmp(inputId, "FIM") == 0) break;

    for (int i = 0; i < count; i++) {
      if (strcmp(shows[i]->id, inputId) == 0) {
        lol[total++] = *shows[i];
        break;
      }
    }
  }

//  sort2(lol, total);
    radixsort(lol, total);

  for (int i = 0; i < total; i++) {
    printShow(&lol[i]);
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
