package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import pojo.Cooperado;
import pojo.Socio;
import util.BancoDados;

public class CooperadoDAO {
	private Cooperado cooperado;

	private final String INCLUIRSQL = "INSERT INTO COOPERADO (ID, CPFCNPJ, NOME, RG, ID_ESTADO_CIVIL, DATANASCIMENTO, DATAFUNDACAO, ID_TIPO_PESSOA, ID_CONCEITO, ID_CIDADE, TELEFONE) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	private final String INCLUIRSOCIO = "INSERT INTO SOCIO VALUES(?,?,?,?)";
	private final String ALTERAR_SQL = "UPDATE COOPERADO SET CPFCNPJ=?, NOME=?, RG=?, ID_ESTADO_CIVIL=?, DATANASCIMENTO=?, DATAFUNDACAO=?, ID_TIPO_PESSOA=?, ID_CONCEITO=?, ID_CIDADE=?, TELEFONE=? WHERE ID=?";
	private final String EXCLUIRSQL = "DELETE FROM cooperado WHERE ID=?";
	private final String CONSULTARSQL = "SELECT ID, NOME, TELEFONE, RG, CPFCNPJ, ID_CIDADE, ID_ESTADO_CIVIL, ID_TIPO_PESSOA, DATANASCIMENTO, ID_CONCEITO, DATAFUNDACAO FROM cooperado WHERE ID=?";
	private final String CONSULTARSOCIOS = "SELECT ID, NOME, CPF, TELEFONE, IS_ESTRANGEIRO AS isEstrangeiro FROM SOCIO WHERE ID_COOPERADO=?";
	public static final String COMBOSQL = "SELECT ID, NOME FROM cooperado ORDER BY NOME";
	public static final String PESQUISARSQL = "SELECT COOPERADO.ID AS ID, COOPERADO.NOME AS NOME, TIPO_PESSOA.DESCRICAO AS TIPOPESSOA "
			+ "FROM COOPERADO " + "INNER JOIN TIPO_PESSOA ON TIPO_PESSOA.ID = COOPERADO.ID_TIPO_PESSOA";

	public static final String CONSULTA_ID_ESTADO_COOPERADO = "SELECT CIDADE.ID_ESTADO FROM COOPERADO INNER JOIN CIDADE ON CIDADE.ID = COOPERADO.ID_CIDADE WHERE COOPERADO.ID = ?";

	public static final String CONSULTA_ID_TIPO_PESSOA = "SELECT ID_TIPO_PESSOA FROM COOPERADO WHERE ID = ?";
	public static final String SQL_CONCEITO_POR_COOPERADO = "SELECT ID_CONCEITO FROM COOPERADO WHERE ID = ?";

	public CooperadoDAO(Cooperado cooperado) {
		this.cooperado = cooperado;
	}

	public boolean incluir() {
		try {

			PreparedStatement ps = BancoDados.getConexao().prepareStatement(INCLUIRSQL);
			cooperado.setId(BancoDados.pegaGenerator("GEN_COOPERADO"));
			ps.setInt(1, cooperado.getId());
			ps.setString(2, cooperado.getCpfcnpj() != null ? cooperado.getCpfcnpj() : "");
			ps.setString(3, cooperado.getNome() != null ? cooperado.getNome() : "");
			ps.setString(4, cooperado.getRg() != null ? cooperado.getRg() : "");
			ps.setObject(5,
					cooperado.getEstadoCivil() != null && cooperado.getEstadoCivil().getId() > 0
							? cooperado.getEstadoCivil().getId()
							: null);
			ps.setObject(6,
					cooperado.getDataNascimento() != null ? new java.sql.Date(cooperado.getDataNascimento().getTime())
							: null);
			ps.setObject(7,
					cooperado.getDataFundacao() != null ? new java.sql.Date(cooperado.getDataFundacao().getTime())
							: null);
			ps.setObject(8,
					cooperado.getTipoPessoa() != null && cooperado.getTipoPessoa().getId() > 0
							? cooperado.getTipoPessoa().getId()
							: null);
			ps.setObject(9,
					cooperado.getConceito() != null && cooperado.getConceito().getId() != null
							? cooperado.getConceito().getId()
							: null);
			ps.setObject(10,
					cooperado.getCidade() != null && cooperado.getCidade().getId() > 0 ? cooperado.getCidade().getId()
							: null);
			ps.setString(11, cooperado.getTelefone() != null ? cooperado.getTelefone() : "");

			ps.executeUpdate();
			JOptionPane.showMessageDialog(null, "Cadastro efetuado com sucesso!");
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Ocorreu um erro de aplicação ao incluir o cooperado");
			return false;
		}
	}

	public boolean incluirSocio(Socio socio) {
		try {
			PreparedStatement ps = BancoDados.getConexao().prepareStatement(INCLUIRSOCIO);
			socio.setId(BancoDados.pegaGenerator("GEN_SOCIO"));
			ps.setString(2, socio.getNome());
			ps.setString(2, socio.getCpf());
			ps.setString(2, socio.getTelefone());
			ps.executeUpdate();
			JOptionPane.showMessageDialog(null, "Cadastro efetuado com sucesso!");
			return true;
		} catch (SQLException e) {

			cooperado.setId(BancoDados.pegaGenerator("GEN_cooperado", -1));
			if (e.getErrorCode() == 335544665) {
				JOptionPane.showMessageDialog(null, "Já existe cadastrado o registro " + cooperado.getNome() + "'.",
						"AVISO", JOptionPane.WARNING_MESSAGE);
			}
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Ocorreu um erro de banco de dados ao incluir o cooperado!");
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Ocorreu um erro de aplicação ao incluir o cooperado");
			return false;
		}
	}

