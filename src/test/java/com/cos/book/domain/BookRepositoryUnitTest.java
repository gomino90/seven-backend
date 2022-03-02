package com.cos.book.domain;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.jupiter.api.Assertions.assertEquals;


@Transactional
@AutoConfigureTestDatabase(replace = Replace.NONE)
@DataJpaTest
public class BookRepositoryUnitTest {

    @Autowired
    private BookRepository bookRepository;

    @Test
    public void save1_test(){
     Book book = new Book(null,"title1","author1");
     Book bookEntity = bookRepository.save(book);
     assertEquals("title1",bookEntity.getTitle());
    }
}
