import org.junit.Test;

import java.util.ArrayList;

import static junit.framework.Assert.*;


public class TreapTest {
    @Test
    public void testRemove() throws Exception {
        Treap<Integer, Integer> first = new Treap<Integer, Integer>(12,3, 4, null, null);
        first.put(29, 9, 3);
        first.put(14, 10, 2);
        first.put(21, 3, 4);
        first.remove(29);
        assertEquals(first.get(29), null);
    }

    @Test
    public void testSizeOf() throws Exception {
        Treap<Integer, Integer> first = new Treap<Integer, Integer>(12,3, 4, null, null);
        first.put(29, 9, 3);
        first.put(14, 10, 2);
        first.put(21, 3, 4);
        assertEquals(first.size(), 4);
    }

    @Test
    public void testSubMap() throws Exception {
        Treap<Integer, Integer> first = new Treap<Integer, Integer>(12,3, 4, null, null);
        first.put(29, 9, 3);
        first.put(14, 10, 2);
        first.put(21, 3, 4);
        Treap<Integer,Integer> second = (Treap<Integer,Integer>)first.subMap(14, 29);
        assertEquals(second.get(29), (Integer)9);
    }


    @Test
    public void testHeadMap() throws Exception {
        Treap<Integer, Integer> first = new Treap<Integer, Integer>(12,3, 4, null, null);
        first.put(29, 9, 3);
        first.put(14, 10, 2);
        first.put(21, 3, 4);
        Treap<Integer,Integer> second = (Treap<Integer,Integer>)first.headMap(29);
        assertEquals(second.get(21), (Integer)3);
    }

    @Test
    public void testTailMap() throws Exception {
        Treap<Integer, Integer> first = new Treap<Integer, Integer>(12,3, 4, null, null);
        first.put(29, 9, 3);
        first.put(14, 10, 2);
        first.put(21, 3, 4);
        Treap<Integer,Integer> second = (Treap<Integer,Integer>)first.tailMap(21);
        assertEquals(second.get(21), (Integer)3);
    }
    /*
    @Test
    public void testIsEmpty() throws Exception {
        Treap first = new Treap(3, 4, null, null);
        first.add(9, 3);
        first.add(10, 2);
        first.add(4, 8);
        assertFalse(first.isEmpty());
    }

    @Test
    public void testContainsKeyValue() throws Exception {
        Treap first = new Treap(3, 4, null, null);
        first.add(9, 3);
        first.add(10, 2);
        first.add(4, 8);
        assertTrue(first.containsKey(9));
        assertFalse(first.containsKey(142));
    }

    @Test
    public void testGet() throws Exception {
        Treap first = new Treap(3, 4, null, null);
        first.add(9, 3);
        first.add(10, 2);
        first.add(4, 8);
        assertEquals(((Treap.TreapNode) first.get(4)).getPriority(), 8);
    }

    @Test
    public void testClear() throws Exception {
        Treap first = new Treap(3, 4, null, null);
        first.add(9, 3);
        first.add(10, 2);
        first.add(4, 8);
        first.clear();
        assertNull(first.left);
        assertNull(first.right);
        assertEquals(first.root.getPriority(), 0);
        assertNull(first.root.getValue());
    }

    @Test
    public void testValues() throws Exception {
        Treap first = new Treap(3, 4, null, null);
        first.add(9, 3);
        first.add(10, 2);
        first.add(4, 8);
        ArrayList arr = (ArrayList) first.values();
        assertEquals(arr.size(), 4);
    }*/
}
