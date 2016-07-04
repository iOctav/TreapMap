package TreapMap;

import com.sun.istack.internal.NotNull;

import java.io.Serializable;
import java.util.*;


public class Treap<K extends Comparable<K>, V> implements Cloneable, SortedMap<K, V>, Serializable {
    private TreapNode<K, V> root;
    private int size;
    private Comparator<? super K> comparator;

    public Treap<K, V> left;
    public Treap<K, V> right;

    public Treap(K keyRoot, V valueRoot, int priorityRoot,
                 Treap<K, V> leftRoot, Treap<K, V> rightRoot) {
        this.root = new TreapNode<K, V>(keyRoot, valueRoot, priorityRoot);
        this.left = leftRoot;
        this.right = rightRoot;
        this.size = 1;
        this.comparator = null;
    }

    public Treap(K keyRoot, V valueRoot, int priorityRoot,
                 Treap leftRoot, Treap rightRoot, Comparator<K> comparator) {
        this.root = new TreapNode<K, V>(keyRoot, valueRoot, priorityRoot);
        this.left = leftRoot;
        this.right = rightRoot;
        this.size = 1;
        this.comparator = comparator;
    }

    public K getRootKey() {
        return root.getKey();
    }

    // only for internals methods, refresh values without created new object
    private void update(K key, V value, int priority,
                        Treap<K, V> left, Treap<K, V> right, Comparator<? super K> comparator) {
        this.root.value = value;
        this.root.priority = priority;
        this.root.key = key;
        this.left = left;
        this.right = right;
        this.comparator = comparator;
        this.recalc();
    }

    // only for internals methods, refresh values without created new object
    private void update(Treap<K, V> tree) {
        this.update(tree.root.key, tree.root.value, tree.root.priority,
                tree.left, tree.right, tree.comparator);
    }



    // !!!Values Left Treap >= Right Treap!!!
    private Treap<K, V> merge(Treap<K, V> L, Treap<K, V> R) {
        if (R == null || R.root.key == null) return L;
        if (L == null || L.root.key == null) return R;

        Treap<K, V> ans;
        if (L.root.priority > R.root.priority) {
            Treap<K, V> newR = merge(L.right, R);
            ans = new Treap<K, V>(L.root.key, L.root.value, L.root.priority,
                    L.left, newR);
        } else {
            Treap<K, V> newL = merge(L, R.left);
            ans = new Treap<K, V>(R.root.key, R.root.value, R.root.priority,
                    newL, R.right);
        }
        ans.recalc();
        return ans;
    }

    // !!! need L and R Treap with root.value = null !!!
    private void split(K key, @NotNull Treap<K, V> L, @NotNull Treap<K, V> R) {
        Treap<K, V> newTree = new Treap<K, V>(null, null, 0, null, null);
        int compare = this.root.key.compareTo(key);
        if (compare <= 0) {
            if (this.right == null || this.right.root.key == null) {
                R.root.key = null;
            } else {
                right.split(key, newTree, R);
            }
            L.update(this.root.key, this.root.value, this.root.priority,
                    this.left, newTree, this.comparator);
        } else {
            if (this.left == null || this.left.root.key == null) {
                L.root.key = null;
            } else {
                this.left.split(key, L, newTree);
            }
            R.update(this.root.key, this.root.value, this.root.priority,
                    newTree, this.right, this.comparator);
        }
    }

    // add node to this treap
    private void add(K key, V value, int priority) {
        Treap<K, V> l = new Treap<K, V>(null, null, 0, null, null);
        Treap<K, V> r = new Treap<K, V>(null, null, 0, null, null);
        this.split(key, l, r);
        Treap<K, V> m = new Treap<K, V>(key, value, priority,
                null, null);
        this.update(merge(merge(l, m), r));
    }

    private void recalc() {
        this.size = sizeOf(this.left) + sizeOf(this.right) + 1;
    }

    private static int sizeOf(Treap treap) {
        return treap == null || treap.root.value == null ? 0 : treap.size;
    }

