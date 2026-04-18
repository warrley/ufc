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

vector slice(vector *v, int start, int end) {
  start = ((start % v->size) + v->size) % v->size;
  end = ((end % v->size) + v->size) % v->size;
  
  vector view;
  view.capacity = view.size = end-start;
  view.data = v->data+start;

  return view;
}

int at(vector *v, int value) {
  for (int i = 0; i < v->size; i++) {
    if (v->data[i] == value) return i;
  }
  return -1;
}

void push(vector* v, int value) {
  if (v->capacity == 0) {
    resize(v, 1);
  } else if (v->size == v->capacity) {
    resize(v, v->capacity*2);
  }

  v->data[v->size++] = value;
}

void insert(vector *v, int index, int value) {
  verify_bounds(v, index);
  for (int i = v->size; i > index; i--) {
    v->data[i] = v->data[i-1];
  }

  v->data[index] = value;
  v->size++;
}

void set(vector *v, int index, int value) {
  insert(v, index, value);
  delete(v, index+1);
}

void delete(vector *v, int index) {
  verify_bounds(v, index);
  for (int i = index; i < v->size-1; i++) {
    v->data[i] = v->data[i+1];
  }

  v->size--;
  v->data[v->size] = 0;
}

void pop(vector *v, int index) {
  delete(v, v->size-1);
}

int get(vector *v, int index) {
  verify_bounds(v, index);
  return v->data[index];
}


void freev(vector *v) {
  free(v->data);
}

void verify_bounds(vector *v, int index) {
  if (index < 0 || index >= v->size) {
    printf("fail: index out of bounds");
    exit(1);
  }
}

void status(vector *v) {
  printf("size:%d\ncapacity:%d\n", v->size, v->capacity);
}

void printv(vector *v) {
  for (int i = 0; i < v->size; i++) {
    printf("%d ", v->data[i]);
  }
  printf("\n");
}

int main() {
  vector v = vector_init(4);

  for (int i = 0; i < 20; i++) {
    push(&v, i);
  }

  // insert(&v, 3, 0);

  printv(&v);
  status(&v);
}
