package tela;

import componente.MensagensSistema;
import componente.MeuComponente;
import componente.MeuJButtonCadastro;
import componente.MeuPainelBotao;
import imagens.ConstantesImages;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractAction;
import javax.swing.ActionMap;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

public class TelaCadastro extends JInternalFrame implements ActionListener {

	protected Icon icoIncluirMenor = new ImageIcon(getClass().getResource(ConstantesImages.incluirMenor));
	protected Icon icoAlterarMenor = new ImageIcon(getClass().getResource(ConstantesImages.alterarMenor));
	protected Icon icoExcluirMenor = new ImageIcon(getClass().getResource(ConstantesImages.excluirMenor));
	protected Icon icoConsultarMenor = new ImageIcon(getClass().getResource(ConstantesImages.consultarMenor));
	protected Icon icoConfirmarMenor = new ImageIcon(getClass().getResource(ConstantesImages.confirmarMenor));
	protected Icon icoCancelarMenor = new ImageIcon(getClass().getResource(ConstantesImages.cancelarMenor));
	protected Icon icoSairMenor = new ImageIcon(getClass().getResource(ConstantesImages.sairMenor));
	protected Icon icoImprimirMenor = new ImageIcon(getClass().getResource(ConstantesImages.imprimirMenor));

	protected Icon icoIncluirMaior = new ImageIcon(getClass().getResource(ConstantesImages.incluirMaior));
	protected Icon icoAlterarMaior = new ImageIcon(getClass().getResource(ConstantesImages.alterarMaior));
	protected Icon icoExcluirMaior = new ImageIcon(getClass().getResource(ConstantesImages.excluirMaior));
	protected Icon icoConsultarMaior = new ImageIcon(getClass().getResource(ConstantesImages.consultarMaior));
	protected Icon icoConfirmarMaior = new ImageIcon(getClass().getResource(ConstantesImages.confirmarMaior));
	protected Icon icoCancelarMaior = new ImageIcon(getClass().getResource(ConstantesImages.cancelarMaior));
	protected Icon icoSairMaior = new ImageIcon(getClass().getResource(ConstantesImages.sairMaior));
	protected Icon icoImprimirMaior = new ImageIcon(getClass().getResource(ConstantesImages.imprimirMaior));
	protected Icon icoDisquete = new ImageIcon(getClass().getResource(ConstantesImages.icoDisquete));

	protected MeuJButtonCadastro btIncluir = new MeuJButtonCadastro("Incluir um novo registro".toUpperCase(),
			icoIncluirMenor, icoIncluirMaior);
	protected MeuJButtonCadastro btAlterar = new MeuJButtonCadastro("Alterar um registro".toUpperCase(),
			icoAlterarMenor, icoAlterarMaior);
	protected MeuJButtonCadastro btExcluir = new MeuJButtonCadastro("Excluir um registro".toUpperCase(),
			icoExcluirMenor, icoExcluirMaior);
	protected MeuJButtonCadastro btConsultar = new MeuJButtonCadastro("Consultar um registro".toUpperCase(),
			icoConsultarMenor, icoConsultarMaior);
	protected MeuJButtonCadastro btConfirmar = new MeuJButtonCadastro("Confirmar operação".toUpperCase(),
			icoConfirmarMenor, icoConfirmarMaior);
	protected MeuJButtonCadastro btCancelar = new MeuJButtonCadastro("Cancelar operação".toUpperCase(),
			icoCancelarMenor, icoCancelarMaior);
	protected MeuJButtonCadastro btSair = new MeuJButtonCadastro("Sair da Tela".toUpperCase(), icoSairMenor,
			icoSairMaior);
	protected MeuJButtonCadastro btImprimir = new MeuJButtonCadastro("Imprimir".toUpperCase(), icoImprimirMaior,
			icoImprimirMaior);
	protected MeuJButtonCadastro btSalvar = new MeuJButtonCadastro("Salvar".toUpperCase(), icoDisquete, icoDisquete);
	protected static int INCLUINDO = 0;
	protected final int ALTERANDO = 1;
	protected final int EXCLUINDO = 2;
	protected final int CONSULTANDO = 3;
	protected final int PADRAO = 4;
	protected final int CIMA = 5;
	protected final int LADO = 6;
	protected final int DIREITA = 13;
	protected final int ESQUERDA = 17;
	protected int estadoTela = PADRAO;
	Icon iconalert = new ImageIcon("c:/BD/check.png");
	public JPanel botoes = new JPanel();
	Icon iconeincluir = new ImageIcon(getClass().getResource("/imagem/iconeincluir.png"));
	public JButton botaoincluir = new JButton("Incluir", iconeincluir);
	Icon iconealterar = new ImageIcon(getClass().getResource("/imagem/iconealterar.png"));
	public JButton botaoalterar = new JButton("Alterar", iconealterar);
	Icon iconexcluir = new ImageIcon(getClass().getResource("/imagem/iconeexcluir.png"));
	public JButton botaoexcluir = new JButton("Excluir", iconexcluir);
	Icon iconeconsultar = new ImageIcon(getClass().getResource("/imagem/iconeconsultar.png"));
	public JButton botaoconsultar = new JButton("Consultar", iconeconsultar);
	Icon iconeconfirmar = new ImageIcon(getClass().getResource("/imagem/confirmar.png"));
	public JButton botaoconfirmar = new JButton("Confirmar", iconeconfirmar);
	Icon iconecancelar = new ImageIcon(getClass().getResource("/imagem/cancelar.png"));
	public JButton botaocancelar = new JButton("Cancelar", iconecancelar);
	Icon iconepesquisar = new ImageIcon(getClass().getResource("/imagem/pesquisarcad.png"));
	public JButton botaopesquisar = new JButton(iconepesquisar);
	public JButton botaopesquisargenerico = new JButton(iconepesquisar);
	public JButton botaopesquisargenerico1 = new JButton(iconepesquisar);