	public boolean alterar() {
		try {

			try (PreparedStatement ps = BancoDados.getConexao().prepareStatement(ALTERAR_SQL)) {
				
				ps.setString(1, cooperado.getCpfcnpj() != null ? cooperado.getCpfcnpj() : "");
				ps.setString(2, cooperado.getNome() != null ? cooperado.getNome() : "");
				ps.setString(3, cooperado.getRg() != null ? cooperado.getRg() : "");
				ps.setObject(4,	cooperado.getEstadoCivil() != null && cooperado.getEstadoCivil().getId() > 0 ? cooperado.getEstadoCivil().getId() : null);
				ps.setObject(5,
						cooperado.getDataNascimento() != null ? new java.sql.Date(cooperado.getDataNascimento().getTime())
								: null);
				ps.setObject(6,
						cooperado.getDataFundacao() != null ? new java.sql.Date(cooperado.getDataFundacao().getTime())
								: null);
				ps.setObject(7,
						cooperado.getTipoPessoa() != null && cooperado.getTipoPessoa().getId() > 0
								? cooperado.getTipoPessoa().getId()
								: null);
				ps.setObject(8,
						cooperado.getConceito() != null && cooperado.getConceito().getId() != null
								? cooperado.getConceito().getId()
								: null);
				ps.setObject(9,
						cooperado.getCidade() != null && cooperado.getCidade().getId() > 0 ? cooperado.getCidade().getId()
								: null);
				ps.setString(10, cooperado.getTelefone() != null ? cooperado.getTelefone() : "");
				ps.setInt(11, cooperado.getId());
				int linhasAfetadas = ps.executeUpdate();

				if (linhasAfetadas > 0) {
					JOptionPane.showMessageDialog(null, "Alteração efetuada com sucesso!");
					return true;
				} else {
					JOptionPane.showMessageDialog(null, "Nenhum registro foi alterado. Verifique o ID do cooperado.");
					return false;
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Ocorreu um erro de banco de dados ao alterar o cooperado!");
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Ocorreu um erro de aplicação ao alterar o cooperado");
			return false;
		}
	}

	public boolean excluir() {
		try {
			PreparedStatement ps = BancoDados.getConexao().prepareStatement(EXCLUIRSQL);
			ps.setInt(1, cooperado.getId());
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
			JOptionPane.showMessageDialog(null, "Ocorreu um erro de aplicação ao excluir o cooperado");
			return false;
		}
	}

	public boolean consultar() {
		try {
			PreparedStatement ps = BancoDados.getConexao().prepareStatement(CONSULTARSQL);
			ps.setInt(1, cooperado.getId());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				cooperado.setId(rs.getInt(1));
				cooperado.setNome(rs.getString(2));
				cooperado.setTelefone(rs.getString(3));
				cooperado.setRg(rs.getString(4));
				cooperado.setCpfcnpj(rs.getString(5));
				
				if(rs.getInt(6) != 0 ) {
					cooperado.getCidade().setId(rs.getInt(6));
				}
				
				if(rs.getInt(7) != 0 ) {
					cooperado.getEstadoCivil().setId(rs.getInt(7));
				}
				
				if(rs.getInt(8) != 0 ) {
					cooperado.getTipoPessoa().setId(rs.getInt(8));
				}
				
				if(rs.getInt(9) != 0 ) {
					cooperado.setDataNascimento(rs.getDate(9));
				}
				
				if(rs.getInt(10) != 0 ) {
					cooperado.getConceito().setId(rs.getInt(10));
				}
				
				cooperado.setDataFundacao(rs.getDate(11));
				return true;
			} else {
				JOptionPane.showMessageDialog(null, "Não foi encontrado nenhuma o cooperado");
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Ocorreu um erro de banco de dados ao excluir o cooperado!");
			return false;
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Ocorreu um erro de aplicação ao excluir o cooperado");
			return false;
		}
	}

	public List<Socio> consultarSocios() {
		List<Socio> socios = new ArrayList<>();

		try {
			PreparedStatement ps = BancoDados.getConexao().prepareStatement(CONSULTARSOCIOS);
			ps.setInt(1, cooperado.getId());
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				Socio socio = new Socio();
				socio.setId(rs.getInt("id"));
				socio.setNome(rs.getString("nome"));
				socio.setCpf(rs.getString("cpf"));
				socio.setTelefone(rs.getString("telefone"));
				socio.setIsEstrangeiro(rs.getBoolean("isEstrangeiro"));
				socios.add(socio);
			}

			return socios;
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Ocorreu um erro de banco de dados ao consultar os sócios!");
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Ocorreu um erro de aplicação ao consultar os sócios");
		}

		return socios;
	}

	public Integer getIdEstadoConsumo(Integer id) {

		try {
			PreparedStatement ps = BancoDados.getConexao().prepareStatement(CONSULTA_ID_ESTADO_COOPERADO);
			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public Integer getIdTipoPessoa(Integer id) {

		try {
			PreparedStatement ps = BancoDados.getConexao().prepareStatement(CONSULTA_ID_TIPO_PESSOA);
			ps.setInt(1, id);

			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				return rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public Integer getConceitoPorCooperado(Integer idCooperado) {

		try {
			PreparedStatement ps = BancoDados.getConexao().prepareStatement(SQL_CONCEITO_POR_COOPERADO);
			ps.setInt(1, idCooperado);

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
