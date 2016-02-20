package poof.core;
import poof.textui.exception.*;

import java.util.TreeMap;  


public class Utilizador implements java.io.Serializable{
	private String _username;
	private String _nome;
	private Directorio _directorio;


	// construtor da class
	public Utilizador(String username, String nome) throws EntryExistsException,EntryUnknownException,IllegalRemovalException{

		_username = username;
		_nome = nome;
	}

	public String obterUsername(){
		return _username;
	}

	public String obterNome(){
		return _nome;
	}

	public Directorio obterDirectorio(){
		return _directorio;
	}

	public void porDirectorio(Directorio directorio){
		_directorio = directorio;
	}
}