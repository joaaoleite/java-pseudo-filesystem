package poof.parser;

import poof.textui.exception.*;
import poof.core.*;


/*import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;*/
import java.io.*;

/**
 * É necessário preencher os métodos createEntry e createUser de acordo com a interface
 * e funcionalidade das entidades do domínio da aplicação concretizadas por cada grupo.
 *
 * Os alunos podem ter utilizados outros nomes para representar as entidades Sistema de Ficheiros,
 * directório (Directory), ficheiro (File) e a super classe comum a directório e ficheiro (Entry).
 * Se for esse o caso, então é necessário substituir neste ficheiro estes nomes pelos nomes utilizados
 * por cada grupo.
 **/

public class ParseFile {

  private SistemaFicheiros _fileSystem;

  public SistemaFicheiros parse(String fileName) throws IOException,FileNotFoundException,UserUnknownException,UserExistsException,IllegalRemovalException,EntryExistsException,EntryUnknownException {

    BufferedReader reader = new BufferedReader(new FileReader(fileName));

    _fileSystem = new SistemaFicheiros();

    String line;

    while ((line = reader.readLine()) != null) {
      parseLine(line);
    }
    
    return _fileSystem;
  }

  public void parseLine(String line) throws UserUnknownException,UserUnknownException,UserUnknownException,IllegalRemovalException,EntryExistsException,EntryUnknownException,EntryUnknownException,UserExistsException{
    String[] args = line.split("\\|");
    
    switch (args[0]) {
    case "USER":
      createUser(args[1], args[2]);
      break;
    case "DIRECTORY":
      createDirectory(args[1], args[2], args[3]);
      break;
    case "FILE":
      createFile(args[1], args[2], args[3], args[4]);
      break;
    }
  }
  
  public void createUser(String username, String name) throws EntryUnknownException,UserUnknownException,UserExistsException,EntryExistsException,IllegalRemovalException {
    _fileSystem.adicionarUtilizador(username,name, _fileSystem.obterDirectorioRaiz());
  }

  private Entrada createEntry(String path, String username, String permission, boolean isDir) throws EntryExistsException,EntryUnknownException,EntryExistsException,EntryUnknownException,EntryExistsException,UserUnknownException,EntryUnknownException {
    int last = path.lastIndexOf('/');
    String parentPath = path.substring(0, last);
    String entryName = path.substring(last + 1);
    Entrada entry;

    Directorio directorio = (Directorio) _fileSystem.entradaDoCaminho(parentPath);
    boolean pub = permission.equals("public");

    Utilizador utilizador = _fileSystem.obterUtilizador(username);
    
    if (isDir){
      directorio.criarSubDirectorio(entryName,utilizador,pub);
      entry = directorio.entrada(entryName); // criar sub directório entryName em directório parentPath por root com permissão permission em _fileSystem
    }
    else{
      directorio.criarFicheiro(entryName,utilizador,pub);
      entry = directorio.entrada(entryName); // criar ficheiro entryName em directório parentPath por root com permissão permission em _fileSystem
    }
    return entry;
  }

  public void createFile(String path, String username, String permission, String content) throws UserUnknownException,EntryExistsException,EntryUnknownException{
    Ficheiro file = (Ficheiro)createEntry(path, username, permission, false);
      
    file.acrescentar(content);
  }

    public void createDirectory(String path, String username, String permission) throws UserUnknownException,EntryExistsException,EntryUnknownException {
      createEntry(path, username, permission, true);
    }    

}
