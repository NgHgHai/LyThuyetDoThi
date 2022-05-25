package maTranKe;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class Tree extends UnDirectedGraph {

	public Tree(int[][] matrix) {
		super(matrix);
		// TODO Auto-generated constructor stub
	}

	// ------------------do lech tam-------------------
	// DFS
	public int getEccDFS(int i) { // do lech tam
		int eccentricity = 0;
		int max = 0;
		int[] visted = new int[lengthOfMatrix];
		eccDFS(i, visted, eccentricity, max);
		return max;
	}

	public void eccDFS(int i, int[] visted, int eccentricity, int max) {
		visted[i] = 1;
		for (int j = 0; j < matrix.length; j++) {
			if (matrix[i][j] >= 1 && visted[j] != 1) {
				eccentricity++;
				if (eccentricity > max) {
					max = eccentricity;
				}
				eccDFS(j, visted, eccentricity, max);
				eccentricity--;
			}
		}
	}

	// BFS
	public int getEccBFS(int i) {
		int eccentricity = 0;
		int[] visted = new int[lengthOfMatrix];
		Queue<Integer> queue = new LinkedList<>();
		queue.offer(i);
		eccBFS(queue, visted, matrix, eccentricity);
//		System.out.println(Arrays.toString(visted));
		return eccentricity;
	}

	public void eccBFS(Queue<Integer> queue, int[] visted, int[][] matrix, int eccentricity) {
		int temp = queue.poll();
		visted[temp] = 1;

		// tim cac phan tu con cua temp chua dc tham
		for (int j = 0; j < matrix.length; j++) {
			if (matrix[temp][j] >= 1 && visted[j] != 1) {

				visted[j] = 1;
				queue.offer(j);
			}
		}
		eccentricity++;

		if (!queue.isEmpty())
			eccBFS(queue, visted, matrix, eccentricity);

	}
	// -----------------------getRoot-------------------------------
	public ArrayList<Integer> getRoot() {
		ArrayList<Integer> result = new ArrayList<>();
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < matrix.length; i++) {
			if (getEccBFS(i) < min) {
				result.set(0, i)   ;
				min = getEccBFS(min);
				result.set(1, null);
			} else if (getEccBFS(i) == min) {
				result.set(1, i);
			}
		}
		return result;
	}

}
