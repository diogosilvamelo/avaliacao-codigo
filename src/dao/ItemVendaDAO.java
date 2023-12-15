package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import pojo.ItemVenda;
import pojo.ItemVendaDTO;
import util.BancoDados;

public class ItemVendaDAO {

	String INCLUIR_SQL = "INSERT INTO ITEM_VENDA (ID, QUANTIDADE, VALOR_UNITARIO, ID_PRODUTO, ID_VENDA, ID_TRIBUTACAO) VALUES (?, ?, ?, ?, ?, ?)";
	String CONSULTAR_ITENS_VENDA = "SELECT ITEM_VENDA.ID as ID, ITEM_VENDA.ID_VENDA AS ID_VENDA, ITEM_VENDA.VALOR_UNITARIO AS VALOR_UNITARIO, ITEM_VENDA.QUANTIDADE AS QUANTIDADE, P.NOME_COMERCIAL AS DESCRICAOPRODUTO, T.ALIQUOTA_ICMS AS ALIQUOTA_ICMS FROM ITEM_VENDA ITEM_VENDA INNER JOIN PRODUTO P ON ITEM_VENDA.ID_PRODUTO = P.ID INNER JOIN TRIBUTACAO T ON ITEM_VENDA.ID_TRIBUTACAO = T.ID WHERE ITEM_VENDA.ID_VENDA=?";
	String EXCLUIR = "DELETE FROM ITEM_VENDA";

	public ItemVenda incluir(ItemVenda itemVenda) {
		try {
			PreparedStatement ps = BancoDados.getConexao().prepareStatement(INCLUIR_SQL);
			itemVenda.setId(BancoDados.pegaGenerator("GEN_ITEM_VENDA"));

			if (itemVenda.getIdVenda() == null) {
				itemVenda.setIdVenda(BancoDados.pegaGenerator("GEN_VENDA"));
			}

			ps.setInt(1, itemVenda.getId());
			ps.setInt(2, itemVenda.getQuantidade());
			ps.setDouble(3, itemVenda.getValor());
			ps.setInt(4, itemVenda.getProduto().getId());
			ps.setInt(5, itemVenda.getIdVenda());
			ps.setInt(6, itemVenda.getTributacao().getId());
			ps.executeUpdate();
			JOptionPane.showMessageDialog(null, "Item de venda cadastrado com sucesso!");
			return itemVenda;
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Ocorreu um erro de banco de dados ao incluir o item de venda!");
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Ocorreu um erro de aplicação ao incluir o item de venda");
		}
		return null;
	}

	public List<ItemVendaDTO> consultarItensVenda(Integer idVenda) {
		List<ItemVendaDTO> itensVenda = new ArrayList<>();

		try {
			PreparedStatement ps = BancoDados.getConexao().prepareStatement(CONSULTAR_ITENS_VENDA);
			ps.setInt(1, idVenda);
			ResultSet rs = ps.executeQuery();

			while (rs.next()) {
				ItemVendaDTO itemVendaDTO = new ItemVendaDTO();
				itemVendaDTO.setIdItemVenda(rs.getInt("ID"));
				itemVendaDTO.setIdVenda(rs.getInt("ID_VENDA"));
				itemVendaDTO.setQuantidade(rs.getInt("QUANTIDADE"));
				itemVendaDTO.setValor(rs.getDouble("VALOR_UNITARIO"));
				itemVendaDTO.setAliquota(rs.getDouble("ALIQUOTA_ICMS"));
				itemVendaDTO.setDescricaoProduto(rs.getString("DESCRICAOPRODUTO"));
				itensVenda.add(itemVendaDTO);
			}

			return itensVenda;
		} catch (SQLException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Ocorreu um erro de banco de dados ao consultar os itens de venda!");
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Ocorreu um erro de aplicação ao consultar os itens de venda");
		}

		return itensVenda;
	}

	public void excluirItens() {
		try {
			PreparedStatement ps = BancoDados.getConexao().prepareStatement(EXCLUIR);
			ps.executeUpdate();
		} catch (Exception e) {
		}
	}

}
