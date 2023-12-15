package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import pojo.ValidarFinalidade;
import util.BancoDados;

public class FinalidadeDAO {

	String CONSULTAR_FINALIDADE = "SELECT ALIQUOTA_ICMS as aliquota FROM TRIBUTACAO WHERE ID_GRUPO_PRODUTO =? AND ID_TIPO_PESSOA =? AND ID_ESTADO_CONSUMO=? AND ID_ESTADO_ORIGEM=? ";

	public ValidarFinalidade validarFinalidade(ValidarFinalidade validarFinalidade) {

		try {
			PreparedStatement ps = BancoDados.getConexao().prepareStatement(CONSULTAR_FINALIDADE);

			ps.setInt(1, validarFinalidade.getIdGrupoProduto());
			ps.setInt(2, validarFinalidade.getIdTipoPessoa());
			ps.setInt(3, validarFinalidade.getIdEstadoConsumo());
			ps.setInt(4, validarFinalidade.getIdEstadoOrigem());

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				validarFinalidade.setValidado(true);
				validarFinalidade.setAliquota(rs.getBigDecimal("aliquota"));
				return validarFinalidade;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return validarFinalidade;
	}

}
