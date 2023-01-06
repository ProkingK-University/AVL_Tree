public class Main
{
    public static void main(String[] args)
    {
        /*AvlTree<Integer> tree = new AvlTree<>();

        tree.root = tree.insert(tree.root, 4);
        tree.root = tree.insert(tree.root, 2);
        tree.root = tree.insert(tree.root, 6);
        tree.root = tree.insert(tree.root, 1);
        tree.root = tree.insert(tree.root, 3);
        tree.root = tree.insert(tree.root, 5);
        tree.root = tree.insert(tree.root, 7);

        ThreadedAvlTree<Integer> threadedAvlTree = new ThreadedAvlTree<>();
        threadedAvlTree.convertAVLtoThreaded(tree.root);

        threadedAvlTree.print(threadedAvlTree.root);
        System.out.println();

        System.out.println(threadedAvlTree.root.left.left.right.data);
        System.out.println(threadedAvlTree.root.left.right.right.data);
        System.out.println(threadedAvlTree.root.right.left.right.data);
        System.out.println(threadedAvlTree.root.right.right.right);
        
        threadedAvlTree.convertToNormalTree(threadedAvlTree.root);

        System.out.println(threadedAvlTree.root.left.left.data);
        System.out.println(threadedAvlTree.root.left.right.data);
        System.out.println(threadedAvlTree.root.right.left.data);
        System.out.println(threadedAvlTree.root.right.right.data);

        threadedAvlTree.print(threadedAvlTree.root);
        System.out.println();*/

        
        
        
        
        
        
        /*AvlTree<Integer> tree = new AvlTree<>();

        tree.root = tree.insert(tree.root, 8);
        tree.root = tree.insert(tree.root, 4);
        tree.root = tree.insert(tree.root, 12);
        tree.root = tree.insert(tree.root, 2);
        tree.root = tree.insert(tree.root, 6);
        tree.root = tree.insert(tree.root, 10);
        tree.root = tree.insert(tree.root, 14);
        tree.root = tree.insert(tree.root, 1);
        tree.root = tree.insert(tree.root, 3);
        tree.root = tree.insert(tree.root, 5);
        tree.root = tree.insert(tree.root, 7);
        tree.root = tree.insert(tree.root, 9);
        tree.root = tree.insert(tree.root, 11);
        tree.root = tree.insert(tree.root, 13);
        tree.root = tree.insert(tree.root, 15);

        ThreadedAvlTree<Integer> threadedAvlTree = new ThreadedAvlTree<>();
        threadedAvlTree.convertAVLtoThreaded(tree.root);

        System.out.println("Data: " + threadedAvlTree.root.left.left.left.data + " Data of parent: " + threadedAvlTree.root.left.left.left.right.data);
        threadedAvlTree.print(threadedAvlTree.root);*/




        
        
        
        
        
        
        
        
        AvlTree<Integer> tree = new AvlTree<>();

        // Constructing tree given in the above figure
        tree.root = tree.insert(tree.root, 20);
        tree.root = tree.insert(tree.root, 25);
        tree.root = tree.insert(tree.root, 35);
        tree.root = tree.insert(tree.root, 14);

        System.out.println("Inorder traversal" +
                " of constructed tree is : ");
        tree.print(tree.root);
        System.out.println("\nTree Height is: " + tree.getHeight(tree.root));

        tree.root = tree.insert(tree.root, 65);
        tree.root = tree.insert(tree.root, 80);

        System.out.println("Inorder traversal" +
                " of constructed tree is : ");
        tree.print(tree.root);
        System.out.println("\nTree Height is: " + tree.getHeight(tree.root));

        tree.root = tree.insert(tree.root, 82);

        System.out.println("Inorder traversal" +
                " of constructed tree is : ");
        tree.print(tree.root);
        System.out.println("\nTree Height is: " + tree.getHeight(tree.root));

        tree.root = tree.removeNode(tree.root, 80);

        System.out.println("Inorder traversal" +
                " of constructed tree is : ");
        tree.print(tree.root);
        System.out.println("\nTree Height is: " + tree.getHeight(tree.root));

        //        threaded Avl tree

        ThreadedAvlTree<Integer> threadedAvlTree = new ThreadedAvlTree<>();
        threadedAvlTree.convertAVLtoThreaded(tree.root);

        System.out.println("Inorder traversal" +
                " of constructed threaded avl tree is : ");
        threadedAvlTree.print(threadedAvlTree.root);
        System.out.println("\nTree Height is: " + threadedAvlTree.getHeight(threadedAvlTree.root));

        /*System.out.println(threadedAvlTree.root.left.left.right.data);
        System.out.println(threadedAvlTree.root.left.right.data);
        System.out.println(threadedAvlTree.root.right.left.right.data);*/

        threadedAvlTree.root = threadedAvlTree.insert(threadedAvlTree.root, 82);
        threadedAvlTree.root = threadedAvlTree.insert(threadedAvlTree.root, 91);
        threadedAvlTree.root = threadedAvlTree.insert(threadedAvlTree.root, 50);

        System.out.println("Inorder traversal" +
                " of constructed threaded avl tree is : ");
        threadedAvlTree.print(threadedAvlTree.root);
        System.out.println("\nTree Height is: " + threadedAvlTree.getHeight(threadedAvlTree.root));

        /*threadedAvlTree.root = threadedAvlTree.removeNode(threadedAvlTree.root, 91);

        System.out.println("Inorder traversal" +
                " of constructed threaded avl tree is : ");
        threadedAvlTree.print(threadedAvlTree.root);
        System.out.println("\nTree Height is: " + threadedAvlTree.getHeight(threadedAvlTree.root));*/
    }
}

/*
Expected output
---------------------------
Inorder traversal of constructed tree is :
14 20 25 35
Tree Height is: 2
Inorder traversal of constructed tree is :
14 20 25 35 65 80
Tree Height is: 2
Inorder traversal of constructed tree is :
14 20 25 35 65 80 82
Tree Height is: 3
Inorder traversal of constructed tree is :
14 20 25 35 65 82
Tree Height is: 2

Inorder traversal of constructed threaded avl tree is :
14 20 25 35 65 82
Tree Height is: 2
Inorder traversal of constructed threaded avl tree is :
14 20 25 35 50 65 82 91
Tree Height is: 3
Inorder traversal of constructed threaded avl tree is :
14 20 25 35 50 65 82
Tree Height is: 3


 */
