package org.example.usersystem.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Table(name = "albums")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Album {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column(name = "background_color")
    private String backgroundColor;

    @Column
    private Boolean isPublic;

    @ManyToOne(optional = false)
    private User users;

    @ManyToMany(mappedBy = "albums")
    private Set<Picture> picture;


}
