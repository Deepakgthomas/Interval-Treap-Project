

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

/**
 * 
 * @author Jun Kit David Tang, Deepak George Thomas
 *
 */

public class IntervalTreap {

	public Node root;
	public Node node;
	public Interval i;
	public int size;
	public int height;

	public IntervalTreap() {
		size = 0;
		i = null;
	}

	public Node getRoot() {
		return root;
	}

	public int getSize() {
		return size;
	}

//	public int size(Node node) {
//		if (node == null)
//			return 0;
//		return (size(node.getLeft()) + 1 + size(node.getRight()));
//	}

	public void setheight(Node z) {

		Node k = z;
		while (k != null) {
			if (k.right == null && k.left == null) {
				k.height = 0;
			} else if (k.right == null || k.left == null) {
				if (k.left == null) {
					k.height = 1 + k.right.height;
				} else {
					k.height = 1 + k.left.height;
				}
			} else {
				k.height = 1 + Math.max(k.left.height, k.right.height);

			}
			k = k.parent;
		}
		
//		if(z == null){
//            return -1;
//         }
//		int leftHeight = setheight(z.getLeft());
//		int rightHeight = setheight(z.getRight());
//
//		if (leftHeight > rightHeight) {
//			return (leftHeight + 1);
//		} else {
//			return (rightHeight + 1);
//		}
	}

	public int getHeight() {
		if (root == null) {
			return -1;
		} else {
			return root.height;
		}
//		
//		Node temp = root;
//		return setheight(temp);
	}

	public void intervalInsert(Node z) {
//		Random rand = new Random();
//		z.priority = rand.nextInt(Integer.MAX_VALUE);
		z.imax = z.interv.high;
		z.key = z.interv.low;
		Node y = null;
		Node x = root;
		while (x != null) {
			y = x;
			if (z.key < x.interv.low) {
				x.imax = Math.max(x.imax, z.interv.high);
				x = x.left;
			}
			else if (z.key > x.interv.low) {
				x.imax = Math.max(x.imax, z.interv.high);
				x = x.getRight();
			} else {
				if (z.interv.high < x.interv.high) {
					x.imax = Math.max(x.imax, z.interv.high);
					x = x.left;
				} else {
					x.imax = Math.max(x.imax, z.interv.high);
					x = x.right;
				}
			}
		}
		
		
		z.parent = y;
		if (y == null) {
			root = z;
			setheight(z); //(1)
		} else if (z.key < y.interv.low) {
			y.left = z;
			setheight(z); //(2)
		} else if (z.key > y.interv.low) {
			y.right = z;
			setheight(z); //(3)
		} else {
			if (z.interv.high < y.interv.high) {
				y.left = z;
				setheight(z); //(4)
			} else {
				y.right = z;
				setheight(z); //(5)
			}
		}
		
		
		
		while (z.parent != null && z.getPriority() < z.parent.getPriority()) {
			if (z == z.parent.right) {
				z = z.parent;
				leftRotate(z);
				z.imax = imax_modification(z);
				z.height-=1;
				//System.out.println(z.imax);
				z.parent.imax = imax_modification(z.parent);
				z.parent.height += 1;
				//System.out.println(z.parent.imax);
				//setheight(z);///set height might cause the run time issue (6)
				z = z.parent;
			}
			else {
				z = z.parent;
				rightRotate(z);
				z.imax = imax_modification(z);
				z.height-=1;
				z.parent.imax = imax_modification(z.parent);
				z.parent.height += 1;
				//setheight(z); (7)
				z = z.parent;
			}
		}
		//size = getSize();
		this.size++;
		
		
	}

