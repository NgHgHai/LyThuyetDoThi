package maTranTrongSo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.PriorityQueue;

import javax.swing.event.TreeExpansionEvent;

import maTranKe.Tree;

public class UnDirectedGraph extends Graph {
	final static int VCL = Integer.MAX_VALUE;// VCL = 2147483647
	int[][] weightArr;

	public UnDirectedGraph(String path) {
		// khoi tao dung file ben ngoai, yeu cau file ben ngoai co VCL = 2147483647
		weightArr = loadGraph.LoadGraph.loadData(path);
	}

	public UnDirectedGraph(int[][] weightArr) {
		// khoi tao = truyen vao 1 do thi trong so la 2 chieu
		this.weightArr = weightArr;
	}

	public void fillWeight(int[][] arr) {
		// do day mang 2 chieu bang VCL
		for (int i = 0; i < arr.length; i++) {
			for (int j = 0; j < arr.length; j++) {
				arr[i][j] = VCL;
			}
		}
	}

	@Override
	public void addEdge(Edge e) {
		// TODO Auto-generated method stub
		weightArr[e.getStart()][e.getDest()] = e.getWeightValue();
		weightArr[e.getDest()][e.getStart()] = e.getWeightValue();
	}

	@Override
	public void removeEdge(Edge e) {
		// TODO Auto-generated method stub
		weightArr[e.getStart()][e.getDest()] = VCL;
		weightArr[e.getDest()][e.getStart()] = VCL;
	}

	@Override
	public int degree(Integer u) {
		// tinh bac cua dinh u
		int count = 0;
		for (int i = 0; i < weightArr.length; i++) {
			if (weightArr[u][i] != VCL) {
				count++;
			}
		}
		return count;
	}

	@Override
	public int numOfEdge() {
		// TODO Auto-generated method stub
		int count = 0;
		for (int i = 0; i < weightArr.length; i++) {
			for (int j = i; j < weightArr.length; j++) {

				if (weightArr[i][j] != VCL) {
					count++;
				}
			}
		}
		return count;
	}

	@Override
	public boolean isComponent() {
		// TODO Auto-generated method stub
		if (countComponent() == 1) {
			return true;
		}
		return false;
	}

	private int countComponent() { // dem so thanh phan lien thong
		int count = 0;
		int[] visted = new int[weightArr.length];
		for (int i = 0; i < visted.length; i++) {
			if (visted[i] == 0) {
				DFS(i, visted);
				count++;
			}
		}
		return count;
	}

	public ArrayList<Integer> repareDFS(int i) {
		// duyet do thi theo dfs bat dau tu i
		ArrayList<Integer> listVisted = new ArrayList<>();
		int[] visted = new int[weightArr.length];
		DFS(i, listVisted, visted);
		return listVisted;
	}

	public void DFS(int i, ArrayList<Integer> listVisted, int[] visted) {
		// phuong thuc chuan bi
		visted[i] = 1;
		listVisted.add(i);
		for (int j = 0; j < weightArr.length; j++) {
			if (weightArr[i][j] < VCL && visted[j] != 1) {
				DFS(j, listVisted, visted);
			}
		}
	}

	public void DFS(int i, int[] visted) {
		// phuong thuc chuan bi
		visted[i] = 1;
		for (int j = 0; j < weightArr.length; j++) {
			if (weightArr[i][j] < VCL && visted[j] != 1) {
				DFS(j, visted);
			}
		}
	}

	public boolean hasPathBetweenTwoVertices(int x, int y, int[][] matrix) {
		ArrayList<Integer> arr = repareDFS(x);
		return arr.contains(y);
	}

	@Override
	public boolean isTree() {
		// kiem tra xem do thi co phai la 1 cay khong
		if (isComponent() && weightArr.length - 1 == numOfEdge()) {
			// do thi lien thong && so dinh -1 = so canh
			return true;
		}
		return false;
	}

	public Tree getMimimunTreeWithKRUSKAL() {
		int countEdge = 0;
		int[][] MTtree = new int[weightArr.length][weightArr.length];
		fillWeight(MTtree);// do day mang MTtree bang VCL
//		PriorityQueue<Edge> edges = new PriorityQueue<>();
		PriorityQueue<Edge> edges = new PriorityQueue<>(new Comparator<Edge>() {

			@Override
			public int compare(Edge o1, Edge o2) {
				// TODO Auto-generated method stub
				return o1.getWeightValue() - o2.getWeightValue();
			}
		});
		for (int i = 0; i < MTtree.length; i++) {
			for (int j = i; j < MTtree.length; j++) {
				if (weightArr[i][j] < VCL) {
					edges.add(new Edge(i, j, weightArr[i][j]));
				}
			}
		}
		while (countEdge < weightArr.length - 1) {
			Edge e = edges.poll();
			if (!hasPathBetweenTwoVertices(e.getStart(), e.getDest(), MTtree)) {
				MTtree[e.getStart()][e.getDest()] = weightArr[e.getStart()][e.getDest()];
				MTtree[e.getDest()][e.getStart()] = weightArr[e.getDest()][e.getStart()];
				countEdge++;
				removeEdge(e);
			} else {
				removeEdge(e);
			}
		}

		return new Tree(MTtree);
	}

	public Tree getMimimunTreeWithPRIM(int begin) {
		int[][] MTtree = new int[weightArr.length][weightArr.length];
		int[] visted = new int[weightArr.length];
		ArrayList<Integer> vertices = new ArrayList<>();
		vertices.add(begin);
		PriorityQueue<Edge> queue = new PriorityQueue<>(new Comparator<Edge>() {
			@Override
			public int compare(Edge o1, Edge o2) {
				// TODO Auto-generated method stub
				return o1.getWeightValue() - o2.getWeightValue();
			}
		});
		// them edge cho queue
		addNewQueue(queue, vertices);
		while (!queue.isEmpty()) {
			Edge temp = queue.poll();// lay ra 1 canh tu queue sau do
			if (!vertices.contains(temp.getDest())) {
				// kiem tra xem 2 dinh cua canh do co thuoc vertices khong
				// neu vertices khong chua dinh (dest) thi them canh do vao MTtree
				MTtree[temp.getStart()][temp.getDest()] = weightArr[temp.getStart()][temp.getDest()];
				MTtree[temp.getDest()][temp.getStart()] = weightArr[temp.getDest()][temp.getStart()];
				vertices.add(temp.getDest());
				// them edge cho queue
				addNewQueue(queue, vertices);
				// sau khi them 1 dinh moi vao vertice thi phai them tat ca cac canh lien
				// thuoc dinh do vao queue
			}
		}
		return new Tree(MTtree);

	}

	private void addNewQueue(PriorityQueue<Edge> queue, ArrayList<Integer> vertices) {
		// them cac canh lien thuoc (cua dinh moi add vao vetices) vao queue
		for (int i = 0; i < weightArr.length; i++) {
			if (weightArr[vertices.get(vertices.size() - 1)][i] < VCL) {
				if (!vertices.contains(i)) { // neu nhu vertices da chua i roi thi khong can them vao
					Edge e = new Edge(vertices.get(vertices.size() - 1), i,
							weightArr[vertices.get(vertices.size() - 1)][i]);
					queue.add(e);
				}
			}
		}
	}

	public static void main(String[] args) {
//		System.out.println(Integer.MAX_VALUE);
	}

}
