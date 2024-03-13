package entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "bets")
@Getter
@Setter
@NoArgsConstructor
public class Bet extends BaseEntity{

    @Column
    private BigDecimal betMoney;

    @Column
    private LocalDateTime dateTime;

    @ManyToOne
    private User user;
}
