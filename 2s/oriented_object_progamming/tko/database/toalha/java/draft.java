public class draft {
    public static void main(String[] args) {
        Toalha t = new Toalha("Azul", "P");
        t.mostrar();      // Azul P 0
        t.dry(5);
        t.mostrar();      // Azul P 5
        System.out.println(t.isDry()); // false
        t.dry(5);
        t.mostrar();      // Azul P 10
        t.dry(5);         // mensagem: toalha encharcada
        t.mostrar();      // Azul P 10

        t.wringOut();
        t.mostrar();      // Azul P 0

        Toalha t2 = new Toalha("Verde", "G");
        System.out.println(t2.isDry()); // true
        t2.dry(30);
        t2.mostrar();      // Verde G 30
        System.out.println(t2.isDry()); // false
        t2.dry(1);         // mensagem: toalha encharcada
    }

    // Classe Toalha dentro da mesma arquivo
    static class Toalha {
        private String cor;
        private String tamanho;
        private int umidade;

        public Toalha(String cor, String tamanho) {
            this.cor = cor;
            this.tamanho = tamanho;
            this.umidade = 0;
        }

        public int getMaxWetness() {
            switch (tamanho) {
                case "P": return 10;
                case "M": return 20;
                case "G": return 30;
                default: return 0;
            }
        }

        public void dry(int quantidade) {
            umidade += quantidade;
            if (umidade > getMaxWetness()) {
                System.out.println("toalha encharcada");
                umidade = getMaxWetness();
            }
        }

        public void wringOut() {
            umidade = 0;
        }

        public boolean isDry() {
            return umidade == 0;
        }

        public void mostrar() {
            System.out.println(this);
        }

        @Override
        public String toString() {
            return cor + " " + tamanho + " " + umidade;
        }
    }
}
