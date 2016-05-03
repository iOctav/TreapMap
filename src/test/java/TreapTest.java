import org.junit.AfterClass;
import org.junit.Test;

import static junit.framework.Assert.*;


public class TreapTest {
    @Test
    public void testPut() throws Exception
    {
        Treap<Integer> treap = new Treap<Integer>(13, 8);
        treap.put(10, 10);
        treap.put(7, 7);
        treap.put(4, 4);
        treap.put(12, 12);
        assertEquals("Check left tree", treap.containsKey(7), true);
    }

    @AfterClass
    public static void testDisplay() throws Exception
    {
        Treap<Integer> treap = new Treap<Integer>(151, 5);
        treap.put(11, 11);
        treap.put(7, 7);
        treap.put(4, 4);
        treap.put(12, 12);
        treap.put(57, 57);
        treap.display();
    }

    @Test
    public void testContains() throws Exception
    {
        Treap<Integer> treap = new Treap<Integer>(21, 9);
        treap.put(10, 10);
        treap.put(7, 7);
        treap.put(4, 4);
        treap.put(12, 12);
        treap.put(57, 57);
        assertTrue(treap.containsValue(4));
        assertFalse(treap.containsKey(16));
    }
}
