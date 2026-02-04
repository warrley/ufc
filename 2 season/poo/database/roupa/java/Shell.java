import java.util.*;

public class Shell {    
    public static void main(String[] args) {
        Roupa camisa = new Roupa();
        
        while (true) {
            var line = scanner.nextLine();
            System.out.println("$" + line);
            
            var par = line.split(" ");
            var cmd = par[0];
            
            if (cmd.equals("end")) {
                break;
            }
            else if (cmd.equals("size")) { // TENTE ATRIBUIR UM TAMANHO A ROUPA
                String size = par[1];
                if(!camisa.setTamanho(size)) {
                    System.out.println("fail: Valor inválido, tente PP, P, M, G, GG ou XG");
                }
            }
            else if (cmd.equals("show")) { // MOSTRE A ROUPA
                System.out.println("size: " + "(" + camisa.getTamanho() + ")");
            }
            else {
                System.out.println("fail: Comando inválido");
            }
        }
    }
    private static Scanner scanner = new Scanner(System.in);

    static class Roupa {
        private String tamanho;

        public Roupa() {
            this.tamanho = "";
        }

        public Boolean setTamanho(String novo) {
            if (novo.equals("PP") || novo.equals("P") || novo.equals("M") 
                || novo.equals("G") || novo.equals("GG") || novo.equals("XG")) {
                this.tamanho = novo;
                return true;
            } else {
                return false;
            }

        }

        public String getTamanho() {
            return this.tamanho;
        }
    }
}