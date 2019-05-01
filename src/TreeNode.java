import java.util.*;
/**
 * Source
 * I tried to make the code from the TExtbook work 
 * but I had trouble with it, then I wnet online looking for a solution
 * I could actually work with and this is what I am submitting here
 * 
 * https://prismoskills.appspot.com/lessons/Binary_Trees/Tree_printing.jsp
 * @author 
 */
public class TreeNode {
 
    public int idata;
    
    public TreeNode left;
    public TreeNode right;
    public TreeNode parent;
    
    // variables needed to print the tree like a tree
    int depth=0;
    int level=0;
    int drawPos=0;
    
    static int sumOfLeaves = 0;
 
    /******** Functions to create a random binary search tree *********/
    
    public static int RANDOM_RANGE = 1000;
    /**
     * this method creates or random binary tree size numNodes
     * @param numNodes
     * @return 
     */
    public static TreeNode createRandomIntegerTree (int numNodes)
    {
        RANDOM_RANGE = 10*numNodes;
        
        TreeNode root = new TreeNode ();
        root.idata = (int)(Math.random()*RANDOM_RANGE);
        
        int treeSize = countNodes(root);
        while (treeSize < numNodes)
        {
            int count = numNodes - treeSize;
            while (count-- > 0)
                root.insertInteger((int)(Math.random()*RANDOM_RANGE));
            treeSize = countNodes (root);
        }
        return root;
    }
    
    
    // Inserts a given number into the BST
    /**
     * This method inserts data into the tree
     * @param data 
     */
    void insertInteger(int data) 
    {
        if (this.idata == data)
            return;
        if (this.idata < data)
        {
            if (this.right == null)
            {
                this.right = new TreeNode();
                this.right.idata = data;
                this.right.parent = this;
            }
            else
            {
                this.right.insertInteger(data);
            }
        }
        else
        {
            if (this.left == null)
            {
                this.left = new TreeNode();
                this.left.idata = data;
                this.left.parent = this;
            }
            else
            {
                this.left.insertInteger(data);
            }
        }
    }
    
    
 
    // Creates a random tree and prints it like a tree
    /**
     * The start of the program
     * Here the tree is created and stats computed
     * @param args 
     */
    
    public static void main(String[] args) 
    {
        //This next line creates the random binary tree
        TreeNode root = createRandomIntegerTree(1023);
//        root.inOrderInteger(", ");//this line prints all the leaves values, i commented cause it makes the program slow
        
//        drawTree (root); I commented this out cause it gets huge and slow, but it draws the tree
        //but only try it with small trees

        //The next two lines print the list of leaves depths
//        System.out.println("\nList of leave's lengths");
        inOrder(root);
        //The next couple of lines print the maximum depth of the tree
        System.out.print("\nMax Depth: ");
        System.out.println(depth(root));
        //the next line gets the sum of all the depths to calcuate the average
        int sumOfDepths = sumOfLeafDepths(root,0);
        //the next two lines prints the sum of all the depths
        System.out.println("\nSum of leave depths");
        System.out.println(sumOfDepths);
        //The next line show the total number of leaves
        System.out.println("\nNumber of leaves :"+sumOfLeaves);
        System.out.println("\nNumber of nodes :"+countNodes(root));;
        //The next line show the average depth of leaves
        System.out.println("\nThe depth average :"+sumOfDepths/sumOfLeaves);
    }
 
 
    /************ Actual functions that print the tree like a tree
     * @param root ********************/
    static void drawTree(TreeNode root) 
    {
        
        System.out.println("\n\nLevel order traversal of tree:");
        int depth = depth(root);
        setLevels (root, 0);
        
        int depthChildCount[] = new int [depth+1];
        
        
        LinkedList<TreeNode> q = new  LinkedList<TreeNode> ();
        q.add(root.left);
        q.add(root.right);
        
        // draw root first
        root.drawPos = (int)Math.pow(2, depth-1)*H_SPREAD;
        currDrawLevel = root.level;
        currSpaceCount = root.drawPos;
        System.out.print(getSpace(root.drawPos) + root.idata);
        
        while (!q.isEmpty())
        {
            TreeNode ele = q.pollFirst();
            drawElement (ele, depthChildCount, depth, q);
            if (ele == null)
                continue;
            q.add(ele.left);
            q.add(ele.right);
        }
        System.out.println();
    }
    
