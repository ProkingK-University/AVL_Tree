public class Node<T extends Comparable<T>>
{
    public T data;
    public int height;

    public Node<T> left;
    public Node<T> right;

    boolean leftThread;
    boolean rightThread;

    public Node(T item)
    {
        data = item;
        left = right = null;
    }
}