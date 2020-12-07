package pageObject;

import io.restassured.response.Response;
import pojoClass.TempratureObject;
import utilities.Reporting;
import utilities.RestFunction;
import utilities.XMLReader;

public class WeatherClass {

	static Response resp;

	public static TempratureObject getWeatherInformation(String city, String degree,Reporting report) {
		XMLReader configReader = new XMLReader();

		if (degree.toLowerCase().startsWith("c"))
			degree = "metric";
		else
			degree = "imperial";

		resp = RestFunction.makeGetRequest(city, degree, configReader.readTagVal("BASEURI"),
				configReader.readTagVal("BasePath"),report);

		TempratureObject obj = new TempratureObject();
		
		obj.setCityName(city);
		obj.setCondition(resp.jsonPath().getString("weather[0].main"));
		obj.setWindSpeed(resp.jsonPath().getFloat("wind.speed"));

		if (degree.equalsIgnoreCase(degree))
			obj.setTempInCell(resp.jsonPath().getFloat("main.temp"));
		else
			obj.setTempInFahren(resp.jsonPath().getFloat("main.temp"));

		return obj;
	}

}
