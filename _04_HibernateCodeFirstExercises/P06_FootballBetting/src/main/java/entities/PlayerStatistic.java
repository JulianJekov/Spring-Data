package entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "player_statistics")
@Getter
@Setter
@NoArgsConstructor
public class PlayerStatistic implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn
    private Game game;

    @Id
    @ManyToOne
    @JoinColumn
    private Player player;

    @Column
    private Short scoredGoals;

    @Column
    private Short playerAssists;

    @Column
    private Short playedMinutes;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PlayerStatistic that = (PlayerStatistic) o;
        return Objects.equals(game, that.game) &&
                Objects.equals(player, that.player) &&
                Objects.equals(scoredGoals, that.scoredGoals) &&
                Objects.equals(playerAssists, that.playerAssists) &&
                Objects.equals(playedMinutes, that.playedMinutes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(game, player, scoredGoals, playerAssists, playedMinutes);
    }
}
