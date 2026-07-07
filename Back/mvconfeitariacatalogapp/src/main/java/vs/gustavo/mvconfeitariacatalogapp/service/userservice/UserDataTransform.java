//package vs.gustavo.mvconfeitariacatalogapp.service.userservice;
//
//import vs.gustavo.mvconfeitariacatalogapp.dtos.userdto.UserRequestDto;
//import vs.gustavo.mvconfeitariacatalogapp.dtos.userdto.UserResponseDto;
//import vs.gustavo.mvconfeitariacatalogapp.entity.User;
//
//public class UserDataTransform {
//
//    public static UserResponseDto userToDto(User user) {
//
//        return new UserResponseDto(
//                user.getId(),
//                user.getName(),
//                user.getCatalog()
//        );
//    }
//
//    public static User dtoToUser(UserRequestDto dto) {
//
//        return User.Builder.builder()
//                .name(dto.name())
//                .login(dto.login())
//                .password(dto.password())
//                .build();
//    }
//}
