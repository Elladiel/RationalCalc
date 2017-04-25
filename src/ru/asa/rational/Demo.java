package ru.asa.rational;

import javax.xml.bind.ValidationException;
import java.io.*;

public class Demo {
    public static void main(String[] args) throws Exception {
        BufferedWriter writer = new BufferedWriter(new FileWriter("output.txt"));
        BufferedWriter writerError = new BufferedWriter((new FileWriter("error.txt")));
        String expression = "";
        int counter = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader("input.txt"))) {
            System.out.println("Считываем выражение.");
            while ((expression = reader.readLine()) != null) {
                try {
                    counter++;
                    Expression ex = ExpressionBuilder.build(expression);
                    Object result = ex.execute();
                    String res = result.toString();
                    System.out.println("Записываем результат в файл.");
                    writer.write(res);
                    writer.newLine();
                    writer.flush();
                } catch (ValidationException ex) {
                    System.out.println("Встречено некорректное выражение.");
                    writerError.write(counter + "  " + expression);
                    writerError.newLine();
                    writerError.flush();
                } catch (ArithmeticException ex) {
                    System.out.println(ex.getMessage() + " Невозможно вычислить.");
                    writerError.write(counter + "  " + expression);
                    writerError.newLine();
                    writerError.flush();
                }

            }

        } catch (FileNotFoundException ex) {
            System.err.println("Не удается найти указанный файл.");
            System.exit(0);
        }
        writer.close();
        writerError.close();
    }

}
