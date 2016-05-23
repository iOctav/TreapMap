import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import static junit.framework.Assert.*;


public class TreapTest {
    @Test
    public void testRemove() throws Exception {
        Treap<Integer, Integer> first = new Treap<Integer, Integer>(12,3, 4,
                                                                    null, null);
        first.put(29, 9, 3);
        first.put(14, 10, 2);
        first.put(21, 3, 4);
        first.remove(29);
        assertEquals(first.get(29), null);
    }

    @Test
    public void testSize() throws Exception {
        Treap<Integer, Integer> first = new Treap<Integer, Integer>(12,3, 4,
                                                    null, null);
        first.put(29, 9, 3);
        first.put(14, 10, 2);
        first.put(21, 3, 4);
        assertEquals(first.size(), 4);
    }

    @Test
    public void testSubMap() throws Exception {
        Treap<Integer, Integer> first = new Treap<Integer, Integer>(12,3, 4,
                                                    null, null);
        first.put(29, 9, 3);
        first.put(14, 10, 2);
        first.put(21, 3, 4);
        Treap<Integer,Integer> second = (Treap<Integer,Integer>)first.subMap(14, 29);
        assertEquals(second.get(12), null);
    }


    @Test
    public void testHeadMap() throws Exception {
        Treap<Integer, Integer> first = new Treap<Integer, Integer>(12,3, 4,
                                                    null, null);
        first.put(29, 9, 3);
        first.put(14, 10, 2);
        first.put(21, 3, 4);
        Treap<Integer,Integer> second = (Treap<Integer,Integer>)first.headMap(12);
        assertEquals(second.size(), 1);
    }

    @Test
    public void testTailMap() throws Exception {
        Treap<Integer, Integer> first = new Treap<Integer, Integer>(12,3, 4,
                                                    null, null);
        first.put(29, 9, 3);
        first.put(14, 10, 2);
        first.put(21, 3, 4);
        Treap<Integer,Integer> second = (Treap<Integer,Integer>)first.tailMap(21);
        assertEquals(second.size(), 2);
    }

    @Test
    public void testIsEmpty() throws Exception {
        Treap<Integer, Integer> first = new Treap<Integer, Integer>(12,3, 4,
                                                    null, null);
        first.put(29, 9, 3);
        first.put(14, 10, 2);
        first.put(21, 3, 4);
        assertFalse(first.isEmpty());
    }

    @Test
    public void testContainsKeyValue() throws Exception {
        Treap<Integer, Integer> first = new Treap<Integer, Integer>(12,3, 4,
                                                    null, null);
        first.put(29, 9, 3);
        first.put(14, 10, 2);
        first.put(21, 3, 4);
        assertTrue(first.containsKey(29));
        assertFalse(first.containsKey(142));
    }

    @Test
    public void testGet() throws Exception {
        Treap<Integer, Integer> first = new Treap<Integer, Integer>(12,3, 4,
                                                    null, null);
        first.put(29, 9, 3);
        first.put(14, 10, 2);
        first.put(21, 3, 4);
        assertEquals(first.get(21), (Integer)3);
    }

    @Test
    public void testClear() throws Exception {
        Treap<Integer, Integer> first = new Treap<Integer, Integer>(12,3, 4,
                                                    null, null);
        first.put(29, 9, 3);
        first.put(14, 10, 2);
        first.put(21, 3, 4);
        first.clear();
        assertNull(first.left);
        assertNull(first.right);
    }

    @Test
    public void testValues() throws Exception {
        Treap<Integer, Integer> first = new Treap<Integer, Integer>(12,3, 4,
                                                    null, null);
        first.put(29, 9, 3);
        first.put(14, 10, 2);
        first.put(21, 3, 4);
        Collection<Integer> scn = first.values();
        assertEquals(scn.size(), 4);
    }

    @Test
    public void testKeySet() throws Exception {
        Treap<Integer, Integer> first = new Treap<Integer, Integer>(12,3, 4,
                                                    null, null);
        first.put(29, 9, 3);
        first.put(14, 10, 2);
        first.put(21, 3, 4);
        Set<Integer> scn = first.keySet();
        assertEquals(scn.size(), 4);
    }
}
