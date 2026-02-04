import java.util.Scanner;

public class Shell {
    public static void main(String[] args) {

        Pencil pencil = new Pencil();

        while (true) {
            var line = scanner.nextLine();
            System.out.println("$" + line);

            var par = line.split(" ");
            var cmd = par[0];

            if (cmd.equals("end")) {
                break;
            }
            else if (cmd.equals("init")) { 
                var thickness = Double.parseDouble(par[1]);
                pencil = new Pencil(thickness);
            }
            else if (cmd.equals("show")) { 
                System.out.println(pencil.toString());
            }
            else if (cmd.equals("insert")) { 
                var thickness = Double.parseDouble(par[1]);
                var hardness = par[2];
                var size = Integer.parseInt(par[3]);
                Lead lead = new Lead(thickness, hardness, size);
                pencil.insert(lead);
            }
            else if (cmd.equals("remove")) { 
                Lead lead = pencil.remove();
            }
            else if (cmd.equals("write")) { 
                pencil.writePage();
            }
            else {
                System.out.println("fail: comando invalido");
            }
        }
    }

    static Scanner scanner = new Scanner(System.in);

    static class Lead {
        private int size;
        private double thickness;
        private String hardness;

        public Lead(double thickness, String hardness, int size) {
            this.thickness = thickness;
            this.hardness = hardness;
            this.size = size;
        }

        public int usagePerSheet() {
            if(this.hardness.equals("HB")) return 1;
            if(this.hardness.equals("2B")) return 2;
            if(this.hardness.equals("4B")) return 4;
            if(this.hardness.equals("6b")) return 6;
            else return 0; //tratar
        }

        public void setSize(int size) {
            this.size = size;
        }

        public String getHardness() {
            return this.hardness;
        }

        public int getSize() {
            return this.size;
        }
        
        public double getThickness() {
            return this.thickness;
        }

        public String toString() {
            return "[" + this.thickness +  ":" + this.hardness + ":" + this.size + "]";
        }
    }

    static class Pencil {
        private Lead tip;
        private double thickness;

        public Pencil() {
            this.thickness = 0;
            this.tip = null;
        }

         public Pencil(double thickness) {
            this.tip = null;
            this.thickness = thickness;
        }

        public boolean hasGrafite() {
            if(this.tip == null) return false;
            else                 return true;
        }

        public void insert(Lead tip) {
            if(this.tip == null) {
                if(this.thickness != tip.getThickness()) {
                    System.out.println("fail: calibre incompativel");
                } else {
                    this.tip = tip;     
                }
            } else {
                System.out.println("fail: ja existe grafite");
            }
        }

        public Lead remove() {
            Lead tmp = this.tip;
            this.tip = null;
            return tmp;
        }

        public void writePage() {
            if(!hasGrafite()) {
                System.out.println("fail: nao existe grafite");
            } else {
                if(this.tip.getSize() > 10) {
                    if(this.tip.getSize() - this.tip.usagePerSheet() >= 10) {
                        this.tip.setSize(this.tip.getSize() - this.tip.usagePerSheet());
                    } else {
                        System.out.println("fail: folha incompleta");
                        this.tip.setSize(10);
                    }
                } else {
                    System.out.println("fail: tamanho insuficiente");
                }
            }
        }

        public String toString() {
            String tip = (!hasGrafite()) ? "null" : this.tip.toString();
            return "calibre: " + this.thickness + ", grafite: " + tip;
        }
    }
}
