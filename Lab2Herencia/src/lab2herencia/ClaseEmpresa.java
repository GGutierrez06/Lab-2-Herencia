package lab2herencia;

import java.util.ArrayList;
import java.util.Calendar;

public class ClaseEmpresa {
    private ArrayList<Empleado> Empleados;

    public ClaseEmpresa() {
        this.Empleados = new ArrayList<>();
    }
    
    public ArrayList<Empleado> getEmpleados(){
        return Empleados;
    }
    public Empleado buscarEmpleado(int codigo){
        return buscarEmpleadoRecursivo(codigo,0);
    }
    
    private Empleado buscarEmpleadoRecursivo(int codigo, int indice){
        
        if (indice>=Empleados.size()){
            return null;
        }
        
        if (Empleados.get(indice).getCodigo() == codigo){
            return Empleados.get(indice);
        }
        return (buscarEmpleadoRecursivo(codigo,indice+1));
    }
    
    public boolean RegistrarEmpleado(Empleado empleadoNuevo){
        if (buscarEmpleado(empleadoNuevo.getCodigo()) == null){
            Empleados.add(empleadoNuevo);
            return true;
        } else {
            System.out.println("El codigo ya esta en uso. Intente de nuevo");
            return false;
        }
    }
    
    public boolean RegistrarHorasTrabajadas(int horas, int codigo){
        Empleado empleadoActual = buscarEmpleado(codigo);
        
        if (empleadoActual == null){
            System.out.println("Codigo no existe");
            return false;
        }
        
        if (horas<0){
            System.out.println("Horas trabajadas no pueden ser negativas");
            return false;
        }
        
        empleadoActual.RegistrarHorasTrabajadas(horas);
        return true;
    }
    
    public boolean RegistrarVentas(int ventas, int codigo){
        Empleado empleadoActual = buscarEmpleado(codigo);
        
        if (empleadoActual==null){
            System.out.println("Codigo no encontrado");
            return false;
        }
        
        if (empleadoActual instanceof EmpleadoVentas){
            EmpleadoVentas empleadoActualVentas = (EmpleadoVentas) empleadoActual;
            empleadoActualVentas.RegistrarVentas(ventas);
            return true;
        } else {
            System.out.println("El empleado no es de ventas.");
            return false;
        }
    }
    
    public boolean ActualizarFinContrato(Calendar fechaNueva, int codigo){
        Empleado empleadoActual = buscarEmpleado(codigo);
        
        if (empleadoActual==null){
            System.out.println("Codigo no encontrado");
            return false;
        }
        
        if (empleadoActual instanceof EmpleadoTemporal){
            EmpleadoTemporal empleadoActualVentas = (EmpleadoTemporal) empleadoActual;
            empleadoActualVentas.ActualizarFinContrato(fechaNueva);
            return true;
        } else {
            System.out.println("El empleado no es Temporal.");
            return false;
        }
    }
    
    public double CalcularPagoMensual(int codigo){
        Empleado empleadoActual = buscarEmpleado(codigo);
        
        if (empleadoActual == null){
            System.out.println("Codigo no encontrado");
            return -1.0;
        }
        
        return empleadoActual.CalcularPago();
    }
    
    public String GenerarReportes(){
        int contEstandar = 0, contTemporal = 0, contVentas = 0;
        String reporte = " ------ REPORTE DE EMPLEADOS ------ \n";
        
        reporte += "--- EMPLEADOS ESTANDAR ---\n";
        for (Empleado empleadoActual : Empleados){
            // Comprobamos que sea exactamente de la clase Empleado base (no subclases)
            if (empleadoActual.getClass() == Empleado.class) {
                contEstandar++;
                reporte += empleadoActual.mostrarInformacion() 
                        + " | Horas: " + empleadoActual.getHorasTrabajadas() 
                        + " | Pago: $" + empleadoActual.calcularPago() + "\n";
            }
        }
        
        reporte += "--- EMPLEADOS DE VENTAS ---\n";
        for (Empleado empleadoActual : Empleados){
            if (empleadoActual instanceof EmpleadoTemporal) {
                contTemporal++;
                EmpleadoTemporal temporal = (EmpleadoTemporal) empleadoActual;
                reporte += temporal.mostrarInformacion() 
                        + " | Horas: " + temporal.getHorasTrabajadas() 
                        + " | Pago: $" + temporal.calcularPago() + "\n";
            }
        }
        reporte += "--- EMPLEADOS DE VENTAS ---\n";
        for (Empleado empleadoActual : Empleados){
            if (empleadoActual instanceof EmpleadoVentas) {
                contVentas++;
                EmpleadoVentas ventas = (EmpleadoVentas) empleadoActual;
                reporte += empleadoActual.mostrarInformacion() 
                        + " | Horas: " + ventas.getHorasTrabajadas()
                        + " | Pago: $" + ventas.calcularPago()
                        + " | Ventas Anuales: $" + ventas.calcularVentasAnuales()+"\n";
            }
        }
        
        reporte += " ---- RESUMEN ----\n";
        reporte += "- Empleados Estandar: "+contEstandar+ "\n";
        reporte += "- Empleados Temporales: "+contTemporal+ "\n";
        reporte += "- Empleados de Ventas: "+contVentas+ "\n";
        reporte += "- TOTAL DE EMPLEADOS: "+Empleados.size()+ "\n";
        return reporte;
    }
}
