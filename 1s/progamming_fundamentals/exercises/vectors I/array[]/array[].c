#include <stdio.h>
#include <math.h>

// https://github.com/qxcodefup/arcade/blob/master/base/vet_iii/Readme.md
void printVector(int items[1000], int length) {
    if(length == 0) {
        printf("[]");
    }
    if(length > 0) {
        printf("[");
        for(int i = 0; i < length; i++) {
            if(i == 0) {
                printf("%d", items[i]);
            } else if(i != 0) {
                printf(", %d", items[i]);
            }
        }
        printf("]");
    }
}

int main() {
    int length;
    int items[1000];

    printf("\nHow many items? ");
    scanf("%d", &length);
    
    for(int i = 0; i < length; i++) {
        printf("\nEnter the %d item ", i + 1);
        scanf("%d", &items[i]);
    }

    printVector(items, length);

    return 0;
}