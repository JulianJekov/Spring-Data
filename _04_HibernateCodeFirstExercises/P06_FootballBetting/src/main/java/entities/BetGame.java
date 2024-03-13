package entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table
@Getter
@Setter
@NoArgsConstructor
public class BetGame implements Serializable {

    @Id
    @OneToOne
    private Game game;

    @Id
    @OneToOne
    private Bet bet;

    @OneToOne
    @JoinColumn(name = "result_prediction")
    private ResultPrediction resultPrediction;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BetGame betGame = (BetGame) o;
        return Objects.equals(game, betGame.game) && Objects.equals(bet, betGame.bet) && Objects.equals(resultPrediction, betGame.resultPrediction);
    }

    @Override
    public int hashCode() {
        return Objects.hash(game, bet, resultPrediction);
    }
}
