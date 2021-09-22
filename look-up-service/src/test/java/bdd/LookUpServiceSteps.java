package bdd;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.hamcrest.MatcherAssert;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class LookUpServiceSteps {

    //ArrayList arrayListResponse;
    HttpClient client = HttpClient.newHttpClient();
    HttpResponse<String> httpResponse;
    final String BASE_URI = "http://localhost:8090";
    HttpRequest request;


    @When("^The client calls GET /fetch$")
    public void theClientCallsGETEndpoint() {
        request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URI+"/fetch"))
                .header("Accept", "application/json")
                .GET()
                .build();
        try {
            httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        }catch (IOException | InterruptedException e){
            e.printStackTrace();
        }
    }

    @Then("receives status code {int}")
    public void receivesStatusCode(int STATUS_CODE) {
        final int currentHttpStatusCode = httpResponse.statusCode();
        MatcherAssert.assertThat("The status code is incorrect"
                ,currentHttpStatusCode==STATUS_CODE);
    }

    @And("The response should contain")
    public void theResponseShouldContain() {
        final String responseBody = httpResponse.body();
        System.out.println(responseBody);
    }

    @Then("for a particular id: {int}")
    public void forAParticularIdId(int id) {
        request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URI+"/fetch/"+id))
                .header("Accept", "application/json")
                .GET()
                .build();
        try {
            httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
        }catch (IOException | InterruptedException e){
            e.printStackTrace();
        }
    }


    @And("The response should contain water-potability object")
    public void theResponseShouldContainWaterPotabilityObject() {
        final String responseBody = httpResponse.body();
        System.out.println(responseBody);
    }
}
