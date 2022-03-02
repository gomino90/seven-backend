package com.cos.book.web;

import com.cos.book.domain.Book;
import com.cos.book.service.BookService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.http.server.reactive.MockServerHttpRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.hamcrest.Matchers;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.data.jpa.domain.AbstractPersistable_.id;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.mockito.Mockito.when;

@WebMvcTest
@Slf4j
public class BookControllerUnitTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

//    @Test
//    public void save_test() throws Exception {

//        Book book = new Book(null,"test1","minwoo");
//        String content = new ObjectMapper().writeValueAsString(book);

//          when(bookService.저장하기(book)).thenReturn(new Book(1L,"test","minwoo"));


//        ResultActions resultAction = mockMvc.perform(post("/book")
//                .contentType(MediaType.APPLICATION_JSON
//                .cntent(content)
//                .accept(MediaType.APPLICATION_JSON));

//        resultAction
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.title").value("test"))
//                .andDo(MockMvcResultHandlers.print());
//    }

    @Test
     public void findAll_test() throws Exception{
        //given
        List<Book>books = new ArrayList<>();
        books.add(new Book(1L,"test1","minwoo"));
        books.add(new Book(2L,"test2","minwoo"));
        when(bookService.모두가져오기()).thenReturn(books);

        ResultActions resultAction = mockMvc.perform(get("/bookall")
                .accept(MediaType.APPLICATION_JSON));

        resultAction
                .andExpect(status().isOk())
                .andExpect(jsonPath("$",Matchers.hasSize(2)))
                .andExpect(jsonPath("$.[0].title").value("test1"))
                .andDo(MockMvcResultHandlers.print());
     }
     @Test
    public void findById_test()throws Exception{

         Long id=1l;
         when(bookService.한건가져오기(id)).thenReturn(new Book(1L,"test1","minwoo"));

         ResultActions resultAction = mockMvc.perform(get("/book/{id}",id)
                 .accept(MediaType.APPLICATION_JSON));

         resultAction
                 .andExpect(status().isOk())
                 .andExpect(jsonPath("$.title").value("test1"))
                 .andDo(MockMvcResultHandlers.print());
    }

    @Test
    public void update_test()throws Exception{
        Long id=1l;
        Book book = new Book(null,"test3","minwoo");
        String content = new ObjectMapper().writeValueAsString(book);
        when(bookService.수정하기(id,book)).thenReturn(new Book(1L,"test3","minwoo"));

        ResultActions resultAction = mockMvc.perform(put("/book/up/{id}",id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .accept(MediaType.APPLICATION_JSON));

         resultAction
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("test3"))
                .andDo(MockMvcResultHandlers.print());

    }
    @Test
    public void delete_test()throws Exception{
        Long id=1l;
        when(bookService.삭제하기(id)).thenReturn("ok");

        ResultActions resultAction = mockMvc.perform(delete("/book/{id}",id)
                .accept(MediaType.TEXT_PLAIN));

        resultAction
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());

        MvcResult requestResult = resultAction.andReturn();
        String result = requestResult.getResponse().getContentAsString();

        assertEquals("ok",result);
    }


}