    private TreapNode<K, V> kthNode(int K) {
        Treap<K, V> cur = this;
        while (cur != null || cur.root.key != null) {
            int sizeLeft = sizeOf(cur.left);

            if (sizeLeft == K)
                return cur.root;

            cur = sizeLeft > K ? cur.left : cur.right;
            if (sizeLeft < K)
                K -= sizeLeft + 1;
        }
        return null;
    }

    private class TreapNode<K extends Comparable<K>, V> {
        K key;
        V value;
        int priority;

        public TreapNode(K key, V value, int priority) {
            this.key = key;
            this.value = value;
            this.priority = priority;
        }

        @Override
        public boolean equals(Object node) {
            if (!node.getClass().equals(this.getClass())) {
                return false;
            }
            TreapNode<K, V> cmn = (TreapNode<K, V>) node;
            if (this.value.equals(cmn.value) && (this.priority == cmn.priority)
                    && this.key.equals(cmn.key)) {
                return true;
            }
            return false;
        }

        public K getKey() {
            return this.key;
        }

        public V getValue() {
            return this.value;
        }

        public int getPriority() {
            return this.priority;
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.key, this.value, this.priority);
        }

        @Override
        public String toString() {
            return ("key: " + this.key + " | value: " + this.value + " | priority: " + this.priority);

        }
    }

    public Comparator comparator() {
        return this.comparator;
    }

    public SortedMap<K, V> subMap(K fromKey, K toKey) {
        Treap<K, V> l = new Treap<K, V>(null, null, 0, null, null);
        Treap<K, V> r = new Treap<K, V>(null, null, 0, null, null);
        Treap<K, V> r1 = new Treap<K, V>(null, null, 0, null, null);
        Treap<K, V> l2 = new Treap<K, V>(null, null, 0, null, null);
        if (fromKey.equals(toKey)) {
            this.split(fromKey, l, r);
            Treap<K, V> l1 = l;
            r1 = l;
            if (l.size() > 1) {
                if (l.root.getKey().equals(fromKey))
                    r1 = l;
                else {
                    while (!l1.right.root.getKey().equals(fromKey)) {
                        l1 = l1.right;
                    }
                    l.split(l1.root.getKey(), l2, r1);
                }
            }
            return r1;
        }
        return this.tailMap(fromKey).headMap(toKey);
    }

    public SortedMap<K, V> headMap(K toKey) {
        Treap<K, V> l = new Treap<K, V>(null, null, 0, null, null);
        Treap<K, V> r = new Treap<K, V>(null, null, 0, null, null);
        Treap<K, V> l2 = new Treap<K, V>(null, null, 0, null, null);
        this.split(toKey, l, r);
        Treap<K, V> l1 = l;
        if (l.size() > 1) {
            if (l.root.getKey().equals(toKey))
                l2 = l.left;
            else {
                while (!l1.right.root.getKey().equals(toKey)) {
                    l1 = l1.right;
                }
                l.split(l1.root.getKey(), l2, r);
            }
        }
        return l2;
    }

    public SortedMap<K, V> tailMap(K fromKey) {
        Treap<K, V> l = new Treap<K, V>(null, null, 0, null, null);
        Treap<K, V> r = new Treap<K, V>(null, null, 0, null, null);
        Treap<K, V> l2 = new Treap<K, V>(null, null, 0, null, null);
        Treap<K, V> r1 = new Treap<K, V>(null, null, 0, null, null);
        this.split(fromKey, l, r);
        Treap<K, V> l1 = l;
        if (l.size() > 1) {
            if (l.root.getKey().equals(fromKey))
                r1 = l;
            else {
                while (!l1.right.root.getKey().equals(fromKey)) {
                    l1 = l1.right;
                }
                l.split(l1.root.getKey(), l2, r1);
            }
        }
        return merge(r1, r);
    }

    public K firstKey() {
        if (kthNode(0) == null)
            throw new NoSuchElementException("empty map");
        else
            return kthNode(0).getKey();
    }

    public K lastKey() {
        if (kthNode(sizeOf(this) - 1) == null)
            throw new NoSuchElementException("empty map");
        else
            return kthNode(sizeOf(this) - 1).getKey();
    }

    public int size() {
        return sizeOf(this);
    }

    public boolean isEmpty() {
        if (root.getValue() == null) {
            return true;
        }
        return false;
    }

    public boolean containsKey(Object key) {
        try {
            Treap<K, V> tmp = (Treap<K, V>) this.clone();
            while (tmp.root.getKey() != null) {
                int cmp = tmp.root.getKey().compareTo((K) key);
                if (cmp < 0) {
                    if ((tmp.right == null) || (tmp.right.root.getValue() == null)) {
                        return false;
                    }
                    tmp = tmp.right;
                } else {
                    if (cmp > 0) {
                        if ((tmp.left == null) || (tmp.left.root.getValue() == null)) {
                            return false;
                        }
                        tmp = tmp.left;
                    } else {
                        return true;
                    }
                }
            }
            return false;
        } catch (CloneNotSupportedException ex) {
            return false;
        }
    }

    public boolean containsValue(Object value) {
        if (value == this.root.getValue()) {
            return true;
        }
        if ((this.left != null) && (this.left.root.getValue() != null)) {
            if (this.left.containsValue(value)) {
                return true;
            }
        }
        if ((this.right != null) && (this.right.root.getValue() != null)) {
            if (this.right.containsValue(value)) {
                return true;
            }
        }
        return false;
    }

    public V get(Object key) {
        V cmnValue = null;
        if (key == this.root.getKey()) {
            cmnValue = this.root.getValue();
        } else {
            int compare = this.root.getKey().compareTo((K) key);
            if (compare < 0 && this.right != null && this.right.root.getValue()!= null) {
                cmnValue = this.right.get(key);
            } else {
                if (this.left != null && this.left.root.getValue()!= null) {
                    cmnValue = this.left.get(key);
                }
            }
        }
        return cmnValue;
    }

    public V put(K key, V value) {
        V tmp = this.get(key);
        if (tmp != null) {
            this.add(key, value, new Random().nextInt(Integer.MAX_VALUE));
            return tmp;
        } else {
            this.add(key, value, new Random().nextInt(Integer.MAX_VALUE));
            return null;
        }
    }

    public V put(K key, V value, int priority) {
        V tmp = this.get(key);
        if (tmp != null) {
            this.add(key, value, priority);
            return tmp;
        } else {
            this.add(key, value, priority);
            return null;
        }
    }

    // remove node
    public V remove(Object key) {
        if (!containsKey(key))
        {
            return null;
        }
        Treap<K, V> l = new Treap<K, V>(null, null, 0, null, null);
        if ((this.size() == 1) && (this.containsKey(key))) {
            this.update(l);
            return this.root.getValue();
        } else if (this.size() == 0) {
            return null;
        }
        V value = this.get(key);
        Treap<K, V> r = new Treap<K, V>(null, null, 0, null, null);
        Treap<K, V> m = new Treap<K, V>(null, null, 0, null, null);
        Treap<K, V> l2 = new Treap<K, V>(null, null, 0, null, null);
        this.split((K)key, l, r);
        Treap<K, V> l1 = l;
        if (l.size() > 1) {
            if (l.root.getKey().equals(key))
                l2 = l.left;
            else {
                while (!l1.right.root.getKey().equals(key)) {
                    l1 = l1.right;
                }
                l.split(l1.root.getKey(), l2, m);
            }
        }
        this.update(merge(l2, r));
        return value;
    }

    public void putAll(Map<? extends K, ? extends V> m) {
        return;
    }

    public void clear() {
        this.update(null, null, 0, null, null, null);
    }

    public Set<K> keySet() {
        Set<K> keySet = new HashSet<K>();
        keySet.add(this.root.getKey());
        if ((this.left != null) && (this.left.root.getValue() != null)) {
            keySet.addAll(this.left.keySet());
        }
        if ((this.right != null) && (this.right.root.getValue() != null)) {
            keySet.addAll(this.right.keySet());
        }
        return keySet;
    }

    public Collection<V> values() {
        Collection<V> values = new ArrayList<V>();
        values.add(this.root.getValue());
        if ((this.left != null) && (this.left.root.getValue() != null)) {
            values.addAll(this.left.values());
        }
        if ((this.right != null) && (this.right.root.getValue() != null)) {
            values.addAll(this.right.values());
        }
        return values;
    }

    public Set<Entry<K, V>> entrySet() {
        return null;
    }
}