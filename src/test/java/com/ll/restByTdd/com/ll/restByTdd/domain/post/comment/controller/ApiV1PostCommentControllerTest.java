package com.ll.restByTdd.com.ll.restByTdd.domain.post.comment.controller;

import com.ll.restByTdd.domain.member.member.entity.Member;
import com.ll.restByTdd.domain.member.member.service.MemberService;
import com.ll.restByTdd.domain.post.comment.controller.ApiV1PostCommentController;
import com.ll.restByTdd.domain.post.comment.entity.PostComment;
import com.ll.restByTdd.domain.post.post.service.PostService;
import java.nio.charset.StandardCharsets;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
@Transactional
public class ApiV1PostCommentControllerTest {

    @Autowired
    private PostService postService;
    @Autowired
    private MemberService memberService;
    @Autowired
    private MockMvc mvc;

    @Test
    @DisplayName("다건 조회")
    void t1() throws Exception {
        ResultActions resultActions = mvc
            .perform(
                get("/api/v1/posts/1/comments")
            )
            .andDo(print());
        resultActions
            .andExpect(handler().handlerType(ApiV1PostCommentController.class))
            .andExpect(handler().methodName("items"))
            .andExpect(status().isOk());
        List<PostComment> comments = postService
            .findById(1).get().getComments();
        for (int i = 0; i < comments.size(); i++) {
            PostComment postComment = comments.get(i);
            resultActions
                .andExpect(jsonPath("$[%d].id".formatted(i)).value(postComment.getId()))
                .andExpect(jsonPath("$[%d].createDate".formatted(i)).value(
                    Matchers.startsWith(postComment.getCreateDate().toString().substring(0, 25))))
                .andExpect(jsonPath("$[%d].modifyDate".formatted(i)).value(
                    Matchers.startsWith(postComment.getModifyDate().toString().substring(0, 25))))
                .andExpect(
                    jsonPath("$[%d].authorId".formatted(i)).value(postComment.getAuthor().getId()))
                .andExpect(jsonPath("$[%d].authorName".formatted(i)).value(
                    postComment.getAuthor().getName()))
                .andExpect(jsonPath("$[%d].content".formatted(i)).value(postComment.getContent()));
        }
    }

    @Test
    @DisplayName("댓글 삭제")
    void t2() throws Exception {
        Member actor = memberService.findByUsername("user2").get();
        ResultActions resultActions = mvc
            .perform(
                delete("/api/v1/posts/1/comments/1")
                    .header("Authorization", "Bearer " + actor.getApiKey())
            )
            .andDo(print());
        resultActions
            .andExpect(handler().handlerType(ApiV1PostCommentController.class))
            .andExpect(handler().methodName("delete"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.resultCode").value("200-1"))
            .andExpect(jsonPath("$.msg").value("1번 댓글이 삭제되었습니다."));
    }

    @Test
    @DisplayName("댓글 수정")
    void t3() throws Exception {
        Member actor = memberService.findByUsername("user2").get();
        ResultActions resultActions = mvc
            .perform(
                put("/api/v1/posts/1/comments/1")
                    .header("Authorization", "Bearer " + actor.getApiKey())
                    .content("""
                        {
                            "content": "내용 new"
                        }
                        """)
                    .contentType(
                        new MediaType(MediaType.APPLICATION_JSON, StandardCharsets.UTF_8)
                    )
            )
            .andDo(print());
        resultActions
            .andExpect(handler().handlerType(ApiV1PostCommentController.class))
            .andExpect(handler().methodName("modify"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.resultCode").value("200-1"))
            .andExpect(jsonPath("$.msg").value("1번 댓글이 수정되었습니다."))
            .andExpect(jsonPath("$.data.id").value(1))
            .andExpect(jsonPath("$.data.createDate").exists())
            .andExpect(jsonPath("$.data.modifyDate").exists())
            .andExpect(jsonPath("$.data.authorId").value(actor.getId()))
            .andExpect(jsonPath("$.data.authorName").value(actor.getName()))
            .andExpect(jsonPath("$.data.content").value("내용 new"));
    }
}