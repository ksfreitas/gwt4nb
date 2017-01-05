package org.codepimps.ginsample.client.gin;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.ButtonBase;

public final class AppClientModule extends AbstractGinModule {
	@Override
	protected void configure() {
		bind(ButtonBase.class).to(Button.class);
	}
}
