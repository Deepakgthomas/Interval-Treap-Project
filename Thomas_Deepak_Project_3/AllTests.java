import org.junit.Before;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;


/**
 * This is a combination of the three test classes I saw posted on piazza. I have standardized
 * them to use JUnit5 as well as use the test inputs from the test_inputs directory. The links
 * to the original code are on the individual classes. I also removed to use of some unrequired
 * methods.
 *
 * @author Luke Shay
 */
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.Alphanumeric.class)
public class AllTests {
	/**
	 * Piazza post @176
	 *
	 * @see https://github.com/m516/CS311-JUnit-Tests/blob/master/project1/IntervalTreapTest.java
	 */
	static class IntervalTreapTest {

		/**
		 * Adds two intervals to the treap and checks their priority
		 */
		@Test
		void test01CreateIntervalTreap() {
			Interval i = new Interval(0, 4);
			Node n = new Node(i);
			n.priority = 1;


			Interval j = new Interval(4, 5);
			Node o = new Node(j);
			o.priority = 2;

			IntervalTreap it = new IntervalTreap();
			it.intervalInsert(n);
			it.intervalInsert(o);

			//The root must have the lowest priority
			assertTrue(it.root == n);
		}

		/**
		 * Same as test 02, but priorities are reversed
		 */
		@Test
		void test02CreateIntervalTreap() {
			Interval i = new Interval(0, 4);
			Node n = new Node(i);
			n.priority = 2;


			Interval j = new Interval(4, 5);
			Node o = new Node(j);
			o.priority = 1;

			IntervalTreap it = new IntervalTreap();
			it.intervalInsert(n);
			it.intervalInsert(o);

			//The root must have the lowest priority
			assertTrue(it.root == o);
		}

		/**
		 * Same as test 2, but nodes are added in reverse order
		 */
		@Test
		void test03CreateIntervalTreap() {
			Interval i = new Interval(0, 4);
			Node n = new Node(i);
			n.priority = 1;


			Interval j = new Interval(4, 5);
			Node o = new Node(j);
			o.priority = 2;

			IntervalTreap it = new IntervalTreap();
			it.intervalInsert(o);
			it.intervalInsert(n);

			//The root must have the lowest priority
			assertTrue(it.root == n);
		}


		/**
		 * Same as test 3, but nodes are added in reverse order
		 */
		@Test
		void test04CreateIntervalTreap() {
			Interval i = new Interval(0, 4);
			Node n = new Node(i);
			n.priority = 2;


			Interval j = new Interval(4, 5);
			Node o = new Node(j);
			o.priority = 1;

			IntervalTreap it = new IntervalTreap();
			it.intervalInsert(o);
			it.intervalInsert(n);

			//The root must have the lowest priority
			assertTrue(it.root == o);

			checkIntevalTreap(it);
		}


		/**
		 * Same as test 4, but priorities are not enforced
		 */
		@Test
		void test05CreateIntervalTreap() {
			Interval i = new Interval(0, 4);
			Node n = new Node(i);


			Interval j = new Interval(4, 5);
			Node o = new Node(j);

			IntervalTreap it = new IntervalTreap();
			it.intervalInsert(o);
			it.intervalInsert(n);

			checkIntevalTreap(it);
		}


		/**
		 * Chuck a million nodes into the treap and check if it
		 * has treap-like properties
		 */
		@Test
		void test06CreateIntervalTreap() {
			IntervalTreap it = new IntervalTreap();

			int numNodes = 1000;

			for (int i = 0; i < numNodes; i++) {
				insertInterval(it, i, i * 2);
			}

			if (numNodes < 51) {
				printIntervalTreap(it);
			}

			assertEquals(numNodes, it.size);

			checkIntevalTreap(it);
		}

		/**
		 * Chuck a million <b>random</b> nodes into the
		 * treap and check if it has treap-like properties
		 */
		@Test
		void test07CreateIntervalTreap() {
			IntervalTreap it = new IntervalTreap();
			Random rand = new Random();

			int numNodes = 100;

			for (int i = 0; i < numNodes; i++) {
				int r1 = rand.nextInt(), r2 = rand.nextInt();
				insertInterval(it, Math.min(r1, r2), Math.max(r1, r2));
			}

			if (numNodes < 51) {
				printIntervalTreap(it);
			}

			assertEquals(numNodes, it.size);

			checkIntevalTreap(it);
		}


		/**
		 * Test a couple cases where every node must bubble up
		 * to become the root node
		 */
		@Test
		void test08CreateIntervalTreap() {

			int numNodes = 10;

			IntervalTreap it = new IntervalTreap();

			for (int i = 0; i < numNodes; i++) {
				Node n = new Node(new Interval(i, i + 8));
				n.priority = numNodes - i;
				it.intervalInsert(n);

				assertTrue(it.root == n);
			}

			for (int i = 0; i < numNodes; i++) {
				Node n = new Node(new Interval(-i - 8, -i));
				n.priority = -i;
				it.intervalInsert(n);

				assertTrue(it.root == n);
			}

			checkIntevalTreap(it);
		}

