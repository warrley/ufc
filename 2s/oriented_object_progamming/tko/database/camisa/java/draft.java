import java.util.Scanner;

public class draft {
    public static void main(String args[]) {
        Roupa camisa = new Roupa();
        Scanner input = new Scanner(System.in);

        while (camisa.getTamanho() == null) {
            System.out.println("Escolha um tamanho");
            camisa.setTamanho(input.nextLine());
        }

        input.close();
    }

    static class Roupa {
        private String tamanho;

        public Roupa() {
            this.tamanho = null;
        }

        public void setTamanho(String novo) {
            if (novo.equals("PP") || novo.equals("P") || novo.equals("M") 
                || novo.equals("G") || novo.equals("GG") || novo.equals("XG")) {
                this.tamanho = novo;
            } else {
                System.out.println("Valores permitidos: PP, P, M, G, GG, XG");
            }

        }

        public String getTamanho() {
            return this.tamanho;
        }
    }
}