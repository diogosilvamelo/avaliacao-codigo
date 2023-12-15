package componente;

import java.awt.Color;
import java.util.InputMismatchException;
import javax.swing.JOptionPane;
import javax.swing.text.MaskFormatter;

public class MeuCampoCNPJ extends MeuCampoGenerico {

    public MeuCampoCNPJ(boolean obrigatorio, String dica) {
        super(obrigatorio, dica, 12);
        try {
            MaskFormatter mf = new MaskFormatter("##.###.###/####-##");
            mf.install(this);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possível cria o campo CPF!");
        }
        if (eObrigatorio() == true) {
            
        }
    }

    @Override
    public boolean eValido() {
        if (obrigatorio == false) {
            return true;
        }
        String pega_valor = getText();
        pega_valor = pega_valor.replace('.', ' ');
        // tira os pontos e poem espaço
        pega_valor = pega_valor.replace('/', ' ');
        pega_valor = pega_valor.replace('-', ' ');
        pega_valor = pega_valor.replaceAll(" ", "");
        if (pega_valor.equals("00000000000000") || pega_valor.equals("11111111111111")
                || pega_valor.equals("22222222222222") || pega_valor.equals("33333333333333")
                || pega_valor.equals("44444444444444") || pega_valor.equals("55555555555555")
                || pega_valor.equals("66666666666666") || pega_valor.equals("77777777777777")
                || pega_valor.equals("88888888888888") || pega_valor.equals("99999999999999")
                || (pega_valor.length() != 14)) {
            return false;
        }

        char dig13, dig14;
        int sm, i, r, num, peso;

        // "try" - protege o código para eventuais erros de conversao de tipo (int) 
        try { // Calculo do 1o. Digito Verificador 
            sm = 0;
            peso = 2;
            for (i = 11; i >= 0; i--) {
                // converte o i-ésimo caractere do CNPJ em um número: 
// por exemplo, transforma o caractere '0' no inteiro 0 
// (48 eh a posição de '0' na tabela ASCII) 
                num = (int) (pega_valor.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso + 1;
                if (peso == 10) {
                    peso = 2;
                }
            }
            r = sm % 11;
            if ((r == 0) || (r == 1)) {
                dig13 = '0';
            } else {
                dig13 = (char) ((11 - r) + 48);
            }

            // Calculo do 2o. Digito Verificador 
            sm = 0;

            peso = 2;

            for (i = 12; i >= 0; i--) {
                num = (int) (pega_valor.charAt(i) - 48);
                sm = sm + (num * peso);
                peso = peso + 1;

                if (peso == 10) {
                    peso = 2;
                }
            }

            r = sm % 11;

            if ((r == 0) || (r == 1)) {
                dig14 = '0';
            } else {
                dig14 = (char) ((11 - r) + 48);
            }

            // Verifica se os dígitos calculados conferem com os dígitos informados.
            if ((dig13 == pega_valor.charAt(12)) && (dig14 == pega_valor.charAt(13))) {
                return (true);
            } else {
                return (false);
            }
        } catch (InputMismatchException erro) {
            return (false);
        }

    }

}
