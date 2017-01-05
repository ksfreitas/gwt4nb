/**
 *	This generated bean class Public matches the schema element 'public'.
 *  The root bean class is Module
 *
 *	Generated on Mon Sep 14 05:41:05 ADT 2009
 * @Generated
 */

package com.nanosn.netbeans.gwtxml.gwtmodule;

import org.w3c.dom.*;
import org.netbeans.modules.schema2beans.*;
import java.beans.*;
import java.util.*;

// BEGIN_NOI18N

public class Public extends org.netbeans.modules.schema2beans.BaseBean
	 implements com.nanosn.netbeans.gwtxml.gwtmodule.CommonBean
{

	static Vector comparators = new Vector();
	private static final org.netbeans.modules.schema2beans.Version runtimeVersion = new org.netbeans.modules.schema2beans.Version(5, 0, 0);

	static public final String PATH = "Path";	// NOI18N
	static public final String INCLUDES = "Includes";	// NOI18N
	static public final String EXCLUDES = "Excludes";	// NOI18N
	static public final String DEFAULTEXCLUDES = "Defaultexcludes";	// NOI18N
	static public final String CASESENSITIVE = "Casesensitive";	// NOI18N
	static public final String INCLUDE = "Include";	// NOI18N
	static public final String INCLUDENAME = "IncludeName";	// NOI18N
	static public final String EXCLUDE = "Exclude";	// NOI18N
	static public final String EXCLUDENAME = "ExcludeName";	// NOI18N

	public Public() {
		this(Common.USE_DEFAULT_VALUES);
	}

	public Public(int options)
	{
		super(comparators, runtimeVersion);
		// Properties (see root bean comments for the bean graph)
		initPropertyTables(2);
		this.createProperty("include", 	// NOI18N
			INCLUDE, Common.SEQUENCE_OR | 
			Common.TYPE_0_N | Common.TYPE_BOOLEAN | Common.TYPE_KEY, 
			Boolean.class);
		this.createAttribute(INCLUDE, "name", "Name", 
						AttrProp.CDATA | AttrProp.REQUIRED,
						null, null);
		this.createProperty("exclude", 	// NOI18N
			EXCLUDE, Common.SEQUENCE_OR | 
			Common.TYPE_0_N | Common.TYPE_BOOLEAN | Common.TYPE_KEY, 
			Boolean.class);
		this.createAttribute(EXCLUDE, "name", "Name", 
						AttrProp.CDATA | AttrProp.REQUIRED,
						null, null);
		this.initialize(options);
	}

	// Setting the default values of the properties
	void initialize(int options) {
		if ((options & Common.USE_DEFAULT_VALUES) == Common.USE_DEFAULT_VALUES) {
			setDefaultexcludes("yes");
			setCasesensitive("true");
		}

	}

	// This attribute is mandatory
	public void setPath(java.lang.String value) {
		setAttributeValue(PATH, value);
	}

	//
	public java.lang.String getPath() {
		return getAttributeValue(PATH);
	}

	// This attribute is optional
	public void setIncludes(java.lang.String value) {
		setAttributeValue(INCLUDES, value);
	}

	//
	public java.lang.String getIncludes() {
		return getAttributeValue(INCLUDES);
	}

	// This attribute is optional
	public void setExcludes(java.lang.String value) {
		setAttributeValue(EXCLUDES, value);
	}

	//
	public java.lang.String getExcludes() {
		return getAttributeValue(EXCLUDES);
	}

	// This attribute is mandatory
	public void setDefaultexcludes(java.lang.String value) {
		setAttributeValue(DEFAULTEXCLUDES, value);
	}

	//
	public java.lang.String getDefaultexcludes() {
		return getAttributeValue(DEFAULTEXCLUDES);
	}

	// This attribute is mandatory
	public void setCasesensitive(java.lang.String value) {
		setAttributeValue(CASESENSITIVE, value);
	}

	//
	public java.lang.String getCasesensitive() {
		return getAttributeValue(CASESENSITIVE);
	}

	// This attribute is an array, possibly empty
	public void setInclude(int index, boolean value) {
		this.setValue(INCLUDE, index, (value ? java.lang.Boolean.TRUE : java.lang.Boolean.FALSE));
	}

	//
	public boolean isInclude(int index) {
		Boolean ret = (Boolean)this.getValue(INCLUDE, index);
		if (ret == null)
			ret = (Boolean)Common.defaultScalarValue(Common.TYPE_BOOLEAN);
		return ((java.lang.Boolean)ret).booleanValue();
	}

	// Return the number of properties
	public int sizeInclude() {
		return this.size(INCLUDE);
	}

	// This attribute is an array, possibly empty
	public void setInclude(boolean[] value) {
		Boolean[] values = null;
		if (value != null)
		{
			values = new Boolean[value.length];
			for (int i=0; i<value.length; i++)
				values[i] = (value[i] ? Boolean.TRUE : Boolean.FALSE);
		}
		this.setValue(INCLUDE, values);
	}

	//
	public boolean[] getInclude() {
		boolean[] ret = null;
		Boolean[] values = (Boolean[])this.getValues(INCLUDE);
		if (values != null)
		{
			ret = new boolean[values.length];
			for (int i=0; i<values.length; i++)
				ret[i] = values[i].booleanValue();
		}
		return ret;
	}

	// Add a new element returning its index in the list
	public int addInclude(boolean value) {
		int positionOfNewItem = this.addValue(INCLUDE, (value ? java.lang.Boolean.TRUE : java.lang.Boolean.FALSE));
		return positionOfNewItem;
	}

	//
	// Remove an element using its reference
	// Returns the index the element had in the list
	//
	public int removeInclude(boolean value) {
		return this.removeValue(INCLUDE, (value ? java.lang.Boolean.TRUE : java.lang.Boolean.FALSE));
	}

	//
	// Remove an element using its index
	//
	public void removeInclude(int index) {
		this.removeValue(INCLUDE, index);
	}

	// This attribute is an array, possibly empty
	public void setIncludeName(int index, java.lang.String value) {
		// Make sure we've got a place to put this attribute.
		if (size(INCLUDE) == 0) {
			addValue(INCLUDE, java.lang.Boolean.TRUE);
		}
		setValue(INCLUDE, index, java.lang.Boolean.TRUE);
		setAttributeValue(INCLUDE, index, "Name", value);
	}

	//
	public java.lang.String getIncludeName(int index) {
		// If our element does not exist, then the attribute does not exist.
		if (size(INCLUDE) == 0) {
			return null;
		} else {
			return getAttributeValue(INCLUDE, index, "Name");
		}
	}

	// Return the number of properties
	public int sizeIncludeName() {
		return this.size(INCLUDE);
	}

	// This attribute is an array, possibly empty
	public void setExclude(int index, boolean value) {
		this.setValue(EXCLUDE, index, (value ? java.lang.Boolean.TRUE : java.lang.Boolean.FALSE));
	}

	//
	public boolean isExclude(int index) {
		Boolean ret = (Boolean)this.getValue(EXCLUDE, index);
		if (ret == null)
			ret = (Boolean)Common.defaultScalarValue(Common.TYPE_BOOLEAN);
		return ((java.lang.Boolean)ret).booleanValue();
	}

	// Return the number of properties
	public int sizeExclude() {
		return this.size(EXCLUDE);
	}

	// This attribute is an array, possibly empty
	public void setExclude(boolean[] value) {
		Boolean[] values = null;
		if (value != null)
		{
			values = new Boolean[value.length];
			for (int i=0; i<value.length; i++)
				values[i] = (value[i] ? Boolean.TRUE : Boolean.FALSE);
		}
		this.setValue(EXCLUDE, values);
	}

	//
	public boolean[] getExclude() {
		boolean[] ret = null;
		Boolean[] values = (Boolean[])this.getValues(EXCLUDE);
		if (values != null)
		{
			ret = new boolean[values.length];
			for (int i=0; i<values.length; i++)
				ret[i] = values[i].booleanValue();
		}
		return ret;
	}

	// Add a new element returning its index in the list
	public int addExclude(boolean value) {
		int positionOfNewItem = this.addValue(EXCLUDE, (value ? java.lang.Boolean.TRUE : java.lang.Boolean.FALSE));
		return positionOfNewItem;
	}

	//
	// Remove an element using its reference
	// Returns the index the element had in the list
	//
	public int removeExclude(boolean value) {
		return this.removeValue(EXCLUDE, (value ? java.lang.Boolean.TRUE : java.lang.Boolean.FALSE));
	}

	//
	// Remove an element using its index
	//
	public void removeExclude(int index) {
		this.removeValue(EXCLUDE, index);
	}

	// This attribute is an array, possibly empty
	public void setExcludeName(int index, java.lang.String value) {
		// Make sure we've got a place to put this attribute.
		if (size(EXCLUDE) == 0) {
			addValue(EXCLUDE, java.lang.Boolean.TRUE);
		}
		setValue(EXCLUDE, index, java.lang.Boolean.TRUE);
		setAttributeValue(EXCLUDE, index, "Name", value);
	}

	//
	public java.lang.String getExcludeName(int index) {
		// If our element does not exist, then the attribute does not exist.
		if (size(EXCLUDE) == 0) {
			return null;
		} else {
			return getAttributeValue(EXCLUDE, index, "Name");
		}
	}

	// Return the number of properties
	public int sizeExcludeName() {
		return this.size(EXCLUDE);
	}

	//
	public static void addComparator(org.netbeans.modules.schema2beans.BeanComparator c) {
		comparators.add(c);
	}

	//
	public static void removeComparator(org.netbeans.modules.schema2beans.BeanComparator c) {
		comparators.remove(c);
	}
	public void validate() throws org.netbeans.modules.schema2beans.ValidateException {
	}

	// Dump the content of this bean returning it as a String
	public void dump(StringBuffer str, String indent){
		String s;
		Object o;
		org.netbeans.modules.schema2beans.BaseBean n;
		str.append(indent);
		str.append("Include["+this.sizeInclude()+"]");	// NOI18N
		for(int i=0; i<this.sizeInclude(); i++)
		{
			str.append(indent+"\t");
			str.append("#"+i+":");
			str.append(indent+"\t");	// NOI18N
			str.append((this.isInclude(i)?"true":"false"));
			this.dumpAttributes(INCLUDE, i, str, indent);
		}

		str.append(indent);
		str.append("Exclude["+this.sizeExclude()+"]");	// NOI18N
		for(int i=0; i<this.sizeExclude(); i++)
		{
			str.append(indent+"\t");
			str.append("#"+i+":");
			str.append(indent+"\t");	// NOI18N
			str.append((this.isExclude(i)?"true":"false"));
			this.dumpAttributes(EXCLUDE, i, str, indent);
		}

	}
	public String dumpBeanNode(){
		StringBuffer str = new StringBuffer();
		str.append("Public\n");	// NOI18N
		this.dump(str, "\n  ");	// NOI18N
		return str.toString();
	}}

