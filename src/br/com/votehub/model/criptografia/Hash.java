package br.com.votehub.model.criptografia;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.jasypt.util.password.StrongPasswordEncryptor;

import br.com.votehub.model.DAOs.DB;

public class Hash {	
	/*Algorithm: SHA-256. o GPT é ?*/
//	Salt size: 16 bytes. Random
//	Iterations: 100000.  
	
	private final static StrongPasswordEncryptor passHash = new StrongPasswordEncryptor();
	
	public static String gerarHash(String senha) { //Deve ser implementado no VotanteDAO
		String senhaHash = passHash.encryptPassword(senha);  
		return senhaHash;
	}
	
	public static boolean verificarHashvot( String senhaDigitada) throws SQLException {
		Connection conn = null;             
		ResultSet rs = null;
		Statement st= null;
		try {
			conn = DB.getConnection();
			st = conn.createStatement();
			rs = st.executeQuery("SELECT senha \r\n" + "FROM votante \r\n");
			while(rs.next()) {
				String senhabd = rs.getString("senha");
				boolean check = passHash.checkPassword(senhaDigitada, senhabd);
				if (check) {
		            return true;
		        }	
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			DB.closeResultSet(rs);
			DB.closestatement(st);
			DB.closeConnection();
		}
		return false;
	}
	public static boolean verificarHashadm( String senhaDigitada) throws SQLException {
		Connection conn = null;             
		ResultSet rs = null;
		Statement st= null;
		try {
			conn = DB.getConnection();
			st = conn.createStatement();
			rs = st.executeQuery("SELECT senha \r\n" + "FROM adm \r\n");
			while(rs.next()) {
				String senhabd = rs.getString("senha");
				boolean check = passHash.checkPassword(senhaDigitada, senhabd);
				if (check) {
		            return true;
		        }	
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		finally {
			DB.closeResultSet(rs);
			DB.closestatement(st);
			DB.closeConnection();
		}
		return false;
	}
}