    static int currDrawLevel  = -1;
    static int currSpaceCount = -1;
    static final int H_SPREAD = 3; 
    static void drawElement(TreeNode ele, int depthChildCount[], int depth, LinkedList<TreeNode> q) 
    {
        if (ele == null)
            return;
        
        if (ele.level != currDrawLevel)
        {
            currDrawLevel = ele.level;
            currSpaceCount = 0;
            System.out.println();
            for (int i=0; i<(depth-ele.level+1); i++)
            {
                int drawn = 0;
                if (ele.parent.left != null)
                {
                    drawn = ele.parent.drawPos - 2*i - 2;
                    System.out.print(getSpace(drawn) + "/");
                }
                if (ele.parent.right != null)
                {
                    int drawn2 = ele.parent.drawPos + 2*i + 2;
                    System.out.print(getSpace(drawn2 - drawn) + "\\");
                    drawn = drawn2;
                }
                
                TreeNode doneParent = ele.parent;
                for (TreeNode sibling: q)
                {
                    if (sibling == null)
                        continue;
                    if (sibling.parent == doneParent)
                        continue;
                    doneParent = sibling.parent;
                    if (sibling.parent.left != null)
                    {
                        int drawn2 = sibling.parent.drawPos - 2*i - 2;
                        System.out.print(getSpace(drawn2-drawn-1) + "/");
                        drawn = drawn2;
                    }
                    
                    if (sibling.parent.right != null)
                    {
                        int drawn2 = sibling.parent.drawPos + 2*i + 2;
                        System.out.print(getSpace(drawn2-drawn-1) + "\\");
                        drawn = drawn2;
                    }
                }
                System.out.println();
            }
        }
        int offset=0;
        int numDigits = (int)Math.ceil(Math.log10(ele.idata));
        if (ele.parent.left == ele)
        {
            ele.drawPos = ele.parent.drawPos - H_SPREAD*(depth-currDrawLevel+1);
            //offset = 2;
            offset += numDigits/2;
        }
        else
        {
            ele.drawPos = ele.parent.drawPos + H_SPREAD*(depth-currDrawLevel+1);
            //offset = -2;
            offset -= numDigits;
        }
        
        System.out.print (getSpace(ele.drawPos - currSpaceCount + offset) + ele.idata);
        currSpaceCount = ele.drawPos + numDigits/2;
    }
 
    
    // Utility functions
    /**
     * this function prints the tree's values in an Inorder fashion. Left-Root_Right
     * @param sep 
     */
    public void inOrderInteger (String sep)
    {
        if (left != null)
            left.inOrderInteger(sep);
        System.out.print(idata + sep);
        if (right != null)
            right.inOrderInteger(sep);
    }
    
    /**
     * this function I adapted to navigate the tree and find leaves for stats purposes.
     * @param n 
     */
    private static void inOrder(TreeNode n) { 
        //this function I got from the link below
        //This basically navigates through the tree and tests for leaves and increments the count
        //https://javarevisited.blogspot.com/2016/08/inorder-traversal-of-binary-tree-in-java-recursion-iteration-example.html
        if (n == null) { 
            return; 
        } 
        
        inOrder(n.left); 
        if(n.left == null && n.right == null){
//            System.out.printf("%s ", n.idata);
            sumOfLeaves++;
        }
        inOrder(n.right); }

    /**
     * computes the sum of leaves depths 
     * @param node
     * @param depth
     * @return 
     */
    static int sumOfLeafDepths( TreeNode node, int depth ) {
             // When called as sumOfLeafDepths(root,0), this will compute the
             // sum of the depths of all the leaves in the tree to which root
             // points.  When called recursively, the depth parameter gives
             // the depth of the node, and the routine returns the sum of the
             // depths of the leaves in the subtree to which node points.
             // In each recursive call to this routine, depth goes up by one.
          if ( node == null ) {
                // Since the tree is empty and there are no leaves,
                // the sum is zero.
             return 0;
          }
          else if ( node.left == null && node.right == null) {
                // The node is a leaf, and there are no subtrees of node, so
                // the sum of the leaf depths is just the depth of this node.
//              System.out.println("\n Node"+node.idata+" depth "+depth+"\n");
//              System.out.println(depth);
             return depth;
          }
          else {
                // The node is not a leaf.  Return the sum of the
                // the depths of the leaves in the subtrees.
             return sumOfLeafDepths(node.left, depth + 1) 
                         + sumOfLeafDepths(node.right, depth + 1);
          }
      } // end sumOfLeafDepth()
    /**
     * returns depth of tree passed in
     * @param n
     * @return 
     */
    public static int depth (TreeNode n)
    {
        if (n == null)
            return 0;
        n.depth = 1 + Math.max(depth(n.left), depth(n.right));
        return n.depth;
    }
    
    /**
     * counts nodes in tree
     * @param n
     * @return 
     */
    public static int countNodes (TreeNode n)
    {
        if (n == null)
            return 0;
        return 1 + countNodes(n.left) + countNodes(n.right);
    }
    
    static void setLevels (TreeNode r, int level)
    {
        if (r == null)
            return;
        r.level = level;
        setLevels (r.left, level+1);
        setLevels (r.right, level+1);
    }
 
    static String getSpace (int i)
    {
        String s = "";
        while (i-- > 0)
            s += " ";
        return s;
    }
 
}