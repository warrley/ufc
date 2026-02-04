import java.util.*;

public class Shell{
    
    public static void main(String[] args) {
        // CRIE SUA MOTO AQUI
        Moto moto = new Moto();

        while(true) {
            var line = scanner.nextLine();
            System.out.println("$" + line);

            var par = line.split(" ");
            var cmd = par[0];

            if (cmd.equals("end")) {
                break;
            }
            else if (cmd.equals("init")) {
                // CRIE SUA MOTO AQUI
                var power = Integer.parseInt(par[1]);
                moto = new Moto(power);
            }
            else if (cmd.equals("show")) {
                // MOSTRE SUA MOTO AQUI
                System.out.println(moto.toString());
            }
            else if (cmd.equals("enter")) {
                // CRIE UM OBJETO PESSOA AQUI
                // DEPOIS INSIRA NA MOTO
                var name = par[1];
                var age = Integer.parseInt(par[2]);
                Person person = new Person(name, age);
                moto.insertPerson(person);
            }
            else if (cmd.equals("leave")) {
                // RETIRE A PESSOA DA MOTO
                Person person = moto.removePerson();
                // MOSTRE A PESSOA RETIRADA AQUI
                if(person != null) {
                    System.out.println(person.toString());
                }
            }
            else if (cmd.equals("buy")) {
                // COMPRE TEMPO
                var time = Integer.parseInt(par[1]);
                moto.buyTime(time);
            }
            else if (cmd.equals("drive")) {
                // DIRIJA A MOTO
                var time = Integer.parseInt(par[1]);
                moto.drive(time);
            }
            else if (cmd.equals("honk")) {
                // BUZINE
                moto.honk();
            }
            else {
                System.out.println("fail: comando invalido");
            }
        }
        scanner.close();
    }

    static Scanner scanner = new Scanner(System.in);

    static class Person {
        private String name;
        private int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String getName() {
            return name;
        }

        public int getAge() {
            return age;
        }

        public String toString() {
            return name + ":" + age;
        }
        
    }

    static class Moto {
        private int power;
        private int time;
        private Person person;

        public Moto() {
            this.power = 1;
            this.time = 0;
            this.person = null;
        }
        
        public Moto(int power) {
            this.power = power;
            this.time = 0;
            this.person = null;
        }

        public boolean insertPerson(Person person) {
            if(this.person != null) {
                System.out.println("fail: busy motorcycle");
                return false;
            } else {
                this.person = person;
                return true;
            }
        }
        
        public Person removePerson() {
            if(this.person == null) {
                System.out.println("fail: empty motorcycle");
                return null;
            } else {
                Person tmp = this.person;
                this.person = null;
                return tmp;
            }
        }

        public void buyTime(int time) {
            this.time += time;
        }
        
        public void drive(int time) {
            if(this.time == 0) {
                System.out.println("fail: buy time first");
            } else if (this.person == null) {
                System.out.println("fail: empty motorcycle");
            } else if (this.person.getAge() > 10) {
                System.out.println("fail: too old to drive");
            } else {
                if(this.time - time <= 0) {
                    System.out.println("fail: time finished after " + this.time + " minutes");
                    this.time = 0;
                } else {
                    this.time -= time;
                }
            }
        }
        
        public void honk() {
            System.out.print("P");
            for(int i = 0; i < this.power; i++) {
                System.out.print("e");
            }
            System.out.println("m");
        }

        public String toString() {
            String person = (this.person == null) ? "empty" : this.person.toString();
            return "power:"+this.power+", time:"+this.time+", person:("+person+")";
        }
    }

}
