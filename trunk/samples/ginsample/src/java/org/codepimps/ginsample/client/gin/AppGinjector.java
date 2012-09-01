package org.codepimps.ginsample.client.gin;

import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;
import com.google.gwt.user.client.ui.ButtonBase;

@GinModules({AppClientModule.class})
public interface AppGinjector extends Ginjector {
	public ButtonBase getButtonBase();
}
