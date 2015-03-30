/*
 * Tree Class
 */

/*
 * Zach Mason, Ryan Schoppy, Darren Martin, Brian Grillo
 * Operation Systems
 * Binary Buddy Memory Allocation
 *
 * version 3.30.2015
 */
package tree;

/**
 *
 * @author
 */
import java.io.*;
import java.util.*;
import java.lang.Math;

public class Tree {

    /**
     * inner class for TreeNode
     */ 
    class TreeNode {

        TreeNode left;
        TreeNode right;
        int memSize;
        String process;
        TreeNode parent;

        /**
         * Creates a TreeNode
         * @param size int, the size of memory
         * @param proc String, name of the process A, B, C, etc
         */
        public TreeNode(int size, String proc) {
            memSize = size;
            left = right = null;
            process = proc;
            parent = null;
        }

        /**
         * inserts a node into the tree
         * @param size int, the size of memory
         * @param proc String, name of the process A, B, C, etc
         */
        private void insert(int size, String proc) {
            left = new TreeNode(size, proc);
            right = new TreeNode(size, proc);
        }

        public String toString(TreeNode n) {
            String result;
            result = "Process: " + process + "  Memory Size: " + memSize;
            return result;
        }
    }

    /* End of TreeNode inner class
     */
    private static TreeNode root;

    /* Construct an empty binary tree at the root
     */
    public Tree() {
        root = new TreeNode(64, "free");
    }

    /**
     * insert node into tree
     * @param mem int, size of memory
     * @param proc String, name of process
     * @param node Treenode
     */
    public void insertNode(int mem, String proc, TreeNode node) {
        if (isLeaf(node) == true && node.process.equals("free")) {
            node.left = new TreeNode(mem, proc);
            node.right = new TreeNode(mem, "free");
            node.left.parent = node.right.parent = node;
            
            node.left.parent.process = node.right.parent.process = "non";
        } else {
            insertNode(mem, proc, node.left);

        }
    }

    /**
     * checks if node is a leaf
     * @param x TreeNode that is being checked
     * @return true if nod is a leaf, false if not
     */
    public boolean isLeaf(TreeNode x) {
        return x.left == null && x.right == null;
    }

    
    /**
     * used for testing, prints out all leaf nodes
     **/ 
    public static void printLeafNodes(TreeNode t)
{
      if(t == null)       
        return;
       if(t.left == null && t.right==null)      
          System.out.println(t.memSize + " " + t.process); 
       printLeafNodes(t.left); 
       printLeafNodes(t.right);      
}

}
