import java.util.Scanner;

public class draft {
    public static void main(String args[]) {
        Scanner scanner = new Scanner(System.in);
        Chinela chinela = new Chinela();

        while (chinela.getTamanho() == 0) {
            System.out.println("Escolha um tamanho");
            int tamanho = scanner.nextInt();

            chinela.setTamanho(tamanho);
        }

        System.out.println("Parabens, voce comprou uma chinela no tamanho " + chinela.getTamanho());
        scanner.close();
    }

    static class Chinela {
        private int tamanho;

        public Chinela() {
            this.tamanho = 0;
        }

        public void setTamanho(int novo) {
            if(novo % 2 == 0 && novo >= 20 && novo <= 50) {
                this.tamanho = novo;
            } else {
                System.out.println("Tamanho invalido, tente novamente");
            }
        }

        public int getTamanho() {
            return this.tamanho;
        }
    }
}