	public void intervalDelete(Node z) {
		if (z.left == null) {
			Transplant(z, z.getRight());////What is Z at the end of the transplant
			if (z.right == null) {
				imax_update(z.parent);///Maybe update imax from Z
			} else {
				imax_update(z.getRight());
			}
			setheight(z.parent); //(8)

		} else if (z.getRight() == null) {
			Transplant(z, z.getLeft());
			if (z.left == null) {
				imax_update(z.parent);
			} else {
				imax_update(z.getLeft());
			}
			setheight(z.parent); //(10)
		} else {
			Node y = Minimum(z.getRight());
			if (y.parent != z) {
				Transplant(y, y.getRight());
//				System.out.println("New Y="+y.interv.low);
				if(y.right == null) {
					imax_update(y.parent);
				}
				else {
					imax_update(y.right);
				}
				
				setheight(y.parent); //(11)
				y.right = z.getRight();
				y.getRight().parent = y;
			}
			Transplant(z, y);
			setheight(z.parent); //(12)
			imax_update(y);
			y.left = z.getLeft();
			y.left.parent = y;
			
			while ((y.getLeft() != null && y.priority > y.getLeft().priority)
					|| (y.getRight() != null && y.priority > y.getRight().priority)) {
				if (y.getLeft() == null) {
					leftRotate(y);
					y.imax = imax_modification(y);
					y.parent.imax = imax_modification(y.parent);
				} else if (y.getRight() == null) {
					rightRotate(y);
					y.imax = imax_modification(y);
					y.parent.imax = imax_modification(y.parent);
				} else if (y.getRight().priority < y.getLeft().priority) {
					leftRotate(y);
					y.imax = imax_modification(y);
					y.parent.imax = imax_modification(y.parent);
				} else {
					rightRotate(y);
					y.imax = imax_modification(y);
					y.parent.imax = imax_modification(y.parent);
				}
			}
			setheight(y); //(13)
		}
		this.size --;
		//size = getSize();
	}

	public Node intervalSearch(Interval i) {
		Node x = root;
		while (x != null && !i.overlaps(x.interv)) {
			if (x.getLeft() != null && x.getLeft().imax >= i.low) {
				x = x.left;
			} else {
				x = x.right;
			}
		}
		return x;
	}

	public Node Minimum(Node x) {
		while (x.left != null) {
			x = x.left;
		}
		return x;
	}

	public Node Maximum(Node x) {
		while (x.right != null) {
			x = x.right;
		}
		return x;
	}

	public void Transplant(Node u, Node v) {
		if (u.parent == null) {
			root = v;
		} else if (u == u.parent.left) {
			u.parent.left = v;
		} else {
			u.parent.right = v;
		}
		if (v != null) {
			v.parent = u.parent;
		}
	}

	public void rightRotate(Node z) {
		Node y = z.getLeft();
		z.left = y.right;
		if (y.right != null) {
			y.right.parent = z;
		}
		y.parent = z.parent;
		if (z.parent == null) {
			root = y;
		} else if (z == z.parent.right) {
			z.parent.right = y;
		} else {
			z.parent.left = y;
		}
		y.right = z;
		z.parent = y;
	}

	public void leftRotate(Node z) {
		Node y = z.right;
		z.right = y.left;
		if (y.left != null) {
			y.left.parent = z;
		}
		y.parent = z.parent;
		if (z.parent == null) {
			root = y;
		} else if (z == z.parent.left) {
			z.parent.left = y;
		} else {
			z.parent.right = y;
		}
		y.left = z;
		z.parent = y;
	}

	public int imax_modification(Node x) {
		if (x.right == null && x.left == null) {
			return x.interv.getHigh();
		} else if (x.right == null) {
			// return Math.max(x.interv.getHigh(), x.getLeft().getIMax());
			return Math.max(x.interv.high, x.getLeft().imax);
		} else if (x.left == null) {
			return Math.max(x.interv.high, x.getRight().imax);
		} else {
			return Math.max(x.interv.high, Math.max(x.left.imax, x.right.imax));
		}
	}

	public void imax_update(Node current) {
		while (current != root) {
			if (current.right == null && current.left == null) {
				current.imax = current.interv.getHigh();
			} else if (current.right == null) {
				current.imax = Math.max(current.interv.getHigh(), current.left.getIMax());
			} else if (current.left == null) {
				current.imax = Math.max(current.interv.getHigh(), current.right.getIMax());
			} else {
				current.imax = Math.max(current.interv.getHigh(),
						Math.max(current.left.getIMax(), current.right.getIMax()));
			}
			current = current.parent;
		}
	}

