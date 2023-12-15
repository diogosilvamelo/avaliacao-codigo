package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import pojo.Socio;
import util.BancoDados;

public class SocioDao {

    private Socio socio;

    private final String INCLUIRSQL = "INSERT INTO SOCIO (ID, NOME, TELEFONE, CPF, ID_COOPERADO) VALUES (?, ?, ?, ?, ?)";
    private final String ALTERARSQL = "UPDATE SOCIO SET NOME=?, TELEFONE=?, CPF=? WHERE ID=?";
    private final String EXCLUIRSQL = "DELETE FROM SOCIO WHERE ID=?";
    private final String CONSULTARSQL = "SELECT ID, NOME, TELEFONE, CPF FROM SOCIO WHERE ID=?";
    public static final String PESQUISARSQL = "SELECT ID, NOME, TELEFONE, CPF FROM SOCIO";

    public SocioDao(Socio socio) {
        this.socio = socio;
    }

    public boolean incluir() {
        try {
            PreparedStatement ps = BancoDados.getConexao().prepareStatement(INCLUIRSQL);
            socio.setId(BancoDados.pegaGenerator("GEN_SOCIO"));
			ps.setInt(1, socio.getId());
            ps.setString(2, socio.getNome());
            ps.setString(3, socio.getTelefone());
            ps.setString(4, socio.getCpf());
            ps.setInt(5, socio.getIdCooperado());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Cadastro efetuado com sucesso!");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Ocorreu um erro de banco de dados ao incluir o sócio!");
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Ocorreu um erro de aplicação ao incluir o sócio");
            return false;
        }
    }

    public boolean alterar() {
        try {
            PreparedStatement ps = BancoDados.getConexao().prepareStatement(ALTERARSQL);
            ps.setString(1, socio.getNome());
            ps.setString(2, socio.getTelefone());
            ps.setString(3, socio.getCpf());
            ps.setInt(4, socio.getId());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Alteração efetuada com sucesso!");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Ocorreu um erro de banco de dados ao alterar o sócio!");
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Ocorreu um erro de aplicação ao alterar o sócio");
            return false;
        }
    }

    public boolean excluir() {
        try {
            PreparedStatement ps = BancoDados.getConexao().prepareStatement(EXCLUIRSQL);
            ps.setInt(1, socio.getId());
            ps.executeUpdate();
            JOptionPane.showMessageDialog(null, "Exclusão efetuada com sucesso!");
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Ocorreu um erro de banco de dados ao excluir o sócio!");
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Ocorreu um erro de aplicação ao excluir o sócio");
            return false;
        }
    }

    public boolean consultar() {
        try {
            PreparedStatement ps = BancoDados.getConexao().prepareStatement(CONSULTARSQL);
            ps.setInt(1, socio.getId());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                socio.setId(rs.getInt(1));
                socio.setNome(rs.getString(2));
                socio.setTelefone(rs.getString(3));
                socio.setCpf(rs.getString(4));
                return true;
            } else {
                JOptionPane.showMessageDialog(null, "Não foi encontrado nenhum sócio");
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Ocorreu um erro de banco de dados ao consultar o sócio!");
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Ocorreu um erro de aplicação ao consultar o sócio");
            return false;
        }
    }
}
