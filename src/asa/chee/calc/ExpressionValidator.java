package asa.chee.calc;

import javax.xml.bind.ValidationException;
import java.util.Scanner;

/**
 * Created by Анастасия on 21.02.2017.
 */
public class ExpressionValidator {
    static Scanner scanner = new Scanner(System.in);
    public static void validateExpression(String expression) throws ValidationException {
        if(!expression.matches("(\\s*\\d+[/]\\d+\\s*[-+*:]\\s*\\d+[/]\\d+\\s*)+")){
            throw new ValidationException("Ошибка при ввода выражения.");
        }
    }

}
