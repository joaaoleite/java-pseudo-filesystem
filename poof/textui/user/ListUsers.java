package poof.textui.user;

import java.io.IOException;

import pt.utl.ist.po.ui.Command;
import pt.utl.ist.po.ui.Display;
import pt.utl.ist.po.ui.InvalidOperation;

import poof.core.*;
import java.util.*;


/**
 * Command for the showing existing users.
 * §2.3.2.
 */
public class ListUsers extends Command<Shell>{

  /**
   * Constructor.
   * 
   * @param entity the target entity.
   */
  public ListUsers(Shell shell) {
    super(MenuEntry.LIST_USERS, shell);
  }

  /**
   * Execute the command.
   */
  @Override
  @SuppressWarnings("nls")
  public final void execute() throws InvalidOperation {
    Display d = new Display(title());

    Collection<Utilizador> utilizadores = entity().obterSF().obterUtilizadores().values();

    for (Utilizador utilizador : utilizadores ) {
            
      d.addNewLine( utilizador.obterUsername()+" "+utilizador.obterNome()+" "+utilizador.obterDirectorio() );

    }
    d.display();
  }
}
