@SuppressWarnings("unchecked")
public class ThreadedAvlTree<T extends Comparable<T>>
{
    public Node<T> root;

    public ThreadedAvlTree()
    {
        this.root = null;
    }

    public int getHeight(Node<T> node)
    {
        if (node == null)
        {
            return -1;
        }

        return node.height;
    }

    void print(Node<T> node)
    {
        if (node != null)
        {
            Node<T> currPtr = getLeftMost(node);
            
            while (currPtr != null)
            {
                System.out.print(currPtr.data + " ");
                
                if (currPtr.rightThread == true)
                {
                    currPtr = currPtr.right;
                }
                else
                {
                    currPtr = getLeftMost(currPtr.right);
                }
            }
        }
    }

    public void convertAVLtoThreaded(Node<T> node)
    {
        root = node;
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
            Node<T> prevPtr = null;
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
                        prevPtr = currPtr;
                        currPtr = currPtr.left;
                    }
                }
                else if (data.compareTo(currPtr.data) > 0)
                {
                    if (currPtr.right == null)
                    {
                        currPtr.right = new Node<T>(data);
                        break;
                    }
                    else if (currPtr.rightThread)
                    {
                        currPtr.right = new Node<T>(data);
                        currPtr.right.right = prevPtr;
                        currPtr.rightThread = false;
                        currPtr.right.rightThread = true;
                        break;
                    }
                    else
                    {
                        prevPtr = currPtr;
                        currPtr = currPtr.right;
                    }
                }
                else
                {
                    return node;
                }
            }
        }

        updateAllHeights(node);

        return rotateNodes(node);
    }

    Node<T> removeNode(Node<T> node, T data)
    {
        Node<T> parent = null;
        Node<T> current = node;

        while (current != null && current.data != data)
        {
            parent = current;

            if (data.compareTo(current.data) < 0)
            {
                current = current.left;
            }
            else
            {
                current = current.right;
            }
        }

        if (current == null)
        {
            return node;
        }
        else if (current.left == null && current.right == null)
        {
            if (parent == null)
            {
                root = null;
            }
            else
            {
                if (parent.left == current)
                {
                    parent.left = null;
                }
                else
                {
                    parent.right = null;
                }
            }
        }
        else if (current.left == null || current.right == null)
        {
            Node<T> child = (current.left != null) ? current.left : current.right;

            if (parent == null)
            {
                root = child;
            }
            else
            {
                if (parent.left == current)
                {
                    parent.left = child;
                }
                else
                {
                    parent.right = child;
                }
            }
        }
        else
        {
            Node<T> inorderSuccessor = current.right;

            while (inorderSuccessor.left != null)
            {
                inorderSuccessor = inorderSuccessor.left;
            }

            current.data = inorderSuccessor.data;
            removeNode(node, inorderSuccessor.data);
        }

        updateAllHeights(node);

        return rotateNodes(node);
    }
    
    private Node<T> rotateNodes(Node<T> node)
    {
        convertToNormalTree(node);

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

        convertToThreaded(node);

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

        rightNode = node;
        node.left = rightNode;

        updateHeight(node);
        updateHeight(leftNode);

        return leftNode;
    }

    private Node<T> rotateLeft(Node<T> node)
    {
        Node<T> rightNode = node.right;
        Node<T> leftNode = rightNode.left;

        leftNode = node;
        node.right = leftNode;

        updateHeight(node);
        updateHeight(rightNode);

        return rightNode;
    }

    private void updateHeight(Node<T> node)
    {
        if (node.rightThread == true)
        {
            node.height = 1 + Math.max(getHeight(node.left), -1);
        }
        else
        {
            node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
        }
    }

    public void convertToNormalTree(Node<T> node)
    {
        if (node == null)
        {
            return;
        }
        else if (node.rightThread == true)
        {
            node.right = null;
            node.rightThread = false;
        }

        convertToNormalTree(node.left);
        convertToNormalTree(node.right);
    }

    public int height(Node<T> node)
    {
        if (node == null)
        {
            return -1;
        }
        else
        {
            if (node.rightThread == true)
            {
                return 1 + Math.max(height(node.left), height(null));
            }
            else
            {
                return 1 + Math.max(height(node.left), height(node.right));
            }
        }
    }
    
    public void updateAllHeights(Node<T> node)
    {
        if (node != null)
        {
            Node<T> currPtr = getLeftMost(node);
    
            while (currPtr != null)
            {
                currPtr.height = height(node);
    
                if (currPtr.rightThread == true)
                {
                    currPtr = currPtr.right;
                }
                else
                {
                    currPtr = getLeftMost(currPtr.right);
                }
            }
        }
    }

    @SuppressWarnings("rawtypes")
    private static Node getLeftMost(Node node)
    {
        while (node != null && node.left != null)
        {
            node = node.left;
        }

        return node;
    }
}