import org.junit.AfterClass;
import org.junit.Test;

import static junit.framework.Assert.*;


public class TreapTest {
    @Test
    public void testPut() throws Exception
    {
        Treap<Integer> treap = buildTreap();
        assertEquals("Check left tree", treap.containsKey(7), true);
    }

    @AfterClass
    public static void testDisplay() throws Exception
    {
        Treap<Integer> treap = buildTreapPri();
        treap.display();
    }

    @Test
    public void testContains() throws Exception
    {
        Treap<Integer> treap = buildTreap();
        assertTrue(treap.containsValue(4));
        assertFalse(treap.containsKey(16));
    }

    public static Treap<Integer> buildTreap()
    {
        Treap<Integer> treap = new Treap<Integer>(58, 548);
        treap.put(10, 10);
        treap.put(7, 7);
        treap.put(4, 4);
        treap.put(12, 12);
        treap.put(57, 57);
        return treap;
    }

    public static Treap<Integer> buildTreapPri()
    {
        Treap<Integer> treap = new Treap<Integer>(58, 12);
        treap.putPri(10, 3);
        treap.putPri(7, 18);
        treap.putPri(4, 21);
        treap.putPri(12, 76);
        treap.putPri(57, 1);
        return treap;
    }

    @Test
    public void testFirstKey()
    {
        Treap<Integer> treap = buildTreapPri();
        assertEquals(treap.SizeOf(treap.root), 1);
    }

}
