#include <pthread.h>
#include <semaphore.h>
#include <stdio.h>
#include <stdlib.h>
#include <time.h>
#include <unistd.h>

int buffer[5];
int in = 0;
int out = 0;
int count = 0;

sem_t sem_empty;
sem_t sem_full;
pthread_mutex_t mutex_buffer;

int producers_done = 0;
pthread_mutex_t mutex_producers;
pthread_cond_t cond_finished;

void *producer(void *args) {
  long tid = (long)args;

  for (int i = 0; i < 20; i++) {
    int value = (rand() % 1000) + 1; // Valores de 1 a 1000 reais

    sem_wait(&sem_empty);              // Espera ter espaço vazio
    pthread_mutex_lock(&mutex_buffer); // Protege o buffer

    buffer[in] = value;
    in = (in + 1) % 5;
    count++;

    printf("(P) TID: %ld | VALOR: R$ %d | ITERACAO: %d\n", tid, value, i + 1);

    if (count == 5) {
      sem_post(&sem_full);
    }

    pthread_mutex_unlock(&mutex_buffer);

    int delay = (rand() % 5) + 1;
    sleep(delay);
  }

  pthread_mutex_lock(&mutex_producers);
  producers_done++;
  if (producers_done == 6) {
    pthread_cond_broadcast(&cond_finished);

    sem_post(&sem_full);
    sem_post(&sem_full);
  }
  pthread_mutex_unlock(&mutex_producers);

  printf("(P) TID: %ld finalizou\n", tid);
  return NULL;
}

void *consumer(void *args) {
  long tid = (long)args;
  int iter = 1;

  while (1) {
    sem_wait(&sem_full);

    pthread_mutex_lock(&mutex_buffer);

    // vazio
    if (producers_done == 6 && count == 0) {
      pthread_mutex_unlock(&mutex_buffer);
      sem_post(&sem_full);
      // consumidora
      break;
    }

    // pega os 5
    if (count == 5) {
      int sum = 0;
      for (int i = 0; i < 5; i++) {
        sum += buffer[out];
        out = (out + 1) % 5;
      }
      printf("(C) TID: %ld | MEDIA: R$ %d | ITERACAO: %d\n", tid, sum / 5,
             iter++);
      count = 0;

      // libera
      for (int i = 0; i < 5; i++) {
        sem_post(&sem_empty);
      }
    }
    pthread_mutex_unlock(&mutex_buffer);
  }

  pthread_mutex_lock(&mutex_producers);
  while (producers_done < 6) {
    pthread_cond_wait(&cond_finished, &mutex_producers);
  }
  pthread_mutex_unlock(&mutex_producers);

  printf("(C) TID: %ld finalizou\n", tid);
  return NULL;
}

int main(void) {
  srand(time(NULL));

  sem_init(&sem_empty, 0, 5); // Começa com 5 espaços vazios
  sem_init(&sem_full, 0, 0);  // Começa com 0 itens cheios
  pthread_mutex_init(&mutex_buffer, NULL);

  pthread_mutex_init(&mutex_producers, NULL);
  pthread_cond_init(&cond_finished, NULL);

  pthread_t prod[6];
  pthread_t cons[2];

  // cria
  for (long i = 0; i < 6; i++) {
    pthread_create(&prod[i], NULL, producer, (void *)(1000 + i));
  }

  for (long i = 0; i < 2; i++) {
    pthread_create(&cons[i], NULL, consumer, (void *)(2000 + i));
  }

  for (int i = 0; i < 6; i++) {
    pthread_join(prod[i], NULL);
  }
  for (int i = 0; i < 2; i++) {
    pthread_join(cons[i], NULL);
  }

  return 0;
}
