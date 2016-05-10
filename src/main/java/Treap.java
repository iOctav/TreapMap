import com.sun.istack.internal.NotNull;

import java.util.*;


public class Treap<T extends Comparable> implements Cloneable, SortedMap {
    TreapNode<T> root;
    int size;

    public Treap<T> left;
    public Treap<T> right;

    public Treap(T value, int priority, Treap left, Treap right) {
        this.root = new TreapNode<T>(value, priority);
        this.left = left;
        this.right = right;
        size = 1;
    }

    // only for internals methods, refresh values without created new object
    private void update(T value, int priority, Treap left, Treap right) {
        this.root.value = value;
        this.root.priority = priority;
        this.left = left;
        this.right = right;
        this.size = size;
    }

    // only for internals methods, refresh values without created new object
    private void update(Treap<T> tree) {
        this.root.value = tree.root.value;
        this.root.priority = tree.root.priority;
        this.left = tree.left;
        this.right = tree.right;
        this.size = tree.size;
    }

    // !!!Values Left Treap >= Right Treap!!!
    public static Treap merge(Treap L, Treap R) {
        if (R == null || R.root.value == null) return L;
        if (L == null || L.root.value == null) return R;

        Treap ans;
        if (L.root.priority > R.root.priority) {
            Treap newR = merge(L.right, R);
            ans = new Treap(L.root.value, L.root.priority, L.left, newR);
        } else {
            Treap newL = merge(L, R.left);
            ans = new Treap(R.root.value, R.root.priority, newL, R.right);
        }
        ans.recalc();
        return ans;
    }


    // !!! need L and R Treap with root.value = null !!!
    public void split(T value, @NotNull Treap<T> L, @NotNull Treap<T> R) {
        Treap newTree = new Treap(null, 0, null, null);
        int compare = this.root.value.compareTo(value);
        if (compare <= 0)
        {
            if (this.right == null || this.right.root.value == null) {
                R.root.value = null;
            } else {
                right.split(value, newTree, R);
            }
            L.update(this.root.value, this.root.priority, this.left, newTree);
            L.recalc();
        }
        else
        {
            if (this.left == null || this.left.root.value == null) {
                L.root.value = null;
            } else {
                this.left.split(value, L, newTree);
            }
            R.update(this.root.value, this.root.priority, newTree, this.right);
            R.recalc();
        }
    }

    // add node to this treap
    public void add(T value, int priority) {
        Treap l = new Treap(null, 0, null, null);
        Treap r = new Treap(null, 0, null, null);
        this.split(value, l, r);
        Treap m = new Treap(value, priority, null, null);
        this.update(merge(merge(l, m), r));
    }

    public void add(T value) {
        Treap l = new Treap(null, 0, null, null);
        Treap r = new Treap(null, 0, null, null);
        split(value, l, r);
        Random rand = new Random();
        Treap m = new Treap(value, rand.nextInt(Integer.MAX_VALUE - 1), null, null);
        this.update(merge(merge(l, m), r));
    }

    // return treap with new node
    public Treap<T> createWithAdd(T value, int priority) {
        Treap<T> l = new Treap<T>(null, 0, null, null);
        Treap<T> r = new Treap<T>(null, 0, null, null);
        split(value, l, r);
        Treap<T> m = new Treap<T>(value, priority, null, null);
        return merge(merge(l, m), r);
    }

    // remove node
    public void remove(T x) {
        Treap<T> l = new Treap<T>(null, 0, null, null);
        Treap<T> r = new Treap<T>(null, 0, null, null);
        Treap<T> m = new Treap<T>(null, 0, null, null);
        this.split(x, l, r);
        T tmp;
        if (l.right == null || l.right.root.value == null) {
            if (l.left == null || l.left.root.value == null) {
                this.update(r);
                return;
            } else {
                tmp = l.root.value;
            }
        } else {
            tmp = l.root.value;
        }
        l.split(tmp, m, l);
        this.update(merge(l, r));
    }

    protected void recalc() {
        this.size = sizeOf(this.left) + sizeOf(this.right) + 1;
    }

