import org.junit.AfterClass;
import org.junit.Test;
import static junit.framework.Assert.*;


public class TreapTest {
    @Test
    public void testMerge() throws Exception
    {
        Treap first = new Treap(3, 4, null, null);
        Treap second = new Treap(2, 7, null, null);
        Treap three = new Treap (4, 5, null, null);
        Treap souz = Treap.merge(first, three);
        Treap souz2 = Treap.merge(second, souz);
        assertEquals(souz2.right.left.root.value, 3);
    }

    @Test
    public void testSplit() throws Exception
    {
        Treap first = new Treap(3, 4, null, null);
        Treap second = new Treap(2, 7, null, null);
        Treap three = new Treap (4, 5, null, null);
        Treap souz = Treap.merge(first, three);
        Treap souz2 = Treap.merge(second, souz);
        Treap splitTest1 = new Treap(null, 0, null, null);
        Treap splitTest2 = new Treap(null, 0, null, null);
        souz2.split(4, splitTest1, splitTest2);
        assertEquals(splitTest1.right.left.root.value, 3);
    }

    @Test
    public void testAdd() throws Exception {
        Treap first = new Treap(3, 4, null, null);
        first.add(9, 3);
        first.add(10, 2);
        assertEquals(first.right.right.root.value, 10);
    }

    @Test
    public void testAdd2() throws Exception {
        Treap first = new Treap(3, 500, null, null);
        first.add(9);
        assertNotNull(first.root.priority);
    }

    @Test
    public void testCreateWithAdd() throws Exception {
        Treap first = new Treap(3, 4, null, null);
        Treap second = first.createWithAdd(9, 3);
        assertEquals(second.right.root.value, 9);
    }

    @Test
    public void testRemove() throws Exception {
        Treap first = new Treap(3, 4, null, null);
        first.add(9, 3);
        first.add(10, 2);
        first.add(3, 4);
        first.remove(3);
        assertEquals(first.left.root.value, null);
    }

    @Test
    public void testSizeOf() throws Exception {
        Treap first = new Treap(3, 4, null, null);
        first.add(9, 3);
        first.add(10, 2);
        first.remove(3);
        assertEquals(Treap.sizeOf(first), 2);
    }

    @Test
    public void testKthNode() throws Exception {
        Treap first = new Treap(3, 4, null, null);
        first.add(9, 3);
        first.add(10, 2);
        assertEquals(first.kthNode(1), 9);
    }
}
