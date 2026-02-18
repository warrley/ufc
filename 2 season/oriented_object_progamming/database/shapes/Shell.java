
import java.util.*;

public class Shell {
    public static void main(String[] a) {
        ArrayList<Shape> shapes = new ArrayList<Shape>();

        while (true) {
            var line = scanner.nextLine();
            System.out.println("$" + line);
    
            var par = line.split(" ");
            var cmd = par[0];
            
            if (cmd.equals("end")) {
                break;
            }
            else if (cmd.equals("show")) {
                String st = "";
                for(Shape s : shapes) {
                    st += s;
                    System.out.println(st);
                }
            }
            else if (cmd.equals("circle")) {
                var x = Double.parseDouble(par[1]);
                var y = Double.parseDouble(par[2]);
                var radius = Double.parseDouble(par[3]);
                shapes.add(new Circle(new Point2D(x, y), radius));
            }
            else if (cmd.equals("rect")) {
                var x1 = Double.parseDouble(par[1]);
                var y1 = Double.parseDouble(par[2]);
                var x2 = Double.parseDouble(par[3]);
                var y2 = Double.parseDouble(par[4]);
                shapes.add(new Rectangle(new Point2D(x1, y1), new Point2D(x2, y2)));
            }
            else if (cmd.equals("info")) {
                String st = "";
                for(Shape s : shapes) {
                    st += String.format(Locale.US, "%s: A=%.2f P=%.2f", s.getName(), s.getArea(), s.getPerimeter());
                }
                System.out.println(st);
            }
            else {
                System.out.println("Comando inválido");
            }
        }
    }

    private static Scanner scanner = new Scanner(System.in);

    public static class Rectangle implements Shape {
        private Point2D p1;
        private Point2D p2;

        public Rectangle(Point2D p1, Point2D p2) {
            this.p1 = p1;
            this.p2 = p2;
        }

        public String getName() {
            return "Rect";
        }
    
        public boolean inside(Point2D p)  {
            if(p.x > p1.x && p.x < p2.x && p.y < p1.y && p.y > p2.y) return true;
            return false;
        }
    
        public double getArea() {
            return (p1.y - p2.y) * (p2.x - p1.x);
        }
        
        public double getPerimeter() {
            return 2 * ((p1.y - p2.y) + (p2.x - p1.y));
        }
        
        public String toString() {
            return String.format(Locale.US, "%s: P1=%s, P2=%s", this.getName(), this.p1, this.p2);
        }
    }

    public static class Circle implements Shape {
        private Point2D center;
        private double radius;

        public Circle(Point2D center, double radius) {
            this.center = center;
            this.radius = radius;
        }

        public String getName() {
            return "Circ";
        }

        public boolean inside(Point2D point)  {
            if(center.distance(point) > radius) return false;
            return true;
        }

        public double getArea() {
            return Math.PI * radius * radius;
        }

        public double getPerimeter() {
            return Math.PI * 2 * radius;
        }

        public String toString() {
            return String.format(Locale.US, "%s: C=%s, R=%.2f", this.getName(), this.center, this.radius);
        }
    }

    public static interface Shape {
        public String getName();
        public boolean inside(Point2D point);
        public double getArea();
        public double getPerimeter();
    }

    public static class Point2D {
        public double x;
        public double y;

        public Point2D(double x, double y) {
            this.x = x;
            this.y = y;
        }

        public double distance(Point2D p) {
            double dx = this.x - p.x;
            double dy = this.y - p.y;

            return Math.sqrt((dx * dx) + (dy * dy));
        }

        public String toString() {
            return String.format(Locale.US, "(%.2f, %.2f)", this.x, this.y);
        }
    }

    public static class MsgException extends Exception {
        public MsgException(String msg) {
            super(msg);
        }
    }
}
