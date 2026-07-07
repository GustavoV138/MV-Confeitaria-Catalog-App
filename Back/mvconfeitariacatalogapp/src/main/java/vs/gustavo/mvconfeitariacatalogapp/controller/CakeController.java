package vs.gustavo.mvconfeitariacatalogapp.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vs.gustavo.mvconfeitariacatalogapp.entity.Cake;
import vs.gustavo.mvconfeitariacatalogapp.service.cakeservice.CakeService;

import java.util.List;

@RestController
@RequestMapping("api/v1/catalog")
public class CakeController {

    private final CakeService cakeService;

    public CakeController(CakeService cakeService) {
        this.cakeService = cakeService;
    }

    @PostMapping("/cake")
    public ResponseEntity<Cake> createCake(@RequestBody Cake cake) {
        Cake cakeCreated = cakeService.createCake(cake);

        return ResponseEntity.status(HttpStatus.CREATED).body(cakeCreated);
    }

    @GetMapping
    public ResponseEntity<List<Cake>> findAll() {
        List<Cake> allCakes = cakeService.findAll();

        return ResponseEntity.ok(allCakes);
    }

    @GetMapping("/cake/{title}")
    public ResponseEntity<List<Cake>> findByTitle(@PathVariable String title) {
        List<Cake> cakeFound = cakeService.findByTitle(title);

        return ResponseEntity.ok(cakeFound);
    }

    @DeleteMapping("/cake/{id}")
    public ResponseEntity<Void> deleteCake(@PathVariable Long id) {
        cakeService.deleteCake(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PutMapping("/cake/{id}")
    public ResponseEntity<Cake> updateCake(@PathVariable Long id, @RequestBody Cake cake) {
        Cake updatedCake = cakeService.updateCake(id, cake);
        return ResponseEntity.ok(updatedCake);
    }
}
