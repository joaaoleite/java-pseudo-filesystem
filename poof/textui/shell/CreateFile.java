package poof.textui.shell;

import java.io.IOException;

import pt.utl.ist.po.ui.Command;
import pt.utl.ist.po.ui.Display;
import pt.utl.ist.po.ui.Form;
import pt.utl.ist.po.ui.InputString;
import pt.utl.ist.po.ui.InvalidOperation;

import poof.core.*;

/**
 * Command for creating a file in the current working directory.
 * §2.2.5.
 */
public class CreateFile extends Command<Shell> {

  /**
   * Constructor.
   * 
   * @param entity the target entity.
   */
  public CreateFile(Shell shell) {
    super(MenuEntry.TOUCH, shell);
  }

  /**
   * Execute the command.
   */
  @Override
  @SuppressWarnings("nls")
  public final void execute() throws InvalidOperation {
    Form f = new Form(title());
    InputString file = new InputString(f,Message.fileRequest());
    f.parse();
    entity().criarFicheiro(file.value());
  }
}
