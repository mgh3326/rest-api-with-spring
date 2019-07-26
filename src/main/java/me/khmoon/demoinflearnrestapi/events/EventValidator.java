package me.khmoon.demoinflearnrestapi.events;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Component
public class EventValidator {
  public void validate(EventDto eventDto, Errors errors) {
    if (eventDto.getBasePrice() > eventDto.getMaxPrice() && eventDto.getMaxPrice() > 0) {
//      errors.rejectValue("basePrice", "WrongValue", "BasePrice is wrong.");
//      errors.rejectValue("maxPrice", "WrongValue", "maxPrice is wrong.");
      errors.reject("wrongPrices", "Values fo prices are wrong");
    }
    LocalDateTime endEventDateTime = eventDto.getEndEventDateTime();
    if (endEventDateTime.isBefore(eventDto.getBeginEventDateTime()) ||
            endEventDateTime.isBefore(eventDto.getCloseEnrollmentDateTime()) ||
            endEventDateTime.isBefore(eventDto.getBeginEnrollmentDateTime())) {
      errors.rejectValue("endEventDateTime", "wrongValue", "endEventDateTime is wrong");
    }
    //TODO BeginEventDateTime
    //TODO CloseEnrollmentDateTime
  }
}
