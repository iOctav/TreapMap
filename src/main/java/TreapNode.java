public class TreapNode<T extends Comparable>
{
    private T value;
    private int priority;

    public TreapNode(T value, int priority)
    {
        this.value = value;
        this.priority = priority;
    }

    public void displayNode()
    {
        System.out.println(this.toString());
    }

    public void setValue(T value) {
        this.value = value;
    }

    public T getValue() {
        return this.value;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public int getPriority() {
        return this.priority;
    }

    @Override
    public boolean equals(Object node) {
        if (this.value.equals(((TreapNode<T>) node).getValue()) && this.priority == ((TreapNode<T>) node).getPriority()) {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return new String("value: " + this.value + " || priority: " + this.priority);

    }
}