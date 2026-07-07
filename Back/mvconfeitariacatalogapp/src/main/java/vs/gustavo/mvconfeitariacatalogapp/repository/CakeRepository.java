package vs.gustavo.mvconfeitariacatalogapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vs.gustavo.mvconfeitariacatalogapp.entity.Cake;

import java.util.List;
import java.util.Optional;

@Repository
public interface CakeRepository extends JpaRepository<Cake, Long> {

    List<Optional<Cake>> findCakeByTitle(String title);
}
