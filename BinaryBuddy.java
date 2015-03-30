package tree;

/*
 * Zach Mason, Ryan Schoppy, Darren Martin, Brian Grillo
 * Operation Systems
 * Binary Buddy Memory Allocation
 *
 * version 3.30.2015
 */


import java.util.ArrayList;
import static tree.Tree.printLeafNodes;

public class BinaryBuddy {

    private static Tree tree;
    protected static Tree.TreeNode root; //protected for Junit testing access
    private static int div;    //the result of TreeNode.memSize / requested mem
    private static int foundDiv;  // same as div, but saved when possibly match in freeList is found
    private static int memoryUsed;  //sum of memory space used
    protected static ArrayList<Tree.TreeNode> freeList = new ArrayList<Tree.TreeNode>();
                    // holds a free memory blocks ready to hold a process
    protected static ArrayList<Tree.TreeNode> leafList = new ArrayList<Tree.TreeNode>();
                    //for testing, holds all leaf nodes, protected for JUnit testing
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
     *
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
        if ((newNode != null && foundDiv > 1)) {
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
     * a method that deallocates memory
     *
     * @param proc
     */
    public static void deallocate(String proc) {

        Tree.TreeNode tmp = searchProcess(proc, root);

        if (tmp == null) {
            System.out.println(" Cannot delete " + proc);

        } else {
            memoryUsed = memoryUsed - tmp.memSize;
            if (tmp == root) {
                root.process = ("free");
            } else {
                Tree.TreeNode tmpPar = tmp.parent;
                while (tmpPar != root) {
                    Tree.TreeNode sib;
                    sib = getSibling(tmp);
                    if (sib == null) {
                        return;
                    } else if (!sib.process.equals("free")) {
                        tmp.process = "free";
                        return;
                    } else if (tmpPar.left.process.equals("free")) {
                        tmpPar.left = null;
                        tmpPar.right = null;
                        tmpPar.process = "free";
                    } else if (tmpPar.right.process.equals("free")) {
                        tmpPar.left = null;
                        tmpPar.right = null;
                        tmpPar.process = "free";
                    }
                    tmp = tmp.parent;
                    tmpPar = tmpPar.parent;
                }
                if (tmpPar == root) {
                    Tree.TreeNode sib;
                    sib = getSibling(tmp);
                    if (sib == null) {
                        return;
                    } else if (!sib.process.equals("free")) {
                        tmp.process = "free";
                    } else if (tmpPar.left.process.equals("free")) {
                        tmpPar.process = "free";
                    } else if (tmpPar.right.process.equals("free")) {
                        tmpPar.left.process = "free";
                    }
                }
            }
        }
    }

    /**
     * a method that finds a nodes sibling
     *
     * @param sib a TreeNode
     * @return the sibling node
     */
    public static Tree.TreeNode getSibling(Tree.TreeNode sib) {
        Tree.TreeNode tmp;
        if (sib == root) {
            return null;
        }
        if (sib.parent.left != sib) {
            return sib.parent.left;
        }
        return sib.parent.right;
    }

    /**
     * a method that traverses the nodes of the tree to
     * find a specified name of a process -- A,B,C, etc.
     *
     * @param p String, name of the process, A,B,C, etc
     * @param node Treenode,
     * @return the node where String p is found
     */
    public static Tree.TreeNode searchProcess(String p, Tree.TreeNode node) {
        Tree.TreeNode result = null;
        if (node != null) {
            if (p.equals(node.process)) {
                return node;
            }
            if ((result = searchProcess(p, node.left)) != null) {
                return result;
            }
            return searchProcess(p, node.right);
        }
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
        Tree.TreeNode found = null;
        freeList.clear();
        getFreeNodes(root);
        for (int i = 0; i < freeList.size(); i++) {
            t = freeList.get(i);
            div = t.memSize / mem;
            if (div > 1) {
                found = t;
                foundDiv = div;
            }
            if (div == 1) {
                freeList.clear();
                return t;
            }
        }
        if (div == 0) {
            freeList.clear();
            return found;
        }
        freeList.clear();
        return t;
    }

    /**
     * a method that fills ArrayList of TreeNodes with free space
     *
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
    /**
     * used for testing to fill an ArrayList that contains all leaves of
     * binary tree which are the nodes that are free and used by a process
     * @param t 
     */
    public static void getLeaves(Tree.TreeNode t){
        if(t == null){       
        return;
        }
       if(t.left == null && t.right==null){      
          leafList.add(t);
       }
       getLeaves(t.left); 
       getLeaves(t.right); 
       //return leafList;
    }

    /**
     * used for testing
     * @param args 
     */
    public static void main(String args[]) {
        new BinaryBuddy();

        allocate(3, "A");
        allocate(3, "B");
        allocate(24, "C");
        allocate(17, "D");
        allocate(2, "E");
        deallocate("D");
        deallocate("A");
        allocate(10, "F");
        allocate(1, "G");
        allocate(56, "H");
        deallocate("H");
        deallocate("B");
        deallocate("E");
        allocate(9, "J");
        allocate(2, "K");
        allocate(5, "L");
        deallocate("F");
        allocate(8, "M");
        deallocate("J");
        
       
        System.out.println();
        System.out.println("Free and Used Memory Blocks:");
        tree.printLeafNodes(root);

    }

}
