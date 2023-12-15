package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import util.BancoDados;

public class ConceitoDao {
	public static final String COMBOSQL = "SELECT ID, DESCRICAO FROM CONCEITO ORDER BY DESCRICAO";

}
