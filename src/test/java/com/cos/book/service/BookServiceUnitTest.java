package com.cos.book.service;

import com.cos.book.domain.Book;
import com.cos.book.domain.BookRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;



@ExtendWith(MockitoExtension.class)
public class BookServiceUnitTest {
    @InjectMocks
    private BookService bookService;
    @Mock
    private BookRepository bookRepository;
    @Test
    public void save_test(){

        Book book = new Book();
        book.setTitle("title1");
        book.setAuthor("author1");

        when(bookRepository.save(book)).thenReturn(book);
        Book bookEntity = bookService.저장하기(book);
        assertEquals(bookEntity,book);
    }
}
