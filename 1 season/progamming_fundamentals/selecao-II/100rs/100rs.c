#include <stdio.h>
#include <math.h>

// https://github.com/qxcodefup/arcade/blob/master/base/cortada/Readme.md

int main() {
    int total = 160 * 70;
    double marzia, felix;
    double base, top;

    printf("\nHow much of the base should you cut to the left?:  ");
    scanf("%lf", &base);
    
    printf("\nHow much of the top should you cut to the left?:  ");
    scanf("%lf", &top);

    marzia = ((160 - base) + (160 - top)) * 35;
    felix = (base + top) * 35;

    printf("\n%lf\n", marzia);
    printf("\n%lf\n", felix);

    if(felix > marzia) {
        printf("----------------------------");
        printf("\nFelix kept the 100 reais\n");
        printf("----------------------------");
    } else if(felix < marzia) {
        printf("----------------------------");
        printf("\nMarzia kept the 100 reais\n");
        printf("----------------------------");
    } else if(marzia == felix) {
        printf("----------------------------");
        printf("\nNobody kept the 100 reais\n");
        printf("----------------------------");
    }
 
    return 0;
}