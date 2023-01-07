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

    @SuppressWarnings("rawtypes")
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
                System.out.print(cur.data + " ");
    
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

        inOrderTraversal(node);

        return rotateNodes(node);
    }

    Node<T> removeNode(Node<T> node, T data)
    {
        Node<T> parent = null;
        Node<T> current = node;

        // Find the node to be deleted
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

        // Node not found
        if (current == null)
        {
            return node;
        }
        // Case 1: Node to be deleted has no children
        else if (current.left == null && current.right == null)
        {
            if (parent == null)
            {
                // Node to be deleted is the root node
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
        // Case 2: Node to be deleted has one child
        else if (current.left == null || current.right == null)
        {
            Node<T> child = (current.left != null) ? current.left : current.right;
            if (parent == null)
            {
                // Node to be deleted is the root node
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
        // Case 3: Node to be deleted has two children
        else
        {
            // Find the inorder successor of the node to be deleted
            Node<T> inorderSuccessor = current.right;

            while (inorderSuccessor.left != null)
            {
                inorderSuccessor = inorderSuccessor.left;
            }

            // Swap the data of the inorder successor with the data of the node to be deleted
            current.data = inorderSuccessor.data;

            // Delete the inorder successor
            removeNode(node, inorderSuccessor.data);
        }

        inOrderTraversal(node);

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

    public int height(Node<T> node) {
        if (node == null) {
            return 0;
        }
        int height = -1;
        Node<T> current = node;
        while (current != null) {
            height++;
            current = current.right;
        }
        return height;
    }
    
    public void inOrderTraversal(Node<T> node)
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
}