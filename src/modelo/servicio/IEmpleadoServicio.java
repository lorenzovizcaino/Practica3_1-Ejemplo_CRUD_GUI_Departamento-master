package modelo.servicio;

import exceptions.InstanceNotFoundException;
import modelo.Empleado;

public interface IEmpleadoServicio {
	public Empleado ConsultarEmpleadoPorId (int idEmp)  throws InstanceNotFoundException; 

}
