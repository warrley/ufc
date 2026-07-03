import java.util.Scanner;


public class Shell {
    public static void main(String[] _args) {
        Notebook notebook = new Notebook();

        while (true) {
            var line = scanner.nextLine();
            System.out.println("$" + line);

            var par = line.split(" ");
            var cmd = par[0];

            if(cmd.equals("end")) {
                break;
            }
            else if (cmd.equals("show")) { 
                System.out.println(notebook.toString());
            }
            else if(cmd.equals("turn_on")) { 
                notebook.ligar();
            }
            else if(cmd.equals("turn_off")) { 
                notebook.desligar();
            }
            else if(cmd.equals("use")) { 
                var minutes = Integer.parseInt(par[1]);
                notebook.usar(minutes);
            }
            else if(cmd.equals("set_battery")) {
                // CRIE UM OBJETO BATERIA E ATRIBUA AO NOTEBOOK
                var capacity = Integer.parseInt(par[1]);
                Bateria bateria = new Bateria(capacity);
                notebook.setBateria(bateria);
            }
            else if(cmd.equals("rm_battery")) {
                // REMOVA A BATERIA DO NOTEBOOK E IMPRIMA SE EXISTIR
                if(notebook.bateria == null) {
                    System.out.println("fail: Sem bateria");
                } else {
                    notebook.rmBateria();
                }
            }
            else if(cmd.equals("set_charger")) {
                // CRIE UM OBJETO CHARGER E ATRIBUA AO NOTEBOOK
                var power = Integer.parseInt(par[1]);
                Carregador carregador = new Carregador(power);
                notebook.setCarregador(carregador);
            }
            else if(cmd.equals("rm_charger")) {
                // REMOVA O CARREGADOR DO NOTEBOOK E IMPRIMA SE EXISTIR
                if(notebook.carregador == null) {
                    System.out.println("fail: Sem carregador");
                } else {
                    notebook.rmCarregador();
                }
            }
            else {
                System.out.println("fail: comando inválido");
            }

        }
    }
    private static Scanner scanner = new Scanner(System.in);

    static class Notebook {
        private boolean ligado;
        private Bateria bateria;
        private Carregador carregador;
        private int time;

        public Notebook() {
            this.ligado = false;
            this.bateria = null;
            this.carregador = null;
            this.time = 0;
        }

        public void ligar() {
            if((this.bateria == null || this.bateria.getCarga() <= 0) && this.carregador == null) {
                System.out.println("fail: não foi possível ligar");
                return;
            }
            this.ligado = true;
        }
        
        public void desligar() {
            this.ligado = false;
        }

        public String toString() {
            String notebook = (!this.ligado) ? "desligado" : "ligado por " + this.time + " min";
            String carregador = (this.carregador != null) ? (", Carregador " + this.carregador.getPotencia() + "W") : ("");
            String bateria = (this.bateria != null) ? (", Bateria " + this.bateria.toString()) : ("");
            return "Notebook: " + notebook + carregador + bateria;
        };

        public void usar(int minutes) {
            int usage = 0;
            if(!this.ligado) {
                System.out.println("fail: desligado");
            } else {
                if(this.carregador != null) {
                    if(this.bateria != null) this.bateria.carregar(minutes, this.carregador.getPotencia());
                    usage = minutes;
                } else {
                    if(this.bateria.carga - minutes <= 0) {
                        usage =  this.bateria.carga;
                        this.bateria.carga = 0;
                        System.out.println("fail: descarregou");
                        this.ligado = false;
                    } else {
                        usage = minutes;
                        this.bateria.carga -= usage;
                    }
                }
                // System.out.println("Usando por " + usage + " minutos, " + ((this.bateria.getCarga() - minutes < 0) ? "notebook descarregou" : ""));
                this.time += usage;
            }
        }

        public void setBateria(Bateria bateria) {
            this.bateria = bateria;
        }

        public Bateria rmBateria(){
            Bateria tmp = this.bateria;
            this.bateria = null;
            System.out.println("Removido " + tmp.toString());
            if(this.carregador == null) this.ligado = false;

            return tmp;
        }

        public void setCarregador(Carregador carregador) {
            if(this.carregador != null) {
                System.out.println("fail: carregador já conectado");
            } else {
                this.carregador = carregador;
            }
        }

        public Carregador rmCarregador() {
            Carregador tmp = this.carregador;
            this.carregador = null;
            System.out.println("Removido " + tmp.getPotencia() + "W");
            if(this.bateria == null) this.ligado = false;

            return tmp;
        }
    }

    static class Bateria {
        private int carga;
        private int capacidade;

        public Bateria(int capacidade) {
            this.capacidade = capacidade;
            this.carga = capacidade;
        }
        
        public int getCarga() {
            return carga;
        }
        
        public int getCapacidade() {
            return capacidade;
        }
        
        public void carregar(int minutes, int potencia) {
            if(this.carga + (minutes * potencia) > this.capacidade) this.carga = this.capacidade;
            else                                                    this.carga += minutes * potencia;
        }
        public void mostrar() {
            System.out.println(this.toString());
        }

        public String toString() {
            return  this.carga + "/" + this.capacidade;
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