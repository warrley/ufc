#include <stdio.h>
#include <math.h>

// https://github.com/qxcodefup/arcade/blob/master/base/vet_ii/Readme.md

int main() {
    int length;
    int items[1000];

    printf("\nHow many items? ");
    scanf("%d", &length);
    
    for(int i = 0; i < length; i++) {
        printf("\nEnter the %d item ", i + 1);
        scanf("%d", &items[i]);
    }
    printf("[");
    for(int i = 0; i < length; i++) {
        printf(" %d", items[i]);
    }
    printf(" ]");
    return 0;
}