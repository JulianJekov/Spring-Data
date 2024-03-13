package entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "continents")
@Getter
@Setter
@NoArgsConstructor
public class Continent extends BaseEntity{

    @Column(nullable = false)
    private String name;

}
