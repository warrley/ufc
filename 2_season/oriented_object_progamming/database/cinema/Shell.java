import java.util.Scanner;
import java.util.*;


public class Shell {
    public static void main(String[] _args) {
        Theater theater = new Theater(0);

        while (true) {
            var line = scanner.nextLine();
            System.out.println("$" + line);

            var args = line.split(" ");
            var cmd = args[0];

            if (cmd.equals("end")) {
                break;
            }
            else if (cmd.equals("show")) {
                System.out.println(theater.toString());
            }
            else if (cmd.equals("init")) {
                var seats = Integer.parseInt(args[1]);
                theater = new Theater(seats);
            }
            else if (cmd.equals("reserve")) {
                var id = args[1];
                var phone = Integer.parseInt(args[2]);
                var index = Integer.parseInt(args[3]);
                // Client client = new Client(id, phone);
                theater.reserve(id, phone, index);
            }
            else if (cmd.equals("cancel")) {
                var id = args[1];
                theater.cancel(id);
            }
            else {
                System.out.println("fail: comando invalido");
            }
        }
    }

    private static Scanner scanner = new Scanner(System.in);

    static class Theater {
        private ArrayList<Client> seats;

        public Theater(int capacity) {
            this.seats = new ArrayList<Client>(capacity);
            for (int i = 0; i < capacity; i++) {
                seats.add(null);
            }
        }


        public String toString() {
            StringBuilder st = new StringBuilder();
            st.append("[");

            for(int i = 0; i < this.seats.size(); i++) {
                if(i != 0) st.append(" ");
                if(this.seats.get(i) == null) {
                    st.append("-");
                } else {
                    st.append(this.seats.get(i).toString());
                }
            }

            st.append("]");
            return st.toString();
        }

        public boolean reserve (String id, int phone, int index) {
            if(!verifyIndex(index)) {
                return false;
            }

            int i = search(id);
            if(i != - 1) {
                System.out.println("fail: cliente ja esta no cinema");
                return false;
            }

            Client client = new Client(id, phone);
            seats.set(index, client);

            return true;
        }

        public void cancel (String id) {
            int index = search(id);
            if(index != -1) {
                seats.set(index, null);
            } else {
                System.out.println("fail: cliente nao esta no cinema");
            }
        }

        public ArrayList<Client> getSteats() {
            return null;
        }

        public int search(String name) {
            int index = -1;
            for(int i = 0; i < seats.size(); i++) {
                if(seats.get(i) != null && seats.get(i).getId().equals(name)) {
                    index = i;
                }
            }

            return index;
        }

        public boolean verifyIndex(int index) {
            if(index >= seats.size()) {
                System.out.println("fail: cadeira nao existe");
                return false;
            } else {
                if(seats.get(index) != null) {
                    System.out.println("fail: cadeira ja esta ocupada");
                    return false;
                }
            }
            return true;
        }
    }

    static class Client {
        private String id;
        private int phone;

        public Client(String id, int phone) {
            this.id = id;
            this.phone = phone;
        }

        public String toString() {
            return id + ":" + phone;
        }

        public String getId() {
            return this.id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public int getPhone() {
            return this.phone;
        }

        public void setPhone(int phone) {
            this.phone = phone;
        }

        
    }
}
