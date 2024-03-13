package entities;

import enums.Prediction;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "result_predictions")
@Getter
@Setter
@NoArgsConstructor
public class ResultPrediction extends BaseEntity{

    @Column
    @Enumerated(EnumType.STRING)
    private Prediction prediction;
}
