package componente;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.event.CaretEvent;
import javax.swing.text.MaskFormatter;
import util.Validacao_CPF_CNPJ;

public class PainelCPFCNPJ extends JPanel implements MeuComponente {

    private MeuCampoFormatado mcf = new MeuCampoFormatado("",false,10) {

        @Override
        public boolean eVazio() {
            return (mcf.getText().equals("___.___.___-__") || mcf.getText().equals("__.___.___/____-__"));
        }

        @Override
        public void caretUpdate(CaretEvent e) {
            if (eVazio()) {
                getCaret().setSelectionVisible(false);
                getCaret().setDot(0);
                getCaret().setSelectionVisible(true);
            }
        }
    };
    private JRadioButton cpf = new JRadioButton("cpf".toUpperCase());
    private JRadioButton cnpj = new JRadioButton("cnpj".toUpperCase());
    private ButtonGroup bt = new ButtonGroup();
   
    private MaskFormatter mf = new MaskFormatter();
    JPanel jp = new JPanel();

    public PainelCPFCNPJ() {
        setLayout(new GridLayout(2,1));
        jp.setLayout(new FlowLayout());
        bt.add(cpf);
        bt.add(cnpj);
        jp.add(cpf);
        jp.add(cnpj);
        add(jp);
        add(mcf);
        mf.install(mcf);
        adicionaListeners();
        mcf.setCaretPosition(0);
    }

    private void adicionaListeners() {

        cpf.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                if (cpf.isSelected()) {
                    try {
                        mf.uninstall();
                        mf = new MaskFormatter("###.###.###-##");
                        mf.setPlaceholderCharacter('_');
                        mf.install(mcf);
                    } catch (ParseException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });

        cnpj.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                if (cnpj.isSelected()) {
                    try {
                        mf.uninstall();
                        mf = new MaskFormatter("##.###.###/####-##");
                        mf.setPlaceholderCharacter('_');
                        mf.install(mcf);
                    } catch (ParseException ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public void limpar() {
        bt.clearSelection();
        mcf.setText("");
    }

    @Override
    public boolean eValido() {
        String pega_valor = mcf.getText();// pega o texto do campo
//        return (pega_valor.length() > 14) ? Validacao_CPF_CNPJ.validaCNPJ(pega_valor) : (pega_valor.length() <= 14) ? Validacao_CPF_CNPJ.validaCPF(pega_valor) : false;
        if(pega_valor.length()==0){
            return false;
        }
        else if(pega_valor.length() > 14){
            return Validacao_CPF_CNPJ.validaCNPJ(pega_valor);
        }else if(pega_valor.length() <= 14){
            return Validacao_CPF_CNPJ.validaCPF(pega_valor);
        }else{
            return false;
        }
    }

    @Override
    public boolean eObrigatorio() {
        return false;
    }

    @Override
    public boolean eVazio() {
        return (mcf.getText().equals("___.___.___-__") || mcf.getText().equals("__.___.___/____-__"));
    }

    @Override
    public void setValor(Object valor) {
        String vlr = (String) valor;
        if (vlr.trim().length() > 14) {
            cnpj.setSelected(true);
            adicionaListeners();
            try {
                mf.uninstall();
                mf = new MaskFormatter("##.###.###/####-##");
                mf.setPlaceholderCharacter('_');
                mf.install(mcf);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        } else {
            cpf.setSelected(true);
            try {
                mf.uninstall();
                mf = new MaskFormatter("###.###.###-##");
                mf.setPlaceholderCharacter('_');
                mf.install(mcf);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }
        mcf.setText("" + valor);
        System.out.println(valor);
    }

    @Override
    public Object getValor() {
        return "" + mcf.getValor();
    }

    @Override
    public String getDica() {
        return "CPF/CNPJ";
    }

    @Override
    public void setEnabled(boolean status) {
        mcf.setEnabled(status);
        cpf.setEnabled(status);
        cnpj.setEnabled(status);
    }
}
