package tacos.web.api;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.EntityLinks;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import tacos.data.jpa.TacoRepositoryJPA;
import tacos.model.Taco;
import tacos.web.api.assembler.TacoResourceAssembler;
import tacos.web.api.resources.TacoResource;

@RestController
@RequestMapping(path="/api/design", produces="application/json")
@CrossOrigin(origins="*")
public class DesignTacoRestController {

    private TacoRepositoryJPA tacoRepo;

    @Autowired
    private EntityLinks entityLinks;

    public DesignTacoRestController(TacoRepositoryJPA tacoRepo) {
        this.tacoRepo = tacoRepo;
    }

    // @GetMapping("/recent")
    // public Iterable<Taco> recentTacos() {
    //     PageRequest page = PageRequest.of(0, 12, Sort.by("createdAt").descending());
    //     return tacoRepo.findAll(page).getContent();
    // }

    // With HATEOAS support
    @GetMapping("/recent")
    public CollectionModel<TacoResource> recentTacos() {
        PageRequest page = PageRequest.of(0, 12, Sort.by("createdAt").descending());
    
        List<Taco> tacos = tacoRepo.findAll(page).getContent();

        List<TacoResource> tacoResources = new ArrayList<>(new TacoResourceAssembler().toCollectionModel(tacos).getContent());
        CollectionModel<TacoResource> recentTacosResources = CollectionModel.of(tacoResources);
        recentTacosResources.add(linkTo(methodOn(DesignTacoRestController.class).recentTacos()).withRel("recents"));

        return recentTacosResources;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Taco> tacoById(@PathVariable("id") Long id) {
        Optional<Taco> optionalTaco = tacoRepo.findById(id);
        return optionalTaco.map(taco -> new ResponseEntity<>(taco, HttpStatus.OK))
            .orElse(new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }

    @PostMapping(consumes="application/json")
    public Taco postTaco(@RequestBody Taco taco) {
        return tacoRepo.save(taco);
    }

}
