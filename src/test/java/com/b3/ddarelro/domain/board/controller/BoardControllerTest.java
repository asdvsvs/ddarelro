package com.b3.ddarelro.domain.board.controller;


import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.b3.ddarelro.domain.board.service.BoardService;
import com.b3.ddarelro.global.config.MockSpringSecurityFilter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(
    controllers = {BoardController.class},
    excludeFilters = {
        @ComponentScan.Filter(
            type = FilterType.ASSIGNABLE_TYPE
            //classes = WebSecurityConfig.class //
        )
    }
)

public class BoardControllerTest {
    @Autowired
    private WebApplicationContext context;
    @Autowired
    ObjectMapper mapper;
    @Autowired
    MockMvc mvc;
    @MockBean
    BoardService boardService;
    @BeforeEach
    public void mockSetup() {

        mvc = MockMvcBuilders.webAppContextSetup(context)
            .apply(springSecurity(new MockSpringSecurityFilter()))
            .build();

    }
    @Test
    @DisplayName("보드 리스트 조회")
    void 리스트조회테스트() throws Exception {
        // given

        // when - then
        mvc.perform(get("/api/boards")
                .accept(MediaType.APPLICATION_JSON) //없어도된다.
                // 이 api가 json타입의 데이터를 허용해주겠다
            )
            .andExpect(status().isOk())
            .andDo(print());
    }


    @Test
    @DisplayName("보드 단건조회 실패 ")
    void 단건조회실패() throws Exception {
        // given

        // when - then
        mvc.perform(get("/api/boards/1")
                    .accept(MediaType.APPLICATION_JSON) //없어도된다.
                // 이 api가 json타입의 데이터를 허용해주겠다
            )
            .andExpect(status().isNotFound())
            .andDo(print());
    }



}
