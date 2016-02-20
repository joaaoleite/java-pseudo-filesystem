package poof.textui.shell;

import java.io.IOException;

import pt.utl.ist.po.ui.Command;
import pt.utl.ist.po.ui.Display;
import pt.utl.ist.po.ui.Form;
import pt.utl.ist.po.ui.InputString;
import pt.utl.ist.po.ui.InputBoolean;
import pt.utl.ist.po.ui.InvalidOperation;

import poof.core.*;

import poof.textui.exception.EntryUnknownException;

/**
 * Command for changing the permission of an entry of the current working directory.
 * §2.2.10.
 */
public class ChangePermission extends Command<Shell> {

  /**
   * Constructor.
   * 
   * @param entity the target entity.
   */
  public ChangePermission(Shell shell) {
    super(MenuEntry.CHMOD,shell);
  }

  /**
   * Execute the command.
   */
  @Override
  @SuppressWarnings("nls")
  public final void execute() throws InvalidOperation {
    Form f = new Form(title());
    InputString entrada = new InputString(f,Message.nameRequest());
    InputString permission = new InputString(f,Message.writeMode());
    f.parse(); 
    
    boolean res = permission.value().equals("s");
    entity().alterarPermissao(entrada.value(), res );
  }

}
