<#assign licenseFirst = "/*">
<#assign licensePrefix = " * ">
<#assign licenseLast = " */">
<#include "../Licenses/license-${project.license}.txt">

<#if package?? && package != "">
package ${package};

</#if>
import com.google.gwt.junit.client.GWTTestCase;

/**
 *
 * @author ${user}
 */
public class ${name} extends GWTTestCase {
    @Override
    public String getModuleName() {
        throw new UnsupportedOperationException("TODO: Not implemented.");
    }
}
