package ru.asa.rational;

public class Rational {
    private int numerator;//числитель
    private int denominator;//знаменатель

    public Rational() {
        this(1, 1);
    }

    public Rational(int numerator, int denominator) {
        this.numerator = numerator;
        this.denominator = denominator;
    }

    /**
     * Конструктор для смешанной дроби.
     *
     * @param integerPart целая часть
     * @param numerator   числитель
     * @param denominator знаменатель
     */
    public Rational(int integerPart, int numerator, int denominator) {
        this.numerator = numerator + denominator * integerPart;
        this.denominator = denominator;
    }

    /**
     * Конструктор для целого числа (представляем его в виде дроби для простоты вычислений).
     *
     * @param numerator числитель
     */
    public Rational(int numerator) {
        this.numerator = numerator;
        this.denominator = 1;
    }

    public Rational add(Rational rational) {
        return new Rational(
                this.numerator * rational.denominator + rational.numerator * this.denominator,
                this.denominator * rational.denominator);
    }

    public Rational sub(Rational rational) {
        return new Rational(
                this.numerator * rational.denominator - rational.numerator * this.denominator,
                this.denominator * rational.denominator);
    }

    public Rational multiply(Rational frac2) {
        return new Rational(
                this.numerator * frac2.numerator,
                this.denominator * frac2.denominator
        );
    }

    public Rational div(Rational frac2) throws ArithmeticException {
        return this.multiply(frac2.reverse());
    }

    public Rational reverse() {
        return new Rational(
                this.denominator,
                this.numerator
        );
    }

    /**
     * Метод для сокращения дроби.
     *
     * @return сокращенная дробь
     */
    public Rational reduce() {

        int num = this.numerator;
        int denom = this.denominator;

        for (int i = denom; i >= 1; i--) {
            if (num % i == 0 && denom % i == 0) {
                num /= i;
                denom /= i;
            }
        }
        return new Rational(num, denom);
    }

    /**
     * Возвращает отрицательное значение дроби(унарный минус).
     *
     * @return
     */
    public Rational toNegative() {
        return new Rational(
                -this.numerator,
                this.denominator
        );
    }

    public static boolean isValidDenominator(int denominator){
        return (denominator != 0);
    }

    @Override
    public String toString() {
        if (denominator==1) return numerator + "";
        else if (numerator > denominator) return (numerator / denominator) + "`" + (numerator%denominator) + "/" + denominator;
        else if (numerator == denominator) return "1";
        else return numerator + "/" + denominator;
    }
}
