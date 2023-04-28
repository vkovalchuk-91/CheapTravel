package org.geekhub.kovalchuk.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PropertiesConfig {
    @Value("${flight.properties.search.max_months}")
    private int maxMonths;
    @Value("${flight.properties.default.list.top.cities_in_operation_id}")
    private String topCitiesInOperationEntityId;
    @Value("${flight.properties.request.interval_in_seconds}")
    private int requestIntervalInSeconds;
    @Value("${flight.properties.request.requests_per_minute}")
    private int requestsPerMinute;
    @Value("${flight.properties.request.error.timeout_before_next_try_in_seconds}")
    private int errorTimeoutBeforeNextTryInSeconds;

    public int getMaxMonths() {
        return maxMonths;
    }

    public void setMaxMonths(int maxMonths) {
        this.maxMonths = maxMonths;
    }

    public String getTopCitiesInOperationEntityId() {
        return topCitiesInOperationEntityId;
    }

    public void setTopCitiesInOperationEntityId(String topCitiesInOperationEntityId) {
        this.topCitiesInOperationEntityId = topCitiesInOperationEntityId;
    }

    public int getRequestIntervalInSeconds() {
        return requestIntervalInSeconds;
    }

    public void setRequestIntervalInSeconds(int requestIntervalInSeconds) {
        this.requestIntervalInSeconds = requestIntervalInSeconds;
    }

    public int getRequestsPerMinute() {
        return requestsPerMinute;
    }

    public void setRequestsPerMinute(int requestsPerMinute) {
        this.requestsPerMinute = requestsPerMinute;
    }

    public int getErrorTimeoutBeforeNextTryInSeconds() {
        return errorTimeoutBeforeNextTryInSeconds;
    }

    public void setErrorTimeoutBeforeNextTryInSeconds(int errorTimeoutBeforeNextTryInSeconds) {
        this.errorTimeoutBeforeNextTryInSeconds = errorTimeoutBeforeNextTryInSeconds;
    }
}
