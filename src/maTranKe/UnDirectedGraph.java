package maTranKe;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

import javax.imageio.plugins.tiff.ExifGPSTagSet;

import loadGraph.LoadGraph;

public class UnDirectedGraph extends Graph {
	int[][] matrix;
	int lengthOfMatrix = 0;

	public UnDirectedGraph(int[][] matrix) {
		super();
		this.matrix = matrix;
	}

//------------------------DFS--------------------------
	public ArrayList<Integer> repareDFS(int i, int[][] matrix) {
		ArrayList<Integer> listVisted = new ArrayList<>();
		int[] visted = new int[lengthOfMatrix];
		DFS(i, listVisted, visted, matrix);
		return listVisted;
	}

	public void DFS(int i, ArrayList<Integer> listVisted, int[] visted, int[][] matrix) {
		visted[i] = 1;
		listVisted.add(i);
		for (int j = 0; j < matrix.length; j++) {
			if (matrix[i][j] >= 1 && visted[j] != 1) {

				DFS(j, listVisted, visted, matrix);

			}
		}
	}

	public void printPathDFS(int k, int[][] matrix) {
		ArrayList<Integer> arr = repareDFS(k, matrix);
		System.out.print("duyet do thi BFS: ");
		for (int i = 0; i < arr.size(); i++) {
			System.out.print(arr.get(i) + " ==>  ");
		}
		System.out.println();
	}

//---------------------------------------------------------
//------------------------BFS------------------------------
	public ArrayList<Integer> repareBFS(int i, int[][] matrix) {
		ArrayList<Integer> path = new ArrayList<>();

		int[] visted = new int[lengthOfMatrix];
		Queue<Integer> queue = new LinkedList<>();
		queue.offer(i);
		path.add(i);
		BFS(queue, path, visted, matrix);
//		System.out.println(Arrays.toString(visted));

		return path;

	}

	public void BFS(Queue<Integer> queue, ArrayList<Integer> path, int[] visted, int[][] matrix) {
		int temp = queue.poll();
		visted[temp] = 1;

		// tim cac phan tu con cua temp chua dc tham
		for (int j = 0; j < matrix.length; j++) {
			if (matrix[temp][j] >= 1 && visted[j] != 1) {
				path.add(j);
				visted[j] = 1;
				queue.offer(j);
			}
		}
		if (!queue.isEmpty())
			BFS(queue, path, visted, matrix);

	}

	public void printPathBFS(int k, int[][] matrix) {
		ArrayList<Integer> arr = repareBFS(k, matrix);
		System.out.print("duyet do thi BFS: ");
		for (int i = 0; i < arr.size(); i++) {
			System.out.print(arr.get(i) + " ==>  ");
		}
		System.out.println();
	}

//----------------------------------------------------------
//--------------------duong di giua 2 dinh------------------

	public void printPathBetweenTwoVertices(int x, int y, int[][] matrix) {
		ArrayList<Integer> arr = pathBetweenTwoVertices(x, y, matrix);
		if (arr == null) {
			System.out.println(" khong co duong di giua 2 dinh");
		} else {
			System.out.print("Duong di giua 2 dinh: " + x + " toi " + y + " la ");
			for (int i = 0; i < arr.size(); i++) {
				System.out.print(arr.get(i) + " ==>  ");
			}
			System.out.println();
		}
	}

	public boolean hasPathBetweenTwoVertices(int x, int y, int[][] matrix) {
		ArrayList<Integer> arr = repareDFS(x, matrix);
		return arr.contains(y);
	}

	public ArrayList<Integer> pathBetweenTwoVertices(int x, int y, int[][] matrix) {
		ArrayList<Integer> arr = new ArrayList<>();
		Stack<Integer> stack = new Stack<>();
		int[] visted = new int[lengthOfMatrix];
		if (!hasPathBetweenTwoVertices(x, y, matrix)) {
			return null;
		}
		findPathDFS(x, y, stack, visted, matrix);
		arr.addAll(stack);
		return arr;

	}

