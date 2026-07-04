import java.util.*;

public class Shell {

    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        VendingMachine vm = new VendingMachine(0);
        while (true) {
            String line = scanner.nextLine();
            System.out.println("$" + line);

            String[] argsSplit = line.split(" ");
            String cmd = argsSplit[0];

            if (cmd.equals("end")) {
                break;
            } else if (cmd.equals("show")) {
                System.out.println(vm.toString());
            } else if (cmd.equals("init")) {
                int qtdEspirais = Integer.parseInt(argsSplit[1]);
                vm = new VendingMachine(qtdEspirais);
            } else if (cmd.equals("limpar")) {
                int indice = Integer.parseInt(argsSplit[1]);
                vm.clearSlot(indice);
            } else if (cmd.equals("dinheiro")) {
                float value = Float.parseFloat(argsSplit[1]);
                vm.insertCash(value);
            } else if (cmd.equals("comprar")) {
                int ind = Integer.parseInt(argsSplit[1]);

                if(ind >= vm.slots.size()) {
                    System.out.println("fail: indice nao existe");
                } else if(vm.getCash() < vm.slots.get(ind).price) {
                    System.out.println("fail: saldo insuficiente");
                } else if(vm.slots.get(ind).quantity <= 0) {
                    System.out.println("fail: espiral sem produtos");
                } else {
                    System.out.printf("voce comprou um %s\n", vm.slots.get(ind).name);
                    vm.buyItem(ind);
                }

            } else if (cmd.equals("set")) {
                int indice = Integer.parseInt(argsSplit[1]);
                String nome = argsSplit[2];
                int qtd = Integer.parseInt(argsSplit[3]);
                float preco = Float.parseFloat(argsSplit[4]);

                if(indice >= vm.slots.size()) {
                    System.out.println("fail: indice nao existe");
                } else {
                    Slot slot = new Slot(nome, preco, qtd);
                    vm.setSlot(indice, slot);
                }
                
            } else if (cmd.equals("troco")) {
                float change = vm.withdrawCash();
                System.out.printf(Locale.US, "voce recebeu %.2f RS\n", change);
            } else {
                System.out.println("comando invalido");
            }
        }
    }

    static class VendingMachine {
        private ArrayList<Slot> slots;
        private float profit;
        private float cash;
        private int capacity;

        public VendingMachine(int capacity) {
            this.slots = new ArrayList<Slot>(capacity);
            this.capacity = capacity;
            this.cash = 0;
            for(int i = 0; i < capacity; i++) {
                Slot slot = new Slot("empty", 0, 0);
                slots.add(slot);
            }
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("saldo: " + String.format(Locale.US, "%.2f", cash) + "\n");

            for(int i = 0; i < capacity; i++) {
                sb.append(i + " " + slots.get(i).toString());
                if(i != capacity - 1) sb.append("\n");
            }

            return sb.toString();
        }

        public void buyItem(int index) {
            Slot slot = slots.get(index);
            slot.setQuantity(slot.getQuantity() - 1);
            slots.set(index, slot);

            cash -= slot.getPrice();
        }

        public float withdrawCash() {
            float cashtmp = cash;
            profit += cash;
            cash = 0;
            return cashtmp;
        }

        public void insertCash(float cash) {
            this.cash += cash;
        }

        public void clearSlot(int index) {
            slots.set(index, new Slot("empty", 0, 0));
        }

        public Slot getSlot(int index) {
            return null;
        }

        public void setSlot(int index, Slot slot) {
            slots.set(index, slot);
        }

        public float getCash() {
            return cash;
        }

        public float getProfit() {
            return profit;
        }
    }

    static class Slot {
        private String name;
        private float price;
        private int quantity;

        public Slot(String name, float price, int quantity) {
            this.name = name;
            this.price = price;
            this.quantity = quantity;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append("[");
            for(int i = 0; i < 8 - name.length(); i++) {
                sb.append(" ");
            }
            sb.append(name + " : " + quantity + " U : " + String.format(Locale.US, "%.2f", price) + " RS]");

            return sb.toString();
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public float getPrice() {
            return price;
        }

        public void setPrice(float price) {
            this.price = price;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }
        
    }
}
