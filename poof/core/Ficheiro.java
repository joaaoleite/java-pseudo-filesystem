package poof.core;


public class Ficheiro extends Entrada{
	private String _conteudo="";


	// construtor da class
	public Ficheiro(String nome, Utilizador dono, boolean publico, Directorio directorioPai){
		super(nome,dono,publico,directorioPai);
	}


	public void acrescentar(String conteudo){
		_conteudo += conteudo + "\n";
	}

	public String verConteudo(){
		return _conteudo;
	}

	public int obterTamanho(){
		return _conteudo.length();
	}

}