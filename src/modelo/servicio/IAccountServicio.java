package modelo.servicio;

import exceptions.SaldoInsuficienteException;
import modelo.AccMovement;
import modelo.Account;

import java.util.List;

import exceptions.InstanceNotFoundException;

public interface IAccountServicio {
	public Account findAccountById(int accId) throws InstanceNotFoundException ;

	public List<Account> mostrarCuentasByIdEmpleado(int idEmp) throws InstanceNotFoundException;
	public Account saveOrUpdate(Account cuenta);

	public AccMovement modificar(int numCta, double cantidad)
			throws SaldoInsuficienteException, InstanceNotFoundException, UnsupportedOperationException;

	public boolean delete(Integer accountno) throws InstanceNotFoundException;
		
		
}
