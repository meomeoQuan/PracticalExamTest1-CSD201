/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication26;

/**
 *
 * @author mac
 */
class AVLTree {
    AVLTreeNode root;

    // A utility function to get the height of the tree
    public int height(AVLTreeNode N) {
        if (N == null)
            return 0;
        return N.height;
    }

    // A utility function to right rotate subtree rooted with y
 public AVLTreeNode rightRotate(AVLTreeNode y) {
        AVLTreeNode x = y.left;
        AVLTreeNode T2 = x.right;

        // Perform rotation
        x.right = y;
        y.left = T2;

        // Update heights
        y.height = Math.max(height(y.left), height(y.right)) + 1;
        x.height = Math.max(height(x.left), height(x.right)) + 1;

        // Return new root
        return x;
    }

    // A utility function to left rotate subtree rooted with x
  public AVLTreeNode leftRotate(AVLTreeNode x) {
        AVLTreeNode y = x.right;
        AVLTreeNode T2 = y.left;

        // Perform rotation
        y.left = x;
        x.right = T2;

        // Update heights
        x.height = Math.max(height(x.left), height(x.right)) + 1;
        y.height = Math.max(height(y.left), height(y.right)) + 1;

        // Return new root
        return y;
    }

    // Get balance factor of node N
   public int getBalance(AVLTreeNode N) {
        if (N == null)
            return 0;
        return height(N.left) - height(N.right);
    }

 public AVLTreeNode insert(AVLTreeNode node, Product product) {
        // 1. Perform the normal BST insertion
        if (node == null)
            return new AVLTreeNode(product);

        if (product.id < node.product.id)
            node.left = insert(node.left, product);
        else if (product.id > node.product.id)
            node.right = insert(node.right, product);
        else // Duplicate IDs are not allowed in AVL tree
            return node;

        // 2. Update height of this ancestor node
        node.height = 1 + Math.max(height(node.left), height(node.right));

        // 3. Get the balance factor of this ancestor node to check whether this node became unbalanced
        int balance = getBalance(node);

        // If this node becomes unbalanced, then there are 4 cases

        // Left Left Case
        if (balance > 1 && product.id < node.left.product.id)
            return rightRotate(node);

        // Right Right Case
        if (balance < -1 && product.id > node.right.product.id)
            return leftRotate(node);

        // Left Right Case
        if (balance > 1 && product.id > node.left.product.id) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        // Right Left Case
        if (balance < -1 && product.id < node.right.product.id) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        // return the (unchanged) node pointer
        return node;
    }

  public AVLTreeNode deleteNode(AVLTreeNode root, int id) {
        // Perform standard BST delete
        if (root == null)
            return root;

        // If the id to be deleted is smaller than the root's id, then it lies in left subtree
        if (id < root.product.id)
            root.left = deleteNode(root.left, id);

        // If the id to be deleted is greater than the root's id, then it lies in right subtree
        else if (id > root.product.id)
            root.right = deleteNode(root.right, id);

        // if id is same as root's id, then this is the node to be deleted
        else {
            // node with only one child or no child
            if ((root.left == null) || (root.right == null)) {
                AVLTreeNode temp = null;
                if (temp == root.left)
                    temp = root.right;
                else
                    temp = root.left;

                // No child case
                if (temp == null) {
                    temp = root;
                    root = null;
                } else // One child case
                    root = temp; // Copy the contents of the non-empty child
            } else {
                // node with two children: Get the inorder successor (smallest in the right subtree)
                AVLTreeNode temp = minValueNode(root.right);

                // Copy the inorder successor's data to this node
                root.product = temp.product;

                // Delete the inorder successor
                root.right = deleteNode(root.right, temp.product.id);
            }
        }

        // If the tree had only one node then return
        if (root == null)
            return root;

        // STEP 2: UPDATE HEIGHT OF THE CURRENT NODE
        root.height = Math.max(height(root.left), height(root.right)) + 1;

        // STEP 3: GET THE BALANCE FACTOR OF THIS NODE (to check whether this node became unbalanced)
        int balance = getBalance(root);

        // If this node becomes unbalanced, then there are 4 cases

        // Left Left Case
        if (balance > 1 && getBalance(root.left) >= 0)
            return rightRotate(root);

        // Left Right Case
        if (balance > 1 && getBalance(root.left) < 0) {
            root.left = leftRotate(root.left);
            return rightRotate(root);
        }

        // Right Right Case
        if (balance < -1 && getBalance(root.right) <= 0)
            return leftRotate(root);

        // Right Left Case
        if (balance < -1 && getBalance(root.right) > 0) {
            root.right = rightRotate(root.right);
            return leftRotate(root);
        }

        return root;
    }

    // Get the inorder successor (smallest in the right subtree)
 public AVLTreeNode minValueNode(AVLTreeNode node) {
        AVLTreeNode current = node;

        /* loop down to find the leftmost leaf */
        while (current.left != null)
            current = current.left;

        return current;
    }

    // A utility function to print inorder traversal of the tree.
    // The function also prints height of every node
    void inOrder(AVLTreeNode node) {
        if (node != null) {
            inOrder(node.left);
            System.out.println(node.product + " ");
            inOrder(node.right);
        }
    }

    // Function to find the product with the highest price
   public Product findMaxPrice(AVLTreeNode node) {
        if (node == null)
            return null;

        Product maxProduct = node.product;
        Product leftMax = findMaxPrice(node.left);
        Product rightMax = findMaxPrice(node.right);

        if (leftMax != null && leftMax.price > maxProduct.price)
            maxProduct = leftMax;

        if (rightMax != null && rightMax.price > maxProduct.price)
            maxProduct = rightMax;

        return maxProduct;
    }

    // Function to find the product with the lowest price
   public Product findMinPrice(AVLTreeNode node) {
        if (node == null)
            return null;

        Product minProduct = node.product;
        Product leftMin = findMinPrice(node.left);
        Product rightMin = findMinPrice(node.right);

        if (leftMin != null && leftMin.price < minProduct.price)
            minProduct = leftMin;

        if (rightMin != null && rightMin.price < minProduct.price)
            minProduct = rightMin;

        return minProduct;
    }

    public static void main(String[] args) {
        AVLTree tree = new AVLTree();

        // Insert products into the AVL tree
        tree.root = tree.insert(tree.root, new Product(1, "Laptop", 1200.0));
        tree.root = tree.insert(tree.root, new Product(2, "Smartphone", 800.0));
        tree.root = tree.insert(tree.root, new Product(3, "Tablet", 450.0));
        tree.root = tree.insert(tree.root, new Product(4, "Monitor", 300.0));
        tree.root = tree.insert(tree.root, new Product(5, "Keyboard", 50.0));

        // In-order traversal
        System.out.println("In-order traversal of the AVL tree:");
        tree.inOrder(tree.root);

        // Find the product with the highest price
        Product maxPriceProduct = tree.findMaxPrice(tree.root);
        System.out.println("\nProduct with the highest price: " + maxPriceProduct);

        // Find the product with the lowest price
        Product minPriceProduct = tree.findMinPrice(tree.root);
        System.out.println("Product with the lowest price: " + minPriceProduct);

        // Delete a product and perform in-order traversal again
        tree.root = tree.deleteNode(tree.root, 2);
        System.out.println("\nIn-order traversal after deleting product with ID 2:");
        tree.inOrder(tree.root);
    }
}