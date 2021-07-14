package it.polito.tdp.PremierLeague.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.PremierLeague.model.Action;
import it.polito.tdp.PremierLeague.model.EdgeAndWeight;
import it.polito.tdp.PremierLeague.model.Player;

public class PremierLeagueDAO {
	
	
	public List<EdgeAndWeight> getArchi(List<Integer> verticiID){
		String sql="select a1.PlayerID as id1,a2.PlayerID as id2, sum(a1.TimePlayed)-sum(a2.TimePlayed) as peso "
				+ "from Actions a1, Actions a2 "
				+ "where a1.PlayerID<>a2.PlayerID and a1.MatchID=a2.MatchID "
				+ "and a1.TeamID<>a2.TeamID  and a1.starts='1' and a2.starts='1' "
				+ "group by a1.PlayerID,a2.PlayerID "
				+ "having peso>'0' ";
		List<EdgeAndWeight> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				if(verticiID.contains(res.getInt("id1")) && verticiID.contains(res.getInt("id2")) ) {
					result.add(new EdgeAndWeight(res.getInt("id1"),res.getInt("id2"),res.getInt("peso")));
				}
				
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Integer> getVertex(double mediaGoal){
		String sql="select PlayerID as pid, avg(Goals) as media "
				+ "from Actions "
				+ "group by PlayerID "
				+ "having media>?";
		List<Integer> result = new ArrayList<>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			st.setDouble(1, mediaGoal);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				result.add(res.getInt("pid"));
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Player> listAllPlayers(){
		String sql = "SELECT * FROM Players";
		List<Player> result = new ArrayList<Player>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Player player = new Player(res.getInt("PlayerID"), res.getString("Name"));
				
				result.add(player);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<Action> listAllActions(){
		String sql = "SELECT * FROM Actions";
		List<Action> result = new ArrayList<Action>();
		Connection conn = DBConnect.getConnection();

		try {
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {

				Action action = new Action(res.getInt("PlayerID"),res.getInt("MatchID"),res.getInt("TeamID"),res.getInt("Starts"),res.getInt("Goals"),
						res.getInt("TimePlayed"),res.getInt("RedCards"),res.getInt("YellowCards"),res.getInt("TotalSuccessfulPassesAll"),res.getInt("totalUnsuccessfulPassesAll"),
						res.getInt("Assists"),res.getInt("TotalFoulsConceded"),res.getInt("Offsides"));
				
				result.add(action);
			}
			conn.close();
			return result;
			
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
}
