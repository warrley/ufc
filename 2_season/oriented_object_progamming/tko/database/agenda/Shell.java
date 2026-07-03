import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Shell {

    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] _args) {
        Agenda agenda = new Agenda();

        while (true) {
            String line = scanner.nextLine();
            System.out.println("$" + line);
            String[] args = line.split(" ");
            
            if (args[0].equals("end")) {
                break;
            }
            else if (args[0].equals("add")) {
                ArrayList<Fone> fones = new ArrayList<Fone>();
                for (int i = 2; i < args.length; i++) {
                    String[] fone = args[i].split(":");
                    fones.add(new Fone(fone[0], fone[1]));
                }
                String name = args[1];
                agenda.addContact(name, fones);
            }
            else if (args[0].equals("show")) {
                System.out.println(agenda.toString());
            }
            else if (args[0].equals("rm")) {
                var name = args[1];
                agenda.rmContact(name);
            }
            else if (args[0].equals("favs")) {
                ArrayList<Contact> cnts = agenda.getFavorited();

                for(Contact contact : cnts) {
                    System.out.println(contact.toString());
                }

            }
            else if (args[0].equals("search")) {
                String pr = args[1];
                ArrayList<Contact> contacts =  agenda.search(pr);
                for(Contact contact : contacts) {
                    System.out.println(contact.toString());
                }
            }
            else if (args[0].equals("rmFone"))  {
                var name = args[1];
                var index = Integer.parseInt(args[2]);
                agenda.getContacts().get(agenda.findPosByName(name)).rmFone(index);
            }
            else if (args[0].equals("tfav")) {
                var name = args[1];
                agenda.getContacts().get(agenda.findPosByName(name)).toggleFavorited();
            }
            else {
                System.out.println("fail: invalid command");
            }
        }
    }

    static class Agenda {
        private ArrayList<Contact> contacts;
        
        public Agenda() {
            this.contacts = new ArrayList<Contact>(0);
        }

        public String toString() {
            String sb = "";
            for(int i = 0; i < contacts.size(); i++) {
                if(i != 0) sb += "\n";
                sb += contacts.get(i).toString();
            }

            return sb;
        }
        
        public int findPosByName(String name) {
            for(int i = 0; i < contacts.size(); i++) {
                if(contacts.get(i).name.equals(name)) {
                    return i;
                }
            }
            return -1;
        }
        
        public void addContact(String name, ArrayList<Fone> fones) {
            int index = findPosByName(name);
            Contact cnt = new Contact(name);

            if(index != -1) {
                cnt = contacts.get(index);
                contacts.remove(contacts.size() - 2); // pula 2 pra pegar o certo
            }

            // cnt.fones = fones; -> é uma boa pratica?
            for(Fone fone : fones) {
                cnt.addFone(fone.getId(), fone.getNumber());
            }

            contacts.add(cnt);
        }
        
        public Contact getContact(String name) {
            return null;
        }
        
        public void rmContact(String name) {
            contacts.remove(findPosByName(name));
        }
        
        public ArrayList<Contact> search(String pattern) {
            ArrayList<Contact> tmp = new ArrayList<Contact>(0);

            for(Contact contact : contacts) {
                if(contact.getName().contains(pattern)) {
                    tmp.add(contact);
                    continue;
                }

                for(Fone fone : contact.fones) {
                    if(fone.getNumber().contains(pattern)) {
                        tmp.add(contact);
                        break;
                    }
                }
            }

            return tmp;
        }

        public ArrayList<Contact> getFavorited() {
            ArrayList<Contact> cntsfv = new ArrayList<Contact>();
            for(Contact contact : contacts) {
                if(contact.isFavorited()) cntsfv.add(contact);
            }
            return cntsfv;
        }

        public ArrayList<Contact> getContacts() {
            ArrayList<Contact> contacts = new ArrayList<Contact>();

            for(Contact contact : this.contacts) {
                contacts.add(contact);
            }

            return contacts;
        }
        
    }

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
