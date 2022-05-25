package maTran1;

import java.util.*;

public class UnDirectedGraph {
	int[][] adjMatrix = null;

	public UnDirectedGraph(int n) {
		adjMatrix = new int[n][n];
	}

	public void addEdge(int u, int v) {
		adjMatrix[u][v]++;
		adjMatrix[v][u]++;

	}

	public void removeEdge(int u, int v) {
		if (adjMatrix[u][v] > 0) {
			adjMatrix[u][v]--;
			adjMatrix[v][u]--;
		}
	}

	public int degree(int v) {

		return 0;
	}

	public boolean BF_Search(int start) {
		Queue<Integer> queue = new LinkedList<>();
		boolean visited[] = new boolean[adjMatrix.length];

		queue.add(start);
		visited[start] = true;

		while (!queue.isEmpty()) {
			int v = queue.poll();
			for (int i = 0; i < adjMatrix.length; i++) {
				if (adjMatrix[v][i] > 0 && !visited[i]) {
					visited[i] = true;
					queue.add(i);
				}
			}
		}
		boolean result = true;
		for (int i = 0; i < visited.length; i++) {
			if (!visited[i]) {
				result = false;
			}
		}
		return result;
	}

	public boolean DF_Search(int start,boolean[] visited ) {
		Stack<Integer> stack = new Stack<>();
//		boolean visited[] = new boolean[adjMatrix.length];
		stack.push(start);
		visited[start] = true;

		while (!stack.isEmpty()) {
			int v = stack.peek();
			for (int i = 0; i < adjMatrix.length; i++) {
				if (adjMatrix[v][i] > 0 && !visited[i]) {
					stack.add(i);
					visited[i] = true;
					break;
				}
				if (i == adjMatrix.length - 1) {
					stack.pop();
				}
			}
		}
		boolean result = true;
//		for (int i = 0; i < visited.length; i++) {
//			if (!visited[i]) {
//				result = false;
//			}
//		}
		return result;
	}
	public boolean findPath(int x, int y) {
		boolean visited[] = new boolean[adjMatrix.length];
		DF_Search(x, visited);
		if(visited[y]) return true;
		
		return false;
		
	}
	public boolean kt_dinhDaDuocDuyet(boolean[] v ) {
		// neu do thi duoc duyet het thi tra ve true, con dinh chua duyet thi tra  ve false
		for (int i = 0; i < v.length; i++) {
			if(v[i]== false) return false;
		}
		return true;
		
	}
	public int soTPLienThong() {
		int result = 0;
		
		boolean visited[] = new boolean[adjMatrix.length];
//		ArrayList<Boolean> visitArr = new ArrayList<>(adjMatrix.length);
//		visitArr.
		while(kt_dinhDaDuocDuyet(visited) == false) {
//			while(visitArr.contains(false)) {
			for (int i = 0; i < visited.length; i++) {
				if(visited[i] = false) {
					if(DF_Search(i, visited))
					result++;
				}
			}	
		}
		return result;
	}
	public static void main(String[] args) {
		
	}
	
}
