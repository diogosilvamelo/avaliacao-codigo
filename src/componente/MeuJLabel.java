package componente;


import java.awt.Font;
import javax.swing.JLabel;

public class MeuJLabel extends JLabel implements MeuComponente{
    private String nome;

    public MeuJLabel(String nome){
        super(nome);
        this.nome = nome;
        this.setFont(new Font("Calibri", Font.BOLD, 12));
    }

    public boolean eObrigatorio() {
        return true;
    }

    public boolean eValido() {
        return true;
    }

    public boolean eVazio() {
        return false;
    }

    public String getDica(){
        return null;
    }

    @Override
    public void limpar() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setValor(Object valor) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Object getValor() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
