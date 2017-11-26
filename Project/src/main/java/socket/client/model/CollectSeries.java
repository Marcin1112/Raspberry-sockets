package socket.client.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.control.CheckMenuItem;

/**
 * Store data from all sensors
 * 
 * @author Marcin
 *
 */
public class CollectSeries {
	private Map<String, SingleSeries> allSeries;
	private double min;
	private double max;

	/**
	 * Constructor. Add series: "Time" and "Kanał - 8"
	 */
	public CollectSeries() {
		allSeries = new HashMap<String, SingleSeries>();
		allSeries.put("Time", new SingleSeries());
		allSeries.put("Kanał 1", new SingleSeries());
		allSeries.put("Kanał 2", new SingleSeries());
		allSeries.put("Kanał 3", new SingleSeries());
		allSeries.put("Kanał 4", new SingleSeries());
		allSeries.put("Kanał 5", new SingleSeries());
		allSeries.put("Kanał 6", new SingleSeries());
		allSeries.put("Kanał 7", new SingleSeries());
		allSeries.put("Kanał 8", new SingleSeries());
	}

	/**
	 * Parse raw data and put into the map 'allSeries'
	 * 
	 * @param line
	 *            contain 9 semicolon separated double values
	 */
	public void addValues(String line) {
		String[] array = line.split(";");
		if (array.length < 8)
			return;
		try {
			addValue("Time", Double.parseDouble(array[0]));
			addValue("Kanał 1", Double.parseDouble(array[1]));
			addValue("Kanał 2", Double.parseDouble(array[2]));
			addValue("Kanał 3", Double.parseDouble(array[3]));
			addValue("Kanał 4", Double.parseDouble(array[4]));
			addValue("Kanał 5", Double.parseDouble(array[5]));
			addValue("Kanał 6", Double.parseDouble(array[6]));
			addValue("Kanał 7", Double.parseDouble(array[7]));
			addValue("Kanał 8", Double.parseDouble(array[8]));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Get List that contain measurement data for single sensor
	 * 
	 * @param series
	 *            The name of the series
	 * @return List
	 */
	public List getSingleSeriesList(String series) {
		return allSeries.get(series).getSeries();
	}

	/**
	 * Calculate minimum value for Y axis
	 * 
	 * @param chn1
	 *            CheckMenuItem
	 * @param chn2
	 *            CheckMenuItem
	 * @param chn3
	 *            CheckMenuItem
	 * @param chn4
	 *            CheckMenuItem
	 * @param chn5
	 *            CheckMenuItem
	 * @param chn6
	 *            CheckMenuItem
	 * @param chn7
	 *            CheckMenuItem
	 * @param chn8
	 *            CheckMenuItem
	 * @return minimum value for Y axis
	 */
	public double getMinAxisScale(CheckMenuItem chn1, CheckMenuItem chn2, CheckMenuItem chn3, CheckMenuItem chn4,
			CheckMenuItem chn5, CheckMenuItem chn6, CheckMenuItem chn7, CheckMenuItem chn8) {
		max = Double.MIN_VALUE;
		min = Double.MAX_VALUE;
		findMin(chn1, chn2, chn3, chn4, chn5, chn6, chn7, chn8);
		findMax(chn1, chn2, chn3, chn4, chn5, chn6, chn7, chn8);

		double diff = max - min;
		double delta = diff * 0.1;
		return min - delta;
	}

	/**
	 * Calculate maximum value for Y axis
	 * 
	 * @param chn1
	 *            CheckMenuItem
	 * @param chn2
	 *            CheckMenuItem
	 * @param chn3
	 *            CheckMenuItem
	 * @param chn4
	 *            CheckMenuItem
	 * @param chn5
	 *            CheckMenuItem
	 * @param chn6
	 *            CheckMenuItem
	 * @param chn7
	 *            CheckMenuItem
	 * @param chn8
	 *            CheckMenuItem
	 * @return maximum value for Y axis
	 */
	public double getMaxAxisScale(CheckMenuItem chn1, CheckMenuItem chn2, CheckMenuItem chn3, CheckMenuItem chn4,
			CheckMenuItem chn5, CheckMenuItem chn6, CheckMenuItem chn7, CheckMenuItem chn8) {
		max = Double.MIN_VALUE;
		min = Double.MAX_VALUE;
		findMin(chn1, chn2, chn3, chn4, chn5, chn6, chn7, chn8);
		findMax(chn1, chn2, chn3, chn4, chn5, chn6, chn7, chn8);
		double diff = max - min;
		double delta = diff * 0.1;
		return max + delta;
	}

	/**
	 * Calculate interval for X axis
	 * 
	 * @return interval
	 */
	public double getTickX() {
		List timeSer = getSingleSeriesList("Time");
		double minX = (double) timeSer.get(0);
		double maxX = (double) getSingleSeriesList("Time").get(timeSer.size() - 1);
		return ((maxX - minX) / 5);
	}

	/**
	 * Find minimum for the active channels
	 * 
	 * @param chn1
	 *            CheckMenuItem
	 * @param chn2
	 *            CheckMenuItem
	 * @param chn3
	 *            CheckMenuItem
	 * @param chn4
	 *            CheckMenuItem
	 * @param chn5
	 *            CheckMenuItem
	 * @param chn6
	 *            CheckMenuItem
	 * @param chn7
	 *            CheckMenuItem
	 * @param chn8
	 *            CheckMenuItem
	 */
	private void findMin(CheckMenuItem chn1, CheckMenuItem chn2, CheckMenuItem chn3, CheckMenuItem chn4,
			CheckMenuItem chn5, CheckMenuItem chn6, CheckMenuItem chn7, CheckMenuItem chn8) {
		chnMin(chn1, "Kanał 1");
		chnMin(chn2, "Kanał 2");
		chnMin(chn3, "Kanał 3");
		chnMin(chn4, "Kanał 4");
		chnMin(chn5, "Kanał 5");
		chnMin(chn6, "Kanał 6");
		chnMin(chn7, "Kanał 7");
		chnMin(chn8, "Kanał 8");
	}

	/**
	 * find maximum for the active channels
	 * 
	 * @param chn1
	 *            CheckMenuItem
	 * @param chn2
	 *            CheckMenuItem
	 * @param chn3
	 *            CheckMenuItem
	 * @param chn4
	 *            CheckMenuItem
	 * @param chn5
	 *            CheckMenuItem
	 * @param chn6
	 *            CheckMenuItem
	 * @param chn7
	 *            CheckMenuItem
	 * @param chn8
	 *            CheckMenuItem
	 */
	private void findMax(CheckMenuItem chn1, CheckMenuItem chn2, CheckMenuItem chn3, CheckMenuItem chn4,
			CheckMenuItem chn5, CheckMenuItem chn6, CheckMenuItem chn7, CheckMenuItem chn8) {
		chnMax(chn1, "Kanał 1");
		chnMax(chn2, "Kanał 2");
		chnMax(chn3, "Kanał 3");
		chnMax(chn4, "Kanał 4");
		chnMax(chn5, "Kanał 5");
		chnMax(chn6, "Kanał 6");
		chnMax(chn7, "Kanał 7");
		chnMax(chn8, "Kanał 8");
	}

	/**
	 * Find minimum for the single sensor measured values
	 * 
	 * @param chn
	 *            CheckMenuItem - sensor
	 * @param chnName
	 *            Name of data series (sensor)
	 */
	private void chnMin(CheckMenuItem chn, String chnName) {
		if (chn.isSelected()) {
			for (Double i : (ArrayList<Double>) getSingleSeriesList(chnName)) {
				if (min > i)
					min = i;
			}
		}
	}

	/**
	 * Find maximum for the single sensor measured values
	 * 
	 * @param chn
	 *            CheckMenuItem - sensor
	 * @param chnName
	 *            Name of data series (sensor)
	 */
	private void chnMax(CheckMenuItem chn, String chnName) {
		if (chn.isSelected()) {
			for (Double i : (ArrayList<Double>) getSingleSeriesList(chnName)) {
				if (max < i)
					max = i;
			}
		}
	}

	/**
	 * Add value to a single series
	 * 
	 * @param series
	 *            name of data series (sensor)
	 * @param value
	 *            value to be added
	 */
	private void addValue(String series, Double value) {
		allSeries.get(series).addValue(value);
	}
}
