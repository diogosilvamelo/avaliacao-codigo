/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package componente;

import javax.swing.JOptionPane;
import javax.swing.event.CaretEvent;
import javax.swing.text.MaskFormatter;

/**
 *
 * @author USER
 */
public class MeuCampoHora extends MeuCampoGenerico {

    private boolean obrigatorio;
    private String dica;

    public MeuCampoHora(boolean obrigatorio, String dica) {
        super(obrigatorio, dica, 5);
        this.obrigatorio = obrigatorio;
        this.dica = dica;
        setColumns(3);
        getText().trim();
        try {
            MaskFormatter mf = new MaskFormatter("##:##");
            mf.install(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean eVazio() {
           return (getText().equals("  :  "));
    }

    @Override
    public void setValor(Object valor) {
        setText("" + valor);
    }

    @Override
    public Object getValor() {
        return "" + getText();
    }

    @Override
    public String getDica() {
        return dica;
    }

    @Override
    public boolean eObrigatorio() {
        return obrigatorio;
    }

    @Override
    public boolean eValido() {
        String hora = getText();
        // separa a hora o minuto e segundo em um array  
        String[] hms = hora.split(":");
        int horas = Integer.parseInt(hms[0]);
        int minutos = Integer.parseInt(hms[1]);
        if (horas > 24) {
            JOptionPane.showMessageDialog(null, "Hora Inválida", "AVISO", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        if (minutos > 60) {
            System.out.println("Minutos Inválido");
            JOptionPane.showMessageDialog(null, "Minutos Inválidos", "AVISO", JOptionPane.WARNING_MESSAGE);
            return false;
        }
        return true;
    }

    @Override
    public void caretUpdate(CaretEvent ce) {
        if (eVazio()) {
            getCaret().setSelectionVisible(false);
            getCaret().setDot(0);
            getCaret().setSelectionVisible(true);
        }
    }
}