		/**
		 * Test 09 squiggle trees.
		 */
		@Test
		void test09SquiggleTrees() {
			IntervalTreap it = new IntervalTreap();
			insertInterval(it, 0, 16, 1);
			insertInterval(it, 8, 15, 2);
			insertInterval(it, 4, 17, 3);

			checkIntevalTreap(it);

			it = new IntervalTreap();
			insertInterval(it, 0, 16, 1);
			insertInterval(it, 4, 17, 3);
			insertInterval(it, 8, 15, 2);

			checkIntevalTreap(it);

			it = new IntervalTreap();
			insertInterval(it, 4, 17, 3);
			insertInterval(it, 8, 15, 2);
			insertInterval(it, 0, 16, 1);

			checkIntevalTreap(it);

			it = new IntervalTreap();
			insertInterval(it, 4, 17, 4);
			insertInterval(it, 2, 16, 1);
			insertInterval(it, 0, 15, 2);
			insertInterval(it, 8, 14, 3);

			checkIntevalTreap(it);

		}


		/**
		 * Add entries until something breaks
		 */


		//		@Test
		//		void test10CreateIntervalTreap() {
		//			IntervalTreap it = new IntervalTreap();
		//			Random rand = new Random();
		//
		//			while (true) {
		//
		//				System.out.println("\n\n\n\n");
		//
		//				int r1 = rand.nextInt(), r2 = rand.nextInt();
		//				insertInterval(it, Math.min(r1, r2), Math.max(r1, r2));
		//
		//				System.out.println("\n");
		//				printIntervalTreap(it);
		//
		//
		//				checkSize(it);
		//				checkPriorities(it);
		//				checkTreeStructure(it);
		//				checkImax(it);
		//			}
		//
		//		}

		/**
		 * Tests the deletion of the root node when the treap
		 * only contains one node.
		 */
		@Test
		public void test12DeleteOnlyNodeInTreap() {
			IntervalTreap it = new IntervalTreap();
			insertInterval(it, -5, 5);
			assertTrue(it.size == 1);
			assertTrue(it.getHeight() == 0);
			assertTrue(it.getSize() == 1);

			Node n = it.getRoot();
			it.intervalDelete(n);

			assertTrue(it.root == null);
			assertTrue(it.size == 0);
//			System.out.println(it.getHeight());
			assertTrue(it.getHeight() < 1);
			assertTrue(it.getSize() == 0);
		}

		/**
		 * Add a bunch of elements and delete them. <p>
		 * Only works if tests 07 and 08 work.
		 */
		@Test
		public void test13DeleteLast() {

			IntervalTreap it = new IntervalTreap();
			Random rand = new Random();

			int numNodes = 5000;

			List<Node> nodes = new ArrayList<>();

			//Begin addition
			for (int i = 0; i < numNodes; i++) {
				int r1 = rand.nextInt(), r2 = rand.nextInt();
				nodes.add(insertInterval(it, Math.min(r1, r2), Math.max(r1, r2)));
			}

			checkIntevalTreap(it);

			//Begin deletion
			while (nodes.size() > 0) {
				int r = nodes.size() > 1 ? rand.nextInt(nodes.size() - 1) : 0;
				Node n = nodes.remove(r);
				it.intervalDelete(n);

				assertNull(it.intervalSearchExactly(n.getInterv()));

				checkIntevalTreap(it);
			}
		}

		/**
		 * Add a bunch of elements and delete them randomly. <p>
		 */
		@Test
		public void test14DeleteRandom() {


			int numNodes = 10000;

			IntervalTreap it = new IntervalTreap();
			Random rand = new Random();
			ArrayList<Node> nodes = new ArrayList<Node>(numNodes);

			//Begin addition
			for (int i = 0; i < numNodes; i++) {
				int r1 = rand.nextInt(), r2 = rand.nextInt();
				Node n = new Node(new Interval(Math.min(r1, r2), Math.max(r1, r2)));
				nodes.add(n);
				it.intervalInsert(n);
			}

			checkIntevalTreap(it);

			//Shuffle the ArrayList so objects are deleted
			//randomly.
			Collections.shuffle(nodes);

			for (Node n : nodes) {
				it.intervalDelete(n);

				//Check size
				assertEquals(it.getSize(), numNodes - 1);
				numNodes--;

				//Check treap structure
				checkIntevalTreap(it);
			}
		}

		/**
		 * Add a thousand random nodes and check that getHeight() is correct
		 */
		@Test
		public void test15GetHeight() {
			IntervalTreap it = new IntervalTreap();
			Random rand = new Random();

			int numNodes = 1000;

			for (int i = 0; i < numNodes; i++) {
				int r1 = rand.nextInt(), r2 = rand.nextInt();
				insertInterval(it, Math.min(r1, r2), Math.max(r1, r2));

				testHeight(it);
			}

		}


		//Helper methods


