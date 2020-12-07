package pojoClass;

import java.util.Comparator;

import utilities.XMLReader;

public class TempratureObject implements Comparator<TempratureObject> {
	String cityName;
	String condition;
	float tempInCell;
	float tempInFahren;
	float windSpeed;

	public String getCityName() {
		return cityName;
	}

	public void setCityName(String cityName) {
		this.cityName = cityName;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public float getTempInCell() {
		return tempInCell;
	}

	public void setTempInCell(float tempInCell) {
		this.tempInCell = tempInCell;
	}

	public float getTempInFahren() {
		return tempInFahren;
	}

	public void setTempInFahren(float tempInFahren) {
		this.tempInFahren = tempInFahren;
	}

	public float getWindSpeed() {
		return windSpeed;
	}

	public void setWindSpeed(float windSpeed) {
		this.windSpeed = windSpeed;
	}

	public int compare(TempratureObject arg0, TempratureObject arg1) {
		float allowedDeviation = Float.parseFloat(new XMLReader().readTagVal("AllowedDeviation"));
		float actualDeviation = 0;

		if (arg0.getTempInCell() > 0.0 && arg1.getTempInCell() > 0.0) {
			actualDeviation = Math.abs(arg0.getTempInCell() - arg1.getTempInCell());
		} else {
			actualDeviation = Math.abs(arg0.getTempInFahren() - arg1.getTempInFahren());
		}

		if (allowedDeviation >= actualDeviation)
			return 0;
		return 1;
	}
}
