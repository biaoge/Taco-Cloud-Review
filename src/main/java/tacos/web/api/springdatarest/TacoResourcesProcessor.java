package tacos.web.api.springdatarest;


import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Component
public class TacoResourcesProcessor implements RepresentationModelProcessor<CollectionModel<?>> {

    private final RepositoryRestConfiguration restConfiguration;

    public TacoResourcesProcessor(RepositoryRestConfiguration restConfiguration) {
        this.restConfiguration = restConfiguration;
    }

    @Override
    public CollectionModel<?> process(CollectionModel<?> model) {
        // Build the absolute URI for the recent tacos endpoint while respecting the configured base path
        String href = ServletUriComponentsBuilder.fromCurrentContextPath()
            .path(restConfiguration.getBasePath().toString())
            .path("/tacos/recent")
            .toUriString();

        // Add the {"recents": "/{basePath}/tacos/recent"} link to the collection model so that clients know this endpoint exists and how to access it
        Link recentLink = Link.of(href, "recents");

        model.add(recentLink);
        return model;
    }
}
