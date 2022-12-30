public class AvlTree<T extends Comparable<T>>
{
    public Node<T> root;

    public AvlTree()
    {
        this.root = null;
    }

    int getHeight(Node<T> N)
    {
        if (N == null)
        {
            return 0;
        }

        return N.height;
    }

    void print(Node<T> node)
    {
        if (node != null)
        {
            print(node.left);
    
            System.out.print(node.data + " ");
    
            print(node.right);
        }
    }

    Node<T> insert(Node<T> node, T data)
    {
        if (node == null)
        {
            node = new Node<T>(data);

            return node;
        }
        else
        {
            if (data.compareTo(node.data) < 0)
            {
                node.left = insert(node.left, data);
            }
            else if (data.compareTo(node.data) > 0)
            {
                node.right = insert(node.right, data);
            }

            return node;
        }
    }


    /**
     * Remove / Delete the node based on the given data
     * Return the node / root if the data do not exist
     */

    Node<T> removeNode(Node<T> node, T data)
    {
        if (node == null)
        {
            return null;
        }
        else if (data.compareTo(node.data) < 0)
        {
            node.left = removeNode(node.left, data);
        }
        else if (data.compareTo(node.data) > 0)
        {
            node.right = removeNode(node.right, data);
        }
        else
        {
            if (node.left == null)
            {
                return node.right;
            }
            else if (node.right == null)
            {
                return node.left;
            }
            else
            {
                Node<T> minNode = findMin(node.right);
                node.data = minNode.data;
                node.right = removeNode(node.right, minNode.data);
            }
        }

        return node;
    }

    private Node<T> findMin(Node<T> node)
    {
        while (node.left != null)
        {
            node = node.left;
        }
        
        return node;
    }
}
