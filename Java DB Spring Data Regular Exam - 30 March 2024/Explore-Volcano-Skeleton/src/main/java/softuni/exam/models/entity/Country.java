package softuni.exam.models.entity;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "countries")
public class Country {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @Column
    private String capital;

    @OneToMany(mappedBy = "country", targetEntity = Volcano.class)
    private Set<Volcano> volcanoes;

    public Country() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCapital() {
        return capital;
    }

    public void setCapital(String capital) {
        this.capital = capital;
    }

    public Set<Volcano> getVolcanoes() {
        return volcanoes;
    }

    public void setVolcanoes(Set<Volcano> volcanoes) {
        this.volcanoes = volcanoes;
    }
}
