import java.util.Comparator;

public class TreapNode<T extends Comparable>
{
    public T value;
    public int priority;

    public TreapNode(T value, int priority)
    {
        this.value = value;
        this.priority = priority;
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