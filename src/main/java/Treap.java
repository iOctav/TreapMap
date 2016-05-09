import com.sun.istack.internal.NotNull;
import java.util.Random;


public class Treap<T extends Comparable> implements Cloneable {
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

    public static int sizeOf(Treap treap) {
        return treap == null || treap.root.value == null ? 0 : treap.size;
    }

    public T kthNode(int K)
    {
        Treap cur = this;
        while (cur != null || cur.root.value != null)
        {
            int sizeLeft = sizeOf(cur.left);

            if (sizeLeft == K)
                return (T)cur.root.value;

            cur = sizeLeft > K ? cur.left : cur.right;
            if (sizeLeft < K)
                K -= sizeLeft + 1;
        }
        return null;
    }


}