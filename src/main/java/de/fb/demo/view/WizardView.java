package de.fb.demo.view;

import io.dropwizard.views.View;
import de.fb.demo.entity.Wizard;
import de.fb.demo.resource.WizardResource;

/**
 * The WizardView class holds the instance of the entity and the name of the template which is used
 * to render the instance.
 * 
 * See {@link WizardResource#getWizardView(String)}.
 *
 */
public class WizardView extends View {

	private final Wizard wizard;

	public WizardView(Wizard wizard) {
		super("wizard.mustache");
		this.wizard = wizard;
	}

	public Wizard getWizard() {
		return wizard;
	}

}
