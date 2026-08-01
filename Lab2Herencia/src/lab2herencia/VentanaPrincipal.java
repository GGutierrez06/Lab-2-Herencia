/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lab2herencia;

/**
 *
 * @author gabri
 */

import com.toedter.calendar.JDateChooser;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.Calendar;


public class VentanaPrincipal extends JFrame {

    private ClaseEmpresa empresa;

    private String FotoSeleccionada = "";
    private JLabel nombreFoto;
    private JTextArea areaReporte;

    public VentanaPrincipal() {
        empresa = new ClaseEmpresa();

        setTitle("Gestión de Empleados - ACME Inc");
        setSize(750, 550);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane pestanas = new JTabbedPane();

        pestanas.addTab("Registrar Empleados", crearPanelRegistro());
        pestanas.addTab("Acciones", crearPanelAcciones());
        pestanas.addTab("Reportes", crearPanelReportes());

        add(pestanas);
    }

    
    private JPanel crearPanelRegistro() {
        JPanel panel = new JPanel(new GridLayout(9, 2, 8, 8));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JTextField espacioCodigo = new JTextField();
        JTextField espacioNombre = new JTextField();
        JTextField espacioSalario = new JTextField();

        JComboBox<String> comboTipo = new JComboBox<>(new String[]{"Estándar", "Temporal", "Ventas"});

        JDateChooser dateContrato = new JDateChooser();
        dateContrato.setCalendar(Calendar.getInstance());

        JDateChooser dateFinContrato = new JDateChooser();
        dateFinContrato.setEnabled(false);

        JTextField espacioTasaVentas = new JTextField();
        espacioTasaVentas.setEnabled(false);

        JButton botonFoto = new JButton("Seleccionar Foto...");
        nombreFoto = new JLabel("Ninguna foto seleccionada");

        botonFoto.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            int resultado = chooser.showOpenDialog(this);
            if (resultado == JFileChooser.APPROVE_OPTION) {
                File archivo = chooser.getSelectedFile();
                FotoSeleccionada = archivo.getAbsolutePath();
                nombreFoto.setText(archivo.getName());
            }
        });

        comboTipo.addActionListener(accion -> {
            String tipo = (String) comboTipo.getSelectedItem();
            dateFinContrato.setEnabled(tipo.equals("Temporal"));
            espacioTasaVentas.setEnabled(tipo.equals("Ventas"));
        });

        JButton btnGuardar = new JButton("Registrar Empleado");
        btnGuardar.addActionListener(a -> {
            try {
                int codigo = Integer.parseInt(espacioCodigo.getText());
                String nombre = espacioNombre.getText();
                double salario = Double.parseDouble(espacioSalario.getText());
                Calendar fechaContrato = dateContrato.getCalendar();

                String tipo = (String) comboTipo.getSelectedItem();
                Empleado nuevoEmpleado = null;

                if (tipo.equals("Estándar")) {
                    nuevoEmpleado = new Empleado(codigo, nombre, fechaContrato, salario, FotoSeleccionada);
                } else if (tipo.equals("Temporal")) {
                    Calendar finContrato = dateFinContrato.getCalendar();
                    if (finContrato == null) {
                        JOptionPane.showMessageDialog(this, "Seleccione la fecha de fin de contrato.");
                        return;
                    }
                    nuevoEmpleado = new EmpleadoTemporal(codigo, nombre, fechaContrato, salario, FotoSeleccionada, finContrato);
                } else if (tipo.equals("Ventas")) {
                    double tasa = Double.parseDouble(espacioTasaVentas.getText());
                    nuevoEmpleado = new EmpleadoVentas(codigo, nombre, fechaContrato, salario, FotoSeleccionada, tasa);
                }

                if (empresa.registrarEmpleado(nuevoEmpleado)) {
                    JOptionPane.showMessageDialog(this, "Empleado registrado exitosamente.");
                    espacioCodigo.setText("");
                    espacioNombre.setText("");
                    espacioSalario.setText("");
                    espacioTasaVentas.setText("");
                    nombreFoto.setText("Ninguna foto seleccionada");
                    FotoSeleccionada = "";
                } else {
                    JOptionPane.showMessageDialog(this, "Error: El código ya existe.");
                }

            } catch (NumberFormatException error) {
                JOptionPane.showMessageDialog(this, "Por favor ingrese números válidos en Código, Salario o Tasa.", "Error de Formato", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(new JLabel("Tipo de Empleado:"));
        panel.add(comboTipo);
        panel.add(new JLabel("Código:"));
        panel.add(espacioCodigo);
        panel.add(new JLabel("Nombre Completo:"));
        panel.add(espacioNombre);
        panel.add(new JLabel("Salario Base:"));
        panel.add(espacioSalario);
        panel.add(new JLabel("Fecha Contratación:"));
        panel.add(dateContrato);
        panel.add(new JLabel("Foto:"));
        
        JPanel panelFoto = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panelFoto.add(botonFoto);
        panelFoto.add(nombreFoto);
        panel.add(panelFoto);

        panel.add(new JLabel("Fin Contrato (Solo Temporal):"));
        panel.add(dateFinContrato);
        panel.add(new JLabel("Tasa Comisión (Solo Ventas):"));
        panel.add(espacioTasaVentas);
        panel.add(new JLabel(""));
        panel.add(btnGuardar);

        return panel;
    }

    
    private JPanel crearPanelAcciones() {
        JPanel panel = new JPanel(new GridLayout(5, 1, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel pHoras = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField txtCodHoras = new JTextField(6);
        JTextField txtCantHoras = new JTextField(6);
        JButton btnHoras = new JButton("Registrar Horas");

        pHoras.setBorder(BorderFactory.createTitledBorder("Horas Trabajadas"));
        pHoras.add(new JLabel("Código:"));
        pHoras.add(txtCodHoras);
        pHoras.add(new JLabel("Horas:"));
        pHoras.add(txtCantHoras);
        pHoras.add(btnHoras);

        btnHoras.addActionListener(e -> {
            try {
                int cod = Integer.parseInt(txtCodHoras.getText());
                int hrs = Integer.parseInt(txtCantHoras.getText());
                if (empresa.registrarHorasTrabajadas(hrs, cod)) {
                    JOptionPane.showMessageDialog(this, "Horas acumuladas correctamente.");
                } else {
                    JOptionPane.showMessageDialog(this, "Error al registrar horas. Verifique el código.");
                }
            } catch (Exception error) {
                JOptionPane.showMessageDialog(this, "Valores numéricos inválidos.");
            }
        });

        JPanel pVentas = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField txtCodVentas = new JTextField(6);
        JTextField txtMontoVentas = new JTextField(8);
        JButton btnVentas = new JButton("Registrar Venta");

        pVentas.setBorder(BorderFactory.createTitledBorder("Ventas del Mes (Solo Empleados Ventas)"));
        pVentas.add(new JLabel("Código:"));
        pVentas.add(txtCodVentas);
        pVentas.add(new JLabel("Monto $:"));
        pVentas.add(txtMontoVentas);
        pVentas.add(btnVentas);

        btnVentas.addActionListener(a -> {
            try {
                int cod = Integer.parseInt(txtCodVentas.getText());
                double monto = Double.parseDouble(txtMontoVentas.getText());
                if (empresa.registrarVentas(monto, cod)) {
                    JOptionPane.showMessageDialog(this, "Venta registrada con éxito.");
                } else {
                    JOptionPane.showMessageDialog(this, "Error: Verifique que el empleado exista y sea de Ventas.");
                }
            } catch (Exception error) {
                JOptionPane.showMessageDialog(this, "Valores numéricos inválidos.");
            }
        });

        JPanel pContrato = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JTextField txtCodTemp = new JTextField(6);
        JDateChooser dateNuevaFecha = new JDateChooser();
        JButton btnNuevaFecha = new JButton("Actualizar Fecha");

        pContrato.setBorder(BorderFactory.createTitledBorder("Extender Contrato (Solo Temporales)"));
        pContrato.add(new JLabel("Código:"));
        pContrato.add(txtCodTemp);
        pContrato.add(new JLabel("Nueva Fecha Fin:"));
        pContrato.add(dateNuevaFecha);
        pContrato.add(btnNuevaFecha);

        btnNuevaFecha.addActionListener(a -> {
            try {
                int cod = Integer.parseInt(txtCodTemp.getText());
                Calendar cal = dateNuevaFecha.getCalendar();
                if (cal != null && empresa.actualizarFinContrato(cal, cod)) {
                    JOptionPane.showMessageDialog(this, "Fecha actualizada exitosamente.");
                } else {
                    JOptionPane.showMessageDialog(this, "Error: Verifique el código o la fecha elegida.");
                }
            } catch (Exception error) {
                JOptionPane.showMessageDialog(this, "Código no válido.");
            }
        });

        panel.add(pHoras);
        panel.add(pVentas);
        panel.add(pContrato);

        return panel;
    }

    
    private JPanel crearPanelReportes() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        areaReporte = new JTextArea();
        areaReporte.setEditable(false);
        areaReporte.setFont(new Font("Monospaced", Font.PLAIN, 12));

        JButton btnActualizar = new JButton("Generar/Actualizar Reportes");
        btnActualizar.addActionListener(e -> {
            areaReporte.setText(empresa.generarReportes());
        });

        panel.add(btnActualizar, BorderLayout.NORTH);
        panel.add(new JScrollPane(areaReporte), BorderLayout.CENTER);

        return panel;
    }
}
