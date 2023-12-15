package componente;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JOptionPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.PlainDocument;

public class MeuCampoMonetario extends MeuCampoFormatado {

	private static final long serialVersionUID = 1L;

	protected boolean permiteNegativo;

	public MeuCampoMonetario(boolean obrigatorio, String dica, int tamanho) {
		super(dica, obrigatorio, tamanho);
		setHorizontalAlignment(RIGHT);
		this.setDocument(new MeuCampoMonetario.MeuDocument(60));
		setText("0.00");
	}

	@Override
	public Object getValor() {
		try {
			String texto = getText().replace(".", "").replace(",", ".");
			return new BigDecimal(texto);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Campo " + getDica() + " contém informação incorreta (CampoMonetario)");
			return null;
		}
	}

	@Override
	public void limpar() {
		setText("0.00");
	}

	@Override
	public boolean eVazio() {
		return getText().equals("0.00");
	}

	@Override
	public boolean eValido() {
		if (!eObrigatorio()) {
			return true;
		}
		try {
			BigDecimal valor = new BigDecimal(getText().replace(".", "").replace(",", "."));
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public void setValor(Object valor) {
		if (valor != null) {
			if (valor instanceof BigDecimal) {
				DecimalFormat df = new DecimalFormat("0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
				setText(df.format(valor));
			} else if (valor instanceof Double) {
				DecimalFormat df = new DecimalFormat("0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));
				setText(df.format((Double) valor));
			} else {
				JOptionPane.showMessageDialog(null,
						"Tipo de entrada (" + valor.getClass().getName() + ") de valor inválido. (CampoMonetario)");
			}
		} else {
			setText("0.00");
		}
	}

	class MeuDocument extends PlainDocument {

		private Integer tamanho;

		public MeuDocument(int tamanho) {
			this.tamanho = tamanho + 1; // tem que contar o separador de decimal (,)
		}

		@Override
		public void insertString(int offs, String str, AttributeSet a) {
			try {
				// Remove caracteres não numéricos, exceto o sinal de menos no início
				str = str.replaceAll("[^0-9-]", "");

				// Verifica se é permitido o sinal negativo
				if (!permiteNegativo && str.contains("-")) {
					return;
				}

				// Obtém o texto atual e remove possíveis pontos e vírgulas
				String valorAtual = getText(0, getLength()).replaceAll("[.,]", "");

				// Adiciona a nova entrada à posição correta
				StringBuilder novoValor = new StringBuilder(valorAtual);
				novoValor.insert(offs, str);

				// Verifica se é um número válido
				if (novoValor.length() > 0 && novoValor.charAt(0) == '-') {
					novoValor.deleteCharAt(0);
					novoValor.insert(0, "-");
				}

				// Converte o valor para Double e formata para exibição
				BigDecimal valorDecimal = new BigDecimal(novoValor.toString()).divide(new BigDecimal(100));
				NumberFormat nf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
				String valorFormatado = nf.format(valorDecimal);

				// Remove possíveis espaços antes do símbolo de moeda
				valorFormatado = valorFormatado.replace("R$ ", "R$");

				// Define o novo valor no campo
				super.remove(0, getLength());
				super.insertString(0, valorFormatado, a);
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Não foi possível formatar / insert(MeuDocument-->CampoMonetario)");
			}
		}

	}
}
