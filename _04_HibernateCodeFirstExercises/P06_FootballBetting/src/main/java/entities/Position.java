package entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "positions")
@Getter
@Setter
@NoArgsConstructor
public class Position{

    @Id
    @Column(length = 2)
    private String id;

    @Column
    private String positionDescription;
}
