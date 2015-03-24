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
import java.util.ArrayList;

public class BinaryBuddy {

    private static Tree tree;
    private static Tree.TreeNode root;
    private static int div;    //the result of TreeNode.memSize / requested mem
    private static int memoryUsed;  //sum of memory space used
    private static ArrayList<Tree.TreeNode> freeList = new ArrayList<Tree.TreeNode>();
                     // holds a free memory blocks ready to hold a process
    /**
     * Constructor for BinaryBuddy
     */
    public BinaryBuddy() {
        tree = new Tree();
        root = tree.new TreeNode(64, "free");
        memoryUsed = 0;
        freeList.add(root);
    }

    /**
     * a method that recursively allocates a memory block, if available
     * @param size int, requested memory size
     * @param proc String, name of process- A, B, C, etc
     * @return a TreeNode
     */
    public static Tree.TreeNode allocate(int size, String proc) {
        if (memoryUsed + size > 64) {
            System.out.println("no more space available for process " + proc);
            return null;
        }
        Tree.TreeNode newNode;
        newNode = searchSpace(size, root);
        if (newNode != null && div == 1) {
            newNode.process = proc;
            memoryUsed = newNode.memSize + memoryUsed;
            System.out.println("Space for process " + proc + " found");
            return newNode;
        }
        if ((newNode != null && div > 1)) {
            int i = newNode.memSize / 2;
            tree.insertNode(i, "free", newNode);
            return allocate(size, proc);
        }
        if (newNode == null && (div == 0 || div == 1)) {
            System.out.println("no space available for process " + proc);
        }

        return null;
    }

    /**
     * TO DO
     * @param proc 
     */
    public static void deallocate(String proc) {

    }

    /**
     * MIGHT NEED TO BE REDONE
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

    /**
     * a method that traverses the nodes of the tree to find a node with the
     * proper space available
     *
     * @param mem int, requested size of process
     * @param node TreeNode,
     * @return the node where mem can be stored
     */
    public static Tree.TreeNode searchSpace(int mem, Tree.TreeNode node) {
        Tree.TreeNode t = null;
        freeList.clear();
        getFreeNodes(root);
        for (int i = 0; i < freeList.size(); i++) {
            t = freeList.get(i);
            div = t.memSize / mem;
            if (div == 1) {
                freeList.clear();
                return t;
            }
        }
        if (div == 0) {
            freeList.clear();
            return null;
        }
        freeList.clear();
        return t;
    }


   
    /**
     * a method that fills ArrayList of TreeNodes with free space
     * @param t a TreeNode
     */
    public static void getFreeNodes(Tree.TreeNode t) {
        if (t == null) {
            return;
        }
        if (t.left == null && t.right == null && t.process.equals("free")) {
            freeList.add(t);
        }
        getFreeNodes(t.left);
        getFreeNodes(t.right);
    }

    public static void main(String args[]) {
        new BinaryBuddy();

        allocate(23, "A");
        allocate(3, "B");
        allocate(4, "C");
        allocate(17, "D");
        allocate(2, "E");

        
        System.out.println();
        System.out.println("Free and Used Memory Bloacks:");
        tree.printLeafNodes(root);

    }

}
