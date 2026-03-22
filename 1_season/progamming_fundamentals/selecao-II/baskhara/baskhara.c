#include <stdio.h>
#include <math.h>

// https://github.com/qxcodefup/arcade/blob/master/base/bhaskara/Readme.md

int main() {
    double a, b, c;
    double delta;
    double res1, res2;

    printf("\nEnter the value of A:  ");
    scanf("%lf", &a);

    printf("\nEnter the value of B:  ");
    scanf("%lf", &b);

    printf("\nEnter the value of C:  ");
    scanf("%lf", &c);

    delta = b * b - 4 * a * c;
    res1 = ((-b + sqrt(delta)) / 2 * a);
    res2 = ((-b - sqrt(delta)) / 2 * a);

    if(delta > 0) {
        printf("\nThe value of x1 is: %.2lf", res1);
        printf("\nThe value of x2 is: %.2lf", res2);
    } else if(delta == 0) {
        printf("\nThe value of x is: %.2lf", res1);
    } else if(delta < 0) {
        printf("\nHas no real value");
    }

    return 0;
}