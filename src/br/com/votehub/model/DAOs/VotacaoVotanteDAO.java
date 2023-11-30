package br.com.votehub.model.DAOs;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import br.com.votehub.model.vo.VotacaoVotante;

public class VotacaoVotanteDAO {
	Connection conn = null;
	Statement st = null;
	ResultSet rs = null;
	PreparedStatement stt = null;
	PreparedStatement stt1 = null;
	PreparedStatement stt2 = null;

	public void mostrarVotacaoVotante() {
		try {
			conn = DB.getConnection();
			st = conn.createStatement();
			rs = st.executeQuery("SELECT * \r\n" + "FROM votacaoVotante \r\n");
			while (rs.next()) {
				System.out.println("votante: " + rs.getString("id_votante") + " votacao: " + rs.getString("id_votacao"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.closeResultSet(rs);
			DB.closestatement(st);
	//		DB.closeConnection();
		}
	}
	
	public void addVotacaoVotante(VotacaoVotante vtvt) {
		try {
			conn = DB.getConnection();
			stt = conn.prepareStatement("INSERT INTO candidato" + "(id_votacao, id_votante )" + "VALUES" + "(?, ?)");

			stt.setInt(1, vtvt.getId_votacao());
			stt.setInt(2, vtvt.getId_votante());

			stt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			DB.closestatement(stt);
	//		DB.closeConnection();
		}
	}
}
