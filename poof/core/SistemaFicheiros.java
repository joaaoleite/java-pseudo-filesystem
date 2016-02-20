package poof.core;

import poof.textui.exception.*;
import java.util.Comparator; 

import java.util.TreeMap;


/**
* Sistema de Ficheiros 
*
* @author	Joao Leite, 77907
* @author	Luis Pereira, 77984
* @author	Tiago Fernandes, 77896
*/
public class SistemaFicheiros implements java.io.Serializable{
	private Directorio _directorioRaiz;
	private TreeMap<String,Utilizador> _utilizadores = new TreeMap<String,Utilizador>();


	/**
	* Construtor do Sistema de Ficheiros
	* 
	* @throws EntryExistsException se ha tentativa de criar uma entrada com um nome nao unico num directorio.
	* @throws EntryUnknownException se e pedido o nome de uma entrada inexistente num directorio.
	* @throws IllegalRemovalException se ha uma tentativa de remover "." ou ".."
	*/
	public SistemaFicheiros() throws EntryUnknownException,EntryExistsException,IllegalRemovalException{
		_directorioRaiz = new Directorio("", null, false, _directorioRaiz);
		Directorio home = _directorioRaiz.criarSubDirectorio("home", null, false);
		Directorio rootDir = home.criarSubDirectorio("root", null, false);
		Utilizador utilizadorRoot = new Utilizador("root", "Super User");
		utilizadorRoot.porDirectorio(rootDir);
		_utilizadores.put("root",utilizadorRoot);
		_directorioRaiz.alterarDono(utilizadorRoot);
		home.alterarDono(utilizadorRoot);
		rootDir.alterarDono(utilizadorRoot);
	}
	
	

	/**
	* Metodo para obter os utilizadores do Sistema de Ficheiros
	* 
	* @return TreeMap<string,Utilizador> devolve todos os utilizadores do Sistema de Ficheiros
	*/
	public TreeMap<String,Utilizador> obterUtilizadores(){
		return _utilizadores;
	}

	/**
	* Metodo para obter o directorio raiz
	* 
	* @return Directorio devolve o directorioRaiz 
	*/
	public Directorio obterDirectorioRaiz(){
		return _directorioRaiz;
	}

	/**
	* Metodo para adicionar utilizador ao sistema de ficheiros
	* Percorre todos os utilizadores para verificar se ja existe algum com o mesmo nome
	* Se isso acontecer lanca a UserExistsException, senao adiciona o utilizador
	*
	*
	* @param username String que representa o identificador unico
	* @param nome String com o nome do utilizador
	*
	* @throws IllegalRemovalException se ha uma tentativa de remover "." ou ".."
	* @throws EntryExistsException se ha tentativa de criar uma entrada com um nome nao unico num directorio.
	* @throws EntryUnknownException se e pedido o nome de uma entrada inexistente num directorio.
	* @throws UserUnknownException se a referencia ao utilizador nao existe no sistema de ficheiros.
	* @throws UserExistsException se na tentativa de criar um utilizador com um username este nao e unico.
	* 
	*/
	public void adicionarUtilizador(String username, String nome, Directorio raiz) throws IllegalRemovalException,EntryExistsException,UserExistsException, UserUnknownException,EntryUnknownException{
		if(_utilizadores.containsKey(username)){
			throw new UserExistsException(username);	
		}else{
			Utilizador novo = new Utilizador(username, nome);
			Directorio home = ((Directorio)(raiz.entrada("home"))).criarSubDirectorio(username, novo, false);
			novo.porDirectorio(home);
			_utilizadores.put(username,novo);
		}
	}

	/**
	* Metodo para obter a ultima entrada do caminho absoluto
	* Usando o string split para dividir o caminho absoluto obtemos o tamanho ate a ultima entrada
	* De seguida se a partir da ultima entrada for ficheiro, devolvemos esse ficheiro 
	* Caso contrario devolve o directorio
	*
	* @param caminho String 
	* 
	* @throws EntryUnknownException se e pedido o nome de uma entrada inexistente num directorio.
	* 
	* @return Ficheiro caso Entrada for do tipo Ficheiro ou Directorio caso Entrada for do tipo Directorio
	* 
	*/
	public Entrada entradaDoCaminho(String caminho) throws EntryUnknownException{
		String[] partes = caminho.split("/");
		int tamanho = partes.length; /* tamanho do caminha dado */
		Directorio actual = _directorioRaiz;
		int i;

		
		for(i=1; i<tamanho-1; i++) 
			actual = (Directorio)((Directorio)(actual)).entrada(partes[i]);

		if(actual.entrada(partes[i]) instanceof Ficheiro)
			return (Ficheiro) actual.entrada(partes[i]);
		else
			return (Directorio) actual.entrada(partes[i]);

	}
	/**
	* Metodo para verificar se ja existe o utilizador com o username pedido e devolve-lo,
	* percorre todos os utilizadores e devolve o utilizador com o username pedido
	*
	* @param username String unica do utilizador
	* 
	* @throws UserUnknownException se a referencia ao utilizador nao existe no sistema de ficheiros.
	* 
	* @return Utilizador pedido
	* 
	*/
	public Utilizador obterUtilizador(String username) throws UserUnknownException{	
		if(_utilizadores.containsKey(username)){
			return _utilizadores.get(username);
		}else{
			throw new UserUnknownException(username); 
		}
	}

}

