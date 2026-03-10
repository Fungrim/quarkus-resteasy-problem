package io.quarkiverse.resteasy.problem.jackson;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.quarkiverse.resteasy.problem.HttpProblem;
import io.quarkiverse.resteasy.problem.postprocessing.PostProcessorsRegistry;
import io.quarkiverse.resteasy.problem.postprocessing.ProblemContext;

public class InstanceEncodingTest {

    @Test
    public void httpProblemInstanceEncoding() throws JsonProcessingException {
        var path = "/api/v1/commons/meeting/666/document/ref/X 1/3";
        var mapper = new ObjectMapper();
        new JacksonProblemModuleRegistrar().customize(mapper);
        var originalProblem = HttpProblem.builder()
                .withTitle("URI Encoding Problem")
                .build();
        var context = ProblemContext.of(null, path);
        var processedProblem = new PostProcessorsRegistry().applyPostProcessing(originalProblem, context);
        var json = mapper.writeValueAsString(processedProblem);
        Assertions.assertDoesNotThrow(() -> {
            mapper.readValue(json, HttpProblem.class);
        });
    }
}
