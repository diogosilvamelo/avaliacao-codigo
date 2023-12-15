package pojo;

public class Socio {

	private int id;
	private String nome;
	private String telefone;
	private String cpf;
	private int idCooperado;
	private Boolean isEstrangeiro;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public int getIdCooperado() {
		return idCooperado;
	}

	public void setIdCooperado(int idCooperado) {
		this.idCooperado = idCooperado;
	}

	public Boolean getIsEstrangeiro() {
		return isEstrangeiro;
	}

	public void setIsEstrangeiro(Boolean isEstrangeiro) {
		this.isEstrangeiro = isEstrangeiro;
	}

}
