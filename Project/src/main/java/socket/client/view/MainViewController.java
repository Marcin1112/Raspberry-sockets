package socket.client.view;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TextField;
import socket.client.model.CollectSeries;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.Socket;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;

import javafx.scene.control.CheckMenuItem;

/**
 * Main Controller
 * 
 * @author Marcin
 *
 */
public class MainViewController implements Initializable {
	@FXML
	private TextField textIP;
	@FXML
	private Button startButton;
	@FXML
	private LineChart<Number, Number> linePlot;
	@FXML
	private CheckMenuItem chn1;
	@FXML
	private CheckMenuItem chn2;
	@FXML
	private CheckMenuItem chn3;
	@FXML
	private CheckMenuItem chn4;
	@FXML
	private CheckMenuItem chn5;
	@FXML
	private CheckMenuItem chn6;
	@FXML
	private CheckMenuItem chn7;
	@FXML
	private CheckMenuItem chn8;
	private Socket client;
	private boolean measurement;

	public MainViewController() {
		measurement = false;
	}

	/**
	 * Event Listener on Button[#startButton].onAction
	 * 
	 * @param event
	 *            ActionEvent
	 */
	@FXML
	public void startMeasurement(ActionEvent event) {
		measurement = !measurement;
		if (measurement) {
			measurementThread();
		} else {
			try {
				startButton.setText("Start");
				client.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Initialize chart
	 */
	public void initialize(URL location, ResourceBundle resources) {
		linePlot.setTitle("Pomiar napięcia");
		linePlot.getXAxis().setLabel("Czas [s]");
		linePlot.getYAxis().setLabel("Napięcie [V]");
		linePlot.getXAxis().setAutoRanging(false);
		linePlot.getYAxis().setAutoRanging(false);
	}

	/**
	 * Create new Thread that update chart
	 */
	private void measurementThread() {
		Thread thread = new Thread(() -> {
			CollectSeries data = new CollectSeries();
			String serverName = textIP.getText().trim();
			int port = 6066;
			try {
				client = new Socket(serverName, port);
				InputStream inFromServer = client.getInputStream();
				DataInputStream in = new DataInputStream(inFromServer);
				readDataFromServer(data, in);
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
		thread.setDaemon(true);
		thread.start();
	}

	/**
	 * Connect to the server and read data
	 * 
	 * @param data
	 *            The model
	 * @param in
	 *            DataInputStream
	 * @throws IOException
	 *             Exception
	 */
	private void readDataFromServer(CollectSeries data, DataInputStream in) throws IOException {
		while (true) {
			String line = in.readUTF();
			if (!line.equals("")) {
				data.addValues(line);
				System.out.println(line);
				modifyChart(data);
			}
		}
	}

	/**
	 * Update chart
	 * 
	 * @param data
	 *            the model
	 */
	private void modifyChart(CollectSeries data) {
		Platform.runLater(() -> {
			startButton.setText("Stop");
			linePlot.getData().clear();

			drawSeriesChn(data, chn1, "Kanał 1");
			drawSeriesChn(data, chn2, "Kanał 2");
			drawSeriesChn(data, chn3, "Kanał 3");
			drawSeriesChn(data, chn4, "Kanał 4");
			drawSeriesChn(data, chn5, "Kanał 5");
			drawSeriesChn(data, chn6, "Kanał 6");
			drawSeriesChn(data, chn7, "Kanał 7");
			drawSeriesChn(data, chn8, "Kanał 8");

			linePlot.setCreateSymbols(false);
			NumberAxis xAxis = (NumberAxis) linePlot.getXAxis();
			xAxis.setLowerBound((double) data.getSingleSeriesList("Time").get(0));
			Integer sizeTime = data.getSingleSeriesList("Time").size();
			List listTime = data.getSingleSeriesList("Time");
			xAxis.setUpperBound((double) listTime.get(sizeTime - 1));

			NumberAxis yAxis = (NumberAxis) linePlot.getYAxis();
			double minY = data.getMinAxisScale(chn1, chn2, chn3, chn4, chn5, chn6, chn7, chn8);
			double maxY = data.getMaxAxisScale(chn1, chn2, chn3, chn4, chn5, chn6, chn7, chn8);
			yAxis.setLowerBound(minY);
			yAxis.setUpperBound(maxY);
			yAxis.setTickUnit((maxY - minY) / 3);
			xAxis.setTickUnit(data.getTickX());

		});
	}

	/**
	 * Draw data for a single channel to chart
	 * 
	 * @param data
	 *            data for single channel
	 * @param item
	 *            CheckMenuItem
	 * @param chnName
	 *            name of the channel
	 */
	private void drawSeriesChn(CollectSeries data, CheckMenuItem item, String chnName) {
		if (item.isSelected()) {
			XYChart.Series<Number, Number> seriesChn1 = new XYChart.Series<Number, Number>();
			seriesChn1.setName(chnName);
			for (int i = 0; i < data.getSingleSeriesList(chnName).size(); i++) {
				Number xData = (Number) data.getSingleSeriesList("Time").get(i);
				Number yData = (Number) data.getSingleSeriesList(chnName).get(i);
				seriesChn1.getData().add(new XYChart.Data<Number, Number>(xData, yData));
			}
			linePlot.getData().add(seriesChn1);
		}
	}

}
