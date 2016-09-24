package ru.spbstu.student.treap;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;

import java.io.Serializable;
import java.util.*;


public class TreapMap<K extends Comparable<K>, V> implements Cloneable, SortedMap<K, V>, Serializable {
    private TreapNode<K,V> root;
    private int size;
    private Comparator<? super K> comparator;

    public TreapMap<K, V> left;
    public TreapMap<K, V> right;

    public TreapMap(K keyRoot, V valueRoot, int priorityRoot,
                 TreapMap<K, V> leftRoot, TreapMap<K, V> rightRoot) {
        this.root = new TreapNode<K, V>(keyRoot, valueRoot, priorityRoot);
        this.left = leftRoot;
        this.right = rightRoot;
        this.size = 1;
        this.comparator = null;
    }

    public TreapMap(K keyRoot, V valueRoot, int priorityRoot,
                 TreapMap<K,V> leftRoot, TreapMap<K,V> rightRoot, Comparator<? super K> comparator) {
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
                        TreapMap<K, V> left, TreapMap<K, V> right, Comparator<? super K> comparator) {
        this.root.value = value;
        this.root.priority = priority;
        this.root.key = key;
        this.left = left;
        this.right = right;
        this.comparator = comparator;
        this.recalc();
    }

    private TreapNode< K,V> getRoot() {
        return root;
    }

    // only for internals methods, refresh values without created new object
    private void update(TreapMap<K, V> tree) {
        this.update(tree.root.key, tree.root.value, tree.root.priority,
                tree.left, tree.right, tree.comparator);
    }



    // !!!Values Left Treap >= Right Treap!!!
    private TreapMap<K, V> merge(TreapMap<K, V> L, TreapMap<K, V> R) {
        if (R == null || R.root.key == null) return L;
        if (L == null || L.root.key == null) return R;

        TreapMap<K, V> ans;
        if (L.root.priority > R.root.priority) {
            TreapMap<K, V> newR = merge(L.right, R);
            ans = new TreapMap<K, V>(L.root.key, L.root.value, L.root.priority,
                    L.left, newR);
        } else {
            TreapMap<K, V> newL = merge(L, R.left);
            ans = new TreapMap<K, V>(R.root.key, R.root.value, R.root.priority,
                    newL, R.right);
        }
        ans.recalc();
        return ans;
    }

    // !!! need L and R Treap with root.value = null !!!
    private void split(K key, @NotNull TreapMap<K, V> L, @NotNull TreapMap<K, V> R) {
        TreapMap<K, V> newTree = new TreapMap<K, V>(null, null, 0, null, null);
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
        TreapMap<K, V> l = new TreapMap<K, V>(null, null, 0, null, null);
        if (this.root.getValue() == null) {
            this.update(key, value, priority, null, null, null);
            return;
        }
        TreapMap<K, V> r = new TreapMap<K, V>(null, null, 0, null, null);
        this.split(key, l, r);
        TreapMap<K, V> m = new TreapMap<K, V>(key, value, priority,
                null, null);
        this.update(merge(merge(l, m), r));
    }

    private void recalc() {
        this.size = sizeOf(this.left) + sizeOf(this.right) + 1;
    }

    private static int sizeOf(TreapMap treap) {
        return treap == null || treap.root.value == null ? 0 : treap.size;
    }

