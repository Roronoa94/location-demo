package com.havells.locationdemo.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.havells.locationdemo.exceptions.LocationNotFoundException;
import com.havells.locationdemo.models.Location;
import com.havells.locationdemo.spy.LocationInteractorSpy;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = {LocationController.class, LocationInteractorSpy.class})
public class LocationControllerTest {

    private MockMvc mockMvc;
    private MvcResult requestResult;
    private static final Location testLocation = new Location(1234L,22.7,23.5,"09/12/2020");
    private static final String locationControllerUrl = "/api/v1/location";
    private static final String testLocationString = "{\"id\":null,\"userId\":1234,\"lattitude\":22.7,\"longitude\":23.5,\"timestamp\":\"09/12/2020\"}";
    private static final String testLocationArrayString = "[{\"id\":null,\"userId\":1234,\"lattitude\":22.7,\"longitude\":23.5,\"timestamp\":\"09/12/2020\"}]";

    @Autowired
    private WebApplicationContext webApplicationContext;

    @Autowired
    private LocationInteractorSpy locationInteractorSpy;

    @BeforeClass
    public static void setUp() {

    }

    @AfterClass
    public static void teardown() {

    }

    @Before
    public void setup() {
        locationInteractorSpy.reset();
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.webApplicationContext).build();
    }

    @Test
    public void addLocation() throws Exception {
        givenLocationInteractorReturns(testLocation);
        whenAddLocationIsCalled();
        thenAssertAddLocationIsInvokedWith(testLocation);
        thenHttpResponseStatusIs(HttpStatus.CREATED);
        thenHttpResponseBodyIs(testLocationString);
    }

    @Test
    public void addLocationThrowsException() throws Exception {
        givenLocationInteractorThrows(new RuntimeException("Error Occured"));
        whenAddLocationIsCalled();
        thenAssertAddLocationIsInvokedWith(testLocation);
        thenHttpResponseStatusIs(HttpStatus.INTERNAL_SERVER_ERROR);
        thenHttpResponseBodyIs("");
    }

    @Test
    public void getLocations() throws Exception {
        givenLocationInteractorReturns(Arrays.asList(testLocation));
        whenGetLocationsIsCalled();
        thenHttpResponseStatusIs(HttpStatus.OK);
        thenHttpResponseBodyIs(testLocationArrayString);
    }

    @Test
    public void getLocationsWhenNoRecordExits() throws Exception {
        givenLocationInteractorReturns(new ArrayList<Location>());
        whenGetLocationsIsCalled();
        thenHttpResponseStatusIs(HttpStatus.OK);
        thenHttpResponseBodyIs("[]");
    }

    @Test
    public void getLocationsThrowsException() throws Exception {
        givenLocationInteractorThrows(new RuntimeException("Error Occured"));
        whenGetLocationsIsCalled();
        thenHttpResponseStatusIs(HttpStatus.INTERNAL_SERVER_ERROR);
        thenHttpResponseBodyIs("");
    }

    @Test
    public void getLocationById() throws Exception {
        givenLocationInteractorReturns(testLocation);
        whenGetLocationByIdIsCalled();
        thenAssertGetLocationByIdIsInvokedWith(1l);
        thenHttpResponseStatusIs(HttpStatus.OK);
        thenHttpResponseBodyIs(testLocationString);
    }

    @Test
    public void getLocationByIdWhenRecordDoesNotExist() throws Exception {
        givenLocationInteractorThrows(new LocationNotFoundException(1L));
        whenGetLocationByIdIsCalled();
        thenAssertGetLocationByIdIsInvokedWith(1l);
        thenHttpResponseStatusIs(HttpStatus.NOT_FOUND);
        thenHttpResponseBodyIs("");
    }

    @Test
    public void getLocationByUserId() throws Exception {
        givenLocationInteractorReturns(Arrays.asList(testLocation));
        whenGetLocationUserIdIsCalled();
        thenAssertGetLocationByIdIsInvokedWith(1234l);
        thenHttpResponseStatusIs(HttpStatus.OK);
        thenHttpResponseBodyIs(testLocationArrayString);
    }

    @Test
    public void getLocationByUserIdWhenNoRecordExits() throws Exception {
        givenLocationInteractorReturns(new ArrayList<Location>());
        whenGetLocationUserIdIsCalled();
        thenAssertGetLocationByIdIsInvokedWith(1234l);
        thenHttpResponseStatusIs(HttpStatus.OK);
        thenHttpResponseBodyIs("[]");
    }

    @Test
    public void getLocationByUserIdThrowsException() throws Exception {
        givenLocationInteractorThrows(new RuntimeException("Error Occured"));
        whenGetLocationUserIdIsCalled();
        thenAssertGetLocationByIdIsInvokedWith(1234l);
        thenHttpResponseStatusIs(HttpStatus.INTERNAL_SERVER_ERROR);
        thenHttpResponseBodyIs("");
    }

    // Given Statements

    private void givenLocationInteractorReturns(Location location) {
        locationInteractorSpy.response = location;
    }

    private void givenLocationInteractorReturns(List<Location> locations) {
        locationInteractorSpy.locationList = locations;
    }

    private void givenLocationInteractorThrows(RuntimeException exception) {
        locationInteractorSpy.exception = exception;
    }

    // When Statements

    private void whenAddLocationIsCalled() throws Exception {
        requestResult = mockMvc.perform(post(locationControllerUrl)
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(getRequestBody(testLocation)))
                .andReturn();
    }

    private void whenGetLocationsIsCalled() throws Exception {
        requestResult = mockMvc.perform(get(locationControllerUrl)
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();
    }

    private void whenGetLocationByIdIsCalled() throws Exception {
        requestResult = mockMvc.perform(get(locationControllerUrl + "/1")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();
    }

    private void whenGetLocationUserIdIsCalled() throws Exception {
        requestResult = mockMvc.perform(get(locationControllerUrl + "/user/1234")
                .header("Content-Type", MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andReturn();
    }

    // Then Statements

    private void thenAssertAddLocationIsInvokedWith(Location expectedLocationObject) {
        assertEquals(expectedLocationObject.getUserId(), locationInteractorSpy.input.getUserId());
        assertEquals(expectedLocationObject.getTimestamp(), locationInteractorSpy.input.getTimestamp());
    }

    private void thenAssertGetLocationByIdIsInvokedWith(Long expectedLocationId) {
        assertEquals(expectedLocationId, locationInteractorSpy.inputId);
    }

    private void thenHttpResponseStatusIs(HttpStatus expectedStatus) {
        assertEquals(expectedStatus.value(), requestResult.getResponse().getStatus());
    }

    private void thenHttpResponseBodyIs(String expectedJson) throws Exception {
        assertEquals(expectedJson, requestResult.getResponse().getContentAsString());
    }

    private String getRequestBody(Object object) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(object);
    }

}
