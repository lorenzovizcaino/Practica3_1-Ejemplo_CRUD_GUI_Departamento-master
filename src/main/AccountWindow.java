package main;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dialog;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import exceptions.InstanceNotFoundException;
import modelo.AccMovement;
import modelo.Account;
import modelo.Empleado;
import modelo.servicio.AccountServicio;
import modelo.servicio.DepartamentoServicio;
import modelo.servicio.EmpleadoServicio;
import modelo.servicio.IAccountServicio;
import modelo.servicio.IDepartamentoServicio;
import modelo.servicio.IEmpleadoServicio;

import javax.swing.JTextField;
import javax.swing.JLabel;

public class AccountWindow extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JPanel contentPane;

	private JTextArea mensajes_text_Area;
	private JList<Account> JListAllCuentas;
	private IAccountServicio accountServicio;
	private IDepartamentoServicio departamentoServicio;
	private IEmpleadoServicio empleadoservicio;
	private CreateNewAccountDialog createDialog;
	private JButton btnShowAllCuentas;
	private JButton btnCrearNuevaCuenta;
	private JButton btnModificarImporteCuenta;
	private JButton btnEliminarCuenta;
	private JTextField textField;
	private int numeroEmpleadoTecleado;
	private Empleado empleado;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AccountWindow frame = new AccountWindow();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AccountWindow() {

		departamentoServicio = new DepartamentoServicio();
		accountServicio=new AccountServicio();
		empleadoservicio=new EmpleadoServicio();
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 847, 772);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(8, 8, 821, 500);
		contentPane.add(panel);
		panel.setLayout(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(19, 264, 669, 228);
		panel.add(scrollPane);

		mensajes_text_Area = new JTextArea();
		scrollPane.setViewportView(mensajes_text_Area);
		mensajes_text_Area.setEditable(false);
		mensajes_text_Area.setText("Panel de mensajes");
		mensajes_text_Area.setForeground(new Color(255, 0, 0));
		mensajes_text_Area.setFont(new Font("Monospaced", Font.PLAIN, 13));

		btnShowAllCuentas = new JButton("Mostrar cuentas");

		btnShowAllCuentas.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnShowAllCuentas.setBounds(50, 76, 208, 36);
		panel.add(btnShowAllCuentas);

		btnModificarImporteCuenta = new JButton("Modificar importe cuenta");

		JListAllCuentas = new JList<Account>();

		JListAllCuentas.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		
		JListAllCuentas.setBounds(403, 37, 377, 200);

		JScrollPane scrollPanel_in_JListAllCuentas = new JScrollPane(JListAllCuentas);
		scrollPanel_in_JListAllCuentas.setLocation(300, 0);
		scrollPanel_in_JListAllCuentas.setSize(500, 250);
		
		panel.add(scrollPanel_in_JListAllCuentas);
	

		btnCrearNuevaCuenta = new JButton("Crear nueva cuenta");

		btnCrearNuevaCuenta.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnCrearNuevaCuenta.setBounds(50, 123, 208, 36);
		panel.add(btnCrearNuevaCuenta);

		btnModificarImporteCuenta.setEnabled(false);
		btnModificarImporteCuenta.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnModificarImporteCuenta.setBounds(50, 170, 208, 36);
		panel.add(btnModificarImporteCuenta);

		btnEliminarCuenta = new JButton("Eliminar cuenta");

		btnEliminarCuenta.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnEliminarCuenta.setEnabled(false);
		btnEliminarCuenta.setBounds(50, 217, 208, 36);
		panel.add(btnEliminarCuenta);
		
		
		
		JLabel lblNewLabel = new JLabel("Introduzca el nº de empleado");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblNewLabel.setBounds(51, 2, 207, 26);
		panel.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setBounds(50, 27, 208, 38);
		panel.add(textField);
		textField.setColumns(10);

		// Eventos
		textField.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent es) {
				if(es.getKeyCode()==KeyEvent.VK_ENTER ||es.getKeyCode()==KeyEvent.VK_R) {
					ConsultarEmpleadoPorId(Integer.parseInt(textField.getText()));
					
				}
			}

			
		});
		
		ActionListener showAllCuentasDelEmpleado = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				getAllCuentas();
			}

			
		};
		btnShowAllCuentas.addActionListener(showAllCuentasDelEmpleado);
	
	
	

		ActionListener crearListener = new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				JFrame owner = (JFrame) SwingUtilities.getRoot((Component) e.getSource());
				createDialog = new CreateNewAccountDialog(owner, "Crear nueva cuenta",
						Dialog.ModalityType.DOCUMENT_MODAL, null, empleado);
				showDialog();
			}
		};
		
		
		btnCrearNuevaCuenta.addActionListener(crearListener);
	

		ActionListener modificarListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedIx = JListAllCuentas.getSelectedIndex();
				if (selectedIx > -1) {
					Account cuenta = (Account) JListAllCuentas.getModel().getElementAt(selectedIx);
					if (cuenta != null) {

						JFrame owner = (JFrame) SwingUtilities.getRoot((Component) e.getSource());

						createDialog = new CreateNewAccountDialog(owner, "Modificar cuenta",
								Dialog.ModalityType.DOCUMENT_MODAL, cuenta, empleado);
						showDialogModificarCuenta(cuenta);
					}
				}
			}

			
		};

		btnModificarImporteCuenta.addActionListener(modificarListener);
	

		ListSelectionListener selectionListListener = new ListSelectionListener() {
			public void valueChanged(ListSelectionEvent e) {
				if (e.getValueIsAdjusting() == false) {
					int selectedIx = JListAllCuentas.getSelectedIndex();
					btnModificarImporteCuenta.setEnabled((selectedIx > -1));
					btnEliminarCuenta.setEnabled((selectedIx > -1));
					if (selectedIx > -1) {
						Account cuenta = (Account) AccountWindow.this.JListAllCuentas.getModel().getElementAt(selectedIx);
						if (cuenta != null) {
							addMensaje(true, "Se ha seleccionado la cuenta" + cuenta);
						}
					}
				}
			}
		};
		JListAllCuentas.addListSelectionListener(selectionListListener);
		
	
	
	
	
	
	
		ActionListener deleteListener = new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int selectedIx = JListAllCuentas.getSelectedIndex();
				if (selectedIx > -1) {
					Account cuenta = (Account) JListAllCuentas.getModel().getElementAt(selectedIx);
					if (cuenta != null) {
						try {
							boolean exito = accountServicio.delete(cuenta.getAccountno());
							if (exito) {
								addMensaje(true, "Se ha eliminado la cuenta con id: " + cuenta.getAccountno());
								getAllCuentas();
							}
						} catch (exceptions.InstanceNotFoundException e1) {
							addMensaje(true, "No se ha podido borrar la cuenta. No se ha encontrado con id: "
									+ cuenta.getAccountno());
						} catch (Exception ex) {
							addMensaje(true, "No se ha podido borrar la cuenta. ");
							System.out.println("Exception: " + ex.getMessage());
							ex.printStackTrace();
						}
					}
				}
			}
		};
		btnEliminarCuenta.addActionListener(deleteListener);
	}
	

	private void addMensaje(boolean keepText, String msg) {
		String oldText = "";
		if (keepText) {
			oldText = mensajes_text_Area.getText();

		}
		oldText = oldText + "\n" + msg;
		mensajes_text_Area.setText(oldText);

	}

	private void showDialog() {
		createDialog.setVisible(true);
		Account cuentaACrear = createDialog.getResult();
		if (cuentaACrear != null) {

			save(cuentaACrear);
		}
	}
	
	private void showDialogModificarCuenta(Account cuentaAModificar) {
		createDialog.setVisible(true);
		cuentaAModificar = createDialog.getResult();
		if (cuentaAModificar != null) {

			update(cuentaAModificar, createDialog.getCantidadModificada());
		}
		
	}

	private void update(Account cuentaAModificar, double cantidad) {
		try {
			AccMovement nuevo = accountServicio.modificar(cuentaAModificar.getAccountno(), cantidad);
			if (nuevo != null) {
				addMensaje(true, "Se ha modificado la cuenta con id: " + cuentaAModificar.getAccountno());
				getAllCuentas();
			} else {
				addMensaje(true, "La Cuenta no se ha actualizado correctamente");
			}

		} catch (Exception ex) {
			addMensaje(true, "Ha ocurrido un error y no se ha podido actualizar la cuenta");
		}
		
	}

	private void save(Account cuenta) {
		try {
			Account nuevo = accountServicio.saveOrUpdate(cuenta);
			if (nuevo != null) {
				addMensaje(true, "Se ha creado una cuenta con id: " + nuevo.getAccountno());
				getAllCuentas();
			} else {
				addMensaje(true, "La Cuenta no se ha creado correctamente");
			}

		} catch (Exception ex) {
			addMensaje(true, "Ha ocurrido un error y no se ha podido crear la cuenta");
		}
	}


	private void ConsultarCuentasPorIdEmpleado(int numeroEmpleadoTecleado)  {
		List<Account> cuentas;
		try {
			cuentas = accountServicio.mostrarCuentasByIdEmpleado(numeroEmpleadoTecleado);
			addMensaje(true, "Se han recuperado: " + cuentas.size() + " cuentas");
			DefaultListModel<Account> defModel = new DefaultListModel<>();

			defModel.addAll(cuentas);

			JListAllCuentas.setModel(defModel);
		} catch (InstanceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	private void ConsultarEmpleadoPorId(int empId) {
		try {
			
			empleado=empleadoservicio.ConsultarEmpleadoPorId(empId);
			addMensaje(true, "Se han recuperado correctamente el empleado:" +
					"\nNumero Empleado: "+empleado.getEmpno()+
					"\nNombre:          "+empleado.getEname()+
					"\nTrabajo:         "+empleado.getJob()+
					"\nAlta en empresa: "+empleado.getHiredate()+
					"\nSalario:         "+empleado.getSal()+
					"\nComision:        "+empleado.getComm());
			
			
			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstanceNotFoundException e) {
			addMensaje(true, "No existe el empleado:" +empId);
			
		}
		
		
	}
	private void getAllCuentas() {
		List<Account> cuentas;
		try {
			cuentas = accountServicio.mostrarCuentasByIdEmpleado(empleado.getEmpno());
			addMensaje(true, "Se han recuperado: " + cuentas.size() + " cuentas");
			DefaultListModel<Account> defModel = new DefaultListModel<>();

			defModel.addAll(cuentas);

			JListAllCuentas.setModel(defModel);
		} catch (InstanceNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
