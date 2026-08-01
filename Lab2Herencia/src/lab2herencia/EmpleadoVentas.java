
package lab2herencia;
import java.util.Locale;
import java.util.Calendar;
    public class EmpleadoVentas extends Empleado {
    protected Calendar hoy = Calendar.getInstance();
    protected double[] meses = new double[12];
    
    protected double tasa;
    
    public EmpleadoVentas(int codigo, String nombre, Calendar fechaContrato, double salarioBase, String foto, double tasa){
        super(codigo, nombre, fechaContrato,salarioBase,foto);
        if (tasa <= 0) {
        throw new IllegalArgumentException("La tasa debe ser mayor que 0"); }
        this.tasa=tasa;
    }
    
    public double getTasa() {
        return tasa;
    }

    public void setTasa(double tasaComision) {
    if (tasaComision > 0) {
        this.tasa = tasaComision;
    } else {
        System.out.println("La tasa debe ser mayor que 0");
    }

}
 
    public void registrarVentas(double monto){
    int mesint=queMes();
    if(monto>0){
         meses[mesint] += monto;
    }else{System.out.println("ERROR Monto invalido");}
    }
    
    public double comision(){
    int mesint=queMes();
    double comision=0;
        comision+=meses[mesint]*tasa;
         
         return comision;
      }
    
   
    
    public double pagoAnual(){
    double pagoanual=0;
    for (double venta : meses) {
        pagoanual += venta;
    }
    return pagoanual;
    }
    
     public int queMes(){
     String meshoy = hoy.getDisplayName(Calendar.MONTH,Calendar.LONG,Locale.forLanguageTag("es"));
     String[] VE={"enero","febrero","marzo","abril","mayo","junio","julio","agosto","septiembre","octubre","noviembre", "diciembre"};
        for(int i=0;i<=VE.length-1;i++){
            if(meshoy.equals(VE[i])){
                return i;
            }
        }
        return 0;
     }
     @Override
     public double calcularPago(){
        double horasPago = getHorasTrabajadas();
        double pago = (salarioBase * horasPago) / 160;
        pago += comision();
        return pago;
    
    }
     @Override
     public String mostrarInformacion(){
        return super.mostrarInformacion()
            + "\nVentas anuales: " + pagoAnual();
     } 
    }

