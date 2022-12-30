import javax.lang.model.util.ElementScanner14;

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
                return root;
            }
        }

        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));

        int balanceFactor = getBalanceFactor(node);

        if (balanceFactor > 1 && data.compareTo(node.left.data) < 0)
        {
            return rightRotate(node);
        }
        else if (balanceFactor < -1 && data.compareTo(node.right.data) > 0)
        {
            return leftRotate(node);
        }
        else if (balanceFactor > 1 && data.compareTo(node.left.data) > 0)
        {
            node.left = leftRotate(node.left);

            return rightRotate(node);
        }
        else if (balanceFactor < -1 && data.compareTo(node.right.data) < 0)
        {
            node.right = rightRotate(node.right);

            return leftRotate(node);
        }

        return node;
    }


    /**
     * Remove / Delete the node based on the given data
     * Return the node / root if the data do not exist
     */

    Node<T> removeNode(Node<T> node, T data)
    {
        if (node == null)
        {
            return node;
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

        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));

        int balanceFactor = getBalanceFactor(node);

        if (balanceFactor > 1 && getBalanceFactor(node.left) >= 0)
        {
            return rightRotate(node);
        }
        else if (balanceFactor < -1 && getBalanceFactor(node.right) <= 0)
        {
            return leftRotate(node);
        }
        else if (balanceFactor > 1 && getBalanceFactor(node.left) < 0)
        {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
        else if (balanceFactor < -1 && getBalanceFactor(node.right) > 0)
        {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    int getHeight(Node<T> N)
    {
        if (N == null)
        {
            return 0;
        }

        return N.height;
    }

    private Node<T> findMin(Node<T> node)
    {
        while (node.left != null)
        {
            node = node.left;
        }

        return node;
    }

    private int getBalanceFactor(Node<T> node)
    {
        if (node == null)
        {
            return 0;
        }

        return getHeight(node.left) - getHeight(node.right);
    }

    private Node<T> rightRotate(Node<T> node)
    {
        Node<T> leftNode = node.left;
        Node<T> rightNode = leftNode.right;

        leftNode.right = node;
        node.left = rightNode;

        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
        leftNode.height = 1 + Math.max(getHeight(leftNode.left), getHeight(leftNode.right));

        return leftNode;
    }

    private Node<T> leftRotate(Node<T> node)
    {
        Node<T> rightNode = node.right;
        Node<T> leftNode = rightNode.left;

        rightNode.left = node;
        node.right = leftNode;

        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
        rightNode.height = 1 + Math.max(getHeight(rightNode.left), getHeight(rightNode.right));

        return rightNode;
    }   
}
