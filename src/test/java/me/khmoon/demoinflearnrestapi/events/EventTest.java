package me.khmoon.demoinflearnrestapi.events;


import org.assertj.core.api.Assertions;
import org.junit.Test;

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
}