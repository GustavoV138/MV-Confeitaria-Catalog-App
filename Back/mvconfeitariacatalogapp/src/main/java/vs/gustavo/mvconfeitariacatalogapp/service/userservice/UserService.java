//package vs.gustavo.mvconfeitariacatalogapp.service.userservice;
//
//import org.springframework.stereotype.Service;
//import vs.gustavo.mvconfeitariacatalogapp.dtos.userdto.UserRequestDto;
//import vs.gustavo.mvconfeitariacatalogapp.dtos.userdto.UserResponseDto;
//import vs.gustavo.mvconfeitariacatalogapp.entity.User;
//import vs.gustavo.mvconfeitariacatalogapp.repository.UserRepository;
//
//import java.util.Optional;
//
//@Service
//public class UserService {
//
//    private final UserRepository userRepository;
//
//    public UserService(UserRepository userRepository) {
//        this.userRepository = userRepository;
//    }
//
//    public UserResponseDto createUser(UserRequestDto dto) {
//
//        Optional<User> userByLogin = userRepository.findUserByLogin(dto.login());
//        if(userByLogin.isPresent()){
//            throw new RuntimeException("Usuário já cadastrado.");
//        }
//
//        User user = UserDataTransform.dtoToUser(dto);
//        User userSaved = userRepository.save(user);
//
//        return UserDataTransform.userToDto(userSaved);
//    }
//
//    public UserResponseDto findById(Long id) {
//        User user = userRepository.findById(id).orElseThrow(RuntimeException::new);
//
//        return UserDataTransform.userToDto(user);
//    }
//
//    public void deleteUser(Long id) {
//        User user = userRepository.findById(id).orElseThrow(RuntimeException::new);
//        userRepository.delete(user);
//    }
//}
