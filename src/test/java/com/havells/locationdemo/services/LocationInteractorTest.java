package com.havells.locationdemo.services;

import com.havells.locationdemo.exceptions.LocationNotFoundException;
import com.havells.locationdemo.models.Location;
import com.havells.locationdemo.repositories.LocationRepository;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import static org.junit.Assert.assertEquals;

public class LocationInteractorTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Mock
    private LocationRepository repositorySpy;

    @InjectMocks
    private LocationInteractor locationInteractor;

    private static final Location testLocation = new Location(1L,1234L,22.7,23.5,"09/12/2020");
    private Location actualLocationResponse;
    private List<Location> actualLocationsList;

    @Before
    public void setUp() {
        locationInteractor = new LocationInteractor(repositorySpy);
    }

    @Test
    public void addLocation() {
        givenLocationRepositoryReturns(testLocation);
        whenAddLocationIsCalled();
        thenAssertResponseIs(testLocation);
    }

    @Test
    public void getLocationById() {
        givenLocationRepositoryReturnsLocationWithId(1L);
        whenGetLocationByIdIsCalled();
        thenAssertResponseIs(testLocation);
    }

    @Test(expected = LocationNotFoundException.class)
    public void getLocationByIdWhenRecordDoesNotExist() {
        givenLocationRepositoryReturns();
        whenGetLocationByIdIsCalled();
    }

    @Test
    public void getLocations() {
        givenLocationRepositoryReturns(Arrays.asList(testLocation));
        whenGetLocationsIsCalled();
        thenAssertResponseIs(Arrays.asList(testLocation));
    }

    @Test
    public void getLocationsWhenRecordDoesNotExist() {
        givenLocationRepositoryReturns(Arrays.asList());
        whenGetLocationsIsCalled();
        thenAssertResponseIs(Arrays.asList());
    }

    @Test
    public void getLocationsByUserId() {
        givenLocationRepositoryReturnsLocationsWithUserId(Arrays.asList(testLocation), 1234L);
        whenGetLocationByUserIdIsCalled();
        thenAssertResponseIs(Arrays.asList(testLocation));
    }

    @Test
    public void getLocationsByUserIdWhenRecordDoesNotExist() {
        givenLocationRepositoryReturnsLocationsWithUserId(Arrays.asList(), 1234L);
        whenGetLocationByUserIdIsCalled();
        thenAssertResponseIs(Arrays.asList());
    }

    // Given Statements

    private void givenLocationRepositoryReturns(Location location) {
        Mockito.when(repositorySpy.save(location))
                .thenReturn(testLocation);
    }

    private void givenLocationRepositoryReturns() {
        Mockito.when(repositorySpy.findById(1L))
                .thenReturn(Optional.empty());
    }

    private void givenLocationRepositoryReturns(List<Location> locations) {
        Mockito.when(repositorySpy.findAll())
                .thenReturn(locations);
    }

    private void givenLocationRepositoryReturnsLocationWithId(Long locationId) {
        Mockito.when(repositorySpy.findById(locationId))
                .thenReturn(Optional.of(new Location(locationId,1234L,22.7,23.5,"09/12/2020")));
    }

    private void givenLocationRepositoryReturnsLocationsWithUserId(List<Location> locations, Long userId) {
        Mockito.when(repositorySpy.findByUserId(userId))
                .thenReturn(locations);
    }

    // When Statements

    private void whenAddLocationIsCalled() {
        actualLocationResponse = locationInteractor.addLocation(testLocation);
    }

    private void whenGetLocationByIdIsCalled() {
        actualLocationResponse = locationInteractor.getLocationById(1L);
    }

    private void whenGetLocationsIsCalled() {
        actualLocationsList = locationInteractor.getLocations();
    }

    private void whenGetLocationByUserIdIsCalled() {
        actualLocationsList = locationInteractor.getLocationByUserId(1234L);
    }

    // Then Statements

    private void thenAssertResponseIs(Location expectedResponse) {
        assertEquals(expectedResponse, actualLocationResponse);
    }

    private void thenAssertResponseIs(List<Location> expectedResponse) {
        assertEquals(expectedResponse, actualLocationsList);
    }

}