	public void findPathDFS(int x, int y, Stack<Integer> stack, int[] visted, int[][] matrix) {
		stack.push(x);
		visted[x] = 1;
		if (!stack.contains(y)) {
			for (int j = 0; j < matrix.length; j++) {
				if (matrix[x][j] >= 1 && visted[j] != 1) {
					findPathDFS(j, y, stack, visted, matrix);// *
					if (!stack.contains(y)) {
						stack.pop();
					} else
						return;
				}
			}
		}
	}

	// ----------------------------------------------------------
	// ----------------------Thanh phan lien thong---------------
	public boolean isComponent() { // kiem tra xem do thi co lien thong hay khong
		if (countComponent() == 1) {
			return true;
		}
		return false;
	}

	public int countComponent() { // dem so thanh phan lien thong
		int count = 0;
		ArrayList<Integer> listVisted = new ArrayList<>();
		int[] visted = new int[lengthOfMatrix];
		for (int i = 0; i < visted.length; i++) {
			if (visted[i] == 0) {
				DFS(i, listVisted, visted, this.matrix);
				count++;
			}
		}
		return count;
	}
	// ----------------------------------------------------------

	// -------------------Euler----------------------------------
	public boolean isEuler() {
		if (!isComponent()) // KT lien thong
			return false;
		for (int i = 0; i < matrix.length; i++) {
			if (degree(i) % 2 == 1) {
				return false;
			}
		}
		return true;
	}

	public boolean isHalf_Euler() {
		int check = 0;
		if (!isComponent()) // KT lien thong
			return false;

		for (int i = 0; i < matrix.length; i++) {
			if (degree(i) % 2 == 1) {
				check++;
			}
		}
		if (check == 2) { // co dung 2 dinh bac le => nua Euler
			return true;
		}
		return false;
	}

	public int[][] clone(int[][] matrix) {
		// sao chep mang matrix[][] sang mang clone[][] de dung thuat toan
		int[][] clone = new int[lengthOfMatrix][lengthOfMatrix];
		// * phai tao ra mang moi, khong duoc dung "int[][] clone = matrix;"
		for (int i = 0; i < clone.length; i++) {
			for (int j = 0; j < clone.length; j++) {
				clone[i][j] = matrix[i][j]; // sao chep
			}
		}
		return clone;
	}

	public boolean isDeadEdge(int u, int v, int[][] matrix) {
		/*
		 * kiem tra 2 canh(u,v) co phai la duong duy nhat noi 2 dinh hay khong bang cach
		 * xoa canh(u,v), sau do duyet do thi : +neu van con duong di => return false
		 * +neu khong con duong di nao khac => return true
		 */
		int[][] cloneMatrix1 = clone(matrix);
		removeEdge(u, v, cloneMatrix1);// xoa canh(u,v) tren mang clone khong anh huong den mang chinh
		if (hasPathBetweenTwoVertices(v, u, cloneMatrix1)) {
			return false;
		}
		return true;
	}

	public ArrayList<Integer> cycleEuler(int i) { // * i la dinh bat dau
		// giai thuat Fleury tim chu trinh tren do thi
		if (!isEuler()) {
			System.out.println(" khong phai Euler !");
			return null;
		}

		int[][] cloneMatrix = clone(matrix);
		int cur_vertices = i;
		ArrayList<Integer> path1 = new ArrayList<Integer>();
		path1.add(i);

		while (numOfEdge(cloneMatrix) != 0) { // tiep tuc cho den khi so canh = 0
			for (int j = 0; j < cloneMatrix.length; j++) {
				if (cloneMatrix[cur_vertices][j] >= 1) { // KT dinh ke
					// chi su dung "DeadEdge" khi khong con canh nao khac
					if (isDeadEdge(cur_vertices, j, cloneMatrix) && degree(i, cloneMatrix) == 1) {
						removeEdge(cur_vertices, j, cloneMatrix);
						path1.add(j);
						cur_vertices = j;
					} else { // them canh khong phai "DeadEdge"
						removeEdge(cur_vertices, j, cloneMatrix);
						path1.add(j);
						cur_vertices = j;
					}
				}
			}
		}
		return path1;
	}

