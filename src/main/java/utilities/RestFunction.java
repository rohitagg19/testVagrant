package utilities;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class RestFunction {

	public static Response makeGetRequest(String city, String degree, String baseURI, String basePath,
			Reporting report) {

		XMLReader configReader = new XMLReader();

		report.info("URI", "Base URI = " + baseURI, false);
		report.info("Base Path", "Base Path = " + basePath, false);
		report.info("GET Request", "Making Get Request", false);

		RequestSpecification request = RestAssured.given();
		request.baseUri(baseURI);
		request.basePath(basePath);
		request.queryParam("q", city).queryParam("appid", configReader.readTagVal("APIToken")).queryParam("units",
				degree);

		Response resp = request.get();
		if (resp.statusCode() == 200)
			report.pass("Status Code", "Expected Code = 200 </br>Actual Code = 200", false);
		else
			report.pass("Status Code", "Expected Code = 200 </br>Actual Code = " + resp.statusCode(), false);
		return resp;

	}

}
