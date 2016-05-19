import com.sun.istack.internal.NotNull;
import java.io.Serializable;
import java.util.*;


public class Treap<K, V extends Comparable<V>> implements Cloneable, SortedMap<K,V>, Serializable {
    private TreapNode<K,V> root;
    private int size;

    public Treap<K,V> left;
    public Treap<K,V> right;

    public Treap(K keyRoot,V valueRoot, int priorityRoot, Treap<K,V> leftRoot, Treap<K,V> rightRoot) {
        this.root = new TreapNode<K, V>(keyRoot, valueRoot, priorityRoot);
        this.left = leftRoot;
        this.right = rightRoot;
        size = 1;
    }

    /*public Treap(K keyRoot, V valueRoot, int priorityRoot, Treap leftRoot, Treap rightRoot, Comparator<V> comparator) {
        this.root = new TreapNode<K,V>(keyRoot, valueRoot, priorityRoot);
        this.left = leftRoot;
        this.right = rightRoot;
        size = 1;
        this.comparator = comparator;
    }*/

    // only for internals methods, refresh values without created new object
    private void update(K key,V value, int priority, Treap<K,V> left, Treap<K,V> right) {
        this.root.value = value;
        this.root.priority = priority;
        this.root.key = key;
        this.left = left;
        this.right = right;
    }

    // only for internals methods, refresh values without created new object
    private void update(Treap<K,V> tree) {
        this.root.value = tree.root.value;
        this.root.priority = tree.root.priority;
        this.root.key = tree.root.key;
        this.left = tree.left;
        this.right = tree.right;
        this.size = tree.size;
    }

    // !!!Values Left Treap >= Right Treap!!!
    private Treap<K,V> merge(Treap<K,V> L, Treap<K,V> R) {
        if (R == null || R.root.value == null) return L;
        if (L == null || L.root.value == null) return R;

        Treap<K,V> ans;
        if (L.root.priority > R.root.priority) {
            Treap<K,V> newR = merge(L.right, R);
            ans = new Treap<K,V>(L.root.key, L.root.value, L.root.priority, L.left, newR);
        } else {
            Treap<K,V> newL = merge(L, R.left);
            ans = new Treap<K,V>(R.root.key, R.root.value, R.root.priority, newL, R.right);
        }
        ans.recalc();
        return ans;
    }

    // !!! need L and R Treap with root.value = null !!!
    private void split(V value, @NotNull Treap<K,V> L, @NotNull Treap<K,V> R) {
        Treap<K,V> newTree = new Treap<K,V>(null, null, 0, null, null);
        int compare = this.root.value.compareTo(value);
        if (compare <= 0)
        {
            if (this.right == null || this.right.root.value== null) {
                R.root.value = null;
            } else {
                right.split(value, newTree, R);
            }
            L.update(this.root.key, this.root.value, this.root.priority, this.left, newTree);
            L.recalc();
        }
        else
        {
            if (this.left == null || this.left.root.value == null) {
                L.root.value = null;
            } else {
                this.left.split(value, L, newTree);
            }
            R.update(this.root.key, this.root.value, this.root.priority, newTree, this.right);
            R.recalc();
        }
    }

    // add node to this treap
    private void add(K key, V value, int priority) {
        Treap<K,V> l = new Treap<K,V>(null, null, 0, null, null);
        Treap<K,V> r = new Treap<K,V>(null, null, 0, null, null);
        this.split(value, l, r);
        Treap<K,V> m = new Treap<K,V>(key ,value, priority, null, null);
        this.update(merge(merge(l, m), r));
    }

    // remove node
    private void remove(V x) {
        Treap<K,V> l = new Treap<K,V>(null, null, 0, null, null);
        Treap<K,V> r = new Treap<K,V>(null, null, 0, null, null);
        Treap<K,V> m = new Treap<K,V>(null, null, 0, null, null);
        Treap<K,V> l1 = new Treap<K,V>(null, null, 0, null, null);
        this.split(x, l, r);
        V tmp;
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

        l.split(tmp, m, l1);
        this.update(merge(l1, r));
    }

    private void recalc() {
        this.size = sizeOf(this.left) + sizeOf(this.right) + 1;
    }

    private static int sizeOf(Treap treap) {
        return treap == null || treap.root.value== null ? 0 : treap.size;
    }

    private TreapNode<K,V> kthNode(int K) {
        Treap<K,V> cur = this;
        while (cur != null || cur.root.value != null)
        {
            int sizeLeft = sizeOf(cur.left);

            if (sizeLeft == K)
                return cur.root;

            cur = sizeLeft > K ? cur.left : cur.right;
            if (sizeLeft < K)
                K -= sizeLeft + 1;
        }
        return null;
    }

    private int kOfNodeKey(K key) {
        for (int i = 0; i < sizeOf(this); i++)
        {
            if (key.equals(kthNode(i).key)) {
                return i;
            }
        }
        return -1;
    }

    private class TreapNode<K,V extends Comparable<V>>
    {
        K key;
        V value;
        int priority;

        public TreapNode(K key,V value, int priority)
        {
            this.key = key;
            this.value = value;
            this.priority = priority;
        }

