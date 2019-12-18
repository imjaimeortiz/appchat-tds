package persistencia;

public abstract class FactoriaDAO {
	private static FactoriaDAO unicaInstancia;
	
	public static final String DAO_TDS = "persistencia.TDSFactoriaDAO";
		
	 
	 
	public static FactoriaDAO getInstancia(String tipo) throws DAOException{
		if (unicaInstancia == null)
			try { unicaInstancia=(FactoriaDAO) Class.forName(tipo).newInstance();
			} catch (Exception e) {	
				//throw new DAOException(e.getMessage());
			} 
		return unicaInstancia;
	}


	public static FactoriaDAO getInstancia() throws DAOException{
			if (unicaInstancia == null) return getInstancia (FactoriaDAO.DAO_TDS);
					else return unicaInstancia;
		}

	//Constructor 
	protected FactoriaDAO (){}
		
		
	// Metodos factoria que devuelven adaptadores que implementen estos interfaces
	
	public abstract IAdaptadorContactoIndividualDAO getContactoIndividualDAO();
	public abstract IAdaptadorGrupoDAO getGrupoDAO();
	public abstract IAdaptadorMensajeDAO getMensajeDAO();
	public abstract IAdaptadorUsuarioDAO getUsuarioDAO();

}