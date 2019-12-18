package persistencia;

public class TDSFactoriaDAO extends FactoriaDAO {
	public TDSFactoriaDAO () {
	}
	
	@Override
	public IAdaptadorContactoIndividualDAO getContactoIndividualDAO() {
		return AdaptadorContactoIndividual.getUnicaInstancia();
	}

	@Override
	public IAdaptadorGrupoDAO getGrupoDAO() {
		return AdaptadorGrupo.getUnicaInstancia();
	}

	@Override
	public IAdaptadorMensajeDAO getMensajeDAO() {
		return AdaptadorMensaje.getUnicaInstancia();
	}

	@Override
	public IAdaptadorUsuarioDAO getUsuarioDAO() {
		return AdaptadorUsuario.getUnicaInstancia();
	}

}