        public void displayNode()
        {
            System.out.println(this.toString());
        }

        @Override
        public boolean equals(Object node) {
            TreapNode<K,V> tmp = (TreapNode<K,V>) node;
            if (this.value.equals(tmp.value) && (this.priority == tmp.priority)
                   && this.key.equals(tmp.key)) {
                return true;
            }
            return false;
        }

        @Override
        public String toString() {
            return ("key: " + this.key + " | value: " + this.value + " | priority: " + this.priority);

        }
    }

    public void dispay () {
        queueNodes(this);
    }

    private void queueNodes(Treap<K,V> locRoot)
        {
            Treap<K,V> x = locRoot;
            if (x == null)
            {
                return;
            } else
                for (int i = 0; i < sizeOf(this); i++) {
                    kthNode(i).displayNode();
                }
        }

    public Comparator comparator() {
        return null;
    }

    public SortedMap<K,V> subMap(Object fromKey, Object toKey) {
        int fromK = this.kOfNodeKey((K)fromKey);
        int toK = this.kOfNodeKey((K)toKey);
        if (fromK == -1 || toK == -1 || toK < fromK) {
            return null;
        }
        TreapNode<K,V> afr = kthNode(fromK);
        Treap<K,V> subMap = new Treap<K,V>(afr.key ,afr.value, afr.priority, null, null);
        for (int i = fromK + 1; i <= toK; i++)
        {
            TreapNode<K,V> tmp = kthNode(i);
            subMap.add(tmp.key, tmp.value, tmp.priority);
        }
        return subMap;
    }

    public SortedMap<K,V> headMap(Object toKey) {
        int toK = this.kOfNodeKey((K)toKey);
        if (toK == -1)
        {
            return new Treap<K,V>(null, null, 0, null, null);
        }
        TreapNode<K,V> tmp1 = this.kthNode(toK);
        Treap<K,V> headMap = new Treap<K,V>(tmp1.key, tmp1.value, tmp1.priority, null, null);
        for (int i = 0; i < toK; i++)
        {
            TreapNode<K,V> tmp = this.kthNode(i);
            headMap.add(tmp.key ,tmp.value, tmp.priority);
        }
        return headMap;
    }

    public SortedMap<K,V> tailMap(Object fromKey) {
        int fromK = kOfNodeKey((K)fromKey);
        if (fromK == -1)
        {
            return new Treap<K,V>(null, null, 0, null, null);
        }
        TreapNode<K,V> tmp1 = kthNode(fromK);
        Treap<K,V> tailMap = new Treap<K,V>(tmp1.key, tmp1.value, tmp1.priority, null, null);
        for (int i = (fromK +1); i < sizeOf(this); i++)
        {
            TreapNode<K,V> tmp = kthNode(i);
            tailMap.add(tmp.key ,tmp.value, tmp.priority);
        }
        return tailMap;
    }

    public K firstKey() {
        if (kthNode(0) == null)
            throw new NullPointerException("empty map");
        else
            return kthNode(0).key;
    }

    public K lastKey() {
        if (kthNode(sizeOf(this) - 1) == null)
            throw new NullPointerException("empty map");
        else
            return kthNode(sizeOf(this) - 1).key;
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
        return kOfNodeKey((K)key) != -1;
    }

    public boolean containsValue(Object value) {
        for (int i = 0; i < sizeOf(this); i++)
        {
            if (value.equals(kthNode(i).value)) {
                return true;
            }
        }
        return false;
    }

    public V get(Object key) {
        if (!containsKey(key)) {
            return null;
        }
        return kthNode(kOfNodeKey((K) key)).value;
    }

    public V put(K key, V value) {
        if (containsKey(key)) {
            V tmp = this.remove(key);
            this.add(key, value, new Random().nextInt(Integer.MAX_VALUE));
            return tmp;
        } else {
            this.add(key, value, new Random().nextInt(Integer.MAX_VALUE));
            return null;
        }
    }

    public V put(K key, V value, int priority) {
        if (containsKey(key)) {
            V tmp = this.remove(key);
            this.add(key, value, priority);
            return tmp;
        } else {
            this.add(key, value, priority);
            return null;
        }
    }

    public V remove(Object key) {
        if (!containsKey(key)) {
            return null;
        }
        V removedValue = kthNode(kOfNodeKey((K)key)).value;
        this.remove(removedValue);
        return removedValue;
    }

    public void putAll(Map<? extends K, ? extends V> m) {
        for (int i = 0; i < sizeOf((Treap)m); i++){
            TreapNode<K,V> tmp = kthNode(i);
            this.put(tmp.key, tmp.value);
        }
    }

    public void clear() {
        this.update(null, null, 0, null, null);
    }

    public Set<K> keySet() {
        Set<K> keySet = new HashSet<K>();
        for (int i = 0; i < (sizeOf(this) - 1); i++){
            keySet.add(kthNode(i).key);
        }
        return keySet;
    }

    public Collection<V> values() {
        Collection<V> values = new ArrayList<V>();
        for (int i = 0; i < sizeOf(this); i++)
        {
            values.add(kthNode(i).value);
        }
        return values;
    }

    public Set<Entry<K, V>> entrySet() {
        return null;
    }
}