// END_NOI18N


/*
		The following schema file has been used for generation:

<?xml version="1.0" encoding="UTF-8"?>
<!--                                                                        -->
<!-- Copyright 2008 Google Inc.                                             -->
<!-- Licensed under the Apache License, Version 2.0 (the "License"); you    -->
<!-- may not use this file except in compliance with the License. You may   -->
<!-- may obtain a copy of the License at                                    -->
<!--                                                                        -->
<!-- http://www.apache.org/licenses/LICENSE-2.0                             -->
<!--                                                                        -->
<!-- Unless required by applicable law or agreed to in writing, software    -->
<!-- distributed under the License is distributed on an "AS IS" BASIS,      -->
<!-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or        -->
<!-- implied. License for the specific language governing permissions and   -->
<!-- limitations under the License.                                         -->

<!-- The module root element -->
<!ELEMENT module (inherits | source | public | super-source | entry-point | 
  stylesheet | script | servlet | replace-with | generate-with |
  define-property | extend-property | set-property | set-configuration-property |
  property-provider | define-linker | add-linker)*>
<!ATTLIST module
	rename-to CDATA #IMPLIED
>
<!-- Inherit the contents of another module -->
<!ELEMENT inherits EMPTY>
<!ATTLIST inherits
	name CDATA #REQUIRED
>
<!-- Specify the source path, relative to the classpath location of the module descriptor -->
<!ELEMENT source (include | exclude)*>
<!ATTLIST source
	path CDATA #REQUIRED
		includes CDATA #IMPLIED
		excludes CDATA #IMPLIED
		defaultexcludes (yes | no) "yes"
		casesensitive (true | false) "true"
>
<!-- Specify the public resource path, relative to the classpath location of the module descriptor -->
<!ELEMENT public (include | exclude)*>
<!ATTLIST public
	path CDATA #REQUIRED
		includes CDATA #IMPLIED
		excludes CDATA #IMPLIED
		defaultexcludes (yes | no) "yes"
		casesensitive (true | false) "true"
>
<!-- Specify a source path that rebases subpackages into the root namespace -->
<!ELEMENT super-source (include | exclude)*>
<!ATTLIST super-source
	path CDATA #REQUIRED
		includes CDATA #IMPLIED
		excludes CDATA #IMPLIED
		defaultexcludes (yes | no) "yes"
		casesensitive (true | false) "true"
>
<!ELEMENT include EMPTY>
<!ATTLIST include
	name CDATA #REQUIRED
>
<!ELEMENT exclude EMPTY>
<!ATTLIST exclude
	name CDATA #REQUIRED
>

<!-- Define a module entry point -->
<!ELEMENT entry-point EMPTY>
<!ATTLIST entry-point
	class CDATA #REQUIRED
>

<!-- Preload a stylesheet before executing the GWT application -->
<!ELEMENT stylesheet EMPTY>
<!ATTLIST stylesheet
	src CDATA #REQUIRED
>
<!-- Preload an external JavaScript file before executing the GWT application -->
<!ELEMENT script (#PCDATA)>
<!ATTLIST script
	src CDATA #REQUIRED
>
<!-- Map a named servlet class to a module-relative path in hosted mode -->
<!ELEMENT servlet EMPTY>
<!ATTLIST servlet 
	path CDATA #REQUIRED
	class CDATA #REQUIRED
>

<!-- Adds a Linker to the compilation process -->
<!ELEMENT add-linker EMPTY>
<!-- A comma-separated list of linker names -->
<!ATTLIST add-linker
	name CDATA #REQUIRED
>

<!-- Defines a Linker type to package compiler output -->
<!ELEMENT define-linker EMPTY>
<!ATTLIST define-linker
	class CDATA #REQUIRED
	name CDATA #REQUIRED
>

<!--                 ^^^ Commonly-used elements ^^^                -->
<!--                VVV Deferred binding elements VVV              -->

<!-- Define a property and allowable values (comma-separated identifiers) -->
<!ELEMENT define-property EMPTY>
<!ATTLIST define-property
	name CDATA #REQUIRED
	values CDATA #REQUIRED
>
<!-- Set the value of a previously-defined property -->
<!ELEMENT set-property EMPTY>
<!ATTLIST set-property
	name CDATA #REQUIRED
	value CDATA #REQUIRED
>
<!-- Set the value of a configuration property -->
<!ELEMENT set-configuration-property EMPTY>
<!ATTLIST set-configuration-property
	name CDATA #REQUIRED
	value CDATA #REQUIRED
>
<!-- Add additional allowable values to a property -->
<!ELEMENT extend-property EMPTY>
<!ATTLIST extend-property
	name CDATA #REQUIRED
	values CDATA #REQUIRED
>
<!-- Define a JavaScript fragment that will return the value for the named property at runtime -->
<!ELEMENT property-provider (#PCDATA)>
<!ATTLIST property-provider
	name CDATA #REQUIRED
>
<!-- All possible predicates -->
<!ENTITY % predicates "when-property-is | when-type-assignable | when-type-is | all | any | none">
<!-- Deferred binding assignment to substitute a named class -->
<!ELEMENT replace-with (%predicates;)*>
<!ATTLIST replace-with
	class CDATA #REQUIRED
>
<!-- Deferred binding assignment to substitute a generated class -->
<!ELEMENT generate-with (%predicates;)*>
<!ATTLIST generate-with
	class CDATA #REQUIRED
>
<!-- Deferred binding predicate that is true when a named property has a given value-->
<!ELEMENT when-property-is EMPTY>
<!ATTLIST when-property-is
	name CDATA #REQUIRED
	value CDATA #REQUIRED
>
<!-- Deferred binding predicate that is true for types in the type system that are assignable to the specified type -->
<!ELEMENT when-type-assignable EMPTY>
<!ATTLIST when-type-assignable
	class CDATA #REQUIRED
>
<!-- Deferred binding predicate that is true for exactly one type in the type system -->
<!ELEMENT when-type-is EMPTY>
<!ATTLIST when-type-is
	class CDATA #REQUIRED
>
<!-- Predicate that ANDs all child conditions -->
<!ELEMENT all (%predicates;)*>
<!-- Predicate that ORs all child conditions -->
<!ELEMENT any (%predicates;)*>
<!-- Predicate that NANDs all child conditions -->
<!ELEMENT none (%predicates;)*>

*/
