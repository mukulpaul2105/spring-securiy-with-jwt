package in.mpapp.springsecurityjwtdemo.services;

import in.mpapp.springsecurityjwtdemo.dtos.UserDTO;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    UserDTO createUser(final UserDTO userDTO);
    Optional<UserDTO> getByUsername(final String username);
    List<UserDTO> getAll();
}
