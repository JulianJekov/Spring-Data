package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.BorrowingDto.BorrowingImportDto;
import softuni.exam.models.dto.BorrowingDto.BorrowingImportRootDto;
import softuni.exam.models.entity.Book;
import softuni.exam.models.entity.BorrowingRecord;
import softuni.exam.models.entity.Genre;
import softuni.exam.models.entity.LibraryMember;
import softuni.exam.repository.BookRepository;
import softuni.exam.repository.BorrowingRecordRepository;
import softuni.exam.repository.LibraryMemberRepository;
import softuni.exam.service.BorrowingRecordsService;
import softuni.exam.util.ValidatorUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static softuni.exam.constatns.Messages.IMPORTED_BORROWING_RECORD_MESSAGE;
import static softuni.exam.constatns.Messages.INVALID_BORROWING_RECORD_MESSAGE;
import static softuni.exam.constatns.Paths.XML_BORROWING_RECORDS_IMPORT_PATH;

@Service
public class BorrowingRecordsServiceImpl implements BorrowingRecordsService {

    private final BorrowingRecordRepository borrowingRecordRepository;
    private final BookRepository bookRepository;
    private final LibraryMemberRepository libraryMemberRepository;
    private final XmlParser xmlParser;
    private final ModelMapper modelMapper;
    private final ValidatorUtil validatorUtil;

    @Autowired
    public BorrowingRecordsServiceImpl(BorrowingRecordRepository borrowingRecordRepository, BookRepository bookRepository, LibraryMemberRepository libraryMemberRepository, XmlParser xmlParser, ModelMapper modelMapper, ValidatorUtil validatorUtil) {
        this.borrowingRecordRepository = borrowingRecordRepository;
        this.bookRepository = bookRepository;
        this.libraryMemberRepository = libraryMemberRepository;
        this.xmlParser = xmlParser;
        this.modelMapper = modelMapper;
        this.validatorUtil = validatorUtil;
    }

    @Override
    public boolean areImported() {
        return this.borrowingRecordRepository.count() > 0;
    }

    @Override
    public String readBorrowingRecordsFromFile() throws IOException {
        return Files.readString(XML_BORROWING_RECORDS_IMPORT_PATH);
    }

    @Override
    public String importBorrowingRecords() throws IOException, JAXBException {
        final String xml = XML_BORROWING_RECORDS_IMPORT_PATH.toAbsolutePath().toString();
        final BorrowingImportRootDto borrowingImportRootDto = xmlParser.convertFromFile(xml, BorrowingImportRootDto.class);

        return borrowingImportRootDto.getBorrowingRecords()
                .stream()
                .map(this::importBorrowingRecord)
                .collect(Collectors.joining("\n"));
    }

    private String importBorrowingRecord(BorrowingImportDto dto) {
        final boolean isValid = this.validatorUtil.isValid(dto);

        if (!isValid) {
            return INVALID_BORROWING_RECORD_MESSAGE;
        }

        final Optional<Book> optionalBook = this.bookRepository.findByTitle(dto.getBook().getTitle());
        final Optional<LibraryMember> optionalLibraryMember = this.libraryMemberRepository.findById(dto.getMember().getId());

        if (optionalBook.isEmpty()) {
            return INVALID_BORROWING_RECORD_MESSAGE;
        }
        if (optionalLibraryMember.isEmpty()) {
            return INVALID_BORROWING_RECORD_MESSAGE;
        }

        final Book book = optionalBook.get();
        final LibraryMember libraryMember = optionalLibraryMember.get();

        final BorrowingRecord borrowingRecord = this.modelMapper.map(dto, BorrowingRecord.class);

        borrowingRecord.setBook(book);
        borrowingRecord.setMember(libraryMember);

        this.borrowingRecordRepository.saveAndFlush(borrowingRecord);


        return IMPORTED_BORROWING_RECORD_MESSAGE + book.getTitle() + " - " + borrowingRecord.getBorrowDate();
    }

    @Override
    public String exportBorrowingRecords() {
        final LocalDate before = LocalDate.of(2021, 9, 10);
        final Genre genre = Genre.SCIENCE_FICTION;
        return
                this.borrowingRecordRepository.findAllByBorrowDateBeforeAndBookGenreIsOrderByBorrowDateDesc(before, genre)
                        .stream()
                        .map(BorrowingRecord::toString)
                        .collect(Collectors.joining("\n"));

    }
}
