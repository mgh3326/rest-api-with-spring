package me.khmoon.demoinflearnrestapi.events;


import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(JUnitParamsRunner.class)
public class EventTest {
  @Test
  public void builder() {
    Event event = Event.builder().build();
    Assertions.assertThat(event).isNotNull();
  }

  @Test
  public void javaBean() {
    //Given
    Event event = new Event();
    String name = "Event";
    event.setName(name);
    String description = "Spring";
    event.setDescription(description);


    Assertions.assertThat(event.getName()).isEqualTo(name);
    Assertions.assertThat(event.getDescription()).isEqualTo(description);
  }

  @Test
  @Parameters(method = "parametersForTestFree")
  public void testFree(int basePrice, int maxPrice, boolean isFree) {
    Event event = Event.builder()
            .basePrice(basePrice)
            .maxPrice(maxPrice)
            .build();
//When
    event.update();
//Then
    Assertions.assertThat(event.isFree()).isEqualTo(isFree);
  }

  private Object[] parametersForTestFree() {
    return new Object[]{
            new Object[]{0, 0, true},
            new Object[]{100, 0, false},
            new Object[]{0, 100, false},
            new Object[]{100, 100, false},
    };
  }

  @Test
  @Parameters
  public void testOffline(String location, boolean isOffline) {
    Event event = Event.builder()
            .location(location)
            .build();

    event.update();

    Assertions.assertThat(event.isOffline()).isEqualTo(isOffline);
  }

  private Object[] parametersForTestOffline() {
    return new Object[]{
            new Object[]{"강남", true},
            new Object[]{null, false},
            new Object[]{"      ", false},

    };
  }
}