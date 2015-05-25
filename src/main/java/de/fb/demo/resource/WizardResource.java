package de.fb.demo.resource;

import java.net.URI;
import java.net.URISyntaxException;

import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.codahale.metrics.annotation.Timed;
import com.google.common.base.Optional;
import com.google.common.net.UrlEscapers;

import de.fb.demo.entity.WIZARD_HAT;
import de.fb.demo.entity.Wizard;
import de.fb.demo.store.WizardStore;
import de.fb.demo.view.WizardView;

/**
 * WizardResource defines the RESTful methods which are offered by this dropwizard application.
 * JAX-RS is used to define methods, paths and media types.
 */
@Path("wizards")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class WizardResource {

	private final WizardStore store;

	Logger log = LoggerFactory.getLogger(WizardResource.class);

	public WizardResource(WizardStore store) {
		this.store = store;
	}

	@Path("ping")
	@Timed
	@GET
	public Wizard getPongWizard() {
		log.debug("ping was called...");
		return new Wizard("PongWizard", WIZARD_HAT.POINTY_HAT, "0800 3512");
	}

	@Path("{name}")
	@GET
	public Optional<Wizard> getWizard(@PathParam("name") String name) {
		return store.get(name);
	}

	@POST
	public Response addWizard(@Valid final Wizard wizard) throws URISyntaxException {
		store.store(wizard);
		return Response.created(new URI("/" + escape(wizard.getName()))).build();
	}

	private String escape(final String wizardName) {
		return UrlEscapers.urlPathSegmentEscaper().escape(wizardName);
	}

	/**
	 * This method delivers either an fully fledged html page when it's called with a web browser
	 * (Accept: text/html) or it delivers the raw {@link Hipster} json representation when accessed
	 * with header "Accept: application/json".
	 */
	@GET
	@Path("{name}/view")
	@Produces({ MediaType.TEXT_HTML, MediaType.APPLICATION_JSON })
	public WizardView getWizardView(@PathParam("name") String name) {
		return new WizardView(getWizard(name).get());
	}

}
