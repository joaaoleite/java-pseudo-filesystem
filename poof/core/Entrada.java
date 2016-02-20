package poof.core;


public abstract class Entrada implements java.io.Serializable{
	private String _nome;
	private Utilizador _dono;
	private boolean _publico;
	protected Directorio _directorioPai;


	// construtor da class
	public Entrada(String nome, Utilizador dono, boolean publico, Directorio directorioPai){
		_nome = nome;
		_dono = dono;
		_publico = publico;
		_directorioPai = directorioPai;
	} 


	public void renomear(String nome){
		_nome = nome;
	}

	public String obterNome(){
		return _nome;
	}
	public Directorio obterPai(){
		return _directorioPai;
	}

	public abstract int obterTamanho();

	public Utilizador obterDono(){
		return _dono;
	}

	public boolean obterPublico(){
		return _publico;
	}

	public void alterarDono(Utilizador novo){
		_dono = novo;
	}
	public void alterarPermissao(boolean permissao){
		_publico = permissao;
	}
	public void definirPai(Directorio directorio){
		_directorioPai=directorio;
	}

	
}