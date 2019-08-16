package me.khmoon.demoinflearnrestapi.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import me.khmoon.demoinflearnrestapi.common.RestDocsConfiguration;
import me.khmoon.demoinflearnrestapi.common.TestDescription;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.

        time.LocalDateTime;

import static org.springframework.restdocs.headers.HeaderDocumentation.*;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.linkWithRel;
import static org.springframework.restdocs.hypermedia.HypermediaDocumentation.links;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs
@Import(RestDocsConfiguration.class)
public class EventControllerTest {
  @Autowired
  MockMvc mockMvc;

  @Autowired
  ObjectMapper objectMapper;

  @Test
  @TestDescription("정상적으로 이벤트를 생성하는 테스트")
  public void createEvent() throws Exception {
    EventDto event = EventDto.builder()
            .name("Spring")
            .description("REST API Development with Spring")
            .beginEnrollmentDateTime(LocalDateTime.of(2018, 11, 23, 14, 21))
            .closeEnrollmentDateTime(LocalDateTime.of(2018, 11, 24, 14, 21))
            .beginEventDateTime(LocalDateTime.of(2018, 11, 25, 14, 21))
            .endEventDateTime(LocalDateTime.of(2018, 11, 26, 14, 21))
            .basePrice(100)
            .maxPrice(200)
            .limitOfEnrollment(100)
            .location("강남역 D2 스타텁 팩토리")
            .build();

    mockMvc.perform(MockMvcRequestBuilders.post("/api/events")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaTypes.HAL_JSON)
            .content(objectMapper.writeValueAsString(event))
    )
            .andDo(print())
            .andExpect(MockMvcResultMatchers.status().isCreated())
            .andExpect(MockMvcResultMatchers.jsonPath("id").exists())
            .andExpect(MockMvcResultMatchers.header().exists(HttpHeaders.LOCATION))
            .andExpect(MockMvcResultMatchers.header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_UTF8_VALUE))
            .andExpect(MockMvcResultMatchers.jsonPath("free").value(false))
            .andExpect(MockMvcResultMatchers.jsonPath("offline").value(true))
            .andExpect(MockMvcResultMatchers.jsonPath("eventStatus").value(EventStatus.DRAFT.name()))
            .andDo(document("create-event",
                    links(
                            linkWithRel("self").description("link to self"),
                            linkWithRel("query-events").description("link to query events"),
                            linkWithRel("update-event").description("link to update an existing event"),
                            linkWithRel("profile").description("link to update an existing event")
                    ),
                    requestHeaders(
                            headerWithName(HttpHeaders.ACCEPT).description("accept header"),
                            headerWithName(HttpHeaders.CONTENT_TYPE).description("content type header")
                    ),
                    requestFields(
                            fieldWithPath("name").description("Name of new event"),
                            fieldWithPath("description").description("description of new event"),
                            fieldWithPath("beginEnrollmentDateTime").description("date time of begin of new event"),
                            fieldWithPath("closeEnrollmentDateTime").description("date time of close of new event"),
                            fieldWithPath("beginEventDateTime").description("date time of close of new event"),
                            fieldWithPath("endEventDateTime").description("date time of close of new event"),
                            fieldWithPath("location").description("location of new event"),
                            fieldWithPath("basePrice").description("base price of new event"),
                            fieldWithPath("maxPrice").description("max price of new event"),
                            fieldWithPath("limitOfEnrollment").description("limit of enrollment")
                    ),
                    responseHeaders(
                            headerWithName(HttpHeaders.LOCATION).description("Location header"),
                            headerWithName(HttpHeaders.CONTENT_TYPE).description("Content type")

                    ),
                    responseFields(
                            fieldWithPath("id").description("identifier of new event"),
                            fieldWithPath("name").description("Name of new event"),
                            fieldWithPath("description").description("description of new event"),
                            fieldWithPath("beginEnrollmentDateTime").description("date time of begin of new event"),
                            fieldWithPath("closeEnrollmentDateTime").description("date time of close of new event"),
                            fieldWithPath("beginEventDateTime").description("date time of close of new event"),
                            fieldWithPath("endEventDateTime").description("date time of close of new event"),
                            fieldWithPath("location").description("location of new event"),
                            fieldWithPath("basePrice").description("base price of new event"),
                            fieldWithPath("maxPrice").description("max price of new event"),
                            fieldWithPath("limitOfEnrollment").description("limit of enrollment"),
                            fieldWithPath("free").description("it tells if this event is free or not"),
                            fieldWithPath("offline").description("it tells if this event is offline event or not"),
                            fieldWithPath("eventStatus").description("event status"),
                            fieldWithPath("_links.self.href").description("link to self"),
                            fieldWithPath("_links.query-events.href").description("link to query events"),
                            fieldWithPath("_links.update-event.href").description("link to update an existing event"),
                            fieldWithPath("_links.profile.href").description("link to profile")
                    )
            ))
    ;
  }

  @Test
  @TestDescription("입력 받을 수 없는 값을 사용한 경우에 에러가 발생하는 테스트")
  public void createEvent_Bad_Request() throws Exception {
    Event event = Event.builder()
            .id(100)
            .name("Spring")
            .description("REST API Development with Spring")
            .beginEnrollmentDateTime(LocalDateTime.of(2018, 11, 23, 14, 21))
            .closeEnrollmentDateTime(LocalDateTime.of(2018, 11, 24, 14, 21))
            .beginEventDateTime(LocalDateTime.of(2018, 11, 25, 14, 21))
            .endEventDateTime(LocalDateTime.of(2018, 11, 26, 14, 21))
            .basePrice(100)
            .maxPrice(200)
            .limitOfEnrollment(100)
            .location("강남역 D2 스타텁 팩토리")
            .free(true)
            .offline(false)
            .eventStatus(EventStatus.PUBLISHED)
            .build();

    mockMvc.perform(MockMvcRequestBuilders.post("/api/events")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .accept(MediaTypes.HAL_JSON)
            .content(objectMapper.writeValueAsString(event))
    )
            .andDo(print())
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
    ;
  }

  @Test
  @TestDescription("입력 값이 비어 있는 경우에 에러가 발생하는 테스트")
  public void createEvent_Bad_Request_Empty_Input() throws Exception {
    EventDto eventDto = EventDto.builder().build();
    this.mockMvc.perform(MockMvcRequestBuilders.post("/api/events")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(this.objectMapper.writeValueAsString(eventDto))
    )
            .andExpect(MockMvcResultMatchers.status().isBadRequest());
  }

  @Test
  @TestDescription("입력 값이 잘못된 경우에 에러가 발생하는 테스트")
  public void createEvent_Bad_Request_Wrong_Input() throws Exception {
    EventDto eventDto = EventDto.builder()
            .name("Spring")
            .description("REST API Development with Spring")
            .beginEnrollmentDateTime(LocalDateTime.of(2018, 11, 23, 14, 21))
            .closeEnrollmentDateTime(LocalDateTime.of(2018, 11, 24, 14, 21))
            .beginEventDateTime(LocalDateTime.of(2018, 11, 25, 14, 21))
            .endEventDateTime(LocalDateTime.of(2018, 11, 23, 14, 21))
            .basePrice(10000)
            .maxPrice(200)
            .limitOfEnrollment(100)
            .location("강남역 D2 스타텁 팩토리")
            .build();
    this.mockMvc.perform(MockMvcRequestBuilders.post("/api/events")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .content(this.objectMapper.writeValueAsString(eventDto))
    )
            .andDo(print())
            .andExpect(MockMvcResultMatchers.status().isBadRequest())
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].objectName").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].defaultMessage").exists())
            .andExpect(MockMvcResultMatchers.jsonPath("$[0].code").exists())
    ;
  }
}