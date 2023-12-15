package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import util.BancoDados;

public class ProdutoDAO {
	public static final String COMBOSQL = "SELECT ID, NOME_COMERCIAL FROM PRODUTO ORDER BY NOME_COMERCIAL";
	public static final String CONSULTA_ID_GRUPO = "SELECT GRUPO_PRODUTO_ID FROM PRODUTO WHERE ID = ?";
	public static final String CONSULTA_QUANTIDADE_GRUPOS = "SELECT COUNT(DISTINCT PRODUTO.GRUPO_PRODUTO_ID) AS QUANTIDADE_GRUPOS FROM PRODUTO PRODUTO WHERE PRODUTO.ID = ?;";

	public Integer getIdGrupo(Integer idProduto) {

		try {
			PreparedStatement ps = BancoDados.getConexao().prepareStatement(CONSULTA_ID_GRUPO);
			ps.setInt(1, idProduto);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public Integer getQuantidadeGrupos(Integer idProduto) {

		try {
			PreparedStatement ps = BancoDados.getConexao().prepareStatement(CONSULTA_QUANTIDADE_GRUPOS);
			ps.setInt(1, idProduto);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
}
