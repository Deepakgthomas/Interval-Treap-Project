import java.util.Random;

/**
 * 
 * @author Jun Kit David Tang, Deepak George Thomas
 *
 */
public class Node {

	public Node parent;
	public Node left;
	public Node right;
	public Node root;
	public Interval interv;

	public int key, imax, priority, height;

	public Node(Interval i) {
		interv = i;
		Random rand = new Random();
		priority = rand.nextInt(Integer.MAX_VALUE);
		getPriority();
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public Node getLeft() {
		return left;
	}

	public void setLeft(Node left) {
		this.left = left;
	}

	public Node getRight() {
		return right;
	}

	public void setRight(Node right) {
		this.right = right;
	}

	public Interval getInterv() {
		return interv;
	}

	public int getIMax() {
		return imax;
	}

	public int getPriority() {
		return priority;
	}
	
	public void setKey()
	{
		key = this.interv.getLow();
	}
	
	public int key()
	{
		return key;
	}
	
//	public int getHeight()
//	{
//		int leftHeight = 0, rightHeight = 0;
//		if(this.left != null)
//			leftHeight = this.getLeft().getHeight();
//		if(this.right != null)
//			rightHeight = this.getRight().getHeight();
//		
//		return 1 + Math.max(leftHeight, rightHeight);
//	}
//	public void setPriority(int v) {
//		 	this.priority = v;
//		}
}
