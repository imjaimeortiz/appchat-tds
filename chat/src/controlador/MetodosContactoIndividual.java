package controlador;

import modelo.CatalogoUsuarios;
import modelo.ContactoIndividual;
import modelo.Usuario;
import persistencia.IAdaptadorContactoIndividualDAO;

public class MetodosContactoIndividual {

	private IAdaptadorContactoIndividualDAO adaptadorContactoIndividual;
	private CatalogoUsuarios catalogoUsuarios;
	
	public MetodosContactoIndividual(IAdaptadorContactoIndividualDAO adaptadorContactoIndividual, CatalogoUsuarios catalogoUsuarios) {
		this.adaptadorContactoIndividual = adaptadorContactoIndividual;
		this.catalogoUsuarios = catalogoUsuarios;
	}

	public void addContacto(ContactoIndividual contacto) {
		adaptadorContactoIndividual.registrarContactoIndividual(contacto);
	}
	
	
	public void deleteContacto(ContactoIndividual contacto) {
		adaptadorContactoIndividual.borrarContactoIndividual(contacto);
			
	}
}
