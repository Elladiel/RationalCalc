package ru.asa.rational;

import javax.xml.bind.ValidationException;
import java.io.*;

public class Demo {
    public static void main(String[] args) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("input.txt"));
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("output.txt"));
        String expression;
        do {
            try {
                System.out.println("Считываем выражение.");
                while((expression = bufferedReader.readLine()) != null){
                Expression ex = ExpressionBuilder.build(expression);
                Object result = ex.execute();
                String res = result.toString();
                System.out.println("Записываем результат в файл.");
                bufferedWriter.write(res);
                bufferedWriter.newLine();
                }
                bufferedWriter.close();
                break;
            } catch (ValidationException ex) {
                System.out.println(ex.getMessage() + "Встречено некорректное выражение.");
            } catch (ArithmeticException ex) {
                System.out.println(ex.getMessage() + " Невозможно вычислить.");
            }

        } while (true);
    }

}
