package pojoClass;

import java.util.Comparator;

import utilities.Reporting;
import utilities.XMLReader;

public class TempratureComparator implements Comparator<TempratureObject> {
	Reporting report;

	public TempratureComparator(Reporting report) {
		this.report = report;
	}

	public int compare(TempratureObject arg0, TempratureObject arg1) {
		float allowedDeviation = Float.parseFloat(new XMLReader().readTagVal("AllowedDeviation"));
		float actualDeviation = 0;

		report.info("Deviation Allowed", "Allowed Deviation in Temperature " + allowedDeviation + " Degrees");

		if (new XMLReader().readTagVal("Degree").toLowerCase().startsWith("c")) {
			actualDeviation = Math.abs(arg0.getTempInCell() - arg1.getTempInCell());
			report.info("Value", "Temperature as per API = " + arg0.getTempInCell());
			report.info("Value", "Temperature as per UI = " + arg1.getTempInCell());
		} else {
			actualDeviation = Math.abs(arg0.getTempInFahren() - arg1.getTempInFahren());
			report.info("Value", "Temperature as per API = " + arg0.getTempInFahren());
			report.info("Value", "Temperature as per UI = " + arg1.getTempInFahren());
		}

		if (allowedDeviation >= actualDeviation)
			return 0;
		return 1;
	}

}
