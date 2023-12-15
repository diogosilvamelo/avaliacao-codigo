package util;

import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

import pojo.Socio;

public class TableModelSocios extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	
	protected String[] colunas = new String[] { "id", "nome", "telefone", "cpf" };
	protected List<Socio> dados = new ArrayList<>();

	public void addRow(Socio linha) {
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

	boolean[] canEdit = new boolean[] { false, true, true, true, true };

	public void cancelCellEditing() {

	}

	@Override
	public boolean isCellEditable(int row, int column) {
		return canEdit[column];
	}

	@Override
	public int getColumnCount() {
		return colunas.length;
	}

	public Socio getItemSocio(int linha) {
		return dados.get(linha);
	}

	public void setDados(List<Socio> dados) {
		this.dados = dados;
		fireTableDataChanged();
	}

	public List<Socio> getDados() {
		return dados;
	}

	@Override
	public void setValueAt(Object value, int row, int col) {
		switch (col) {
		case 0:
			dados.get(row).setId((Integer) value);
			break;
		case 1:
			dados.get(row).setNome((String) value);
			break;
		case 2:
			dados.get(row).setTelefone((String) value);
			break;
		case 3:
			dados.get(row).setCpf((String) value);
			break;
		case 4:
			dados.get(row).setIdCooperado((Integer) value);
			break;
		default:
			JOptionPane.showMessageDialog(null,
					"Não existe tratamento para a coluna " + col + " na tabela de Socios. (set)");
		}
		fireTableCellUpdated(row, col);
	}

	@Override
	public Object getValueAt(int row, int col) {
		Socio socio = dados.get(row);
		switch (col) {
		case 0:
			return socio.getId();
		case 1:
			return socio.getNome();
		case 2:
			return socio.getTelefone();
		case 3:
			return socio.getCpf();
		case 4:
			return socio.getIdCooperado();
		default:
			JOptionPane.showMessageDialog(null,
					"Não existe tratamento para a coluna " + col + " na tabela de Socios. (get)");
			return "";
		}
	}

	@Override
	public String getColumnName(int numeroColuna) {
		return colunas[numeroColuna];
	}
}
