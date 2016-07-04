import org.junit.Test;

import TreapMap.Treap;
import java.util.Collection;
import java.util.Random;
import java.util.Set;

import static junit.framework.Assert.*;


public class TreapTest {
    @Test
    public void testRemove() throws Exception {
        Treap<Integer, Integer> first = new Treap<Integer, Integer>(12,3, 4,
                                                                    null, null);
        Random rand = new Random();
        int fh;
        int N = 100000;
        for (int i = 0; i < N; i++) {
            fh = rand.nextInt();
            first.put(fh, rand.nextInt(), rand.nextInt());
            first.remove(fh);
        }
        assertEquals(first.size(), 1);
    }

    @Test
    public void testSize() throws Exception {
        Treap<Integer, Integer> first = new Treap<Integer, Integer>(12,3, 4,
                                                    null, null);
        Random rand = new Random();
        int N = 100000;
        for (int i = 0; i < N; i++) {
            first.put(rand.nextInt(), rand.nextInt(), rand.nextInt());
        }
        assertEquals(first.size(), N + 1);
    }

    @Test
    public void testSubMap() throws Exception {
        Treap<Integer, Integer> first = new Treap<Integer, Integer>(12,3, 4,
                                                    null, null);
        Random rand = new Random();
        int N = 100000;
        for (int i = 0; i < N; i++) {
            first.put(rand.nextInt(), rand.nextInt(), rand.nextInt());
        }
        first.put(29, 9, 3);
        Treap<Integer,Integer> second = (Treap<Integer,Integer>)first.subMap(12, 29);
        assertEquals(second.get(12), (Integer)3);
        assertNull(second.get(29));
    }


    @Test
    public void testHeadMap() throws Exception {
        Treap<Integer, Integer> first = new Treap<Integer, Integer>(12,3, 4,
                                                    null, null);
        Random rand = new Random();
        int N = 100000;
        for (int i = 0; i < N; i++) {
            first.put(rand.nextInt(), rand.nextInt(), rand.nextInt());
        }
        first.put(29, 9, 3);
        Treap<Integer,Integer> second = (Treap<Integer,Integer>)first.headMap(29);
        assertNull(second.get(29));
    }

    @Test
    public void testTailMap() throws Exception {
        Treap<Integer, Integer> first = new Treap<Integer, Integer>(12,3, 4,
                                                    null, null);
        Random rand = new Random();
        int N = 100000;
        for (int i = 0; i < N; i++) {
            first.put(rand.nextInt(), rand.nextInt(), rand.nextInt());
        }
        first.put(29, 9, 3);
        Treap<Integer,Integer> second = (Treap<Integer,Integer>)first.tailMap(12);
        assertEquals(second.get(12), (Integer) 3);
    }

    @Test
    public void testIsEmpty() throws Exception {
        Treap<Integer, Integer> first = new Treap<Integer, Integer>(12,3, 4,
                                                    null, null);
        Random rand = new Random();
        int N = 100000;
        for (int i = 0; i < N; i++) {
            first.put(rand.nextInt(), rand.nextInt(), rand.nextInt());
        }
        assertFalse(first.isEmpty());
    }

    @Test
    public void testContainsKeyValue() throws Exception {
        Treap<Integer, Integer> first = new Treap<Integer, Integer>(12,3, 4,
                                                    null, null);
        Random rand = new Random();
        int N = 100000;
        for (int i = 0; i < N; i++) {
            first.put(rand.nextInt(), rand.nextInt(), rand.nextInt());
        }
        assertTrue(first.containsKey(12));
        assertTrue(first.containsValue(3));
    }

    @Test
    public void testGet() throws Exception {
        Treap<Integer, Integer> first = new Treap<Integer, Integer>(12,3, 4,
                                                    null, null);
        Random rand = new Random();
        int N = 100000;
        for (int i = 0; i < N; i++) {
            first.put(rand.nextInt(), rand.nextInt(), rand.nextInt());
        }
        assertEquals(first.get(12), (Integer)3);
    }

    @Test
    public void testClear() throws Exception {
        Treap<Integer, Integer> first = new Treap<Integer, Integer>(12,3, 4,
                                                    null, null);
        Random rand = new Random();
        int N = 100000;
        for (int i = 0; i < N; i++) {
            first.put(rand.nextInt(), rand.nextInt(), rand.nextInt());
        }
        first.clear();
        assertNull(first.getRootKey());
        assertNull(first.left);
        assertNull(first.right);
    }

    @Test
    public void testValues() throws Exception {
        Treap<Integer, Integer> first = new Treap<Integer, Integer>(12,3, 4,
                                                    null, null);
        Random rand = new Random();
        int N = 100000;
        for (int i = 0; i < N; i++) {
            first.put(rand.nextInt(), rand.nextInt(), rand.nextInt());
        }
        Collection<Integer> scn = first.values();
        assertEquals(scn.size(), N + 1);
    }

    @Test
    public void testKeySet() throws Exception {
        Treap<Integer, Integer> first = new Treap<Integer, Integer>(12,3, 4,
                                                    null, null);
        Random rand = new Random();
        int N = 100000;
        for (int i = 0; i < N; i++) {
            first.put(rand.nextInt(), rand.nextInt(), rand.nextInt());
        }
        Set<Integer> scn = first.keySet();
        assertEquals(scn.size(), N + 1);
    }
}
