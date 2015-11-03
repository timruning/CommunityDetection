package sf.Community.date;
public class Graph {
	public int[][] nodes={{1,2},{0,2},{0,1,3},{2,4},{3,5,6},{4,7},{4,7},{6,5}};
	public int getDegrees(){
		int degree=0;
		for(int i=0;i<nodes.length;i++){
			degree+=nodes[i].length;
		}
		return degree;
	}
	public int getNodeNum(){
		return nodes.length;
	}
	public double getNodeSimilarity(int node1,int node2){
		double similarity=0;
		int count=0;
		for(int i=0;i<nodes[node1].length;i++){
			for(int j=0;j<nodes[node2].length;j++){
				if(nodes[node1][i]==nodes[node2][j]){
					count++;
				}
			}
		}
		double t=(double)nodes[node1].length*nodes[node2].length;
		Math.sqrt(t);
		similarity=(double)count/t;
		return similarity;
	}
	public boolean nodeConnec(int node1,int node2) {
		for(int i:nodes[node1]){
			if(i==node2){
				return true;
			}
		}
		return false;
	}
}
