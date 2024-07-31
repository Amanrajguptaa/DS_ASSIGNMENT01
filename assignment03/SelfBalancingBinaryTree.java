package assignment03;
class SelfBalancingBinaryTree {
    private class Node {
        int key, height, size;
        Node left, right;

        Node(int d) {
            key = d;
            height = 1;
            size = 1;
        }
    }

    private Node root;

    private int height(Node N) {
        return N == null ? 0 : N.height;
    }

    private int size(Node N) {
        return N == null ? 0 : N.size;
    }

    private Node rightRotate(Node y) {
        Node x = y.left;
        Node T2 = x.right;
        x.right = y;
        y.left = T2;
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        y.size = size(y.left) + size(y.right) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        x.size = size(x.left) + size(x.right) + 1;
        return x;
    }

    private Node leftRotate(Node x) {
        Node y = x.right;
        Node T2 = y.left;
        y.left = x;
        x.right = T2;
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        x.size = size(x.left) + size(x.right) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        y.size = size(y.left) + size(y.right) + 1;
        return y;
    }

    private int getBalance(Node N) {
        return N == null ? 0 : height(N.left) - height(N.right);
    }

    public boolean find(int key) {
        return find(root, key);
    }

    private boolean find(Node node, int key) {
        if (node == null) return false;
        if (key < node.key) return find(node.left, key);
        else if (key > node.key) return find(node.right, key);
        else return true;
    }

    public void insert(int key) {
        root = insert(root, key);
    }

    private Node insert(Node node, int key) {
        if (node == null) return new Node(key);
        if (key < node.key) node.left = insert(node.left, key);
        else if (key > node.key) node.right = insert(node.right, key);
        else return node;

        node.height = 1 + Math.max(height(node.left), height(node.right));
        node.size = 1 + size(node.left) + size(node.right);

        int balance = getBalance(node);
        if (balance > 1 && key < node.left.key) return rightRotate(node);
        if (balance < -1 && key > node.right.key) return leftRotate(node);
        if (balance > 1 && key > node.left.key) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
        if (balance < -1 && key < node.right.key) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }
        return node;
    }

    public void remove(int key) {
        root = remove(root, key);
    }

    private Node remove(Node root, int key) {
        if (root == null) return root;

        if (key < root.key) root.left = remove(root.left, key);
        else if (key > root.key) root.right = remove(root.right, key);
        else {
            if ((root.left == null) || (root.right == null)) {
                Node temp = null;
                if (temp == root.left) temp = root.right;
                else temp = root.left;

                if (temp == null) {
                    temp = root;
                    root = null;
                } else root = temp;
            } else {
                Node temp = minValueNode(root.right);
                root.key = temp.key;
                root.right = remove(root.right, temp.key);
            }
        }
        if (root == null) return root;

        root.height = Math.max(height(root.left), height(root.right)) + 1;
        root.size = size(root.left) + size(root.right) + 1;

        int balance = getBalance(root);
        if (balance > 1 && getBalance(root.left) >= 0) return rightRotate(root);
        if (balance > 1 && getBalance(root.left) < 0) {
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }
        if (balance < -1 && getBalance(root.right) <= 0) return leftRotate(root);
        if (balance < -1 && getBalance(root.right) > 0) {
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }
        return root;
    }

    private Node minValueNode(Node node) {
        Node current = node;
        while (current.left != null) current = current.left;
        return current;
    }

    public int order_of_key(int key) {
        return order_of_key(root, key);
    }

    private int order_of_key(Node node, int key) {
        if (node == null) return 0;
        if (key < node.key) return order_of_key(node.left, key);
        if (key > node.key) return 1 + size(node.left) + order_of_key(node.right, key);
        return size(node.left);
    }

    public int get_by_order(int k) {
        return get_by_order(root, k);
    }

    private int get_by_order(Node node, int k) {
        if (node == null) return -1;
        int leftSize = size(node.left);
        if (leftSize == k) return node.key;
        if (leftSize > k) return get_by_order(node.left, k);
        return get_by_order(node.right, k - leftSize - 1);
    }
}


