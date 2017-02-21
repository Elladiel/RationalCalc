package asa.chee.calc;

/**
 * Created by Анастасия on 20.02.2017.
 */
public class Fraction {

    private int numerator;//числитель
    private int denumerator;//знаменатель

    public Fraction(){
        this(1,1);
    }

    public Fraction(int numerator, int denumerator) {
        this.numerator = numerator;
        this.denumerator = denumerator;
    }

    public Fraction(int numerator){
        this.numerator = numerator;
        this.denumerator = 1;
    }

    public Fraction add(Fraction frac2) {
        return new Fraction(
                this.numerator * frac2.denumerator + frac2.numerator * this.denumerator,
                this.denumerator * frac2.denumerator);
    }

    public Fraction sub(Fraction frac2) {
        return new Fraction(
                this.numerator * frac2.denumerator - frac2.numerator * this.denumerator,
                this.denumerator * frac2.denumerator);
    }

    public Fraction multiply(Fraction frac2) {
        return new Fraction(
                this.numerator * frac2.numerator,
                this.denumerator * frac2.denumerator
        );
    }

    public Fraction div(Fraction frac2) throws ArithmeticException {
            return this.multiply(frac2.reverse());
    }



    public Fraction reverse() {
        return new Fraction(
                this.denumerator,
                this.numerator
        );
    }

    public void reduce(){
        for (int i = 2; i < denumerator ; i++) {
            if(numerator%i==0&&denumerator%i==0){
                numerator/=i;
                denumerator/=i;
            }
        }
    }

    @Override
    public String toString() {
        return numerator +
                "/" + denumerator;
    }
}
