package poof.core;

import java.io.*;
import java.lang.ClassNotFoundException;
import java.util.Collection;

import java.util.*;
import pt.utl.ist.po.ui.*;

import poof.textui.exception.*;


public class Shell{
	private Directorio _directorioActual;
	private Utilizador _utilizadorActual;
	private SistemaFicheiros _sistemaFicheirosActual;
	private String _nome;
	private boolean _faltaGuardar;


	public void importSF(SistemaFicheiros sf) throws UserUnknownException{
		_sistemaFicheirosActual=sf;
		_utilizadorActual=sf.obterUtilizador("root");
		_directorioActual=sf.obterUtilizador("root").obterDirectorio();
	}

	public String obterNome(){
		return _nome;
	}
	public void definirNome(String nome){
		_nome=nome;
	}

	// métodos para gerir sistema de ficheiros
	public void criar() throws UserUnknownException,EntryUnknownException,EntryExistsException,IllegalRemovalException{
		_sistemaFicheirosActual = new SistemaFicheiros();
		_utilizadorActual = _sistemaFicheirosActual.obterUtilizador("root");
		_directorioActual = (Directorio) _sistemaFicheirosActual.obterDirectorioRaiz().entrada("home");
		_directorioActual = (Directorio) _directorioActual.entrada("root");
		_nome=null;
		_faltaGuardar=false;
	}
	public void abrir(ObjectInputStream in) throws UserUnknownException{
		try {
            int numObjs = in.readInt();
            SistemaFicheiros[] values = new SistemaFicheiros[numObjs];
            for (int i = 0; i < numObjs; ++i) {
				values[i] = (SistemaFicheiros)in.readObject();
      		}	

            _sistemaFicheirosActual = values[0];
            _utilizadorActual = _sistemaFicheirosActual.obterUtilizador("root");
            _directorioActual = _utilizadorActual.obterDirectorio();
        }
        catch (ClassNotFoundException|IOException ex) {
            ex.printStackTrace();
        } 
	}
	public void guardar(String filename){

		try {
            ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(filename)));
              
            out.writeInt(1);   
            out.writeObject(_sistemaFicheirosActual);
            out.flush();
            out.close();
            _nome=filename;
            _faltaGuardar=false;

        } catch (IOException ex) {
            ex.printStackTrace();
        } 

	}
	public boolean faltaGuardar(){
		return _faltaGuardar;
	}


	public void login(String username) throws UserUnknownException{
		_utilizadorActual = _sistemaFicheirosActual.obterUtilizador(username);
		_directorioActual = _sistemaFicheirosActual.obterUtilizador(username).obterDirectorio();

	}

	// funcionalidades da Shell
	public Collection listarEntrada(String directorio) throws EntryUnknownException,IsNotDirectoryException{
		Directorio tmp = _directorioActual;
		alterarDirectorio(directorio);
		Collection entradas = listar();
		_directorioActual = tmp;
		return entradas;

	}
	public Collection listar(){
		return _directorioActual.obterEntradas().values();
	}

	public void removerEntrada(String nome) throws UserUnknownException,EntryUnknownException,AccessDeniedException,IllegalRemovalException{
		Entrada entrada = _directorioActual.entrada(nome);
		temPermissao(entrada);
		_directorioActual.remover(nome);
		_faltaGuardar=true;
	}
	public void alterarDirectorio(String nome) throws EntryUnknownException,IsNotDirectoryException{
		if( nome.equals("..") ){
				_directorioActual=_directorioActual.obterPai();	
				_faltaGuardar=true;
		}
		else if( !nome.equals(".") ){
			if( _directorioActual.entrada(nome) instanceof Directorio ){
				_directorioActual = (Directorio) _directorioActual.entrada(nome);
				_faltaGuardar=true;
			}
			else throw new IsNotDirectoryException(nome);
		}
	}
	public void criarFicheiro(String nome) throws EntryExistsException{
		_directorioActual.criarFicheiro(nome, _utilizadorActual, false);
		_faltaGuardar=true;

	}
	public void criarDirectorio(String nome) throws EntryExistsException{
		_directorioActual.criarSubDirectorio(nome, _utilizadorActual, false);
		_faltaGuardar=true;

	}
	public String caminhoActual(){
		return _directorioActual.obterCaminhoAbsoluto();

	}
	public void escreveFicheiro(String nome, String conteudo) throws UserUnknownException,EntryUnknownException,AccessDeniedException,IsNotFileException{
		
		if( _directorioActual.entrada(nome) instanceof Ficheiro  ){	
			Ficheiro entrada = (Ficheiro) _directorioActual.entrada(nome);
			temPermissao( (Entrada) entrada);
			entrada.acrescentar(conteudo);
			_faltaGuardar=true;
		}
		else throw new IsNotFileException(nome);	

	}
	public String verFicheiro(String nome) throws EntryUnknownException{
		Ficheiro entrada = (Ficheiro) _directorioActual.entrada(nome);
		return entrada.verConteudo();

	}

	public Collection listarUtilizadores(){
		return _sistemaFicheirosActual.obterUtilizadores().values();
	}

	public void alterarDono(String nome, String username) throws UserUnknownException,EntryUnknownException,AccessDeniedException{
		Entrada entrada = _directorioActual.entrada(nome);

		temPermissao(entrada);
		Utilizador utilizador = _sistemaFicheirosActual.obterUtilizador(username);
		entrada.alterarDono(utilizador);
		_faltaGuardar=true;
	}

	public void alterarPermissao(String nome, boolean permissao) throws UserUnknownException,EntryUnknownException,AccessDeniedException{
		Entrada entrada = _directorioActual.entrada(nome);

		temPermissao(entrada);
		entrada.alterarPermissao(permissao);
		_faltaGuardar=true;
	}

	/*
	Verificar se utilizador actual tem permissão para alterar entrada dada.
	*/
	public void temPermissao(Entrada entrada) throws AccessDeniedException,UserUnknownException{
		if(!(entrada.obterDono().obterUsername().equals(_utilizadorActual.obterUsername() ) || _utilizadorActual.obterUsername().equals(_sistemaFicheirosActual.obterUtilizador("root").obterUsername() )) ){
			throw new AccessDeniedException(_utilizadorActual.obterUsername());	
		}
	}

	public void criarUtilizador(String username, String nome) throws EntryExistsException,UserUnknownException,UserExistsException,EntryUnknownException,IllegalRemovalException{
		_sistemaFicheirosActual.adicionarUtilizador(username,nome, _sistemaFicheirosActual.obterDirectorioRaiz());
		_faltaGuardar=true;
	}

	public SistemaFicheiros obterSF(){
		return _sistemaFicheirosActual;
	}


}
