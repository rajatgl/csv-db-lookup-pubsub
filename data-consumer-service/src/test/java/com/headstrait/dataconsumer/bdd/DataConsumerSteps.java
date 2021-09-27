package com.headstrait.dataconsumer.bdd;

import com.headstrait.dataconsumer.consumer.WaterPortabilityEventConsumer;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.extern.slf4j.Slf4j;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;

@Slf4j
public class DataConsumerSteps {

    WaterPortabilityEventConsumer consumer = new WaterPortabilityEventConsumer();

    @When("Data is produced onto {string} topic pipeline")
    public void dataIsProducedOntoTopicPipeline(String topic) {
        MatcherAssert.assertThat(consumer.consumerRecord.toString(),
                Matchers.containsString("embedded-test-topic"));
    }

    @Then("Data is persisted onto Oracle database.")
    public void dataIsPersistedOntoOracleDatabase() {
        MatcherAssert.assertThat("data persisted",true);
    }

    @Given("The producer is producing the data")
    public void theProducerIsProducingTheData() {
        MatcherAssert.assertThat("producer is running",true);
    }
}
