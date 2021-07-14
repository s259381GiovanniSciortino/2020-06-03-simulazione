package it.polito.tdp.PremierLeague.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import it.polito.tdp.PremierLeague.db.PremierLeagueDAO;

public class Model {
	Map<Integer,Player> playerIDMap;
	Graph<Player,DefaultWeightedEdge> grafo;
	
	public String doCreaGrafo(double media) {
		PremierLeagueDAO dao= new PremierLeagueDAO();
		grafo= new SimpleDirectedWeightedGraph<>(DefaultWeightedEdge.class);
		playerIDMap = new HashMap<>();
		List<Player> giocatori = new ArrayList<>(dao.listAllPlayers());
		for(Player p: giocatori)
			this.playerIDMap.put(p.getPlayerID(), p);
		List<Integer> verticiID = new ArrayList<>(dao.getVertex(media));
		
		for(Integer vertici: verticiID)
			grafo.addVertex(playerIDMap.get(vertici));
		
		List<EdgeAndWeight> archi = new ArrayList<>(dao.getArchi(verticiID));
		
		for(EdgeAndWeight e : archi) 
			Graphs.addEdge(grafo, playerIDMap.get(e.getPlayerID1()), playerIDMap.get(e.getPlayerID2()), e.getPeso());
		String result = "";
		if(this.grafo==null) {
			result ="Grafo non creato";
			return result;
		}
		result = "Grafo creato con :\n# "+this.grafo.vertexSet().size()+" VERTICI\n# "+this.grafo.edgeSet().size()+" ARCHI\n";
		return result;
	}
	
	public String doTopPlayer() {
		int maxBattuti = 0;
		Player bestPlayer= null;
		for(Player p: grafo.vertexSet()) {
			int battuti = grafo.outDegreeOf(p);
			if(battuti>maxBattuti) {
				maxBattuti= battuti;
				bestPlayer = p;
			}
		}
		List<PlayerWeight> battuti = new ArrayList<>();
		String result="\n\nTOP PLAYER : "+bestPlayer+"\n\nAVVERSARI BATTUTI:\n";
		for(Player p: Graphs.successorListOf(grafo,bestPlayer )) {
			battuti.add(new PlayerWeight(p,(int) grafo.getEdgeWeight(grafo.getEdge(bestPlayer, p))));
		}
		Collections.sort(battuti);
		for(PlayerWeight p: battuti)
			result+=p;
		return result;
	}
}
