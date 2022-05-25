package maTranLienThuoc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class UnDirectedGraph extends Graph{
	Map<Integer, List<Integer>> adjList = null;
  public UnDirectedGraph() {
	// TODO Auto-generated constructor stub
	  adjList = new HashMap<Integer, List<Integer>>();
}
	@Override
	public void addEdge(Integer u, Integer v) {
		// TODO Auto-generated method stub
		if(!adjList.containsKey(u)) {
			adjList.put(u, new ArrayList<Integer>());
		}
		if(!adjList.containsKey(v)) {
			adjList.put(v, new ArrayList<Integer>());
		}
		if(!adjList.get(u).contains(v)) {
			adjList.get(u).add(v);
		}
		if(!adjList.get(v).contains(u)) {
			adjList.get(v).add(u);
		}
	}

	@Override
	public void removeEdge(Integer u, Integer v) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void printAdjList() {
		// TODO Auto-generated method stub
		Set<Integer> keyset = adjList.keySet();
		for (Integer key : keyset) {
			System.out.println(key+":"+ adjList.get(key));
		}
	}
	public int degree(Integer u) {
		// TODO Auto-generated method stub
		return adjList.get(u).size();
	}
	
	@Override
	public int numOfEdge() {
		// TODO Auto-generated method stub
		int sum = 0;
		Set<Integer> setkey = adjList.keySet();
		for(Integer key : setkey) {
			sum += adjList.get(key).size();
		}
		return sum/2;
	}
	public static void main(String[] args) {
		UnDirectedGraph G1 = new UnDirectedGraph();
		G1.addEdge(1, 2);
		G1.addEdge(1, 3);
		G1.addEdge(1, 4);
		G1.addEdge(2, 1);
		G1.addEdge(2, 3);
		G1.addEdge(2, 4);
		G1.addEdge(3, 1);
		G1.addEdge(3, 2);
		G1.addEdge(3, 5);
		G1.addEdge(5, 3);
		G1.addEdge(4, 1);
		G1.addEdge(2, 5);
		G1.printAdjList();
		System.out.println(G1.numOfEdge());
	}


}
