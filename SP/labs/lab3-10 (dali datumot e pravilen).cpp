//Да се напише програма којашто за вчитан датум како влезна променлива (ДД ММ ГГГГ) ќе испечати на излез порака 1 или 0
//во зависност од тоа дали внесениот датум е правилен или не. За проверка на валидноста:
// - за датумот 29.02. да се внимава дали станува збор за престапна година (година делива со 400 или делива со 4, но не и со 100)
// - дали месецот е број помеѓу 1 и 12
// - дали денот одговара со бројот на денови во тој месец

//input               output
//1 6 1992            1
//30 2 2004           0

#include <iostream>
using namespace std;
int main(){
    int d, m, g, prestapna;
    cin >> d >> m >> g;
    prestapna = ( g % 400 == 0 || ( g % 4 == 0 && g % 100 != 0 ) );
    if ( d > 31 || m > 12 ) {
        cout << 0;
    }


    else {
        if (m >= 1 && m <= 12) {
            if (d >= 1 && d <= 31) {
                if (m == 1 || m == 3 || m == 5 || m == 7 || m == 8 || m == 10 || m == 12) {
                    cout << 1;
                } else if (( d <= 30 ) && ( m == 4 || m == 6 || m == 9 || m == 11)){
                        cout << 1;
                    }
                else if ( (d<=28 && m==2) || (d == 29 && m == 2 && prestapna == 1) ){
                    cout << 1;
                }
                else {
                    cout << 0;
                }
            }

        }

    }
    
    return 0;
}
