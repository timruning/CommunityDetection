package sf.CommunityAlgrithm;

import java.util.Iterator;
import java.util.Set;

import sf.Community.date.Graph;
import sf.Community.date.Result;

public class CodeTest {

	public static void main(String[] arge) {
		Graph graph=new Graph();
//		GeneticAlgrithmOper geneticAlgrithmOper=new GeneticAlgrithmOper();
		
//		List<Result> list=geneticAlgrithmOper.getInitialCommunities(graph);
		GeneticAlgrithm geneticAlgrithm=new GeneticAlgrithm();
		Result result=geneticAlgrithm.GeneticAlgorithm(graph);
//		
		if(result!=null){
			System.out.println(result.getModularity(graph));
			System.out.println(result.result[0].length);
			for(int i=0;i<result.result[0].length;i++){
				System.out.println(" "+result.result[0][i]+" "+result.result[1][i]);
			}
		}
//		System.out.println(t);
	}
}
