package ru.skypro.kakavito.mappers;

import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.skypro.kakavito.dto.*;
import ru.skypro.kakavito.model.User;

import java.util.Optional;

@Data
@Component
public class UserMapper {

    private final ModelMapper modelMapper;

    @Value("${query.to.get.image}")
    private String imageQuery;


    public UpdateUserDTO convertToUpdateUserDTO(User user) {
        UpdateUserDTO updateUserDTO = modelMapper.map(user, UpdateUserDTO.class);

        return updateUserDTO;
    }

    public UserDto convertToUserDTO(User user) {
        UserDto userDto = modelMapper.map(user, UserDto.class);
        Optional.ofNullable(user.getImage())
                .ifPresent(elem -> userDto.setImage(imageQuery + user.getImage().getId()));
        return userDto;
    }

    public UserPrincipalDTO convertToUserPrincipalDTO(User user) {
        return modelMapper.map(user, UserPrincipalDTO.class);
    }

}
