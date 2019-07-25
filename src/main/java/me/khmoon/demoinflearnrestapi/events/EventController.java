package me.khmoon.demoinflearnrestapi.events;

import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URI;

@Controller
@RequestMapping(value = "/api/events", produces = MediaTypes.HAL_JSON_UTF8_VALUE)
public class EventController {
  @PostMapping
  public ResponseEntity createEvent(@RequestBody Event event) {

    URI createUri = ControllerLinkBuilder.linkTo(EventController.class).slash("{id}").toUri();
    event.setId(10);
    return ResponseEntity.created(createUri).body(event);
  }
}
