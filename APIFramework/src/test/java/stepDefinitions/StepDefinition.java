package stepDefinitions;
import org.junit.*;
import org.junit.Assert.*;
import pojo.AddPlace;
import pojo.Location;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static org.junit.Assert.assertEquals;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.given;

public class StepDefinition extends Utils {

    RequestSpecification res;
    ResponseSpecification resspec;
    TestDataBuild dataBuild = new TestDataBuild();
    static String placeId;
    Response response;

    @Given("Add Place Payload with {string} {string} {string}")
    public void add_place_payload_with(String name, String language, String address) throws IOException {
        // Write code here that turns the phrase above into concrete actions
        System.out.println("add place payload");


        res = given().spec(requestSpecification())
                .body(dataBuild.addPlacePayLoad(name, language, address));

    }

    @When("user calls {string} with {string} http request")
    public void user_calls_with_post_http_request(String resource, String method) {
        // Write code here that turns the phrase above into concrete actions
        //constructor will be called with value of resource which you pass
        System.out.println("user call " + method + " request");
        APIResources recourseAPI = APIResources.valueOf("deletePlaceAPI");
        System.out.println(recourseAPI.getResource());

      //  resspec = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON).build();


        if (method.equalsIgnoreCase("POST"))
            response = res.when().post(recourseAPI.getResource());
        else if (method.equalsIgnoreCase("GET"))
            response = res.when().get(recourseAPI.getResource());

    }

    @Then("the API call is succes with status code {int}")
    public void the_api_call_is_succes_with_status_code(Object int1) {
        System.out.println("checking status code ");
        // Write code here that turns the phrase above into concrete actions
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Then("{string} in response body is {string}")
    public void in_response_body_is(String key, String ExpectedValue) {
        // Write code here that turns the phrase above into concrete actions
        System.out.println("checking " + key + " response ");

        assertEquals(getJsonPath(response, key), ExpectedValue);
    }

    @Then("verify place_Id created maps to {string} using {string}")
    public void verify_place_Id_created_maps_to_using(String expectedName, String resource) throws IOException {
        //Write code here that turns the phrase above into concrete actions

        String place_id = getJsonPath(response, "place_id");
        res = given().spec(requestSpecification()).queryParam("place_Id", place_id);
        user_calls_with_post_http_request(resource, "GET");
        String actualName = getJsonPath(response, "name");

        assertEquals(actualName, expectedName);

    }

  @Given("DeletePlace payload")
   public void delete_place_payload() throws IOException {
     res = given().spec(requestSpecification()).body(dataBuild.deletePlacePayload(placeId));
   }
}
