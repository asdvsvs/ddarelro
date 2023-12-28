package com.b3.ddarelro.domain.board.controller;


import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.b3.ddarelro.domain.board.dto.request.BoardCreateReq;
import com.b3.ddarelro.domain.board.entity.Color;
import com.b3.ddarelro.domain.board.service.BoardService;
import com.b3.ddarelro.domain.user.entity.User;
import com.b3.ddarelro.global.config.MockSpringSecurityFilter;
import com.b3.ddarelro.global.security.UserDetailsImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.security.Principal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
    private Principal mockPrincipal;
    private User testUser;
    private UserDetailsImpl testUserDetails;

    @BeforeEach
    public void mockSetup() {

        // Mock 테스트 유져 생성
        String username = "hwang";
        String password = "123456789";
        String email = "123asdad@naver.com";
        testUser = User.builder()
            .username(username)
            .password(password)
            .email(email)
            .build();
        testUserDetails = new UserDetailsImpl(testUser);
        mockPrincipal = new UsernamePasswordAuthenticationToken(testUserDetails, "",
            testUserDetails.getAuthorities());

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
    @DisplayName("보드 생성테스트")
    void 보드생성테스트() throws Exception {
        // given

        BoardCreateReq req = BoardCreateReq.builder()
            .description("보드설명")
            .color(Color.BLUE)
            .name("보드이름")
            .build();

        String body = mapper.writeValueAsString(
            req
        );

        // when - then
        mvc.perform(post("/api/boards")
                .content(body)
                .contentType(MediaType.APPLICATION_JSON)

                .principal(mockPrincipal)
            )
            .andExpect(status().isCreated())
            .andDo(print());
    }


}
