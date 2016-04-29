import org.junit.AfterClass;
import org.junit.Test;

import static junit.framework.Assert.*;


public class TreapTest {
    @Test
    public void testAdd() throws Exception
    {
        Treap<Integer> treap = new Treap<Integer>();
        treap.add(10, 7);
        treap.add(7, 4);
        treap.add(4, 5);
        treap.add(12, 2);
        assertEquals("Check left tree", treap.contains(7), true);
    }

    @AfterClass
    public static void testDisplay() throws Exception
    {
        Treap<Integer> treap = new Treap<Integer>();
        treap.add(10, 7);
        treap.add(7, 4);
        treap.add(4, 5);
        treap.add(12, 2);
        treap.display();
    }
}
