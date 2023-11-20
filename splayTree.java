import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.StringTokenizer;
public class splayTree {
    treeNode root;
    splayTree(){
        root = null;
    }

    void rotateLeft(treeNode treeNode) {
        treeNode temp = treeNode.right;
        treeNode.right = temp.left;
        if(temp.left != null) {
            temp.left.parent = treeNode;
        }
        temp.parent = treeNode.parent;

        if(treeNode.parent == null) { //node is root
            this.root = temp;
        }
        else if(treeNode == treeNode.parent.left) { //node is left
            treeNode.parent.left = temp;
        }
        else { //node is right
            treeNode.parent.right = temp;
        }
        temp.left = treeNode;
        treeNode.parent = temp;
    }
    void rotateRight(treeNode treeNode) {
        treeNode temp = treeNode.left;
        treeNode.left = temp.right;
        if(temp.right != null) {
            temp.right.parent = treeNode;
        }
        temp.parent = treeNode.parent;
        if(treeNode.parent == null) { //node is root
            this.root = temp;
        }
        else if(treeNode == treeNode.parent.right) { //node is right
            treeNode.parent.right = temp;
        }
        else { //node is left
            treeNode.parent.left = temp;
        }
        temp.right = treeNode;
        treeNode.parent = temp;
    }

    treeNode splay(treeNode treeNode){

        while (treeNode.parent != null) {


            treeNode p = treeNode.parent;
            treeNode gp = p.parent;
            if (gp == null && treeNode.equals(p.left)) { //zig left
                rotateRight(p);
            }

            else if (gp == null && treeNode.equals(p.right)) { //zig right
                rotateLeft(p);
            }

            else if (gp != null && gp.left != null && treeNode.equals(gp.left.left)) { //zigZig left
                rotateRight(gp);
                rotateRight(p);

            }

            else if (gp != null && gp.right != null && treeNode.equals(gp.right.right)) { // zigZig right
                rotateLeft(gp);
                rotateLeft(p);
            }

            else if (gp != null && gp.right != null && treeNode.equals(gp.right.left)) { //zigZag right left
                rotateRight(p);
                rotateLeft(gp);
            }

            else if (gp != null && gp.left != null && treeNode.equals(gp.left.right)) {
                rotateLeft(p);
                rotateRight(gp);
            }
        }

        return treeNode;
    }

    void add(long x){

        if (root == null) {
            root = new treeNode(x);
        }

        else if (find(x)) {

        }
        else{
            treeNode newTreeNodeParent = null;
            treeNode temp = root;

            while (temp != null) {
                newTreeNodeParent = temp;
                if (x > temp.data) {
                    temp = temp.right;
                }
                else{
                    temp = temp.left;
                }
            }
            if(newTreeNodeParent != null) {
                if (x > newTreeNodeParent.data) {
                    newTreeNodeParent.right = new treeNode(x);
                    newTreeNodeParent.right.parent = newTreeNodeParent;
                    root = splay(newTreeNodeParent.right);
                } else {
                    newTreeNodeParent.left = new treeNode(x);
                    newTreeNodeParent.left.parent = newTreeNodeParent;
                    root = splay(newTreeNodeParent.left);
                }
            }
        }
    }

    boolean find(long x){
        treeNode temp = root;
        while (temp != null) {
            if (x == temp.data) {
                root = splay(temp);
                return true;
            }
            else if (x > temp.data) {
                temp = temp.right;
            }
            else {
                temp = temp.left;
            }
        }
        return false;
    }

    void del(long x){
        if (find(x)) {

            if (root.left == null) {
                root = root.right;
            }
            else if (root.right == null) {
                root = root.left;
            }
            else{
                treeNode rootleft = root.left;
                treeNode rootRight = root.right;
                if (root.left.right == null) {
                    root = rootleft;
                    rootleft = rootleft.left;
                }
                else {//find maximum in leftSubtree
                    treeNode max = rootleft;
                    while (max.right != null)
                        max = max.right;
                    max.parent.right = max.left;
                    if (max.left != null) {
                        max.left.parent = max.parent;
                    }
                    root = max;
                }
                root.right = rootRight;
                rootRight.parent = root;
                root.left = rootleft;
                if (rootleft != null)
                    rootleft.parent = root;

            }
            if (root != null) {
                root.parent = null;
            }
        }
    }

