package modelo.servicio;

import exceptions.SaldoInsuficienteException;
import modelo.AccMovement;
import modelo.Account;

import java.util.List;

import exceptions.InstanceNotFoundException;

public interface IAccountServicio {
	public Account findAccountById(int accId) throws InstanceNotFoundException ;
	
	public AccMovement transferir(int accOrigen, int accDestino, double cantidad)
			throws SaldoInsuficienteException, InstanceNotFoundException, UnsupportedOperationException ;
	
	public List<Account> mostrarCuentasByIdEmpleado(int idEmp) throws InstanceNotFoundException;
	public Account saveOrUpdate(Account cuenta);

	public Account Update(Account cuentaAModificar);
	
	public AccMovement modificar(Account cuenta, double cantidad)
			throws SaldoInsuficienteException, InstanceNotFoundException, UnsupportedOperationException;

	public boolean delete(Integer accountno) throws InstanceNotFoundException;
		
		
}
