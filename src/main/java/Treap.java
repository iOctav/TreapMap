import java.util.*;
import java.util.concurrent.CopyOnWriteArraySet;


public class Treap<T extends Comparable> implements SortedMap, Cloneable
{
    public TreapNode<T> root;

    public Treap()
    {
        root = null;
        root.size = 0;
    }

    public Treap(TreapNode<T> root)
    {
        this.root = root;
        root.size = 0;
    }

    public Treap(T rootValue, int rootPriority)
    {
        root = new TreapNode<T>(rootValue, rootPriority);
        root.size = 0;
    }

    public void add(T element, int priority)
    {
        TreapNode<T> newNode = new TreapNode<T>(element, priority);

        if (root == null)
        {
            root = newNode;

        } else {
            add(newNode, root);
        }
        this.root.size++;
    }

    public int SizeOf(TreapNode<T> node){
        return node == null ? 0 : node.size;
    }

    private void add(TreapNode<T> node, TreapNode<T> root)
    {
        if (node.getValue().compareTo(root.getValue()) < 0) {
            if (root.left == null) {
                root.left = node;
                node.parent = root;
            } else {
                add(node, root.left);
            }
            if (root.priority > root.left.priority)
            {
                rotateRight(root);
            }
        } else
        {
            if (root.right == null)
            {
                root.right = node;
                node.parent = root;
            } else
            {
                add(node, root.right);
            }
            if (root.priority > root.right.priority)
            {
                rotateLeft(root);
            }
        }
    }

    public T KthElement(int K)
    {
        TreapNode<T> cur = this.root;
        while (cur != null)
        {
            int sizeLeft = SizeOf(cur.left);
            if(sizeLeft == K)
            {
                return cur.getValue();
            }

            cur = sizeLeft > K ? cur.left :cur.right;
            if (sizeLeft < K)
            {
                K -= sizeLeft + 1;
            }
        }
        return null;
    }

    private int SearchKElement (T element)
    {
        for (int i = 0; i < SizeOf(this.root); i++)
        {
            if (element == KthElement(i))
            {
                return i;
            }
        }
        return 0;
    }

    private void rotateLeft(TreapNode<T> node)
    {
        if (node.right != null)
        {
            TreapNode<T> A = node;
            TreapNode<T> B = node.right;

            TreapNode<T> tmp = B.left;
            B.left = A;
            TreapNode<T> oldParent = A.parent;
            A.parent = B;
            A.right = tmp;
            if (tmp != null)
            {
                tmp.parent = A;
            }
            B.parent = oldParent;
            if (oldParent == null)
            {
                root = B;
            } else
            {
                if (oldParent.left == A)
                {
                    oldParent.left = B;
                } else
                {
                    oldParent.right = B;
                }
            }
        }

    }

    private void rotateRight(TreapNode<T> node)
    {
        if (node.left != null)
        {
            TreapNode<T> A = node.left;
            TreapNode<T> B = node;
            TreapNode<T> tmp = A.right;
            A.right = B;
            TreapNode<T> oldParent = B.parent;
            B.parent = A;
            B.left = tmp;
            if (tmp != null)
            {
                tmp.parent = B;
            }

            A.parent = oldParent;
            if (oldParent == null)
            {
                root = A;
            }
            else
            {
                if (B == oldParent.left)
                {
                    oldParent.left = A;
                }
                else
                {
                    oldParent.right = A;
                }
            }
        }
    }

    private TreapNode<T> getNode(T value, TreapNode<T> root)
    {
        if (root == null)
        {
            return null;
        }

        int compare = value.compareTo(root.getValue());
        if (compare == 0)
        {
            return root;
        } else if (compare < 0)
        {
            return getNode(value, root.left);
        } else
        {
            return getNode(value, root.right);
        }
    }

    private boolean contains(T element)
    {
        TreapNode<T> node = getNode(element, root);
        return node != null;
    }

    public void display()
    {
        queueNodes(root);
    }

    public void queueNodes(TreapNode<T> locRoot)
    {
        Queue<TreapNode> q = new LinkedList<TreapNode>();
        TreapNode<T> x = locRoot;
        TreapNode<T> y = null;
        if (x == null)
        {
            return;
        } else
        q.add(x);
        while (!q.isEmpty())
        {
            y = q.poll();
            if (y.left != null)
            {
                q.add(y.left);
            }
            if (y.right != null)
            {
                q.add(y.right);
            }
            y.displayNode();
        }
    }

