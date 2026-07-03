import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;


public class Shell {

    public static void main(String[] a) {
        Estacionamento est = new Estacionamento();
        while (true) {
            String line = scanner.nextLine();
            System.out.println("$" + line);

            var par = line.split(" ");
            var cmd = par[0];

            try {
                if (cmd.equals("end")) {
                break;
                }
                else if (cmd.equals("show")) {
                    System.out.println(est);
                }
                else if (cmd.equals("init")) {

                }
                else if (cmd.equals("estacionar")) {
                    var veiculo = par[1];
                    var id = par[2];
                    if(veiculo.equals("carro")) est.estacionar(new Carro(id));
                    if(veiculo.equals("bike")) est.estacionar(new Bike(id));
                    if(veiculo.equals("moto")) est.estacionar(new Moto(id));
                }
                else if (cmd.equals("pagar")) {
                    // CHAME OS METODOS DE PAGAMENTO E SAIDA
                    var veiculo = par[1];
                    est.pagar(veiculo);
                }
                else if (cmd.equals("tempo")) {
                    var tempo = Integer.parseInt(par[1]);
                    est.passarTempo(tempo);
                }
                else {
                    System.out.println("fail: comando inválido");
                }
            } catch (MsgException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    static class Estacionamento {
        private ArrayList<Veiculo> veiculos;
        private int horaAtual;

        private int procurarVeiculo(String id) throws MsgException {
            for(int i = 0; i < this.veiculos.size(); i++) if(this.veiculos.get(i).getId().equals(id)) return i;
            throw new MsgException("fail: veiculo nao encontrado");
        }

        public Estacionamento() {
            this.veiculos = new ArrayList<Veiculo>();
            this.horaAtual = 0;
        }

        public void estacionar(Veiculo veiculo) {
            veiculo.setEntrada(horaAtual);
            this.veiculos.add(veiculo);
        }

        public void pagar(String id) throws MsgException {
            int i = procurarVeiculo(id);
            System.out.printf(Locale.US, "%s chegou %d saiu %d. Pagar R$ %.2f\n", 
            this.veiculos.get(i).getTipo(), this.veiculos.get(i).getEntrada(), this.horaAtual, this.veiculos.get(i).calcularValor(this.horaAtual));
            this.sair(id);
        }

        public void sair(String id) throws MsgException {
            this.veiculos.remove(this.veiculos.get(procurarVeiculo(id)));
        }

        public void passarTempo(int tempo) {
            this.horaAtual += tempo;
        }

        public String toString() {
            String st = "";

            for(Veiculo v : this.veiculos) {
                st += v;
            }
            
            st += "Hora atual: " + this.horaAtual;

            return st;
        }
    }

    static class Carro extends Veiculo {
        public Carro(String id) {
            super(id, "Carro");
        }

        @Override
        public double calcularValor(int horaSaida) {
            double valor = (horaSaida - super.getEntrada())/10;
            if(valor < 5) return 5;
            else          return valor;
        }
    }
    
    static class Moto extends Veiculo {
        public Moto(String id) {
            super(id, "Moto");
        }

        @Override
        public double calcularValor(int horaSaida) {
            return (horaSaida -  super.getEntrada()) / 20;
        }
    }

    static class Bike extends Veiculo {
        public Bike(String id) {
            super(id, "Bike");
        }

        @Override
        public double calcularValor(int horaSaida) {
            return 3.0f;
        }
    }

    static abstract class Veiculo {
        protected String id;
        protected String tipo;
        protected int horaEntrada;

        public Veiculo(String id, String tipo) {
            this.id = id;
            this.tipo = tipo;
        }

        public String toString() {
            String st = "";
            for(int i = 0; i < 10 - this.getTipo().length(); i++) {
                st += "_";
            }
            st += this.getTipo() + " : ";
            for(int i = 0; i < 10 - this.getId().length(); i++) {
                st += "_";
            }
            st += this.getId() + " : " + this.getEntrada() + "\n";
            return st;
        }

        public void setEntrada(int horaEntrada) {
            this.horaEntrada = horaEntrada;
        }
        public int getEntrada() {
            return this.horaEntrada;
        }

        public String getTipo() {
            return this.tipo;
        }

        public String getId() {
            return this.id;
        }

        public abstract double calcularValor(int horaSaida);
    }

    static class MsgException extends Exception {
        public MsgException(String msg) {
            super(msg);
        }
    }

    private static Scanner scanner = new Scanner(System.in);
}
