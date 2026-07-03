import java.util.*;
// import java.util.stream.Collectors;


public class Shell {
    public static void main(String[] s) {
        Agency ag = new Agency();

        while(true){
            try {
                var line = scanner.nextLine();
                var args = line.split(" ");
                var cmd = args[0];
                System.out.println("$" + line);

                if(cmd.equals("end")) {
                    break;
                }
                else if(cmd.equals("show")) {
                    System.out.println(ag);
                }
                else if(cmd.equals("addCli")) {
                    var clientId = args[1];
                    ag.addClient(clientId);
                }
                else if(cmd.equals("saque")) {
                    var accountId = Integer.parseInt(args[1]);
                    var value = Double.parseDouble(args[2]);
                    ag.withdraw(accountId, value);
                }
                else if(cmd.equals("deposito")) {
                    var accountId = Integer.parseInt(args[1]);
                    var value = Double.parseDouble(args[2]);
                    ag.deposit(accountId, value);
                }
                else if(cmd.equals("transf")) {
                    var accountIdFrom = Integer.parseInt(args[1]);
                    var accountIdTo = Integer.parseInt(args[2]);
                    var value = Double.parseDouble(args[3]);
                    ag.transfer(accountIdFrom, accountIdTo, value);
                }
                else if(cmd.equals("update")) {
                    ag.updateMonthly();
                }
                else {
                    System.out.println("fail: comando invalido");
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
    private static Scanner scanner = new Scanner(System.in);

    static public class MsgException extends Exception {
        public MsgException(String msg) {
            super(msg);
        }
    }

    static public class Agency {
        private Map<Integer, Account> accounts;
        private Map<String, Client> clients;

        private Account getAccount(int accountId) throws MsgException {
            for(Account acc : this.accounts.values()) {
                if(acc.getAccId() == accountId) return acc;
            } 

            throw new MsgException("fail: conta nao encontrada");
        }

        public Agency() {
            this.accounts = new TreeMap<Integer, Account>();
            this.clients = new TreeMap<String, Client>();
        }

        public void addClient(String clientId) {
            Client cl = new Client(clientId);
            CheckingAccount accCC = new CheckingAccount(clientId);
            SavingsAccount accCP = new SavingsAccount(clientId);

            this.accounts.put(accCC.getAccId(), accCC);
            this.accounts.put(accCP.getAccId(), accCP);

            cl.addAccount(accCC);
            cl.addAccount(accCP);

            this.clients.put(clientId, cl);
        }

        public void deposit(int accId, double value) throws MsgException {
            Account acc = getAccount(accId);
            acc.deposit(value);
        }
        
        public void withdraw(int accId, double value) throws MsgException {
            Account acc = getAccount(accId);
            acc.withdraw(value);
        }

        public void transfer(int fromAccId, int toAccId, double value) throws MsgException {
            getAccount(fromAccId).transfer(getAccount(toAccId), value);
        }

        public void updateMonthly() {
            for(Account acc : this.accounts.values()) {
                acc.updateMonthly();
            }
        }

        public String toString() {
            String st = "- Clients\n";

            for(Client cl : this.clients.values()) {
                st += cl;
            }
            
            st += "- Accounts\n";
            
            int i = 0;
            for(Account acc : this.accounts.values()) {
                if(i != 0) st += "\n";
                st += acc;
                i++;
            }

            return st;
        }
     }

    static public class Client {
        private String clientId;
        private ArrayList<Account> accounts;

        public Client(String clientId) {
            this.clientId = clientId;
            this.accounts = new ArrayList<Account>();
        }

        public void addAccount(Account acc) {
            this.accounts.add(acc);
        }
        
        public ArrayList<Account> getAccounts() {
            return this.accounts;
        }

        public String getClientId() {
            return this.clientId;
        }

        public String toString() {
            String st = this.clientId + " [";

            int i = 0;
            for(Account acc : this.accounts) {
                if(i != 0) st += ", ";
                st += acc.accId;
                i++;
            }

            st += "]\n";

            return st;
        }
    }

    static public class SavingsAccount extends Account {
        protected double monthlyInterest;

        public SavingsAccount(String clientId) {
            super(clientId, "CP");
        }

        public void updateMonthly() {
            super.deposit(super.getBalance() * 0.01);
        }
    }

    static public class CheckingAccount extends Account {
        protected double monthlyFee;

        public CheckingAccount(String clientId) {
            super(clientId, "CC");
        }

        public void updateMonthly() {
            super.deposit(-20);
        }
    }

    static public class Account {
        static int nextId = 0;
        private double balance;
        private int accId;
        private String clientId;
        private String typeId;

        public Account(String clientId, String typeId) {
            this.accId = nextId++;
            this.clientId = clientId;
            this.typeId = typeId;
            this.balance = 0;
        }

        public void deposit(double value) {
            this.balance += value;
        }

        public void withdraw(double value) throws MsgException {
            if(this.balance < value) throw new MsgException("fail: saldo insuficiente");
            this.balance -= value;
        }

        public void transfer(Account other, double value) throws MsgException {
            this.withdraw(value);
            other.deposit(value);
        }

        public double getBalance() {
            return this.balance;
        }

        public int getAccId() {
            return this.accId;
        }

        public String getClientId() {
            return this.clientId;
        }

        public String getTypeId() {
            return this.typeId;
        }

        public void updateMonthly() {

        }

        public String toString() {
            return String.format(Locale.US ,"%d:%s:%.2f:%s", this.accId, this.clientId, this.balance, this.typeId);
        }
    }
}
