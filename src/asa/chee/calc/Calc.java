package asa.chee.calc;

/**
 * Created by Анастасия on 20.02.2017.
 */

import javax.xml.bind.ValidationException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Calc {
    static Scanner scanner = new Scanner(System.in);

    public static void main(String args[]) {
        String expression = inputExpression();

        String trimExpression = expression.replaceAll(" ", "");//вырезаем пробелы, чтобы не мешались

        ArrayList<Fraction> fractions = getFraction(trimExpression);

        Fraction frac1 = fractions.get(0);
        Fraction frac2 = fractions.get(1);
        Fraction result;
        String operation = getOperation(expression);

        switch (operation) {
            case "+":
                result = frac1.add(frac2);
                break;
            case "-":
                result = frac1.sub(frac2);
                break;
            case "*":
                result = frac1.multiply(frac2);
                break;
            case ":":
                result = frac1.div(frac2);
                break;
            default:
                result = null;
        }
        System.out.println("Результат:");
        result.reduce();
        System.out.println(expression + " = " + result.toString());

    }

    public static String inputExpression() {
        do {
            try {
                System.out.print("Введите выражение: ");
                String expression = scanner.nextLine();
                ExpressionValidator.validateExpression(expression);
                return expression;
            } catch (ValidationException ex) {
                System.out.println(ex.getMessage() + " Попробуйте снова.");
            }
        } while (true);
    }

    public static String getOperation(String expression) {
        Pattern p = Pattern.compile("[-+*:]");
        Matcher m = p.matcher(expression);

        String operation = "";

        while (m.find()) {
            operation = m.group();
        }
        return operation;
    }

    public static ArrayList<Fraction> getFraction(String expression) {
        ArrayList<Fraction> fractions = new ArrayList<>();
        Pattern p = Pattern.compile("\\d+/\\d+");
        Matcher m = p.matcher(expression);
        while (m.find()) {
            String[] numbers = m.group().split("/");
            fractions.add(new Fraction(
                    Integer.parseInt(numbers[0]),
                    Integer.parseInt(numbers[1])));
        }

        return fractions;
    }

}

