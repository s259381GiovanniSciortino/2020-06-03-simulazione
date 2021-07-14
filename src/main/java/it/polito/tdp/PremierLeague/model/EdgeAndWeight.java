package it.polito.tdp.PremierLeague.model;

public class EdgeAndWeight {
	Integer playerID1;
	Integer playerID2;
	Integer peso;
	public EdgeAndWeight(Integer playerID1, Integer playerID2, Integer peso) {
		super();
		this.playerID1 = playerID1;
		this.playerID2 = playerID2;
		this.peso = peso;
	}
	public Integer getPlayerID1() {
		return playerID1;
	}
	public void setPlayerID1(Integer playerID1) {
		this.playerID1 = playerID1;
	}
	public Integer getPlayerID2() {
		return playerID2;
	}
	public void setPlayerID2(Integer playerID2) {
		this.playerID2 = playerID2;
	}
	public Integer getPeso() {
		return peso;
	}
	public void setPeso(Integer peso) {
		this.peso = peso;
	}
	

}
