package util;

import java.awt.Color;
import java.awt.Component;
import java.text.DecimalFormat;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class CellRendererColuna extends DefaultTableCellRenderer {


    public CellRendererColuna() {
        setHorizontalTextPosition(DefaultTableCellRenderer.RIGHT);
    }

    @Override
    public Component getTableCellRendererComponent(JTable tabela, Object valor, boolean estaSelecionado, boolean temFoco, int linha, int coluna) {
        super.getTableCellRendererComponent(tabela, valor, estaSelecionado, temFoco, linha, coluna);
        setBackground(new Color(238, 238, 238));
        setForeground(Color.BLACK);
        setHorizontalAlignment(JLabel.CENTER);
//outras alterações entram aqui…
        return this;
    }
}