	JLabel rotuloMig;
	protected JPanel jpPaiBotao = new JPanel();
	public JPanel jpComponentes = new JPanel();
	public JPanel jpPainel = new JPanel();
	protected MeuPainelBotao meuPainelBotoes = new MeuPainelBotao(jpPaiBotao);
	boolean resultado;
	boolean erro;
	protected boolean temDados = false;
	JPanel painelCentral = new JPanel();
	protected List<MeuComponente> campos = new ArrayList();
	protected GridBagConstraints gbc = new GridBagConstraints();

	public TelaCadastro(String titulo) {
		super(titulo, true, true, false, false);
		initComponent();
		executaAcao();
	}

	private void initComponent() {
		setVisible(true);
		jpComponentes.setLayout(new GridBagLayout());
		jpPainel.setLayout(new GridBagLayout());
		painelCentral.setLayout(new GridBagLayout());
		getContentPane().add("South", jpPaiBotao);
		getContentPane().add("West", jpComponentes);
		estadoTela = PADRAO;
		jpPaiBotao.setPreferredSize(new Dimension(350, 55));
		meuPainelBotoes.setLayout(new GridLayout(1, 8, 1, 1));
		meuPainelBotoes.setPreferredSize(new Dimension(350, 50));
		addBotoes(meuPainelBotoes, btIncluir);
		addBotoes(meuPainelBotoes, btAlterar);
		addBotoes(meuPainelBotoes, btExcluir);
		addBotoes(meuPainelBotoes, btConsultar);
		addBotoes(meuPainelBotoes, btConfirmar);
		addBotoes(meuPainelBotoes, btCancelar);
		addBotoes(meuPainelBotoes, btSair);
	}

	public void desativarBotoes() {
		btConsultar.setVisible(false);
		btCancelar.setVisible(false);
		btExcluir.setVisible(false);
		btIncluir.setVisible(false);
		btAlterar.setVisible(false);
		btConfirmar.setVisible(false);
		btSair.setVisible(false);
	}

	public void executaAcao() {

		InputMap iMap = this.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		ActionMap aMap = this.getActionMap();
		iMap.put(KeyStroke.getKeyStroke("F1"), "f1");
		iMap.put(KeyStroke.getKeyStroke("F2"), "f2");
		iMap.put(KeyStroke.getKeyStroke("F3"), "f3");
		iMap.put(KeyStroke.getKeyStroke("F4"), "f4");
		iMap.put(KeyStroke.getKeyStroke("F12"), "enter");
		iMap.put(KeyStroke.getKeyStroke("F7"), "sair");
		iMap.put(KeyStroke.getKeyStroke("F11"), "esc");
		iMap.put(KeyStroke.getKeyStroke("UP"), "cima");
		iMap.put(KeyStroke.getKeyStroke("DOWN"), "baixo");
		iMap.put(KeyStroke.getKeyStroke("LEFT"), "esqueda");
		iMap.put(KeyStroke.getKeyStroke("RIGHT"), "direita");
		aMap.put("f1", new novoAction());
		aMap.put("f2", new alterarAction());
		aMap.put("f3", new excluirAction());
		aMap.put("f4", new consultarAction());
		aMap.put("enter", new confirmarAction());
		aMap.put("esc", new cancelaActio());
		aMap.put("sair", new sairAction());
		aMap.put("cima", new acimaAction());
		aMap.put("baixo", new baixoAction());
		aMap.put("esquerda", new esquerdaAction());
		aMap.put("direita", new direitaAction());
	}

