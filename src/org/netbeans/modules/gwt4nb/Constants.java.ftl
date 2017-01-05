<#assign licenseFirst = "/*">
<#assign licensePrefix = " * ">
<#assign licenseLast = " */">
<#include "../Licenses/license-${project.license}.txt">

<#if package?? && package != "">
package ${package};

</#if>
/**
 * Use the following code to "instantiate" this interface:
 * {@code ${name} i18n = GWT.create(${name}.class);}
 * See this URL for more examples:
 * http://code.google.com/intl/en-EN/webtoolkit/doc/latest/DevGuideI18nConstants.html
 *
 * @author ${user}
 */
public interface ${name} extends com.google.gwt.i18n.client.Constants {
    String greetings();
}
