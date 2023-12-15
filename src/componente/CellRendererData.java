package componente;

import java.awt.Component;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.text.MaskFormatter;

public class CellRendererData extends DefaultTableCellRenderer {

    Locale loc = new Locale("pt", "BR");
    private SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", loc);

    public CellRendererData() {
        setHorizontalTextPosition(JLabel.RIGHT);
        try {
            MaskFormatter mf = new MaskFormatter("##/##/####");
            //  mf.install(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
        try {
            setText(sdf.format((Date) value));
        } catch (Exception e) {
        }
        return this;
    }
}
