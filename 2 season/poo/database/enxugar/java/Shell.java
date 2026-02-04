import java.util.Scanner;


public class Shell {
    
    public static void main(String[] a) {
//         CRIE SUA TOALHA AQUI
         Towel toalha = new Towel();
        
        while (true) {
            var line = scanner.nextLine();
            System.out.println("$" + line);

            var par = line.split(" ");
            var cmd = par[0];

            if (cmd.equals("end")) {
                break;
            }
            else if (cmd.equals("criar")) { // ATUALIZE SUA TOALHA USANDO O CONSTRUTOR
                 var cor = par[1];
                 var tamanho = par[2];
                 toalha = new Towel(cor, tamanho);
            }
            else if (cmd.equals("mostrar")) { // MOSTRE SUA TOALHA
                toalha.show();
            }
            else if (cmd.equals("enxugar")) { // ENXUGUE
                var quantidade = Integer.parseInt(par[1]);
                toalha.dry(quantidade);
            }
            else if (cmd.equals("seca")) { // OBTENHA SE ESTA SECA E IMPRIMA SIM OU NAO
                System.out.printf("%s\n", (toalha.isDry()) ? ("sim") : ("nao"));
            }
            else if (cmd.equals("torcer")) { // CHAME O METODO TORCER
                toalha.wringOut();
            }
            else {
                System.out.println("comando invalido");
            }
        }
    }

    static class Towel {
        private String color;
        private String size;
        private int wetness;

        public Towel() {

        }

        public Towel(String color, String size) {
            this.color = color;
            this.size = size;
            this.wetness = 0;
        }

        public void dry(int amount) {
            if(wetness + amount < getMaxWetness()) {
                wetness += amount;
            } else {
                System.out.println("toalha encharcada");
                wetness = getMaxWetness();
            }
        }

        public void wringOut() {
            wetness = 0;
        }

        public int getMaxWetness() {
            if(size.equals("P")) {
                return 10;
            } else if(size.equals("M")) {
                return 20;
            } else {
                return 30;
            }
        }

        public boolean isDry() {
            if(wetness <= 0) {
                return true;
            } else {
                return false;
            }
        }

//        public String toString() {
//
//        }

        public void show() {
            System.out.println("Cor: " + color + ", Tamanho: " + size + ", Umidade: " + wetness);
        }
    }

    private static Scanner scanner = new Scanner(System.in);
}
