package com.headstrait.datasource.bdd;

import com.google.gson.Gson;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.MatcherAssert;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;

@Slf4j
public class DataSourceSteps {

    //ArrayList arrayListResponse;
    HttpClient client = HttpClient.newHttpClient();
    HttpResponse<String> httpResponse;
    final String BASE_URI = "http://localhost:9001";
    HttpRequest request;
    
    @When("^client calls GET /get$")
    public void clientCallsGETGet() {
        request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URI+"/get"))
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

    @And("The response should contain the json response")
    public void theResponseShouldContainTheJsonResponse() {
        final String responseBody = httpResponse.body();
        log.info(responseBody.substring(0,10));
    }


    @When("client calls GET with {string}")
        public void clientCallsGET(String endpoint) {
            request = HttpRequest.newBuilder()
                    .uri(URI.create(BASE_URI+"/"+endpoint))
                    .header("Accept", "application/json")
                    .GET()
                    .build();
            try {
                httpResponse = client.send(request, HttpResponse.BodyHandlers.ofString());
            }catch (IOException | InterruptedException e){
                e.printStackTrace();
            }
    }

    @And("if {int} is 200 receive a valid array")
    public void receivesAValidArray(int STATUS_CODE) {
        if(STATUS_CODE==200) {
            final String currentResponseBody = httpResponse.body();
            ArrayList<Object> convertedObject = new Gson().fromJson(currentResponseBody, ArrayList.class);
            MatcherAssert.assertThat("The status code is incorrect"
                    , convertedObject != null);
        }
    }
}
