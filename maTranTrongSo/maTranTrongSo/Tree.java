package maTranTrongSo;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

public class Tree extends UnDirectedGraph {

	public Tree(int[][] weightArr) {
		super(weightArr);
		// TODO Auto-generated constructor stub
	}

	// ------------------do lech tam-------------------
	// DFS
	public int getEccDFS(int i) { // do lech tam
		int eccentricity = 0; // do lech tam ban dau dc gan = 0
		int max = 0; // luu lai do lech tam lon nhat cua dinh
		int[] visted = new int[weightArr.length];
		eccDFS(i, visted, eccentricity, max);
		return max;
	}

	public void eccDFS(int i, int[] visted, int eccentricity, int max) {
		visted[i] = 1;
		for (int j = 0; j < weightArr.length; j++) {
			if (weightArr[i][j] < VCL && visted[j] != 1) {
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
		int[] visted = new int[weightArr.length];
		Queue<Integer> queue = new LinkedList<>();
		queue.offer(i);
		eccBFS(queue, visted, weightArr, eccentricity);
//			System.out.println(Arrays.toString(visted));
		return eccentricity;
	}

	public void eccBFS(Queue<Integer> queue, int[] visted, int[][] matrix, int eccentricity) {
		int temp = queue.poll();
		visted[temp] = 1;

		// tim cac phan tu con cua temp chua dc tham
		for (int j = 0; j < matrix.length; j++) {
			if (matrix[temp][j] < VCL && visted[j] != 1) {

				visted[j] = 1;
				queue.offer(j);
			}
		}
		eccentricity++;

		if (!queue.isEmpty())
			eccBFS(queue, visted, matrix, eccentricity);

	}

	// -----------------------getRoot-------------------------------
	public ArrayList<Integer> getRoot() { // tim tam cua Tree, co the co den 2 tam
		ArrayList<Integer> result = new ArrayList<>();
		int min = Integer.MAX_VALUE;
		for (int i = 0; i < weightArr.length; i++) {
			if (getEccBFS(i) < min) {
				// neu dieu nay xay ra thi mang ket qua o vi tri
				// 0 se dc do day , va o vi tri 1 se = null
				result.set(0, i);
				min = getEccBFS(min);
				result.set(1, null);
			} else if (getEccBFS(i) == min) {
				// neu nhu dieu nay xay ra thi co nghia la "Tree" co 2 tam
				result.set(1, i);
			}
		}
		return result;
	}
	public int getRadiusOfTree() {
		// co the co 2 tam nhung chung se co cung ban kinh
		return getRoot().get(0);
	}

}
