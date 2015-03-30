/*
 * Testing class for BinaryBuddy class
 */
package tree;

import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import static tree.BinaryBuddy.allocate;
import static tree.BinaryBuddy.deallocate;

public class BinaryBuddyTest {

    public BinaryBuddyTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {

    }

    @Before
    public void setUp() {
        new BinaryBuddy();
    }

    @After
    public void tearDown() {
        new BinaryBuddy();
    }

    /**
     * Test of allocate method, of class BinaryBuddy.
     */
    @org.junit.Test
    public void testAllocateRoot() {
        System.out.println("allocate");
        int size = 0;
        String expResult = "A";
        Tree.TreeNode result = BinaryBuddy.allocate(56, "A");
        assertEquals(expResult, result.process);
    }

    /**
     * Test of allocate method, of class BinaryBuddy.
     */
    @org.junit.Test
    public void testAllocateSmall() {
        System.out.println("allocate");

        boolean found = false;
        boolean expResult = true;
        Tree.TreeNode result = BinaryBuddy.allocate(3, "A");
        if (result.process == "A" && result.memSize == 4) {
            found = true;
        }
        assertEquals(expResult, found);

        found = false;
        expResult = true;
        result = BinaryBuddy.allocate(4, "B");
        if (result.process == "B" && result.memSize == 4) {
            found = true;
        }
        assertEquals(expResult, found);

        found = false;
        expResult = true;
        result = BinaryBuddy.allocate(20, "C");
        if (result.process == "C" && result.memSize == 32) {
            found = true;
        }
        assertEquals(expResult, found);
    }

    /**
     * Test of allocate method, of class BinaryBuddy.
     */
    @org.junit.Test
    public void testAllocateBig() {
        System.out.println("allocate");

        boolean found = false;
        boolean expResult = true;
        Tree.TreeNode result = BinaryBuddy.allocate(3, "A");
        if (result.process == "A" && result.memSize == 4) {
            found = true;
        }
        assertEquals(expResult, found);

        found = false;
        expResult = true;
        result = BinaryBuddy.allocate(4, "B");
        if (result.process == "B" && result.memSize == 4) {
            found = true;
        }
        assertEquals(expResult, found);

        found = false;
        expResult = true;
        result = BinaryBuddy.allocate(24, "C");
        if (result.process == "C" && result.memSize == 32) {
            found = true;
        }
        assertEquals(expResult, found);

        result = BinaryBuddy.allocate(17, "D");
        assertEquals(result, null);

        found = false;
        expResult = true;
        result = BinaryBuddy.allocate(2, "E");
        if (result.process == "E" && result.memSize == 2) {
            found = true;
        }
        assertEquals(expResult, found);

        found = false;
        expResult = true;
        result = BinaryBuddy.allocate(10, "F");
        if (result.process == "F" && result.memSize == 16) {
            found = true;
        }
        assertEquals(expResult, found);

        found = false;
        expResult = true;
        result = BinaryBuddy.allocate(1, "G");
        if (result.process == "G" && result.memSize == 1) {
            found = true;
        }
        assertEquals(expResult, found);

        found = false;
        expResult = true;
        result = BinaryBuddy.allocate(4, "H");
        if (result.process == "H" && result.memSize == 4) {
            found = true;
        }
        assertEquals(expResult, found);

        result = BinaryBuddy.allocate(7, "I");
        assertEquals(result, null);
    }

    /**
     * Test of allocate method, of class BinaryBuddy.
     */
    @org.junit.Test
    public void testAllocateAndDeallocate() {
        System.out.println("allocate and deallocate");

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

        BinaryBuddy.getLeaves(BinaryBuddy.root);

        boolean result = false;
        
        Tree.TreeNode node = BinaryBuddy.leafList.get(0);
        if(node.memSize == 32 && node.process.equals("C")){
            result = true;
        }
        result = false;
        node = BinaryBuddy.leafList.get(1);
        if(node.memSize == 8 && node.process.equals("M")){
            result = true;
        }
        result = false;
        node = BinaryBuddy.leafList.get(2);
        if(node.memSize == 8 && node.process.equals("free")){
            result = true;
        }
        result = false;
        node = BinaryBuddy.leafList.get(3);
        if(node.memSize == 8 && node.process.equals("L")){
            result = true;
        }
        result = false;
        node = BinaryBuddy.leafList.get(4);
        if(node.memSize == 2 && node.process.equals("K")){
            result = true;
        }
        result = false;
        node = BinaryBuddy.leafList.get(5);
        if(node.memSize == 1 && node.process.equals("G")){
            result = true;
        }
        result =false;
        node = BinaryBuddy.leafList.get(6);
        if(node.memSize == 1 && node.process.equals("free")){
            result = true;
        }
        result = false;
        node = BinaryBuddy.leafList.get(7);
        if(node.memSize == 4 && node.process.equals("free")){
            result = true;
        }
        assertEquals(result, true);

    }