	class novoAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent ez) {
			if (estadoTela == PADRAO) {
				novo();
				habilitaBotoes();
			} else {
				JOptionPane.showMessageDialog(null, "operação de INCLUSÃO não pode ser efetuada no momento", "AVISO", 1,
						iconalert);
			}
		}
	}

	class alterarAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent ex) {
			if (temDados == true || estadoTela == EXCLUINDO) {
				alterar();
				habilitaBotoes();
			} else {
				JOptionPane.showMessageDialog(null, "operação de ALTERAÇÃO não pode ser efetuada no momento", "AVISO",
						1, iconalert);
			}
		}
	}

	class excluirAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent ec) {
			if (temDados == true && estadoTela != ALTERANDO && estadoTela != INCLUINDO) {
				excluir();
				habilitaBotoes();
			} else {
				JOptionPane.showMessageDialog(null, "operação de EXCLUSÃO não pode ser efetuada no momento", "AVISO", 1,
						iconalert);
			}
		}
	}

	class consultarAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent ev) {
			if (estadoTela != INCLUINDO && estadoTela != ALTERANDO && estadoTela != EXCLUINDO) {
				consultar();
			} else {
				JOptionPane.showMessageDialog(null, "operação de CONSULTA não pode ser efetuada no momento", "AVISO", 1,
						iconalert);
			}
		}
	}

	class confirmarAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent eb) {
			if (estadoTela != PADRAO || estadoTela != CONSULTANDO) {
				confirmar();
			} else {
				JOptionPane.showMessageDialog(null, "operação de CONFIRMAR não pode ser efetuada no momento", "AVISO",
						1, iconalert);
			}
		}
	}

	class cancelaActio extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent en) {
			if (estadoTela != PADRAO) {
				cancelar();
			} else {
				JOptionPane.showMessageDialog(null, "operação de CANCELAR não pode ser efetuada no momento", "AVISO", 1,
						iconalert);
			}

		}
	}

	class sairAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent em) {
			if (estadoTela == PADRAO || estadoTela != INCLUINDO || estadoTela != ALTERANDO && estadoTela != EXCLUINDO) {
				sair();
			} else {
				JOptionPane.showMessageDialog(null, "operação de sair não pode ser efetuada no momento", "AVISO", 1,
						iconalert);
			}

		}

	}

	class acimaAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent ea) {
			JOptionPane.showMessageDialog(null, "cima");
			System.out.println("" + jpComponentes.getComponents());
		}

	}

	class baixoAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent es) {
			if (estadoTela == PADRAO || estadoTela != INCLUINDO || estadoTela != ALTERANDO && estadoTela != EXCLUINDO) {
				sair();
			} else {
				JOptionPane.showMessageDialog(null, "operação de sair não pode ser efetuada no momento", "AVISO", 1,
						iconalert);
			}

		}

	}

	class esquerdaAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent ed) {
			if (estadoTela == PADRAO || estadoTela != INCLUINDO || estadoTela != ALTERANDO && estadoTela != EXCLUINDO) {
				sair();
			} else {
				JOptionPane.showMessageDialog(null, "operação de sair não pode ser efetuada no momento", "AVISO", 1,
						iconalert);
			}

		}

	}

	class direitaAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (estadoTela == PADRAO || estadoTela != INCLUINDO || estadoTela != ALTERANDO && estadoTela != EXCLUINDO) {
				sair();
			} else {
				JOptionPane.showMessageDialog(null, "operação de sair não pode ser efetuada no momento", "AVISO", 1,
						iconalert);
			}

		}

	}

	protected void addBotoes(JPanel p, MeuJButtonCadastro b) {
		p.add(b);
		b.addActionListener(this);
	}

	public void pack() {
		super.pack();
		setSize(getSize().width, getSize().height + 10);
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == btIncluir) {
			novo();
		} else if (e.getSource() == btExcluir) {
			excluir();
		} else if (e.getSource() == btAlterar) {
			alterar();
		} else if (e.getSource() == btConfirmar) {
			confirmar();
		} else if (e.getSource() == btCancelar) {
			cancelar();
		} else if (e.getSource() == btConsultar) {
			consultar();
		} else if (e.getSource() == btSair) {
			sair();
		}
		habilitaBotoes();
	}

	protected void novo() {
		estadoTela = INCLUINDO;
		limparCampos();
		habilitarCampos(true);
	}

	protected void alterar() {
		estadoTela = ALTERANDO;
		habilitarCampos(true);
	}

	protected void excluir() {
		estadoTela = EXCLUINDO;
		habilitarCampos(false);
	}

	protected void consultar() {
	}

	protected void consultarEmpregado() {
	}

	protected void confirmar() {
		if (estadoTela == INCLUINDO && validarCampos() && incluirBD()) {
			metodos();
		}
		if (estadoTela == ALTERANDO && validarCampos() && alterarBD()) {
			metodos();
		}
		if (estadoTela == EXCLUINDO && excluirBD()) {
			metodos();
		}
	}

	protected void metodos() {
		estadoTela = PADRAO;
		temDados = false;
		habilitaBotoes();
		habilitarCampos(false);
		limparCampos();
	}

	protected void habilitaBotoes() {
		btIncluir.setEnabled(estadoTela == PADRAO);
		btAlterar.setEnabled(estadoTela == PADRAO && temDados == true);
		btExcluir.setEnabled(estadoTela == PADRAO && temDados == true);
		btConfirmar.setEnabled(estadoTela != PADRAO);
		btCancelar.setEnabled(estadoTela != PADRAO);
		btConsultar.setEnabled(estadoTela == PADRAO);
		btSair.setEnabled(estadoTela == PADRAO);
		btIncluir.mudaStatus(estadoTela == PADRAO);
		btAlterar.mudaStatus(estadoTela == PADRAO && temDados == true);
		btExcluir.mudaStatus(estadoTela == PADRAO && temDados == true);
		btConfirmar.mudaStatus(estadoTela != PADRAO);
		btCancelar.mudaStatus(estadoTela != PADRAO);
		btConsultar.mudaStatus(estadoTela == PADRAO);
		btSair.mudaStatus(estadoTela == PADRAO);
	}

	protected void cancelar() {
		if (estadoTela == INCLUINDO || estadoTela == ALTERANDO || estadoTela == CONSULTANDO
				|| estadoTela == EXCLUINDO) {
			int operacao = MensagensSistema.MensagemConfirmarOperacao("Deseja realmente cancelar a operação?");
			if (operacao == JOptionPane.OK_OPTION) {
				limparCampos();
				temDados = false;
				estadoTela = PADRAO;
				habilitaBotoes();
				habilitarCampos(false);
			}
		}
	}

	protected void sair() {
		int operacao = MensagensSistema.MensagemConfirmarOperacao("Deseja realmente sair da tela?");
		if (operacao == JOptionPane.OK_OPTION) {
			metodos();
			dispose();
		}
	}

	protected void habilitarCampos(boolean status) {
		for (int i = 0; i < campos.size(); i++) {
			campos.get(i).setEnabled(status);
		}
	}

	protected void limparCampos() {
		try {
			for (int i = 0; i < campos.size(); i++) {
				if (campos.get(i) != null && campos.get(i).getClass().getMethod("limpar") != null) {
					campos.get(i).limpar();
				}
			}
		} catch (Exception e) {
		}

	}

	protected boolean validarCampos() {
		String mensagemInvalidos = "";
		String mensagemObrigatoriosNaoPreenchidos = "";

		for (int i = 0; i < campos.size(); i++) {
			if (!campos.get(i).eValido()) {
				mensagemInvalidos = mensagemInvalidos + campos.get(i).getDica() + "\n\n";
			}
			if (campos.get(i).eObrigatorio() && campos.get(i).eVazio()) {
				mensagemObrigatoriosNaoPreenchidos = mensagemObrigatoriosNaoPreenchidos + "\n\n"
						+ campos.get(i).getDica();
			}
		}
		if (!mensagemInvalidos.isEmpty()) {
			MensagensSistema.MensagemAtencao("OS CAMPOS ABAIXO ESTÃO INVÁLIDOS:\n\n" + mensagemInvalidos);
		}
		if (!mensagemObrigatoriosNaoPreenchidos.isEmpty()) {
			MensagensSistema.MensagemAtencao("OS CAMPOS ABAIXO SÃO OBRIGATÓRIOS E NÃO FORAM PREENCHIDOS CORRETAMENTE: "
					+ mensagemObrigatoriosNaoPreenchidos);
		}

		return (mensagemInvalidos.isEmpty() && mensagemObrigatoriosNaoPreenchidos.isEmpty());
	}

	public void adicionaComponentes(int linha, int coluna, int altura, int largura, JComponent componente,
			int posicaoRotulo) {
		gbc = new GridBagConstraints();
		gbc.gridy = linha;
		gbc.gridx = coluna;
		gbc.insets = new Insets(2, 2, 2, 2);

		if (componente instanceof MeuComponente) {
			campos.add((MeuComponente) componente);
			String nomeRotulo = "<html><body>" + ((MeuComponente) componente).getDica();
			if (((MeuComponente) componente).eObrigatorio()) {
				nomeRotulo = nomeRotulo + "<font color = red> *</font>";
			}
			nomeRotulo = nomeRotulo + "</body></html>";
			JLabel rotulo = new JLabel(nomeRotulo);
			gbc.gridheight = 1;
			gbc.gridwidth = 1;
			gbc.anchor = GridBagConstraints.WEST;// ORIENTAÇÃO DOS CAMPOS
			jpComponentes.add(rotulo, gbc);
			if (posicaoRotulo == CIMA) {
				gbc.gridy++;
			} else {
				gbc.gridx++;
			}
		}
		gbc.gridheight = altura;
		gbc.gridwidth = largura;
		gbc.anchor = GridBagConstraints.WEST;// ORIENTAÇÃO DO RÓTULO
		jpComponentes.add(componente, gbc);

	}

	public void adicionaComponentes(int linha, int coluna, int altura, int largura, JComponent componente,
			int posicaoRotulo, JPanel jp) {
		componente.setEnabled(false);
		gbc = new GridBagConstraints();
		gbc.gridy = linha;
		gbc.gridx = coluna;
		gbc.insets = new Insets(2, 5, 2, 2);
		if (componente instanceof MeuComponente) {
			campos.add((MeuComponente) componente);
			String nomeRotulo = "<html><body>" + ((MeuComponente) componente).getDica();
			if (((MeuComponente) componente).eObrigatorio()) {
				nomeRotulo = nomeRotulo + "<font color = red> *</font>";
			}
			nomeRotulo = nomeRotulo + "</body></html>";
			JLabel rotulo = new JLabel(nomeRotulo);
			gbc.gridheight = 1;
			gbc.gridwidth = 1;
			gbc.anchor = GridBagConstraints.WEST;// ORIENTAÇÃO DOS CAMPOS
			jp.add(rotulo, gbc);
			if (posicaoRotulo == CIMA) {
				gbc.gridy++;
			} else {
				gbc.gridx++;
			}
		}
		gbc.gridheight = altura;
		gbc.gridwidth = largura;
		gbc.anchor = GridBagConstraints.WEST;// ORIENTAÇÃO DO RÓTULO
		jp.add(componente, gbc);
	}

	public void adicionaComponentes(int linha, int coluna, int linhas, int colunas, int espacoHorizontal,
			int espacoVertical, int alinhamento, JComponent componente, JPanel painel, int redimensionamento, int iTop,
			int iLeft, int iBottom, int iRight) {

		componente.setEnabled(false);
		gbc.anchor = GridBagConstraints.EAST;
		gbc.gridx = coluna;
		gbc.gridy = linha;
		gbc.gridwidth = colunas;
		gbc.gridheight = linhas;
		gbc.weightx = espacoHorizontal;
		gbc.weighty = espacoVertical;
		gbc.anchor = alinhamento;
		gbc.fill = redimensionamento;
//        gbc.gridx = gbc.gridx + gbc.gridwidth;
		gbc.insets = new Insets(iTop, iLeft, iBottom, iRight);
		painel.add((JComponent) componente, gbc);
		// Os componentes estão add dentro do Vetor
		campos.add((MeuComponente) componente);

	}

	protected void carregarDados(int pk) {
		temDados = true;
		habilitaBotoes();
		// implementar na subclasse
	}

	protected void carregarDadosEmpregado(int pks) {
		temDados = true;
		habilitaBotoes();
	}

	protected boolean incluirBD() {
		return true;
	}

	protected boolean alterarBD() {
		return true;
	}

	protected boolean excluirBD() {
		return true;
	}
}
