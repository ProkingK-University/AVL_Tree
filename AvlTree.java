public class AvlTree<T extends Comparable<T>>
{
    public Node<T> root;

    public AvlTree()
    {
        this.root = null;
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
            else
            {
                return node;
            }
        }

        updateHeight(node);

        return rotateNodes(node);
    }

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

        updateHeight(node);

        return rotateNodes(node);
    }

    private Node<T> findMin(Node<T> node)
    {
        while (node.left != null)
        {
            node = node.left;
        }

        return node;
    }

    private Node<T> rotateNodes(Node<T> node)
    {
        int balanceFactor = getBalanceFactor(node);

        if (balanceFactor > 1)
        {
            if (getBalanceFactor(node.left) < 0)
            {
                node.left = rotateLeft(node.left);
            }
            
            return rotateRight(node);
        }
        else if (balanceFactor < -1)
        {
            if (getBalanceFactor(node.right) > 0)
            {
                node.right = rotateRight(node.right);
            }
            
            return rotateLeft(node);
        }

        return node;
    }

    private int getBalanceFactor(Node<T> node)
    {
        if (node == null)
        {
            return 0;
        }
        else
        {
            return getHeight(node.left) - getHeight(node.right);
        }
    }

    private Node<T> rotateRight(Node<T> node)
    {
        Node<T> leftNode = node.left;
        Node<T> rightNode = leftNode.right;

        leftNode.right = node;
        node.left = rightNode;

        updateHeight(node);
        updateHeight(leftNode);

        return leftNode;
    }

    private Node<T> rotateLeft(Node<T> node)
    {
        Node<T> rightNode = node.right;
        Node<T> leftNode = rightNode.left;

        rightNode.left = node;
        node.right = leftNode;

        updateHeight(node);
        updateHeight(rightNode);

        return rightNode;
    }

    private void updateHeight(Node<T> node)
    {
        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
    }

    int getHeight(Node<T> N)
    {
        if (N == null)
        {
            return -1;
        }

        return N.height;
    }
}
