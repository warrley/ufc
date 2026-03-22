import java.util.*;

public class Shell {
    
    public static void main(String[] a) {

        Carro carro = new Carro(0, 2, 0, 100, 0);

        while (true) {
            var line = scanner.nextLine();
            System.out.println("$" + line);

            var par = line.split(" ");
            var cmd = par[0];

            if (cmd.equals("end")) {
                break;
            }
            else if (cmd.equals("show")) {
                System.out.println(carro.toString());
            }
            else if (cmd.equals("enter")) {
                carro.oneMore();
            }
            else if (cmd.equals("leave")) {
                carro.oneLess();
            }
            else if (cmd.equals("fuel")) {
                 int increment = Integer.parseInt(par[1]);
                carro.fuel(increment);
            }
            else if (cmd.equals("drive")) {
                 int distance = Integer.parseInt(par[1]);
                 carro.drive(distance);
            }  
            else {
                System.out.println("fail: comando invalido");
            }
        }   
    }

    static class Carro {
        private int pass;
        private int passMax;
        private int gas;
        private int gasMax;
        private int km;

        public Carro(int pass, int passMax, int gas, int gasMax, int km) {
            this.pass = pass;
            this.passMax = passMax;
            this.gas = gas;
            this.gasMax = gasMax;
            this.km = km;
        }

        public String toString() {
            return  "pass: " + + pass + ", " + "gas: " + + gas + ", " + "km: " + km;
        }

        public void oneMore() {
            if(pass + 1 > passMax) {
                System.out.println("fail: limite de pessoas atingido");
            } else {
                pass++;
            }
        }

        public void oneLess() {
            if(pass - 1 < 0) {
                System.out.println("fail: nao ha ninguem no carro");
            } else {
                pass--;
            }
        }

        public void fuel(int gasQt) {
            gas += gasQt;
            if (gas > gasMax) {
                gas = gasMax;
            }
        }

        public void drive(int kmQt) {
            if(pass <= 0) {
                System.out.println("fail: nao ha ninguem no carro");
                return;
            }
            if(gas <= 0) {
                System.out.println("fail: tanque vazio");
                return;
            }

            if(kmQt <= gas) {
                gas -= kmQt;
                km += kmQt;
            } else {
                km += gas;
                System.out.println("fail: tanque vazio apos andar " + gas + " km");
                gas = 0;
            }
        }
    }
    private static Scanner scanner = new Scanner(System.in);
}
