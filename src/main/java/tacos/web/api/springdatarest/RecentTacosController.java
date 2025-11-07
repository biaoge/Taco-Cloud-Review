package tacos.web.api.springdatarest;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;

import tacos.data.jpa.TacoRepositoryJPA;
import tacos.model.Taco;
import tacos.web.api.assembler.TacoResourceAssembler;
import tacos.web.api.resources.TacoResource;

@RepositoryRestController
public class RecentTacosController {

    private TacoRepositoryJPA tacoRepo;

    public RecentTacosController(TacoRepositoryJPA tacoRepo) {
        this.tacoRepo = tacoRepo;
    }

    // Spring Data REST’s content negotiation tries to serve HAL (application/hal+json), so we must specify it in the produces attribute.
    @GetMapping(path="/tacos/recent", produces="application/hal+json")
    public ResponseEntity<CollectionModel<TacoResource>> recentTacos() {
        PageRequest page = PageRequest.of(0, 12, Sort.by("createdAt").descending());
        List<Taco> tacos = tacoRepo.findAll(page).getContent();
        
        List<TacoResource> tacoResources = new ArrayList<>(new TacoResourceAssembler().toCollectionModel(tacos).getContent());
        CollectionModel<TacoResource> recentTacosResources = CollectionModel.of(tacoResources);
        recentTacosResources.add(
            linkTo(methodOn(RecentTacosController.class).recentTacos())
                .withRel("recents")
        );
        
        return ResponseEntity.ok(recentTacosResources);
    }
}