    public static int sizeOf(Treap treap) {        return treap == null || treap.root.value == null ? 0 : treap.size;
    }

    public TreapNode<T> kthNode(int K) {
        Treap cur = this;
        while (cur != null || cur.root.value != null)
        {
            int sizeLeft = sizeOf(cur.left);

            if (sizeLeft == K)
                return (TreapNode<T>) cur.root;

            cur = sizeLeft > K ? cur.left : cur.right;
            if (sizeLeft < K)
                K -= sizeLeft + 1;
        }
        return null;
    }

    public int kOfNode(T node) {
        for (int i = 0; i < sizeOf(this); i++)
        {
            if (node.equals(kthNode(i).value)) {
                return i;
            }
        }
        return -1;
    }


    public Comparator comparator() {
        return null;
    }

    public SortedMap subMap(Object fromKey, Object toKey) {
        int fromK = this.kOfNode((T)fromKey);
        int toK = this.kOfNode((T)toKey);
        if (fromK == -1 || toK == -1 || toK < fromK) {
            return null;
        }
        Treap subMap = new Treap(kthNode(fromK).value, kthNode(fromK).priority, null, null);
        for (int i = fromK + 1; i <= toK; i++)
        {
            TreapNode<T> tmp = kthNode(i);
            subMap.add(tmp.value, tmp.priority);
        }
        return subMap;
    }

    public SortedMap headMap(Object toKey) {
        int toK = this.kOfNode((T)toKey);
        if (toK == -1)
        {
            return null;
        }
        TreapNode<T> tmp1 = this.kthNode(toK);
        Treap headMap = new Treap(tmp1.value, tmp1.priority, null, null);
        for (int i = 0; i < toK; i++)
        {
            TreapNode<T> tmp = this.kthNode(i);
            headMap.add(tmp.value, tmp.priority);
        }
        return headMap;
    }

    public SortedMap tailMap(Object fromKey) {
        int fromK = kOfNode((T)fromKey);
        if (fromK == -1)
        {
            return null;
        }
        TreapNode<T> tmp1 = kthNode(fromK);
        Treap<T> tailMap = new Treap<T>(tmp1.value, tmp1.priority, null, null);
        for (int i = (fromK +1); i < sizeOf(this); i++)
        {
            TreapNode<T> tmp = kthNode(i);
            tailMap.add(tmp.value, tmp.priority);
        }
        return tailMap;
    }

    public Object firstKey() {
        return kthNode(0).value;
    }

    public Object lastKey() {
        return kthNode(sizeOf(this) - 1).value;
    }

    public int size() {
        return sizeOf(this);
    }

    public boolean isEmpty() {
        if (root.value == null) {
            return true;
        }
        return false;
    }

    public boolean containsKey(Object key) {
        return kOfNode((T)key) != -1;
    }

    // Because value == key in Treap!!
    public boolean containsValue(Object value) {
        return containsKey(value);
    }

    public Object get(Object key) {
        if (!containsKey(key)) {
            return null;
        }
        return kthNode(kOfNode((T) key));
    }

    public Object put(Object key, Object value) {
        if (key.equals(value)) {
            this.add((T)value);
        }
        return null;
    }

    public Object remove(Object key) {
        if (!containsKey(key)) {
            return null;
        }
        TreapNode<T> removedNode = kthNode(kOfNode((T)key));
        this.remove((T) key);
        return removedNode;
    }

    public void putAll(Map m) {
        int size = sizeOf((Treap)m);
        for (int i = 0; i < size; i++) {
            TreapNode<T> tmp = ((Treap) m).kthNode(i);
            this.add(tmp.value, tmp.priority);
        }
    }

    public void clear() {
        this.update(null, 0, null, null);
    }

    public Set keySet() {
        return (Set)this.values();
    }

    public Collection values() {
        Collection<T> keys = new ArrayList<T>();
        for (int i = 0; i < sizeOf(this); i++)
        {
            keys.add(kthNode(i).value);
        }
        return keys;
    }

    //it is impossible, because key = value
    public Set<Entry> entrySet() {
        return null;
    }
}