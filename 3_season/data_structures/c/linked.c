#include <stdio.h>
#include <stdlib.h>

typedef struct node node;

struct node {
  int data;
  node *previous;
  node *next;
};

node *pushFront(node *head, int data) {
  node *n = malloc(sizeof(node));
  n->data = data;
  n->next = head;
  n->previous = NULL;

  if (head != NULL) {
    head->previous = n;
  }

  return n;
}

node *pushBack(node *tail, int data) {
  node *n = malloc(sizeof(node));
  n->data = data;
  n->previous = tail;
  n->next = NULL;
}

void print(node *l) {
  node *current = l;
  while (current != NULL) {
    printf("%d ", current->data);
    current = current->next;
  }
}

int main() {
  node *linkedlist = NULL;

  linkedlist = pushFront(linkedlist, 7);
  linkedlist = pushFront(linkedlist, 10);
  linkedlist = pushFront(linkedlist, 3);

  print(linkedlist);
}
