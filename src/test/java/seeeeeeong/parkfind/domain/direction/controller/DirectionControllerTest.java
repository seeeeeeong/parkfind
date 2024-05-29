package seeeeeeong.parkfind.domain.direction.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import seeeeeeong.parkfind.domain.direction.service.DirectionService;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
class DirectionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DirectionService directionService;

    @Test
    void searchDirection() throws Exception {
        // given
        String encodedId = "r";
        String redirectURL = "https://map.kakao.com/link/map/pharmacy,38.11,128.11";

        // when
        when(directionService.findDirectionUrlById(encodedId)).thenReturn(redirectURL);
        ResultActions result = mockMvc.perform(get("/dir/{encodedId}", encodedId));

        // then
        result
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl(redirectURL))
                .andDo(print());
    }


}