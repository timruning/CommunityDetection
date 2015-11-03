package sf.Community.date;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Result {
	public Integer[][] result;
	public int hashCode() {
		int t=0;
		for(int i=0;i<result[0].length;i++)
			t+=result[0][i];
		return t;
	}
	public boolean equals(Object o) {
		Result t=(Result)o;
		for(int i=0;i<result[0].length;i++){
			if(result[0][i]!=t.result[0][i])
				return false;
		}
		return true;
	}
	public Result() {
		// TODO Auto-generated constructor stub
	}
	public Result(Integer[][] a) {
		// TODO Auto-generated constructor stub
		this.result=a;
	}
	
	public Map<Integer, Set<Integer>> toCommunity(){
		Map<Integer, Set<Integer>> anwser = new HashMap<Integer,Set<Integer>>();
		for(int i=0;i<result[0].length;i++){
			if(anwser.containsKey(result[0][i])){
				anwser.get(result[0][i]).add(i);
			}else{
				Set<Integer> set=new HashSet<Integer>();
				set.add(i);
				anwser.put(result[0][i], set);
			}
		}
		return anwser;
	}
	public double getModularity(Graph graph){
		double modularity=0;
		int degree=graph.getDegrees();
		Map<Integer, Set<Integer>> communities=this.toCommunity();
		for(Object object:communities.keySet()){
			Set<Integer> nodes=communities.get(object);
			double modularityi=0;
			int comDegree=0;
			int internalLine=0;
			for(Integer iobject:nodes){
				comDegree+=graph.nodes[iobject].length;
				for(int igr=0;igr<graph.nodes[iobject].length;igr++){
					if(nodes.contains(graph.nodes[iobject][igr])){
						internalLine++;
					}
				}
			}
			double first=(double)internalLine/degree;
			double second=(double)comDegree/degree;
			modularityi=first-second*second;
			modularity+=modularityi;
		}
		return modularity;
	}
	
	public double getComSimilarity(Graph graph,Integer node1,Integer com){
		double comSimilarity=0;
		Map<Integer, Set<Integer>> communities=this.toCommunity();
		for(Integer node2:communities.get(com)){
			comSimilarity+=graph.getNodeSimilarity(node1, node2);
		}
		return comSimilarity;
	}
	public Set<Integer> comNeighber(Graph graph,Integer com) {
		Set<Integer> setNode=new HashSet<Integer>();
		Map<Integer, Set<Integer>> communities=this.toCommunity();
		Set<Integer> comNodes=new HashSet<Integer>();
		if(communities.containsKey(com));
			comNodes=communities.get(com);
		if(comNodes!=null){
			for(Integer node:comNodes){
				for(int i=0;i<graph.nodes[node].length;i++){
					if(!comNodes.contains((Integer)graph.nodes[node][i])){
						setNode.add((Integer)graph.nodes[node][i]);
					}
				}
			}
		}
		return setNode;
	}
	public double getComTightness(Graph graph,Integer com){
		double comTightness=0;
		double comTightnessIn=0;
		double comTightnessOut=0;
		Map<Integer, Set<Integer>> communities=this.toCommunity();
		for(Integer node1:communities.get(com)){
			for(Integer node2:communities.get(com)){
				if(!node1.equals(node2)){
					comTightnessIn+=graph.getNodeSimilarity(node1, node2);
				}
			}
		}
		Set<Integer> outNodes=this.comNeighber(graph, com);
		for(Integer node1:outNodes){
			for(Integer node2:communities.get(com)){
				if(!node1.equals(node2)){
					comTightnessOut+=graph.getNodeSimilarity(node1, node2);
				}
			}
		}
		comTightness=comTightnessIn+comTightnessOut;
		return comTightness;
	}
}
