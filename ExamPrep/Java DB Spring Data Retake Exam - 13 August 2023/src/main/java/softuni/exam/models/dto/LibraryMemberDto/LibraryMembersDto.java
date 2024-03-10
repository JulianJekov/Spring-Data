package softuni.exam.models.dto.LibraryMemberDto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class LibraryMembersDto {

    @Size(min = 2, max = 40)
    private String address;

    @Size(min = 2, max = 30)
    @NotNull
    private String firstName;

    @Size(min = 2, max = 30)
    private String lastName;

    @Size(min = 2, max = 20)
    private String phoneNumber;

    public LibraryMembersDto() {
    }

    public String getAddress() {
        return address;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
