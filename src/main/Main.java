package main;

import exceptions.InstanceNotFoundException;
import exceptions.SaldoInsuficienteException;
import modelo.Account;
import modelo.Empleado;
import modelo.servicio.*;
import util.SessionFactoryUtil;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;


public class Main {
	private static IAccountServicio accountServicio;
	private static IEmpleadoServicio empleadoServicio;
	public static void main(String[] args) {

		//ConsultarCuentasPorIdEmpleado(7369);
		//ConsultarEmpleadoPorId(17369);
		ModificarSaldo(4,300);



	}

	private static void ModificarSaldo(int accId, double cantidad)  {
		accountServicio=new AccountServicio();
		try {
			SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactory();
			Session session = sessionFactory.openSession();
			Account cuenta = session.get(Account.class, accId);
			if (cuenta == null) {
				throw new InstanceNotFoundException(Account.class.getName());
			}
			accountServicio.modificar(cuenta, cantidad);
			

			session.close();
			
		}catch(InstanceNotFoundException e) {
			e.getMessage();
		} catch (UnsupportedOperationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SaldoInsuficienteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

	private static void ConsultarEmpleadoPorId(int idEmp) {
		empleadoServicio=new EmpleadoServicio();
		try {
			Empleado empleado=empleadoServicio.ConsultarEmpleadoPorId(idEmp);
			System.out.println(empleado);
		} catch (InstanceNotFoundException e) {
			System.err.println("No existe ningun empleado con el id "+idEmp);
		}
		
	}

	private static void ConsultarCuentasPorIdEmpleado(int idEmp) {
		accountServicio=new AccountServicio();
		try {
			List<Account> lista=accountServicio.mostrarCuentasByIdEmpleado(idEmp);
			for (Account account:lista) {
				System.out.println(account);
			}
		} catch (InstanceNotFoundException e) {
			System.err.println("El empleado no tiene ninguna cuenta");
		}
	}

}
