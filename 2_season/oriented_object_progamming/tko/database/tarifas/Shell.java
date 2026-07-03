import java.util.ArrayList;
import java.util.Scanner;

public class Shell{
    public static void main(String[] _args) {
        Account acc = new Account(0);

        while (true) {
            var line = scanner.nextLine();
            var args = line.split(" ");
            var cmd = args[0];
            System.out.println("$" + line);

            if (cmd.equals("end")) {
                break;
            } else if (cmd.equals("show")) {
                System.out.println(acc.toString());
            } else if (cmd.equals("init")) {
                var id = Integer.parseInt(args[1]);
                acc = new Account(id);
            } else if (cmd.equals("withdraw")) {
                var value = Integer.parseInt(args[1]);
                acc.withdraw(value);
            } else if (cmd.equals("fee")) {
                var value = Integer.parseInt(args[1]);
                acc.fee(value);
            } else if (cmd.equals("deposit")) {
                var value = Integer.parseInt(args[1]);
                acc.deposit(value);
            } else if (cmd.equals("reverse")) {
                for (int i = 1; i < args.length; i++) {
                    var index = Integer.parseInt(args[i]);
                    acc.reverse(index);
                }
            } else if (cmd.equals("extract")) {
                var qtdOp = Integer.parseInt(args[1]);
                ArrayList<Operation> ops = acc.balanceManager.getExtract(qtdOp);
                for(int i = ops.size() - 1; i >= 0; i--) {
                    System.out.println(ops.get(i).toString());
                }
            }
            else {
                System.out.println("fail: comando invalido");
            }
        }
    }

    static Scanner scanner = new Scanner(System.in);

    public static class Account {
        private BalanceManager balanceManager;
        private int id;

        public Account(int id) {
            this.id = id;
            this.balanceManager = new BalanceManager();
            balanceManager.addOperation(Label.OPENING, 0);
        }

        public String toString() {
            return String.format("account:%d balance:%d", id, balanceManager.balance);
        }

        public void reverse(int index) {
            if(index >= balanceManager.nextId) {
                System.out.println("fail: index " + index + " invalid");
            } else if(!balanceManager.extract.get(index).getLabel().label.equals("fee")) {
                System.out.println("fail: index " + index + " is not a fee");
            } else {
                int value = -balanceManager.extract.get(index).getValue();
                balanceManager.addOperation(Label.REVERSE, value);
                balanceManager.balance += value;
            }
        }

        public void fee(int value) {
            balanceManager.addOperation(Label.FEE, -value);
            balanceManager.balance -= value;
        }
        
        public void withdraw(int value) {
            if(value < 0) {
                System.out.println("fail: invalid value");
            } else if(value > balanceManager.balance) {
                System.out.println("fail: insufficient balance");
                // balanceManager.addOperation(Label.ERROR, -value);
            } else {
                balanceManager.addOperation(Label.WITHDRAW, -value);
                balanceManager.balance -= value;
            }
        }
        
        public void deposit(int value) {
            if(value < 0) {
                System.out.println("fail: invalid value");
                // balanceManager.addOperation(Label.ERROR, value);
            } else {
                balanceManager.addOperation(Label.DEPOSIT, value);
                balanceManager.balance += value;
            }
        }

        public BalanceManager getBalanceManager() {
            return balanceManager;
        }
    } 

    public static class BalanceManager {
        private int balance;
        private ArrayList<Operation> extract;
        private int nextId;
        
        public BalanceManager() {
            this.balance = 0;
            this.extract = new ArrayList<Operation>(0);
            this.nextId = 0;
        }
        
        public void addOperation(Label label, int value) {
            // int realValue = (value > 0) ? value : 0;
            extract.add(new Operation(nextId, label, value, balance + value));
            nextId++;
        }
        
        public Operation getOperation(int index) {
            return extract.get(index);
        }
        
        public String toString() {
            String s = "";

            for(int i = 0; i < extract.size(); i++) {
                s += extract.get(i).toString();
            }

            return s;
        }
        
        public ArrayList<Operation> getExtract(int qtdOp) {
            ArrayList<Operation> tmp = new ArrayList<Operation>(qtdOp);
            qtdOp = (qtdOp == 0) ? extract.size() : qtdOp;
            for(int i = extract.size() - 1; i >= extract.size() - qtdOp; i--) {
                tmp.add(extract.get(i));
            }
            return tmp;
        }

    }

    public static class Operation {
        private final int index;
        private final Label label;
        private final int value;
        private final int balance;

        public Operation(int index, Label label, int value, int balance) {
            this.index = index;
            this.label = label;
            this.value = value;
            this.balance = balance;
        }
        
        public String toString() {
            String cindex = "";
            for(int i = 0; i < 2 - String.valueOf(getIndex()).length(); i++) {
                cindex += " ";
            }
            cindex += getIndex();
            
            String clabel = "";
            for(int i = 0; i < 8 - getLabel().label.length(); i++) {
                clabel += " ";
            }
            clabel += getLabel().label;

            String cvalue = "";
            for(int i = 0; i < 4 - String.valueOf(getValue()).length(); i++) {
                cvalue += " ";
            }
            cvalue += getValue();

            String cbalance = "";
            for(int i = 0; i < 4 - String.valueOf(getBalance()).length(); i++) {
                cbalance += " ";
            }
            cbalance += getBalance();

            
            return String.format("%s: %s: %s: %s", cindex, clabel, cvalue, cbalance);
        }
        
        public int getIndex() {
            return this.index;
        }

        public Label getLabel() {
            return this.label;
        }

        public int getValue() {
            return this.value;
        }

        public int getBalance() {
            return this.balance;
        }
        
    }

    public enum Label {
        DEPOSIT("deposit"),
        FEE("fee"),
        OPENING("opening"),
        REVERSE("reverse"),
        WITHDRAW("withdraw"),
        ERROR("error");

        private String label;

        Label(String label) {
            this.label = label;
        }

        public String getLabel() {
            return label;
        }
    }

}
