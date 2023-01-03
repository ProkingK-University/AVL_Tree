@SuppressWarnings("unchecked")
public class ThreadedAvlTree<T extends Comparable<T>>
{
    public Node<T> root;

    public ThreadedAvlTree()
    {
        this.root = null;
    }

    int getHeight(Node<T> N)
    {
        if (N == null)
        {
            return -1;
        }

        return N.height;
    }

    static Node getLeftMost(Node node)
    {
        while (node != null && node.left != null)
        {
            node = node.left;
        }

        return node;
    }

    void print(Node<T> node)
    {
        if (node != null)
        {
            Node<T> cur = getLeftMost(node);
    
            while (cur != null)
            {
                System.out.print(" " + cur.data + " ");
    
                if (cur.rightThread == true)
                {
                    cur = cur.right;
                }
                else
                {
                    cur = getLeftMost(cur.right);
                }
            }
        }
    }

    public void convertAVLtoThreaded(Node<T> node)
    {
        convertToThreaded(node);
    }

    private Node<T> convertToThreaded(Node<T> node)
    {
        if (node == null)
        {
            return null;
        }
        else if (node.left == null && node.right == null)
        {
            return node;
        }
        else
        {
            Node<T> leftLast = convertToThreaded(node.left);
    
            if (leftLast != null)
            {
                leftLast.right = node;
                leftLast.rightThread = true;
            }
    
            Node<T> rightLast = convertToThreaded(node.right);
    
            return rightLast != null ? rightLast : node;
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
            Node<T> currPtr = node;

            while (true)
            {
                if (data.compareTo(currPtr.data) < 0)
                {
                    if (currPtr.left == null)
                    {
                        currPtr.left = new Node<T>(data);
                        currPtr.left.right = currPtr;
                        currPtr.rightThread = true;
                        break;
                    }
                    else
                    {
                        currPtr = currPtr.left;
                    }
                }
                else
                {
                    if (currPtr.right == null)
                    {
                        currPtr.right = new Node<T>(data);
                        break;
                    }
                    else if (currPtr.rightThread)
                    {
                        currPtr.right = new Node<T>(data);
                        currPtr.right.right = currPtr.right;
                        currPtr.rightThread = false;
                        break;
                    }
                    else
                    {
                        currPtr = currPtr.right;
                    }
                }
            }
        }

        updateHeight(node);
        rotateNodes(node);

        return convertToThreaded(node);
    }

    /**
     * Delete the given element \texttt{data} from the tree.  Re-balance the tree after deletion.
     * If the data is not in the tree, return the given node / root.
     */
    Node<T> removeNode(Node<T> root, T data)
    {
        return null;
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
}
