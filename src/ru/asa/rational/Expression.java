package ru.asa.rational;

/**
 * Вычислимое Выражение
 */
public abstract class Expression {

    /**
     * Вычислить выражение для даных значений переменных
     */
    public abstract Object execute() throws Exception;


    /**
     * Узел дерева - "Дробное число"
     */

    static class Fraction extends Expression {
        private final Rational value;

        public Fraction(Rational x) {
            value = x;
        }

        @Override
        public Object execute() {
            return value;
        }
    }


    /**
     * Узел дерева — "Унарный оператор"
     */

    static class Unary extends Expression {
        private final Expression expr;
        private final String operator;

        public Unary(Expression e, String operator) {
            expr = e;
            this.operator = operator;

        }

        @Override
        public Object execute() throws Exception {
            Rational o = (Rational) expr.execute();
            if (operator.equals("-")) {
                return o.toNegative();
            } else throw new Exception("Неподдерживаемая унарная операция: " + operator);
        }
    }


    /**
     * Узел дерева — "Бинарный оператор"
     */
    static class Binary extends Expression {
        private final Expression x1, x2;
        private final String operator;

        public Binary(Expression x1, String operator, Expression x2) throws Exception {
            this.x1 = x1;
            this.x2 = x2;
            this.operator = operator;
        }

        @Override
        public Object execute() throws Exception {
            Rational
                    leftExpr = (Rational) x1.execute(),
                    rightExpr = (Rational) x2.execute();
            Rational result;
            switch (this.operator) {
                case "+":
                    result = leftExpr.add(rightExpr);
                    break;
                case "-":
                    result = leftExpr.sub(rightExpr);
                    break;
                case "*":
                    result = leftExpr.multiply(rightExpr);
                    break;
                case ":":
                    result = leftExpr.div(rightExpr);
                    break;
                default:
                    throw new Exception("Неподдерживаемая бинарная операция: " + operator);
            }
            return result.reduce();
        }
    }
}

