
public class Main {
    public static void main(String[] args) {
        Port port1 = new Port (01, "Alpha", 1, 1, 20, 30, 5);
        Port port2 = new Port (02, "Beta", 2, 2, 30, 20, 10);
        System.out.println(port1.getDistanceBetweenPorts(port2));
    }
}