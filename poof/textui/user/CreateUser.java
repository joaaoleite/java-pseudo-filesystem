package poof.textui.user;

import java.io.IOException;

import pt.utl.ist.po.ui.Command;
import pt.utl.ist.po.ui.Display;
import pt.utl.ist.po.ui.Form;
import pt.utl.ist.po.ui.InputString;
import pt.utl.ist.po.ui.InvalidOperation;

import poof.core.*;
import java.util.TreeMap; 


/**
 * Command for creating a user.
 * §2.3.1.
 */
public class CreateUser extends Command<Shell>{

  /**
   * Constructor.
   * 
   * @param entity the target entity.
   */
  public CreateUser(Shell shell) {
    super(MenuEntry.CREATE_USER, shell);
  }

  /**
   * Execute the command.
   */
  @Override
  @SuppressWarnings("nls")
  public final void execute() throws InvalidOperation {
    Form f = new Form(title());
    InputString nome = new InputString(f,Message.nameRequest());
    InputString username = new InputString(f,Message.usernameRequest());
    f.parse();
    entity().criarUtilizador(username.value(),nome.value());


  
    Directorio home = ((Directorio)(entity().obterSF().obterDirectorioRaiz().entrada("home")));

    if(home.obterEntradas().containsKey(username.value())){
      home.remover(username.value());
    }
  }
}


