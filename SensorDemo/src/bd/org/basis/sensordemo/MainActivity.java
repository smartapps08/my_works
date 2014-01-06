package bd.org.basis.sensordemo;

import java.util.ArrayList;

import org.achartengine.ChartFactory;
import org.achartengine.chart.LineChart;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity implements SensorEventListener {
	private TextView txtAx, txtAy, txtAz;
	private SensorManager sensorManager;
	private boolean isListening = false;
	private ArrayList<AccelData> allData;
	private LinearLayout layout;
	private View mChart;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		txtAx = (TextView) findViewById(R.id.txtAx);
		txtAy = (TextView) findViewById(R.id.txtAy);
		txtAz = (TextView) findViewById(R.id.txtAz);
		layout = (LinearLayout) findViewById(R.id.layout);

		sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);

	}

	public void start(View v) {
		Sensor sensor = sensorManager
				.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		sensorManager.registerListener(this, sensor,
				SensorManager.SENSOR_DELAY_NORMAL);
		isListening = true;
		allData = new ArrayList<AccelData>();
	}

	public void stop(View v) {
		sensorManager.unregisterListener(this);
		isListening = false;
		openChart();
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		if (isListening) {
			sensorManager.unregisterListener(this);
		}
	}

	@Override
	public void onAccuracyChanged(Sensor sensor, int accuracy) {

	}

	@Override
	public void onSensorChanged(SensorEvent event) {

		double x = event.values[0];
		double y = event.values[1];
		double z = event.values[2];
		txtAx.setText("Ax=" + x);
		txtAy.setText("Ay=" + y);
		txtAz.setText("Az=" + z);
		AccelData data = new AccelData(x, y, z, System.currentTimeMillis());
		allData.add(data);

	}

	public void openChart() {
		if (allData != null || allData.size() > 0) {
			long t = allData.get(0).getTimeStamp();
			XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();

			XYSeries xSeries = new XYSeries("X");
			XYSeries ySeries = new XYSeries("Y");
			XYSeries zSeries = new XYSeries("Z");

			for (AccelData data : allData) {
				xSeries.add(data.getTimeStamp() - t, data.getX());
				ySeries.add(data.getTimeStamp() - t, data.getY());
				zSeries.add(data.getTimeStamp() - t, data.getZ());
			}

			dataset.addSeries(xSeries);
			dataset.addSeries(ySeries);
			dataset.addSeries(zSeries);

			XYSeriesRenderer xRenderer = new XYSeriesRenderer();
			xRenderer.setColor(Color.RED);
			xRenderer.setPointStyle(PointStyle.CIRCLE);
			xRenderer.setFillPoints(true);
			xRenderer.setLineWidth(1);
			xRenderer.setDisplayChartValues(false);

			XYSeriesRenderer yRenderer = new XYSeriesRenderer();
			yRenderer.setColor(Color.GREEN);
			yRenderer.setPointStyle(PointStyle.CIRCLE);
			yRenderer.setFillPoints(true);
			yRenderer.setLineWidth(1);
			yRenderer.setDisplayChartValues(false);

			XYSeriesRenderer zRenderer = new XYSeriesRenderer();
			zRenderer.setColor(Color.BLUE);
			zRenderer.setPointStyle(PointStyle.CIRCLE);
			zRenderer.setFillPoints(true);
			zRenderer.setLineWidth(1);
			zRenderer.setDisplayChartValues(false);

			XYMultipleSeriesRenderer multiRenderer = new XYMultipleSeriesRenderer();
			multiRenderer.setXLabels(0);
			multiRenderer.setLabelsColor(Color.RED);
			multiRenderer.setChartTitle("t vs (x,y,z)");
			multiRenderer.setXTitle("Sensor Data");
			multiRenderer.setYTitle("Values of Acceleration");
			multiRenderer.setZoomButtonsVisible(true);
			for (int i = 0; i < allData.size(); i++) {

				multiRenderer.addXTextLabel(i + 1, ""
						+ (allData.get(i).getTimeStamp() - t));
			}
			for (int i = 0; i < 12; i++) {
				multiRenderer.addYTextLabel(i + 1, "" + i);
			}

			multiRenderer.addSeriesRenderer(xRenderer);
			multiRenderer.addSeriesRenderer(yRenderer);
			multiRenderer.addSeriesRenderer(zRenderer);

			// Getting a reference to LinearLayout of the MainActivity Layout

			// Creating a Line Chart
			mChart = ChartFactory.getLineChartView(getBaseContext(), dataset,
					multiRenderer);

			// Adding the Line Chart to the LinearLayout
			layout.addView(mChart);

		}
	}

}
