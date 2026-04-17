#include <stdio.h>
#include <stdlib.h>

typedef struct {
  int *data;
  int size;
  int capacity;
} vector;

vector vector_init(int capacity) {
  vector v;
  v.capacity = capacity;
  v.size = 0;
  v.data = malloc(capacity * sizeof(int));

  return v;
}

void resize(vector *v, int capacity) {
  if (capacity < v->size) {
    v->size = capacity;
  }

  int* new_data = malloc(capacity*sizeof(int));
  for (int i = 0; i < v->size; i++) {
    new_data[i] = v->data[i];
  }

  free(v->data);
  v->capacity = capacity;
  v->data = new_data;
}

//TODO verification
void push(vector* v, int value) {

  if (v->capacity == 0) {
    resize(v, 1);
  } else if (v->size == v->capacity) {
    resize(v, v->capacity*2);
  }

  v->data[v->size++] = value;
}

void printv(vector* v) {
  for (int i = 0; i < v->size; i++) {
    printf("%d ", v->data[i]);
  }
}

int main() {
  vector v = vector_init(4);

  for (int i = 0; i < 20; i++) {
    push(&v, i);
  }

  printv(&v);
}