	public void printCycleEuler(int i) { // in ra chu trinh
		ArrayList<Integer> arr = cycleEuler(i);
		if (arr == null) {
			System.out.println("do thi khong co chu trinh euler");
		} else {
			System.out.print("Chu trinh: ");
			for (int j = 0; j < arr.size(); j++) {
				System.out.print(arr.get(j) + " ==>  ");
			}
			System.out.println();
		}
	}

	public ArrayList<Integer> wayOfEuler() {// tim duong di Euler
		int begin = 0;
		if (isHalf_Euler()) {
			begin = getOdd_Vertices();
		} else
			return null;
		ArrayList<Integer> way = cycleEuler(begin);
		return way;
	}

	private int getOdd_Vertices() {
		// TODO Auto-generated method stub
		for (int i = 0; i < matrix.length; i++) {
			if (degree(i) % 2 == 1) {
				return i;
			}
		}
		return 0;
	}

	// -------------------is Tree -----------------------------------
	public boolean isTree() {
		if (isComponent() && lengthOfMatrix - 1 == numOfEdge()) {
			return true;
		}
		return false;
	}

	public Tree getSpanningTreeDFS(int i) {
		if (!isComponent())
			return null;
		int[][] MTtree = new int[lengthOfMatrix][lengthOfMatrix];
		int[] visted = new int[lengthOfMatrix];
		Stack<Integer> stack = new Stack<>();
		int v = 0;
		stack.push(i);

		while (!stack.isEmpty()) {
			v = stack.pop();
			if (visted[v] == 1)
				continue;
			visted[v] = 1;
			for (int j = 0; j < matrix.length; j++) {
				if (matrix[v][j] >= 1 && visted[j] != 1) {
					stack.push(j);
					MTtree[v][j] = 1;// luu vao tree
					MTtree[j][v] = 1;
				}
			}
		}

		return new Tree(MTtree);
	}

	public Tree getSpanningTreeBFS(int i) {
		if (!isComponent())
			return null;
		int[][] MTtree = new int[lengthOfMatrix][lengthOfMatrix];
		int[] visted = new int[lengthOfMatrix];
		Queue<Integer> queue = new LinkedList<Integer>();
		int v = 0;
		queue.add(i);
		while (!queue.isEmpty()) {
			v = queue.poll();
			visted[v] = 1;
			for (int j = 0; j < matrix.length; j++) {
				if (matrix[v][j] >= 1 && visted[j] != 1) {
					visted[j] = 1;
					MTtree[v][j] = 1;// luu vao tree
					MTtree[j][v] = 1;
					queue.offer(j);
				}
			}
		}
		return new Tree(MTtree);
	}

	// -------------------weight Tree -----------------------------------
	public Tree getTreeWithKRUSKAL(int[][] weightMT) {
		// khong su dung class edge
		int edge = 0;
		int[][] MTtree = new int[lengthOfMatrix][lengthOfMatrix];
		while (edge < lengthOfMatrix - 1) {// luu y co the la lengthOfMatrix -2
			int a[] = getMin(weightMT);
			if (!hasPathBetweenTwoVertices(a[0], a[1], MTtree)) {
				addEdge(a[0], a[1], MTtree);
				edge++;// khi so canh = so dinh -1 thi dung
				upMaxEdge(a[0], a[1], weightMT);
			} else
				upMaxEdge(a[0], a[1], weightMT);

		}
		return new Tree(MTtree);
	}

	private void upMaxEdge(int i, int j, int[][] weightMT) {
		// xoa trong so khoi ma tran bang cach tang trong so len MAX
		weightMT[i][j] = Integer.MAX_VALUE;
		weightMT[j][i] = Integer.MAX_VALUE;

	}

	private int[] getMin(int[][] weightMT) {
		// Tra ve 2 dinh thuoc canh co trong so nho nhat
		int[] a = new int[2];
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < weightMT.length; i++) {
			for (int j = i; j < weightMT.length; j++) {
				if (weightMT[i][j] < min) {
					min = weightMT[i][j];
					a[0] = i;
					a[1] = j;
				}
			}
		}
		return a;
	}

