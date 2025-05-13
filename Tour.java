public class Tour {
    private class Node {
        private Point p;
        private Node next;

        public Node() {}

        public Node(Point p, Node next) {
            this.p = p;
            this.next = next;
        }
    }

    private Node start;

    public Tour() {
        start = new Node();
    }

    public Tour(Point a, Point b, Point c, Point d) {
        Node na = new Node(a, null);
        Node nb = new Node(b, null);
        Node nc = new Node(c, null);
        Node nd = new Node(d, null);
        na.next = nb;
        nb.next = nc;
        nc.next = nd;
        nd.next = na;
        start = na;
    }

    public int size() {
        if (start == null || start.p == null) return 0;
        int count = 1;
        Node current = start.next;
        while (current != start) {
            count++;
            current = current.next;
        }
        return count;
    }

    public double length() {
        if (start == null || start.p == null) return 0.0;
        double distance = 0.0;
        Node current = start;
        do {
            distance += current.p.distanceTo(current.next.p);
            current = current.next;
        } while (current != start);
        return distance;
    }

    public String toString() {
        if (start == null || start.p == null) return "";
        StringBuilder sb = new StringBuilder();
        Node current = start;
        do {
            sb.append(current.p.toString()).append("\n");
            current = current.next;
        } while (current != start);
        return sb.toString();
    }

    public void draw() {
        if (start == null || start.p == null) return;
        Node current = start;
        do {
            current.p.drawTo(current.next.p);
            current = current.next;
        } while (current != start);
    }

    public void insertNearest(Point p) {
        Node newNode = new Node(p, null);
        if (start == null || start.p == null) {
            start.p = p;
            start.next = start;
            return;
        }

        Node closest = start;
        double minDistance = p.distanceTo(start.p);
        Node current = start.next;
        while (current != start) {
            double dist = p.distanceTo(current.p);
            if (dist < minDistance) {
                minDistance = dist;
                closest = current;
            }
            current = current.next;
        }

        newNode.next = closest.next;
        closest.next = newNode;
    }

    public void insertSmallest(Point p) {
        Node newNode = new Node(p, null);
        if (start == null || start.p == null) {
            start.p = p;
            start.next = start;
            return;
        }

        if (start.next == start) {
            Node second = new Node(start.p, start);
            start.p = p;
            start.next = second;
            return;
        }

        Node bestNode = start;
        double minIncrease = Double.POSITIVE_INFINITY;
        Node current = start;
        do {
            double increase = current.p.distanceTo(p) + p.distanceTo(current.next.p)
                    - current.p.distanceTo(current.next.p);
            if (increase < minIncrease) {
                minIncrease = increase;
                bestNode = current;
            }
            current = current.next;
        } while (current != start);

        newNode.next = bestNode.next;
        bestNode.next = newNode;
    }

public static void main(String[] args) {
    // define 4 pontos
    Point a = new Point(1.0, 1.0);
    Point b = new Point(1.0, 4.0);
    Point c = new Point(4.0, 4.0);
    Point d = new Point(4.0, 1.0);
    Point e = new Point(2.5, 3.0);

    // cria o ciclo a -> b -> c -> d -> a
    Tour tour = new Tour(a, b, c, d);

    // insere pelo menor aumento
    tour.insertSmallest(e);

    // imprime resultados
    StdOut.println("Após insertSmallest:");
    StdOut.println(tour);
    StdOut.println("Comprimento = " + tour.length());

    // desenha
    StdDraw.setXscale(0, 6);
    StdDraw.setYscale(0, 6);
    tour.draw();
}

}
