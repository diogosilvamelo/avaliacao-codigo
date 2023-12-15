package dao;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseManager {

	private static final String USER = "sa";
	private static final String PASSWORD = "";

	public static void initializeDatabase() throws IOException {

		Path diretorioAtual = Paths.get("").toAbsolutePath();

		String nomeBanco = "banco";
		String jdbcUrl = "jdbc:h2:" + diretorioAtual.resolve(nomeBanco);

		try (Connection connection = DriverManager.getConnection(jdbcUrl, USER, PASSWORD)) {

			createTables(connection);
			insertEstados(connection);
			insertCidades(connection);
			insertSocios(connection);
			insertDadosCooperados(connection);
			insertConceito(connection);
			insertTipoPessoa(connection);
			insertEstadoCivil(connection);

			createGenerators(connection);

			insertUnidadeMedida(connection);
			insertGrupoProduto(connection);
			insertProdutos(connection);
			insertFiliais(connection);
			insertTributacao(connection);

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private static void createTables(Connection connection) throws SQLException {
		try (Statement statement = connection.createStatement()) {

			String createTableEstado = "CREATE TABLE IF NOT EXISTS ESTADO (ID INT PRIMARY KEY, NOME VARCHAR(255))";
			String createTableCidade = "CREATE TABLE IF NOT EXISTS CIDADE (ID INT PRIMARY KEY, ID_ESTADO INT, NOME VARCHAR(255))";
			String createTableCooperado = "CREATE TABLE IF NOT EXISTS COOPERADO (ID INT PRIMARY KEY, CPFCNPJ VARCHAR(50), NOME VARCHAR(255), RG VARCHAR(20), ID_ESTADO_CIVIL INT, DATANASCIMENTO DATE, DATAFUNDACAO DATE, ID_TIPO_PESSOA INT, ID_CONCEITO INT, ID_CIDADE INT, TELEFONE VARCHAR(50));";
			String createTableSocios = "CREATE TABLE IF NOT EXISTS SOCIO (ID INT PRIMARY KEY, NOME VARCHAR(50), TELEFONE VARCHAR(10), CPF VARCHAR(20), ID_COOPERADO INT, IS_ESTRANGEIRO BIT DEFAULT 0)";
			String createTableConceito = "CREATE TABLE IF NOT EXISTS CONCEITO (ID INT PRIMARY KEY, DESCRICAO VARCHAR(20), SIGLA VARCHAR(1))";
			String createTableTipoPessoa = "CREATE TABLE IF NOT EXISTS TIPO_PESSOA (ID INT PRIMARY KEY, DESCRICAO VARCHAR(20))";
			String createTableEstadoCivil = "CREATE TABLE IF NOT EXISTS ESTADO_CIVIL (ID INT PRIMARY KEY, DESCRICAO VARCHAR(20))";

			String createTableUnidadeMedida = "CREATE TABLE IF NOT EXISTS UNIDADE_MEDIDA (ID INT PRIMARY KEY, DESCRICAO VARCHAR(255))";
			String createTableGrupoProduto = "CREATE TABLE IF NOT EXISTS GRUPO_PRODUTO (ID INT PRIMARY KEY, DESCRICAO VARCHAR(255))";
			String createTableProduto = "CREATE TABLE IF NOT EXISTS PRODUTO (" + "ID INT PRIMARY KEY,"
					+ "NOME_COMERCIAL VARCHAR(255)," + "FORMULA VARCHAR(255)," + "UNIDADE_MEDIDA_ID INT,"
					+ "GRUPO_PRODUTO_ID INT," + "FOREIGN KEY (UNIDADE_MEDIDA_ID) REFERENCES UNIDADE_MEDIDA(ID),"
					+ "FOREIGN KEY (GRUPO_PRODUTO_ID) REFERENCES GRUPO_PRODUTO(ID)" + ")";

			String createTableTributacao = "CREATE TABLE IF NOT EXISTS TRIBUTACAO (" + "ID INT PRIMARY KEY,"
					+ "FINALIDADE VARCHAR(255)," + "ID_ESTADO_ORIGEM INT," + "ID_ESTADO_CONSUMO INT,"
					+ "ID_GRUPO_PRODUTO INT," + "ID_TIPO_PESSOA INT," + "ALIQUOTA_ICMS DECIMAL(5, 2)" + ")";

			String createTableItemVenda = "CREATE TABLE IF NOT EXISTS ITEM_VENDA (" + "ID INT PRIMARY KEY,"
					+ "QUANTIDADE INT," + "VALOR_UNITARIO DECIMAL(10, 2)," + "ID_PRODUTO INT," + "ID_VENDA INT, "
					+ "ID_TRIBUTACAO INT)";

			String createTableFilial = "CREATE TABLE IF NOT EXISTS FILIAL ("
					+ "ID INT PRIMARY KEY, NOME VARCHAR(255), ID_CIDADE INT)";

			String createTableVenda = "CREATE TABLE IF NOT EXISTS VENDA (ID INT PRIMARY KEY)";

			statement.executeUpdate(createTableCidade);
			statement.executeUpdate(createTableEstado);
			statement.executeUpdate(createTableCooperado);
			statement.executeUpdate(createTableSocios);
			statement.executeUpdate(createTableConceito);
			statement.executeUpdate(createTableTipoPessoa);
			statement.executeUpdate(createTableEstadoCivil);

			statement.executeUpdate(createTableUnidadeMedida);
			statement.executeUpdate(createTableGrupoProduto);
			statement.executeUpdate(createTableProduto);

			statement.executeUpdate(createTableTributacao);
			statement.executeUpdate(createTableItemVenda);

			statement.executeUpdate(createTableFilial);
			statement.executeUpdate(createTableVenda);
		}
	}

	private static void createGenerators(Connection connection) throws SQLException {
		try (Statement statement = connection.createStatement()) {
			String generatorEstado = "CREATE SEQUENCE GEN_ESTADO START WITH 1000;";
			String generatorCidade = "CREATE SEQUENCE GEN_CIDADE START WITH 1000;";
			String generatorCooperado = "CREATE SEQUENCE GEN_COOPERADO START WITH 1000;";
			String generatorSocio = "CREATE SEQUENCE GEN_SOCIO START WITH 5;";
			String generatorUnidadeMedida = "CREATE SEQUENCE GEN_UNIDADE_MEDIDA START WITH 1000;";
			String generatorGrupoProduto = "CREATE SEQUENCE GEN_GRUPO_PRODUTO START WITH 1000;";
			String generatorProduto = "CREATE SEQUENCE GEN_PRODUTO START WITH 1000;";
			String generatorItemVenda = "CREATE SEQUENCE GEN_ITEM_VENDA START WITH 1000;";
			String generatorVenda = "CREATE SEQUENCE GEN_VENDA START WITH 1000;";
			String generatorFilial = "CREATE SEQUENCE GEN_FILIAL START WITH 1000;";

			statement.execute(generatorEstado);
			statement.execute(generatorCidade);
			statement.execute(generatorCooperado);
			statement.execute(generatorSocio);
			statement.execute(generatorUnidadeMedida);
			statement.execute(generatorGrupoProduto);
			statement.execute(generatorProduto);
			statement.execute(generatorItemVenda);
			statement.execute(generatorVenda);
			statement.execute(generatorFilial);
		}
	}

	private static void insertEstados(Connection connection) throws SQLException {
		try (Statement statement = connection.createStatement()) {

			String insertAcre = "INSERT INTO ESTADO VALUES (1, 'ACRE')";
			String insertAlagoas = "INSERT INTO ESTADO VALUES (2, 'ALAGOAS')";
			String insertAmapa = "INSERT INTO ESTADO VALUES (3, 'AMAPÁ')";
			String insertParana = "INSERT INTO ESTADO VALUES (4, 'PARANÁ')";
			String insertSantaCatarina = "INSERT INTO ESTADO VALUES (5, 'SANTA CATARINA')";
			String insertMatoGrossoSul = "INSERT INTO ESTADO VALUES (6, 'MATO GROSSO DO SUL')";

			statement.executeUpdate(insertAcre);
			statement.executeUpdate(insertAlagoas);
			statement.executeUpdate(insertAmapa);
			statement.executeUpdate(insertParana);
			statement.executeUpdate(insertSantaCatarina);
			statement.executeUpdate(insertMatoGrossoSul);
		}
	}

	private static void insertConceito(Connection connection) throws SQLException {
		try (Statement statement = connection.createStatement()) {
			String insertA = "INSERT INTO CONCEITO VALUES (1, 'EXCELENTE', 'A')";
			String insertB = "INSERT INTO CONCEITO VALUES (2, 'BOM', 'B')";
			String insertC = "INSERT INTO CONCEITO VALUES (3, 'BÁSICO', 'C')";

			statement.executeUpdate(insertA);
			statement.executeUpdate(insertB);
			statement.executeUpdate(insertC);
		}

	}

	private static void insertSocios(Connection connection) throws SQLException {
		try (Statement statement = connection.createStatement()) {

			String insertJohnDoe = "INSERT INTO SOCIO VALUES (1, 'John Doe', '1234567890', '123.456.789-01', 4, 1)";
			String insertJaneSmith = "INSERT INTO SOCIO VALUES (2, 'Jane Smith', '9876543210', '987.654.321-02', 4, 0)";
			String insertBobJohnson = "INSERT INTO SOCIO VALUES (3, 'Bob Johnson', '5556667777', '555.666.777-03', 4, 1)";

			statement.executeUpdate(insertJohnDoe);
			statement.executeUpdate(insertJaneSmith);
			statement.executeUpdate(insertBobJohnson);
		}
	}

	private static void insertCidades(Connection connection) throws SQLException {
		try (Statement statement = connection.createStatement()) {

			String insertCuritiba = "INSERT INTO CIDADE (ID, ID_ESTADO, NOME) VALUES (1, 4, 'CURITIBA');";
			String insertLondrina = "INSERT INTO CIDADE (ID,ID_ESTADO, NOME) VALUES (2, 4, 'LONDRINA');";
			String insertMaringa = "INSERT INTO CIDADE (ID,ID_ESTADO, NOME) VALUES (3, 4, 'MARINGÁ');";
			String insertCampoMourao = "INSERT INTO CIDADE (ID,ID_ESTADO, NOME) VALUES (4, 4, 'CAMPO MOURÃO');";
			String insertGoioere = "INSERT INTO CIDADE (ID,ID_ESTADO, NOME) VALUES (5, 4, 'GOIOERÊ');";
			String insertUbirata = "INSERT INTO CIDADE (ID,ID_ESTADO, NOME) VALUES (6, 4, 'UBIRATÃ');";
			String insertSantaCatarina = "INSERT INTO CIDADE (ID,ID_ESTADO, NOME) VALUES (7, 5, 'JOINVILLE');";
			String insertFeijo = "INSERT INTO CIDADE (ID,ID_ESTADO, NOME) VALUES (8, 1, 'FEIJÓ');";
			String insertMaceio = "INSERT INTO CIDADE (ID,ID_ESTADO, NOME) VALUES (9, 2, 'MACEIÓ');";

			statement.executeUpdate(insertCuritiba);
			statement.executeUpdate(insertLondrina);
			statement.executeUpdate(insertMaringa);
			statement.executeUpdate(insertCampoMourao);
			statement.executeUpdate(insertGoioere);
			statement.executeUpdate(insertUbirata);
			statement.executeUpdate(insertSantaCatarina);
			statement.executeUpdate(insertFeijo);
			statement.executeUpdate(insertMaceio);
		}
	}

	private static void insertEstadoCivil(Connection connection) throws SQLException {
		try (Statement statement = connection.createStatement()) {

			String insertSolteiro = "INSERT INTO ESTADO_CIVIL VALUES (1, 'SOLTEIRO(A)')";
			String insertCasado = "INSERT INTO ESTADO_CIVIL VALUES (2, 'CASADO(A)')";
			String insertViuvo = "INSERT INTO ESTADO_CIVIL VALUES (3, 'VIUVO(A)')";
			String insertDivorciado = "INSERT INTO ESTADO_CIVIL VALUES (4, 'DIVORCIADO(A)')";

			statement.executeUpdate(insertSolteiro);
			statement.executeUpdate(insertCasado);
			statement.executeUpdate(insertViuvo);
			statement.executeUpdate(insertDivorciado);
		}
	}

	private static void insertTipoPessoa(Connection connection) throws SQLException {
		try (Statement statement = connection.createStatement()) {

			String insertFisica = "INSERT INTO TIPO_PESSOA VALUES (1, 'FISICA')";
			String insertJuridica = "INSERT INTO TIPO_PESSOA VALUES (2, 'JURIDICA')";

			statement.executeUpdate(insertFisica);
			statement.executeUpdate(insertJuridica);
		}
	}

	private static void insertDadosCooperados(Connection connection) throws SQLException {
		try (Statement statement = connection.createStatement()) {

			String insertCooperado1 = "INSERT INTO COOPERADO (ID, CPFCNPJ, NOME, RG, ID_ESTADO_CIVIL, DATANASCIMENTO, DATAFUNDACAO, ID_TIPO_PESSOA, ID_CONCEITO, ID_CIDADE, TELEFONE) VALUES (1, '123.456.789-01', 'JOÃO SILVA', '1234567', 1, '1985-08-20', NULL, 1, 1, 1, '44-99865571');";
			String insertCooperado2 = "INSERT INTO COOPERADO (ID, CPFCNPJ, NOME, RG, ID_ESTADO_CIVIL, DATANASCIMENTO, DATAFUNDACAO, ID_TIPO_PESSOA, ID_CONCEITO, ID_CIDADE, TELEFONE) VALUES (2, '12.345.678/9012-34', 'INSUMOS LTDA', '9876543', 2, NULL, '1985-08-20', 2, 2, 5, '44-99865572');";
			String insertCooperado3 = "INSERT INTO COOPERADO (ID, CPFCNPJ, NOME, RG, ID_ESTADO_CIVIL, DATANASCIMENTO, DATAFUNDACAO, ID_TIPO_PESSOA, ID_CONCEITO, ID_CIDADE, TELEFONE) VALUES (3, '345.678.901-23', 'PEDRO OLIVEIRA', '5432167', 4, '1995-12-05', NULL, 1, 1, 1, '44-99865573');";
			String insertCooperado4 = "INSERT INTO COOPERADO (ID, CPFCNPJ, NOME, RG, ID_ESTADO_CIVIL, DATANASCIMENTO, DATAFUNDACAO, ID_TIPO_PESSOA, ID_CONCEITO, ID_CIDADE, TELEFONE) VALUES (4, '23.456.789/0123-45', 'FAZN BOA SORTE', '7654321', 3, NULL, '1995-12-05', 2, 3, 5, '44-99865574');";

			statement.executeUpdate(insertCooperado1);
			statement.executeUpdate(insertCooperado2);
			statement.executeUpdate(insertCooperado3);
			statement.executeUpdate(insertCooperado4);

		}
	}

	private static void insertUnidadeMedida(Connection connection) throws SQLException {
		try (Statement statement = connection.createStatement()) {
			String insertKilograma = "INSERT INTO UNIDADE_MEDIDA VALUES (1, 'Kilograma')";
			statement.executeUpdate(insertKilograma);
		}
	}

	private static void insertGrupoProduto(Connection connection) throws SQLException {
		try (Statement statement = connection.createStatement()) {
			String insertFertilizantes = "INSERT INTO GRUPO_PRODUTO VALUES (1, 'Fertilizantes')";
			String insertCorretivos = "INSERT INTO GRUPO_PRODUTO VALUES (2, 'Corretivos')";
			String insertHerbicidas = "INSERT INTO GRUPO_PRODUTO VALUES (3, 'Herbicidas')";
			String insertFungicidas = "INSERT INTO GRUPO_PRODUTO VALUES (4, 'Fungicidas')";
			String insertInseticidas = "INSERT INTO GRUPO_PRODUTO VALUES (5, 'Inseticidas')";

			statement.executeUpdate(insertFertilizantes);
			statement.executeUpdate(insertCorretivos);
			statement.executeUpdate(insertHerbicidas);
			statement.executeUpdate(insertFungicidas);
			statement.executeUpdate(insertInseticidas);
		}
	}

	private static void insertProdutos(Connection connection) throws SQLException {
		try (Statement statement = connection.createStatement()) {
			String insertAduboNPK = "INSERT INTO PRODUTO (ID, NOME_COMERCIAL, FORMULA, UNIDADE_MEDIDA_ID, GRUPO_PRODUTO_ID) VALUES (1, 'Adubo NPK', '10-20-10', 1, 1)";
			String insertCalcario = "INSERT INTO PRODUTO (ID, NOME_COMERCIAL, FORMULA, UNIDADE_MEDIDA_ID, GRUPO_PRODUTO_ID) VALUES (2, 'Calcário', 'CaCO3', 1, 2)";
			String insertGlyphosate = "INSERT INTO PRODUTO (ID, NOME_COMERCIAL, FORMULA, UNIDADE_MEDIDA_ID, GRUPO_PRODUTO_ID) VALUES (3, 'Glyphosate', 'C3H8NO5P', 1, 3)";
			String insertCaptan = "INSERT INTO PRODUTO (ID, NOME_COMERCIAL, FORMULA, UNIDADE_MEDIDA_ID, GRUPO_PRODUTO_ID) VALUES (4, 'Captan', 'C9H8ClNO2', 1, 4)";
			String insertMalathion = "INSERT INTO PRODUTO (ID, NOME_COMERCIAL, FORMULA, UNIDADE_MEDIDA_ID, GRUPO_PRODUTO_ID) VALUES (5, 'Malathion', 'C10H19O6PS2', 1, 5)";

			statement.executeUpdate(insertAduboNPK);
			statement.executeUpdate(insertCalcario);
			statement.executeUpdate(insertGlyphosate);
			statement.executeUpdate(insertCaptan);
			statement.executeUpdate(insertMalathion);
		}
	}

	private static void insertTributacao(Connection connection) throws SQLException {
		try (Statement statement = connection.createStatement()) {

			String insertTributacao1 = "INSERT INTO TRIBUTACAO VALUES (1, 'Finalidade 1', 4, 5, 1, 1, 12)";
			String insertTributacao2 = "INSERT INTO TRIBUTACAO VALUES (2, 'Finalidade 2', 6, 4, 2, 2, 18)";
			String insertTributacao3 = "INSERT INTO TRIBUTACAO VALUES (3, 'Finalidade 3', 4, 6, 3, 1, 0)";
			String insertTributacao4 = "INSERT INTO TRIBUTACAO VALUES (4, 'Finalidade 4', 5, 4, 1, 2, 18)";
			
			statement.executeUpdate(insertTributacao1);
			statement.executeUpdate(insertTributacao2);
			statement.executeUpdate(insertTributacao3);
			statement.executeUpdate(insertTributacao4);
		}
	}

	private static void insertFiliais(Connection connection) throws SQLException {
		try (Statement statement = connection.createStatement()) {
			String insertFilial1 = "INSERT INTO FILIAL VALUES (4, 'Filial PARANÁ', 8)";
			String insertFilial2 = "INSERT INTO FILIAL VALUES (5, 'Filial MATO GROSSO DO SUL', 2)";
			String insertFilial3 = "INSERT INTO FILIAL VALUES (6, 'Filial SC', 7)";

			statement.executeUpdate(insertFilial1);
			statement.executeUpdate(insertFilial2);
			statement.executeUpdate(insertFilial3);
		}
	}

	private static void createFKS(Connection connection) throws SQLException {
		try (Statement statement = connection.createStatement()) {

			String addForeignKeyCidade = "ALTER TABLE CIDADE ADD FOREIGN KEY (ID_ESTADO) REFERENCES ESTADO(ID)";
			statement.executeUpdate(addForeignKeyCidade);

			String addForeignKeyCooperadoEstadoCivil = "ALTER TABLE COOPERADO ADD FOREIGN KEY (ID_ESTADO_CIVIL) REFERENCES ESTADO_CIVIL(ID)";
			statement.executeUpdate(addForeignKeyCooperadoEstadoCivil);

			String addForeignKeyCooperadoTipoPessoa = "ALTER TABLE COOPERADO ADD FOREIGN KEY (ID_TIPO_PESSOA) REFERENCES TIPO_PESSOA(ID)";
			statement.executeUpdate(addForeignKeyCooperadoTipoPessoa);

			String addForeignKeyCooperadoConceito = "ALTER TABLE COOPERADO ADD FOREIGN KEY (ID_CONCEITO) REFERENCES CONCEITO(ID)";
			statement.executeUpdate(addForeignKeyCooperadoConceito);

			String addForeignKeyCooperadoCidade = "ALTER TABLE COOPERADO ADD FOREIGN KEY (ID_CIDADE) REFERENCES CIDADE(ID)";
			statement.executeUpdate(addForeignKeyCooperadoCidade);

			String addForeignKeySocio = "ALTER TABLE SOCIO ADD FOREIGN KEY (ID_COOPERADO) REFERENCES COOPERADO(ID)";
			statement.executeUpdate(addForeignKeySocio);

			String addForeignKeyProdutoUnidadeMedida = "ALTER TABLE PRODUTO ADD FOREIGN KEY (UNIDADE_MEDIDA_ID) REFERENCES UNIDADE_MEDIDA(ID)";
			statement.executeUpdate(addForeignKeyProdutoUnidadeMedida);

			String addForeignKeyProdutoGrupoProduto = "ALTER TABLE PRODUTO ADD FOREIGN KEY (GRUPO_PRODUTO_ID) REFERENCES GRUPO_PRODUTO(ID)";
			statement.executeUpdate(addForeignKeyProdutoGrupoProduto);

			String addForeignKeyTributacaoEstadoOrigem = "ALTER TABLE TRIBUTACAO ADD FOREIGN KEY (ID_ESTADO_ORIGEM) REFERENCES ESTADO(ID)";
			statement.executeUpdate(addForeignKeyTributacaoEstadoOrigem);

			String addForeignKeyTributacaoEstadoConsumo = "ALTER TABLE TRIBUTACAO ADD FOREIGN KEY (ID_ESTADO_CONSUMO) REFERENCES ESTADO(ID)";
			statement.executeUpdate(addForeignKeyTributacaoEstadoConsumo);

			String addForeignKeyTributacaoGrupoProduto = "ALTER TABLE TRIBUTACAO ADD FOREIGN KEY (ID_GRUPO_PRODUTO) REFERENCES GRUPO_PRODUTO(ID)";
			statement.executeUpdate(addForeignKeyTributacaoGrupoProduto);

			String addForeignKeyTributacaoTipoPessoa = "ALTER TABLE TRIBUTACAO ADD FOREIGN KEY (ID_TIPO_PESSOA) REFERENCES TIPO_PESSOA(ID)";
			statement.executeUpdate(addForeignKeyTributacaoTipoPessoa);

			String addForeignKeyItemVendaProduto = "ALTER TABLE ITEM_VENDA ADD FOREIGN KEY (ID_PRODUTO) REFERENCES PRODUTO(ID)";
			statement.executeUpdate(addForeignKeyItemVendaProduto);

			String addForeignKeyItemVendaVenda = "ALTER TABLE ITEM_VENDA ADD FOREIGN KEY (ID_VENDA) REFERENCES VENDA(ID)";
			statement.executeUpdate(addForeignKeyItemVendaVenda);

			String addForeignKeyItemVendaTributacao = "ALTER TABLE ITEM_VENDA ADD FOREIGN KEY (ID_TRIBUTACAO) REFERENCES TRIBUTACAO(ID)";
			statement.executeUpdate(addForeignKeyItemVendaTributacao);

			String addForeignKeyFilial = "ALTER TABLE FILIAL ADD FOREIGN KEY (ID_CIDADE) REFERENCES CIDADE(ID)";
			statement.executeUpdate(addForeignKeyFilial);
		}
	}

}
