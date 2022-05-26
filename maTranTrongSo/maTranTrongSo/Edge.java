package maTranTrongSo;

public class Edge implements Comparable<Edge> {
private int start;
private int dest;
private int weightValue;

public Edge(int start, int dest, int weightValue) {
	super();
	this.start = start;
	this.dest = dest;
	this.weightValue = weightValue;
}
public int getStart() {
	return start;
}
public void setStart(int start) {
	this.start = start;
}
public int getDest() {
	return dest;
}
public void setDest(int taget) {
	this.dest = taget;
}
public int getWeightValue() {
	return weightValue;
}
public void setWeightValue(int weightValue) {
	this.weightValue = weightValue;
}
@Override
public int compareTo(Edge o) {
	// TODO Auto-generated method stub
	return this.weightValue - o.getWeightValue();
}



}
