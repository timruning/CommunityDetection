package sf.CommunityAlgrithm;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import sf.Community.date.Graph;
import sf.Community.date.Result;
class ResultModul implements Comparable<ResultModul>{
	public Result r;
	public Double d;
	@Override
	public int compareTo(ResultModul o) {
		// TODO Auto-generated method stub
		if(this.d<o.d){
			return 1;
		}else
			if(this.d==o.d)
			{
				return 0;
			}
			else {
				return -1;
			}
	}
}
public class GeneticAlgrithmOper {
	Set<Result> getInitialCommunities(Graph graph){
		Set<Result> results=new HashSet<Result>();
		int nodeNum=graph.getNodeNum();
		for(int ilist=0;ilist<10;ilist++){
			Integer[][] res=new Integer[2][nodeNum];
			for(int i=0;i<nodeNum;i++){
				res[0][i]=i;
				res[1][i]=0;
			}
			for(int i=0;i<nodeNum;i++){
				if(graph.nodes[i].length>(4*Math.random())){
					res[1][i]=1;
				}
				for(int j=i;j<nodeNum;j++){
					if(j!=i&&graph.nodeConnec(i, j)&&graph.getNodeSimilarity(i, j)>Math.random()){
						res[0][j]=res[0][i];
						res[1][i]=1;
						res[1][j]=1;
					}
				}
			}
			Result result=new Result(res);
			results.add(result);
		}
		return results;
	}
	Set<Result> combineCommunities(Graph graph,Set<Result> results){
		if(results==null)
			{
			System.out.println("something wrong");
			return null;
			}
		if(results.isEmpty())
		{
			System.out.println("something wrong");
			return results;
		}
		Set<Result> set=new HashSet<Result>();
		for(Result result:results){
			for(int i=0;i<20;i++){
				Integer[][] t=result.result;
				Result resultNew=new Result(t);
				Map<Integer, Set<Integer>> communities=resultNew.toCommunity();
				for(Integer com:communities.keySet()){
					Set<Integer> neighberNodes=new HashSet<Integer>();
							neighberNodes=resultNew.comNeighber(graph, com);
					if(neighberNodes.isEmpty()){
						continue;
					}
					else{
						for(Integer node:neighberNodes){
							if(result.getComSimilarity(graph, node, com)>Math.random()){
								resultNew.result[0][node]=com;
								neighberNodes=result.comNeighber(graph, com);
							}
						}
					}
				}
				set.add(resultNew);
			}
		}
		results.addAll(set);
		return results;
	}
	Set<Result> crossoverOperater(Set<Result> results){
		for(int i=0;i<50;i++){
			int p=(int) (results.size()*Math.random());
			Result resultNew1=new Result();
			int q=(int) (results.size()*Math.random());
			Result resultNew2=new Result();
			int t=0;
			for(Result result:results){
				if(t==p){
					resultNew1=new Result(result.result);
				}
				if(t==q){
					resultNew2=new Result(result.result);
				}
				t++;
			}
			int klength=resultNew1.result[0].length;
			Integer[][] arrayNew1=resultNew1.result;
			Integer[][] arrayNew2=resultNew2.result;
			for(int j=0;j<klength;j++){
				int min=Math.min(arrayNew1[1][j], arrayNew2[1][j])+1;
				double d1=(double)min/(arrayNew1[1][j]+arrayNew2[1][j]+1);
				if(Math.random()>d1){
					if(min==arrayNew1[1][j]){
						arrayNew1[0][j]=arrayNew2[0][j];
					}
				}
			}
			Result resultNew=new Result(arrayNew1);
			results.add(resultNew);
		}
		return results;
	}
	Set<Result> mutateOperater(Set<Result> results){
		for(int i=0;i<50;i++){
			int p=(int) (results.size()*Math.random());
			Result resultNew=new Result();
			int t=0;
			for(Result result:results){
				if(t==p){
					resultNew=new Result(result.result);
				}
				t++;
			}
			int klength=resultNew.result[0].length;
			Integer[][] arrayNew=new Integer[2][klength];
			
			for(int j=0;j<klength;j++){
				double d=(double)(resultNew.result[1][j]+1)/100;
				if(Math.random()>d){
					int com1=(int)(klength*Math.random());
					arrayNew[0][j]=com1;
					arrayNew[1][j]=0;
				}else{
					arrayNew[0][j]=resultNew.result[0][j];
					arrayNew[1][j]=resultNew.result[1][j];
				}
			}
			resultNew =new Result(arrayNew);
			results.add(resultNew);
		}
		return results;
	}
	Set<Result> Choose(Graph graph,Set<Result> results){
		System.setProperty("java.util.Arrays.useLegacyMergeSort","true");
		List<ResultModul> list=new ArrayList<ResultModul>();
		for(Result result:results){
			ResultModul rm=new ResultModul();
			if(result!=null)
			{
				rm.r=result;
				rm.d=result.getModularity(graph);
				list.add(rm);
			}
		}
		Collections.sort(list);
		Set<Result> newResults=new HashSet<Result>();
		int i=0;
		while(newResults.size()<20&&i<list.size()){
			newResults.add(list.get(i).r);
			i++;
		}
		return newResults;
	}
	Result ChooseBig(Graph graph,Set<Result> results){
		System.setProperty("java.util.Arrays.useLegacyMergeSort","true");
		List<ResultModul> list=new ArrayList<ResultModul>();
		for(Result result:results){
			ResultModul rm=new ResultModul();
			if(result!=null)
			{
				rm.r=result;
				rm.d=result.getModularity(graph);
				list.add(rm);
			}
		}
		Collections.sort(list);
		Result newResults=new Result();
		newResults=list.get(0).r;
		return newResults;
	}
}
