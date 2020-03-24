package informacionUso;

import java.awt.Color;
import java.util.Arrays;

import org.knowm.xchart.BitmapEncoder;
import org.knowm.xchart.BitmapEncoder.BitmapFormat;
import org.knowm.xchart.CategoryChart;
import org.knowm.xchart.CategoryChartBuilder;
import org.knowm.xchart.PieChart;
import org.knowm.xchart.PieChartBuilder;
import org.knowm.xchart.demo.charts.ExampleChart;
import org.knowm.xchart.style.Styler.LegendPosition;

public class Graficos implements ExampleChart<CategoryChart> {
	
	public void getPngChart(Integer[]valores)throws Exception{
		
		//create chart
		CategoryChart chart = new CategoryChartBuilder().width(800).height(600).title("Score Histogram")
				.xAxisTitle("Meses").yAxisTitle("Numero de mensajes").build();
		//customize chart
		chart.getStyler().setLegendPosition(LegendPosition.InsideNW);
		chart.getStyler().setHasAnnotations(true);
		//series
		chart.addSeries("test 1", Arrays.asList(new Integer[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12}),
				Arrays.asList(valores));
		BitmapEncoder.saveBitmap(chart, "C:\\Usuarios\\Luu\\Escritorio\\histograma.png", BitmapFormat.PNG);
	}
	
	public void getPngChartTarta(Integer[]valores)throws Exception{
		PieChart chart = new PieChartBuilder().width(800).height(600).title(getClass().getSimpleName()).build();
		//customize chart
		Color[] sliceColors = new Color[] {new Color(224, 68, 14), new Color(230, 105, 62), new Color(234, 143, 110),
				new Color(243, 180, 159), new Color(246, 199, 182), new Color(74, 127, 58)};
		chart.getStyler().setSeriesColors(sliceColors);
		
		int cont = 1;
		String nombre = "Grupo";
		for(int i = 0; i < valores.length; i++) {
			nombre = nombre + cont;
			chart.addSeries(nombre, valores[i]);
			cont++;
		}
		chart.getStyler().setLegendPosition(LegendPosition.InsideNW);
		chart.getStyler().setHasAnnotations(true);
		BitmapEncoder.saveBitmap(chart,"C:\\Usuarios\\Luu\\Escritorio\\histograma.png",BitmapFormat.PNG);
		
	}
	
	public CategoryChart getChart() {
		return null;
	}

}
