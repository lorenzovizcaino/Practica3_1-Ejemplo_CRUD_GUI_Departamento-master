package modelo.servicio;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import exceptions.InstanceNotFoundException;
import modelo.Account;
import modelo.Empleado;
import util.SessionFactoryUtil;

public class EmpleadoServicio implements IEmpleadoServicio{

	@Override
	public Empleado ConsultarEmpleadoPorId(int idEmp) throws InstanceNotFoundException {
		SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Empleado empleado = session.get(Empleado.class, idEmp);
		if (empleado == null) {
			throw new InstanceNotFoundException(Empleado.class.getName());
		}

		session.close();
		return empleado;
		
	}

}
