package maTranTrongSo;

public abstract class Graph {
	 public abstract void addEdge(Edge e);
	 public abstract void removeEdge(Edge e);
	 public abstract int degree(Integer u);
	 public abstract int numOfEdge();
	 public abstract boolean isComponent();
	 public abstract boolean isTree();
}
