package com.b3.ddarelro.domain.column.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.b3.ddarelro.domain.column.dto.request.ColumnCreateReq;
import com.b3.ddarelro.domain.column.dto.request.ColumnDeleteReq;
import com.b3.ddarelro.domain.column.dto.request.ColumnGetReq;
import com.b3.ddarelro.domain.column.dto.request.ColumnMoveReq;
import com.b3.ddarelro.domain.column.dto.request.ColumnRestoreReq;
import com.b3.ddarelro.domain.column.dto.request.ColumnUpdateReq;
import com.b3.ddarelro.domain.column.dto.response.ColumnCreateRes;
import com.b3.ddarelro.domain.column.dto.response.ColumnDeleteRes;
import com.b3.ddarelro.domain.column.dto.response.ColumnMoveRes;
import com.b3.ddarelro.domain.column.dto.response.ColumnRestoreRes;
import com.b3.ddarelro.domain.column.dto.response.ColumnUpdateRes;
import com.b3.ddarelro.domain.column.dto.response.ColumnsGetRes;
import com.b3.ddarelro.domain.column.service.ColumnService;
import com.b3.ddarelro.domain.user.entity.User;
import com.b3.ddarelro.global.security.UserDetailsImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@WebMvcTest(controllers = {ColumnController.class})
class ColumnControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private WebApplicationContext context;
    @MockBean
    private ColumnService columnService;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();

        User testUser = User.builder()
            .email("test123@gmail.com")
            .username("testUser")
            .password("12345678")
            .build();
        UserDetailsImpl testUserDetails = new UserDetailsImpl(testUser);
        SecurityContextHolder.getContext()
            .setAuthentication(new UsernamePasswordAuthenticationToken(testUserDetails, null,
                testUserDetails.getAuthorities()));
    }

    @Test
    @DisplayName("컬럼 생성 테스트")
    void test1() throws Exception {
        //given
        ColumnCreateReq req = ColumnCreateReq.builder()
            .boardId(1L).title("testTitle").build();
        ColumnCreateRes res = ColumnCreateRes.builder()
            .boardId(1L).columnId(1L).title("testTitle").build();

        //when
        when(columnService.createColumn(any(), any())).thenReturn(res);
        ResultActions actions = mockMvc.perform(post("/api/columns")
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8")
            .content(objectMapper.writeValueAsString(req)));

        //then
        actions
            .andExpect(status().isCreated())
            .andDo(print());
    }

    @Test
    @DisplayName("컬럼 전체 조회 테스트")
    void test2() throws Exception {
        //given
        ColumnGetReq req = ColumnGetReq.builder()
            .boardId(1L).build();
        ColumnsGetRes res = ColumnsGetRes.builder()
            .columnId(1L).title("testTitle").priority(1L).build();
        List<ColumnsGetRes> list = List.of(res);

        //when
        when(columnService.getColumns(any(), any())).thenReturn(list);
        ResultActions actions = mockMvc.perform(get("/api/columns")
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8")
            .content(objectMapper.writeValueAsString(req)));

        //then
        actions
            .andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    @DisplayName("컬럼 수정 테스트")
    void test3() throws Exception {
        //given
        ColumnUpdateReq req = ColumnUpdateReq.builder()
            .boardId(1L).title("testTitle").build();
        ColumnUpdateRes res = ColumnUpdateRes.builder()
            .title("testTitle").build();

        //when
        when(columnService.updateColumn(any(), any(), any())).thenReturn(res);
        ResultActions actions = mockMvc.perform(put("/api/columns/" + 1L)
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8")
            .content(objectMapper.writeValueAsString(req)));

        //then
        actions
            .andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    @DisplayName("컬럼 삭제 테스트")
    void test4() throws Exception {
        //given
        ColumnDeleteReq req = ColumnDeleteReq.builder()
            .boardId(1L).build();
        ColumnDeleteRes res = ColumnDeleteRes.builder()
            .title("testTitle").deleted(true).build();

        //when
        when(columnService.deleteColumn(any(), any(), any())).thenReturn(res);
        ResultActions actions = mockMvc.perform(delete("/api/columns/" + 1L)
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8")
            .content(objectMapper.writeValueAsString(req)));

        //then
        actions
            .andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    @DisplayName("컬럼 복구 테스트")
    void test5() throws Exception {
        //given
        ColumnRestoreReq req = ColumnRestoreReq.builder()
            .boardId(1L).build();
        ColumnRestoreRes res = ColumnRestoreRes.builder()
            .title("testTitle").deleted(false).build();

        //when
        when(columnService.restoreColumn(any(), any(), any())).thenReturn(res);
        ResultActions actions = mockMvc.perform(patch("/api/columns/" + 1L + "/restore")
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8")
            .content(objectMapper.writeValueAsString(req)));

        //then
        actions
            .andExpect(status().isOk())
            .andDo(print());
    }

    @Test
    @DisplayName("컬럼 이동 테스트")
    void test6() throws Exception {
        //given
        ColumnMoveReq req = ColumnMoveReq.builder()
            .boardId(1L).move(2L).build();
        ColumnMoveRes res = ColumnMoveRes.builder()
            .ColumnId(1L).title("testTitle").priority(2L).build();

        //when
        when(columnService.moveColumn(any(), any(), any())).thenReturn(res);
        ResultActions actions = mockMvc.perform(put("/api/columns/" + 1L + "/move")
            .contentType(MediaType.APPLICATION_JSON)
            .characterEncoding("utf-8")
            .content(objectMapper.writeValueAsString(req)));

        //then
        actions
            .andExpect(status().isOk())
            .andDo(print());
    }
}