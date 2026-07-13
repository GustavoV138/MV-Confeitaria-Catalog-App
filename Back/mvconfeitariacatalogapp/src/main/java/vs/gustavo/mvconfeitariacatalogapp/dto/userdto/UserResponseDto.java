package vs.gustavo.mvconfeitariacatalogapp.dtos.userdto;

import vs.gustavo.mvconfeitariacatalogapp.entity.Cake;

import java.util.List;

public record UserResponseDto( Long id, String name, List<Cake> catalog) {
}
