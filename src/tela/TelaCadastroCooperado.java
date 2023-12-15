package tela;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.lang.reflect.Method;

import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.event.InternalFrameAdapter;
import javax.swing.event.InternalFrameEvent;

import br.com.nordestefomento.jrimum.utilix.ObjectUtil;
import componente.MeuCampoCodigo;
import componente.MeuCampoData;
import componente.MeuCampoFormatado;
import componente.MeuCampoRG;
import componente.MeuCampoTexto;
import componente.MeuComboPesquisa;
import componente.MeuJButton;
import dao.CidadeDao;
import dao.ConceitoDao;
import dao.CooperadoDAO;
import dao.EstadoCivilDao;
import dao.TipoPessoaDao;
import pojo.Cooperado;
import pojo.Socio;
import util.FiltrosConsulta;
import util.TableModelSocios;

public class TelaCadastroCooperado extends TelaCadastro {

	private MeuCampoCodigo campoCodigoCooperado = new MeuCampoCodigo(false, "Código");
	private MeuCampoFormatado campoNomeCooperado = new MeuCampoFormatado(false, "Nome", 25, 80);
	private MeuCampoFormatado campoTelefoneCelular = new MeuCampoFormatado(false, "Celular", 25, 80);
	private MeuComboPesquisa campoCidade = new MeuComboPesquisa(CidadeDao.COMBOSQL, false, "Cidade", 125, null);

	private MeuComboPesquisa campoConceito = new MeuComboPesquisa(ConceitoDao.COMBOSQL, false, "Conceito", 125, null);

	private MeuComboPesquisa campoEstadoCivil = new MeuComboPesquisa(EstadoCivilDao.COMBOSQL, true, "Estado civil", 125,
			null);

	private MeuComboPesquisa campoTipoPessoa = new MeuComboPesquisa(TipoPessoaDao.COMBOSQL, false, "Tipo", 125, null);

	private MeuCampoRG campoRG = new MeuCampoRG(true, "RG");
	private MeuCampoTexto campoCPFCNPJ = new MeuCampoTexto(true, "CPF/CNPJ", 120, 20);
	private MeuCampoData campoDataNascimento = new MeuCampoData("Data de nascimento", true);
	private MeuCampoData campoDataFundacaoEmpresa = new MeuCampoData("Data de fundação", false);

	protected TableModelSocios tableModelSocios = new TableModelSocios();
	protected JTable tabelaSocios = new JTable(tableModelSocios);
	protected JScrollPane scrollPane = new JScrollPane(tabelaSocios);

	MeuJButton adicionar = new MeuJButton("Adicionar Sócios");

	private static TelaCadastroCooperado tela;

	private Cooperado cooperado = new Cooperado();
	private CooperadoDAO CooperadoDAO = new CooperadoDAO(cooperado);

	public TelaCadastroCooperado() {
		super("Tela cadastro de Cooperado");
		scrollPane.setPreferredSize(new Dimension(860, 350));
		montarTela();
	}

	public static TelaCadastroCooperado getTela() {
		if (tela == null) {
			tela = new TelaCadastroCooperado();
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

	public void montarTela() {
		adicionaComponentes(1, 1, 1, 1, campoCodigoCooperado, LADO);
		adicionaComponentes(2, 1, 1, 1, campoNomeCooperado, LADO);
		adicionaComponentes(3, 1, 1, 1, campoTelefoneCelular, LADO);
		adicionaComponentes(4, 1, 1, 1, campoCidade, LADO);
		adicionaComponentes(5, 1, 1, 1, campoDataNascimento, LADO);
		adicionaComponentes(6, 1, 1, 1, campoDataFundacaoEmpresa, LADO);

		adicionaComponentes(1, 6, 1, 1, campoConceito, LADO);
		adicionaComponentes(2, 6, 1, 1, campoTipoPessoa, LADO);
		adicionaComponentes(3, 6, 1, 1, campoEstadoCivil, LADO);
		adicionaComponentes(4, 6, 1, 1, campoRG, LADO);
		adicionaComponentes(5, 6, 1, 1, campoCPFCNPJ, LADO);

		adicionaComponentes(7, 6, 1, 1, adicionar, LADO);
		adicionaComponentes(8, 1, 60, 60, scrollPane, LADO);

		adicionar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {

					if (ObjectUtil.isNull(cooperado.getId())) {
						JOptionPane.showMessageDialog(null, "O cooperado ainda não possui ID!");
					} else {
						if (cooperado.getTipoPessoa().getId() == 2) {
							Method metodoChamaTela = TelaCadastroSocio.class.getMethod("getTela", new Class[] {});
							TelaCadastroSocio telaCadastroSocio = (TelaCadastroSocio) metodoChamaTela.invoke(null,
									new Object[] {});

							telaCadastroSocio.setIdCooperado(cooperado.getId());

							telaCadastroSocio.addInternalFrameListener(new InternalFrameAdapter() {
								@Override
								public void internalFrameClosed(InternalFrameEvent e) {
									listarSocios();
								}
							});
						} else {
							JOptionPane.showMessageDialog(null, "O cooperado selecionado não é uma pessoa jurídica!");
						}
					}

				} catch (Exception a) {
					a.printStackTrace();
				}
			}
		});

		habilitarCampos(false);
		pack();
		habilitaBotoes();

