package de.fb.demo.entity;

import java.util.UUID;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * A Wizard is a simple entity, which is used in this WizBook demo application.
 */
public class Wizard {

	private UUID id;
	@NotEmpty
	private String name;
	private WIZARD_HAT hat;
	@NotEmpty
	private String phoneNumber;

	public Wizard() {
	}

	public Wizard(String name, WIZARD_HAT hat, String phone) {
		this.id = UUID.randomUUID();
		this.name = name;
		this.hat = hat;
		this.phoneNumber = phone;
	}

	public String getName() {
		return name;
	}

	public WIZARD_HAT getHat() {
		return hat;
	}

	public UUID getId() {
		return id;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	@Override
	public String toString() {
		return "Wizard [id=" + id + ", name=" + name + ", hat=" + hat + ", phoneNumber=" + phoneNumber + "]";
	}

}