    private TreapNode<K,V> kthNode(int K) {
        TreapMap<K, V> cur = this;
        while (cur != null || cur.root.key != null) {
            int sizeLeft = sizeOf(cur.left);

            if (sizeLeft == K)
                return cur.getRoot();

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

        private TreapNode(K key, V value, int priority) {
            this.key = key;
            this.value = value;
            this.priority = priority;
        }

        @Override
        public boolean equals(Object node) {
            if (!node.getClass().equals(this.getClass())) {
                return false;
            }
            @SuppressWarnings("unchecked")
            TreapNode<K, V> cmn = (TreapNode<K, V>) node;
            return (this.value.equals(cmn.value) && (this.priority == cmn.priority)
                    && this.key.equals(cmn.key));
        }

        @Nullable
        private K getKey() {
            return this.key;
        }

        @Nullable
        private V getValue() {
            return this.value;
        }

        private int getPriority() {
            return this.priority;
        }

        @Override
        public int hashCode() {
            int hash = 0;
            hash += this.key.hashCode();
            hash += this.value.hashCode();
            Integer prior = new Integer(priority);
            hash += prior.hashCode();
            return hash;
        }

        @Override
        public String toString() {
            return ("key: " + this.key + " | value: " + this.value + " | priority: " + this.priority);

        }
    }

    public Comparator<? super K> comparator() {
        return this.comparator;
    }

    public SortedMap<K, V> subMap(K fromKey, K toKey) {
        TreapMap<K, V> l = new TreapMap<K, V>(null, null, 0, null, null);
        TreapMap<K, V> r = new TreapMap<K, V>(null, null, 0, null, null);
        TreapMap<K, V> l2 = new TreapMap<K, V>(null, null, 0, null, null);
        if (fromKey.equals(toKey)) {
            this.split(fromKey, l, r);
            TreapMap<K, V> l1 = l;
            TreapMap<K, V> r1 = l;
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
        TreapMap<K, V> l = new TreapMap<K, V>(null, null, 0, null, null);
        TreapMap<K, V> r = new TreapMap<K, V>(null, null, 0, null, null);
        TreapMap<K, V> l2 = new TreapMap<K, V>(null, null, 0, null, null);
        this.split(toKey, l, r);
        TreapMap<K, V> l1 = l;
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
        TreapMap<K, V> l = new TreapMap<K, V>(null, null, 0, null, null);
        TreapMap<K, V> r = new TreapMap<K, V>(null, null, 0, null, null);
        TreapMap<K, V> l2 = new TreapMap<K, V>(null, null, 0, null, null);
        TreapMap<K, V> r1 = new TreapMap<K, V>(null, null, 0, null, null);
        this.split(fromKey, l, r);
        TreapMap<K, V> l1 = l;
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

    public List<V> firstValues() {
        if (kthNode(0) == null)
            throw new NoSuchElementException("empty map");
        else {
            List<V> listValues = new ArrayList<V>();
            listValues.add(kthNode(0).getValue());
            for (int i = 1; kthNode(i).getValue().equals(listValues.get(0)); i++) {
                listValues.add(i, kthNode(i).getValue());
            }
            return listValues;
        }
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
        return (root.getValue() == null);
    }

    public boolean containsKey(Object key) {
        TreapMap<K,V> tmp = this;
        while (tmp.root.getKey() != null) {
            @SuppressWarnings("unchecked")
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
            @SuppressWarnings("unchecked")
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
        V tmp;
        if (!containsKey(key)) {
            tmp = null;
        } else {
            tmp = this.get(key);
        }
        if (tmp != null) {
            this.add(key, value, new Random().nextInt(Integer.MAX_VALUE));
            return tmp;
        } else {
            this.add(key, value, new Random().nextInt(Integer.MAX_VALUE));
            return null;
        }
    }

    //add new key+value and return value, if key was initialization
    public V put(K key, V value, int priority) {
        V tmp = null;
        if (this.size() > 1) {
            tmp = this.get(key);
        }
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
        TreapMap<K, V> l = new TreapMap<K, V>(null, null, 0, null, null);
        if ((this.size() == 1) && (this.containsKey(key))) {
            this.update(l);
            return this.root.getValue();
        } else if (this.size() == 0) {
            return null;
        }
        V value = this.get(key);
        TreapMap<K, V> r = new TreapMap<K, V>(null, null, 0, null, null);
        TreapMap<K, V> m = new TreapMap<K, V>(null, null, 0, null, null);
        TreapMap<K, V> l2 = new TreapMap<K, V>(null, null, 0, null, null);
        @SuppressWarnings("unchecked")
        K tmpKey = (K) key;
        this.split(tmpKey, l, r);
        TreapMap<K, V> l1 = l;
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
        Iterator entries = m.entrySet().iterator();
        Entry thisEntry = (Entry) entries.next();
        for (; entries.hasNext(); thisEntry = (Entry) entries.next()) {
            @SuppressWarnings("unchecked")
            K cmnKey = (K) thisEntry.getKey();
            @SuppressWarnings("unchecked")
            V cmnValue = (V) thisEntry.getValue();
            this.put(cmnKey, cmnValue);
        }
        @SuppressWarnings("unchecked")
        K cmnKey = (K) thisEntry.getKey();
        @SuppressWarnings("unchecked")
        V cmnValue = (V) thisEntry.getValue();
        this.put(cmnKey, cmnValue);
    }

    public void clear() {
        this.update(null, null, 0, null, null, null);
    }

    public Set<K> keySet() {
        Set<K> keySet = new TreeSet<K>();
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
        Set<Map.Entry<K,V>> entries = new HashSet<Entry<K, V>>();
        Entry<K,V> entry = new AbstractMap.SimpleEntry<K, V>(this.root.getKey(),this.root.getValue());
        entries.add(entry);
        if ((this.left != null) && (this.left.root.getValue() != null)) {
            entries.addAll(this.left.entrySet());
        }
        if ((this.right != null) && (this.right.root.getValue() != null)) {
            entries.addAll(this.right.entrySet());
        }
        return entries;
    }

    @Override
    public boolean equals(Object node) {
        if (!node.getClass().equals(this.getClass())) {
            return false;
        }
        @SuppressWarnings("unchecked")
        TreapMap<K, V> cmn = (TreapMap<K, V>) node;
        return ((this.root.getValue().equals(cmn.root.getValue())) && (this.root.getPriority() == cmn.root.getPriority())
                && (this.root.getKey().equals(cmn.root.getKey())) && (this.left.equals(cmn.left)) && (this.right.equals(cmn.right))
                && (this.comparator().equals(cmn.comparator())));
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += this.root.hashCode();
        hash += this.left.hashCode();
        hash += this.right.hashCode();
        return hash;
    }

    @Override
    public String toString() {
        if (root.getValue() != null) {
            return ("root: " + this.root.toString() + "\nleft: " + this.left.toString() + "\nright: " + this.right.toString());
        }
        return " Empty";
    }
}