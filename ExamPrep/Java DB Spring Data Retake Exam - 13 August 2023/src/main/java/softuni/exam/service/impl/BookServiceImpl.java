package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.BookDto.BooksImportDto;
import softuni.exam.models.entity.Book;
import softuni.exam.repository.BookRepository;
import softuni.exam.service.BookService;
import softuni.exam.util.ValidatorUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import static softuni.exam.constatns.Messages.IMPORTED_BOOK_MESSAGE;
import static softuni.exam.constatns.Messages.INVALID_BOOK_MESSAGE;
import static softuni.exam.constatns.Paths.JSON_BOOKS_IMPORT_PATH;

@Service
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final Gson gson;
    private final ValidatorUtil validator;
    private final ModelMapper modelMapper;

    @Autowired
    public BookServiceImpl(BookRepository bookRepository, Gson gson, ValidatorUtil validatorUtil, ModelMapper modelMapper) {
        this.bookRepository = bookRepository;
        this.gson = gson;
        this.validator = validatorUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.bookRepository.count() > 0;
    }

    @Override
    public String readBooksFromFile() throws IOException {
        return Files.readString(JSON_BOOKS_IMPORT_PATH);
    }

    @Override
    public String importBooks() throws IOException {
        final String json = this.readBooksFromFile();
        final BooksImportDto[] booksImportDto = this.gson.fromJson(json, BooksImportDto[].class);

        return Arrays.stream(booksImportDto)
                .map(this::importBook)
                .collect(Collectors.joining("\n"));
    }

    private String importBook(BooksImportDto dto) {
        final boolean isValid = this.validator.isValid(dto);

        if (!isValid) {
            return INVALID_BOOK_MESSAGE;
        }

        final Optional<Book> optionalBook = this.bookRepository.findByTitle(dto.getTitle());

        if (optionalBook.isPresent()) {
            return INVALID_BOOK_MESSAGE;
        }

        final Book book = modelMapper.map(dto, Book.class);

        this.bookRepository.saveAndFlush(book);
        return IMPORTED_BOOK_MESSAGE + book;
    }
}
