package de.fb.demo.store;

import io.dropwizard.lifecycle.Managed;

import java.util.HashMap;

import com.google.common.base.Optional;
import com.google.common.collect.Maps;

import de.fb.demo.entity.Wizard;

/**
 * A dummy store which is backed by a simple HashMap. Used as data store in the WizBook application
 * example.
 */
public class WizardStore implements Managed {

	final HashMap<String, Wizard> internalStore = Maps.newHashMap();
	private boolean running = true;

	public void store(final Wizard wizard) {
		internalStore.put(wizard.getName(), wizard);
	}

	public Optional<Wizard> get(final String name) {
		return Optional.fromNullable(internalStore.get(name));
	}

	@Override
	public void start() throws Exception {
		running = true;
	}

	@Override
	public void stop() throws Exception {
		running = false;
	}

	public boolean isRunning() {
		return running;
	}

}
