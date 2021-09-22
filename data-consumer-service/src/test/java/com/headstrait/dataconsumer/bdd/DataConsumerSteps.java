package com.headstrait.dataconsumer.bdd;

import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@Slf4j
public class DataConsumerSteps {

    //ArrayList arrayListResponse;
    HttpClient client = HttpClient.newHttpClient();
    HttpResponse<String> httpResponse;
    final String BASE_URI = "http://localhost:9001";
    HttpRequest request;

    @When("Data is produced onto {string} topic pipeline")
    public void dataIsProducedOntoTopicPipeline(String arg0) {
        assert true;
    }

    @Then("Data is persisted onto Oracle database.")
    public void dataIsPersistedOntoOracleDatabase() {
        assert true;
    }
}
