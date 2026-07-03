import java.util.*;

public class Shell {
    public static void main(String[] a) {
        Pig pig = new Pig(0);
        while (true) {
            var line = scanner.nextLine();
            System.out.println("$" + line);
            var args = line.split(" ");
            var cmd = args[0];
            
            try {
                if (cmd.equals("end")) {
                    break;
                }
                else if(cmd.equals("show")) { 
                    System.out.println(pig);
                }
                else if(cmd.equals("addCoin")) {
                    var value = Integer.parseInt(args[1]);
                    Coin c;
                    if(value == 10) c = Coin.M10; 
                    else if(value == 25) c = Coin.M25;
                    else if(value == 50) c = Coin.M50;
                    else if(value == 100) c = Coin.M100;
                    else throw new MsgException("fail: moeda invalida");
                    pig.addValuable(c);
                }
                else if(cmd.equals("init")) {
                    var volume = Integer.parseInt(args[1]);
                    pig = new Pig(volume);
                }
                else if(cmd.equals("addItem")) {
                    var label = args[1];
                    var value = Double.parseDouble(args[2]);
                    var volume = Integer.parseInt(args[3]);
                    Item i = new Item(label, volume, value);
                    pig.addValuable(i);
                }
                else if(cmd.equals("break")) {
                    pig.breakPig();
                }
                else if(cmd.equals("extractCoins")) { 
                    System.out.println(pig.getCoins());
                }
                else if(cmd.equals("extractItems")) { 
                    System.out.println(pig.getItems());
                }
                else {
                    System.out.println("fail: comando invalido");
                }
            } catch (MsgException e) {
                System.out.println(e.getMessage());
            } catch (BrokenException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private static Scanner scanner = new Scanner(System.in);

    public static class Pig {
        private boolean broken;
        private ArrayList<Valuable> valuables;
        private int volumeMax;

        public Pig(int volumeMax) {
            this.volumeMax = volumeMax;
            this.broken = false;
            this.valuables = new ArrayList<Valuable>();
        }

        public boolean addValuable(Valuable valuable) throws MsgException {
            if(isBroken()) throw new MsgException("fail: the pig is broken");
            if(this.getVolume() + valuable.getVolume() > this.getVolumeMax()) throw new MsgException("fail: the pig is full");
            this.valuables.add(valuable);
            return true;
        }

        public boolean breakPig() throws MsgException {
            if(this.isBroken()) {
                throw new MsgException("fail: ja quebrado");
            }

            return this.broken = true;
        }

        public ArrayList<Coin> getCoins() throws BrokenException {
            if(!this.isBroken()) throw new BrokenException();
            ArrayList<Coin> coins = new ArrayList<Coin>();
            for(int i = 0; i < this.valuables.size(); i++) {
                Valuable v = this.valuables.get(i);
                if(v instanceof Coin) {
                    coins.add((Coin) v);
                    this.valuables.remove(i);
                    i--;
                }
            }

            return coins;
        }
        
        public ArrayList<Item> getItems() throws BrokenException {
            if(!this.isBroken()) throw new BrokenException();
            ArrayList<Item> items = new ArrayList<Item>();

            for(int i = 0; i < this.valuables.size(); i++) {
                Valuable v = this.valuables.get(i);
                if(v instanceof Item) {
                    items.add((Item) v);
                    this.valuables.remove(i);
                    i--;
                }
            }

            return items;
        }

        public double calcValue() {
            double value = 0;
            for(Valuable val : this.valuables) {
                value += val.getValue();
            }
            return value;
        }
        
        public int getVolume() {
            int volume = 0;
            for(Valuable val : this.valuables) {
                volume += val.getVolume();
            }
            if(isBroken()) return 0;
            else return volume;
        }

        public int getVolumeMax() {
            return this.volumeMax;
        }

        public boolean isBroken() {
            return this.broken;
        }

        public String toString() {
            String st = "[";
            int i = 0;
            for(Valuable val : this.valuables) {
                if(i != 0) st += ", ";
                st += val;
                i++;
            }
            st += String.format(Locale.US, "] : %.2f$ : %d/%d : %s", 
                    this.calcValue(), this.getVolume(), this.getVolumeMax(), (this.isBroken()) ? "broken" : "intact");
            return st;
        }
    }

    public static class Item implements Valuable {
        private String label;
        private double value;
        private int volume;

        public Item(String label, int volume, double value) {
            this.label = label;
            this.volume = volume;
            this.value = value;
        }

        public String getLabel() {
            return this.label;
        }
        public double getValue() {
            return this.value;
        }
        public int getVolume() {
            return volume;
        }

        public void setLabel(String label) {
            this.label = label;
        }
        public void setVolume(int volume) {
            this.volume = volume;
        }

        public String toString() {
            return String.format(Locale.US, "%s:%.2f:%d", this.getLabel(), this.getValue(), this.getVolume());
        }
    }

    public static enum Coin implements Valuable{
        M10("M10", 0.10, 1),        
        M25("M25", 0.25, 2),
        M50("M50", 0.50, 3),
        M100("M100", 1.00, 4);

        private String label;
        private double value;
        private int volume;
        
        private Coin(String label, double value, int volume) {
            this.label = label;
            this.value = value;
            this.volume = volume;
        }

        public String getLabel() {
            return this.label;
        }
        public double getValue() {
            return this.value;
        }
        public int getVolume() {
            return this.volume;
        }

        public String toString() {
            return String.format(Locale.US, "%s:%.2f:%d", this.getLabel(), this.getValue(), this.getVolume());
        }
    }

    public static interface Valuable {
        public String getLabel();
        public double getValue();
        public int getVolume();
    }

    public static class MsgException extends Exception {
        public MsgException(String msg) {
            super(msg);
        }
    }

    public static class BrokenException extends Exception {
        public BrokenException() {
            super("fail: you must break the pig first");
        }
    }
}
