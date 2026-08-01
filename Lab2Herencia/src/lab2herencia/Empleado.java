package lab2herencia;
import java.util.Calendar;
public class Empleado {
    protected int codigo;
    protected String nombre;
    protected Calendar fechaContrato;
    protected double salarioBase;
    protected int horasTrabajadas;
    protected String foto;

    public Empleado(int codigo, String nombre, Calendar fechaContrato, double salarioBase, int horasTrabajadas, String foto) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.salarioBase = salarioBase;
        this.foto = foto;
        this.fechaContrato = fechaContrato;
        this.horasTrabajadas = 0;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public Calendar getFechaContrato() {
        return fechaContrato;
    }

    public double getSalarioBase() {
        return salarioBase;
    }

    public String getFoto() {
        return foto;
    }
    
    public int getHorasTrabajadas() {
        if (horasTrabajadas > 160) {
            return 160;
        }

        return horasTrabajadas;
    }
    
     public void registrarHoras(int horas){
        if (horas<0) {
            throw new IllegalArgumentException("las horas no pueden ser negativas");
        }
        horasTrabajadas+=horas;
    }
     
    public double calcularPago() {
        double horasPago = getHorasTrabajadas();
        double pagoProporcional = (salarioBase * horasPago) / 160;
        double deduccion = salarioBase * 0.035;
        double pago = pagoProporcional - deduccion;

        if (pago < 0) {
            pago = 0;
        }

        return pago;
    }

    public String mostrarInformacion() {

    int dia = fechaContrato.get(Calendar.DAY_OF_MONTH);
    int mes = fechaContrato.get(Calendar.MONTH) + 1;
    int anio = fechaContrato.get(Calendar.YEAR);

    return "Código: " + codigo + "\nNombre: " + nombre + "\nFecha de contratación: " + dia + "/" + mes + "/" + anio;
}
}
