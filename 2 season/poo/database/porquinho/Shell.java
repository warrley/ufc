import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

public class Shell {
    public static void main(String[] _args) {
        Pig pig = new Pig(0);

        while (true) {
            var line = scanner.nextLine();
            System.out.println("$" + line);

            var par = line.split(" ");
            var cmd = par[0];

            if (cmd.equals("end")) {
                break;
            }
            else if (cmd.equals("init")) {
                int maxVolume = Integer.parseInt(par[1]);
                pig = new Pig(maxVolume);
            }
            else if (cmd.equals("show")) {
                System.out.println(pig.toString());
            }
            else if (cmd.equals("addCoin")) {
                int value = Integer.parseInt(par[1]);
                if(value == 10)       pig.addCoin(Coin.C10);
                else if(value == 25)  pig.addCoin(Coin.C25);
                else if(value == 50)  pig.addCoin(Coin.C50);
                else if(value == 100) pig.addCoin(Coin.C100);
            }
            else if (cmd.equals("addItem")) {
                var item = par[1];
                var volume = Integer.parseInt(par[2]);
                Item i = new Item(item, volume);
                pig.addItem(i);
            }
            else if (cmd.equals("break")) {
                pig.breakPig();
            }
            else if (cmd.equals("extractCoins")) {
                // Obtenha as moedas usando o método extractCoins
                // Imprima as moedas obtidas
                if(!pig.isBroken()) {
                    System.out.println("fail: you must break the pig first\n[]");
                } else {
                    ArrayList<Coin> coins = pig.extractCoins();
                    System.out.println(coins);
                }
            }
            else if (cmd.equals("extractItems")) {
                // Obtenha os itens usando o método extractItems
                // Imprima os itens obtidos
                ArrayList<Item> items = pig.extractItems();
                System.out.println(items);
            }
            else {
                System.out.println("fail: invalid command");
            }
        }
    }

    public static Scanner scanner = new Scanner(System.in);

    static class Pig {
        private Boolean broken;
        private ArrayList<Coin> coins;
        private ArrayList<Item> items;
        private int volumeMax;

        public Pig(int volumeMax) {
            this.volumeMax = volumeMax;
            this.broken = false;
            this.coins = new ArrayList<Coin>(0);
            this.items = new ArrayList<Item>(0);
        }
        
        @Override
        public String toString() {
            String state = (broken) ? "broken" : "intact";
            int volumec = getVolume();
            double value = 0;
            
            StringBuilder coins = new StringBuilder();
            for(int i = 0; i < this.coins.size(); i++) {
                if(i != 0) coins.append(", ");
                coins.append(this.coins.get(i).toString());
                value += this.coins.get(i).getValue();
            }
            
            StringBuilder items = new StringBuilder();
            for(int i = 0; i < this.items.size(); i++) {
                if(i != 0) items.append(", ");
                items.append(this.items.get(i).toString());
            }
            String volume = volumec + "/" + this.getVolumeMax();
            
            String sb = String.format(Locale.US, "state=%s : coins=[%s] : items=[%s] : value=%.2f : volume=%s", state, coins.toString(), items.toString(), value, volume);
            
            return sb;
        }

        public ArrayList<Coin> extractCoins() {
            ArrayList<Coin> tmp = coins;
            coins = new ArrayList<Coin>(0);
            return tmp;
        }
        
        public ArrayList<Item> extractItems() {
            ArrayList<Item> tmp = new ArrayList<Item>(0);
            if(!isBroken()) {
                System.out.println("fail: you must break the pig first");
            } else {
                tmp = items;
                items = new ArrayList<Item>(0);
            }
            return tmp;
        }

        public boolean breakPig() {
            return broken = true;
        }
        
        public boolean addCoin(Coin coin) {
            if(isBroken()) {
                System.out.println("fail: the pig is broken");
                return false;
            } else if(getVolume() + coin.getVolume() > getVolumeMax()) {
                System.out.println("fail: the pig is full");
                return false;
            }
            coins.add(coin);
            return true;
        }

        public boolean addItem(Item item) {
            if(isBroken()) {
                System.out.println("fail: the pig is broken");
                return false;
            } else if(getVolume() + item.getVolume() > getVolumeMax()) {
                System.out.println("fail: the pig is full");
                return false;
            }
            items.add(item);
            return true;
        }

        public Boolean isBroken() {
            return this.broken;
        }

        public ArrayList<Coin> getCoins() {
            return this.coins;
        }

        public ArrayList<Item> getItems() {
            return this.items;
        }

        public int getVolume() {
            int total = 0;

            for(Coin coin : coins) {
                total += coin.getVolume();
            }
            
            for(Item item : items) {
                total += item.getVolume();
            }
            if(isBroken()) {
                return 0;
            } else {
                return total;
            }
        }

        public int getVolumeMax() {
            return this.volumeMax;
        }

	}

    static class Coin {
        private final double value;
        private final int volume;
        private final String label;

        public static final Coin C10  = new Coin(0.10, 1, "C10");
        public static final Coin C25  = new Coin(0.25, 2, "C25");
        public static final Coin C50  = new Coin(0.50, 3, "C50");
        public static final Coin C100 = new Coin(1.00, 4, "C100");

        public Coin(double value, int volume, String label) {
            this.value = value;
            this.volume = volume;
            this.label = label;
        }

        @Override
        public String toString() {
            return String.format(Locale.US, "%.2f:%d", value, volume);
        }

        public double getValue() {
            return this.value;
        }

        public int getVolume() {
            return this.volume;
        }

        public String getLabel() {
            return this.label;
        }

    }

    public static class Item {
        private String label;
        private int volume;
        
        public Item(String label, int volume) {
            this.label = label;
            this.volume = volume;
        }

        @Override
        public String toString() {
            return String.format("%s:%d", label, volume);
        }

        public String getLabel() {
            return this.label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public int getVolume() {
            return this.volume;
        }

        public void setVolume(int volume) {
            this.volume = volume;
        }

    }
}
