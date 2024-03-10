package softuni.exam.models.dto.BorrowingDto;

import softuni.exam.models.dto.BookDto.BookTitleDto;
import softuni.exam.models.dto.LibraryMemberDto.LibraryMemberIdDto;
import softuni.exam.util.LocalDateAdapter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.time.LocalDate;

@XmlAccessorType(XmlAccessType.FIELD)
public class BorrowingImportDto {

    @XmlElement(name = "borrow_date")
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    @NotNull
    private LocalDate borrowDate;

    @XmlElement(name = "return_date")
    @XmlJavaTypeAdapter(LocalDateAdapter.class)
    @NotNull
    private LocalDate returnDate;

    @XmlElement(name = "book")
    @NotNull
    private BookTitleDto book;

    @XmlElement(name = "member")
    @NotNull
    private LibraryMemberIdDto member;

    @XmlElement(name = "remarks")
    @Size(min = 3, max = 100)
    private String remarks;

    public BorrowingImportDto() {
    }

    public LocalDate getBorrowDate() {
        return borrowDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public BookTitleDto getBook() {
        return book;
    }

    public LibraryMemberIdDto getMember() {
        return member;
    }

    public String getRemarks() {
        return remarks;
    }
}
