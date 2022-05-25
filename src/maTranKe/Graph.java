package maTranKe;

public abstract class Graph {
	
	 public abstract void addEdge(int u,int v);
	 public abstract void removeEdge(int u, int v);
	 public abstract void printAdjList();
	 public abstract int degree(Integer u);
	 public abstract int numOfEdge();
}
