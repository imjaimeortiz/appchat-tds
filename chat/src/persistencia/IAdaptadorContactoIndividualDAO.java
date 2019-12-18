package persistencia;

import java.util.List;

import modelo.ContactoIndividual;

public interface IAdaptadorContactoIndividualDAO {
	public void registrarContactoIndividual(ContactoIndividual contactoi);
	public void borrarContactoIndividual(ContactoIndividual contactoi);
	public void modificarContactoIndividual(ContactoIndividual contactoi);
	public ContactoIndividual recuperarContactoIndividual(int codigo);
	public List<ContactoIndividual> recuperarTodosContactosIndividuales();

}
