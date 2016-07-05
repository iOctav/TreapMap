package ru.spbstu.student.treap;

import org.junit.Test;

import java.util.*;
import static junit.framework.Assert.*;


public class TreapMapTest {
    @Test
    public void testRemove() throws Exception {
        TreapMap<Integer, Integer> first = new TreapMap<Integer, Integer>(12, 3, 4,
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
        TreapMap<Integer, Integer> first = new TreapMap<Integer, Integer>(12, 3, 4,
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
        TreapMap<Integer, Integer> first = new TreapMap<Integer, Integer>(12, 3, 4,
                null, null);
        Random rand = new Random();
        int N = 100000;
        for (int i = 0; i < N; i++) {
            first.put(rand.nextInt(), rand.nextInt(), rand.nextInt());
        }
        first.put(29, 9, 3);
        TreapMap<Integer, Integer> second = (TreapMap<Integer, Integer>) first.subMap(12, 29);
        assertEquals(second.get(12), (Integer) 3);
        assertNull(second.get(29));
    }

    @Test
    public void testHeadMap() throws Exception {
        TreapMap<Integer, Integer> first = new TreapMap<Integer, Integer>(12, 3, 4,
                null, null);
        Random rand = new Random();
        int N = 100000;
        for (int i = 0; i < N; i++) {
            first.put(rand.nextInt(), rand.nextInt(), rand.nextInt());
        }
        first.put(29, 9, 3);
        TreapMap<Integer, Integer> second = (TreapMap<Integer, Integer>) first.headMap(29);
        assertNull(second.get(29));
    }

    @Test
    public void testTailMap() throws Exception {
        TreapMap<Integer, Integer> first = new TreapMap<Integer, Integer>(12, 3, 4,
                null, null);
        Random rand = new Random();
        int N = 100000;
        for (int i = 0; i < N; i++) {
            first.put(rand.nextInt(), rand.nextInt(), rand.nextInt());
        }
        first.put(29, 9, 3);
        TreapMap<Integer, Integer> second = (TreapMap<Integer, Integer>) first.tailMap(12);
        assertEquals(second.get(12), (Integer) 3);
    }

    @Test
    public void testIsEmpty() throws Exception {
        TreapMap<Integer, Integer> first = new TreapMap<Integer, Integer>(12, 3, 4,
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
        TreapMap<Integer, Integer> first = new TreapMap<Integer, Integer>(12, 3, 4,
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
        TreapMap<Integer, Integer> first = new TreapMap<Integer, Integer>(12, 3, 4,
                null, null);
        Random rand = new Random();
        int N = 100000;
        for (int i = 0; i < N; i++) {
            first.put(rand.nextInt(), rand.nextInt(), rand.nextInt());
        }
        assertEquals(first.get(12), (Integer) 3);
    }

    @Test
    public void testClear() throws Exception {
        TreapMap<Integer, Integer> first = new TreapMap<Integer, Integer>(12, 3, 4,
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
        TreapMap<Integer, Integer> first = new TreapMap<Integer, Integer>(12, 3, 4,
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
        TreapMap<Integer, Integer> first = new TreapMap<Integer, Integer>(12, 3, 4,
                null, null);
        Random rand = new Random();
        int N = 50000;
        for (int i = 0; i < N; i++) {
            first.put(rand.nextInt(), rand.nextInt(), rand.nextInt());
        }
        Set<Integer> scn = first.keySet();
        Object[] arr = new Object[N+1];
        arr = scn.toArray();
        for(int i = 0; i < N; i++) {
            for (int j = i+1; j < (N+1); j++)
            {
                assertFalse(arr[i].equals(arr[j]));
            }
        }
    }

    @Test
    public void testPutAll() throws Exception {
        TreapMap<Integer, Integer> first = new TreapMap<Integer, Integer>(12, 3, 4,
                null, null);
        Random rand = new Random();
        int N = 100000;
        for (int i = 0; i < N; i++) {
            first.put(rand.nextInt(), rand.nextInt(), rand.nextInt());
        }
        TreapMap<Integer, Integer> second = new TreapMap<Integer, Integer>(null, null, 0,
                null, null);
        second.putAll(first);
        assertEquals(second.size(), first.size());
    }

    @Test
    public void testEntrySet() throws Exception {
        TreapMap<Integer, Integer> first = new TreapMap<Integer, Integer>(12, 3, 4,
                null, null);
        Random rand = new Random();
        int N = 100000;
        for (int i = 0; i < N; i++) {
            first.put(rand.nextInt(), rand.nextInt(), rand.nextInt());
        }
        TreapMap<Integer, Integer> second = new TreapMap<Integer, Integer>(null, null, 0,
                null, null);
        Iterator entries = first.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry thisEntry = (Map.Entry) entries.next();
            second.put((Integer) thisEntry.getKey(), (Integer) thisEntry.getValue());
        }
        assertEquals(first.size(), second.size());
    }
}