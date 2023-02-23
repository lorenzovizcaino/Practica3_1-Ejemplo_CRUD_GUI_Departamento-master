package modelo.servicio;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import exceptions.SaldoInsuficienteException;
import modelo.AccMovement;
import modelo.Account;
import modelo.Departamento;
import exceptions.InstanceNotFoundException;
import util.SessionFactoryUtil;

public class AccountServicio implements IAccountServicio {

    @Override
    public Account findAccountById(int accId) throws InstanceNotFoundException {
        SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Account account = session.get(Account.class, accId);
        if (account == null) {
            throw new InstanceNotFoundException(Account.class.getName());
        }

        session.close();
        return account;
    }


    @Override
    public List<Account> mostrarCuentasByIdEmpleado(int idEmp) throws InstanceNotFoundException {
        SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<Account> cuentas = session.createQuery("select a from Account a where emp.empno=:idEmpleado").setParameter("idEmpleado", idEmp).list();
        if (cuentas.size() < 1) {
            throw new InstanceNotFoundException(Account.class.getName());
        }
        return cuentas;
    }

    public Account saveOrUpdate(Account cuenta) {
        SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction tx = null;

        try {
            tx = session.beginTransaction();

            session.saveOrUpdate(cuenta);
            tx.commit();
        } catch (Exception ex) {
            System.out.println("Ha ocurrido una excepción en create Cuenta: " + ex.getMessage());
            if (tx != null) {
                tx.rollback();
            }
            throw ex;
        } finally {
            session.close();
        }
        return cuenta;
    }


    @Override
    public AccMovement modificar(int numCta, double cantidad)
            throws SaldoInsuficienteException, InstanceNotFoundException, UnsupportedOperationException {

        Transaction tx = null;
        Session session = null;
        AccMovement movement = null;
        Account cuenta;


        try {

            if (cantidad < 0) {
                throw new UnsupportedOperationException();
            }
            SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactory();
            session = sessionFactory.openSession();
            cuenta = session.get(Account.class, numCta);

            BigDecimal cantidadBD = new BigDecimal(cantidad);
            double saldoDouble = cuenta.getAmount().doubleValue();
            double diferencia;

            tx = session.beginTransaction();
            System.out.println(cuenta.toString());
            cuenta.setAmount(cantidadBD);
            System.out.println(cuenta.toString());

            movement = new AccMovement();
            if (cantidad >= saldoDouble) {
                diferencia = cantidad - saldoDouble;

            } else {
                diferencia = -(saldoDouble - cantidad);

            }
            movement.setAmount(new BigDecimal(diferencia));
            movement.setDatetime(LocalDateTime.now());
            movement.setAccountOrigen(cuenta);
            movement.setAccountDestino(cuenta);
            session.save(movement);
            tx.commit();

        } catch (Exception ex) {
            System.out.println("Ha ocurrido una exception: " + ex.getMessage());
            if (tx != null) {
                tx.rollback();
            }
            throw ex;
        } finally {
            if (session != null) {
                session.close();
            }
        }
        return movement;


    }


    @Override
    public boolean delete(Integer accountno) throws InstanceNotFoundException {
        SessionFactory sessionFactory = SessionFactoryUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Transaction tx = null;
        boolean exito = false;

        try {
            tx = session.beginTransaction();
            Account cuenta = session.get(Account.class, accountno);
            if (cuenta != null) {
                session.remove(cuenta);
            } else {
                throw new InstanceNotFoundException(Account.class.getName() + " id: " + accountno);
            }
            tx.commit();
            exito = true;
        } catch (Exception ex) {
            System.out.println("Ha ocurrido una excepción al borrar la cuenta: " + ex.getMessage());
            if (tx != null) {
                tx.rollback();
            }

            throw ex;
        } finally {
            session.close();
        }
        return exito;
    }

}
