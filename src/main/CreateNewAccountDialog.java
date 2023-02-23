package main;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import modelo.Account;
import modelo.Empleado;

public class CreateNewAccountDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldCantidad;
	private JButton okButton;
	private JLabel label_saldoActual;
	private JLabel label_CantidadSaldoActual;
	private Empleado empleado;
	private Account cuentaACrearOActualizar=null;
	private double cantidadModificada;
	
	public Account getResult() {
		return this.cuentaACrearOActualizar;
	}
	public double getCantidadModificada() {
		return this.cantidadModificada;
	}



	/**
	 * Create the dialog.
	 */
	public void initComponents() {
		
		setBounds(100, 100, 598, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		textFieldCantidad = new JTextField();
		textFieldCantidad.setFont(new Font("Tahoma", Font.PLAIN, 14));
		textFieldCantidad.setBounds(330, 83, 197, 23);
		contentPanel.add(textFieldCantidad);
		textFieldCantidad.setColumns(10);

		JLabel lblCantidad = new JLabel("Cantidad");
		lblCantidad.setFont(new Font("Tahoma", Font.PLAIN, 14));
		lblCantidad.setBounds(39, 82, 140, 24);
		contentPanel.add(lblCantidad);
		
		label_saldoActual = new JLabel("Saldo Actual");
		label_saldoActual.setFont(new Font("Tahoma", Font.PLAIN, 14));
		label_saldoActual.setBounds(39, 11, 140, 23);
		contentPanel.add(label_saldoActual);
		
		label_CantidadSaldoActual = new JLabel("");
		label_CantidadSaldoActual.setBounds(330, 11, 197, 24);
		contentPanel.add(label_CantidadSaldoActual);

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
		getContentPane().add(buttonPane, BorderLayout.SOUTH);

		okButton = new JButton("Guardar");

		okButton.setActionCommand("OK");
		buttonPane.add(okButton);
		getRootPane().setDefaultButton(okButton);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cuentaACrearOActualizar=null;
				CreateNewAccountDialog.this.dispose();
				
			}
		});
		cancelButton.setActionCommand("Cancel");
		buttonPane.add(cancelButton);

		ActionListener crearBtnActionListener = new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (!(textFieldCantidad.getText().trim().equals(""))) {
					
					if(cuentaACrearOActualizar==null) {
						//Solo para creación
						cuentaACrearOActualizar= new Account();
						
						double cant=Integer.parseInt(textFieldCantidad.getText().trim());
						cuentaACrearOActualizar.setAmount(new BigDecimal(cant));
						cuentaACrearOActualizar.setEmp(empleado);
						
					}else {
						
						label_CantidadSaldoActual.setText(String.valueOf(cuentaACrearOActualizar.getAmount()));
						cantidadModificada=Integer.parseInt(textFieldCantidad.getText().trim());
						
					}
					
					
					
					CreateNewAccountDialog.this.dispose();
				}
			}
		};

		this.okButton.addActionListener(crearBtnActionListener);

	}

	public CreateNewAccountDialog(Window owner, String title, ModalityType modalityType, Account cuenta, Empleado empleado) {
		super(owner, title, modalityType);
		this.empleado=empleado;
		initComponents();
		cuentaACrearOActualizar=cuenta;
		if(cuentaACrearOActualizar!=null) {
			double cant=cuentaACrearOActualizar.getAmount().doubleValue();
			String c=String.valueOf(cant);
			//textFieldCantidad.setText(c);
			label_CantidadSaldoActual.setText(String.valueOf(cuentaACrearOActualizar.getAmount()));
			
		}else {
			label_saldoActual.setText("");
		}
		this.setLocationRelativeTo(owner);
	}
}
