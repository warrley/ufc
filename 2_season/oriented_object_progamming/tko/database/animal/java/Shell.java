import java.util.Scanner;

public class Shell {

    public static void main(String[] _args) {
        Animal animal = new Animal("", "");

        while (true) {
            var line = scanner.nextLine();
            System.out.println("$" + line);

            var par = line.split(" ");
            var cmd = par[0];

            if (cmd.equals("end")  ) {
                break;
            }
            else if (cmd.equals("init") ) {
                animal = new Animal(par[1], par[2]);
            }
            else if (cmd.equals("show")) {
                System.out.println(animal);
            }
            else if (cmd.equals("noise")) {
                System.out.println(animal.fazerBarulho());
            }
            else if (cmd.equals("grow")) {
                var increment = Integer.parseInt(par[1]);
                animal.envelhecer(increment);
            }
            else {
                System.out.println("fail: comando invalido\n");
            }
        }
    }

    static class Animal {
        private String especie;
        private String barulho;
        private int estagio;

        public Animal(String especie, String barulho) {
            this.especie = especie;
            this.barulho = barulho;
        }

        public String fazerBarulho() {
            if(this.estagio == 0){
                return "---";
            }
            if(this.estagio == 4){
                return "RIP";
            }
            return this.barulho;
        }
        public void envelhecer(int value) {
            if(this.estagio + value >= 4){
                this.estagio = 4;
                System.out.println("warning: " + this.especie + " morreu");
                return;
            }
            this.estagio += value;
        }

        public String toString() {
            return this.especie + ":" + this.estagio + ":" + this.barulho;
        }
    }

    static Scanner scanner = new Scanner(System.in);
}
