package tree;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 *
 */
public class BinaryBuddy {

    private static Tree tree;
    private static Tree.TreeNode root;
    private static int div;

    /**
     * Constructor for BinaryBuddy
     */
    public BinaryBuddy() {
        tree = new Tree();
        root = tree.new TreeNode(64, "free");
    }

    public static Tree.TreeNode allocate(int size, String proc) {
        Tree.TreeNode newNode;
        newNode = searchSpace(size, root);
        if (newNode != null && newNode.process.equals("free") && tree.isLeaf(newNode)) {
            newNode.process = proc;
            System.out.println("Space for process " + proc + " found");
            return newNode;
        }
        
        if (newNode == null && div > 1) {
            double newSize;
            int power;
            power = tree.treeHeightDriver(root);

            newSize = Math.pow(2, 6 - power);
            int i = (int) newSize;
            tree.insertNode(i, "free", root);
            return allocate(size, proc);
        }
        if (newNode == null && (div == 1 || div == 0)) {
            System.out.println("no space available");
        }
        
//        else{
//            System.out.println("No space for process " + proc);
//        }

        return null;
    }

    public static void deallocate(String proc) {

    }

    /**
     * a method that traverses the nodes of the tree to find a specified name of
     * a process -- A,B,C, etc.
     *
     * @param p String, name of the process, A,B,C, etc
     * @param node Treenode,
     * @return the node where String p is found
     */
    public static Tree.TreeNode searchProcess(String p, Tree.TreeNode node) {
        if (node != null) {
            if (p.equals(node.process)) {
                return node;
            }
            if ((node.left) != null) {
                return searchProcess(p, node.left);
            }
            if ((node.right) != null) {
                return searchProcess(p, node.right);
            }
        }
        System.out.println("no matches found");
        return null;
    }

    public static Tree.TreeNode searchSpace(int mem, Tree.TreeNode node) {
        if (node != null) {
            div = node.memSize / mem;
            if (div ==0){
                return null;
            }
            if (div == 1 && node.process.equals("free")) {
                return node;
            }

            if ((node.left) != null) {
                return searchSpace(mem, node.left);
            }
            if ((node.parent.right) != null) {
                return searchSpace(mem, node.parent.right);
            }
        }
        //System.out.println("no space available");
        return null;
    }
//  THis method used for testing
//    public static Tree.TreeNode searchSpace(int mem, Tree.TreeNode node) {
//        if (node != null) {
//            if ((node.memSize / mem) == 1) {
//                return node;
//            }
//
//            if ((node.left) != null) {
//                return searchSpace(mem, node.left);
//            }
//            if ((node.right) != null) {
//                return searchSpace(mem, node.right);
//            }
//        }
//        System.out.println("no space available");
//        return null;
//    }

    public static void main(String args[]) {
        new BinaryBuddy();

//        tree.insertNode(32, "free", root);
//        tree.insertNode(16, "free", root);
//        tree.insertNode(8, "free", root);
//        tree.insertNode(4, "free", root);
//        System.out.println(tree.treeHeightDriver(root));
        allocate(32, "A");
        allocate(32, "B");
        //allocate(13, "C");
        //Tree.TreeNode n = searchSpace(4, root);
        //System.out.println(div);
        //System.out.println(n.toString(n));
    }

}