	/*
	 * Extra credit for Node intervalSearchExactly(Interval i) method
	 */
	public Node intervalSearchExactly(Interval i) {
		Node x = root;

		int k = i.low;
		int l = i.high;
		while (x != null) {
			if (k < x.interv.getLow()) {
				x = x.left;
				if (x != null && k == x.interv.getLow() && l == x.interv.getHigh()) {
					return x;
				}
			}
			else if (k > x.interv.getLow()) {
				x = x.right;
				if (x != null && k == x.interv.getLow() && l == x.interv.getHigh()) {
					return x;
				}

			} else if (x != null && k == x.interv.getLow() && l == x.interv.getHigh()) {
				return x;
			} else {
				if (l < x.interv.getHigh()) {
					x = x.left;
					if (x != null && k == x.interv.getLow() && l == x.interv.getHigh()) {
						return x;
					}
				} else {
					x = x.right;
					if (x != null && k == x.interv.getLow() && l == x.interv.getHigh()) {
						return x;
					}

				}
			}
		}
		return x;
	}

	/*
	 * Extra credit for List<Interval> overlappingIntervals(Interval i) method
	 */
	public List<Interval> overlappingIntervals(Interval i) {
		List<Interval> results = new ArrayList<Interval>();
		searchAll(i, root, results);
		return results;
	}

