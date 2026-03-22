import java.util.Scanner;
import java.util.ArrayList;

public class Shell {
    public static void main(String[] args) {
        Trampoline tp = new Trampoline();

        while (true) {
            var line = scanner.nextLine();
            System.out.println("$" + line);

            var par = line.split(" ");
            var cmd = par[0];

            if (cmd.equals("end")) {
                break;
            } 
            else if (cmd.equals("show")) { 
                System.out.println(tp.toString());
            } 
            else if (cmd.equals("arrive")) {
                var name = par[1];
                var age = Integer.parseInt(par[2]);
                Kid kid = new Kid(name, age);
                tp.arrive(kid);
            } 
            else if (cmd.equals("enter")) {
                tp.enter();
            } 
            else if (cmd.equals("leave")) {
                tp.leave();
            } 
            else if (cmd.equals("remove")) {
                var name = par[1];
                Kid tmp = tp.removeKid(name);
                if (tmp == null) {
                    System.out.println("fail: " + name + " nao esta no pula-pula");
                }
            } 
            else {
                System.out.println("fail: comando invalido");
            }
        }

    }

    private static Scanner scanner = new Scanner(System.in);


    static class Trampoline {
        private ArrayList<Kid> playing;
        private ArrayList<Kid> waiting;

        public Trampoline() {
            this.playing = new ArrayList<Kid>();
            this.waiting = new ArrayList<Kid>();
        }

        public String toString() {
            StringBuilder string = new StringBuilder();
            
            string.append("[");
            for(int i = this.waiting.size() - 1; i >= 0; i--) {
                string.append(this.waiting.get(i).toString());
                if(i != 0) string.append(", ");
            }
            
            string.append("] => [");
            
            for(int i = this.playing.size() - 1; i >= 0; i--) {
                string.append(this.playing.get(i).toString());
                if(i != 0) string.append(", ");
            }
            
            string.append("]");

            return string.toString();
        }

        public void arrive (Kid kid) {
            this.waiting.add(kid);
        }

        public void enter() {
            Kid tmp = removeKid(this.waiting.get(0).getName());
            this.playing.add(tmp);
        }
        
        public void leave() {
            if(this.playing.size() > 0) {
                Kid tmp = this.playing.get(0);
                this.playing.remove(0);
    
                this.waiting.add(tmp);
            }
        }
        
        public Kid removeKid (String name) {
            Kid tmp = new Kid("", 0);
            
            int pfound = -1;
            for(int i = 0; i < this.playing.size(); i++) {
                if(this.playing.get(i).getName().equals(name)) pfound = i;
            }
            
            if(pfound != -1) {
                tmp.setAge(this.playing.get(pfound).getAge());
                tmp.setName(this.playing.get(pfound).getName());
                this.playing.remove(pfound);
            }
            
            int wfound = -1;
            for(int i = 0; i < this.waiting.size(); i++) {
                if(this.waiting.get(i).getName().equals(name)) wfound = i;
            }
            
            if(wfound != -1) {
                tmp.setAge(this.waiting.get(wfound).getAge());
                tmp.setName(this.waiting.get(wfound).getName());
                this.waiting.remove(wfound);
            }

            if(wfound != -1 || pfound != -1) {
                return tmp;
            } else {
                return null;
            }
        }
    }

    static class Kid {
        private int age;
        private String name;

        public Kid(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String toString() {
            StringBuilder string = new StringBuilder();
            string.append(this.name).append(":").append(age);

            return string.toString();
        }

        public int getAge() {
            return this.age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        public String getName() {
            return this.name;
        }

        public void setName(String name) {
            this.name = name;
        }
        
        
    }


}