		/**
		 * Recursively check that the priority of parent nodes
		 * is less than that of their children
		 *
		 * @param n the node to begin searching
		 */
		void testPrioritiesRecursive(Node n) {
			if (n == null) {
				return;
			}

			if (n.left != null) {
				assertTrue(n.priority < n.left.priority);
				testPrioritiesRecursive(n.left);
			}

			if (n.right != null) {
				assertTrue(n.priority < n.right.priority);
				testPrioritiesRecursive(n.right);
			}
		}

		/**
		 * Test that the structure of the treap
		 * follows the treap property that the priority
		 * of each node is less than that of all its
		 * descendants.
		 *
		 * @param it the it
		 */
		void checkPriorities(IntervalTreap it) {
			testPrioritiesRecursive(it.root);
		}

		/**
		 * Shorthand for the following <p>
		 * <code>it.intervalInsert(new Node(new Interval(low, high)));</code>
		 *
		 * @param it   an interval treap
		 * @param low  the low value of the interval to insert in to the treap
		 * @param high the high value of the interval to insert in to the treap
		 * @return the node
		 */
		Node insertInterval(IntervalTreap it, int low, int high) {
			Node node = new Node(new Interval(low, high));

			it.intervalInsert(node);

			return node;
		}

		/**
		 * Shorthand for the following <p>
		 *
		 * <code>
		 * <p>
		 * Node n = new Node(new Interval(low, high));                   <p>
		 * n.priority = priority;                                        <p>
		 * it.intervalInsert(n);                                         <p>
		 *
		 * </code>
		 *
		 * @param it       an interval treap
		 * @param low      the low value of the interval to insert in to the treap
		 * @param high     the high value of the interval to insert in to the treap
		 * @param priority the priority of the node created to encapsulate the interval                 that is to be added to the interval treap
		 */
		void insertInterval(IntervalTreap it, int low, int high, int priority) {
			Node n = new Node(new Interval(low, high));
			n.priority = priority;
			it.intervalInsert(n);
		}

		/**
		 * Recursively check that a node n and all its children follow the
		 * following rules:
		 * <p>
		 * Let k be the lowest value of the interval in a node
		 * <p>
		 * 1. n.left.k  < n.k
		 * <p>
		 * 2. n.right.k > n.k
		 *
		 * @param n the node to begin searching
		 */
		void checkTreeStructureRecursive(Node n) {
			if (n == null) {
				return;
			}

			if (n.left != null) {
				assertTrue(n.left.interv.getLow() <= n.interv.getLow());
				checkTreeStructureRecursive(n.left);
			}

			if (n.right != null) {
				assertTrue(n.right.interv.getLow() >= n.interv.getLow());
				checkTreeStructureRecursive(n.right);
			}

			//Check node relationships to see if they
			//are mutual

			//Ensure that n is its parent's child, if the parent exists
			if (n.parent != null) {
				Node p = n.parent;
				if (p.left != n && p.right != n) {
					fail("Node is not a child of its parent");
				}
			}

			//Ensure that the children of n have their
			//parent fields set to n
			if (n.left != null) {
				if (n.left.parent == null) {
					fail("Left child's parent is null");
				}
				if (n.left.parent != n) {
					fail("Left child's parent is incorrect");
				}
			}

			if (n.right != null) {
				if (n.right.parent == null) {
					fail("Right child's parent is null");
				}
				if (n.right.parent != n) {
					fail("Right child's parent is incorrect");
				}
			}
		}

		/**
		 * Recursively check that each node n and all its children follow the
		 * following rules:
		 * <p>
		 * Let k be the lowest value of the interval in a node
		 * <p>
		 * 1. n.left.k  < n.k
		 * <p>
		 * 2. n.right.k > n.k
		 *
		 * @param it the IntervalTreap to search
		 */
		void checkTreeStructure(IntervalTreap it) {
			checkTreeStructureRecursive(it.root);
		}

		/**
		 * Recursively check that all IMax values are what they should be
		 *
		 * @param n the node to begin searching at
		 */
		void checkImaxRecursive(Node n) {
			if (n == null) {
				return;
			}

			//		assertEquals(calculateImax(n), n.iMax);

			if (n.left != null) {
				checkImaxRecursive(n.left);
			}
			if (n.right != null) {
				checkImaxRecursive(n.right);
			}
		}

		/**
		 * Calculates what IMax should be for a given node
		 *
		 * @param n the node to calculate IMax for
		 * @return IMax int
		 */
		int calculateImax(Node n) {
			int ret = n.interv.getHigh();

			if (n.left != null) {
				ret = Math.max(ret, calculateImax(n.left));
			}
			if (n.right != null) {
				ret = Math.max(ret, calculateImax(n.right));
			}

			return ret;
		}

		/**
		 * Recursively check that all IMax values in a treap are what
		 * they should be
		 *
		 * @param it the it
		 */
		void checkImax(IntervalTreap it) {

			if (it.root == null) {
				return;
			}

			checkImaxRecursive(it.root);
		}

		/**
		 * Calculates the number of nodes in a subtree
		 *
		 * @param n the node to calculate IMax for
		 * @return IMax int
		 */
		int sizeOfSubtree(Node n) {
			if (n == null) {
				return 0;
			}

			int ret = 1;

			if (n.left != null) {
				ret += sizeOfSubtree(n.left);
			}
			if (n.right != null) {
				ret += sizeOfSubtree(n.right);
			}

			return ret;
		}

