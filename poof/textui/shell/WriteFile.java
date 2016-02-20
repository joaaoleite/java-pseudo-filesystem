package poof.textui.shell;

import java.io.IOException;

import pt.utl.ist.po.ui.Command;
import pt.utl.ist.po.ui.Display;
import pt.utl.ist.po.ui.Form;
import pt.utl.ist.po.ui.InputString;
import pt.utl.ist.po.ui.InvalidOperation;

import poof.core.*;

/**
 * Command for writing in a file of the current working directory.
 * §2.2.8.
 */
public class WriteFile extends Command<Shell> /* FIXME: select core type for entity */ {

  /**
   * Constructor.
   * 
   * @param entity the target entity.
   */
  public WriteFile(Shell shell) {
    super(MenuEntry.APPEND, shell);
  }

  /**
   * Execute the command.
   */
  @Override
  @SuppressWarnings("nls")
  public final void execute() throws InvalidOperation {
    Form f = new Form(title());
    InputString entr = new InputString(f,Message.nameRequest());
    InputString conteudo = new InputString(f,Message.textRequest());
    f.parse();
    
    entity().escreveFicheiro(entr.value(), conteudo.value() );
    
  }
}
