package org.example.usersystem.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.usersystem.anotations.password.Password;
import org.example.usersystem.constants.Messages;

import java.time.LocalDateTime;
import java.util.Set;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotNull
    @Size(min = 4, max = 30, message = Messages.USERNAME_INCORRECT_LENGTH)
    private String userName;

    @Column(nullable = false)
    @Password(minLength = 6,
            maxLength = 50,
            containsLowerCase = true,
            containsUpperCase = true,
            containsDigit = true,
            containsSpecialSymbol = true)
    private String password;

    @Column(nullable = false, unique = true)
    @Email
    private String email;

    @Lob
    @Size(max = 1024 * 1024)
    private byte[] profilePicture;

    @Column(name = "registered_on")
    private LocalDateTime registeredOn;

    @Column(name = "last_time_logged_in")
    private LocalDateTime lastTimeLoggedIn;

    @Min(1)
    @Max(120)
    private Integer age;

    @Column(name = "is_deleted")
    private Boolean isDeleted;

    @ManyToOne
    private Town bornTown;

    @ManyToOne
    private Town currentTown;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @ManyToMany
    @JoinTable(
            name = "user_friends",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "friend_id"))
    private Set<User> friends;


    @OneToMany
    private Set<Album> album;

    public String fullName() {
        return this.firstName + " " + this.lastName;
    }

}