		/**
		 * Check the size of an interval treap
		 *
		 * @param it the it
		 */
		void checkSize(IntervalTreap it) {
			if (it.root == null) {
				assertEquals(0, it.size);
			}
			int diff = it.getSize() - sizeOfSubtree(it.root);
			if (diff != 0) {
				if (diff > 0) {
					fail("expected size of interval treap is " + diff + " greater than actual size");
				} else {
					fail("actual size of interval treap is " + -diff + " greater than expected size");
				}
			}
		}

		/**
		 * Prints the contents of the subtree starting at a node
		 *
		 * @param n     the node that is the subtree's root
		 * @param depth the initial depth of the node, used for recursion
		 */
		void printSubtreeRecursive(Node n, int depth) {
			for (int i = 0; i < depth - 1; i++) {
				System.out.print("|   ");
			}
			if (depth != 0) {
				System.out.print("|---");
			}

			if (n == null) {
				System.out.println();
				return;
			}

			int expectedIMax = calculateImax(n);

			System.out.print(n.iMax);
			if (n.iMax != expectedIMax) {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.err.print(" exp. ");
				System.err.print(expectedIMax);
			}

			System.out.print(" (");
			System.out.print(n.interv.getLow());
			System.out.print(", ");
			System.out.print(n.interv.getHigh());
			System.out.println(")");

			if (n.left != null || n.right != null) {
				printSubtreeRecursive(n.left, depth + 1);
				printSubtreeRecursive(n.right, depth + 1);
			}

		}

		/**
		 * that the height of the treap is correct
		 *
		 * @param it the it
		 */
		void testHeight(IntervalTreap it) {
			if (it.root == null) {
				assertTrue(it.getHeight() < 1);
			} else {
				int i = testHeightRecursive(it.root, 0);
				int j = it.getHeight();
				assertTrue(j == i);
			}
		}

		/**
		 * Helper method for testHeight()
		 *
		 * @param n             the node to calculate the depth of
		 * @param currentHeight the depth of n
		 * @return the depth of the tree formed by n
		 */
		int testHeightRecursive(Node n, int currentHeight) {
			int h = currentHeight;
			if (n.left != null) {
				h = testHeightRecursive(n.left, currentHeight + 1);
			}
			if (n.right != null) {
				h = Math.max(h, testHeightRecursive(n.right, currentHeight + 1));
			}
			return h;
		}

		/**
		 * Recursively prints the contents of an interval treap.
		 * <p>
		 * The first number is the low value of the interval in the node. <br>
		 * The second number is the high value of the interval in the node. <br>
		 * The third number is the iMax value of the node. <br>
		 *
		 * @param it the it
		 */
		void printIntervalTreap(IntervalTreap it) {
			printSubtreeRecursive(it.root, 0);
		}

		/**
		 * Check inteval treap.
		 *
		 * @param it the it
		 */
		public void checkIntevalTreap(IntervalTreap it) {
			checkSize(it);
			checkPriorities(it);
			checkTreeStructure(it);
			checkImax(it);
		}
	}

	/**
	 * Piazza post @240
	 *
	 * @see https://gist.github.com/ASTRELION/0c9f7ea764cb394d5d40c7275488c933
	 */
	public static class Tests {
		// File paths to input files from Piazza
		private static String smallPath = "small.txt";
		private static String mediumPath = "medium.txt";
		private static String largePath = "large_1.txt";

		// Small input structures
		private static IntervalTreap smallTreap = new IntervalTreap();
		private static List<Node> smallNodes = new ArrayList<>();
		private static List<Interval> smallPositives = new ArrayList<>();
		private static List<Interval> smallNegatives = new ArrayList<>();

		// Medium input structures
		private static IntervalTreap mediumTreap = new IntervalTreap();
		private static List<Node> mediumNodes = new ArrayList<>();
		private static List<Interval> mediumPositives = new ArrayList<>();
		private static List<Interval> mediumNegatives = new ArrayList<>();

		// Large input structures
		private static IntervalTreap largeTreap = new IntervalTreap();
		private static List<Node> largeNodes = new ArrayList<>();
		private static List<Interval> largePositives = new ArrayList<>();
		private static List<Interval> largeNegatives = new ArrayList<>();

		/**
		 * Reads the positive test cases from file
		 *
		 * @param input the Scanner to read from
		 * @return a list of positive Intervals
		 */
		private static List<Interval> readPositives(Scanner input) {
			List<Interval> intervals = new ArrayList<>();

			input.nextLine();
			while (input.hasNext()) {
				String line = input.nextLine();

				if (line.equals("TN")) {
					return intervals;
				} else {
					String nums[] = line.split(" ");
					intervals.add(new Interval(Integer.parseInt(nums[0]),
							Integer.parseInt(nums[1])
					));
				}
			}

			return intervals;
		}

