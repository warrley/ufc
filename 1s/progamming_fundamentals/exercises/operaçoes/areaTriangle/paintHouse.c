#include <stdio.h>
#include <math.h>

int main() {

    double side1;
    double side2;
    double side3;

    printf("\nType a side of triangle\n");
    scanf("%lf", &side1);

    printf("\nType another side of triangle\n");
    scanf("%lf", &side2);

    printf("\nType another side of triangle\n");
    scanf("%lf", &side3);

    double p = (side1 + side2 + side3) / 2;

    double area = sqrt(p * (p-side1) * (p-side2) * (p-side3));

    printf("The triangle area is:  %.6lf", area);


    return 0;
}