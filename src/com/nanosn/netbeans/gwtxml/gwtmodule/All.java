/**
 *	This generated bean class All matches the schema element 'all'.
 *  The root bean class is Module
 *
 *	Generated on Mon Sep 14 05:41:06 ADT 2009
 * @Generated
 */

package com.nanosn.netbeans.gwtxml.gwtmodule;

import org.w3c.dom.*;
import org.netbeans.modules.schema2beans.*;
import java.beans.*;
import java.util.*;

// BEGIN_NOI18N

public class All extends org.netbeans.modules.schema2beans.BaseBean
	 implements com.nanosn.netbeans.gwtxml.gwtmodule.CommonBean
{

	static Vector comparators = new Vector();
	private static final org.netbeans.modules.schema2beans.Version runtimeVersion = new org.netbeans.modules.schema2beans.Version(5, 0, 0);

	static public final String WHEN_PROPERTY_IS = "WhenPropertyIs";	// NOI18N
	static public final String WHENPROPERTYISNAME = "WhenPropertyIsName";	// NOI18N
	static public final String WHENPROPERTYISVALUE = "WhenPropertyIsValue";	// NOI18N
	static public final String WHEN_TYPE_ASSIGNABLE = "WhenTypeAssignable";	// NOI18N
	static public final String WHENTYPEASSIGNABLECLASS = "WhenTypeAssignableClass";	// NOI18N
	static public final String WHEN_TYPE_IS = "WhenTypeIs";	// NOI18N
	static public final String WHENTYPEISCLASS = "WhenTypeIsClass";	// NOI18N
	static public final String ALL = "All";	// NOI18N
	static public final String ANY = "Any";	// NOI18N
	static public final String NONE = "None";	// NOI18N

	public All() {
		this(Common.USE_DEFAULT_VALUES);
	}

	public All(int options)
	{
		super(comparators, runtimeVersion);
		// Properties (see root bean comments for the bean graph)
		initPropertyTables(6);
		this.createProperty("when-property-is", 	// NOI18N
			WHEN_PROPERTY_IS, Common.SEQUENCE_OR | 
			Common.TYPE_0_N | Common.TYPE_BOOLEAN | Common.TYPE_KEY, 
			Boolean.class);
		this.createAttribute(WHEN_PROPERTY_IS, "name", "Name", 
						AttrProp.CDATA | AttrProp.REQUIRED,
						null, null);
		this.createAttribute(WHEN_PROPERTY_IS, "value", "Value", 
						AttrProp.CDATA | AttrProp.REQUIRED,
						null, null);
		this.createProperty("when-type-assignable", 	// NOI18N
			WHEN_TYPE_ASSIGNABLE, Common.SEQUENCE_OR | 
			Common.TYPE_0_N | Common.TYPE_BOOLEAN | Common.TYPE_KEY, 
			Boolean.class);
		this.createAttribute(WHEN_TYPE_ASSIGNABLE, "class", "Class", 
						AttrProp.CDATA | AttrProp.REQUIRED,
						null, null);
		this.createProperty("when-type-is", 	// NOI18N
			WHEN_TYPE_IS, Common.SEQUENCE_OR | 
			Common.TYPE_0_N | Common.TYPE_BOOLEAN | Common.TYPE_KEY, 
			Boolean.class);
		this.createAttribute(WHEN_TYPE_IS, "class", "Class", 
						AttrProp.CDATA | AttrProp.REQUIRED,
						null, null);
		this.createProperty("all", 	// NOI18N
			ALL, Common.SEQUENCE_OR | 
			Common.TYPE_0_N | Common.TYPE_BEAN | Common.TYPE_KEY, 
			All.class);
		this.createProperty("any", 	// NOI18N
			ANY, Common.SEQUENCE_OR | 
			Common.TYPE_0_N | Common.TYPE_BEAN | Common.TYPE_KEY, 
			Any.class);
		this.createProperty("none", 	// NOI18N
			NONE, Common.SEQUENCE_OR | 
			Common.TYPE_0_N | Common.TYPE_BEAN | Common.TYPE_KEY, 
			None.class);
		this.initialize(options);
	}

	// Setting the default values of the properties
	void initialize(int options) {

	}

	// This attribute is an array, possibly empty
	public void setWhenPropertyIs(int index, boolean value) {
		this.setValue(WHEN_PROPERTY_IS, index, (value ? java.lang.Boolean.TRUE : java.lang.Boolean.FALSE));
	}

	//
	public boolean isWhenPropertyIs(int index) {
		Boolean ret = (Boolean)this.getValue(WHEN_PROPERTY_IS, index);
		if (ret == null)
			ret = (Boolean)Common.defaultScalarValue(Common.TYPE_BOOLEAN);
		return ((java.lang.Boolean)ret).booleanValue();
	}

	// Return the number of properties
	public int sizeWhenPropertyIs() {
		return this.size(WHEN_PROPERTY_IS);
	}

	// This attribute is an array, possibly empty
	public void setWhenPropertyIs(boolean[] value) {
		Boolean[] values = null;
		if (value != null)
		{
			values = new Boolean[value.length];
			for (int i=0; i<value.length; i++)
				values[i] = (value[i] ? Boolean.TRUE : Boolean.FALSE);
		}
		this.setValue(WHEN_PROPERTY_IS, values);
	}

	//
	public boolean[] getWhenPropertyIs() {
		boolean[] ret = null;
		Boolean[] values = (Boolean[])this.getValues(WHEN_PROPERTY_IS);
		if (values != null)
		{
			ret = new boolean[values.length];
			for (int i=0; i<values.length; i++)
				ret[i] = values[i].booleanValue();
		}
		return ret;
	}

	// Add a new element returning its index in the list
	public int addWhenPropertyIs(boolean value) {
		int positionOfNewItem = this.addValue(WHEN_PROPERTY_IS, (value ? java.lang.Boolean.TRUE : java.lang.Boolean.FALSE));
		return positionOfNewItem;
	}

	//
	// Remove an element using its reference
	// Returns the index the element had in the list
	//
	public int removeWhenPropertyIs(boolean value) {
		return this.removeValue(WHEN_PROPERTY_IS, (value ? java.lang.Boolean.TRUE : java.lang.Boolean.FALSE));
	}

	//
	// Remove an element using its index
	//
	public void removeWhenPropertyIs(int index) {
		this.removeValue(WHEN_PROPERTY_IS, index);
	}

	// This attribute is an array, possibly empty
	public void setWhenPropertyIsName(int index, java.lang.String value) {
		// Make sure we've got a place to put this attribute.
		if (size(WHEN_PROPERTY_IS) == 0) {
			addValue(WHEN_PROPERTY_IS, java.lang.Boolean.TRUE);
		}
		setValue(WHEN_PROPERTY_IS, index, java.lang.Boolean.TRUE);
		setAttributeValue(WHEN_PROPERTY_IS, index, "Name", value);
	}

	//
	public java.lang.String getWhenPropertyIsName(int index) {
		// If our element does not exist, then the attribute does not exist.
		if (size(WHEN_PROPERTY_IS) == 0) {
			return null;
		} else {
			return getAttributeValue(WHEN_PROPERTY_IS, index, "Name");
		}
	}

	// Return the number of properties
	public int sizeWhenPropertyIsName() {
		return this.size(WHEN_PROPERTY_IS);
	}

	// This attribute is an array, possibly empty
	public void setWhenPropertyIsValue(int index, java.lang.String value) {
		// Make sure we've got a place to put this attribute.
		if (size(WHEN_PROPERTY_IS) == 0) {
			addValue(WHEN_PROPERTY_IS, java.lang.Boolean.TRUE);
		}
		setValue(WHEN_PROPERTY_IS, index, java.lang.Boolean.TRUE);
		setAttributeValue(WHEN_PROPERTY_IS, index, "Value", value);
	}

	//
	public java.lang.String getWhenPropertyIsValue(int index) {
		// If our element does not exist, then the attribute does not exist.
		if (size(WHEN_PROPERTY_IS) == 0) {
			return null;
		} else {
			return getAttributeValue(WHEN_PROPERTY_IS, index, "Value");
		}
	}

	// Return the number of properties
	public int sizeWhenPropertyIsValue() {
		return this.size(WHEN_PROPERTY_IS);
	}

	// This attribute is an array, possibly empty
	public void setWhenTypeAssignable(int index, boolean value) {
		this.setValue(WHEN_TYPE_ASSIGNABLE, index, (value ? java.lang.Boolean.TRUE : java.lang.Boolean.FALSE));
	}

	//
	public boolean isWhenTypeAssignable(int index) {
		Boolean ret = (Boolean)this.getValue(WHEN_TYPE_ASSIGNABLE, index);
		if (ret == null)
			ret = (Boolean)Common.defaultScalarValue(Common.TYPE_BOOLEAN);
		return ((java.lang.Boolean)ret).booleanValue();
	}

	// Return the number of properties
	public int sizeWhenTypeAssignable() {
		return this.size(WHEN_TYPE_ASSIGNABLE);
	}

	// This attribute is an array, possibly empty
	public void setWhenTypeAssignable(boolean[] value) {
		Boolean[] values = null;
		if (value != null)
		{
			values = new Boolean[value.length];
			for (int i=0; i<value.length; i++)
				values[i] = (value[i] ? Boolean.TRUE : Boolean.FALSE);
		}
		this.setValue(WHEN_TYPE_ASSIGNABLE, values);
	}

	//
	public boolean[] getWhenTypeAssignable() {
		boolean[] ret = null;
		Boolean[] values = (Boolean[])this.getValues(WHEN_TYPE_ASSIGNABLE);
		if (values != null)
		{
			ret = new boolean[values.length];
			for (int i=0; i<values.length; i++)
				ret[i] = values[i].booleanValue();
		}
		return ret;
	}

	// Add a new element returning its index in the list
	public int addWhenTypeAssignable(boolean value) {
		int positionOfNewItem = this.addValue(WHEN_TYPE_ASSIGNABLE, (value ? java.lang.Boolean.TRUE : java.lang.Boolean.FALSE));
		return positionOfNewItem;
	}

	//
	// Remove an element using its reference
	// Returns the index the element had in the list
	//
	public int removeWhenTypeAssignable(boolean value) {
		return this.removeValue(WHEN_TYPE_ASSIGNABLE, (value ? java.lang.Boolean.TRUE : java.lang.Boolean.FALSE));
	}

	//
	// Remove an element using its index
	//
	public void removeWhenTypeAssignable(int index) {
		this.removeValue(WHEN_TYPE_ASSIGNABLE, index);
	}

	// This attribute is an array, possibly empty
	public void setWhenTypeAssignableClass(int index, java.lang.String value) {
		// Make sure we've got a place to put this attribute.
		if (size(WHEN_TYPE_ASSIGNABLE) == 0) {
			addValue(WHEN_TYPE_ASSIGNABLE, java.lang.Boolean.TRUE);
		}
		setValue(WHEN_TYPE_ASSIGNABLE, index, java.lang.Boolean.TRUE);
		setAttributeValue(WHEN_TYPE_ASSIGNABLE, index, "Class", value);
	}

	//
	public java.lang.String getWhenTypeAssignableClass(int index) {
		// If our element does not exist, then the attribute does not exist.
		if (size(WHEN_TYPE_ASSIGNABLE) == 0) {
			return null;
		} else {
			return getAttributeValue(WHEN_TYPE_ASSIGNABLE, index, "Class");
		}
	}

	// Return the number of properties
	public int sizeWhenTypeAssignableClass() {
		return this.size(WHEN_TYPE_ASSIGNABLE);
	}

	// This attribute is an array, possibly empty
	public void setWhenTypeIs(int index, boolean value) {
		this.setValue(WHEN_TYPE_IS, index, (value ? java.lang.Boolean.TRUE : java.lang.Boolean.FALSE));
	}

	//
	public boolean isWhenTypeIs(int index) {
		Boolean ret = (Boolean)this.getValue(WHEN_TYPE_IS, index);
		if (ret == null)
			ret = (Boolean)Common.defaultScalarValue(Common.TYPE_BOOLEAN);
		return ((java.lang.Boolean)ret).booleanValue();
	}

	// Return the number of properties
	public int sizeWhenTypeIs() {
		return this.size(WHEN_TYPE_IS);
	}

	// This attribute is an array, possibly empty
	public void setWhenTypeIs(boolean[] value) {
		Boolean[] values = null;
		if (value != null)
		{
			values = new Boolean[value.length];
			for (int i=0; i<value.length; i++)
				values[i] = (value[i] ? Boolean.TRUE : Boolean.FALSE);
		}
		this.setValue(WHEN_TYPE_IS, values);
	}

	//
	public boolean[] getWhenTypeIs() {
		boolean[] ret = null;
		Boolean[] values = (Boolean[])this.getValues(WHEN_TYPE_IS);
		if (values != null)
		{
			ret = new boolean[values.length];
			for (int i=0; i<values.length; i++)
				ret[i] = values[i].booleanValue();
		}
		return ret;
	}

	// Add a new element returning its index in the list
	public int addWhenTypeIs(boolean value) {
		int positionOfNewItem = this.addValue(WHEN_TYPE_IS, (value ? java.lang.Boolean.TRUE : java.lang.Boolean.FALSE));
		return positionOfNewItem;
	}

	//
	// Remove an element using its reference
	// Returns the index the element had in the list
	//
	public int removeWhenTypeIs(boolean value) {
		return this.removeValue(WHEN_TYPE_IS, (value ? java.lang.Boolean.TRUE : java.lang.Boolean.FALSE));
	}

	//
	// Remove an element using its index
	//
	public void removeWhenTypeIs(int index) {
		this.removeValue(WHEN_TYPE_IS, index);
	}

	// This attribute is an array, possibly empty
	public void setWhenTypeIsClass(int index, java.lang.String value) {
		// Make sure we've got a place to put this attribute.
		if (size(WHEN_TYPE_IS) == 0) {
			addValue(WHEN_TYPE_IS, java.lang.Boolean.TRUE);
		}
		setValue(WHEN_TYPE_IS, index, java.lang.Boolean.TRUE);
		setAttributeValue(WHEN_TYPE_IS, index, "Class", value);
	}

	//
	public java.lang.String getWhenTypeIsClass(int index) {
		// If our element does not exist, then the attribute does not exist.
		if (size(WHEN_TYPE_IS) == 0) {
			return null;
		} else {
			return getAttributeValue(WHEN_TYPE_IS, index, "Class");
		}
	}

	// Return the number of properties
	public int sizeWhenTypeIsClass() {
		return this.size(WHEN_TYPE_IS);
	}

	// This attribute is an array, possibly empty
	public void setAll(int index, All value) {
		this.setValue(ALL, index, value);
	}

	//
	public All getAll(int index) {
		return (All)this.getValue(ALL, index);
	}

	// Return the number of properties
	public int sizeAll() {
		return this.size(ALL);
	}

	// This attribute is an array, possibly empty
	public void setAll(All[] value) {
		this.setValue(ALL, value);
	}

	//
	public All[] getAll() {
		return (All[])this.getValues(ALL);
	}

	// Add a new element returning its index in the list
	public int addAll(com.nanosn.netbeans.gwtxml.gwtmodule.All value) {
		int positionOfNewItem = this.addValue(ALL, value);
		return positionOfNewItem;
	}

	//
	// Remove an element using its reference
	// Returns the index the element had in the list
	//
	public int removeAll(com.nanosn.netbeans.gwtxml.gwtmodule.All value) {
		return this.removeValue(ALL, value);
	}

	// This attribute is an array, possibly empty
	public void setAny(int index, Any value) {
		this.setValue(ANY, index, value);
	}

	//
	public Any getAny(int index) {
		return (Any)this.getValue(ANY, index);
	}

	// Return the number of properties
	public int sizeAny() {
		return this.size(ANY);
	}

	// This attribute is an array, possibly empty
	public void setAny(Any[] value) {
		this.setValue(ANY, value);
	}

	//
	public Any[] getAny() {
		return (Any[])this.getValues(ANY);
	}

	// Add a new element returning its index in the list
	public int addAny(com.nanosn.netbeans.gwtxml.gwtmodule.Any value) {
		int positionOfNewItem = this.addValue(ANY, value);
		return positionOfNewItem;
	}

	//
	// Remove an element using its reference
	// Returns the index the element had in the list
	//
	public int removeAny(com.nanosn.netbeans.gwtxml.gwtmodule.Any value) {
		return this.removeValue(ANY, value);
	}

	// This attribute is an array, possibly empty
	public void setNone(int index, None value) {
		this.setValue(NONE, index, value);
	}

	//
	public None getNone(int index) {
		return (None)this.getValue(NONE, index);
	}

	// Return the number of properties
	public int sizeNone() {
		return this.size(NONE);
	}

	// This attribute is an array, possibly empty
	public void setNone(None[] value) {
		this.setValue(NONE, value);
	}

	//
	public None[] getNone() {
		return (None[])this.getValues(NONE);
	}

	// Add a new element returning its index in the list
	public int addNone(com.nanosn.netbeans.gwtxml.gwtmodule.None value) {
		int positionOfNewItem = this.addValue(NONE, value);
		return positionOfNewItem;
	}

	//
	// Remove an element using its reference
	// Returns the index the element had in the list
	//
	public int removeNone(com.nanosn.netbeans.gwtxml.gwtmodule.None value) {
		return this.removeValue(NONE, value);
	}

	/**
	 * Create a new bean using it's default constructor.
	 * This does not add it to any bean graph.
	 */
	public All newAll() {
		return new All();
	}

	/**
	 * Create a new bean using it's default constructor.
	 * This does not add it to any bean graph.
	 */
	public Any newAny() {
		return new Any();
	}

	/**
	 * Create a new bean using it's default constructor.
	 * This does not add it to any bean graph.
	 */
	public None newNone() {
		return new None();
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
		str.append("WhenPropertyIs["+this.sizeWhenPropertyIs()+"]");	// NOI18N
		for(int i=0; i<this.sizeWhenPropertyIs(); i++)
		{
			str.append(indent+"\t");
			str.append("#"+i+":");
			str.append(indent+"\t");	// NOI18N
			str.append((this.isWhenPropertyIs(i)?"true":"false"));
			this.dumpAttributes(WHEN_PROPERTY_IS, i, str, indent);
		}

		str.append(indent);
		str.append("WhenTypeAssignable["+this.sizeWhenTypeAssignable()+"]");	// NOI18N
		for(int i=0; i<this.sizeWhenTypeAssignable(); i++)
		{
			str.append(indent+"\t");
			str.append("#"+i+":");
			str.append(indent+"\t");	// NOI18N
			str.append((this.isWhenTypeAssignable(i)?"true":"false"));
			this.dumpAttributes(WHEN_TYPE_ASSIGNABLE, i, str, indent);
		}

		str.append(indent);
		str.append("WhenTypeIs["+this.sizeWhenTypeIs()+"]");	// NOI18N
		for(int i=0; i<this.sizeWhenTypeIs(); i++)
		{
			str.append(indent+"\t");
			str.append("#"+i+":");
			str.append(indent+"\t");	// NOI18N
			str.append((this.isWhenTypeIs(i)?"true":"false"));
			this.dumpAttributes(WHEN_TYPE_IS, i, str, indent);
		}

		str.append(indent);
		str.append("All["+this.sizeAll()+"]");	// NOI18N
		for(int i=0; i<this.sizeAll(); i++)
		{
			str.append(indent+"\t");
			str.append("#"+i+":");
			n = (org.netbeans.modules.schema2beans.BaseBean) this.getAll(i);
			if (n != null)
				n.dump(str, indent + "\t");	// NOI18N
			else
				str.append(indent+"\tnull");	// NOI18N
			this.dumpAttributes(ALL, i, str, indent);
		}

		str.append(indent);
		str.append("Any["+this.sizeAny()+"]");	// NOI18N
		for(int i=0; i<this.sizeAny(); i++)
		{
			str.append(indent+"\t");
			str.append("#"+i+":");
			n = (org.netbeans.modules.schema2beans.BaseBean) this.getAny(i);
			if (n != null)
				n.dump(str, indent + "\t");	// NOI18N
			else
				str.append(indent+"\tnull");	// NOI18N
			this.dumpAttributes(ANY, i, str, indent);
		}

		str.append(indent);
		str.append("None["+this.sizeNone()+"]");	// NOI18N
		for(int i=0; i<this.sizeNone(); i++)
		{
			str.append(indent+"\t");
			str.append("#"+i+":");
			n = (org.netbeans.modules.schema2beans.BaseBean) this.getNone(i);
			if (n != null)
				n.dump(str, indent + "\t");	// NOI18N
			else
				str.append(indent+"\tnull");	// NOI18N
			this.dumpAttributes(NONE, i, str, indent);
		}

	}
	public String dumpBeanNode(){
		StringBuffer str = new StringBuffer();
		str.append("All\n");	// NOI18N
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