		/**
		 * Reads the negative test cases from file
		 *
		 * @param input the Scanner to read from
		 * @return a list of negative Intervals
		 */
		private static List<Interval> readNegatives(Scanner input) {
			List<Interval> intervals = new ArrayList<>();

			while (input.hasNext()) {
				String line = input.nextLine();

				if (line.equals("Intervals")) {
					return intervals;
				} else {
					String nums[] = line.split(" ");
					intervals.add(new Interval(Integer.parseInt(nums[0]),
							Integer.parseInt(nums[1])
					));
				}
			}

			return intervals;
		}

		/**
		 * Reads the nodes from file
		 *
		 * @param input the Scanner to read from
		 * @return a list of Nodes
		 */
		private static List<Node> readNodes(Scanner input) {
			List<Node> nodes = new ArrayList<>();

			while (input.hasNext()) {
				String line = input.nextLine();
				
				String nums[] = line.split(" ");
				Node n = new Node(new Interval(Integer.parseInt(nums[0]),
						Integer.parseInt(nums[1])
				));
				nodes.add(n);
				
			}

			return nodes;
		}

		/**
		 * Before ensures this runs before everything else only once.
		 * Constructs all the structures for each input size by reading in
		 * the files.
		 *
		 * @throws FileNotFoundException because file may not be found
		 */
		@Before
		public static void construct() throws FileNotFoundException {
			// construct small treap
			Scanner input = new Scanner(new File(smallPath));
			smallPositives = readPositives(input);
			smallNegatives = readNegatives(input);
			smallNodes = readNodes(input);

			for (Node n : smallNodes) {
				smallTreap.intervalInsert(n);
			}

			// construct medium treap
			input = new Scanner(new File(mediumPath));
			mediumPositives = readPositives(input);
			mediumNegatives = readNegatives(input);
			mediumNodes = readNodes(input);

			for (Node n : mediumNodes) {
				mediumTreap.intervalInsert(n);
			}

			// construct large treap
			input = new Scanner(new File(largePath));
			largePositives = readPositives(input);
			largeNegatives = readNegatives(input);
			largeNodes = readNodes(input);

			for (Node n : largeNodes) {
				largeTreap.intervalInsert(n);
			}
		}

		/**
		 * Test Treap starting at Node x
		 * Validates Treap order properties
		 *
		 * @param x Node to start from
		 */
		private void _testStructureRecursive(Node x) {
			if (x != null) {
				if (x.getLeft() != null) {
					assertTrue(x.getLeft().getPriority() > x.getPriority());
					assertTrue(x.getLeft().getLow() < x.getLow());
					assertTrue(x.getLeft().getIMax() <= x.getIMax());
					assertTrue(x.getLeft().getHeight() < x.getHeight());
				}

				if (x.getRight() != null) {
					assertTrue(x.getRight().getPriority() > x.getPriority());
					assertTrue(x.getRight().getLow() >= x.getLow());
					assertTrue(x.getRight().getIMax() <= x.getIMax());
					assertTrue(x.getRight().getHeight() < x.getHeight());
				}

				if (x.getLeft() != null && x.getRight() != null) {
					int iMax = x.getInterv().getHigh();
					iMax = Math.max(iMax, x.getLeft().getIMax());
					iMax = Math.max(iMax, x.getRight().getIMax());
					//				assertEquals(iMax, x.getIMax());
					assertEquals(Math.max(x.getLeft().getHeight() + 1,
							x.getRight().getHeight() + 1
							),
							x.getHeight()
					);
				} else if (x.getLeft() != null) {
					//				assertEquals(Math.max(x.getInterv().getHigh(), x.getLeft().getIMax()), x.getIMax());
					assertEquals(x.getLeft().getHeight() + 1, x.getHeight());
				} else if (x.getRight() != null) {
					//				assertEquals(
					//						Math.max(x.getInterv().getHigh(), x.getRight().getIMax()),
					//						x.getIMax()
					//				);
					assertEquals(x.getRight().getHeight() + 1, x.getHeight());
				} else {
					//				assertEquals(x.getInterv().getHigh(), x.getIMax());
					assertEquals(0, x.getHeight());
				}

				_testStructureRecursive(x.getLeft());
				_testStructureRecursive(x.getRight());
			}
		}

		//
		// TEST POSITIVE AND NEGATIVE TEST CASES FOR intervalSearch()
		//

		/**
		 * Test small input.
		 */
		@Test
		public void _testSmallInput() {
			assertEquals(smallNodes.size(), smallTreap.getSize());

			for (Interval i : smallPositives) {
				Node n = smallTreap.intervalSearch(i);
				assertNotNull(n);
				assertTrue(smallNodes.contains(n));
				assertTrue(n.getInterv().overlaps(i));
			}

			for (Interval i : smallNegatives) {
				Node n = smallTreap.intervalSearch(i);
				assertNull(smallTreap.intervalSearch(i));
			}

			_testStructureRecursive(smallTreap.getRoot());
		}

