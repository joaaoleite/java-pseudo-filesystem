package poof.textui.main;

import java.io.IOException;

import pt.utl.ist.po.ui.Command;
import pt.utl.ist.po.ui.Display;
import pt.utl.ist.po.ui.Form;
import pt.utl.ist.po.ui.InputString;
import pt.utl.ist.po.ui.InvalidOperation;

import poof.core.*;

/**
 * Command for saving the relevant applicaion state.
 */
public class Save extends Command<Shell>{

  /**
   * Constructor.
   * 
   * @param entity the target entity.
   */
  public Save(Shell shell) {
    super(MenuEntry.SAVE, shell);
  }

  /**
   * Execute the command.
   */
  @Override
  @SuppressWarnings("nls")
  public final void execute() throws InvalidOperation {


    if(entity().obterNome()==null){
      Form f = new Form(title());
      InputString filename = new InputString(f,Message.newSaveAs());
      f.parse();

      entity().guardar(filename.value() );

    }else{
      entity().guardar( entity().obterNome() );
    }
  }
}
