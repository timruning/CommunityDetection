package sf.CommunityAlgrithm;
import java.util.Set;

import sf.Community.date.Graph;
import sf.Community.date.Result;


public class GeneticAlgrithm {
	
	GeneticAlgrithmOper geneticAlgoritmOper=new GeneticAlgrithmOper();
	Result GeneticAlgorithm(Graph graph){
		Set<Result> results=geneticAlgoritmOper.getInitialCommunities(graph);
		System.out.println("getInitialCommunities");
		for(Result result:results){
			System.out.println(result.getModularity(graph));
		}
		System.out.println(results.size());
		int i=0;
		while(i<100){
			results=geneticAlgoritmOper.combineCommunities(graph, results);
			System.out.println("combineCommunities");
			for(Result result:results){
				System.out.println(result.getModularity(graph));
			}
			System.out.println(results.size());
			results=geneticAlgoritmOper.crossoverOperater(results);
			System.out.println("crossoverOperater");
			for(Result result:results){
				System.out.println(result.getModularity(graph));
			}
			System.out.println(results.size());
			
			results=geneticAlgoritmOper.mutateOperater(results);
			System.out.println("mutateOperater");
			for(Result result:results){
				System.out.println(result.getModularity(graph));
			}
			System.out.println(results.size());
			results=geneticAlgoritmOper.Choose(graph, results);
			System.out.println("Choose");
			for(Result result:results){
				System.out.println(result.getModularity(graph));
			}
			System.out.println(results.size());
			i++;
			
		}
		Result anwser=geneticAlgoritmOper.ChooseBig(graph, results);
		return anwser;
	}
}
