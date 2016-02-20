package poof.textui.main;

import java.io.IOException;

import pt.utl.ist.po.ui.Command;
import pt.utl.ist.po.ui.Display;
import pt.utl.ist.po.ui.Form;
import pt.utl.ist.po.ui.InputBoolean;
import pt.utl.ist.po.ui.InvalidOperation;

import poof.core.*;

/**
 * Command for creating a new file system and logging the root user.
 */
public class New extends Command<Shell> {
  /**
   * Constructor.
   * 
   * @param entity the target entity.
   */
  public New(Shell shell) {
    super(MenuEntry.NEW, shell);
  }

  /**
   * Execute the command.
   */
  @Override
  @SuppressWarnings("nls")
  public final void execute() throws InvalidOperation {
      
    if(entity().faltaGuardar()){
      Form f = new Form(title());
      InputBoolean pergunta = new InputBoolean(f,Message.saveBeforeExit());
      f.parse();

      if(pergunta.value() ){
        ((MainMenu) menu()).entry(2).execute();
      }
    }
    
    entity().criar();
    ((MainMenu) menu()).showOptionsNonEmpty();
  
  }
}
