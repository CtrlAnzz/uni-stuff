package drva.binaryTree;
import java.util.*;

public class Drva3 {
    private static BNode<String> find(String element, BNode<String> node) {
        if (node == null){
            return null;
        }
        if (Objects.equals(node.info, element)){
            return node;
        }

        BNode<String> left = find(element, node.left);
        BNode<String> right = find(element, node.right);
        if (left != null) {
            return left;
        }
        return right;
    }

    private static int countNodesWithTwoChildren(BNode<String> node) {
        if (node == null){
            return 0;
        }

        int left = countNodesWithTwoChildren(node.left);
        int right = countNodesWithTwoChildren(node.right);

        if (node.left != null && node.right != null){ //imat 2 deca toj jazol
            return left + right + 1;
        }

        return left + right;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        int q = sc.nextInt();
        sc.nextLine();

        BTree<String> tree = new BTree<>();

        for (int i = 0; i < n+q; i++) {
            String []parts = sc.nextLine().split(" ");
            switch (parts[0]){
                case "root":
                    tree.makeRoot(parts[1]);
                    break;
                case "add":
                    BNode<String> node = find(parts[1], tree.root);
                    if (parts[3].equals("LEFT")){
                        tree.addChild(node, BNode.LEFT, parts[2]);
                    }
                    else if (parts[3].equals("RIGHT")){
                        tree.addChild(node, BNode.RIGHT, parts[2]);
                    }
                    break;
                case "ask":
                    BNode<String> nodeToAsk = find(parts[1], tree.root);
                    if (nodeToAsk != null){
                        int num = countNodesWithTwoChildren(nodeToAsk);
                        System.out.println(num);
                    }
            }
        }
    }
}
