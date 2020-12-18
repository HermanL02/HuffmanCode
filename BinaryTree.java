package Assignment4;


/**
This class describes a simple BinaryTree class. It was provided by Srini
(Dr. Srini Sampalli).
*/
/**Herman Liang
 * B00837314
 * Class: BinaryTree.java
 * 
 * READ THIS FIRST:
 * The PDF indicates that the only path we need to input in ENCODING PART is the path of INPUT FILE. 
 * However, I think to make it more regulated. During the Encode Part, there are three inputs. 
 * The first input is the path of the input. 
 * The second input is the path of the Huffman.txt file. 
 * The third input is the path of the Encoded.txt file. 
 * 
 * Samething for DECODE METHOD
 * The first input is the path of the Encoded.txt
 * The second input is the path of the Huffman.txt
 * The third input is the path of the Decoded.txt
 * 
 * Just want to make the work more precise
 */

import java.util.LinkedList;
import java.util.Queue;

public class BinaryTree<T>
{
	private T data;
	private BinaryTree<T> parent;
	private BinaryTree<T> left;
	private BinaryTree<T> right;

	public BinaryTree()
	{
		parent = left = right = null;
		data = null;
	}

	public void makeRoot(T data)
	{
		if (!isEmpty())
		{
			System.out.println("Can't make root. Already exists");
		}
		else
			this.data = data;
	}

	public void setData(T data)
	{
		this.data = data;
	}

	public void setLeft(BinaryTree<T> tree)
	{
		left = tree;
	}
	public void setRight(BinaryTree<T> tree)
	{
		right = tree;
	}
	public void setParent(BinaryTree<T> tree)
	{
		parent = tree;
	}

	public T getData()
	{
		return data;
	}
	public BinaryTree<T> getParent()
	{
		return parent;
	}
	public BinaryTree<T> getLeft()
	{
		return left;
	}
	public BinaryTree<T> getRight()
	{
		return right;
	}


	public void attachLeft(BinaryTree<T> tree)
	{
		if (tree==null) return;
		else if (left!=null || tree.getParent()!=null)
		{
			System.out.println("Can't attach");
			return;
		}
		else
		{

				tree.setParent(this);
				this.setLeft(tree);
		}
	}

	public void attachRight(BinaryTree<T> tree)
	{
		if (tree==null) return;
		else if (right!=null || tree.getParent()!=null)
		{
			System.out.println("Can't attach");
			return;
		}
		else
		{

				tree.setParent(this);
				this.setRight(tree);
		}
	}

	public BinaryTree<T> detachLeft()
	{
		if (this.isEmpty()) return null;
		BinaryTree<T> retLeft = left;
		left = null;
		if (retLeft!=null) retLeft.setParent(null);
		return retLeft;
	}
	public BinaryTree<T> detachRight()
	{
		if (this.isEmpty()) return null;
		BinaryTree<T> retRight = right;
		right =null;
		if (retRight!=null) retRight.setParent(null);
		return retRight;
	}
	public boolean isEmpty()
	{
		if (data == null)
			return true;
		else
			return false;
	}
	public void clear()
	{
		left = right = parent =null;
		data = null;
	}

	public BinaryTree<T> root()
	{
		if (parent == null)
			return this;
		else
		{
			BinaryTree<T> next = parent;
			while (next.getParent()!=null)
				next = next.getParent();
			return next;
		}
	}

	public static <T> void preorder(BinaryTree<T> t)
	{
		if (t!=null)
		{
			System.out.print(t.getData()+"\t");
			preorder(t.getLeft());
			preorder(t.getRight());
		}
	}

	public static <T> void inorder(BinaryTree<T> t)
	{
		if (t!=null)
		{
			inorder(t.getLeft());
			System.out.print(t.getData() + "\t");
			inorder(t.getRight());
		}
	}

	public static <T> void postorder(BinaryTree<T> t)
	{
		if (t!=null)
		{
			postorder(t.getLeft());
			postorder(t.getRight());
			System.out.print(t.getData() + "\t");
		}
	}
	
    // this method uses a modified BFS to print the data associated with all
    // nodes/trees in level order
    public static <T> void levelorder(BinaryTree<T> t){
        if(t==null)
            return;
        // initialize Queue to store references to BinaryTrees and
        // preload agenda
        Queue<BinaryTree<T>> q = new LinkedList<BinaryTree<T>>();
        q.add(t);

        while(!q.isEmpty()){
            BinaryTree<T> tmp = q.remove();
			System.out.print(tmp.getData() + "\t");
            if(tmp.getLeft()!=null)
                q.add(tmp.getLeft());
            if(tmp.getRight()!=null)
                q.add(tmp.getRight());
        }
	}
}
