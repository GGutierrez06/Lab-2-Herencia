package lab2herencia;
import java.util.Calendar;
public class EmpleadoTemporal extends Empleado {
    private Calendar finContrato;
    
    public EmpleadoTemporal(int codigo, String nombre, Calendar fechaContrato, double salarioBase, String foto, Calendar finContrato) {
        super(codigo, nombre, fechaContrato, salarioBase, foto);
        this.finContrato = finContrato;
    }

    public Calendar getFinContrato() {
        return finContrato;
    }

    public void actualizarFinContrato(Calendar finContrato) {
        if (finContrato == null) {
            throw new IllegalArgumentException("La fecha de fin de contrato es obligatoria");
        }
        
        this.finContrato = finContrato;
    }

    @Override
    public double calcularPago() {
        Calendar fechaActual = Calendar.getInstance();
        Calendar actual = (Calendar) fechaActual.clone();
        Calendar fin = (Calendar) finContrato.clone();

        actual.set(Calendar.HOUR_OF_DAY, 0);
        actual.set(Calendar.MINUTE, 0);
        actual.set(Calendar.SECOND, 0);
        actual.set(Calendar.MILLISECOND, 0);

        fin.set(Calendar.HOUR_OF_DAY, 0);
        fin.set(Calendar.MINUTE, 0);
        fin.set(Calendar.SECOND, 0);
        fin.set(Calendar.MILLISECOND, 0);

        if (actual.compareTo(fin) <= 0) {
            double horasPago = getHorasTrabajadas();
            double pago = (salarioBase * horasPago) / 160;
            return pago;
        }

        return 0;
    }

    @Override
    public String mostrarInformacion() {
        int dia = finContrato.get(Calendar.DAY_OF_MONTH);
        int mes = finContrato.get(Calendar.MONTH) + 1;
        int anio = finContrato.get(Calendar.YEAR);

        return super.mostrarInformacion() + "\nFecha de fin de contrato: " + dia + "/" + mes + "/" + anio;
    }
}
