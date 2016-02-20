package poof.core;

import java.util.TreeMap;  
import java.util.Comparator;

import poof.textui.exception.*;


public class Directorio extends Entrada {
	private TreeMap<String,Entrada> _entradas = new TreeMap<String,Entrada>();

	// construtor da class (criar novo directorio)
	public Directorio(String nome, Utilizador dono, boolean publico, Directorio directorioPai){
		super(nome,dono,publico,directorioPai);
		_entradas.put("..",directorioPai);
		_entradas.put(".",this);
	}

	//copiar directorio
	public Directorio(Directorio dir, String nome){
		super(nome,dir.obterDono(),dir.obterPublico(),dir.obterPai());
		_entradas = dir._entradas;
	}

	public int obterTamanho(){
		return (_entradas.size()+2)*8; /* (numero de entradas + pai + proprio) * tamanho predefinido */
	}

	public TreeMap<String,Entrada> obterEntradas(){
		return _entradas;
	}

	public Directorio criarSubDirectorio(String nome, Utilizador dono, boolean publico) throws EntryExistsException{
		if(_entradas.containsKey(nome)){
			throw new EntryExistsException(nome);
		}

		Directorio novo = new Directorio(nome, dono, publico, this);
		_entradas.put(nome,novo);
		
		return novo;
	}

	public Ficheiro criarFicheiro(String nome, Utilizador dono, boolean publico) throws EntryExistsException{
		if(_entradas.containsKey(nome)){
			throw new EntryExistsException(nome);
		}

		Ficheiro novo = new Ficheiro(nome, dono, publico, this);
		_entradas.put(nome,novo);
		return novo;
	}

	public String obterCaminhoAbsoluto(){
		String caminho="";
		Entrada actual = (Entrada) this;
		while(!actual.obterNome().equals("")){
			caminho = actual.obterNome() + "/" + caminho;
			actual = actual.obterPai();
		}
		return "/" + caminho.substring(0,caminho.length()-1);
	}

	/* Devolver entrada com o nome dado, se não existir dar erro */
	public Entrada entrada(String nome) throws EntryUnknownException{
		try{ return _entradas.get(nome); }
		catch(Exception e){ throw new EntryUnknownException(nome); }
	}
	
	public void remover(String nome) throws EntryUnknownException,IllegalRemovalException{
		
		if(nome.equals("..") || nome.equals(".") || nome.equals("/"))
			throw new IllegalRemovalException();

		try{ _entradas.remove(nome); }
		catch(Exception e){ throw new EntryUnknownException(nome); }
	}

}