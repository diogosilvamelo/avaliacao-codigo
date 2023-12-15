package util;

import java.awt.Color;
import java.awt.Component;
import java.text.DecimalFormat;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class CellRendererBigDecimal extends DefaultTableCellRenderer {

	private DecimalFormat df;

	public CellRendererBigDecimal(int numeroCasasDecimais) {
		setHorizontalTextPosition(DefaultTableCellRenderer.RIGHT);
		String mascara = "###,###,##0.";
		for (int i = 0; i < numeroCasasDecimais; i++) {
			mascara = mascara + "0";
		}
		df = new DecimalFormat(mascara + "0");
	}

	@Override
	public Component getTableCellRendererComponent(JTable tabela, Object valor, boolean estaSelecionado,
			boolean temFoco, int linha, int coluna) {
		super.getTableCellRendererComponent(tabela, valor, estaSelecionado, temFoco, linha, coluna);
		if (valor == null) {
			setForeground(Color.BLACK);
			valor = 0.0; // Alteração para aceitar Double
		}
		setValue(valor); // Utiliza o método setValue para formatar o texto
		setText(df.format(valor));
		return this;
	}

	public String format(Double valor) {
		if (valor == null) {
			return df.format(0.0);
		}
		return df.format(valor);
	}
}