    // Если компаратор задан, вы передаем его, если нет - то null
    public Comparator comparator() {
        return root.comparator != null ? root.comparator : null;
    }

    // возвращает дерево от fromkey до toKey
    public SortedMap subMap(Object fromKey, Object toKey) {
        if (!contains((T) toKey) || !contains((T) fromKey)) {
            return null;
        }
        Treap<T> tmp = new Treap<T>();
        Random rand = new Random();
        for (int i = SearchKElement((T) fromKey); i < SearchKElement((T) toKey); i++)
        {
            tmp.add(KthElement(i), rand.nextInt(Integer.SIZE - 1));
        }
        return tmp;
    }

    // возвращает дерево от firstKey до toKey
    public SortedMap headMap(Object toKey) {
        if (!contains((T) toKey)) {
            return null;
        }
        Treap<T> tmp = new Treap<T>();
        Random rand = new Random();
        for (int i = 0; i < SearchKElement((T) toKey); i++)
        {
            tmp.add(KthElement(i), rand.nextInt(Integer.SIZE - 1));
        }
        return tmp;
    }

    // возвращает дерево от fromKey до lastKey
    public SortedMap tailMap(Object fromKey) {
        if (!contains((T) fromKey)) {
            return null;
        }
        Treap<T> tmp = new Treap<T>();
        Random rand = new Random();
        for (int i = SearchKElement((T) fromKey); i < SizeOf(this.root); i++)
        {
            tmp.add(KthElement(i), rand.nextInt(Integer.SIZE - 1));
        }
        return tmp;
    }

    // возвращает 0-ое значение(ключ)
    public T firstKey() {
        return KthElement(0);
    }

    // возвращает последнее значение(ключ)
    public T lastKey() {
        return KthElement(SizeOf(this.root) - 1);
    }

    // возвращает размер дерева/поддереве из данного ключа
    public int size() {
        return SizeOf(root);
    }

    // при отсутствие ключа, возвращает true, при наличие ключа - false
    public boolean isEmpty() {
        if (root == null)
        {
            return true;
        } else {
            return false;
        }
    }

    // проверка наличия элемента по ключу
    public boolean containsKey(Object key) {
        return contains((T)key);
    }

    // проверка наличия элемента по значению
    public boolean containsValue(Object value) {
        return contains((T)value);
    }

    // получение узла по значению(ключу)
    public Object get(Object key) {
        if (!containsKey(key)) {
            return null;
        }
        return getNode((T)key, root);
    }

    // добавление элемента
    public Object put(Object key, Object value)
    {
        Random rand = new Random();
        add((T)value, rand.nextInt(Integer.SIZE - 1));
        return null;
    }

    public void putPri(Object key, int priority)
    {
        add((T)key, priority);
        return;
    }

    // удаление элемента по ключу(значению)
    public Object remove(Object key) {
        TreapNode<T> rmv = getNode((T) key, this.root);
        TreapNode<T> left = rmv.left;
        TreapNode<T> right = rmv.right;
        if (rmv.parent.left.getValue() == rmv.getValue())
        {
            rmv.parent.left = null;
        } else
        {
            rmv.parent.right = null;
        }
        add(left, this.root);
        add(right, this.root);
        return null;
    }

    // добавление всех узлов из Map m
    public void putAll(Map m) {
        Treap<T> das = (Treap<T>) m;
        add(das.root, this.root);
        return;
    }

    // удаление всех узлов
    public void clear() {
        for (int i = 0; i < SizeOf(this.root); i++)
        {
            remove(KthElement(i));
        }
        return;
    }

    // возвращает коллекцию ключей
    public Set keySet() {
        Set<T> ers = new CopyOnWriteArraySet<T>();
        for (int i = 0; i < SizeOf(this.root); i++)
        {
            ers.add(KthElement(i));
        }
        return ers;
    }

    // возвращает коллекцию значений
    public Collection values() {
        Collection<T> ers = new CopyOnWriteArraySet<T>();
        for (int i = 0; i < SizeOf(this.root); i++)
        {
            ers.add(KthElement(i));
        }
        return ers;
    }

    public Set<Entry> entrySet() {
        return null;
    }
}