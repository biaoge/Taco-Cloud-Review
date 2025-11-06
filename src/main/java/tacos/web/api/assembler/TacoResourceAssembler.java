package tacos.web.api.assembler;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

import tacos.model.Taco;
import tacos.web.api.DesignTacoRestController;
import tacos.web.api.resources.TacoResource;

public class TacoResourceAssembler extends RepresentationModelAssemblerSupport<Taco, TacoResource> {

    public TacoResourceAssembler() {
        super(DesignTacoRestController.class, TacoResource.class);
    }

    @Override
    protected TacoResource instantiateModel(Taco taco) {
        return new TacoResource(taco);
    }
    
    @Override
    public TacoResource toModel(Taco taco) {
        return createModelWithId(taco.getId(), taco);
    }

    @Override
    public CollectionModel<TacoResource> toCollectionModel(Iterable<? extends Taco> tacos) {
        List<TacoResource> tacoResources = StreamSupport
            .stream(tacos.spliterator(), false)
            .map(this::toModel)
            .collect(Collectors.toList());

        return CollectionModel.of(tacoResources);
    }
}
