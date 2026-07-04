import java.util.*;

public class Shell {
    public static void main(String[] a) {
        
        Pet pet = new Pet(0, 0);
        Game game = new Game(null);

        while (true) {
            var line = scanner.nextLine();
            System.out.println("$" + line);

            var par = line.split(" ");
            var cmd = par[0];

            if (cmd.equals("end")) {
                break;
            }
            else if (cmd.equals("init")) {
                // CRIE UM NOVO JOGO COM UM NOVO PET
                var energy = Integer.parseInt(par[1]);
                var clean = Integer.parseInt(par[2]);
                pet = new Pet(energy, clean);
                game = new Game(pet);
            }
            else if (cmd.equals("show")) { 
                System.out.println(pet.toString());
            }
            else if (cmd.equals("play")) { 
                if(!game.testAlive()) {
                    System.out.println("fail: pet esta morto");
                } else {
                    game.play();
                }
            }
            else if (cmd.equals("shower")) { 
                if(!game.testAlive()) {
                    System.out.println("fail: pet esta morto");
                } else {
                    game.shower();
                }
            }
            else if (cmd.equals("sleep")) { 
                if(!game.testAlive()) {
                    System.out.println("fail: pet esta morto");
                } else {
                    game.sleep();
                }
            }
            else {
                System.out.println("fail: comando invalido");
            }
        }
    }

    private static Scanner scanner = new Scanner(System.in);
    
    static class Game {
        private Pet pet;

        public Game(Pet pet) {
            this.pet = pet;
        }

        public void play() {
            this.pet.setClean(this.pet.getClean() - 3);
            this.pet.setEnergy(this.pet.getEnergy() - 2);
            this.pet.setAge(this.pet.getAge() + 1);
            
            if(this.pet.getClean() <= 0) {
                System.out.println("fail: pet morreu de sujeira");
                this.pet.setClean(0);
            }
            if(this.pet.getEnergy() <= 0) {
                System.out.println("fail: pet morreu de fraqueza");
                this.pet.setEnergy(0);
            }
        }
        
        public void sleep() {
            if(this.pet.getEnergyMax() - this.pet.getEnergy() < 5) {
                System.out.println("fail: nao esta com sono");
            } else {
                int slept = this.pet.getEnergyMax() - this.pet.getEnergy();
                this.pet.setEnergy(this.pet.getEnergyMax());
                this.pet.setAge(this.pet.getAge() + slept);
            }
        }
        
        public void shower() {
            this.pet.setClean(this.pet.getCleanMax());
            this.pet.setEnergy(this.pet.getEnergy() - 3);
            this.pet.setAge(this.pet.getAge() + 2);
            
            if(this.pet.getEnergy() <= 0) {
                System.out.println("fail: pet morreu de fraqueza");
                this.pet.setEnergy(0);
            }
        }

        public boolean testAlive() {
            return this.pet.isAlive();
        }
    }

    static class Pet {
        private int energyMax;
        private int energy;
        private int cleanMax;
        private int clean;
        private boolean alive;
        private int age;

        public Pet(int energyMax, int cleanMax) {
            this.energyMax = energyMax;
            this.energy = energyMax;
            this.cleanMax = cleanMax;
            this.clean = cleanMax;
            this.age = 0;
            this.alive = true;
        }

        public boolean isAlive() {
            if(this.getClean() <= 0 || this.getEnergy() <= 0) {
                this.alive = false;
                return this.alive;
            } else {
                this.alive = true;
                return this.alive;
            }        }

        public int getCleanMax() {
            return this.cleanMax;
        }
        
        public int getClean() {
            return this.clean;
        }

        public void setClean(int clean) {
            this.clean = clean;
        }
        
        public int getEnergyMax() {
            return this.energyMax;
        }
        
        public int getEnergy() {
            return this.energy;
        }
        
        public void setEnergy(int energy) {
            this.energy = energy;
        }

        public int getAge() {
            return this.age;
        }
        
        public void setAge(int age) {
            this.age = age;
        }

        public String toString() {
            return "E:"+this.energy+"/"+this.energyMax+", L:"+this.clean+"/"+this.cleanMax+", I:"+this.age;
        }
    }

}
