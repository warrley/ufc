import java.util.List;
import java.util.Locale;
import java.util.Arrays;
import java.util.Scanner;
import java.util.ArrayList;
import java.text.DecimalFormat;

class Student {

    public static int abs(int x) {
        if (x < 0) {
            return -x;
        } 
        return x;
    }

    public static int count(List<Integer> vet, int value) {
        int cont = 0;
        for (int i = 0; i < vet.size(); i++) {
            if (value == vet.get(i)) {
                cont++;
             }
        }

        return cont;
    }

    public static int sum(List<Integer> vet) {
        int soma = 0;

        for (int i = 0; i < vet.size(); i++) {
            soma += abs(vet.get(i));
        }

        return soma;
    }

    public static double average(List<Integer> vet) {
        return sum(vet) / vet.size();
    }

    public static String halfCompare(List<Integer> vet) {
        return "";
    }

    public static String moreMen(List<Integer> vet) {
        return "";
    }

    public static String sexBattle(List<Integer> vet) {
        return "";
    }
}


public class Shell {
    public static void main(String[] _args) {
        while (true) {
            String line = scanner.nextLine();
            System.out.println("$" + line);
            
            var args = line.split(" ");
            var cmd = args[0];
            
            if (cmd.equals("end")) {
                break;
            }
            else if (args[0].equals("count")) {
                int result = Student.count(strToVet(args[1]), Integer.parseInt(args[2]));
                System.out.println("" + result);
            }
            else if (args[0].equals("half_compare")) {
                String result = Student.halfCompare(strToVet(args[1]));
                System.out.println(result);
            }
            else if (args[0].equals("sex_battle")) {
                String result = Student.sexBattle(strToVet(args[1]));
                System.out.println(result);
            }
            else if (args[0].equals("sum")) {
                int result = Student.sum(strToVet(args[1]));
                System.out.println("" + result);
            }
            else if (args[0].equals("average")) {
                System.out.printf(Locale.US, ".2f\n", Student.average(strToVet(args[1])));
            }
            else if (args[0].equals("more_men")) {
                String result = Student.moreMen(strToVet(args[1]));
                System.out.println(result);
            }
            else {
                System.out.println("fail: comando invalido");
            }
        }
    }

    public static List<Integer> strToVet(String s) {
        if (s.length() == 2) return new ArrayList<>();
        List<String> parts = Arrays.asList(s.substring(1, s.length() - 1).split(","));
        List<Integer> result = new ArrayList<>();
        for (String part : parts) {
            result.add(Integer.parseInt(part.trim()));
        }
        return result;
    }

    static Scanner scanner = new Scanner(System.in);
}