<#assign licenseFirst = "/*">
<#assign licensePrefix = " * ">
<#assign licenseLast = " */">
<#include "../Licenses/license-${project.license}.txt">
<#if package?? && package != "">
package ${package};
</#if>

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

/**
 *
 * @author ${user}
 */
public class ${name} extends Composite {

    private static ${name}UiBinder uiBinder = GWT.create(${name}UiBinder.class);

    interface ${name}UiBinder extends UiBinder<Widget, ${name}> {
    }

    public ${name}() {
        initWidget(uiBinder.createAndBindUi(this));
    }
}