import java.util.ArrayList;

public class draft {
    public static void main(String args[]) {

        // Array
        int[] num = new int[5];

        num[0] = 52;
        num[1] = 100;
        num[2] = 57;
        num[3] = 578;
        num[4] = 02;

        for(int i = 0; i < num.length; i++) {
            System.out.println(num[i]);
        }
        
        int[] num2 = {52, 100, 57, 587, 0};
        
        for(int n:num2) {
            System.out.println(n);
        }

        //ArrayList -> addiciona automaticamente, sem se preocupar com o tamanho dele
        //tem que passar uma Classe como tipo do array
        //Primitimos int -> Integer, float -> Float, double -> Double, char -> Character
        ArrayList<String> str = new ArrayList<String>();
        str.add("guilherme");
        str.add("warley");
        str.add("brito");
        str.add("farias");

        for(String s:str) {
            System.out.println(s);
        }

        //metodos -> .size() = tamanho do array, .get(index) = pega o elemento passado, .remove(index) = remove o elemento passado, .clear() = limpa


        //LinkedList -> 
        LinkedList<Integer> list = new LinkedList<>();
        list.add(1);

        System.out.println(list);
    }

    //LikedList -> 
    
    static class LinkedList<T> {
        private Node<T> start;

        public void add(T element) {
            Node<T> box = new Node<T>(element);
            this.start = box;
        }

        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("Node [element=").append(start);
            return builder.toString();
        }
    }

    static class Node<T> {
        private T element;
        private Node<T> next;

        public Node(T element) {
            this.element = element;
            this.next = null;
        }
        
        public Node(T element, Node<T> next) {
            this.element = element;
            this.next = next;
        }

        public T getElement() {
            return element;
        }

        public void setElement(T element) {
            this.element = element;
        }

        public Node<T> getProximo() {
            return this.next;
        }

        public void setProximo(Node<T> next) {
            this.next = next;
        }

        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();
            builder.append("Node [element=").append(element).append(". next=").append(next).append("]");
            return builder.toString();
        }

    }
}