//	public Tree getTreeWithPRIM(int i,int[][]weightMT ) {
//		int ver = 0;
//		int[][] MTtree = new int[lengthOfMatrix][lengthOfMatrix];
//		while (ver < lengthOfMatrix) {
//			// lay ra ma tran trong so cua cac dinh ke voi MTtree
//			int[][] mtOfWeight = getMTOfWeight(MTtree, weightMT);
//			int[] a = getMin(mtOfWeight);
//			addEdge(a[0], a[1], MTtree);
//
//		}
//		return new Tree(MTtree);
//
//	}
//
//	private int[][] getMTOfWeight(int[][] MTtree, int[][] weightMT, int start) {
//		// TODO Auto-generated method stub
//		int[][] result = new int[lengthOfMatrix][lengthOfMatrix];
//		// do day mang ket qua bang gia tri cuc dai
//		for (int i = 0; i < result.length; i++) {
//			for (int j = 0; j < result.length; j++) {
//				result[i][j] = Integer.MAX_VALUE;
//			}
//		}
//		//
//
//		return null;
//	}
	// -------------------Loading Graph-----------------------------------

	public boolean loadData(String s) {
		try {
			matrix = LoadGraph.loadData(s);
			lengthOfMatrix = matrix.length;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
		return true;

	}

	public void printMatrix(int[][] matrix) {
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				System.out.print(matrix[i][j] + " ");
			}
			System.out.println();
		}
	}

	public void printMatrix() {
		printMatrix(this.matrix);
	}

	public void addEdge(int u, int v, int[][] matrix) {
		// TODO Auto-generated method stub
		matrix[u][v]++;
		matrix[v][u]++;
	}

	@Override
	public void addEdge(int u, int v) {
		// TODO Auto-generated method stub
		addEdge(u, v, this.matrix);
	}

	public void removeEdge(int u, int v, int[][] matrix) {
		// TODO Auto-generated method stub
		matrix[u][v]--;
		matrix[v][u]--;

	}

	@Override
	public void removeEdge(int u, int v) {
		// TODO Auto-generated method stub
		removeEdge(u, v, this.matrix);
	}

	@Override
	public void printAdjList() {
		// TODO Auto-generated method stub
		printMatrix();

	}

	public int degree(Integer u, int[][] matrix) {
		// TODO Auto-generated method stub
		int count = 0;
		for (int i = 0; i < matrix.length; i++) {
			count += matrix[u][i];
		}
		return count;
	}

	@Override
	public int degree(Integer u) {
		return degree(u, this.matrix);
	}

	public int numOfEdge(int[][] matrix) {
		// TODO Auto-generated method stub
		int sumDegree = 0;
		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix.length; j++) {
				sumDegree += matrix[i][j];
			}
		}
		return sumDegree / 2;
	}

	@Override
	public int numOfEdge() {
		// TODO Auto-generated method stub

		return numOfEdge(this.matrix);
	}

	public static void main(String[] args) {
		UnDirectedGraph directedGraph = new UnDirectedGraph(null);
		directedGraph.loadData("euler.txt");
		directedGraph.printMatrix();
		int[][] matrix = directedGraph.matrix;

		System.out.println("do thi lien thong ? :" + directedGraph.isComponent());
//		directedGraph.DFS(0);
		directedGraph.printPathDFS(0, matrix);
// 		co duong di giua 2 dinh
		boolean b = directedGraph.hasPathBetweenTwoVertices(0, 3, matrix);
		System.out.println(b);

		directedGraph.printPathBetweenTwoVertices(0, 3, matrix);
		System.out.println("so thanh phan lien thong :" + directedGraph.countComponent());

		System.out.println("co phai do thi euler? :" + directedGraph.isEuler());
		System.out.println("co phai do thi nua euler? :" + directedGraph.isHalf_Euler());

		directedGraph.printCycleEuler(0);
//		directedGraph.cycleEuler(0);

	}

}
