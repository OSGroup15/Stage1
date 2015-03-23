/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
        if (isLeaf(node) == true) {
            node.left = new TreeNode(mem, proc);
            node.right = new TreeNode(mem, "free");
            node.left.parent = node.right.parent = node;
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


    public int treeSize() {
        int k = treeSizeDriver(root);
        return k;
    }

//    public int treeHeight() {
//        int k = treeHeightDriver(root);
//        return k;
//    }

    public int leafCount() {
        int k = leafCountDriver(root);
        return k;
    }

    
    private int treeSizeDriver(TreeNode r) {
        if (r == null) {
            return 0;
        } else {
            return (1 + treeSizeDriver(r.left)
                    + treeSizeDriver(r.right));
        }
    }

    
    public int treeHeightDriver(TreeNode r) {
        if (r == null) {
            return 0;
        } else {
            return Math.max(treeHeightDriver(r.left),
                    treeHeightDriver(r.right)) + 1;
        }
    }

    
    private int leafCountDriver(TreeNode r) {
        if (r == null) {
            return 0;
        } else if (r.right == null && r.left == null) {
            return 1;
        } else {
            return leafCountDriver(r.left)
                    + leafCountDriver(r.right);
        }
    }

}
