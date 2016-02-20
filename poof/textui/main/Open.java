package poof.textui.main;

import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.*;

import pt.utl.ist.po.ui.Command;
import pt.utl.ist.po.ui.Display;
import pt.utl.ist.po.ui.Form;
import pt.utl.ist.po.ui.InputString;
import pt.utl.ist.po.ui.InputBoolean;
import pt.utl.ist.po.ui.InvalidOperation;

import poof.core.*;

/**
 * Command for loading a file system and the last logged user stored in the given file.
 */
public class Open extends Command<Shell>{

  /**
   * Constructor.
   * 
   * @param entity the target entity.
   */
  public Open(Shell shell) {
    super(MenuEntry.OPEN, shell);
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
    try{
      Form f = new Form(title());
      InputString ficheiro = new InputString(f,Message.openFile());
      f.parse();
      ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(ficheiro.value())));
      entity().abrir( in );
      ((MainMenu) menu()).showOptionsNonEmpty();
    }
    catch(FileNotFoundException e){
      Display dis = new Display(title());
      dis.add(Message.fileNotFound());
      dis.display();
    }
    catch(IOException e){
      throw new InvalidOperation( e.getMessage() );
    }
  }
}
