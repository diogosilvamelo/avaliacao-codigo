package tela;

import java.awt.Dimension;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import componente.MeuCampoCodigo;
import componente.MeuCampoDecimal;
import componente.MeuCampoInteiro;
import componente.MeuCampoMonetario;
import componente.MeuComboPesquisa;
import componente.MeuJButton;
import dao.CidadeDao;
import dao.CooperadoDAO;
import dao.EstadoDao;
import dao.FilialDao;
import dao.FinalidadeDAO;
import dao.ItemVendaDAO;
import dao.ProdutoDAO;
import dao.TributacaoDAO;
import pojo.Cidade;
import pojo.ItemVenda;
import pojo.ItemVendaDTO;
import pojo.ValidarFinalidade;
import util.FiltrosConsulta;
import util.TableModelItemVendaDTO;

public class TelaCadastroVenda extends TelaCadastro {

	private MeuCampoCodigo campoCodigoVenda = new MeuCampoCodigo(false, "Código");
	private MeuComboPesquisa campoProduto = new MeuComboPesquisa(ProdutoDAO.COMBOSQL, true, "Produto", 100, null);
	private MeuComboPesquisa campoEstado = new MeuComboPesquisa(ProdutoDAO.COMBOSQL, true, "Estado", 250, null);
	private MeuComboPesquisa campoCooperado = new MeuComboPesquisa(CooperadoDAO.COMBOSQL, true, "Cooperado", 250, null);
	private MeuComboPesquisa campoFilial = new MeuComboPesquisa(FilialDao.COMBOSQL, true, "Filial", 250, null);
	private MeuComboPesquisa campoTributacao = new MeuComboPesquisa(TributacaoDAO.COMBOSQL, true, "Finalidade", 250,
			null);

	private MeuCampoInteiro campoQuantidade = new MeuCampoInteiro(true, "Quantidade", 10, 30);
	private MeuCampoInteiro campoQuantidadeTotal = new MeuCampoInteiro(true, "Quantidade Total", 10, 30);
	private MeuCampoDecimal campoValorProduto = new MeuCampoDecimal(false, "Valor do produto", 10, 30);
	private MeuCampoMonetario campoValorTotal = new MeuCampoMonetario(false, "Valor total", 10);

	protected TableModelItemVendaDTO tableModelVenda = new TableModelItemVendaDTO();
	protected JTable tabelaVenda = new JTable(tableModelVenda);
	protected JScrollPane scrollPane = new JScrollPane(tabelaVenda);

	private Cidade cidade = new Cidade();
	private CidadeDao cidadeDao = new CidadeDao(cidade);

	private ProdutoDAO produtoDao = new ProdutoDAO();
	private CooperadoDAO cooperadoDao = new CooperadoDAO(null);

	private ItemVendaDAO itemVendaoDAO = new ItemVendaDAO();

	JPanel painelDados = new JPanel();
	JPanel painel = new JPanel();
	JPanel painelTotal = new JPanel();

	MeuJButton adicionar = new MeuJButton("Adicionar");

	private static TelaCadastroVenda tela;

	public static TelaCadastroVenda getTela() {
		if (tela == null) {
			tela = new TelaCadastroVenda();
			tela.addInternalFrameListener(new InternalFrameAdapter() {

				@Override
				public void internalFrameClosed(InternalFrameEvent e) {
					TelaSistema.jdp.remove(tela);
					tela = null;
				}
			});
			TelaSistema.jdp.add(tela);
		}
		TelaSistema.jdp.setSelectedFrame(tela);
		TelaSistema.jdp.moveToFront(tela);
		TelaSistema.centraliza(tela);

		return tela;
	}

	public TelaCadastroVenda() {
		super("Tela Cadastro de Vendas");
		scrollPane.setPreferredSize(new Dimension(860, 150));

		painelDados.setPreferredSize(new Dimension(860, 200));
		painelDados.setBorder(BorderFactory.createTitledBorder("Dados"));

		painel.setPreferredSize(new Dimension(900, 200));
		painel.setBorder(BorderFactory.createTitledBorder("itens"));

		painelTotal.setPreferredSize(new Dimension(900, 100));
		painelTotal.setBorder(BorderFactory.createTitledBorder("Total"));

		montarTela();
	}

