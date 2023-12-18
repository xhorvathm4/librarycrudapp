package com.mhexercise.librarycrud;

import com.mhexercise.librarycrud.controller.BookController;
import com.mhexercise.librarycrud.entity.Book;
import com.mhexercise.librarycrud.service.BookService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@WebMvcTest(BookController.class)
public class BookControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private BookService service;

    @Test
    public void givenBooks_whenFindAll_thenReturnJson()
            throws Exception {

        Book book1 = new Book("Prva Kniha", "Prvy Autor", null);
        Book book2 = new Book("Druha Kniha", "Druhy Autor", null);


        List<Book> allBooks = Arrays.asList(book1, book2);

        given(service.findAll()).willReturn(allBooks);

        mvc.perform(get("/books").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].name", is(book1.getName())))
                .andExpect(jsonPath("$[0].author", is(book1.getAuthor())))
                .andExpect(jsonPath("$[1].name", is(book2.getName())))
                .andExpect(jsonPath("$[1].author", is(book2.getAuthor())));
    }
}
