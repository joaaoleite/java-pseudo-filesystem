package poof.textui.shell;

import java.io.IOException;

import pt.utl.ist.po.ui.Command;
import pt.utl.ist.po.ui.Display;
import pt.utl.ist.po.ui.Form;
import pt.utl.ist.po.ui.InputString;
import pt.utl.ist.po.ui.InvalidOperation;

import poof.core.*;
import java.util.*;

import poof.textui.exception.EntryUnknownException;

/**
 * Command for showing an entry of the current working directory.
 * §2.2.2.
 */
public class ListEntry extends Command<Shell> {

  /**
   * Constructor.
   * 
   * @param entity the target entity.
   */
  public ListEntry(Shell shell) {
    super(MenuEntry.LS_ENTRY, shell);
  }

  /**
   * Execute the command.
   */
  @Override
  @SuppressWarnings("nls")
  public final void execute() throws InvalidOperation {
    Form f = new Form(title());
    InputString dir = new InputString(f,Message.nameRequest());
    f.parse();

    Display d = new Display(title());
    String perm,tipo;
    Collection<Entrada> entradas = entity().listarEntrada( dir.value() );

    for (Entrada entrada : entradas ) {
      
      if(entrada.obterPublico()){ perm="w"; }
      else{ perm="-"; }

      if(entrada instanceof Ficheiro){ tipo="-"; }
      else{ tipo="d"; }

      d.addNewLine( tipo+" "+perm+" "+entrada.obterDono().obterUsername()+" "+entrada.obterTamanho()+" "+entrada.obterNome() );

    }
    d.display();
  }
}
