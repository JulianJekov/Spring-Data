package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.LibraryMemberDto.LibraryMembersDto;
import softuni.exam.models.entity.Book;
import softuni.exam.models.entity.LibraryMember;
import softuni.exam.repository.LibraryMemberRepository;
import softuni.exam.service.LibraryMemberService;
import softuni.exam.util.ValidatorUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import static softuni.exam.constatns.Messages.*;
import static softuni.exam.constatns.Paths.JSON_LIBRARY_MEMBERS_IMPORT_PATH;

@Service
public class LibraryMemberServiceImpl implements LibraryMemberService {

    private final LibraryMemberRepository libraryMemberRepository;
    private final Gson gson;
    private final ValidatorUtil validator;
    private final ModelMapper modelMapper;


    @Autowired
    public LibraryMemberServiceImpl(LibraryMemberRepository libraryMemberRepository, Gson gson, ValidatorUtil validatorUtil, ModelMapper modelMapper) {
        this.libraryMemberRepository = libraryMemberRepository;
        this.gson = gson;
        this.validator = validatorUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.libraryMemberRepository.count() > 0;
    }

    @Override
    public String readLibraryMembersFileContent() throws IOException {
        return Files.readString(JSON_LIBRARY_MEMBERS_IMPORT_PATH);
    }

    @Override
    public String importLibraryMembers() throws IOException {
        final String json = this.readLibraryMembersFileContent();
        final LibraryMembersDto[] libraryMembersDto = this.gson.fromJson(json, LibraryMembersDto[].class);

        return Arrays.stream(libraryMembersDto)
                .map(this::importLibraryMember)
                .collect(Collectors.joining("\n"));
    }

    private String importLibraryMember(LibraryMembersDto dto) {
        final boolean isValid = this.validator.isValid(dto);

        if (!isValid) {
            return INVALID_LIBRARY_MEMBER_MESSAGE;
        }

        final Optional<LibraryMember> optionalLibraryMember = this.libraryMemberRepository.findByPhoneNumber(dto.getPhoneNumber());

        if (optionalLibraryMember.isPresent()) {
            return INVALID_LIBRARY_MEMBER_MESSAGE;
        }

        final LibraryMember libraryMember = modelMapper.map(dto, LibraryMember.class);

        this.libraryMemberRepository.saveAndFlush(libraryMember);
        return IMPORTED_LIBRARY_MEMBER_MESSAGE + libraryMember;
    }
}
