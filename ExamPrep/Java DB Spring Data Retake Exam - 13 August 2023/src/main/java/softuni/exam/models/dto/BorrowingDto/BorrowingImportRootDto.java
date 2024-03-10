package softuni.exam.models.dto.BorrowingDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "borrowing_records")
@XmlAccessorType(XmlAccessType.FIELD)
public class BorrowingImportRootDto {

    @XmlElement(name = "borrowing_record")
    private List<BorrowingImportDto> borrowingRecords;

    public List<BorrowingImportDto> getBorrowingRecords() {
        return borrowingRecords;
    }
}
