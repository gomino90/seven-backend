package com.cos.book.web;
import com.cos.book.domain.Book;
import com.cos.book.domain.BookRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.mock.http.server.reactive.MockServerHttpRequest.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Transactional
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)

public class BookControllerIntegreTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private EntityManager entityManager;

    @BeforeEach
    public void init(){
    entityManager.createNativeQuery("ALTER TABLE book AUTO_INCREMENT=1").executeUpdate();
    }
    @Autowired
    private BookRepository bookRepository;

//    @Test
//    public void save_test() throws Exception {
//
//        Book book = new Book(null,"test1","minwoo");
//        String content = new ObjectMapper().writeValueAsString(book);
//
//
//        ResultActions resultAction = mockMvc.perform(post("/book")
//                .contentType(MediaType.APPLICATION_JSON
//                        .content(content)
//                        .accept(MediaType.APPLICATION_JSON));
//        //then(검증)
//        resultAction
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("$.title").value("test"))
//                .andDo(MockMvcResultHandlers.print());
//    }

@Test
public void findAll_test() throws Exception{

    List<Book> books = new ArrayList<>();
    books.add(new Book(null,"test1","minwoo"));
    books.add(new Book(null,"test2","minwoo"));
    books.add(new Book(null,"test3","minwoo"));
    bookRepository.saveAll(books);

    ResultActions resultAction = mockMvc.perform(get("/bookall")
            .accept(MediaType.APPLICATION_JSON));


    resultAction
            .andExpect(status().isOk())
            .andExpect(jsonPath("$", Matchers.hasSize(3)))
            .andExpect(jsonPath("$.[2].title").value("test3"))
            .andDo(MockMvcResultHandlers.print());
}


    @Test
    public void findById_test() throws Exception {

        Long id = 2L;

        List<Book> books = new ArrayList<>();
        books.add(new Book(null, "test1", "minwoo"));
        books.add(new Book(null, "test2", "minwoo"));
        books.add(new Book(null, "test3", "minwoo"));
        bookRepository.saveAll(books);

        ResultActions resultAction = mockMvc.perform(get("/book/{id}", id)
                .accept(MediaType.APPLICATION_JSON));
        resultAction
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("test2"))
                .andDo(MockMvcResultHandlers.print());
    }


    @Test
    public void update_test()throws Exception{
        Long id=2L;
        List<Book> books = new ArrayList<>();
        books.add(new Book(null, "test1", "minwoo"));
        books.add(new Book(null, "test2", "minwoo"));
        books.add(new Book(null, "test3", "minwoo"));

        bookRepository.saveAll(books);

        Book book = new Book(null,"test3","minwoo");
        String content = new ObjectMapper().writeValueAsString(book);

        ResultActions resultAction = mockMvc.perform(put("/book/up/{id}",id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content)
                .accept(MediaType.APPLICATION_JSON));

        resultAction
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(2L))
                .andExpect(jsonPath("$.title").value("test3"))
                .andDo(MockMvcResultHandlers.print());

    }
    @Test
    public void delete_test()throws Exception{
        Long id=2L;

        List<Book> books = new ArrayList<>();
        books.add(new Book(null, "test1", "minwoo"));
        books.add(new Book(null, "test2", "minwoo"));
        books.add(new Book(null, "test3", "minwoo"));
        bookRepository.saveAll(books);

        ResultActions resultAction = mockMvc.perform(delete("/book/{id}",id)
                .accept(MediaType.TEXT_PLAIN));


        resultAction
                .andExpect(status().isOk())
                .andDo(MockMvcResultHandlers.print());

        MvcResult requestResult = resultAction.andReturn();
        String result = requestResult.getResponse().getContentAsString();

        assertEquals("OK",result);
    }




}