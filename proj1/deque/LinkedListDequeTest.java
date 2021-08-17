package deque;

import org.junit.Test;
import static org.junit.Assert.*;


/** Performs some basic linked list deque tests. */
public class LinkedListDequeTest<T> {
    public static LinkedListDeque<Integer> lld = new LinkedListDeque<>();
    public static Deque<String> str = new LinkedListDeque<>();
    public static LinkedListDeque<Integer> lld2 = new LinkedListDeque<>();

    @Test
    public void addIsEmptySizeTest() {
       // lld.printDeque();
		assertTrue("A newly initialized LLDeque should be empty", lld.isEmpty());
		lld.addFirst(0);
        lld.printDeque();
        assertFalse("lld should now contain 1 item", lld.isEmpty());
        lld.addFirst(0);
        lld.printDeque();

        // Reset the linked list deque at the END of the test.
        lld = new LinkedListDeque<Integer>();
        lld.addLast(0);
        lld.printDeque();
        assertFalse("lld should now contain 1 item", lld.isEmpty());
        lld.addLast(0);
        lld.printDeque();
        lld = new LinkedListDeque<Integer>();

    }

    @Test
    public void testAddLast(){
        lld.addLast(0);

        lld = new LinkedListDeque<Integer>();
        return;
    }

    @Test
    public void testIsEmpty(){
        lld = new LinkedListDeque<Integer>();
        assertTrue(lld.isEmpty());
        lld.addLast(12);
        assertFalse(lld.isEmpty());
    }
    @Test
    public void testSize(){
        assertEquals(0,lld.size());
        lld.addFirst(0);
        assertEquals(1,lld.size());
        lld.addFirst(0);
        assertEquals(2,lld.size());
        lld.addLast(0);
        assertEquals(3,lld.size());
        lld = new LinkedListDeque<Integer>();
    }
    @Test
    public void testPrintDeque(){
        lld.printDeque();
        lld.addFirst(0);
        lld.printDeque();
        lld.addFirst(1);
        lld.printDeque();
        str.addFirst("a");
        str.printDeque();
        str.addFirst("test");
        str.printDeque();
        lld = new LinkedListDeque<Integer>();
        str = new LinkedListDeque<>();
    }

    @Test
    public void testRemoveFirst(){
        lld.addLast(52348);
        lld.addFirst(932);
        lld.addLast(435);
        lld.addLast(234);
        lld.printDeque();
        lld.removeFirst();
        lld.printDeque();
        lld.removeFirst();
        lld.printDeque();
        assertEquals(435,(int)lld.removeFirst());
        lld.printDeque();
        str.addLast("jfgh");
        str.addLast("lhugk");
        str.addLast("Oof");
        str.printDeque();
        str.removeFirst();
        str.printDeque();
        str.removeFirst();
        str.printDeque();
        assertEquals("Oof", (String) str.removeFirst());
        lld = new LinkedListDeque<Integer>();
        str = new LinkedListDeque<>();
    }
    @Test
    public void testRemoveLast(){
        lld.addLast(52348);
        lld.addFirst(932);
        lld.addLast(435);
        lld.addLast(234);
        lld.printDeque();
        lld.removeLast();
        lld.printDeque();
        lld.removeLast();
        lld.printDeque();
        assertEquals(52348,(int)lld.removeLast());
        lld.printDeque();
        str.addLast("jfgh");
        str.addLast("lhugk");
        str.addLast("Oof");
        str.printDeque();
        str.removeLast();
        str.printDeque();
        str.removeLast();
        str.printDeque();
        assertEquals("jfgh", (String) str.removeFirst());
        lld = new LinkedListDeque<Integer>();
        str = new LinkedListDeque<>();
    }

    @Test
    public void testGet(){
        lld.addLast(52348);
        lld.addFirst(932);
        lld.addLast(435);
        lld.addLast(234);
        lld.printDeque();
        int node=lld.get(2);
        assertEquals(435,node);
        node=lld.get(0);
        int nodeR=lld.getRecursive(3);
        lld.printDeque();
        assertEquals(932,node);
        str.addLast("jfgh");
        str.addLast("lhugk");
        str.addLast("Oof");
        str.printDeque();
        String st=str.get(2);
        assertEquals("Oof",st);
        lld = new LinkedListDeque<Integer>();
        lld.addLast(1);
        lld.addLast(2);
        lld.getRecursive(1);
        lld.addLast(4);
        assertEquals(4,(int)lld.removeLast());
        lld.printDeque();
        System.out.println(lld.getRecursive(1));
        System.out.println(lld.getRecursive(0));
        lld.printDeque();
        lld= new LinkedListDeque<>();
       /* lld.removeFirst();
        lld.addLast(6);
        lld.printDeque();
        int nodeRR=lld.getRecursive(0);
        System.out.println(nodeRR);*/
        str = new LinkedListDeque<>();
    }


    /** You MUST use the variable below for all of your tests. If you test
     * using a local variable, and not this static variable below, the
     * autograder will not grade that test. If you would like to test
     * LinkedListDeques with types other than Integer (and you should),
     * you can define a new local variable. However, the autograder will
     * not grade that test. */



    /** Adds a few things to the list, checks that isEmpty() is correct.
     * This is one simple test to remind you how junit tests work. You
     * should write more tests of your own.
     *
     * && is the "and" operation. */


}
