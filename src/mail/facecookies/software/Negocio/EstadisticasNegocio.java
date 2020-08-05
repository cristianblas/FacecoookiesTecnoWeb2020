/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mail.facecookies.software.Negocio;

import java.io.File;
import java.util.LinkedList;
import mail.facecookies.software.Datos.Estadisticas;
import com.idrsolutions.image.pdf.PdfEncoder;
import java.io.File;
import java.util.LinkedList;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartRenderingInfo;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.entity.StandardEntityCollection;
import org.jfree.data.general.DefaultPieDataset;
/**
 *
 * @author home
 */
public class EstadisticasNegocio {
    private Estadisticas estadistica;

    public EstadisticasNegocio() {
        this.estadistica = new Estadisticas();
    }
    
    
    
    
    public void tortaPorcentajeEdad() {
        double Menores = estadistica.getPorcentajeMenores();
        double Medio = estadistica.getPorcentajeMedio();
        double Mayores = estadistica.getPorcentajeMayores();
        LinkedList<Double> lista = new LinkedList<>();
        lista.add(Menores);
        lista.add(Medio);
        lista.add(Mayores);
        guardarPDF(lista);

    }
    public void tortaPorcentajeGenero() {
        double Hombres = estadistica.getPorcentajeHombres();
        double Mujeres = estadistica.getPorcentajeMujeres();
        LinkedList<Double> lista = new LinkedList<>();
        lista.add(Hombres);
        lista.add(Mujeres);
        guardarPDF(lista);

    }
    
 
    private void guardarPDF(LinkedList<Double> lista) {
        double menores = lista.get(0);
        double medio = lista.get(1);
        double mayores = lista.get(2);
        DefaultPieDataset dataset = new DefaultPieDataset();
        dataset.setValue("MENORES: " + menores + "%", new Double(menores));
        dataset.setValue("MEDIO: " + medio + "%", new Double(medio));
        dataset.setValue("MAYORES: " + mayores + "%", new Double(mayores));

        JFreeChart chart = ChartFactory.createPieChart(// char t
                "Estadisticas Facecookies ",// title                                                                     
                dataset, // data
                true, // include legend
                true, false);
        try {
            final ChartRenderingInfo info = new ChartRenderingInfo(new StandardEntityCollection());
            final File file = new File("chart.jpg");
            // ChartUtilities.saveChartAsJPEG(file, chart, 800, 600);
            ChartUtilities.saveChartAsJPEG(file, chart, 800, 600);
            File pdfFile = new File("Estadistica.pdf");
            pdfFile.createNewFile();
            //write the image to the pdf
            PdfEncoder encoder = new PdfEncoder();
            encoder.write(file, pdfFile);

        } catch (Exception e) {
            System.out.println("ALGO SALIO MAL INTENTE DE NUEVO");
        }

    }


}