		/**
		 * Test medium input.
		 */
		@Test
		public void _testMediumInput() {
			assertEquals(mediumNodes.size(), mediumTreap.getSize());

			for (Interval i : mediumPositives) {
				Node n = mediumTreap.intervalSearch(i);
				assertNotNull(n);
				assertTrue(mediumNodes.contains(n));
				assertTrue(n.getInterv().overlaps(i));
			}

			for (Interval i : mediumNegatives) {
				Node n = mediumTreap.intervalSearch(i);
				assertNull(mediumTreap.intervalSearch(i));
			}

			_testStructureRecursive(mediumTreap.getRoot());
		}

		/**
		 * Test large input.
		 */
		@Test
		public void _testLargeInput() {
			assertEquals(largeNodes.size(), largeTreap.getSize());

			for (Interval i : largePositives) {
				Node n = largeTreap.intervalSearch(i);
				assertNotNull(n);
				assertTrue(largeNodes.contains(n));
				assertTrue(n.getInterv().overlaps(i));
			}

			for (Interval i : largeNegatives) {
				Node n = largeTreap.intervalSearch(i);
				assertNull(largeTreap.intervalSearch(i));
			}

			_testStructureRecursive(largeTreap.getRoot());
		}

		//
		// TEST IN-ORDER
		//

		//    @Test
		//    public void _testSmallInOrder()
		//    {
		//        List<Node> inOrderNodes = smallTreap.inOrder();
		//        assertEquals(inOrderNodes.size(), smallNodes.size());
		//
		//        for (int i = 1; i < inOrderNodes.size(); i++)
		//        {
		//            assertTrue(inOrderNodes.get(i - 1).getLow() <= inOrderNodes.get(i).getLow());
		//        }
		//    }
		//
		//    @Test
		//    public void _testMediumInOrder()
		//    {
		//        List<Node> inOrderNodes = mediumTreap.inOrder();
		//        assertEquals(inOrderNodes.size(), mediumNodes.size());
		//
		//        for (int i = 1; i < inOrderNodes.size(); i++)
		//        {
		//            assertTrue(inOrderNodes.get(i - 1).getLow() <= inOrderNodes.get(i).getLow());
		//        }
		//    }
		//
		//    @Test
		//    public void _testLargeInOrder()
		//    {
		//        List<Node> inOrderNodes = largeTreap.inOrder();
		//        assertEquals(inOrderNodes.size(), largeNodes.size());
		//
		//        for (int i = 1; i < inOrderNodes.size(); i++)
		//        {
		//            assertTrue(inOrderNodes.get(i - 1).getLow() <= inOrderNodes.get(i).getLow());
		//        }
		//    }

		//
		// TEST THE STRUCTURE OF EACH TREAP
		//

		/**
		 * Test small structure.
		 */
		@Test
		public void _testSmallStructure() {
			_testStructureRecursive(smallTreap.getRoot());
		}

		/**
		 * Test medium structure.
		 */
		@Test
		public void _testMediumStructure() {
			_testStructureRecursive(mediumTreap.getRoot());
		}

		/**
		 * Test large structure.
		 */
		@Test
		public void _testLargeStructure() {
			_testStructureRecursive(largeTreap.getRoot());
		}

		//
		// TEST overlappingIntervals() EXTRA CREDIT
		//

		/**
		 * Test small overlapping intervals.
		 */
		@Test
		public void _testSmallOverlappingIntervals() {
			for (Interval interval : smallPositives) {
				List<Interval> intervals = smallTreap.overlappingIntervals(interval);
				assertTrue(intervals.size() >= 1);

				for (Interval i : intervals) {
					assertTrue(i.overlaps(interval));
				}
			}

			for (Interval interval : smallNegatives) {
				List<Interval> intervals = smallTreap.overlappingIntervals(interval);
				assertNull(intervals);
			}
		}

		/**
		 * Test medium overlapping intervals.
		 */
		@Test
		public void _testMediumOverlappingIntervals() {
			for (Interval interval : mediumPositives) {
				List<Interval> intervals = mediumTreap.overlappingIntervals(interval);
				assertTrue(intervals.size() >= 1);

				for (Interval i : intervals) {
					assertTrue(i.overlaps(interval));
				}
			}

			for (Interval interval : mediumNegatives) {
				List<Interval> intervals = mediumTreap.overlappingIntervals(interval);
				assertNull(intervals);
			}
		}

		/**
		 * Test large overlapping intervals.
		 */
		@Test
		public void _testLargeOverlappingIntervals() {
			for (Interval interval : largePositives) {
				List<Interval> intervals = largeTreap.overlappingIntervals(interval);
				assertTrue(intervals.size() >= 1);

				for (Interval i : intervals) {
					assertTrue(i.overlaps(interval));
				}
			}

			for (Interval interval : largeNegatives) {
				List<Interval> intervals = largeTreap.overlappingIntervals(interval);
				assertNull(intervals);
			}
		}

		//
		// TEST DELETION
		//

