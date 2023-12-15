package util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class BancoDados {

	private static Connection conexao;
	protected static String sqlGenerator = "";
	protected static String enderecoBanco;

	private static final String USER = "sa";
	private static final String PASSWORD = "";

	public static Connection getConexao() {
		try {
			Path diretorioAtual = Paths.get("").toAbsolutePath();

			String nomeBanco = "banco";
			String jdbcUrl = "jdbc:h2:" + diretorioAtual.resolve(nomeBanco);

			Connection connection = DriverManager.getConnection(jdbcUrl, USER, PASSWORD);
			return connection;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static ResultSet executaQuery(PreparedStatement ps) {
		try {
			return ps.executeQuery();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Não foi possível consultar o banco de dados.");
			return null;
		}
	}

	public static int gerarCodigo(String gen) {
		try {
			PreparedStatement ps = getConexao().prepareStatement("SELECT GEN_ID(" + gen + ", 1) FROM RDB$DATABASE");
			ResultSet rs = executaQuery(ps);

			if (rs == null) {
				JOptionPane.showMessageDialog(null, "Não foi possível obter o código (" + gen + ")");
				return -1;
			}
			if (rs.next()) {
				return rs.getInt(1);
			} else {
				return -1;
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Não foi possível obter o generator (" + gen + ")");
			e.printStackTrace();
			return -1;
		}

	}

	public static int pegarCodigo(String linha, String tabela) {
		try {
			PreparedStatement ps = getConexao().prepareStatement("SELECT MAX (" + linha + ") FROM (" + tabela + ")");
			ResultSet rs = executaQuery(ps);

			if (rs == null) {
				JOptionPane.showMessageDialog(null, "Não foi possível obter o código (" + linha + ")");
				return -1;
			}
			if (rs.next()) {
				return rs.getInt(1);
			} else {
				return -1;
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Não foi possível obter o generator (" + linha + ")");
			e.printStackTrace();
			return -1;
		}
	}

	public static ResultSet executaQuery(String sql) {
		try {
			Statement stmt = getConexao().createStatement();
			return stmt.executeQuery(sql);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static int pegaGenerator(String generator) {
		
		Path diretorioAtual = Paths.get("").toAbsolutePath();

		// Define o nome do banco de dados H2
		String nomeBanco = "banco";
		String jdbcUrl = "jdbc:h2:" + diretorioAtual.resolve(nomeBanco);
		
		try (Connection connection = DriverManager.getConnection(jdbcUrl, "sa", "")) {
			try (PreparedStatement ps = connection.prepareStatement("SELECT NEXT VALUE FOR " + generator)) {
				ResultSet rs = ps.executeQuery();
				rs.next();
				return rs.getInt(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public static int pegaGenerator(String generator, int numero) {
		try {
			PreparedStatement ps = BancoDados.getConexao()
					.prepareStatement("SELECT GEN_ID(" + generator + ", " + numero + ") FROM RDB$DATABASE");
			ResultSet rs = ps.executeQuery();
			rs.next();
			return rs.getInt(1);
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		}
	}

	public static List<Object[]> pesquisar(String sql) {
		try {
			List<Object[]> retorno = new ArrayList();
			Statement st = getConexao().createStatement();
			ResultSet rs = st.executeQuery(sql);
			int colunas = rs.getMetaData().getColumnCount();
			while (rs.next()) {
				Object[] linha = new Object[colunas];
				for (int i = 1; i <= colunas; i++) {
					linha[i - 1] = rs.getString(i);
				}
				retorno.add(linha);
			}
			return retorno;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
			e.printStackTrace();
			return null;
		}
	}

}
