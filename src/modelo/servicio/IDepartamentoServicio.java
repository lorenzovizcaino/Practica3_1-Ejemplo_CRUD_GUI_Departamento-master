package modelo.servicio;

import java.util.List;

import exceptions.InstanceNotFoundException;

import modelo.Departamento;


public interface IDepartamentoServicio {

	public List<Departamento> getAll();
	public Departamento saveOrUpdate(Departamento d) ;
	public boolean delete(int deptId) throws InstanceNotFoundException;
}
