import java.util.*;

public class Shell {

    public static void main(String[] args) {
        Calc calculator = new Calc(0);

        while (true) {
            var line = scanner.nextLine();
            System.out.println("$" + line);

            var par = line.split(" ");
            var cmd = par[0];

            if (cmd.equals("end")) {
                break;
            }
            else if (cmd.equals("init")) {
                int batteryMax = Integer.parseInt(par[1]);
                calculator = new Calc(batteryMax);
            }
            else if (cmd.equals("show")) {
                System.out.println(calculator.toString());
            }
            else if (cmd.equals("charge")) {
                int value = Integer.parseInt(par[1]);
                calculator.addCharge(value);
            }
            else if (cmd.equals("sum")) {
                int a = Integer.parseInt(par[1]);
                int b = Integer.parseInt(par[2]);
                calculator.sum(a, b);
            }
            else if (cmd.equals("div")) {
                int num = Integer.parseInt(par[1]);
                int den = Integer.parseInt(par[2]);
                calculator.div(num, den);
            }
            else {
                System.out.println("fail: comando invalido");
            }
        }
    }

    static class Calc {
        private int battery;
        private int batteryMax;
        private double display;

        public Calc(int batteryMax) {
            this.batteryMax = batteryMax;
            this.battery = 0;       // começa descarregada
            this.display = 0.0;
        }

        @Override
        public String toString() {
            // força usar ponto como separador decimal
            return String.format(Locale.US, "display = %.2f, battery = %d", display, battery);
        }

        public void addCharge(int increment) {
            battery += increment;
            if (battery > batteryMax) {
                battery = batteryMax;
            }
        }

        public void sum(int a, int b) {
            if (battery == 0) {
                System.out.println("fail: bateria insuficiente");
                return;
            }
            display = a + b;
            battery--; // gasta 1 de bateria
        }

        public void div(int a, int b) {
            if (battery == 0) {
                System.out.println("fail: bateria insuficiente");
                return;
            }
            if (b == 0) {
                System.out.println("fail: divisao por zero");
                return;
            }
            display = (double) a / b;
            battery--; // gasta 1 de bateria
        }
    }

    private static Scanner scanner = new Scanner(System.in);
}
