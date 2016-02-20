package poof.textui;

import poof.textui.exception.*;
import poof.core.*;
import java.io.*;
import poof.textui.main.*;
import pt.utl.ist.po.ui.*;
import static pt.utl.ist.po.ui.UserInteraction.IO;

/**
 * 
 * @author G42
 * @version 1.0
 */
public class Shell {

	public static void main(String[] args) {

		poof.core.Shell shell = new poof.core.Shell();

    	String fileIn = System.getProperty("import");
    	if(fileIn != null){
    		poof.parser.ParseFile importar = new poof.parser.ParseFile();	
    		try{
    			SistemaFicheiros sf = importar.parse(fileIn);
    			shell.criar();
    			shell.importSF(sf);
    		}
    		catch(Exception e){
    			//escrever !!!
    		}
    		Menu menu = new MainMenu(shell);
    		((MainMenu) menu).showOptionsNonEmpty();
			menu.open();
			IO.close();
    	}else{	
			Menu menu = new MainMenu(shell);
			menu.open();
			IO.close();
		} 
	}
}