		campoTipoPessoa.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (ObjectUtil.isNotNull(campoTipoPessoa.getValor()) && campoTipoPessoa.getValor().equals(1)) {
					campoDataFundacaoEmpresa.setVisible(false);
					campoDataNascimento.setVisible(true);
					campoDataFundacaoEmpresa.clearValue();
					campoDataNascimento.adicionarObrigatorio();
					campoEstadoCivil.adicionarObrigatorio();
					campoRG.adicionarObrigatorio();
					campoDataFundacaoEmpresa.removeObrigatorio();
				} else if (ObjectUtil.isNotNull(campoTipoPessoa.getValor()) && campoTipoPessoa.getValor().equals(2)) {
					campoDataFundacaoEmpresa.setVisible(true);
					campoDataNascimento.clearValue();
					campoDataNascimento.setVisible(false);
					campoDataNascimento.removeObrigatorio();
					campoEstadoCivil.removeObrigatorio();
					campoRG.removeObrigatorio();
					campoEstadoCivil.setValor(null);
					campoDataFundacaoEmpresa.adicionarObrigatorio();
				}
			}
		});
		campoDataFundacaoEmpresa.setVisible(false);

	}

	public void adicionarSocio() {
	}

	public boolean incluirBD() {
		setPersistencia();
		return CooperadoDAO.incluir();
	}

	public void listarSocios() {
		tableModelSocios.setDados(CooperadoDAO.consultarSocios());
	}

	public void consultar() {
		super.consultar();
		FiltrosConsulta[] filtros = new FiltrosConsulta[] {
				new FiltrosConsulta("Código", "COOPERADO.ID", Integer.class),
				new FiltrosConsulta("Nome", "COOPERADO.NOME", String.class),
				new FiltrosConsulta("Nome", "TIPO_PESSOA.DESCRICAO", String.class) };

		new TelaPesquisa(this, "Pesquisa dde pesquisa de cooperados", new String[] { "Código", "Nome", "Tipo" },
				filtros, CooperadoDAO.PESQUISARSQL);
	}
	
	public boolean excluirBD() {
		return CooperadoDAO.excluir();
	}

	private void setPersistencia() {

		cooperado.setId(Integer.parseInt((String) "0" + (String) campoCodigoCooperado.getValor()));
		cooperado.setNome((String) campoNomeCooperado.getValor());
		cooperado.setTelefone((String) campoTelefoneCelular.getValor());
		cooperado.setRg((String) campoRG.getValor());
		cooperado.setCpfcnpj((String) campoCPFCNPJ.getValor());

		if (ObjectUtil.isNotNull(campoCidade.getValor())) {
			cooperado.getCidade().setId((Integer) campoCidade.getValor());
		}

		if (ObjectUtil.isNotNull(campoDataNascimento.getValue())) {
			cooperado.setDataNascimento((java.util.Date) campoDataNascimento.getValor());
			campoDataFundacaoEmpresa.setVisible(false);
		}

		if (ObjectUtil.isNotNull(campoDataFundacaoEmpresa.getValor())) {
			cooperado.setDataFundacao((java.util.Date) campoDataFundacaoEmpresa.getValor());
			campoDataNascimento.setHabilitado(false);
		}

		if (ObjectUtil.isNotNull(campoConceito.getValor())) {
			cooperado.getConceito().setId((Integer) campoConceito.getValor());
		}

		if (ObjectUtil.isNotNull(campoTipoPessoa.getValor())) {
			cooperado.getTipoPessoa().setId((Integer) campoTipoPessoa.getValor());
		}

		if (ObjectUtil.isNotNull(campoEstadoCivil.getValor())) {
			cooperado.getEstadoCivil().setId((Integer) campoEstadoCivil.getValor());
		}

	}

	public Socio setPersistenciaSocio() {
		Socio socio = new Socio();

		socio.setId((Integer) campoCodigoCooperado.getValor());
		socio.setNome((String) campoNomeCooperado.getValor());
		socio.setTelefone((String) campoTelefoneCelular.getValor());

		return socio;
	}

	private void setGUI() {
		campoCodigoCooperado.setValor(cooperado.getId());
		campoNomeCooperado.setValor(cooperado.getNome());
		campoTelefoneCelular.setValor(cooperado.getTelefone());
		campoRG.setValor(cooperado.getRg());
		campoCPFCNPJ.setValor(cooperado.getCpfcnpj());

		if (ObjectUtil.isNotEmpty(cooperado.getCidade().getId())) {
			campoCidade.setValor(cooperado.getCidade().getId());
		}

		if (cooperado.getEstadoCivil() != null && cooperado.getEstadoCivil().getId() > 0) {
			campoEstadoCivil.setValor(cooperado.getEstadoCivil().getId());
		}

		if (cooperado.getTipoPessoa() != null && cooperado.getTipoPessoa().getId() > 0) {
			campoTipoPessoa.setValor(cooperado.getTipoPessoa().getId());
		}

		if (ObjectUtil.isNotNull(cooperado.getDataNascimento())) {
			campoDataNascimento.setValor(cooperado.getDataNascimento());
			campoDataFundacaoEmpresa.setHabilitado(false);
		}

		if (ObjectUtil.isNotNull(cooperado.getDataFundacao())) {
			campoDataFundacaoEmpresa.setValor(cooperado.getDataFundacao());
			campoDataNascimento.removeObrigatorio();
			campoDataNascimento.setHabilitado(false);
		}

		if (cooperado.getConceito() != null && cooperado.getConceito().getId() > 0) {
			campoConceito.setValor(cooperado.getConceito().getId());
		}

		tableModelSocios.setDados(CooperadoDAO.consultarSocios());
		campoTipoPessoa.setEnabled(false);
	}

	public boolean alterarBD() {
		setPersistencia();
		return CooperadoDAO.alterar();
	}

	public void carregarDados(int pk) {
		super.carregarDados(pk);
		cooperado.setId(pk);
		CooperadoDAO.consultar();
		setGUI();

	}

}
