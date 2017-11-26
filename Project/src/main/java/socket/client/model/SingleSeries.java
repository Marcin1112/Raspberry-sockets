package socket.client.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Store measurement data from single sensor
 * 
 * @author Marcin
 *
 */
public class SingleSeries {
	private List<Double> series;

	/**
	 * Getter
	 * 
	 * @return
	 */
	public List<Double> getSeries() {
		return series;
	}

	/**
	 * Constructor
	 */
	public SingleSeries() {
		series = new ArrayList<Double>();
	}

	/**
	 * Add value to single series
	 * 
	 * @param value
	 *            value to add to the list
	 */
	public void addValue(double value) {
		if (series.size() == 300)
			series.remove(0);
		series.add(value);
	}
}
