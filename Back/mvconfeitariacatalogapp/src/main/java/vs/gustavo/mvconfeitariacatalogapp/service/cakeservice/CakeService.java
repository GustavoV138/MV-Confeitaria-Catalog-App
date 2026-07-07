package vs.gustavo.mvconfeitariacatalogapp.service.cakeservice;

import org.springframework.stereotype.Service;
import vs.gustavo.mvconfeitariacatalogapp.entity.Cake;
import vs.gustavo.mvconfeitariacatalogapp.repository.CakeRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CakeService {

    private final CakeRepository cakeRepository;

    public CakeService(CakeRepository cakeRepository) {
        this.cakeRepository = cakeRepository;
    }

    public Cake createCake(Cake cake) {
        List<Optional<Cake>> cakeByTitle = cakeRepository.findCakeByTitle(cake.getTitle());

        List<Cake> cakeList = cakeByTitle.stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();

        cakeList.forEach(c -> {
            if (c.getTitle().equals(cake.getTitle()) && c.getSlices().equals(cake.getSlices())) {
                throw new RuntimeException("Este item já foi adicionado.");
            }
        });

        return cakeRepository.save(cake);
    }

    public List<Cake> findAll() {
        return cakeRepository.findAll();
    }

    public List<Cake> findByTitle(String title) {

        List<Cake> cakes = new ArrayList<>();

        cakeRepository.findCakeByTitle(title).forEach(c -> c.ifPresent(cakes::add));

        return cakes;
    }

    public void deleteCake(Long id) {
        Optional<Cake> cake = cakeRepository.findById(id);

        if (cake.isEmpty()) {
            throw new RuntimeException("Bolo não encontrado.");
        }

        cakeRepository.delete(cake.get());
    }

    public Cake updateCake(Long id, Cake cakeDetails) {
        Optional<Cake> existingCakeOpt = cakeRepository.findById(id);
        if (existingCakeOpt.isEmpty()) {
            throw new RuntimeException("Bolo não encontrado.");
        }

        Cake existingCake = existingCakeOpt.get();

        List<Optional<Cake>> cakeByTitle = cakeRepository.findCakeByTitle(cakeDetails.getTitle());
        cakeByTitle.stream()
                .filter(Optional::isPresent)
                .map(Optional::get)
                .forEach(c -> {
                    if (!c.getId().equals(id) && c.getTitle().equals(cakeDetails.getTitle()) && c.getSlices().equals(cakeDetails.getSlices())) {
                        throw new RuntimeException("Este item já foi adicionado.");
                    }
                });

        existingCake.setTitle(cakeDetails.getTitle());
        existingCake.setDescription(cakeDetails.getDescription());
        existingCake.setSlices(cakeDetails.getSlices());
        existingCake.setPrice(cakeDetails.getPrice());

        return cakeRepository.save(existingCake);
    }
}
