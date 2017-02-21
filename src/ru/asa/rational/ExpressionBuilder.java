package ru.asa.rational;

import javax.xml.bind.ValidationException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Компилятор выражений.
 */
public class ExpressionBuilder {

    private String expression; // Строка с исходным выражением
    private int currentPosition = 0; // текущая позиция

    /**
     * Создает экземпляр ExpressionBuilder'a, скармилвает ему введенное выражение,
     * предварительно вырезав из него все пробелы, и запускает парсинг.
     */
    public static Expression build(String expression) throws Exception {
        return new ExpressionBuilder(expression.replaceAll(" ", "")).buildNode(0);
    }

    private ExpressionBuilder(String expression) {
        this.expression = expression;
    }

    /**
     * Построить узел выражения
     */
    Expression buildNode(int state) throws Exception {
        if (lastState(state)) {
            Expression ex;

            boolean isMinus = startWith("-");
            if (isMinus) skip("-");

            if (startWith("(")) {
                skip("(");
                ex = buildNode(0);
                skip(")");
            } else {
                ex = readSingle();
            }

            if (isMinus) {
                ex = new Expression.Unary(ex, "-");
            }

            return ex;
        }

        /* Строим первый операнд */
        Expression a1 = buildNode(state + 1);

        /*строим последущие операнды*/
        String op;
        while ((op = readStateOperator(state)) != null) {
            Expression a2 = buildNode(state + 1);
            a1 = new Expression.Binary(a1, op, a2);

        }
        return a1;
    }

    /**
     * В этом массиве знаки операций сгруппированы и расположены по приоритетам выполнения.
     * Самый низкий приоритет имеют операции сложения и вычитания.
     * Далее следуют умножение и деление.
     * И самый высокий приоритет в нашем случае будет иметь унарный минус.
     * В нашем массиве он представлен null. Да здравствуют костыли!!
     */
    private static String[][] states = new String[][]{
            {"+", "-"},
            {"*", ":"},
            null
    };

    /**
     * Возвращает TRUE, если state находится на последней позиции массива states, т.е равен null.
     * Грубо говоря, определяет, на каком уровне рекурсии мы находимся.
     */
    private boolean lastState(int s) {
        return s + 1 >= states.length;
    }

    /**
     * Возвращает TRUE, если в потоке строка <code>s</code>.
     * @param s
     */
    private boolean startWith(String s) {
        return expression.startsWith(s, currentPosition);
    }

    /**
     * С помощью этого метода мы "двигаемся" по строке. Текущая позиция изменяется на длину строки-параметра.
     *
     * @param s
     */
    private void skip(String s) {
        if (startWith(s))
            currentPosition += s.length();

    }

    /**
     * Считывает знак операции.
     *
     * @param state состояние(приоритет)
     * @return знак операции
     * @throws ValidationException
     */
    private String readStateOperator(int state) throws ValidationException {
        validate();
        String[] ops = states[state];
        for (String s : ops) {
            if (startWith(s)) {
                skip(s);
                return s;
            }
        }
        return null;
    }

    private void validate() throws ValidationException {
        boolean isValid = false;
        for (String[] state : states) {
            if (state == null) continue;

            for (String operator : state) {
                if (startWith(operator) || expression.substring(currentPosition).length() == 0) {
                    isValid = true;
                    break;
                }
            }
        }

        if (!isValid) {
            throw new ValidationException("");
        }
    }


    /**
     * Считываем из потока обыкновенную дробь, смешанную дробь или целое число.
     * Даёшь регулярочки ^__^
     * @return
     */
    private Expression readSingle() throws Exception {
        if (startWithPattern("(\\d+`)?\\d+/\\d+.*")) {
            String fraction = extractByPattern("^(\\d+`)?\\d+/\\d+");
            Rational x = parseToFraction(fraction);
            return new Expression.Fraction(x);
        } else if (startWithPattern("\\d+.*")) {
            String number = extractByPattern("^\\d+");
            int x = Integer.parseInt(number);
            return new Expression.Fraction(new Rational(x));
        }

        throw new ValidationException("");
    }

    private String extractByPattern(String pattern) {
        Pattern p = Pattern.compile(pattern);
        Matcher m = p.matcher(expression.substring(currentPosition));
        m.find();

        currentPosition += m.group().length();
        return m.group();
    }

    private Rational parseToFraction(String fraction) {
        int integerPart = 0;

        //Если дробь содержит целую часть:
        if (fraction.contains("`")) {
            String[] x = fraction.split("`");
            fraction = x[1];
            integerPart = Integer.parseInt(x[0]);
        }

        String[] numbers = fraction.split("/");

        int numerator = Integer.parseInt(numbers[0]);
        int denominator = Integer.parseInt(numbers[1]);

        if (!Rational.isValidDenominator(denominator))
            throw new ArithmeticException("Произошло деление на ноль.");

        return new Rational(integerPart,
                numerator,
                denominator);
    }

    /**
     *
     * @param pattern
     * @return
     */
    private boolean startWithPattern(String pattern) {
        return expression.substring(currentPosition).matches(pattern);
    }
}