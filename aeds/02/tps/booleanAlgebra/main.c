#include <stdio.h>
#include <stdbool.h>
#include <string.h>
#include <ctype.h>
#include <stdlib.h>

#define MAX_SIZE 100
#define MAX_LEN 10241

// gcc -o ../bin/a main.c && ../bin/a 

typedef struct {
    char* arr[MAX_SIZE];
    int top;
} Stack;

void initializeStack(Stack *stack) { stack->top = -1; }
bool isEmpty(Stack *stack) { return stack->top == -1; }
bool isFull(Stack *stack) { return stack->top == MAX_SIZE - 1; }

void push(Stack *stack, char* value) {
    if(isFull(stack)) { return; }
    stack->arr[++stack->top] = value;
}

char* pop(Stack *stack) {
    if(isEmpty(stack)) { return NULL; }
    char *popped = stack->arr[stack->top];
    stack->top--;
    return popped;
}

char* peek(Stack *stack) {
    if(isEmpty(stack)) { return NULL; }
    return stack->arr[stack->top];
}

void remove_newline_and_remove_white_space(char* s) {
    int len = 0;
    while (s[len] != '\0') {
        if (s[len] == ' ' && !isdigit(s[len - 1])) s[len] = '#';
        if (s[len] == '\n' || s[len] == '\r') {
            s[len] = '\0';
            break;
        }
        len++;
    }
}

void replaceVariable(char *expression, char *variable, int value) {
    char temp[MAX_LEN];
    char *pos;
    int varLen = strlen(variable);
    int valLen = snprintf(NULL, 0, "%d", value); // todo oq é isso porra
    while((pos = strstr(expression, variable)) != NULL) {
        strncpy(temp, expression, pos - expression);
        temp[pos - expression] = '\0';
        snprintf(temp + (pos - expression), sizeof(temp) - (pos - expression), "%d", value);
        strcat(temp, pos + valLen); 
        strcpy(expression, temp);
    }
}

char* getExpression(char *statement) {
    char *firstPart = strtok(statement, " ");
    char *remaining = strtok(NULL, "");

    if (firstPart && remaining) {
        int numInputs = atoi(firstPart);
        int values[numInputs]; 
        char *token = strtok(remaining, " ");
        for (int i = 0; i < numInputs; i++) {
            if (token != NULL) {
                values[i] = atoi(token);
                token = strtok(NULL, " ");
            }
        }
        char *expression = token ? token : "";
        const char *variables[] = {"A", "B", "C", "D"};
        for (int i = 0; i < numInputs; i++) {
            char expCopy[MAX_LEN];
            strcpy(expCopy, expression);
            replaceVariable(expCopy, (char *)variables[i], values[i]);
            strcpy(expression, expCopy);
        }
        return expression;
    }

    return NULL;
}

char** tokenizeExpression(char *expression, int *numTokens) {
    int len = strlen(expression);
    int tokenCount = 0;
    char token[MAX_LEN];
    int tokenIndex = 0;

    char **tokens = malloc(MAX_LEN * sizeof(char*));
    if (tokens == NULL) {
        fprintf(stderr, "Memory allocation failed!\n");
        exit(1);
    }

    for (int i = 0; i < len; i++) {
        char ch = expression[i];
        if (ch == '0' || ch == '1') {
            token[tokenIndex++] = ch;
            token[tokenIndex] = '\0';
            tokens[tokenCount] = malloc((tokenIndex + 1) * sizeof(char));
            strcpy(tokens[tokenCount], token);
            tokenCount++;
            tokenIndex = 0;
            continue;
        }

        if (ch == 'a' || ch == 'o' || ch == 'n') {
            if (strncmp(&expression[i], "and", 3) == 0) {
                tokens[tokenCount] = malloc(4 * sizeof(char));
                strcpy(tokens[tokenCount], "and");
                tokenCount++;
                i += 2;
                continue;
            } else if (strncmp(&expression[i], "or", 2) == 0) {
                tokens[tokenCount] = malloc(3 * sizeof(char));
                strcpy(tokens[tokenCount], "or");
                tokenCount++;
                i += 1;
                continue;
            } else if (strncmp(&expression[i], "not", 3) == 0) {
                tokens[tokenCount] = malloc(4 * sizeof(char));
                strcpy(tokens[tokenCount], "not");
                tokenCount++;
                i += 2;
                continue;
            }
        }

        if (ch == ',' || ch == '#') {
            continue;
        }

        if (ch == '(' || ch == ')') {
            tokens[tokenCount] = malloc(2 * sizeof(char));
            tokens[tokenCount][0] = ch;
            tokens[tokenCount][1] = '\0';
            tokenCount++;
            continue;
        }
    }

    *numTokens = tokenCount;

    return tokens;
}

void freeTokens(char **tokens, int numTokens) {
    for (int i = 0; i < numTokens; i++) {
        free(tokens[i]);
    }
    free(tokens);
}

void shuntingYard(char *tokens) {

}

void and(bool *xs, int n) {
    for (int i = 0; i < n; i++)
        if (!xs[i]) return false;
    return true;
}

void or(bool *xs, int n) {
    for (int i = 0; i < n; i++)
        if (xs[i]) return true;
    return false;
}

void not(bool *x) {
    return !x[0];
}

int main() {
    char statement[MAX_LEN];
    int numTokens;

    while (fgets(statement, sizeof(statement), stdin)) {
        remove_newline_and_remove_white_space(statement);
        if (strcmp(statement, "FIM") == 0) break;

        char *expression = getExpression(statement);

        char **tokens = tokenizeExpression(expression, &numTokens);

        printf("Tokens:\n");
        for (int i = 0; i < numTokens; i++) {
            printf("Token %d: %s\n", i + 1, tokens[i]);
        }

        freeTokens(tokens, numTokens);
    }
    return 0;
}