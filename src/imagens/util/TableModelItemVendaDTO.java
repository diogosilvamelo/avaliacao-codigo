package util;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import pojo.ItemVendaDTO;

public class TableModelItemVendaDTO extends AbstractTableModel {

	private static final long serialVersionUID = 1L;

	protected String[] colunas = new String[] { "Código item", "Descrição Produto", "Quantidade", "Valor", "Aliquota",
			"Valor total item" };
	protected List<ItemVendaDTO> dados = new ArrayList<>();

	private CellRendererBigDecimal cellRendererBigDecimal = new CellRendererBigDecimal(2);

	public void addRow(ItemVendaDTO linha) {
		dados.add(linha);
		int numeroLinha = dados.size() - 1;
		fireTableRowsInserted(numeroLinha, numeroLinha);
	}

	public void removeRow(int numeroLinha) {
		dados.remove(numeroLinha);
		fireTableRowsDeleted(numeroLinha, numeroLinha);
	}

	@Override
	public int getRowCount() {
		return dados.size();
	}

	boolean[] canEdit = new boolean[] { false, false, false, false, false };

	@Override
	public boolean isCellEditable(int row, int column) {
		return canEdit[column];
	}

	@Override
	public int getColumnCount() {
		return colunas.length;
	}

	public ItemVendaDTO getItemItemVendaDTO(int linha) {
		return dados.get(linha);
	}

	public void setDados(List<ItemVendaDTO> dados) {
		this.dados = dados;
		fireTableDataChanged();
	}

	public List<ItemVendaDTO> getDados() {
		return dados;
	}

	@Override
	public void setValueAt(Object value, int row, int col) {
		switch (col) {
		case 0:
			dados.get(row).setIdItemVenda((Integer) value);
			break;
		case 1:
			dados.get(row).setDescricaoProduto((String) value);
			break;
		case 2:
			dados.get(row).setQuantidade((Integer) value);
			break;
		case 3:
			dados.get(row).setValor((Double) value);
			break;
		case 4:
			dados.get(row).setAliquota((Double) value);
			break;
		case 5:
			dados.get(row).setTotalItem((Double) value);
			break;
		default:
			JOptionPane.showMessageDialog(null,
					"Não existe tratamento para a coluna " + col + " na tabela de ItemVendaDTOs. (set)");
		}
		fireTableCellUpdated(row, col);
	}

	@Override
	public Object getValueAt(int row, int col) {
		ItemVendaDTO itemVendaDTO = dados.get(row);
		switch (col) {
		case 0:
			return itemVendaDTO.getIdItemVenda();
		case 1:
			return itemVendaDTO.getDescricaoProduto();
		case 2:
			return itemVendaDTO.getQuantidade();
		case 3:
			return cellRendererBigDecimal.format(itemVendaDTO.getValor());
		case 4:
			return cellRendererBigDecimal.format(itemVendaDTO.getAliquota());
		case 5:
			return cellRendererBigDecimal.format(itemVendaDTO.getTotalItem());
		default:
		}
		return itemVendaDTO;
	}

	public void limparTabela() {
		int tamanho = getRowCount();
		dados.clear();
		fireTableRowsDeleted(0, tamanho - 1);
	}

	@Override
	public String getColumnName(int numeroColuna) {
		return colunas[numeroColuna];
	}
}
