package softuni.exam.models.dto.LibraryMemberDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class LibraryMemberIdDto {

    @XmlElement(name = "id")
    private Long id;

    public LibraryMemberIdDto() {
    }

    public Long getId() {
        return id;
    }
}
