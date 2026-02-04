import java.util.*;

// }

public class Shell {
    private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        Board board = new Board(0, 0);

        while (true) {
            String line = scanner.nextLine();
            System.out.println("$" + line);

            var par = line.split(" ");
            var cmd = par[0];
            

            if (cmd.equals("end")) {
                break;
            }
            else if (cmd.equals("init")) {
                var nPlayers = Integer.parseInt(par[1]);
                var size = Integer.parseInt(par[2]);
                board = new Board(nPlayers, size);
            }
            else if (cmd.equals("addTrap")) {
                var pos = Integer.parseInt(par[1]);
                board.addTrap(pos);
            }
            else if (cmd.equals("roll")) {
                var value = Integer.parseInt(par[1]);
                board.rollDice(value);
            }
            else if (cmd.equals("show")) {
                System.out.println(board.toString());
            }
            else {
                System.out.println("invalid command");
            }
        }
        scanner.close();

    }
    static class Board {
        private ArrayList<Integer> trapList;
        private ArrayList<Player> players;
        private boolean running;
        private int boardSize;

        public Board(int nPlayers, int boardSize) {
            this.players = new ArrayList<Player>(nPlayers);
            for(int i = 0; i < nPlayers; i++) {
                this.players.add(new Player(i + 1));
            }
            this.boardSize = boardSize;
            this.trapList = new ArrayList<Integer>();
            this.running = true;
        } 

        public String toString() {
            String str = "";

            for(int i = 0; i < this.players.size(); i++) {
                str += this.players.get(i).toString() + ": ";
                for(int j = 0; j <= boardSize; j++) {
                    if(j != this.players.get(i).pos) {
                        str += ".";
                    } else {
                        str += i+1;
                    }
                }
                str += "\n";
            }

            str += "traps__: ";
            for(int i = 0; i <= boardSize; i++) {
                boolean find = false;
                for(int j = 0; j < this.trapList.size(); j++) {
                    if(i == this.trapList.get(j)) {
                        find = true;
                    } 
                }

                if(find) {
                    str += "x";
                } else {
                    str += ".";
                }
            }

            return str;
        }

        public void addTrap(int pos) {
            this.trapList.add(pos);
        }
        
        int current = 0;
        public void rollDice(int value) {
            if(!this.running) {
                System.out.println("game is over");
            } else if(!this.players.get(current).free && value % 2 != 0) {
                System.out.println(this.players.get(current).toString() + " continua preso");
            } else if(!this.players.get(current).free && value % 2 == 0) {
                System.out.println(this.players.get(current).toString() + " se libertou");
                this.players.get(current).free = true;
            } else {
                this.players.get(current).setPos(this.players.get(current).pos + value);

                if(this.players.get(current).pos >= boardSize) {
                    System.out.println(this.players.get(current).toString() + " ganhou");
                    this.players.get(current).pos = this.boardSize;
                    this.running = false;
                } else {
                    System.out.println(this.players.get(current).toString() + " andou para " + this.players.get(current).pos);
                }
                
                for(int i = 0; i < this.trapList.size(); i++) {
                    if(this.players.get(current).pos == this.trapList.get(i)) {
                        System.out.println(this.players.get(current).toString() + " caiu em uma armadilha");
                        this.players.get(current).setFree(false);
                        break;
                    }
                }
                
            }
            
            if(current + 1 >= this.players.size()) {
                current = 0;
            } else {
                current++;
            }
        }
    }

    static class Player {
        private int label;
        private int pos;
        private boolean free;

        public Player(int label) {
            this.label = label;
            this.pos = 0;
            this.free = true;
        }

        public boolean isFree() {
            return this.free;
        }

        public String toString() {
            return "player" + this.label;
        }

        public int getLabel() {
            return this.label;
        }

        public int getPos() {
            return this.pos;
        }
        
        public void setPos(int pos) {
            this.pos = pos;
        }

        public void setFree(boolean free) {
            this.free = free;
        }
    }
}
