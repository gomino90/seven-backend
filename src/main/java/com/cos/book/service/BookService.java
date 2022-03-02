package com.cos.book.service;
import com.cos.book.domain.Book;
import com.cos.book.domain.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.function.Supplier;


@RequiredArgsConstructor
@Service
public class BookService {

    private final BookRepository bookRepository;

    @Transactional
    public Book 저장하기(Book book){
        return bookRepository.save(book);

    }
    @Transactional(readOnly = true)
    public Book 한건가져오기(Long id){
        return bookRepository.findById(id)
                .orElseThrow(new Supplier<IllegalArgumentException>() {
            @Override
            public IllegalArgumentException get() {
                return new IllegalArgumentException("id를 확인해주세요");
            }
        });
    }

    @Transactional(readOnly = true)
    public List<Book> 모두가져오기(){
        return bookRepository.findAll();

    }
    @Transactional
    public Book 수정하기(Long id,Book book){
        
        Book bookEntitiy = bookRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("id를 확인해주세요!!"));
        bookEntitiy.setTitle(book.getTitle());
        bookEntitiy.setAuthor(book.getAuthor());
        return bookEntitiy;
        

    }
    @Transactional
    public String 삭제하기(Long id){
        bookRepository.deleteById(id);

        return "OK";
    }


}
