package de.fb.demo;

import io.dropwizard.Application;
import io.dropwizard.assets.AssetsBundle;
import io.dropwizard.servlets.tasks.Task;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;
import io.dropwizard.views.ViewBundle;

import java.io.PrintWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMultimap;

import de.fb.demo.entity.WIZARD_HAT;
import de.fb.demo.entity.Wizard;
import de.fb.demo.health.WizBookHealth;
import de.fb.demo.resource.WizardResource;
import de.fb.demo.store.WizardStore;

/**
 * Main entry point of the application. In the initialize()-method new bundles are registered. In
 * the run()-method the application is wired together.
 *
 */
public class WizBookApplication extends Application<WizBookConfig> {

	private final Logger logger = LoggerFactory.getLogger(WizBookApplication.class);

	public static void main(String[] args) throws Exception {
		new WizBookApplication().run(args);
	}

	@Override
	public void initialize(Bootstrap<WizBookConfig> bootstrap) {

		bootstrap.addBundle(new AssetsBundle("/static-content", "/static", "index.html"));

		bootstrap.addBundle(new ViewBundle<WizBookConfig>() {
			@Override
			public ImmutableMap<String, ImmutableMap<String, String>> getViewConfiguration(WizBookConfig config) {
				return config.getViewRendererConfiguration();
			}
		});
	}

	@Override
	public void run(WizBookConfig conf, Environment environment) throws Exception {
		logger.info(conf.getGreeting() + ", starting WizBook...");

		WizardStore store = new WizardStore();

		environment.jersey().register(new WizardResource(store));

		// Alternative registration of resources by package scan
		// (only possible with no-arg-constructors in all resources)
		// environment.jersey().packages("de.fb.demo.resource");

		environment.lifecycle().manage(store);

		environment.healthChecks().register("WBHealth", new WizBookHealth());

		environment.admin().addTask(new HelloTask("Hello"));

		// Create two entries
		store.store(new Wizard("StaticWizard", WIZARD_HAT.POINTY_HAT, "0800245"));
		store.store(new Wizard("Gandalf", WIZARD_HAT.POINTY_HAT, "0800555111"));
	}

	/**
	 * A simple task. It's added in the {@link WizBookApplication#run(String...)} method. You can
	 * execute it with a POST on /tasks/Hello.
	 * 
	 */
	private class HelloTask extends Task {

		protected HelloTask(String name) {
			super(name);
		}

		@Override
		public void execute(ImmutableMultimap<String, String> parameters, PrintWriter output) throws Exception {
			output.println("Hello Task!!");
		}

	}

}