    /**
     * Test of deallocate method, of class BinaryBuddy.
     */
    @org.junit.Test
    public void testDeallocateRoot() {
        new BinaryBuddy();
        System.out.println("deallocate");
        BinaryBuddy.allocate(56, "A");
        String proc = "A";
        BinaryBuddy.deallocate(proc);
        String expResult = "free";
        Tree.TreeNode result = BinaryBuddy.root;
        assertEquals(expResult, result.process);
    }

    /**
     * Test of getSibling method, of class BinaryBuddy.
     */
    @org.junit.Test
    public void testGetSiblingRoot() {
        System.out.println("getSibling");
        Tree.TreeNode sib = BinaryBuddy.root;
        Tree.TreeNode expResult = null;
        Tree.TreeNode result = BinaryBuddy.getSibling(sib);
        assertEquals(expResult, result);
    }

    /**
     * Test of searchProcess method, of class BinaryBuddy.
     */
    @org.junit.Test
    public void testSearchProcess() {
        allocate(3, "A");
        allocate(3, "B");
        allocate(24, "C");
        allocate(7, "D");
        allocate(2, "E");
        deallocate("D");
        deallocate("A");
        allocate(10, "F");
        allocate(6, "G");
        allocate(56, "H");
        deallocate("H");
        deallocate("B");
        deallocate("E");
        allocate(9, "J");
        allocate(2, "K");
        allocate(5, "L");
        deallocate("F");
        allocate(8, "M");

        System.out.println("searchProcess");
        String p = "C";
        Tree.TreeNode node = BinaryBuddy.root;;
        String expResult = "C";
        Tree.TreeNode result = BinaryBuddy.searchProcess(p, node);
        assertEquals(expResult, result.process);

        p = "J";
        expResult = "J";
        result = BinaryBuddy.searchProcess(p, node);
        assertEquals(expResult, result.process);

        p = "K";
        expResult = "K";
        result = BinaryBuddy.searchProcess(p, node);
        assertEquals(expResult, result.process);

        p = "G";
        expResult = "G";
        result = BinaryBuddy.searchProcess(p, node);
        assertEquals(expResult, result.process);

        p = "F";
        Tree.TreeNode expNode = null;
        result = BinaryBuddy.searchProcess(p, node);
        assertEquals(expNode, result);
    }

    /**
     * Test of searchSpace method, of class BinaryBuddy.
     */
    @org.junit.Test
    public void testSearchSpace() {
        allocate(3, "A");
        allocate(3, "B");
        allocate(24, "C");
        allocate(7, "D");
        allocate(2, "E");
        deallocate("D");
        deallocate("A");
        allocate(10, "F");
        allocate(6, "G");
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

        System.out.println("searchSpace");
        int mem = 20;
        Tree.TreeNode node = BinaryBuddy.root;
        Tree.TreeNode expNode = null;
        Tree.TreeNode result = BinaryBuddy.searchSpace(mem, node);
        assertEquals(expNode, result);

        mem = 2;
        int expResult = 2;
        result = BinaryBuddy.searchSpace(mem, node);
        assertEquals(expResult, result.memSize);

        mem = 9;
        expResult = 16;
        result = BinaryBuddy.searchSpace(mem, node);
        assertEquals(expResult, result.memSize);

        mem = 6;
        expResult = 16;
        result = BinaryBuddy.searchSpace(mem, node);
        assertEquals(expResult, result.memSize);

        mem = 3;
        expResult = 4;
        result = BinaryBuddy.searchSpace(mem, node);
        assertEquals(expResult, result.memSize);

        mem = 17;
        expNode = null;
        result = BinaryBuddy.searchSpace(mem, node);
        assertEquals(expNode, result);

    }

    /**
     * Test of getFreeNodes method, of class BinaryBuddy.
     */
    @org.junit.Test
    public void testGetFreeNodes() {
        allocate(3, "A");
        allocate(3, "B");
        allocate(24, "C");
        allocate(7, "D");
        allocate(2, "E");
        deallocate("D");
        deallocate("A");
        allocate(10, "F");
        allocate(6, "G");
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

        System.out.println("getFreeNodes");
        Tree.TreeNode t = BinaryBuddy.root;
        BinaryBuddy.getFreeNodes(t);
        assertEquals(BinaryBuddy.freeList.size(), 3);
    }

}
