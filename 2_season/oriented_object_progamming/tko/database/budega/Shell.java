import java.util.*;


class Shell {
    public static void main(String[] _args) {
        Market market = new Market(0);

        while(true) {
            var line = scanner.nextLine();
            System.out.println("$" + line);

            var par = line.split(" ");
            var cmd = par[0];

            if (cmd.equals("end")) {
                break;
            }
            else if (cmd.equals("init")) { 
                var qtd_caixas = Integer.parseInt(par[1]);
                market = new Market(qtd_caixas);
            }
            else if (cmd.equals("show")) { 
                System.out.println(market.toString());
            }
            else if (cmd.equals("arrive")) { 
                var nome = par[1];
                Person client = new Person(nome);
                market.arrive(client);
            }
            else if (cmd.equals("call")) { 
                var indice = Integer.parseInt(par[1]);
                market.call(indice);
            }
            else if (cmd.equals("finish")) { 
                var indice = Integer.parseInt(par[1]);
                Person client = market.finish(indice);
            }
            else {
                System.out.println("fail: comando invalido");
            }
        }
    }

    static Scanner scanner = new Scanner(System.in);

    static class Market {
        private Person[] counters;
        private ArrayList<Person> queue;
        
        public boolean validateIndex(int index) {
            return true;
        }
        
        public Market(int counterCount) {
            this.counters = new Person[counterCount];
            this.queue = new ArrayList<Person>();
        }
        
        public void arrive(Person person) {
            this.queue.add(person);
        }

        public void call(int index) {
            if(this.queue.size() <= 0) {
                System.out.println("fail: sem clientes");
            } else if(this.counters[index] != null) {
                System.out.println("fail: caixa ocupado");
            } else {
                Person client = this.queue.get(0);
                this.queue.remove(0);
                
                this.counters[index] = client;
            }

        }

        public Person finish(int index) {
            if(index >= this.counters.length) {
                System.out.println("fail: caixa inexistente");

                return null;
            } else if(this.counters[index] == null) {
                System.out.println("fail: caixa vazio");
                return null;
            } else {
                Person client = this.counters[index];
    
                this.counters[index] = null;
                    
                return client;
            }
        }

        public String toString() {
            String counters = "Caixas: [";

            for(int i = 0; i < this.counters.length; i++) {
                if(this.counters[i] == null) counters += "-----";
                else                         counters += this.counters[i].name;

                if(i != this.counters.length - 1) counters += ", ";
            }

            counters += "]\n";

            String queue = "Espera: [";

            for(int i = 0; i < this.queue.size(); i++) {
                if(this.queue.get(i) == null) queue += "-----";
                else                         queue += this.queue.get(i).name;

                if(i != this.queue.size() - 1) queue += ", ";
            }

            queue += "]";

            return counters + queue;
        }
    }

    static class Person {
        private String name;

        public Person(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
