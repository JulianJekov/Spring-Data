package softuni.exam.models.dto.BookDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class BookTitleDto {

    @XmlElement(name = "title")
    private String title;

    public BookTitleDto() {
    }

    public String getTitle() {
        return title;
    }
}