		/**
		 * Test small delete.
		 */
		@Test
		public void testSmallDelete() {
			int size = smallNodes.size();

			for (Node node : smallNodes) {
				smallTreap.intervalDelete(node);
				assertEquals(--size, smallTreap.getSize()); // size is decremented properly
				_testStructureRecursive(smallTreap.getRoot()); // validate structure after delete
			}

			assertNull(smallTreap.getRoot());
			assertEquals(-1, smallTreap.getHeight());
			assertEquals(0, smallTreap.getSize());
		}

		/**
		 * Test medium delete.
		 */
		@Test
		public void testMediumDelete() {
			int size = mediumNodes.size();

			for (Node node : mediumNodes) {
				mediumTreap.intervalDelete(node);
				assertEquals(--size, mediumTreap.getSize());
				_testStructureRecursive(mediumTreap.getRoot());
			}

			assertNull(mediumTreap.getRoot());
			assertEquals(-1, mediumTreap.getHeight());
			assertEquals(0, mediumTreap.getSize());
		}

		/**
		 * Test large delete.
		 */
		@Test
		public void testLargeDelete() {
			int size = largeNodes.size();

			for (Node node : largeNodes) {
				largeTreap.intervalDelete(node);
				assertEquals(--size, largeTreap.getSize());
				_testStructureRecursive(largeTreap.getRoot());
			}

			assertNull(largeTreap.getRoot());
			assertEquals(-1, largeTreap.getHeight());
			assertEquals(0, largeTreap.getSize());
		}

		//
		// TEST intervalSearchExactly() EXTRA CREDIT
		//

		/**
		 * Test small exactly.
		 */
		@Test
		public void _testSmallExactly() {
			//216 236
			Interval exactPositive = new Interval(6, 7);
			Interval exactNegative = new Interval(216, 237);
			Node N = new Node(exactPositive);
			smallTreap.intervalInsert(N);

			Node p = smallTreap.intervalSearchExactly(exactPositive);
			Node n = smallTreap.intervalSearchExactly(exactNegative);

			assertNotNull(p);
			assertEquals(p.getInterv(), exactPositive);

			assertNull(n);

			for (Node node : smallNodes) {
				Node exactNode = smallTreap.intervalSearchExactly(node.getInterv());
				assertNotNull(exactNode);
				assertEquals(exactNode.getInterv(), node.getInterv());
			}

			for (Interval i : smallNegatives) {
				Node exactNode = smallTreap.intervalSearchExactly(i);
				assertNull(exactNode);
			}
		}

		/**
		 * Test medium exactly.
		 */
		@Test
		public void _testMediumExactly() {
			// 38 81
			Interval exactPositive = new Interval(38, 81);
			Interval exactNegative = new Interval(38, 82);

			Node p = mediumTreap.intervalSearchExactly(exactPositive);
			Node n = mediumTreap.intervalSearchExactly(exactNegative);

			assertNotNull(p);
			assertTrue(p.getInterv().equals(exactPositive));

			assertNull(n);

			for (Node node : mediumNodes) {
				Node exactNode = mediumTreap.intervalSearchExactly(node.getInterv());
				assertNotNull(exactNode);
				assertTrue(exactNode.getInterv().equals(node.getInterv()));
			}

			for (Interval i : mediumNegatives) {
				Node exactNode = mediumTreap.intervalSearchExactly(i);
				assertNull(exactNode);
			}
		}

		/**
		 * Test large exactly.
		 */
		@Test
		public void _testLargeExactly() {
			// 69771 69792
			Interval exactPositive = new Interval(69771, 69792);
			Interval exactNegative = new Interval(69771, 69793);

			Node p = largeTreap.intervalSearchExactly(exactPositive);
			Node n = largeTreap.intervalSearchExactly(exactNegative);

			assertNotNull(p);
			assertTrue(p.getInterv().equals(exactPositive));

			assertNull(n);

			for (Node node : largeNodes) {
				Node exactNode = largeTreap.intervalSearchExactly(node.getInterv());
				assertNotNull(exactNode);
				assertTrue(exactNode.getInterv().equals(node.getInterv()));
			}

			for (Interval i : largeNegatives) {
				Node exactNode = largeTreap.intervalSearchExactly(i);
				assertNull(exactNode);
			}
		}

		//
		// TEST CLASSES & METHODS
		//

		/**
		 * Tests minimum()
		 */
		//    @Test
		//    public void _testSmallIntervalTreapMinimum()
		//    {
		//        Node minimum = smallTreap.minimum();
		//        int min = smallTreap.getRoot().getLow();
		//        for (Node n : smallNodes)
		//        {
		//            assertTrue(n.getLow() >= minimum.getLow());
		//            min = Math.min(min, n.getLow());
		//        }
		//
		//        assertEquals(minimum.getLow(), min);
		//    }
		@Test
		public void _testInterval() {
			boolean thrown = false;
			Interval interval = new Interval(47, 138);
			Interval i1 = new Interval(9, 46);
			Interval i2 = new Interval(15, 47);
			Interval i3 = new Interval(130, 150);
			Interval i4 = new Interval(139, 155);
			Interval i5 = new Interval(139, 155);

			assertTrue(i4.equals(i5));

			assertEquals(47, interval.getLow());
			assertEquals(138, interval.getHigh());

			assertFalse(interval.overlaps(i1));
			assertTrue(interval.overlaps(i2));
			assertTrue(interval.overlaps(i3));
			assertFalse(interval.overlaps(i4));
		}

