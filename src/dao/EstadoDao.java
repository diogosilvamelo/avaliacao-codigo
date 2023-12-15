package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import pojo.Estado;
import util.BancoDados;

public class EstadoDao {

	private Estado estado;

	private final String INCLUIRSQL = "INSERT INTO ESTADO VALUES(?,?)";
	private final String ALTERARSQL = "UPDATE ESTADO SET NOME=? WHERE ID=?";
	private final String EXCLUIRSQL = "DELETE FROM ESTADO WHERE ID=?";
	private final String CONSULTARSQL = "SELECT * FROM ESTADO WHERE ID=?";
	public static final String COMBOSQL = "SELECT ID,NOME FROM ESTADO ORDER BY NOME";
	public static final String PESQUISARSQL = "SELECT * FROM ESTADO";

	public EstadoDao(Estado estado) {
		this.estado = estado;
	}

	public boolean incluir() {
		try {
			PreparedStatement ps = BancoDados.getConexao().prepareStatement(INCLUIRSQL);
			estado.setId(BancoDados.pegaGenerator("GEN_ESTADO"));
			ps.setInt(1, estado.getId());
			ps.setString(2, estado.getNome());
			ps.executeUpdate();
			JOptionPane.showMessageDialog(null, "Cadastro efetuado com sucesso!");
			return true;
		} catch (SQLException e) {
			estado.setId(BancoDados.pegaGenerator("GEN_ESTADO", -1));
			if (e.getErrorCode() == 335544665) {
				JOptionPane.showMessageDialog(null, "Já existe cadastrado o registro " + estado.getNome() + "'.",
						"AVISO", JOptionPane.WARNING_MESSAGE);
			}
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Ocorreu um erro de banco de dados ao incluir o estado!");
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Ocorreu um erro de aplicação ao incluir o estado");
			return false;
		}
	}

	public boolean alterar() {
		try {
			PreparedStatement ps = BancoDados.getConexao().prepareStatement(ALTERARSQL);
			ps.setString(1, estado.getNome());
			ps.setInt(2, estado.getId());
			ps.executeUpdate();
			JOptionPane.showMessageDialog(null, "Alteração efetuada com sucesso!");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Ocorreu um erro de banco de dados ao alterar o estado!");
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Ocorreu um erro de aplicação ao alterar o estado");
			return false;
		}
	}

	public boolean excluir() {
		try {
			PreparedStatement ps = BancoDados.getConexao().prepareStatement(EXCLUIRSQL);
			ps.setInt(1, estado.getId());
			ps.executeUpdate();
			JOptionPane.showMessageDialog(null, "Exclusão efetuada com sucesso!");
			return true;
		} catch (SQLException e) {
			String message = e.getMessage();
			if (e.getErrorCode() == 335544466) {
				JOptionPane.showMessageDialog(null, "Não é possivel deletar este cadastro por possuir depêndencias.",
						"AVISO", JOptionPane.WARNING_MESSAGE);
			}
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Ocorreu um erro de aplicação ao excluir o estado!");
			return false;
		}
	}

	public boolean consultar() {
		try {
			PreparedStatement ps = BancoDados.getConexao().prepareStatement(CONSULTARSQL);
			ps.setInt(1, estado.getId());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				estado.setId(rs.getInt(1));
				estado.setNome(rs.getString(2));
				return true;
			} else {
				JOptionPane.showMessageDialog(null, "Não foi encontrado nenhum estado!");
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Ocorreu um erro de banco de dados ao consultar o estado!");
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Ocorreu um erro de aplicação ao consultar o estado!");
			return false;
		}
	}
}