	public void montarTela() {

		painelDados.setLayout(new GridBagLayout());
		painel.setLayout(new GridBagLayout());
		painelTotal.setLayout(new GridBagLayout());

		adicionaComponentes(1, 1, 1, 60, painelDados, estadoTela);
		adicionaComponentes(2, 1, 1, 60, painel, estadoTela);
		adicionaComponentes(3, 1, 1, 60, painelTotal, estadoTela);

		adicionaComponentes(1, 1, 1, 1, campoCodigoVenda, LADO, painelDados);
		adicionaComponentes(2, 1, 1, 1, campoProduto, LADO, painelDados);
		adicionaComponentes(3, 1, 1, 1, campoCooperado, LADO, painelDados);
		adicionaComponentes(4, 1, 1, 1, campoTributacao, LADO, painelDados);
		adicionaComponentes(5, 1, 1, 1, campoFilial, LADO, painelDados);

		adicionaComponentes(1, 6, 1, 1, campoValorProduto, LADO, painelDados);
		adicionaComponentes(2, 6, 1, 1, campoQuantidade, LADO, painelDados);

		adicionaComponentes(3, 6, 1, 1, adicionar, LADO, painelDados);

		adicionaComponentes(1, 1, 1, 1, campoQuantidadeTotal, LADO, painelTotal);
		adicionaComponentes(1, 6, 1, 1, campoValorTotal, LADO, painelTotal);
		adicionaComponentes(2, 6, 1, 1, scrollPane, LADO, painel);

		habilitarCampos(false);
		listenerAtualizar();
		pack();
		habilitaBotoes();

		adicionar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					adicionarItem();
				} catch (Exception a) {
					a.printStackTrace();
				}
			}
		});

		btConsultar.setVisible(false);
	}

	public void adicionarItem() {

		if (validarFinalidade()) {
			ItemVenda itemVenda = new ItemVenda();
			ItemVendaDAO itemVendaDao = new ItemVendaDAO();

			itemVenda.setId(null);
			itemVenda.setIdVenda((Integer.parseInt((String) "0" + (String) campoCodigoVenda.getValor())));
			itemVenda.getProduto().setId((Integer) campoProduto.getValor());
			itemVenda.getTributacao().setId((Integer) campoTributacao.getValor());
			itemVenda.setQuantidade((Integer) campoQuantidade.getValor());
			itemVenda.setValor(Double.parseDouble(campoValorProduto.getValor().toString()));

			itemVenda = itemVendaDao.incluir(itemVenda);

			campoCodigoVenda.setValor(itemVenda.getIdVenda());
			listarItensVenda(itemVenda);

			calcularTotatis();
			calcularValorVenda();
		} else {
			JOptionPane.showMessageDialog(null, "Operação bloqueada ", "AVISO", JOptionPane.WARNING_MESSAGE);
		}

	}

	public Boolean validarFinalidade() {
		FinalidadeDAO finalidadeDao = new FinalidadeDAO();
		ValidarFinalidade validarFinalidade = new ValidarFinalidade();

		validarFinalidade.setIdGrupoProduto(produtoDao.getIdGrupo((Integer) campoProduto.getValor()));
		validarFinalidade.setIdEstadoOrigem((Integer) campoFilial.getValor());
		validarFinalidade.setIdEstadoConsumo(cooperadoDao.getIdEstadoConsumo((Integer) campoCooperado.getValor()));
		validarFinalidade.setIdTipoPessoa(cooperadoDao.getIdTipoPessoa((Integer) campoCooperado.getValor()));

		validarFinalidade = finalidadeDao.validarFinalidade(validarFinalidade);

		return validarFinalidade.getValidado();
	}

	public void listarItensVenda(ItemVenda itemVenda) {
		ItemVendaDAO itemVendaDao = new ItemVendaDAO();
		List<ItemVendaDTO> itensVendaDTO = itemVendaDao.consultarItensVenda(itemVenda.getIdVenda());
		tableModelVenda.setDados(itensVendaDTO);
	}

	public void calcularTotatis() {
		List<ItemVendaDTO> itensVendaDTO = tableModelVenda.getDados();

		Integer quantidadeTotal = 0;

		for (ItemVendaDTO itemVendaDTO : itensVendaDTO) {
			int quantidade = itemVendaDTO.getQuantidade();
			double valor = itemVendaDTO.getValor();
			double aliquota = itemVendaDTO.getAliquota();

			double totalItem = (valor * quantidade * (1 + (aliquota / 100)));

			itemVendaDTO.setTotalItem(totalItem);
			quantidadeTotal += quantidade;
		}

		campoQuantidadeTotal.setValor(quantidadeTotal);

		tableModelVenda.setDados(itensVendaDTO);
	}

	private void setPersistencia() {
	}

	private void setGUI() {
	}

	@Override
	protected void confirmar() {
		JOptionPane.showMessageDialog(null, "Compra efetuada com sucesso");
		limparCampos();
		temDados = false;
		estadoTela = PADRAO;
		habilitaBotoes();
		habilitarCampos(false);
		tableModelVenda.limparTabela();
		itemVendaoDAO.excluirItens();
	}

	@Override
	protected void novo() {
		super.novo();
	}

	public boolean incluirBD() {
		setPersistencia();
		return cidadeDao.incluir();
	}

	public boolean alterarBD() {
		setPersistencia();
		return cidadeDao.alterar();
	}

	public boolean excluirBD() {
		return cidadeDao.excluir();
	}

	public void listenerAtualizar() {
		campoEstado.combo.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				campoEstado.limpar();
				campoEstado.setSql(EstadoDao.COMBOSQL);
			}
		});
	}

	public void consultar() {
		super.consultar();
		FiltrosConsulta[] filtros = new FiltrosConsulta[] { new FiltrosConsulta("Código", "CIDADE.ID", Integer.class),
				new FiltrosConsulta("Nome", "CIDADE.NOME", String.class),
				new FiltrosConsulta("Estado", "ESTADO.NOME", String.class) };

		new TelaPesquisa(this, "Pesquisa de cidade", new String[] { "Código", "Nome", "Estado" }, filtros,
				CidadeDao.PESQUISARSQL);
	}

	public void carregarDados(int pk) {
		super.carregarDados(pk);
		cidade.setId(pk);
		cidadeDao.consultar();
		setGUI();
	}

	public void calcularValorVenda() {
		List<ItemVendaDTO> itensVendaDTO = tableModelVenda.getDados();
		double valorTotalVenda = 0.0;

		for (ItemVendaDTO itemVendaDTO : itensVendaDTO) {

			double valorItem = itemVendaDTO.getValor();
			int quantidadeItem = itemVendaDTO.getQuantidade();
			double aliquota = itemVendaDTO.getAliquota();

			int gruposProduto = produtoDao.getQuantidadeGrupos((Integer) campoProduto.getValor());
			double descontoProgressivo = Math.min(gruposProduto * 0.01, 0.05);
			double descontoCooperado = 0.0;

			int conceitoCooperado = cooperadoDao.getConceitoPorCooperado((Integer) campoCooperado.getValor());

			if (conceitoCooperado == 'A') {
				descontoCooperado = 0.05;
			} else if (conceitoCooperado == 'B') {
				descontoCooperado = 0.03;
			}

			double valorTotalItem = (valorItem * quantidadeItem * (1 + (aliquota / 100)))
					* (1 - descontoProgressivo - descontoCooperado);

			valorTotalVenda += valorTotalItem;
		}

		campoValorTotal.setValor(valorTotalVenda);
	}

}
