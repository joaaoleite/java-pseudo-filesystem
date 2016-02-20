package poof.textui.shell;

import java.io.IOException;

import pt.utl.ist.po.ui.Command;
import pt.utl.ist.po.ui.Display;
import pt.utl.ist.po.ui.Form;
import pt.utl.ist.po.ui.InputString;
import pt.utl.ist.po.ui.InvalidOperation;

import poof.core.*;

/**
 * Command for viewing the content of a file of the current working directory.
 * §2.2.9.
 */
public class ViewFile extends Command<Shell> {

  /**
   * Constructor.
   * 
   * @param entity the target entity.
   */
  public ViewFile(Shell shell) {
    super(MenuEntry.CAT, shell);
  }

  /**
   * Execute the command.
   */
  @Override
  @SuppressWarnings("nls")
  public final void execute() throws InvalidOperation {
    Form f = new Form(title());
    InputString entr = new InputString(f,Message.nameRequest());
    f.parse();
    

    Display d = new Display(title());
    d.add(entity().verFicheiro(entr.value()) );
    d.display();
  }
}
