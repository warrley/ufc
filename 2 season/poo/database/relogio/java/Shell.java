import java.util.*;


public class Shell {
    public static void main(String[] a) {
        Watch relogio = new Watch(0,0,0);
        
        while (true) {
            var line = scanner.nextLine();
            System.out.println("$" + line);

            var par = line.split(" ");
            var cmd = par[0];

            if (cmd.equals("end")) {
                break;
            }
            else if (cmd.equals("show")) {
                // MOSTRE O RELÓGIO AQUI
                System.out.println(relogio.toString());
            }
            else if (cmd.equals("init")) {
                // INICIE O RELÓGIO AQUI utilizando o construtor
                int hour = Integer.parseInt(par[1]);
                int minute = Integer.parseInt(par[2]);
                int second = Integer.parseInt(par[3]);
                relogio = new Watch(hour, minute, second);
                // if(!relogio.setHour(hour)) System.out.println("fail: hora invalida");
                // if(!relogio.setMinute(minute)) System.out.println("fail: minuto invalido");
                // if(!relogio.setSecond(second)) System.out.println("fail: segundo invalido");
            }
            else if (cmd.equals("set")) {
                // CHAME OS MÉTODOS SET AQUI
                int hour = Integer.parseInt(par[1]);
                int minute = Integer.parseInt(par[2]);
                int second = Integer.parseInt(par[3]);
                relogio.setHour(hour);
                relogio.setMinute(minute);
                relogio.setSecond(second);
            }
            else if (cmd.equals("next")) {
                // CHAME O MÉTODO nextSecond AQUI
                relogio.nextSecond();
            }
            else {
                System.out.println("fail: comando invalido");
            }
        }
    }

    private static Scanner scanner = new Scanner(System.in);

    static class Watch {
        private int hour;
        private int minute;
        private int second;
        // private int totalSeconds;

        public Watch(int hour, int minute, int second) {
            setHour(hour);
            setMinute(minute);
            setSecond(second);
        }

        public int getHour() {
            return this.hour;
        }

        public int getMinute() {
            return this.minute;
        }
        
        public int getSecond() {
            return this.second;
        }

        public void setHour(int value) {
            if(value >= 0 && value <= 23) {
                this.hour = value;
            } else {
                System.out.println("fail: hora invalida");
            }
        }

        public void setMinute(int value) {
            if(value >= 0 && value <= 59) {
                this.minute = value;
            } else {
                System.out.println("fail: minuto invalido");
            }
        }      
        
        public void setSecond(int value) {
            if(value >= 0 && value <= 59) {
                this.second = value;
            } else {
                System.out.println("fail: segundo invalido");
            }
        }

        public void nextSecond() {
            this.second++;
            if(this.second == 60) {
                this.second = 0;
                this.minute++;
                if(this.minute == 60) {
                    this.minute = 0;
                    this.hour++;
                    if(hour == 24) {
                        this.hour = 0;
                    }
                }
            }
        }

        public String toString() {
            return String.format("%02d:%02d:%02d", hour, minute, second);
        }   

    }
}
