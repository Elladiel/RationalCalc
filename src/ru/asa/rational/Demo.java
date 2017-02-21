package ru.asa.rational;

import javax.xml.bind.ValidationException;
import java.util.Scanner;

/**
 * Created by Анастасия on 01.03.2017.
 */
public class Demo {
    static Scanner scanner = new Scanner((System.in));

    public static void main(String[] args) throws Exception {
        do {
            try {
                System.out.println("Введите выражение: ");
                String expression = scanner.nextLine();
                Expression ex = ExpressionBuilder.build(expression);
                Object result = ex.execute();
                System.out.println(expression + " = " + result.toString());
                break;
            } catch (ValidationException ex) {
                System.out.println("Выражение введено неправильно!");
            } catch (ArithmeticException ex) {
                System.out.println(ex.getMessage() + " Невозможно вычислить.");
                break;
            }

        } while (true);
    }

}
