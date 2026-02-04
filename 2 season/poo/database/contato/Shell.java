import java.util.ArrayList;
import java.util.Scanner;

public class Shell{

    public static void main(String[] Args) {
        Contact cnt = new Contact("");

        while (true) {
            String line = scanner.nextLine();
            System.out.println("$" + line);
            String[] args = line.split(" ");
            
            if (args[0].equals("end")) {
                break;
            }
            else if (args[0].equals("init")) {
                var name = args[1];
                cnt = new Contact(name);
            }
            else if (args[0].equals("show")) {
                System.out.println(cnt.toString());
            }
            else if (args[0].equals("add")) {
                var id = args[1];
                var number = args[2];
                cnt.addFone(id, number);
            }
            else if (args[0].equals("rm")) {
                var index = Integer.parseInt(args[1]);
                cnt.rmFone(index);
            }
            else if (args[0].equals("tfav")) {
                cnt.toggleFavorited();
            }
            else {
                System.out.println("fail: invalid command");
            }
        }
    }
    static Scanner scanner = new Scanner(System.in);

    static class Contact {
        private boolean favorited;
        private ArrayList<Fone> fones;
        private String name;

        public Contact(String name) {
            this.name = name;
            this.favorited = false;
            this.fones = new ArrayList<Fone>(0);
        }

        public String toString() {
            String tel = "[";
            for(int i = 0; i < fones.size(); i++) {
                if(i != 0) tel += ", ";
                tel += fones.get(i).toString();
            }
            tel += "]";
            return String.format("%c %s %s", (isFavorited()) ? '@' : '-', getName(), tel);
        }

        public void addFone(String id, String number) {
            Fone fone = new Fone(id, number);
            if(!fone.isValid()) {
                System.out.println("fail: invalid number");
                return;
            }
            fones.add(fone);
        }
        
        public void rmFone(int id) {
            fones.remove(id);
        }

        public void toggleFavorited() {
            favorited = !favorited;
        }

        public boolean isFavorited() {
            return this.favorited;
        }

        public ArrayList<Fone> getFones() {
            return this.fones;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }

        
    }

    static class Fone {
        private String id;
        private String number;

        public Fone(String id, String number) {
            this.id = id;
            this.number = number;
        }

        public String toString() {
            return String.format("%s:%s", getId(), getNumber());
        }

        public boolean isValid() {
            char[] c = getNumber().toCharArray();
            for(int i = 0; i < getNumber().length(); i++) {
                if(!((c[i] >= '0' && c[i] <= '9') || c[i] == '(' || c[i] == ')' || c[i] == '.')) {
                    return false;
                } 
            }
            return true;
        }

        public String getId() {
            return this.id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getNumber() {
            return this.number;
        }

        public void setNumber(String number) {
            this.number = number;
        }
    }
}
