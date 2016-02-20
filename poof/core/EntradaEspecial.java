/*public class EntradaEspecial extends Entrada {
	private TreeMap<String,Entrada> _entradas = new TreeMap<String,Entrada>();

	//	construtor da class (criar novo directorio)
	public EntradaEspecial(String nome, Utilizador dono, boolean publico, Directorio directorioPai){	
		if (_nome.equals("root")){
			_entradas.put("..",directorioPai);
			return (nome,dono,publico,directorioPai); 
		}
		if (_nome.equals("home")){
			directorioPai = _entradas.put(".",this);
			return (nome,dono,publico,directorioPai);
		}

	}
}*/