package softuni.exam.models.dto.VolcanologistsDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;
import java.util.Set;

@XmlRootElement(name = "volcanologists")
@XmlAccessorType(XmlAccessType.FIELD)
public class VolcanologistsRootDto {

    @XmlElement(name = "volcanologist")
    private List<VolcanologistImportDto> volcanologists;

    public VolcanologistsRootDto() {
    }

    public List<VolcanologistImportDto> getVolcanologists() {
        return volcanologists;
    }

    public void setVolcanologists(List<VolcanologistImportDto> volcanologists) {
        this.volcanologists = volcanologists;
    }
}
