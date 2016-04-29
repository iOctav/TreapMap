import java.util.*;

public class Treap<T extends Comparable>
{
    private TreapNode<T> root;

    public Treap()
    {
        root = null;
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
    }

    private void add(TreapNode<T> node, TreapNode<T> root)
    {
        if (node.value.compareTo(root.value) < 0) {
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

        int compare = value.compareTo(root.value);
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

    public boolean contains(T element)
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
}