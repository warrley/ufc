#include <stdio.h>

int main() {
    int total, type[100], species[100], size = 0;

    printf("\nEnter the number of animals: ");
    scanf("%d", &total);

    for (int i = 0; i < total; i++) {
        printf("\nEnter the type of %d anmimal: ", i + 1);
        scanf("%d", &type[i]);
    }

    for (int i = 0; i < total; i++) {
        int duplicate = 0;
        for (int j = 0; j < size; j++) {
            if(species[j] == type[i]) {
                duplicate = 1;
                break;
            }
        }
        if(!duplicate) {
            species[size] = type[i];
            size++;
        }
    }

    for (int i = 0; i < size - 1; i++) {
        for (int j = 0; j < size - 1; j++) {
            if (species[j] > species[j + 1]){
                int aux = species[j];
                species[j] = species[j + 1];
                species[j + 1] = aux;
            }
        }
    }

        for (int i = 0; i < size; i++)
        {
            printf("%d ", species[i]);
        }

    return 0;
}