    long rangeSum(treeNode root, long l, long r)
    {
        if (root == null){
            return 0;
        }

        long sum = 0;
        linkList list = new linkList();

        list.addLast(root);
        while (!list.IsEmpty())
        {
            treeNode current = list.removeFirst();
            if (current.data >= l && current.data <= r)
                sum += current.data;


            if (current.left != null && current.data > l)
                list.addLast(current.left);

            if (current.right != null && current.data < r)
                list.addLast(current.right);

        }

        return sum;
    }
//    public static void main(String[] args) throws IOException {
//        File input = new File("src/input25.txt");
//        File out = new File("src/out.txt");
//        out.createNewFile();
//        FileWriter fw = new FileWriter(out);
//        Scanner scanner = new Scanner(input);
//        int n = Integer.parseInt(scanner.nextLine());
//        String operation;
//        long x, l, r;
//        splayTree tree = new splayTree();
//        for (int i = 0; i < n; i++) {
//
//            operation = scanner.next();
//
//            if (operation.equals("add")) {
//                x = scanner.nextInt();
//                tree.add(x);
//            }
//            else if (operation.equals("find")) {
//                x = scanner.nextInt();
//                fw.write(String.valueOf(tree.find(x))+"\n");
//                fw.flush();
//            }
//            else if (operation.equals("del")) {
//                x = scanner.nextInt();
//                tree.del(x);
//            }
//            else if (operation.equals("sum")) {
//                l = scanner.nextInt();
//                r = scanner.nextInt();
//                fw.write(String.valueOf(tree.rangeSum(tree.root, l, r))+"\n");
//                fw.flush();
//            }
//        }
//        fw.close();
//    }
    /*public static void main(String[] args) throws IOException {

        Scanner scanner = new Scanner();
        int n = scanner.nextInt();
        String operation;
        long x, l, r;
        splayTree tree = new splayTree();
        for (int i = 0; i < n; i++) {
            operation = scanner.next();
            if (operation.equals("add")) {
                x = scanner.nextInt();
                tree.add(x);
            }
            else if (operation.equals("find")) {
                x = scanner.nextInt();
                System.out.println(String.valueOf(tree.find(x))+"\n");
            }
            else if (operation.equals("del")) {
                x = scanner.nextInt();
                tree.del(x);
            }
            else if (operation.equals("sum")) {
                l = scanner.nextInt();
                r = scanner.nextInt();
                System.out.println(String.valueOf(tree.rangeSum(tree.root, l, r))+"\n");
            }
        }
    }*/
//    public static void main(String[] args) {
//        long startTime = System.nanoTime();
//        long x;
//        Random random = new Random();
//        ArrayList<Long> arr = new ArrayList<Long>();
//        splayTree tree = new splayTree();
//        for (int i = 0; i < 100; i++) {
//            for (int j = 0; j < 10000; j++) {
//                x = Math.round((random.nextGaussian() * Math.sqrt(25) + 50));
//                if(!tree.find(x)) {
//                    tree.add(x);
//                    arr.add(x);
//                }
//            }
//            for (int j = 0; j < 10000; j++) {
//                x = random.nextInt(100) + 1;
//                if(!tree.find(x)) {
//                    tree.add(x);
//                    arr.add(x);
//                }
//                tree.find(arr.get((int) (Math.random() * arr.size())));
//            }
//
//        }
//        long endTime = System.nanoTime();
//        long duration = (endTime - startTime);
//        System.out.println(duration);
//    }
    public static void main(String[] args) throws IOException {

        long x;
        Random random = new Random();
        ArrayList<Long> arr = new ArrayList<Long>();
        int number = 0;
        File timeGaussian = new File("src/timeNormal.txt");
        File timeNormal = new File("src/timeGaussian.txt");
        timeNormal.createNewFile();
        timeGaussian.createNewFile();
        FileWriter fw = new FileWriter(timeNormal);
        FileWriter fw2 = new FileWriter(timeGaussian);
        long startTime, endTime;
        for (int i = 0; i < 10000; i++) {
            //Normal ----------------------------------------------------------------------
            startTime = System.nanoTime();
            splayTree tree = new splayTree();
            for (int j = 0; j < number; j++) {
                x = random.nextInt(100) + 1;
                if (!tree.find(x)) {
                    tree.add(x);
                    arr.add(x);
                }
            }
            endTime = System.nanoTime();
            if(i%100 == 0) {
                fw.write(((endTime - startTime)) + " " + number + "\n");
                fw.flush();
            }

            //Gaussian ---------------------------------------------------------------------
            startTime = System.nanoTime();
            tree = new splayTree();
            for (int j = 0; j < number; j++) {
                x =(long) ((random.nextGaussian() * Math.sqrt(25) + 50)+.5);
                if (!tree.find(x)) {
                    tree.add(x);
                    arr.add(x);
                }
            }
            endTime = System.nanoTime();
            if(i%100 == 0) {
                fw2.write(((endTime - startTime)) + " " + number + "\n");
                fw2.flush();
            }

            number += 50;
        }
    }
}

class linkNode {
    treeNode data;
    linkNode next;
    linkNode(treeNode data){
        this.data = data;
        next = null;
    }
}
class linkList {
    linkNode head = new linkNode(null);
    linkNode tail = head;
    int counter = 0;

    void addLast(treeNode data){
        linkNode newNode = new linkNode(data);
        tail.next = newNode;
        tail = newNode;
        newNode = null;
        counter++;
    }

    treeNode removeFirst(){
        if(IsEmpty())
            return null;
        else {
            head = head.next;
            counter--;
            return head.data;
        }
    }

    boolean IsEmpty(){
        return counter == 0;
    }
}

class Scanner {
    BufferedReader br;
    StringTokenizer st;

    public Scanner()
    {
        br = new BufferedReader(
                new InputStreamReader(System.in));
    }

    String next()
    {
        while (st == null || !st.hasMoreElements()) {
            try {
                st = new StringTokenizer(br.readLine());
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        return st.nextToken();
    }

    int nextInt() { return Integer.parseInt(next()); }

    long nextLong() { return Long.parseLong(next()); }

    double nextDouble()
    {
        return Double.parseDouble(next());
    }

    String nextLine()
    {
        String str = "";
        try {
            if(st.hasMoreTokens()){
                str = st.nextToken("\n");
            }
            else{
                str = br.readLine();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return str;
    }
}

class treeNode {
    long data;
    treeNode parent;
    treeNode right;
    treeNode left;
    public treeNode(long data){
        this.data = data;
        parent = null;
        left = null;
        right = null;
    }
}


