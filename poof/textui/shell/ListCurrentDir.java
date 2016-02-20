package poof.textui.shell;

import java.io.IOException;

import pt.utl.ist.po.ui.Command;
import pt.utl.ist.po.ui.Display;
import pt.utl.ist.po.ui.Form;
import pt.utl.ist.po.ui.InputInteger;
import pt.utl.ist.po.ui.InvalidOperation;

import poof.core.*;
import java.util.Collection;

/**
 * Command for showing the content of working directory.
 * §2.2.1.
 */
public class ListCurrentDir extends Command<Shell> /* FIXME: select core type for entity */ {

  /**
   * Constructor.
   * 
   * @param entity the target entity.
   */
  public ListCurrentDir(Shell shell) {
    super(MenuEntry.LS, shell);
  }

  /**
   * Execute the command.
   */
  @Override
  @SuppressWarnings("nls")
  public final void execute() {
    Display d = new Display(title());
    String perm,tipo;

    

    Collection<Entrada> entradas = entity().listar();

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
