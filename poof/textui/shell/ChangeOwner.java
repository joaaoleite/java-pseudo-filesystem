package poof.textui.shell;

import java.io.IOException;

import pt.utl.ist.po.ui.Command;
import pt.utl.ist.po.ui.Display;
import pt.utl.ist.po.ui.Form;
import pt.utl.ist.po.ui.InputString;
import pt.utl.ist.po.ui.InvalidOperation;

import poof.core.*;

import poof.textui.exception.EntryUnknownException;

/**
 * Command for changing the owner of an entry of the current working directory.
 * §2.2.11.
 */
public class ChangeOwner extends Command<Shell> {

  /**
   * Constructor.
   * 
   * @param entity the target entity.
   */
  public ChangeOwner(Shell shell) {
    super(MenuEntry.CHOWN, shell);
  }

  /**
   * Execute the command.
   */
  @Override
  @SuppressWarnings("nls")
  public final void execute() throws InvalidOperation {
    Form f = new Form(title());
    InputString entrada = new InputString(f,Message.nameRequest());
    InputString username = new InputString(f,Message.usernameRequest());
    f.parse();
    entity().alterarDono(entrada.value(), username.value() );
  }
}