		/**
		 * Test node.
		 */
		@Test
		public void _testNode() {
			Interval i1 = new Interval(4, 5);
			Interval i2 = new Interval(3, 4);
			Interval i3 = new Interval(5, 6);

			Node n1 = new Node(i1);
			Node n2 = new Node(i2);
			Node n3 = new Node(i3);

			n1.setLeft(n2);
			n2.setParent(n1);
			n1.setRight(n3);
			n3.setParent(n1);

			assertNull(n1.getParent());
			assertEquals(n1, n2.getParent());
			assertEquals(n1, n3.getParent());
			assertEquals(n2, n1.getLeft());
			assertEquals(n3, n1.getRight());
			assertEquals(4, n1.getLow());
		}

		/**
		 * Test interval treap.
		 */
		@Test
		public void _testIntervalTreap() {
			Interval i1 = new Interval(4, 5);
			Interval i2 = new Interval(3, 4);
			Interval i3 = new Interval(5, 6);

			Node n1 = new Node(i1);
			Node n2 = new Node(i2);
			Node n3 = new Node(i3);

			IntervalTreap treap = new IntervalTreap();
			assertNull(treap.getRoot());

			treap.intervalInsert(n1);
			assertEquals(n1, treap.getRoot());
		}
	}

	/**
	 * Piazza post @176
	 *
	 * @see https://github.com/evmckinney9/CS311-Project-1-JUnit4-Test-Cases/blob/master/TestIntervalTreap.java
	 */
	public static class TestIntervalTreap {

		private IntervalTreap it1;
		private ArrayList<Interval> TP;
		private ArrayList<Interval> TN;
		private Scanner sc;

		/**
		 * Sets up.
		 */
		@BeforeEach
		public void setUp() {
			it1 = new IntervalTreap();
			TP = new ArrayList<>();
			TN = new ArrayList<>();
		}

		@AfterEach
		public void tearDown() {
			it1 = null;
			sc.close();
			sc = null;
			TP = null;
			TN = null;
		}

		@Test
		public void testSmall() {
			scanConstruct("small.txt");
			for (Interval i : TP) {
				assertNotNull(it1.intervalSearch(i));
			}
			for (Interval j : TN) {
				assertNull(it1.intervalSearch(j));
			}
			testTreapStructure(it1);
		}

		@Test
		public void testMedium() {
			scanConstruct("medium.txt");
			for (Interval i : TP) {
				assertNotNull(it1.intervalSearch(i));
			}
			for (Interval j : TN) {
				assertNull(it1.intervalSearch(j));
			}
			testTreapStructure(it1);
		}

		@Test
		public void testLarge() {
			scanConstruct("large_1.txt");
			for (Interval i : TP) {
				assertNotNull(it1.intervalSearch(i));
			}
			for (Interval j : TN) {
				assertNull(it1.intervalSearch(j));
			}
			testTreapStructure(it1);
		}

		private void testTreapStructure(IntervalTreap it0) {
			//Do an InOrder Traversal and append the nodes into an array
			ArrayList<Node> inOrder = new ArrayList<Node>();
			inOrder(it0.getRoot(), inOrder);

			//Check if the array is sorted. If it is not sorted, it's not a valid treap.
			for (int k = 0; k < inOrder.size() - 1; k++) {
				if (inOrder.get(k).getInterv().getLow() > inOrder.get(k + 1).getInterv().getLow()) {
					fail("failed treap's BST property!");
				}
			}
		}

		public void inOrder(Node node, ArrayList<Node> array) {
			if (node == null) {
				return;
			}
			inOrder(node.getLeft(), array);
			array.add(node);

			//As you visit each node, check for the heap property.
			if (node.getParent() != null && node.getPriority() < node.getParent().getPriority()) {
				fail("failed treap's min-heap property!");
			}

			inOrder(node.getRight(), array);
		}


		private void scanConstruct(String fn) {
			File f = new File(fn);
			String line;
			String[] split;
			try {
				sc = new Scanner(f);
				sc.nextLine(); //skip first line "TP"
				while (sc.hasNextLine()) {
					line = sc.nextLine();
					if (line.equals("TN")) {
						break;
					}
					split = line.split(" ");
					TP.add(new Interval(Integer.parseInt(split[0]), Integer.parseInt(split[1])));
				}
				while (sc.hasNextLine()) {
					line = sc.nextLine();
					if (line.equals("Intervals")) {
						break;
					}
					split = line.split(" ");
					TN.add(new Interval(Integer.parseInt(split[0]), Integer.parseInt(split[1])));
				}
				while (sc.hasNextLine()) {
					line = sc.nextLine();
					split = line.split(" ");
					it1.intervalInsert(new Node(new Interval(Integer.parseInt(split[0]),
							Integer.parseInt(split[1])
					)));
				}
			} catch (FileNotFoundException e) {
				fail("File not found exception");
			}

		}
	}
}