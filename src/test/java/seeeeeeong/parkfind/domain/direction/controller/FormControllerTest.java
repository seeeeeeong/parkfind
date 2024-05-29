package seeeeeeong.parkfind.domain.direction.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import seeeeeeong.parkfind.domain.park.service.ParkRecommendationService;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.log;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class FormControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void main() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(handler().handlerType(FormController.class))
                .andExpect(handler().methodName("main"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andDo(log());
    }

//    @Test
//    void postDirection() throws Exception {
//        // given
//        String inputAddress = "서울 성북구 종암동";
//
//        // when
//        ResultActions result = mockMvc.perform(post("/search")
//                .param("address", inputAddress));
//
//        // then
//        result
//                .andExpect(status().isOk())
//                .andExpect(view().name("output"))
//                .andExpect(model().attributeExists("outputFormList"))
//                .andDo(print());
//    }

}