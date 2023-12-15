package tela;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.table.DefaultTableModel;

import componente.MeuJButtonSecundario;
import imagens.ConstantesImages;
import util.BancoDados;
import util.FiltrosConsulta;

public class TelaPesquisa extends JDialog {

	private TelaCadastro tela;

	private ImageIcon iconeprincipal = new ImageIcon(getClass().getResource(""));

	private ImageIcon icoPesquisar = new ImageIcon(ConstantesImages.iconeTelaPesquisar);
	private MeuJButtonSecundario jbPesquisar = new MeuJButtonSecundario("Pesquisar", icoPesquisar, "Pesquisar");

	private JPanel jpPaiSuperior = new JPanel();
	private JPanel jpSuperior = new JPanel();

	private JPanel jpCentral = new JPanel();
	private JComboBox jcbFiltros = new JComboBox();
	private JTextField campoPesquisa = new JTextField(50);

	private DefaultTableModel tableModel;
	private JTable tabela;
	private JScrollPane painelTabela;

	private String sql;
	private FiltrosConsulta[] filtros;

	private ButtonGroup bg = new ButtonGroup();
	private JRadioButton jrAlfabetico = new JRadioButton("Alfabética");
	private JRadioButton jrNumerico = new JRadioButton("Numérica");
	private static final String solve = "Solve";

	public TelaPesquisa(TelaCadastro tela, String titulo, String[] colunas, FiltrosConsulta[] filtros, String sql) {
		setTitle(titulo);
		this.tela = tela;
		this.filtros = filtros;
		this.sql = sql;
		tableModel = new DefaultTableModel(new Object[][] {}, colunas);
		tabela = new JTable(tableModel) {
			public boolean isCellEditable(int i, int i1) {
				return false;
			}
		};
		painelTabela = new JScrollPane(tabela);
		montaTela();
	}

	private void montaTela() {
		setLayout(new BorderLayout());
		setIconImage(iconeprincipal.getImage());
		add("North", jpPaiSuperior);
		add("Center", jpCentral);

		bg.add(jrAlfabetico);
		bg.add(jrNumerico);
		jpPaiSuperior.setLayout(new BorderLayout());
		jpPaiSuperior.add("Center", jpSuperior);

		jpSuperior.setLayout(new FlowLayout());
		jpSuperior.add(jcbFiltros);
		jpSuperior.add(campoPesquisa);
		jpSuperior.add(jbPesquisar);
		jpSuperior.add(jrAlfabetico);
		jpSuperior.add(jrNumerico);

		KeyStroke enter = KeyStroke.getKeyStroke(KeyEvent.VK_ENTER, 0);
		KeyStroke tab = KeyStroke.getKeyStroke(KeyEvent.VK_TAB, 0);
		tabela.getInputMap(JTable.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(enter, solve);
		tabela.getActionMap().put(solve, new EnterAction());

		jbPesquisar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				pesquisar();
			}
		});

		tabela.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent me) {
				if (me.getClickCount() == 2) {
					int codigo = Integer.parseInt("0" + tableModel.getValueAt(tabela.getSelectedRow(), 0));
					tela.carregarDados(codigo);
					dispose();
				}
			}
		});

		jrAlfabetico.setSelected(true);

		jrAlfabetico.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				pesquisar();
			}
		});

		jrNumerico.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent ae) {
				pesquisar();
			}
		});

		jpCentral.add(painelTabela);
		addFiltros();
		pesquisar();
		pack();
		setSize(TelaSistema.jdp.getWidth(), TelaSistema.jdp.getHeight());
		Dimension tamanhoTela = Toolkit.getDefaultToolkit().getScreenSize();
		setLocation((tamanhoTela.width - getWidth()) / 2, (tamanhoTela.height - getHeight() - 20) / 2);
		painelTabela.setPreferredSize(new Dimension(getWidth() - 30, getHeight() - 100));
		setModal(true);
		setVisible(true);
	}

	private class EnterAction extends AbstractAction {

		@Override
		public void actionPerformed(ActionEvent e) {
			int codigo = Integer.parseInt("0" + tableModel.getValueAt(tabela.getSelectedRow(), 0));
			tela.carregarDados(codigo);
			dispose();
		}
	}

	private void addFiltros() {
		jcbFiltros.addItem("Todos");
		for (int i = 0; i < filtros.length; i++) {
			jcbFiltros.addItem(filtros[i].nomeFiltro);
		}
	}

	private void pesquisar() {
		String tempSql = sql;
		if (jcbFiltros.getSelectedIndex() > 0) {
			if (tempSql.contains(" WHERE ")) {
				tempSql = tempSql + " AND ";
			} else {
				tempSql = tempSql + " WHERE ";
			}
			Class tipo = filtros[jcbFiltros.getSelectedIndex() - 1].tipo;
			if (tipo == Integer.class) {
				tempSql = tempSql + filtros[jcbFiltros.getSelectedIndex() - 1].nomeAtributo + " = ";
				tempSql = tempSql + campoPesquisa.getText();
			} else if (tipo == String.class) {
				tempSql = tempSql + "UPPER(" + filtros[jcbFiltros.getSelectedIndex() - 1].nomeAtributo + ") LIKE '%";
				tempSql = tempSql + campoPesquisa.getText().toUpperCase() + "%'";

				if (jrAlfabetico.isSelected()) {
					tempSql = tempSql + " ORDER BY " + filtros[jcbFiltros.getSelectedIndex() - 2].nomeAtributo + " ASC";
				} else {
					tempSql = tempSql + " ORDER BY " + filtros[jcbFiltros.getSelectedIndex() - 1].nomeAtributo + " ASC";
				}
			}
		}
		while (tableModel.getRowCount() > 0) {
			tableModel.removeRow(0);
		}

		if (jcbFiltros.getSelectedIndex() == 0 && jrAlfabetico.isSelected()) {
			tempSql = tempSql + " ORDER BY NOME ASC";
		} else if (jcbFiltros.getSelectedIndex() == 0 && jrNumerico.isSelected()) {
			tempSql = tempSql + " ORDER BY ID ASC";
		}
		List<Object[]> dados = BancoDados.pesquisar(tempSql);
		for (int i = 0; i < dados.size(); i++) {
			tableModel.addRow(dados.get(i));
		}
	}

}
