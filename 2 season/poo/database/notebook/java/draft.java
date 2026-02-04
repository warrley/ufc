public class draft {
    public static void main(String args[]) {
        Notebook notebook = new Notebook();

        notebook.mostrar();    // sem bateria
        notebook.usar(10);
        notebook.ligar();
        notebook.mostrar();

        Bateria bateria = new Bateria(50); // cria bateria
        bateria.mostrar();

        notebook.setBateria(bateria);
        notebook.mostrar();
        notebook.usar(10);

        notebook.ligar();
        notebook.mostrar();

        notebook.usar(30);
        notebook.mostrar();

        notebook.usar(30);
        notebook.mostrar();

        bateria = notebook.rmBateria();
        bateria.mostrar();   
        notebook.mostrar();

        Carregador carregador = new Carregador(2);
        carregador.mostrar();

        notebook.setCarregaor(carregador);
        notebook.mostrar();
        notebook.ligar();
        notebook.mostrar();
        
        notebook.setBateria(bateria);
        notebook.mostrar();
        notebook.usar(10);
        notebook.mostrar();
    }

    static class Notebook {
        private boolean ligado;
        private Bateria bateria;
        private Carregador carregador;

        public Notebook() {
            this.ligado = false;
            this.bateria = null;
        }

        public void ligar() {
            if((this.bateria == null || this.bateria.getCarga() <= 0) && this.carregador == null) {
                System.out.println("não foi possível ligar");
                return;
            }
            this.ligado = true;
            System.out.println("notebook ligado");
        }
        
        public void desligar() {
            this.ligado = false;
            System.out.println("notebook desligando");
        }

        public void mostrar() {
            String status = this.ligado ? "Ligado" : "Desligado";
            String bateria = (this.bateria == null) ? "Nenhuma" : this.bateria.toString();
            String carregador = (this.carregador == null) ? "Desconectado" : "(Potência " + this.carregador.getPotencia() + ")" ;
            System.out.println("Status: " + status + ", Bateria: " + bateria + ", Carregador: " + carregador);
        }

        public void usar(int minutes) {
            int usage = 0;
            if(!this.ligado) {
                System.out.println("erro: ligue o notebook primeiro");
            } else {
                if(this.carregador != null) {
                    this.bateria.carregar(minutes, this.carregador.getPotencia());
                } else {
                    if(this.bateria.carga - minutes < 0) {
                        usage = this.bateria.carga;
                        this.bateria.carga = 0;
                    } else {
                        usage = minutes;
                        this.bateria.carga -= usage;
                    }
                }
                System.out.println("Usando por " + usage + " minutos, " + ((this.bateria.getCarga() - minutes < 0) ? "notebook descarregou" : ""));
            }
        }

        public void setBateria(Bateria bateria) {
            this.bateria = bateria;
            System.out.println("Bateria inserida");
        }

        public Bateria rmBateria(){
            Bateria tmp = this.bateria;
            this.bateria = null;
            System.out.println("bateria removida");
            return tmp;
        }

        public void setCarregaor(Carregador carregador) {
            this.carregador = carregador;
            System.out.println("adicionando carregador no notebook");
        }
    }

    static class Bateria {
        private int carga;
        private int capacidade;

        public Bateria(int capacidade) {
            this.capacidade = capacidade;
            this.carga = capacidade;
            System.out.println("criando bateria que suporta 50 minutos e começa carregada");
        }
        
        public int getCarga() {
            return carga;
        }
        
        public int getCapacidade() {
            return capacidade;
        }
        
        public void carregar(int minutes, int potencia) {
            this.carga = minutes * potencia;
        }
        public void mostrar() {
            System.out.println(this.toString());
        }

        @Override
        public String toString() {
            return "(" + this.carga + "/" + this.capacidade + ")";
        }
    }

    static class Carregador {
        private int potencia;

        public Carregador (int potencia) {
            this.potencia = potencia;
        }

        public int getPotencia() {
            return this.potencia;
        }

        public void mostrar() {
            System.out.println("Potencia " + this.potencia);
        }
    }
}

