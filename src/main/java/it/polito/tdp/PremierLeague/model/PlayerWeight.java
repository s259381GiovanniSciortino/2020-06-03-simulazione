package it.polito.tdp.PremierLeague.model;

public class PlayerWeight implements Comparable<PlayerWeight>{
	Player player;
	Integer peso;
	public PlayerWeight(Player player, Integer peso) {
		super();
		this.player = player;
		this.peso = peso;
	}
	public Player getPlayer() {
		return player;
	}
	public void setPlayer(Player player) {
		this.player = player;
	}
	public Integer getPeso() {
		return peso;
	}
	public void setPeso(Integer peso) {
		this.peso = peso;
	}
	@Override
	public int compareTo(PlayerWeight other) {
		return -this.getPeso().compareTo(other.getPeso());
	}
	@Override
	public String toString() {
		return player + " | " + peso + "\n";
	}
	
}
