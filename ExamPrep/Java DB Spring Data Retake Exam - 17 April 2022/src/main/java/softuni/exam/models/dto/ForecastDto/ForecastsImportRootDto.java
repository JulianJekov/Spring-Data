package softuni.exam.models.dto.ForecastDto;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "forecasts")
@XmlAccessorType(XmlAccessType.FIELD)
public class ForecastsImportRootDto {

    @XmlElement(name = "forecast")
    private List<ForecastImportDto> forecasts;

    public List<ForecastImportDto> getForecasts() {
        return forecasts;
    }
}
