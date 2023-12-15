package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import pojo.Cidade;
import util.BancoDados;

public class CidadeDao {

	private Cidade cidade;

	private final String INCLUIRSQL = "INSERT INTO CIDADE(ID, NOME, ID_ESTADO ) VALUES(?,?,?)";
	private final String ALTERARSQL = "UPDATE CIDADE SET NOME=?, ID_ESTADO=? WHERE ID=?";
	private final String EXCLUIRSQL = "DELETE FROM CIDADE WHERE ID=?";
	private final String CONSULTARSQL = "SELECT ID, NOME, ID_ESTADO FROM CIDADE WHERE ID=?";
	public static final String COMBOSQL = "SELECT ID, NOME FROM CIDADE ORDER BY NOME";
	public static final String PESQUISARSQL = "SELECT CIDADE.ID AS ID, CIDADE.NOME AS NOME,	ESTADO.NOME AS ESTADONOME FROM	CIDADE INNER JOIN ESTADO ON	ESTADO.ID = CIDADE.ID_ESTADO";

	public CidadeDao(Cidade cidade) {
		this.cidade = cidade;
	}

	public boolean incluir() {
		try {
			PreparedStatement ps = BancoDados.getConexao().prepareStatement(INCLUIRSQL);
			cidade.setId(BancoDados.pegaGenerator("GEN_CIDADE"));
			ps.setInt(1, cidade.getId());
			ps.setString(2, cidade.getNome());
			ps.setInt(3, cidade.getEstado().getId());
			ps.executeUpdate();
			JOptionPane.showMessageDialog(null, "Cadastro efetuado com sucesso!");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public boolean alterar() {
		try {
			PreparedStatement ps = BancoDados.getConexao().prepareStatement(ALTERARSQL);
			ps.setString(1, cidade.getNome());
			ps.setInt(2, cidade.getEstado().getId());
			ps.setInt(3, cidade.getId());
			ps.executeUpdate();
			JOptionPane.showMessageDialog(null, "Alteração efetuada com sucesso!");
			return true;
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Ocorreu um erro de banco de dados ao alterar a cidade!");
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Ocorreu um erro de aplicação ao alterar o cidade");
			return false;
		}
	}

	public boolean excluir() {
		try {
			PreparedStatement ps = BancoDados.getConexao().prepareStatement(EXCLUIRSQL);
			ps.setInt(1, cidade.getId());
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
			JOptionPane.showMessageDialog(null, "Ocorreu um erro de aplicação ao excluir a cidade");
			return false;
		}
	}

	public boolean consultar() {
		try {
			PreparedStatement ps = BancoDados.getConexao().prepareStatement(CONSULTARSQL);
			ps.setInt(1, cidade.getId());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				cidade.setId(rs.getInt(1));
				cidade.setNome(rs.getString(2));
				cidade.getEstado().setId(rs.getInt(3));
				return true;
			} else {
				JOptionPane.showMessageDialog(null, "Não foi encontrado nenhuma a cidade");
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Ocorreu um erro de banco de dados ao excluir a cidade!");
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Ocorreu um erro de aplicação ao excluir a cidade");
			return false;
		}
	}
}
