import java.util.Comparator;

public class TreapNode<T extends Comparable>
{
    TreapNode left;
    TreapNode right;
    TreapNode parent;
    private T value;
    int size;
    int priority;
    Comparator<T> comparator;

    public TreapNode(T value, int priority)
    {
        this.value = value;
        this.priority = priority;
    }

    public TreapNode(T value, int priority, Comparator<T> comparator)
    {
        this.value = value;
        this.priority = priority;
        this.comparator = comparator;
    }

    public void displayNode()
    {
        System.out.println("value: " + this.value + " || priority: " + this.priority);
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }
}