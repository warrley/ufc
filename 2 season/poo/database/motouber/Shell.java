import java.util.Scanner;

public class Shell {

    public static void main(String[] args) {
        Moto moto = new Moto();

        while (true) {
            var line = scanner.nextLine();
            System.out.println("$" + line);

            var par = line.split(" ");
            var cmd = par[0];

            if (cmd.equals("end")) {
                break;
            }
             else if (cmd.equals("show")) {
                System.out.println(moto.toString());
             }
             else if (cmd.equals("setDriver")) {
                 String name = par[1];
                 int money = Integer.parseInt(par[2]);
                 Person driver = new Person(name, money);
                 moto.setDriver(driver);
             }
             else if (cmd.equals("setPass")) {
                 String name = par[1];
                 int money = Integer.parseInt(par[2]);
                 Person pass = new Person(name, money);
                 moto.setPassenger(pass);
             }
             else if (cmd.equals("drive")) {
                 int distance = Integer.parseInt(par[1]);
                 moto.drive(distance);
             }
             else if (cmd.equals("leavePass")) {
                 Person pass = moto.leavePass();
                 System.out.println(pass.toString() + " left");
             }
            else {
                System.out.println("fail: command not found");
            }
        }
    }
    static Scanner scanner = new Scanner(System.in);

    static class Moto {
        private Person passenger;
        private Person driver;
        private int cost;

        public Moto() {
            this.passenger = null;
            this.driver = null;
            this.cost = 0;
        }

        public Moto(Person passenger, Person driver) {
            this.passenger = passenger;
            this.driver = driver;
        }

        public void drive(int cost) {
            this.cost += cost;
        }

        public Person getPassenger() {
            return passenger;
        }

        public Person getDriver() {
            return driver;
        }

        public void setPassenger(Person passenger) {
            this.passenger = passenger;
        }

        public void setDriver(Person driver) {
            this.driver = driver;
        }

        public void getOnMoto() {
//            if(this.driver == null) {
//                System.out.println("");
//            }
        }

        public Person leavePass() {
            if(this.passenger.getMoney() < this.cost) {
                System.out.println("fail: Passenger does not have enough money");
                this.passenger.setMoney(0);
            } else {
                this.passenger.setMoney(this.passenger.getMoney() - cost);
            }
            this.driver.setMoney(this.driver.getMoney() + cost);
            this.cost = 0;
            Person tmp = this.passenger;
            this.passenger = null;
            return tmp;
        }

        public String toString() {
            String driver = (this.driver == null) ? "None" : this.driver.getName() + ":" + this.driver.getMoney();
            String passenger = (this.passenger == null) ? "None" : this.passenger.getName() + ":" + this.passenger.getMoney();
            return "Cost: " + this.cost + ", Driver: " + driver + ", Passenger: " + passenger;
        }
    }

    static class Person {
        private String name;
        private int money;

        public Person(String name, int money) {
            this.name = name;
            this.money = money;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getMoney() {
            return money;
        }

        public void setMoney(int money) {
            this.money = money;
        }

        public String toString() {
            return this.name + ":" + this.money;
        }
    }
}
