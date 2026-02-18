package database.salario;
import java.util.*;

public class Solver {
    public static void main(String[] arg) {
        UFC ufc = new UFC();

        while (true) {
            String line = input();
            System.out.println("$" + line);
            String[] args = line.split(" ");

            try {
                if      (args[0].equals("end"))          { break; }
                else if (args[0].equals("addProf"))      { ufc.addFuncionario(new Professor(args[1], args[2])); }
                else if (args[0].equals("addSta"))       { ufc.addFuncionario(new STA(args[1], Integer.parseInt(args[2]))); }
                else if (args[0].equals("addTer"))       { ufc.addFuncionario(new Terceirizado(args[1], Integer.parseInt(args[2]), args[3])); }
                else if (args[0].equals("rm"))           { ufc.rmFuncionario(args[1]); }
                else if (args[0].equals("showAll"))      { System.out.println(ufc); }
                else if (args[0].equals("show"))         { System.out.println(ufc.getFuncionario(args[1])); }
                else if (args[0].equals("addDiaria"))    { ufc.getFuncionario(args[1]).addDiaria(); }
                else if (args[0].equals("setBonus"))     { ufc.setBonus(Integer.parseInt(args[1])); }
                else                                     { System.out.println("fail: comando invalido"); }
            } catch (MsgException me) {
                System.out.println(me.getMessage());
                // e.printStackTrace(System.out);
            }
        }
    }

    private static Scanner scanner = new Scanner(System.in);
    private static String  input()                { return scanner.nextLine();        }
}
