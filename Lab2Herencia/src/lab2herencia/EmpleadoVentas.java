
package lab2herencia;
import java.util.Locale;
import java.util.Calendar;
    public class EmpleadoVentas {
    protected Calendar hoy = Calendar.getInstance();
    protected double tot;
    protected double[][] meses={
        {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}, {}
    };
    
    protected double tasa;
    
    public EmpleadoVentas(double tasa){
        super();
        this.tot=0;
        if(tasa>0){this.tasa=tasa;}
        else{system.out.println("tasa invalida");}
    }
    
 
    public void RegistrarVentas(double monto){
    int mesint=queMes();
    if(monto>0){
        meses[mesint][meses[mesint].length]=monto;
    }else{System.out.println("ERROR Monto invalido");}
    }
    
    public double comision(){
    int mesint=queMes();
    double comision=0;
    for(double mes:meses[mesint]){
        comision+=mes*tasa;
         }
         return comision;
      }
    
    public double calcularPago(){
    
    
    return 2;
    }
    
    public double pagoAnual(){
    int mesint=queMes();
    double pagoanual=0;
        for(double[] i:meses){
            for(double mes: i){
                pagoanual+=mes*tasa;
                 }
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
     public void mostrarInformacion(){
     System.out.println("VENTA ANUAL: "+pagoAnual());
     
     
     
     } 
}
