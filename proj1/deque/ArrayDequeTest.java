package deque;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import org.junit.Test;

import static org.junit.Assert.*;

/* Performs some basic array deque tests. */
public class ArrayDequeTest<T> {

    /** You MUST use the variable below for all of your tests. If you test
     * using a local variable, and not this static variable below, the
     * autograder will not grade that test. If you would like to test
     * ArrayDeques with types other than Integer (and you should),
     * you can define a new local variable. However, the autograder will
     * not grade that test. */

    public static Deque<Integer> ad = new ArrayDeque<>();
    public static ArrayDeque<String> str = new ArrayDeque<>();

    @Test
    public void addIsEmptySizeTest() {
        ad.printDeque();
        assertTrue("A newly initialized LLDeque should be empty", ad.isEmpty());
        ad.addFirst(1);
        ad.printDeque();
        assertFalse("lld should now contain 1 item", ad.isEmpty());
        ad.addFirst(0);
        ad.printDeque();
        // Reset the linked list deque at the END of the test.
        ad = new ArrayDeque<Integer>();
        ad.addLast(0);
        ad.printDeque();
        assertFalse("lld should now contain 1 item", ad.isEmpty());
        ad.addLast(0);
        ad.printDeque();
        ad = new ArrayDeque<Integer>();
        str = new ArrayDeque<String>();

    }
    @Test
    public void testAAAAAaddfirst(){
        ad.addFirst(0);
        ad.removeLast();
        ad.addFirst(2);
        ad.printDeque();
        ad = new ArrayDeque<Integer>();
    }

    @Test
    public void testAddFirst(){
        ad.addFirst(2);
        assertEquals(1,ad.size());
        ad.addFirst(1);
        assertEquals(2,ad.size());
        ad.addFirst(0);
        assertEquals(3,ad.size());
        ad = new ArrayDeque<Integer>();
        for (int i=0; i<9; i++){
            ad.addLast(i);
        }
        System.out.println(ad.get(8));
        ad.printDeque();
        ad = new ArrayDeque<Integer>();
    }

    @Test
    public void testAddLast(){
        ad.addLast(2);
        assertEquals(1,ad.size());
        ad.addLast(1);
        assertEquals(2,ad.size());
        ad.addLast(0);
        System.out.println(ad.get(0));
        ad.addLast(9);
        ad.addLast(6);
        System.out.println(ad.get(2));
        ad.printDeque();
        ad = new ArrayDeque<Integer>();

    }

    @Test
    public void testIsEmpty(){

        assertTrue(ad.isEmpty());
        ad.addLast(12);
      //  ad.printDeque();
        assertFalse(ad.isEmpty());
        ad = new ArrayDeque<Integer>();

    }

    @Test
    public void testSize(){
        ad = new ArrayDeque<Integer>();
        assertEquals(0,ad.size());
        ad.addFirst(0);
        assertEquals(1,ad.size());
        ad.addFirst(0);
        assertEquals(2,ad.size());
        ad.addLast(0);
        assertEquals(3,ad.size());
        ad = new ArrayDeque<Integer>();
    }

    @Test
    public void testPrintDeque(){
        ad.printDeque();
        ad.addFirst(0);
        ad.addFirst(1);
        ad.addFirst(2);
        ad.addFirst(3);
        ad.addFirst(4);
        ad.printDeque();
        str.addFirst("a");
        str.addFirst("test");
        str.printDeque();
        ad = new ArrayDeque<Integer>();
        str = new ArrayDeque<String>();
    }

    @Test
    public void testRemoveFirst(){
        ad.addLast(52348);
        ad.addFirst(932);
        ad.addLast(435);
        ad.addLast(234);
        ad.printDeque();
        ad.removeFirst();
        ad.printDeque();
        ad.removeFirst();
        ad.printDeque();
        assertEquals(435,(int)ad.removeFirst());
        ad.printDeque();
        str.addLast("jfgh");
        str.addLast("lhugk");
        str.addLast("Oof");
        str.printDeque();
        str.removeFirst();
        str.printDeque();
        str.removeFirst();
        str.printDeque();
        assertEquals("Oof", (String) str.removeFirst());
        ad = new ArrayDeque<Integer>();
        str = new ArrayDeque<String>();
    }
    @Test
    public void testRemoveLast(){
        ad.addLast(52348);
        ad.addFirst(932);
        ad.addLast(435);
        ad.addLast(234);
        ad.printDeque();
        ad.removeLast();
        ad.printDeque();
        ad.removeLast();
        ad.printDeque();
        ad.removeLast();
        ad.printDeque();
        assertEquals(932,(int)ad.removeLast());
        ad.printDeque();
        str.addLast("jfgh");
        str.addLast("lhugk");
        str.addLast("Oof");
        str.printDeque();
        str.removeLast();
        str.printDeque();
        str.removeLast();
        str.printDeque();
        str.removeFirst();
       // assertEquals("jfgh", (String) str.removeFirst());
        ad = new ArrayDeque<Integer>();
        str = new ArrayDeque<String>();
    }

    @Test
    public void testDeep(){
        ad = new ArrayDeque<Integer>();
        Deque<Integer> lld = new LinkedListDeque<>();
        ad.addLast(1);
        lld.addLast(1);
        ad.addFirst(12434);
        lld.addFirst(12434);
        System.out.println(ad.get(0));
        System.out.println(lld.get(0));
        System.out.println(ad.equals(lld));
        ad.addFirst(0);
        System.out.println(ad.equals(lld));
        ad.removeFirst();
        ad.removeFirst();
        ad.removeFirst();
        lld.removeFirst();
        lld.removeFirst();
        System.out.println(ad.equals(lld));

        str = new ArrayDeque<String>();
        ad = new ArrayDeque<Integer>();
    }

    @Test
    public void testGet(){

        ad.addLast(0);
        ad.addLast(1);
        ad.addLast(2);
        ad.addLast(3);
        assertEquals(0,(int)ad.get(0));
        ad.addLast(52348);
        ad.addFirst(932);//932 5xx
        ad.addLast(435);//9 5 4
        ad.addLast(234);//
        ad.printDeque();
        int node=ad.get(2);
        assertEquals(1,node);
        node=ad.get(0);
        ad.printDeque();
        assertEquals(932,node);
        str.addLast("jfgh");
        str.addLast("lhugk");
        str.addLast("Oof");
        str.printDeque();
        String st=str.get(2);
        assertEquals("Oof",st);
        ad = new ArrayDeque<Integer>();
        for (int i=0; i<9;i++){
            ad.addFirst(i);
        }
        ad.printDeque();
        System.out.println(ad.removeFirst());
        System.out.println(ad.removeLast());

        ad.printDeque();
        str = new ArrayDeque<String>();
    }
}