	/*
	 * Helper method for List<Interval> overlappingIntervals method
	 */
	private List<Interval> searchAll(Interval interval, Node node, List<Interval> results) {
		if (node.interv.overlaps(interval)) {
			results.add(node.interv);
		}
		if (node.left != null && node.left.imax >= interval.low) {
			searchAll(interval, node.left, results);
		}
		if (node.right != null && node.right.key <= interval.high) {
			searchAll(interval, node.right, results);
		}
		return results;
	}
//	public void inOrder(Node i){
//        if(i != null){
//            inOrder(i.getLeft());
//            Node parent = i.getParent();
//            Node left = i.getLeft();
//            Node right = i.getRight();
//
//            System.out.print("Node: ("+i.getInterv().getLow()+" "+i.getInterv().getHigh()+" "+ "IMAX="+i.getIMax()+" "+i.getPriority()+" "+"height="+this.getHeight()+") ");
//            if(parent != null)
//                System.out.print("Parent: ("+parent.getInterv().getLow()+" "+parent.getInterv().getHigh()+" "+ parent.getIMax()+" "+parent.getPriority()+" ");
//            else
//                System.out.print("Parent: (null) ");
//            if(left != null)
//                System.out.print(" Left: ("+left.getInterv().getLow()+" "+left.getInterv().getHigh()+" "+ left.getIMax()+" "+left.getPriority()+" ");
//            else
//                System.out.print("Left: (null) ");
//            if(right != null)
//                System.out.println("Right: ("+right.getInterv().getLow()+" "+right.getInterv().getHigh()+" "+ right.getIMax()+" "+right.getPriority()+" ");
//            else
//                System.out.println("Right: (null)");
//            inOrder(i.getRight());
//        }
//    }
}
//class MyClass{
//////	private Scanner sc;
//////	IntervalTreap it1 = new IntervalTreap();
//////	ArrayList<Interval> TP = new ArrayList<Interval>();
//////	ArrayList<Interval> TN = new ArrayList<Interval>();
//////
//////	@AfterEach
//////	public void tearDown() {
//////		it1 = null;
//////		sc.close();
//////		sc = null;
//////		TP = null;
//////		TN = null;
//////	}
//////	private void scanConstruct(String fn) {
//////		File f = new File(fn);
//////		String line;
//////		String[] split;
//////		try {
//////			sc = new Scanner(f);
//////			sc.nextLine(); //skip first line "TP"
//////			while (sc.hasNextLine()) {
//////				line = sc.nextLine();
//////				if (line.equals("TN")) {
//////					break;
//////				}
//////				split = line.split(" ");
//////				TP.add(new Interval(Integer.parseInt(split[0]), Integer.parseInt(split[1])));
////////				TP.add(new Interval(5, 6));
//////			}
//////			while (sc.hasNextLine()) {
//////				line = sc.nextLine();
//////				if (line.equals("Intervals")) {
//////					break;
//////				}
//////				split = line.split(" ");
//////				TN.add(new Interval(Integer.parseInt(split[0]), Integer.parseInt(split[1])));
//////			}
//////			while (sc.hasNextLine()) {
//////				line = sc.nextLine();
//////				split = line.split(" ");
//////				it1.intervalInsert(new Node(new Interval(Integer.parseInt(split[0]),
//////						Integer.parseInt(split[1])
//////				)));
//////			}
//////		} catch (FileNotFoundException e) {
//////			fail("File not found exception");
//////		}
//////
//////	}
//////	public void testSmall() {
//////		scanConstruct("smal.txt");
//////		for (Interval i : TP) {
//////			System.out.println(i.low + " " + i.high);
//////			assertNotNull(it1.intervalSearch(i));
//////		}
//////		for (Interval j : TN) {
//////			assertNull(it1.intervalSearch(j));
//////		}
//////		//testTreapStructure(it1);
//////	}
////	
//	public static void main(String[] args) throws FileNotFoundException {
////		Interval i1 = new Interval(10,20);
////		Node n1 = new Node(i1);
////		n1.priority = 0;
////		
////		MyClass X = new MyClass();
////		X.construct();
//		Interval i1 = new Interval(16,21);
//		Interval i2 = new Interval(8,9);
//		Interval i3 = new Interval(5,8);
//		Interval i4 = new Interval(15,23);
//		Interval i5 = new Interval(0,3);
//		Interval i6 = new Interval(6,10);
//		Interval i7 = new Interval(25,30);
//		Interval i8 = new Interval(17,19);
//		Interval i9 = new Interval(26,26);
//		Interval i10 = new Interval(19,20);
//		Interval i11 = new Interval(25,29);
//		Interval i12 = new Interval(25,31);
//		Interval i13 = new Interval(25,26);
//		Interval i14 = new Interval(25,32);
//		Interval i15 = new Interval(17,1);
//		Interval i16 = new Interval(8,8);
//		Node n1 = new Node(i1);
//		Node n2 = new Node(i2);
//		Node n3 = new Node(i3);
//		Node n4 = new Node(i4);
//		Node n5 = new Node(i5);
//		Node n6 = new Node(i6);
//		Node n7 = new Node(i7);
//		Node n8 = new Node(i8);
//		Node n9 = new Node(i9);
//		Node n10 = new Node(i10);
//		Node n11 = new Node(i11);
//		Node n12 = new Node(i12);
//		Node n13 = new Node(i13);
//		Node n14 = new Node(i14);
//		Node n15 = new Node(i15);
//		Node n16 = new Node(i16);
//		n1.priority = 8;
//		n2.priority = 12;
//		n3.priority = 17;
//		n4.priority = 16;
//		n5.priority = 21;
//		n6.priority = 20;
//		n7.priority = 10;
//		n8.priority = 13;
//		n9.priority = 11;
//		n10.priority = 12;
//		n11.priority = 14;
//		n12.priority = 2;
//		n13.priority = 18;
//		n14.priority = 16;
//		n15.priority = 1;
//		n16.priority = 0;
//		
//		IntervalTreap treap = new IntervalTreap();
//		treap.intervalInsert(n1);
//		treap.intervalInsert(n2);
//		treap.intervalInsert(n3);
//		treap.intervalInsert(n4);
//		treap.intervalInsert(n5);
//		treap.intervalInsert(n6);
//		treap.intervalInsert(n7);
//		treap.intervalInsert(n8);
//		treap.intervalInsert(n9);
//		treap.intervalInsert(n10);
//		treap.intervalInsert(n11);
//		treap.intervalInsert(n12);
//		treap.intervalInsert(n13);
//		treap.intervalInsert(n14);
//		treap.intervalInsert(n15);
//		treap.intervalInsert(n16);
//		
//		System.out.println("Priority of N1 = "+n1.priority);
//		System.out.println("Priority of N2 = "+n2.priority);
//		System.out.println("Priority of N3 = "+n3.priority);
//		System.out.println("Priority of N4 = "+n4.priority);
//		System.out.println("Priority of N5 = "+n5.priority);
//		System.out.println("Priority of N6 = "+n6.priority);
//		System.out.println("Priority of N7 = "+n7.priority);
//		System.out.println("Priority of N8 = "+n8.priority);
//		System.out.println("Priority of N9 = "+n9.priority);
//		System.out.println("Priority of N10 = "+n10.priority);
//		System.out.println("Imax of node 8 = "+n8.imax);
//		System.out.println("Imax of node 10 = "+n10.imax);
//		Node p = treap.intervalSearchExactly(i9);
//		
//		assertNotNull(p);
//		//System.out.println(treap.getSize());
////		
//////		
////		
////		System.out.println(treap.intervalSearch(new Interval(11,14)));
////		assertEquals(n4,treap.intervalSearch(new Interval(22,25)));
//////		}
////		MyClass X = new MyClass();
////		X.testSmall();
//		
//		
//}}
//
//	