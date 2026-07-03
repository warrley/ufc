import java.util.*;

// DIAGRAMA ERRADO ? -> addOperation em Cliente nao é passado o objeto

public class Shell {
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        Agiota agiota = new Agiota();

        while (true) {
            String line = scanner.nextLine();
            System.out.println("$" + line);

            String[] parts = line.split(" ");
            String cmd = parts[0];

            if (cmd.equals("end")) {
                break;
            }
            else if (cmd.equals("show")) {
                System.out.println(agiota.toString());
            }
            else if (cmd.equals("addCli")) {
                String name = parts[1];
                int limite = Integer.parseInt(parts[2]);
                agiota.addClient(name, limite);
            }
            else if (cmd.equals("showCli")) {
                String name = parts[1];
                Client cli = agiota.getClient(name);
                if(cli == null) {
                    System.out.println("fail: cliente nao existe");
                } else {
                    System.out.println(cli.toString());
                    for(Operation op : cli.getOperations()) {
                        System.out.println(op.toString());
                    }
                }
            }
            else if (cmd.equals("kill")) {
                String name = parts[1];
                agiota.kill(name);
            }
            else if (cmd.equals("give")) {
                String name = parts[1];
                int value = Integer.parseInt(parts[2]);
                agiota.give(name, value);
            }
            else if (cmd.equals("take")) {
                String name = parts[1];
                int value = Integer.parseInt(parts[2]);
                agiota.take(name, value);
            }
            else if (cmd.equals("plus")) {
                agiota.plus();
            }
            else {
                System.out.println("fail: comando invalido");
            }
        }
    }

    static class Agiota {
        private ArrayList<Client> alive_list;
        private ArrayList<Client> death_list;
        private ArrayList<Operation> alive_oper;
        private ArrayList<Operation> death_oper;
        private int nextOpId;
    
        public Agiota() {
            this.alive_list = new ArrayList<Client>();
            this.death_list = new ArrayList<Client>();
            this.alive_oper = new ArrayList<Operation>();
            this.death_oper = new ArrayList<Operation>();
            nextOpId = 0;
        }

        public String toString() {
            this.alive_list.sort((p1, p2) -> p1.getName().compareTo(p2.getName()));
            this.death_list.sort((p1, p2) -> p1.getName().compareTo(p2.getName()));

            String sb = "";
            
            int i = 0;
            for(Client alive : alive_list) {
                if(i != 0) sb += "\n"; 
                sb += ":) " + alive.toString();
                i++;
            }
            
            for(Operation operation : alive_oper) {
                sb += "\n+ " + operation.toString();
            }
            
            for(Client death : death_list) {
                sb += "\n:( " + death.toString();
            }

            for(Operation operation : death_oper) {
                sb += "\n- " + operation.toString();
            }

            return sb;
        }

        public void pushOperation(Client client, String name, Label label, int value) {

        }

        public Client getClient(String name) {
            int index = searchClient(name);
            return alive_list.get(index);
        }

        public void addClient(String name, int limite) {
            if(searchClient(name) != -1) {
                System.out.println("fail: cliente ja existe");
            } else {
                Client cli = new Client(name, limite);
                alive_list.add(cli);
            }
        }

        public void give(String name, int value) {
            int index = searchClient(name);
            if(index == -1) {
                System.out.println("fail: cliente nao existe");
            } else {
                if(alive_list.get(index).getBalance() + value > alive_list.get(index).getLimite()) {
                    System.out.println("fail: limite excedido");
                } else {
                    Operation op = new Operation(nextOpId, name, Label.GIVE, value);
                    alive_oper.add(op);
                    alive_list.get(index).addOperation(op);
                    nextOpId++;
                }
            }
        }

        public void take(String name, int value) {
            int index = searchClient(name);
            if(index == - 1) {
                System.out.println("fail: cliente nao existe");
            } else {
                Operation op = new Operation(nextOpId, name, Label.TAKE, value);
                alive_oper.add(op);
                alive_list.get(index).addOperation(op);
                nextOpId++;
            }
        }

        public void plus() {
            ArrayList<String> deaths = new ArrayList<>();
            for(Client cli : alive_list) {
                Operation op = new Operation(nextOpId, cli.getName(), Label.PLUS, (int) Math.ceil(cli.getBalance() * 10.0 / 100.0));
                alive_oper.add(op);
                cli.addOperation(op);
                if(cli.getBalance() > cli.getLimite()) deaths.add(cli.getName());
                nextOpId++;
            }

            for(String death : deaths) {
                kill(death);
            }
        }

        public void kill(String name) {
            int index = searchClient(name);
            Client cli = getClient(name);

            ArrayList<Operation> tmp = new ArrayList<>();
            for(Operation operation : alive_oper) {
                if(operation.getName().equals(name)) {
                    death_oper.add(operation);
                } else {
                    // System.out.println("id: " + operation.getName() + " person id: " + name);
                    tmp.add(operation);
                }
            }
            alive_oper.clear();
            
            for(Operation operation : tmp) {
                alive_oper.add(operation);
            }

            alive_list.remove(index);
            death_list.add(cli);
        }


        public int searchClient(String name) {
            for(int i = 0; i < alive_list.size(); i++) {
                if(alive_list.get(i).getName().equals(name)) {
                    return i;
                }
            }
            return -1;
        }

        
    }

    static class Client {
        private String name;
        private int limite;
        private ArrayList<Operation> operations;

        public Client(String name, int limite) {
            this.name = name;
            this.limite = limite;
            this.operations = new ArrayList<Operation>();
        }
        
        public String toString() {
            return String.format("%s %d/%d", name, getBalance(), getLimite());
        }

        public int getBalance() {
            int total = 0;
            for(Operation operation : operations) {
                if(operation.getLabel() == Label.GIVE || operation.getLabel() == Label.PLUS) total += operation.value;
                else                                   total -= operation.value;
            }
            return total;
        }
        
        public String getName() {
            return this.name;
        }

        public int getLimite() {
            return this.limite;
        }

        public ArrayList<Operation> getOperations() {
            return this.operations;
        }

        public void addOperation(Operation operation) {
            operations.add(operation);
        }

    }

    static class Operation {
        private int id;
        private String name;
        private Label label;
        private int value;
        
        public Operation(int id, String name, Label label, int value) {
            this.id = id;
            this.name = name;
            this.label = label;
            this.value = value;
        }

        public String toString() {
            return String.format("id:%d %s:%s %d", id, label, name, value);
        }

        public int getId() {
            return this.id;
        }
    
        public String getName() {
            return this.name;
        }
    
        public Label getLabel() {
            return this.label;
        }
    }

    static enum Label {
        GIVE,
        TAKE,
        PLUS;

        public String toString() {
            return this.name().toLowerCase();
        }
    }
}