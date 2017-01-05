/**
 *	This generated bean class Module matches the schema element 'module'.
 *
 *	Generated on Mon Sep 14 05:41:05 ADT 2009
 *
 *	This class matches the root element of the DTD,
 *	and is the root of the following bean graph:
 *
 *	module <module> : Module
 *		[attr: rename-to CDATA #IMPLIED ]
 *		(
 *		  | inherits <inherits> : boolean
 *		  | 	[attr: name CDATA #REQUIRED ]
 *		  | 	EMPTY : String
 *		  | source <source> : Source
 *		  | 	[attr: path CDATA #REQUIRED ]
 *		  | 	[attr: includes CDATA #IMPLIED ]
 *		  | 	[attr: excludes CDATA #IMPLIED ]
 *		  | 	[attr: defaultexcludes ENUM ( yes no ) yes]
 *		  | 	[attr: casesensitive ENUM ( true false ) true]
 *		  | 	(
 *		  | 	  | include <include> : boolean
 *		  | 	  | 	[attr: name CDATA #REQUIRED ]
 *		  | 	  | 	EMPTY : String
 *		  | 	  | exclude <exclude> : boolean
 *		  | 	  | 	[attr: name CDATA #REQUIRED ]
 *		  | 	  | 	EMPTY : String
 *		  | 	)[0,n]
 *		  | public <public> : Public
 *		  | 	[attr: path CDATA #REQUIRED ]
 *		  | 	[attr: includes CDATA #IMPLIED ]
 *		  | 	[attr: excludes CDATA #IMPLIED ]
 *		  | 	[attr: defaultexcludes ENUM ( yes no ) yes]
 *		  | 	[attr: casesensitive ENUM ( true false ) true]
 *		  | 	(
 *		  | 	  | include <include> : boolean
 *		  | 	  | 	[attr: name CDATA #REQUIRED ]
 *		  | 	  | 	EMPTY : String
 *		  | 	  | exclude <exclude> : boolean
 *		  | 	  | 	[attr: name CDATA #REQUIRED ]
 *		  | 	  | 	EMPTY : String
 *		  | 	)[0,n]
 *		  | superSource <super-source> : SuperSource
 *		  | 	[attr: path CDATA #REQUIRED ]
 *		  | 	[attr: includes CDATA #IMPLIED ]
 *		  | 	[attr: excludes CDATA #IMPLIED ]
 *		  | 	[attr: defaultexcludes ENUM ( yes no ) yes]
 *		  | 	[attr: casesensitive ENUM ( true false ) true]
 *		  | 	(
 *		  | 	  | include <include> : boolean
 *		  | 	  | 	[attr: name CDATA #REQUIRED ]
 *		  | 	  | 	EMPTY : String
 *		  | 	  | exclude <exclude> : boolean
 *		  | 	  | 	[attr: name CDATA #REQUIRED ]
 *		  | 	  | 	EMPTY : String
 *		  | 	)[0,n]
 *		  | entryPoint <entry-point> : boolean
 *		  | 	[attr: class CDATA #REQUIRED ]
 *		  | 	EMPTY : String
 *		  | stylesheet <stylesheet> : boolean
 *		  | 	[attr: src CDATA #REQUIRED ]
 *		  | 	EMPTY : String
 *		  | script <script> : String
 *		  | 	[attr: src CDATA #REQUIRED ]
 *		  | servlet <servlet> : boolean
 *		  | 	[attr: path CDATA #REQUIRED ]
 *		  | 	[attr: class CDATA #REQUIRED ]
 *		  | 	EMPTY : String
 *		  | replaceWith <replace-with> : ReplaceWith
 *		  | 	[attr: class CDATA #REQUIRED ]
 *		  | 	(
 *		  | 	  | whenPropertyIs <when-property-is> : boolean
 *		  | 	  | 	[attr: name CDATA #REQUIRED ]
 *		  | 	  | 	[attr: value CDATA #REQUIRED ]
 *		  | 	  | 	EMPTY : String
 *		  | 	  | whenTypeAssignable <when-type-assignable> : boolean
 *		  | 	  | 	[attr: class CDATA #REQUIRED ]
 *		  | 	  | 	EMPTY : String
 *		  | 	  | whenTypeIs <when-type-is> : boolean
 *		  | 	  | 	[attr: class CDATA #REQUIRED ]
 *		  | 	  | 	EMPTY : String
 *		  | 	  | all <all> : All
 *		  | 	  | 	(
 *		  | 	  | 	  | whenPropertyIs <when-property-is> : boolean
 *		  | 	  | 	  | 	[attr: name CDATA #REQUIRED ]
 *		  | 	  | 	  | 	[attr: value CDATA #REQUIRED ]
 *		  | 	  | 	  | 	EMPTY : String
 *		  | 	  | 	  | whenTypeAssignable <when-type-assignable> : boolean
 *		  | 	  | 	  | 	[attr: class CDATA #REQUIRED ]
 *		  | 	  | 	  | 	EMPTY : String
 *		  | 	  | 	  | whenTypeIs <when-type-is> : boolean
 *		  | 	  | 	  | 	[attr: class CDATA #REQUIRED ]
 *		  | 	  | 	  | 	EMPTY : String
 *		  | 	  | 	  | all <all> : All...
 *		  | 	  | 	  | any <any> : Any
 *		  | 	  | 	  | 	(
 *		  | 	  | 	  | 	  | whenPropertyIs <when-property-is> : boolean
 *		  | 	  | 	  | 	  | 	[attr: name CDATA #REQUIRED ]
 *		  | 	  | 	  | 	  | 	[attr: value CDATA #REQUIRED ]
 *		  | 	  | 	  | 	  | 	EMPTY : String
 *		  | 	  | 	  | 	  | whenTypeAssignable <when-type-assignable> : boolean
 *		  | 	  | 	  | 	  | 	[attr: class CDATA #REQUIRED ]
 *		  | 	  | 	  | 	  | 	EMPTY : String
 *		  | 	  | 	  | 	  | whenTypeIs <when-type-is> : boolean
 *		  | 	  | 	  | 	  | 	[attr: class CDATA #REQUIRED ]
 *		  | 	  | 	  | 	  | 	EMPTY : String
 *		  | 	  | 	  | 	  | all <all> : All...
 *		  | 	  | 	  | 	  | any <any> : Any...
 *		  | 	  | 	  | 	  | none <none> : None
 *		  | 	  | 	  | 	  | 	(
 *		  | 	  | 	  | 	  | 	  | whenPropertyIs <when-property-is> : boolean
 *		  | 	  | 	  | 	  | 	  | 	[attr: name CDATA #REQUIRED ]
 *		  | 	  | 	  | 	  | 	  | 	[attr: value CDATA #REQUIRED ]
 *		  | 	  | 	  | 	  | 	  | 	EMPTY : String
 *		  | 	  | 	  | 	  | 	  | whenTypeAssignable <when-type-assignable> : boolean
 *		  | 	  | 	  | 	  | 	  | 	[attr: class CDATA #REQUIRED ]
 *		  | 	  | 	  | 	  | 	  | 	EMPTY : String
 *		  | 	  | 	  | 	  | 	  | whenTypeIs <when-type-is> : boolean
 *		  | 	  | 	  | 	  | 	  | 	[attr: class CDATA #REQUIRED ]
 *		  | 	  | 	  | 	  | 	  | 	EMPTY : String
 *		  | 	  | 	  | 	  | 	  | all <all> : All...
 *		  | 	  | 	  | 	  | 	  | any <any> : Any...
 *		  | 	  | 	  | 	  | 	  | none <none> : None...
 *		  | 	  | 	  | 	  | 	)[0,n]
 *		  | 	  | 	  | 	)[0,n]
 *		  | 	  | 	  | none <none> : None
 *		  | 	  | 	  | 	(
 *		  | 	  | 	  | 	  | whenPropertyIs <when-property-is> : boolean
 *		  | 	  | 	  | 	  | 	[attr: name CDATA #REQUIRED ]
 *		  | 	  | 	  | 	  | 	[attr: value CDATA #REQUIRED ]
 *		  | 	  | 	  | 	  | 	EMPTY : String
 *		  | 	  | 	  | 	  | whenTypeAssignable <when-type-assignable> : boolean
 *		  | 	  | 	  | 	  | 	[attr: class CDATA #REQUIRED ]
 *		  | 	  | 	  | 	  | 	EMPTY : String
 *		  | 	  | 	  | 	  | whenTypeIs <when-type-is> : boolean
 *		  | 	  | 	  | 	  | 	[attr: class CDATA #REQUIRED ]
 *		  | 	  | 	  | 	  | 	EMPTY : String
 *		  | 	  | 	  | 	  | all <all> : All...
 *		  | 	  | 	  | 	  | any <any> : Any
 *		  | 	  | 	  | 	  | 	(
 *		  | 	  | 	  | 	  | 	  | whenPropertyIs <when-property-is> : boolean
 *		  | 	  | 	  | 	  | 	  | 	[attr: name CDATA #REQUIRED ]
 *		  | 	  | 	  | 	  | 	  | 	[attr: value CDATA #REQUIRED ]
 *		  | 	  | 	  | 	  | 	  | 	EMPTY : String
 *		  | 	  | 	  | 	  | 	  | whenTypeAssignable <when-type-assignable> : boolean
 *		  | 	  | 	  | 	  | 	  | 	[attr: class CDATA #REQUIRED ]
 *		  | 	  | 	  | 	  | 	  | 	EMPTY : String
 *		  | 	  | 	  | 	  | 	  | whenTypeIs <when-type-is> : boolean
 *		  | 	  | 	  | 	  | 	  | 	[attr: class CDATA #REQUIRED ]
 *		  | 	  | 	  | 	  | 	  | 	EMPTY : String
 *		  | 	  | 	  | 	  | 	  | all <all> : All...
 *		  | 	  | 	  | 	  | 	  | any <any> : Any...
 *		  | 	  | 	  | 	  | 	  | none <none> : None...
 *		  | 	  | 	  | 	  | 	)[0,n]
 *		  | 	  | 	  | 	  | none <none> : None...
 *		  | 	  | 	  | 	)[0,n]
 *		  | 	  | 	)[0,n]
 *		  | 	  | any <any> : Any
 *		  | 	  | 	(
 *		  | 	  | 	  | whenPropertyIs <when-property-is> : boolean
 *		  | 	  | 	  | 	[attr: name CDATA #REQUIRED ]
 *		  | 	  | 	  | 	[attr: value CDATA #REQUIRED ]
 *		  | 	  | 	  | 	EMPTY : String
 *		  | 	  | 	  | whenTypeAssignable <when-type-assignable> : boolean
 *		  | 	  | 	  | 	[attr: class CDATA #REQUIRED ]
 *		  | 	  | 	  | 	EMPTY : String
 *		  | 	  | 	  | whenTypeIs <when-type-is> : boolean
 *		  | 	  | 	  | 	[attr: class CDATA #REQUIRED ]
 *		  | 	  | 	  | 	EMPTY : String
 *		  | 	  | 	  | all <all> : All
 *		  | 	  | 	  | 	(
 *		  | 	  | 	  | 	  | whenPropertyIs <when-property-is> : boolean
 *		  | 	  | 	  | 	  | 	[attr: name CDATA #REQUIRED ]
 *		  | 	  | 	  | 	  | 	[attr: value CDATA #REQUIRED ]
 *		  | 	  | 	  | 	  | 	EMPTY : String
 *		  | 	  | 	  | 	  | whenTypeAssignable <when-type-assignable> : boolean
 *		  | 	  | 	  | 	  | 	[attr: class CDATA #REQUIRED ]
 *		  | 	  | 	  | 	  | 	EMPTY : String
 *		  | 	  | 	  | 	  | whenTypeIs <when-type-is> : boolean
 *		  | 	  | 	  | 	  | 	[attr: class CDATA #REQUIRED ]
 *		  | 	  | 	  | 	  | 	EMPTY : String
 *		  | 	  | 	  | 	  | all <all> : All...
 *		  | 	  | 	  | 	  | any <any> : Any...
 *		  | 	  | 	  | 	  | none <none> : None
 *		  | 	  | 	  | 	  | 	(
 *		  | 	  | 	  | 	  | 	  | whenPropertyIs <when-property-is> : boolean
 *		  | 	  | 	  | 	  | 	  | 	[attr: name CDATA #REQUIRED ]
 *		  | 	  | 	  | 	  | 	  | 	[attr: value CDATA #REQUIRED ]
 *		  | 	  | 	  | 	  | 	  | 	EMPTY : String
 *		  | 	  | 	  | 	  | 	  | whenTypeAssignable <when-type-assignable> : boolean
 *		  | 	  | 	  | 	  | 	  | 	[attr: class CDATA #REQUIRED ]
 *		  | 	  | 	  | 	  | 	  | 	EMPTY : String
 *		  | 	  | 	  | 	  | 	  | whenTypeIs <when-type-is> : boolean
 *		  | 	  | 	  | 	  | 	  | 	[attr: class CDATA #REQUIRED ]
 *		  | 	  | 	  | 	  | 	  | 	EMPTY : String
 *		  | 	  | 	  | 	  | 	  | all <all> : All...
 *		  | 	  | 	  | 	  | 	  | any <any> : Any...
 *		  | 	  | 	  | 	  | 	  | none <none> : None...
 *		  | 	  | 	  | 	  | 	)[0,n]
 *		  | 	  | 	  | 	)[0,n]
 *		  | 	  | 	  | any <any> : Any...
 *		  | 	  | 	  | none <none> : None
 *		  | 	  | 	  | 	(
 *		  | 	  | 	  | 	  | whenPropertyIs <when-property-is> : boolean
 *		  | 	  | 	  | 	  | 	[attr: name CDATA #REQUIRED ]
 *		  | 	  | 	  | 	  | 	[attr: value CDATA #REQUIRED ]
 *		  | 	  | 	  | 	  | 	EMPTY : String
 *		  | 	  | 	  | 	  | whenTypeAssignable <when-type-assignable> : boolean
 *		  | 	  | 	  | 	  | 	[attr: class CDATA #REQUIRED ]
 *		  | 	  | 	  | 	  | 	EMPTY : String
 *		  | 	  | 	  | 	  | whenTypeIs <when-type-is> : boolean
 *		  | 	  | 	  | 	  | 	[attr: class CDATA #REQUIRED ]
 *		  | 	  | 	  | 	  | 	EMPTY : String
 *		  | 	  | 	  | 	  | all <all> : All
 *		  | 	  | 	  | 	  | 	(
 *		  | 	  | 	  | 	  | 	  | whenPropertyIs <when-property-is> : boolean
 *		  | 	  | 	  | 	  | 	  | 	[attr: name CDATA #REQUIRED ]
 *		  | 	  | 	  | 	  | 	  | 	[attr: value CDATA #REQUIRED ]
 *		  | 	  | 	  | 	  | 	  | 	EMPTY : String
 *		  | 	  | 	  | 	  | 	  | whenTypeAssignable <when-type-assignable> : boolean
 *		  | 	  | 	  | 	  | 	  | 	[attr: class CDATA #REQUIRED ]
 *		  | 	  | 	  | 	  | 	  | 	EMPTY : String
 *		  | 	  | 	  | 	  | 	  | whenTypeIs <when-type-is> : boolean
 *		  | 	  | 	  | 	  | 	  | 	[attr: class CDATA #REQUIRED ]
 *		  | 	  | 	  | 	  | 	  | 	EMPTY : String
 *		  | 	  | 	  | 	  | 	  | all <all> : All...
 *		  | 	  | 	  | 	  | 	  | any <any> : Any...
 *		  | 	  | 	  | 	  | 	  | none <none> : None...
 *		  | 	  | 	  | 	  | 	)[0,n]
 *		  | 	  | 	  | 	  | any <any> : Any...
 *		  | 	  | 	  | 	  | none <none> : None...
 *		  | 	  | 	  | 	)[0,n]
 *		  | 	  | 	)[0,n]
 *		  | 	  | none <none> : None
 *		  | 	  | 	(
 *		  | 	  | 	  | whenPropertyIs <when-property-is> : boolean
 *		  | 	  | 	  | 	[attr: name CDATA #REQUIRED ]
 *		  | 	  | 	  | 	[attr: value CDATA #REQUIRED ]
 *		  | 	  | 	  | 	EMPTY : String
 *		  | 	  | 	  | whenTypeAssignable <when-type-assignable> : boolean
 *		  | 	  | 	  | 	[attr: class CDATA #REQUIRED ]
 *		  | 	  | 	  | 	EMPTY : String
 *		  | 	  | 	  | whenTypeIs <when-type-is> : boolean
 *		  | 	  | 	  | 	[attr: class CDATA #REQUIRED ]
 *		  | 	  | 	  | 	EMPTY : String
 *		  | 	  | 	  | all <all> : All
 *		  | 	  | 	  | 	(
 *		  | 	  | 	  | 	  | whenPropertyIs <when-property-is> : boolean
 *		  | 	  | 	  | 	  | 	[attr: name CDATA #REQUIRED ]
 *		  | 	  | 	  | 	  | 	[attr: value CDATA #REQUIRED ]
 *		  | 	  | 	  | 	  | 	EMPTY : String
 *		  | 	  | 	  | 	  | whenTypeAssignable <when-type-assignable> : boolean
 *		  | 	  | 	  | 	  | 	[attr: class CDATA #REQUIRED ]
 *		  | 	  | 	  | 	  | 	EMPTY : String
 *		  | 	  | 	  | 	  | whenTypeIs <when-type-is> : boolean
 *		  | 	  | 	  | 	  | 	[attr: class CDATA #REQUIRED ]
 *		  | 	  | 	  | 	  | 	EMPTY : String
 *		  | 	  | 	  | 	  | all <all> : All...
 *		  | 	  | 	  | 	  | any <any> : Any
 *		  | 	  | 	  | 	  | 	(
 *		  | 	  | 	  | 	  | 	  | whenPropertyIs <when-property-is> : boolean
 *		  | 	  | 	  | 	  | 	  | 	[attr: name CDATA #REQUIRED ]
 *		  | 	  | 	  | 	  | 	  | 	[attr: value CDATA #REQUIRED ]
 *		  | 	  | 	  | 	  | 	  | 	EMPTY : String
 *		  | 	  | 	  | 	  | 	  | whenTypeAssignable <when-type-assignable> : boolean
 *		  | 	  | 	  | 	  | 	  | 	[attr: class CDATA #REQUIRED ]
 *		  | 	  | 	  | 	  | 	  | 	EMPTY : String
 *		  | 	  | 	  | 	  | 	  | whenTypeIs <when-type-is> : boolean
 *		  | 	  | 	  | 	  | 	  | 	[attr: class CDATA #REQUIRED ]
 *		  | 	  | 	  | 	  | 	  | 	EMPTY : String
 *		  | 	  | 	  | 	  | 	  | all <all> : All...
 *		  | 	  | 	  | 	  | 	  | any <any> : Any...
 *		  | 	  | 	  | 	  | 	  | none <none> : None...
 *		  | 	  | 	  | 	  | 	)[0,n]
 *		  | 	  | 	  | 	  | none <none> : None...
 *		  | 	  | 	  | 	)[0,n]
 *		  | 	  | 	  | any <any> : Any
 *		  | 	  | 	  | 	(
 *		  | 	  | 	  | 	  | whenPropertyIs <when-property-is> : boolean
 *		  | 	  | 	  | 	  | 	[attr: name CDATA #REQUIRED ]
 *		  | 	  | 	  | 	  | 	[attr: value CDATA #REQUIRED ]
 *		  | 	  | 	  | 	  | 	EMPTY : String
 *		  | 	  | 	  | 	  | whenTypeAssignable <when-type-assignable> : boolean
 *		  | 	  | 	  | 	  | 	[attr: class CDATA #REQUIRED ]
 *		  | 	  | 	  | 	  | 	EMPTY : String
 *		  | 	  | 	  | 	  | whenTypeIs <when-type-is> : boolean
 *		  | 	  | 	  | 	  | 	[attr: class CDATA #REQUIRED ]
 *		  | 	  | 	  | 	  | 	EMPTY : String
 *		  | 	  | 	  | 	  | all <all> : All
 *		  | 	  | 	  | 	  | 	(
 *		  | 	  | 	  | 	  | 	  | whenPropertyIs <when-property-is> : boolean
 *		  | 	  | 	  | 	  | 	  | 	[attr: name CDATA #REQUIRED ]
 *		  | 	  | 	  | 	  | 	  | 	[attr: value CDATA #REQUIRED ]
 *		  | 	  | 	  | 	  | 	  | 	EMPTY : String
 *		  | 	  | 	  | 	  | 	  | whenTypeAssignable <when-type-assignable> : boolean
 *		  | 	  | 	  | 	  | 	  | 	[attr: class CDATA #REQUIRED ]
 *		  | 	  | 	  | 	  | 	  | 	EMPTY : String
 *		  | 	  | 	  | 	  | 	  | whenTypeIs <when-type-is> : boolean
 *		  | 	  | 	  | 	  | 	  | 	[attr: class CDATA #REQUIRED ]
 *		  | 	  | 	  | 	  | 	  | 	EMPTY : String
 *		  | 	  | 	  | 	  | 	  | all <all> : All...
 *		  | 	  | 	  | 	  | 	  | any <any> : Any...
 *		  | 	  | 	  | 	  | 	  | none <none> : None...
 *		  | 	  | 	  | 	  | 	)[0,n]
 *		  | 	  | 	  | 	  | any <any> : Any...
 *		  | 	  | 	  | 	  | none <none> : None...
 *		  | 	  | 	  | 	)[0,n]
 *		  | 	  | 	  | none <none> : None...
 *		  | 	  | 	)[0,n]
 *		  | 	)[0,n]
 *		  | generateWith <generate-with> : GenerateWith
 *		  | 	[attr: class CDATA #REQUIRED ]
 *		  | 	(
 *		  | 	  | whenPropertyIs <when-property-is> : boolean
 *		  | 	  | 	[attr: name CDATA #REQUIRED ]
 *		  | 	  | 	[attr: value CDATA #REQUIRED ]
 *		  | 	  | 	EMPTY : String
 *		  | 	  | whenTypeAssignable <when-type-assignable> : boolean
 *		  | 	  | 	[attr: class CDATA #REQUIRED ]
 *		  | 	  | 	EMPTY : String
 *		  | 	  | whenTypeIs <when-type-is> : boolean
 *		  | 	  | 	[attr: class CDATA #REQUIRED ]
 *		  | 	  | 	EMPTY : String
 *		  | 	  | all <all> : All
 *		  | 	  | 	(
 *		  | 	  | 	  | whenPropertyIs <when-property-is> : boolean
 *		  | 	  | 	  | 	[attr: name CDATA #REQUIRED ]
 *		  | 	  | 	  | 	[attr: value CDATA #REQUIRED ]
 *		  | 	  | 	  | 	EMPTY : String
 *		  | 	  | 	  | whenTypeAssignable <when-type-assignable> : boolean
 *		  | 	  | 	  | 	[attr: class CDATA #REQUIRED ]
 *		  | 	  | 	  | 	EMPTY : String
 *		  | 	  | 	  | whenTypeIs <when-type-is> : boolean
 *		  | 	  | 	  | 	[attr: class CDATA #REQUIRED ]
 *		  | 	  | 	  | 	EMPTY : String
 *		  | 	  | 	  | all <all> : All...
 *		  | 	  | 	  | any <any> : Any
 *		  | 	  | 	  | 	(
 *		  | 	  | 	  | 	  | whenPropertyIs <when-property-is> : boolean
 *		  | 	  | 	  | 	  | 	[attr: name CDATA #REQUIRED ]
 *		  | 	  | 	  | 	  | 	[attr: value CDATA #REQUIRED ]
 *		  | 	  | 	  | 	  | 	EMPTY : String
 *		  | 	  | 	  | 	  | whenTypeAssignable <when-type-assignable> : boolean
 *		  | 	  | 	  | 	  | 	[attr: class CDATA #REQUIRED ]
 *		  | 	  | 	  | 	  | 	EMPTY : String
 *		  | 	  | 	  | 	  | whenTypeIs <when-type-is> : boolean
 *		  | 	  | 	  | 	  | 	[attr: class CDATA #REQUIRED ]
 *		  | 	  | 	  | 	  | 	EMPTY : String
 *		  | 	  | 	  | 	  | all <all> : All...
 *		  | 	  | 	  | 	  | any <any> : Any...
 *		  | 	  | 	  | 	  | none <none> : None
 *		  | 	  | 	  | 	  | 	(
 *		  | 	  | 	  | 	  | 	  | whenPropertyIs <when-property-is> : boolean
 *		  | 	  | 	  | 	  | 	  | 	[attr: name CDATA #REQUIRED ]
 *		  | 	  | 	  | 	  | 	  | 	[attr: value CDATA #REQUIRED ]
 *		  | 	  | 	  | 	  | 	  | 	EMPTY : String
 *		  | 	  | 	  | 	  | 	  | whenTypeAssignable <when-type-assignable> : boolean
 *		  | 	  | 	  | 	  | 	  | 	[attr: class CDATA #REQUIRED ]
 *		  | 	  | 	  | 	  | 	  | 	EMPTY : String
 *		  | 	  | 	  | 	  | 	  | whenTypeIs <when-type-is> : boolean
 *		  | 	  | 	  | 	  | 	  | 	[attr: class CDATA #REQUIRED ]
 *		  | 	  | 	  | 	  | 	  | 	EMPTY : String
 *		  | 	  | 	  | 	  | 	  | all <all> : All...
 *		  | 	  | 	  | 	  | 	  | any <any> : Any...
 *		  | 	  | 	  | 	  | 	  | none <none> : None...
 *		  | 	  | 	  | 	  | 	)[0,n]
 *		  | 	  | 	  | 	)[0,n]
 *		  | 	  | 	  | none <none> : None
 *		  | 	  | 	  | 	(
 *		  | 	  | 	  | 	  | whenPropertyIs <when-property-is> : boolean
 *		  | 	  | 	  | 	  | 	[attr: name CDATA #REQUIRED ]
 *		  | 	  | 	  | 	  | 	[attr: value CDATA #REQUIRED ]
 *		  | 	  | 	  | 	  | 	EMPTY : String
 *		  | 	  | 	  | 	  | whenTypeAssignable <when-type-assignable> : boolean
 *		  | 	  | 	  | 	  | 	[attr: class CDATA #REQUIRED ]
 *		  | 	  | 	  | 	  | 	EMPTY : String
 *		  | 	  | 	  | 	  | whenTypeIs <when-type-is> : boolean
 *		  | 	  | 	  | 	  | 	[attr: class CDATA #REQUIRED ]
 *		  | 	  | 	  | 	  | 	EMPTY : String
 *		  | 	  | 	  | 	  | all <all> : All...
 *		  | 	  | 	  | 	  | any <any> : Any
 *		  | 	  | 	  | 	  | 	(
 *		  | 	  | 	  | 	  | 	  | whenPropertyIs <when-property-is> : boolean
 *		  | 	  | 	  | 	  | 	  | 	[attr: name CDATA #REQUIRED ]
 *		  | 	  | 	  | 	  | 	  | 	[attr: value CDATA #REQUIRED ]
 *		  | 	  | 	  | 	  | 	  | 	EMPTY : String
 *		  | 	  | 	  | 	  | 	  | whenTypeAssignable <when-type-assignable> : boolean
 *		  | 	  | 	  | 	  | 	  | 	[attr: class CDATA #REQUIRED ]
 *		  | 	  | 	  | 	  | 	  | 	EMPTY : String
 *		  | 	  | 	  | 	  | 	  | whenTypeIs <when-type-is> : boolean
 *		  | 	  | 	  | 	  | 	  | 	[attr: class CDATA #REQUIRED ]
 *		  | 	  | 	  | 	  | 	  | 	EMPTY : String
 *		  | 	  | 	  | 	  | 	  | all <all> : All...
 *		  | 	  | 	  | 	  | 	  | any <any> : Any...
 *		  | 	  | 	  | 	  | 	  | none <none> : None...
 *		  | 	  | 	  | 	  | 	)[0,n]
 *		  | 	  | 	  | 	  | none <none> : None...
 *		  | 	  | 	  | 	)[0,n]
 *		  | 	  | 	)[0,n]
 *		  | 	  | any <any> : Any
 *		  | 	  | 	(
 *		  | 	  | 	  | whenPropertyIs <when-property-is> : boolean
 *		  | 	  | 	  | 	[attr: name CDATA #REQUIRED ]
 *		  | 	  | 	  | 	[attr: value CDATA #REQUIRED ]
 *		  | 	  | 	  | 	EMPTY : String
 *		  | 	  | 	  | whenTypeAssignable <when-type-assignable> : boolean
 *		  | 	  | 	  | 	[attr: class CDATA #REQUIRED ]
 *		  | 	  | 	  | 	EMPTY : String
 *		  | 	  | 	  | whenTypeIs <when-type-is> : boolean
 *		  | 	  | 	  | 	[attr: class CDATA #REQUIRED ]
 *		  | 	  | 	  | 	EMPTY : String
 *		  | 	  | 	  | all <all> : All
 *		  | 	  | 	  | 	(
 *		  | 	  | 	  | 	  | whenPropertyIs <when-property-is> : boolean
 *		  | 	  | 	  | 	  | 	[attr: name CDATA #REQUIRED ]
 *		  | 	  | 	  | 	  | 	[attr: value CDATA #REQUIRED ]
 *		  | 	  | 	  | 	  | 	EMPTY : String
 *		  | 	  | 	  | 	  | whenTypeAssignable <when-type-assignable> : boolean
 *		  | 	  | 	  | 	  | 	[attr: class CDATA #REQUIRED ]
 *		  | 	  | 	  | 	  | 	EMPTY : String
 *		  | 	  | 	  | 	  | whenTypeIs <when-type-is> : boolean
 *		  | 	  | 	  | 	  | 	[attr: class CDATA #REQUIRED ]
 *		  | 	  | 	  | 	  | 	EMPTY : String
 *		  | 	  | 	  | 	  | all <all> : All...
 *		  | 	  | 	  | 	  | any <any> : Any...
 *		  | 	  | 	  | 	  | none <none> : None
 *		  | 	  | 	  | 	  | 	(
 *		  | 	  | 	  | 	  | 	  | whenPropertyIs <when-property-is> : boolean
 *		  | 	  | 	  | 	  | 	  | 	[attr: name CDATA #REQUIRED ]
 *		  | 	  | 	  | 	  | 	  | 	[attr: value CDATA #REQUIRED ]
 *		  | 	  | 	  | 	  | 	  | 	EMPTY : String
 *		  | 	  | 	  | 	  | 	  | whenTypeAssignable <when-type-assignable> : boolean
 *		  | 	  | 	  | 	  | 	  | 	[attr: class CDATA #REQUIRED ]
 *		  | 	  | 	  | 	  | 	  | 	EMPTY : String
 *		  | 	  | 	  | 	  | 	  | whenTypeIs <when-type-is> : boolean
 *		  | 	  | 	  | 	  | 	  | 	[attr: class CDATA #REQUIRED ]
 *		  | 	  | 	  | 	  | 	  | 	EMPTY : String
 *		  | 	  | 	  | 	  | 	  | all <all> : All...
 *		  | 	  | 	  | 	  | 	  | any <any> : Any...
 *		  | 	  | 	  | 	  | 	  | none <none> : None...
 *		  | 	  | 	  | 	  | 	)[0,n]
 *		  | 	  | 	  | 	)[0,n]
 *		  | 	  | 	  | any <any> : Any...
 *		  | 	  | 	  | none <none> : None
 *		  | 	  | 	  | 	(
 *		  | 	  | 	  | 	  | whenPropertyIs <when-property-is> : boolean
 *		  | 	  | 	  | 	  | 	[attr: name CDATA #REQUIRED ]
 *		  | 	  | 	  | 	  | 	[attr: value CDATA #REQUIRED ]
 *		  | 	  | 	  | 	  | 	EMPTY : String
 *		  | 	  | 	  | 	  | whenTypeAssignable <when-type-assignable> : boolean
 *		  | 	  | 	  | 	  | 	[attr: class CDATA #REQUIRED ]
 *		  | 	  | 	  | 	  | 	EMPTY : String
 *		  | 	  | 	  | 	  | whenTypeIs <when-type-is> : boolean
 *		  | 	  | 	  | 	  | 	[attr: class CDATA #REQUIRED ]
 *		  | 	  | 	  | 	  | 	EMPTY : String
 *		  | 	  | 	  | 	  | all <all> : All
 *		  | 	  | 	  | 	  | 	(
 *		  | 	  | 	  | 	  | 	  | whenPropertyIs <when-property-is> : boolean
 *		  | 	  | 	  | 	  | 	  | 	[attr: name CDATA #REQUIRED ]
 *		  | 	  | 	  | 	  | 	  | 	[attr: value CDATA #REQUIRED ]
 *		  | 	  | 	  | 	  | 	  | 	EMPTY : String
 *		  | 	  | 	  | 	  | 	  | whenTypeAssignable <when-type-assignable> : boolean
 *		  | 	  | 	  | 	  | 	  | 	[attr: class CDATA #REQUIRED ]
 *		  | 	  | 	  | 	  | 	  | 	EMPTY : String
 *		  | 	  | 	  | 	  | 	  | whenTypeIs <when-type-is> : boolean
 *		  | 	  | 	  | 	  | 	  | 	[attr: class CDATA #REQUIRED ]
 *		  | 	  | 	  | 	  | 	  | 	EMPTY : String
 *		  | 	  | 	  | 	  | 	  | all <all> : All...
 *		  | 	  | 	  | 	  | 	  | any <any> : Any...
 *		  | 	  | 	  | 	  | 	  | none <none> : None...
 *		  | 	  | 	  | 	  | 	)[0,n]
 *		  | 	  | 	  | 	  | any <any> : Any...
 *		  | 	  | 	  | 	  | none <none> : None...
 *		  | 	  | 	  | 	)[0,n]
 *		  | 	  | 	)[0,n]
 *		  | 	  | none <none> : None
 *		  | 	  | 	(
 *		  | 	  | 	  | whenPropertyIs <when-property-is> : boolean
 *		  | 	  | 	  | 	[attr: name CDATA #REQUIRED ]
 *		  | 	  | 	  | 	[attr: value CDATA #REQUIRED ]
 *		  | 	  | 	  | 	EMPTY : String
 *		  | 	  | 	  | whenTypeAssignable <when-type-assignable> : boolean
 *		  | 	  | 	  | 	[attr: class CDATA #REQUIRED ]
 *		  | 	  | 	  | 	EMPTY : String
 *		  | 	  | 	  | whenTypeIs <when-type-is> : boolean
 *		  | 	  | 	  | 	[attr: class CDATA #REQUIRED ]
 *		  | 	  | 	  | 	EMPTY : String
 *		  | 	  | 	  | all <all> : All
 *		  | 	  | 	  | 	(
 *		  | 	  | 	  | 	  | whenPropertyIs <when-property-is> : boolean
 *		  | 	  | 	  | 	  | 	[attr: name CDATA #REQUIRED ]
 *		  | 	  | 	  | 	  | 	[attr: value CDATA #REQUIRED ]
 *		  | 	  | 	  | 	  | 	EMPTY : String
 *		  | 	  | 	  | 	  | whenTypeAssignable <when-type-assignable> : boolean
 *		  | 	  | 	  | 	  | 	[attr: class CDATA #REQUIRED ]
 *		  | 	  | 	  | 	  | 	EMPTY : String
 *		  | 	  | 	  | 	  | whenTypeIs <when-type-is> : boolean
 *		  | 	  | 	  | 	  | 	[attr: class CDATA #REQUIRED ]
 *		  | 	  | 	  | 	  | 	EMPTY : String
 *		  | 	  | 	  | 	  | all <all> : All...
 *		  | 	  | 	  | 	  | any <any> : Any
 *		  | 	  | 	  | 	  | 	(
 *		  | 	  | 	  | 	  | 	  | whenPropertyIs <when-property-is> : boolean
 *		  | 	  | 	  | 	  | 	  | 	[attr: name CDATA #REQUIRED ]
 *		  | 	  | 	  | 	  | 	  | 	[attr: value CDATA #REQUIRED ]
 *		  | 	  | 	  | 	  | 	  | 	EMPTY : String
 *		  | 	  | 	  | 	  | 	  | whenTypeAssignable <when-type-assignable> : boolean
 *		  | 	  | 	  | 	  | 	  | 	[attr: class CDATA #REQUIRED ]
 *		  | 	  | 	  | 	  | 	  | 	EMPTY : String
 *		  | 	  | 	  | 	  | 	  | whenTypeIs <when-type-is> : boolean
 *		  | 	  | 	  | 	  | 	  | 	[attr: class CDATA #REQUIRED ]
 *		  | 	  | 	  | 	  | 	  | 	EMPTY : String
 *		  | 	  | 	  | 	  | 	  | all <all> : All...
 *		  | 	  | 	  | 	  | 	  | any <any> : Any...
 *		  | 	  | 	  | 	  | 	  | none <none> : None...
 *		  | 	  | 	  | 	  | 	)[0,n]
 *		  | 	  | 	  | 	  | none <none> : None...
 *		  | 	  | 	  | 	)[0,n]
 *		  | 	  | 	  | any <any> : Any
 *		  | 	  | 	  | 	(
 *		  | 	  | 	  | 	)[0,n]
 *		  | 	  | 	  | none <none> : None...
 *		  | 	  | 	)[0,n]
 *		  | 	)[0,n]
 *		  | defineProperty <define-property> : boolean
 *		  | 	[attr: name CDATA #REQUIRED ]
 *		  | 	[attr: values CDATA #REQUIRED ]
 *		  | extendProperty <extend-property> : boolean
 *		  | 	[attr: name CDATA #REQUIRED ]
 *		  | 	[attr: values CDATA #REQUIRED ]
 *		  | setProperty <set-property> : boolean
 *		  | 	[attr: name CDATA #REQUIRED ]
 *		  | 	[attr: value CDATA #REQUIRED ]
 *		  | setConfigurationProperty <set-configuration-property> : boolean
 *		  | 	[attr: name CDATA #REQUIRED ]
 *		  | 	[attr: value CDATA #REQUIRED ]
 *		  | propertyProvider <property-provider> : String
 *		  | 	[attr: name CDATA #REQUIRED ]
 *		  | defineLinker <define-linker> : boolean
 *		  | 	[attr: class CDATA #REQUIRED ]
 *		  | 	[attr: name CDATA #REQUIRED ]
 *		  | addLinker <add-linker> : boolean
 *		  | 	[attr: name CDATA #REQUIRED ]
 *		)[0,n]
 *	... etc ...
 *
 * @Generated
 */

package com.nanosn.netbeans.gwtxml.gwtmodule;

import org.w3c.dom.*;
import org.netbeans.modules.schema2beans.*;
import java.beans.*;
import java.util.*;
import java.io.*;

// BEGIN_NOI18N

public class Module extends org.netbeans.modules.schema2beans.BaseBean
	 implements com.nanosn.netbeans.gwtxml.gwtmodule.CommonBean
{

	static Vector comparators = new Vector();
	private static final org.netbeans.modules.schema2beans.Version runtimeVersion = new org.netbeans.modules.schema2beans.Version(5, 0, 0);

	static public final String RENAMETO = "RenameTo";	// NOI18N
	static public final String INHERITS = "Inherits";	// NOI18N
	static public final String INHERITSNAME = "InheritsName";	// NOI18N
	static public final String SOURCE = "Source";	// NOI18N
	static public final String PUBLIC = "Public";	// NOI18N
	static public final String SUPER_SOURCE = "SuperSource";	// NOI18N
	static public final String ENTRY_POINT = "EntryPoint";	// NOI18N
	static public final String ENTRYPOINTCLASS = "EntryPointClass";	// NOI18N
	static public final String STYLESHEET = "Stylesheet";	// NOI18N
	static public final String STYLESHEETSRC = "StylesheetSrc";	// NOI18N
	static public final String SCRIPT = "Script";	// NOI18N
	static public final String SCRIPTSRC = "ScriptSrc";	// NOI18N
	static public final String SERVLET = "Servlet";	// NOI18N
	static public final String SERVLETPATH = "ServletPath";	// NOI18N
	static public final String SERVLETCLASS = "ServletClass";	// NOI18N
	static public final String REPLACE_WITH = "ReplaceWith";	// NOI18N
	static public final String GENERATE_WITH = "GenerateWith";	// NOI18N
	static public final String DEFINE_PROPERTY = "DefineProperty";	// NOI18N
	static public final String DEFINEPROPERTYNAME = "DefinePropertyName";	// NOI18N
	static public final String DEFINEPROPERTYVALUES = "DefinePropertyValues";	// NOI18N
	static public final String EXTEND_PROPERTY = "ExtendProperty";	// NOI18N
	static public final String EXTENDPROPERTYNAME = "ExtendPropertyName";	// NOI18N
	static public final String EXTENDPROPERTYVALUES = "ExtendPropertyValues";	// NOI18N
	static public final String SET_PROPERTY = "SetProperty";	// NOI18N
	static public final String SETPROPERTYNAME = "SetPropertyName";	// NOI18N
	static public final String SETPROPERTYVALUE = "SetPropertyValue";	// NOI18N
	static public final String SET_CONFIGURATION_PROPERTY = "SetConfigurationProperty";	// NOI18N
	static public final String SETCONFIGURATIONPROPERTYNAME = "SetConfigurationPropertyName";	// NOI18N
	static public final String SETCONFIGURATIONPROPERTYVALUE = "SetConfigurationPropertyValue";	// NOI18N
	static public final String PROPERTY_PROVIDER = "PropertyProvider";	// NOI18N
	static public final String PROPERTYPROVIDERNAME = "PropertyProviderName";	// NOI18N
	static public final String DEFINE_LINKER = "DefineLinker";	// NOI18N
	static public final String DEFINELINKERCLASS = "DefineLinkerClass";	// NOI18N
	static public final String DEFINELINKERNAME = "DefineLinkerName";	// NOI18N
	static public final String ADD_LINKER = "AddLinker";	// NOI18N
	static public final String ADDLINKERNAME = "AddLinkerName";	// NOI18N

	public Module() {
		this(null, Common.USE_DEFAULT_VALUES);
	}

	public Module(org.w3c.dom.Node doc, int options) {
		this(Common.NO_DEFAULT_VALUES);
		try {
			initFromNode(doc, options);
		}
		catch (Schema2BeansException e) {
			throw new RuntimeException(e);
		}
	}
	protected void initFromNode(org.w3c.dom.Node doc, int options) throws Schema2BeansException
	{
		if (doc == null)
		{
			doc = GraphManager.createRootElementNode("module");	// NOI18N
			if (doc == null)
				throw new Schema2BeansException(Common.getMessage(
					"CantCreateDOMRoot_msg", "module"));
		}
		Node n = GraphManager.getElementNode("module", doc);	// NOI18N
		if (n == null)
			throw new Schema2BeansException(Common.getMessage(
				"DocRootNotInDOMGraph_msg", "module", doc.getFirstChild().getNodeName()));

		this.graphManager.setXmlDocument(doc);

		// Entry point of the createBeans() recursive calls
		this.createBean(n, this.graphManager());
		this.initialize(options);
	}
	public Module(int options)
	{
		super(comparators, runtimeVersion);
		initOptions(options);
	}
	protected void initOptions(int options)
	{
		// The graph manager is allocated in the bean root
		this.graphManager = new GraphManager(this);
		this.createRoot("module", "Module",	// NOI18N
			Common.TYPE_1 | Common.TYPE_BEAN, Module.class);

		// Properties (see root bean comments for the bean graph)
		initPropertyTables(17);
		this.createProperty("inherits", 	// NOI18N
			INHERITS, Common.SEQUENCE_OR | 
			Common.TYPE_0_N | Common.TYPE_BOOLEAN | Common.TYPE_KEY, 
			Boolean.class);
		this.createAttribute(INHERITS, "name", "Name", 
						AttrProp.CDATA | AttrProp.REQUIRED,
						null, null);
		this.createProperty("source", 	// NOI18N
			SOURCE, Common.SEQUENCE_OR | 
			Common.TYPE_0_N | Common.TYPE_BEAN | Common.TYPE_KEY, 
			Source.class);
		this.createAttribute(SOURCE, "path", "Path", 
						AttrProp.CDATA | AttrProp.REQUIRED,
						null, null);
		this.createAttribute(SOURCE, "includes", "Includes", 
						AttrProp.CDATA | AttrProp.IMPLIED,
						null, null);
		this.createAttribute(SOURCE, "excludes", "Excludes", 
						AttrProp.CDATA | AttrProp.IMPLIED,
						null, null);
		this.createAttribute(SOURCE, "defaultexcludes", "Defaultexcludes", 
						AttrProp.ENUM,
						new String[] {
							"yes",
							"no"
						}, "yes");
		this.createAttribute(SOURCE, "casesensitive", "Casesensitive", 
						AttrProp.ENUM,
						new String[] {
							"true",
							"false"
						}, "true");
		this.createProperty("public", 	// NOI18N
			PUBLIC, Common.SEQUENCE_OR | 
			Common.TYPE_0_N | Common.TYPE_BEAN | Common.TYPE_KEY, 
			Public.class);
		this.createAttribute(PUBLIC, "path", "Path", 
						AttrProp.CDATA | AttrProp.REQUIRED,
						null, null);
		this.createAttribute(PUBLIC, "includes", "Includes", 
						AttrProp.CDATA | AttrProp.IMPLIED,
						null, null);
		this.createAttribute(PUBLIC, "excludes", "Excludes", 
						AttrProp.CDATA | AttrProp.IMPLIED,
						null, null);
		this.createAttribute(PUBLIC, "defaultexcludes", "Defaultexcludes", 
						AttrProp.ENUM,
						new String[] {
							"yes",
							"no"
						}, "yes");
		this.createAttribute(PUBLIC, "casesensitive", "Casesensitive", 
						AttrProp.ENUM,
						new String[] {
							"true",
							"false"
						}, "true");
		this.createProperty("super-source", 	// NOI18N
			SUPER_SOURCE, Common.SEQUENCE_OR | 
			Common.TYPE_0_N | Common.TYPE_BEAN | Common.TYPE_KEY, 
			SuperSource.class);
		this.createAttribute(SUPER_SOURCE, "path", "Path", 
						AttrProp.CDATA | AttrProp.REQUIRED,
						null, null);
		this.createAttribute(SUPER_SOURCE, "includes", "Includes", 
						AttrProp.CDATA | AttrProp.IMPLIED,
						null, null);
		this.createAttribute(SUPER_SOURCE, "excludes", "Excludes", 
						AttrProp.CDATA | AttrProp.IMPLIED,
						null, null);
		this.createAttribute(SUPER_SOURCE, "defaultexcludes", "Defaultexcludes", 
						AttrProp.ENUM,
						new String[] {
							"yes",
							"no"
						}, "yes");
		this.createAttribute(SUPER_SOURCE, "casesensitive", "Casesensitive", 
						AttrProp.ENUM,
						new String[] {
							"true",
							"false"
						}, "true");
		this.createProperty("entry-point", 	// NOI18N
			ENTRY_POINT, Common.SEQUENCE_OR | 
			Common.TYPE_0_N | Common.TYPE_BOOLEAN | Common.TYPE_KEY, 
			Boolean.class);
		this.createAttribute(ENTRY_POINT, "class", "Class", 
						AttrProp.CDATA | AttrProp.REQUIRED,
						null, null);
		this.createProperty("stylesheet", 	// NOI18N
			STYLESHEET, Common.SEQUENCE_OR | 
			Common.TYPE_0_N | Common.TYPE_BOOLEAN | Common.TYPE_KEY, 
			Boolean.class);
		this.createAttribute(STYLESHEET, "src", "Src", 
						AttrProp.CDATA | AttrProp.REQUIRED,
						null, null);
		this.createProperty("script", 	// NOI18N
			SCRIPT, Common.SEQUENCE_OR | 
			Common.TYPE_0_N | Common.TYPE_STRING | Common.TYPE_KEY, 
			String.class);
		this.createAttribute(SCRIPT, "src", "Src", 
						AttrProp.CDATA | AttrProp.REQUIRED,
						null, null);
		this.createProperty("servlet", 	// NOI18N
			SERVLET, Common.SEQUENCE_OR | 
			Common.TYPE_0_N | Common.TYPE_BOOLEAN | Common.TYPE_KEY, 
			Boolean.class);
		this.createAttribute(SERVLET, "path", "Path", 
						AttrProp.CDATA | AttrProp.REQUIRED,
						null, null);
		this.createAttribute(SERVLET, "class", "Class", 
						AttrProp.CDATA | AttrProp.REQUIRED,
						null, null);
		this.createProperty("replace-with", 	// NOI18N
			REPLACE_WITH, Common.SEQUENCE_OR | 
			Common.TYPE_0_N | Common.TYPE_BEAN | Common.TYPE_KEY, 
			ReplaceWith.class);
		this.createAttribute(REPLACE_WITH, "class", "Class2", 
						AttrProp.CDATA | AttrProp.REQUIRED,
						null, null);
		this.createProperty("generate-with", 	// NOI18N
			GENERATE_WITH, Common.SEQUENCE_OR | 
			Common.TYPE_0_N | Common.TYPE_BEAN | Common.TYPE_KEY, 
			GenerateWith.class);
		this.createAttribute(GENERATE_WITH, "class", "Class2", 
						AttrProp.CDATA | AttrProp.REQUIRED,
						null, null);
		this.createProperty("define-property", 	// NOI18N
			DEFINE_PROPERTY, Common.SEQUENCE_OR | 
			Common.TYPE_0_N | Common.TYPE_BOOLEAN | Common.TYPE_KEY, 
			Boolean.class);
		this.createAttribute(DEFINE_PROPERTY, "name", "Name", 
						AttrProp.CDATA | AttrProp.REQUIRED,
						null, null);
		this.createAttribute(DEFINE_PROPERTY, "values", "Values", 
						AttrProp.CDATA | AttrProp.REQUIRED,
						null, null);
		this.createProperty("extend-property", 	// NOI18N
			EXTEND_PROPERTY, Common.SEQUENCE_OR | 
			Common.TYPE_0_N | Common.TYPE_BOOLEAN | Common.TYPE_KEY, 
			Boolean.class);
		this.createAttribute(EXTEND_PROPERTY, "name", "Name", 
						AttrProp.CDATA | AttrProp.REQUIRED,
						null, null);
		this.createAttribute(EXTEND_PROPERTY, "values", "Values", 
						AttrProp.CDATA | AttrProp.REQUIRED,
						null, null);
		this.createProperty("set-property", 	// NOI18N
			SET_PROPERTY, Common.SEQUENCE_OR | 
			Common.TYPE_0_N | Common.TYPE_BOOLEAN | Common.TYPE_KEY, 
			Boolean.class);
		this.createAttribute(SET_PROPERTY, "name", "Name", 
						AttrProp.CDATA | AttrProp.REQUIRED,
						null, null);
		this.createAttribute(SET_PROPERTY, "value", "Value", 
						AttrProp.CDATA | AttrProp.REQUIRED,
						null, null);
		this.createProperty("set-configuration-property", 	// NOI18N
			SET_CONFIGURATION_PROPERTY, Common.SEQUENCE_OR | 
			Common.TYPE_0_N | Common.TYPE_BOOLEAN | Common.TYPE_KEY, 
			Boolean.class);
		this.createAttribute(SET_CONFIGURATION_PROPERTY, "name", "Name", 
						AttrProp.CDATA | AttrProp.REQUIRED,
						null, null);
		this.createAttribute(SET_CONFIGURATION_PROPERTY, "value", "Value", 
						AttrProp.CDATA | AttrProp.REQUIRED,
						null, null);
		this.createProperty("property-provider", 	// NOI18N
			PROPERTY_PROVIDER, Common.SEQUENCE_OR | 
			Common.TYPE_0_N | Common.TYPE_STRING | Common.TYPE_KEY, 
			String.class);
		this.createAttribute(PROPERTY_PROVIDER, "name", "Name", 
						AttrProp.CDATA | AttrProp.REQUIRED,
						null, null);
		this.createProperty("define-linker", 	// NOI18N
			DEFINE_LINKER, Common.SEQUENCE_OR | 
			Common.TYPE_0_N | Common.TYPE_BOOLEAN | Common.TYPE_KEY, 
			Boolean.class);
		this.createAttribute(DEFINE_LINKER, "class", "Class", 
						AttrProp.CDATA | AttrProp.REQUIRED,
						null, null);
		this.createAttribute(DEFINE_LINKER, "name", "Name", 
						AttrProp.CDATA | AttrProp.REQUIRED,
						null, null);
		this.createProperty("add-linker", 	// NOI18N
			ADD_LINKER, Common.SEQUENCE_OR | 
			Common.TYPE_0_N | Common.TYPE_BOOLEAN | Common.TYPE_KEY, 
			Boolean.class);
		this.createAttribute(ADD_LINKER, "name", "Name", 
						AttrProp.CDATA | AttrProp.REQUIRED,
						null, null);
		this.createAttribute("rename-to", "RenameTo", 
						AttrProp.CDATA | AttrProp.IMPLIED,
						null, null);
		this.initialize(options);
	}

	// Setting the default values of the properties
	void initialize(int options) {

	}

	// This attribute is optional
	public void setRenameTo(java.lang.String value) {
		setAttributeValue(RENAMETO, value);
	}

	//
	public java.lang.String getRenameTo() {
		return getAttributeValue(RENAMETO);
	}

	// This attribute is an array, possibly empty
	public void setInherits(int index, boolean value) {
		this.setValue(INHERITS, index, (value ? java.lang.Boolean.TRUE : java.lang.Boolean.FALSE));
	}

	//
	public boolean isInherits(int index) {
		Boolean ret = (Boolean)this.getValue(INHERITS, index);
		if (ret == null)
			ret = (Boolean)Common.defaultScalarValue(Common.TYPE_BOOLEAN);
		return ((java.lang.Boolean)ret).booleanValue();
	}

	// Return the number of properties
	public int sizeInherits() {
		return this.size(INHERITS);
	}

	// This attribute is an array, possibly empty
	public void setInherits(boolean[] value) {
		Boolean[] values = null;
		if (value != null)
		{
			values = new Boolean[value.length];
			for (int i=0; i<value.length; i++)
				values[i] = (value[i] ? Boolean.TRUE : Boolean.FALSE);
		}
		this.setValue(INHERITS, values);
	}

	//
	public boolean[] getInherits() {
		boolean[] ret = null;
		Boolean[] values = (Boolean[])this.getValues(INHERITS);
		if (values != null)
		{
			ret = new boolean[values.length];
			for (int i=0; i<values.length; i++)
				ret[i] = values[i].booleanValue();
		}
		return ret;
	}

	// Add a new element returning its index in the list
	public int addInherits(boolean value) {
		int positionOfNewItem = this.addValue(INHERITS, (value ? java.lang.Boolean.TRUE : java.lang.Boolean.FALSE));
		return positionOfNewItem;
	}

	//
	// Remove an element using its reference
	// Returns the index the element had in the list
	//
	public int removeInherits(boolean value) {
		return this.removeValue(INHERITS, (value ? java.lang.Boolean.TRUE : java.lang.Boolean.FALSE));
	}

	//
	// Remove an element using its index
	//
	public void removeInherits(int index) {
		this.removeValue(INHERITS, index);
	}

	// This attribute is an array, possibly empty
	public void setInheritsName(int index, java.lang.String value) {
		// Make sure we've got a place to put this attribute.
		if (size(INHERITS) == 0) {
			addValue(INHERITS, java.lang.Boolean.TRUE);
		}
		setValue(INHERITS, index, java.lang.Boolean.TRUE);
		setAttributeValue(INHERITS, index, "Name", value);
	}

	//
	public java.lang.String getInheritsName(int index) {
		// If our element does not exist, then the attribute does not exist.
		if (size(INHERITS) == 0) {
			return null;
		} else {
			return getAttributeValue(INHERITS, index, "Name");
		}
	}

	// Return the number of properties
	public int sizeInheritsName() {
		return this.size(INHERITS);
	}

	// This attribute is an array, possibly empty
	public void setSource(int index, Source value) {
		this.setValue(SOURCE, index, value);
	}

	//
	public Source getSource(int index) {
		return (Source)this.getValue(SOURCE, index);
	}

	// Return the number of properties
	public int sizeSource() {
		return this.size(SOURCE);
	}

	// This attribute is an array, possibly empty
	public void setSource(Source[] value) {
		this.setValue(SOURCE, value);
	}

	//
	public Source[] getSource() {
		return (Source[])this.getValues(SOURCE);
	}

	// Add a new element returning its index in the list
	public int addSource(com.nanosn.netbeans.gwtxml.gwtmodule.Source value) {
		int positionOfNewItem = this.addValue(SOURCE, value);
		return positionOfNewItem;
	}

	//
	// Remove an element using its reference
	// Returns the index the element had in the list
	//
	public int removeSource(com.nanosn.netbeans.gwtxml.gwtmodule.Source value) {
		return this.removeValue(SOURCE, value);
	}

	// This attribute is an array, possibly empty
	public void setPublic(int index, Public value) {
		this.setValue(PUBLIC, index, value);
	}

	//
	public Public getPublic(int index) {
		return (Public)this.getValue(PUBLIC, index);
	}

	// Return the number of properties
	public int sizePublic() {
		return this.size(PUBLIC);
	}

	// This attribute is an array, possibly empty
	public void setPublic(Public[] value) {
		this.setValue(PUBLIC, value);
	}

	//
	public Public[] getPublic() {
		return (Public[])this.getValues(PUBLIC);
	}

	// Add a new element returning its index in the list
	public int addPublic(com.nanosn.netbeans.gwtxml.gwtmodule.Public value) {
		int positionOfNewItem = this.addValue(PUBLIC, value);
		return positionOfNewItem;
	}

	//
	// Remove an element using its reference
	// Returns the index the element had in the list
	//
	public int removePublic(com.nanosn.netbeans.gwtxml.gwtmodule.Public value) {
		return this.removeValue(PUBLIC, value);
	}

	// This attribute is an array, possibly empty
	public void setSuperSource(int index, SuperSource value) {
		this.setValue(SUPER_SOURCE, index, value);
	}

	//
	public SuperSource getSuperSource(int index) {
		return (SuperSource)this.getValue(SUPER_SOURCE, index);
	}

	// Return the number of properties
	public int sizeSuperSource() {
		return this.size(SUPER_SOURCE);
	}

	// This attribute is an array, possibly empty
	public void setSuperSource(SuperSource[] value) {
		this.setValue(SUPER_SOURCE, value);
	}

	//
	public SuperSource[] getSuperSource() {
		return (SuperSource[])this.getValues(SUPER_SOURCE);
	}

	// Add a new element returning its index in the list
	public int addSuperSource(com.nanosn.netbeans.gwtxml.gwtmodule.SuperSource value) {
		int positionOfNewItem = this.addValue(SUPER_SOURCE, value);
		return positionOfNewItem;
	}

	//
	// Remove an element using its reference
	// Returns the index the element had in the list
	//
	public int removeSuperSource(com.nanosn.netbeans.gwtxml.gwtmodule.SuperSource value) {
		return this.removeValue(SUPER_SOURCE, value);
	}

	// This attribute is an array, possibly empty
	public void setEntryPoint(int index, boolean value) {
		this.setValue(ENTRY_POINT, index, (value ? java.lang.Boolean.TRUE : java.lang.Boolean.FALSE));
	}

	//
	public boolean isEntryPoint(int index) {
		Boolean ret = (Boolean)this.getValue(ENTRY_POINT, index);
		if (ret == null)
			ret = (Boolean)Common.defaultScalarValue(Common.TYPE_BOOLEAN);
		return ((java.lang.Boolean)ret).booleanValue();
	}

	// Return the number of properties
	public int sizeEntryPoint() {
		return this.size(ENTRY_POINT);
	}

	// This attribute is an array, possibly empty
	public void setEntryPoint(boolean[] value) {
		Boolean[] values = null;
		if (value != null)
		{
			values = new Boolean[value.length];
			for (int i=0; i<value.length; i++)
				values[i] = (value[i] ? Boolean.TRUE : Boolean.FALSE);
		}
		this.setValue(ENTRY_POINT, values);
	}

	//
	public boolean[] getEntryPoint() {
		boolean[] ret = null;
		Boolean[] values = (Boolean[])this.getValues(ENTRY_POINT);
		if (values != null)
		{
			ret = new boolean[values.length];
			for (int i=0; i<values.length; i++)
				ret[i] = values[i].booleanValue();
		}
		return ret;
	}

	// Add a new element returning its index in the list
	public int addEntryPoint(boolean value) {
		int positionOfNewItem = this.addValue(ENTRY_POINT, (value ? java.lang.Boolean.TRUE : java.lang.Boolean.FALSE));
		return positionOfNewItem;
	}

	//
	// Remove an element using its reference
	// Returns the index the element had in the list
	//
	public int removeEntryPoint(boolean value) {
		return this.removeValue(ENTRY_POINT, (value ? java.lang.Boolean.TRUE : java.lang.Boolean.FALSE));
	}

	//
	// Remove an element using its index
	//
	public void removeEntryPoint(int index) {
		this.removeValue(ENTRY_POINT, index);
	}

	// This attribute is an array, possibly empty
	public void setEntryPointClass(int index, java.lang.String value) {
		// Make sure we've got a place to put this attribute.
		if (size(ENTRY_POINT) == 0) {
			addValue(ENTRY_POINT, java.lang.Boolean.TRUE);
		}
		setValue(ENTRY_POINT, index, java.lang.Boolean.TRUE);
		setAttributeValue(ENTRY_POINT, index, "Class", value);
	}

	//
	public java.lang.String getEntryPointClass(int index) {
		// If our element does not exist, then the attribute does not exist.
		if (size(ENTRY_POINT) == 0) {
			return null;
		} else {
			return getAttributeValue(ENTRY_POINT, index, "Class");
		}
	}

	// Return the number of properties
	public int sizeEntryPointClass() {
		return this.size(ENTRY_POINT);
	}

	// This attribute is an array, possibly empty
	public void setStylesheet(int index, boolean value) {
		this.setValue(STYLESHEET, index, (value ? java.lang.Boolean.TRUE : java.lang.Boolean.FALSE));
	}

	//
	public boolean isStylesheet(int index) {
		Boolean ret = (Boolean)this.getValue(STYLESHEET, index);
		if (ret == null)
			ret = (Boolean)Common.defaultScalarValue(Common.TYPE_BOOLEAN);
		return ((java.lang.Boolean)ret).booleanValue();
	}

	// Return the number of properties
	public int sizeStylesheet() {
		return this.size(STYLESHEET);
	}

	// This attribute is an array, possibly empty
	public void setStylesheet(boolean[] value) {
		Boolean[] values = null;
		if (value != null)
		{
			values = new Boolean[value.length];
			for (int i=0; i<value.length; i++)
				values[i] = (value[i] ? Boolean.TRUE : Boolean.FALSE);
		}
		this.setValue(STYLESHEET, values);
	}

	//
	public boolean[] getStylesheet() {
		boolean[] ret = null;
		Boolean[] values = (Boolean[])this.getValues(STYLESHEET);
		if (values != null)
		{
			ret = new boolean[values.length];
			for (int i=0; i<values.length; i++)
				ret[i] = values[i].booleanValue();
		}
		return ret;
	}

	// Add a new element returning its index in the list
	public int addStylesheet(boolean value) {
		int positionOfNewItem = this.addValue(STYLESHEET, (value ? java.lang.Boolean.TRUE : java.lang.Boolean.FALSE));
		return positionOfNewItem;
	}

	//
	// Remove an element using its reference
	// Returns the index the element had in the list
	//
	public int removeStylesheet(boolean value) {
		return this.removeValue(STYLESHEET, (value ? java.lang.Boolean.TRUE : java.lang.Boolean.FALSE));
	}

	//
	// Remove an element using its index
	//
	public void removeStylesheet(int index) {
		this.removeValue(STYLESHEET, index);
	}

	// This attribute is an array, possibly empty
	public void setStylesheetSrc(int index, java.lang.String value) {
		// Make sure we've got a place to put this attribute.
		if (size(STYLESHEET) == 0) {
			addValue(STYLESHEET, java.lang.Boolean.TRUE);
		}
		setValue(STYLESHEET, index, java.lang.Boolean.TRUE);
		setAttributeValue(STYLESHEET, index, "Src", value);
	}

	//
	public java.lang.String getStylesheetSrc(int index) {
		// If our element does not exist, then the attribute does not exist.
		if (size(STYLESHEET) == 0) {
			return null;
		} else {
			return getAttributeValue(STYLESHEET, index, "Src");
		}
	}

	// Return the number of properties
	public int sizeStylesheetSrc() {
		return this.size(STYLESHEET);
	}

	// This attribute is an array, possibly empty
	public void setScript(int index, String value) {
		this.setValue(SCRIPT, index, value);
	}

	//
	public String getScript(int index) {
		return (String)this.getValue(SCRIPT, index);
	}

	// Return the number of properties
	public int sizeScript() {
		return this.size(SCRIPT);
	}

	// This attribute is an array, possibly empty
	public void setScript(String[] value) {
		this.setValue(SCRIPT, value);
	}

	//
	public String[] getScript() {
		return (String[])this.getValues(SCRIPT);
	}

	// Add a new element returning its index in the list
	public int addScript(String value) {
		int positionOfNewItem = this.addValue(SCRIPT, value);
		return positionOfNewItem;
	}

	//
	// Remove an element using its reference
	// Returns the index the element had in the list
	//
	public int removeScript(String value) {
		return this.removeValue(SCRIPT, value);
	}

	// This attribute is an array, possibly empty
	public void setScriptSrc(int index, java.lang.String value) {
		// Make sure we've got a place to put this attribute.
		if (size(SCRIPT) == 0) {
			addValue(SCRIPT, "");
		}
		setAttributeValue(SCRIPT, index, "Src", value);
	}

	//
	public java.lang.String getScriptSrc(int index) {
		// If our element does not exist, then the attribute does not exist.
		if (size(SCRIPT) == 0) {
			return null;
		} else {
			return getAttributeValue(SCRIPT, index, "Src");
		}
	}

	// Return the number of properties
	public int sizeScriptSrc() {
		return this.size(SCRIPT);
	}

	// This attribute is an array, possibly empty
	public void setServlet(int index, boolean value) {
		this.setValue(SERVLET, index, (value ? java.lang.Boolean.TRUE : java.lang.Boolean.FALSE));
	}

	//
	public boolean isServlet(int index) {
		Boolean ret = (Boolean)this.getValue(SERVLET, index);
		if (ret == null)
			ret = (Boolean)Common.defaultScalarValue(Common.TYPE_BOOLEAN);
		return ((java.lang.Boolean)ret).booleanValue();
	}

	// Return the number of properties
	public int sizeServlet() {
		return this.size(SERVLET);
	}

	// This attribute is an array, possibly empty
	public void setServlet(boolean[] value) {
		Boolean[] values = null;
		if (value != null)
		{
			values = new Boolean[value.length];
			for (int i=0; i<value.length; i++)
				values[i] = (value[i] ? Boolean.TRUE : Boolean.FALSE);
		}
		this.setValue(SERVLET, values);
	}

	//
	public boolean[] getServlet() {
		boolean[] ret = null;
		Boolean[] values = (Boolean[])this.getValues(SERVLET);
		if (values != null)
		{
			ret = new boolean[values.length];
			for (int i=0; i<values.length; i++)
				ret[i] = values[i].booleanValue();
		}
		return ret;
	}

	// Add a new element returning its index in the list
	public int addServlet(boolean value) {
		int positionOfNewItem = this.addValue(SERVLET, (value ? java.lang.Boolean.TRUE : java.lang.Boolean.FALSE));
		return positionOfNewItem;
	}

	//
	// Remove an element using its reference
	// Returns the index the element had in the list
	//
	public int removeServlet(boolean value) {
		return this.removeValue(SERVLET, (value ? java.lang.Boolean.TRUE : java.lang.Boolean.FALSE));
	}

	//
	// Remove an element using its index
	//
	public void removeServlet(int index) {
		this.removeValue(SERVLET, index);
	}

	// This attribute is an array, possibly empty
	public void setServletPath(int index, java.lang.String value) {
		// Make sure we've got a place to put this attribute.
		if (size(SERVLET) == 0) {
			addValue(SERVLET, java.lang.Boolean.TRUE);
		}
		setValue(SERVLET, index, java.lang.Boolean.TRUE);
		setAttributeValue(SERVLET, index, "Path", value);
	}

	//
	public java.lang.String getServletPath(int index) {
		// If our element does not exist, then the attribute does not exist.
		if (size(SERVLET) == 0) {
			return null;
		} else {
			return getAttributeValue(SERVLET, index, "Path");
		}
	}

	// Return the number of properties
	public int sizeServletPath() {
		return this.size(SERVLET);
	}

	// This attribute is an array, possibly empty
	public void setServletClass(int index, java.lang.String value) {
		// Make sure we've got a place to put this attribute.
		if (size(SERVLET) == 0) {
			addValue(SERVLET, java.lang.Boolean.TRUE);
		}
		setValue(SERVLET, index, java.lang.Boolean.TRUE);
		setAttributeValue(SERVLET, index, "Class", value);
	}

	//
	public java.lang.String getServletClass(int index) {
		// If our element does not exist, then the attribute does not exist.
		if (size(SERVLET) == 0) {
			return null;
		} else {
			return getAttributeValue(SERVLET, index, "Class");
		}
	}

	// Return the number of properties
	public int sizeServletClass() {
		return this.size(SERVLET);
	}

	// This attribute is an array, possibly empty
	public void setReplaceWith(int index, ReplaceWith value) {
		this.setValue(REPLACE_WITH, index, value);
	}

	//
	public ReplaceWith getReplaceWith(int index) {
		return (ReplaceWith)this.getValue(REPLACE_WITH, index);
	}

	// Return the number of properties
	public int sizeReplaceWith() {
		return this.size(REPLACE_WITH);
	}

	// This attribute is an array, possibly empty
	public void setReplaceWith(ReplaceWith[] value) {
		this.setValue(REPLACE_WITH, value);
	}

	//
	public ReplaceWith[] getReplaceWith() {
		return (ReplaceWith[])this.getValues(REPLACE_WITH);
	}

	// Add a new element returning its index in the list
	public int addReplaceWith(com.nanosn.netbeans.gwtxml.gwtmodule.ReplaceWith value) {
		int positionOfNewItem = this.addValue(REPLACE_WITH, value);
		return positionOfNewItem;
	}

	//
	// Remove an element using its reference
	// Returns the index the element had in the list
	//
	public int removeReplaceWith(com.nanosn.netbeans.gwtxml.gwtmodule.ReplaceWith value) {
		return this.removeValue(REPLACE_WITH, value);
	}

	// This attribute is an array, possibly empty
	public void setGenerateWith(int index, GenerateWith value) {
		this.setValue(GENERATE_WITH, index, value);
	}

	//
	public GenerateWith getGenerateWith(int index) {
		return (GenerateWith)this.getValue(GENERATE_WITH, index);
	}

	// Return the number of properties
	public int sizeGenerateWith() {
		return this.size(GENERATE_WITH);
	}

	// This attribute is an array, possibly empty
	public void setGenerateWith(GenerateWith[] value) {
		this.setValue(GENERATE_WITH, value);
	}

	//
	public GenerateWith[] getGenerateWith() {
		return (GenerateWith[])this.getValues(GENERATE_WITH);
	}

	// Add a new element returning its index in the list
	public int addGenerateWith(com.nanosn.netbeans.gwtxml.gwtmodule.GenerateWith value) {
		int positionOfNewItem = this.addValue(GENERATE_WITH, value);
		return positionOfNewItem;
	}

	//
	// Remove an element using its reference
	// Returns the index the element had in the list
	//
	public int removeGenerateWith(com.nanosn.netbeans.gwtxml.gwtmodule.GenerateWith value) {
		return this.removeValue(GENERATE_WITH, value);
	}

	// This attribute is an array, possibly empty
	public void setDefineProperty(int index, boolean value) {
		this.setValue(DEFINE_PROPERTY, index, (value ? java.lang.Boolean.TRUE : java.lang.Boolean.FALSE));
	}

	//
	public boolean isDefineProperty(int index) {
		Boolean ret = (Boolean)this.getValue(DEFINE_PROPERTY, index);
		if (ret == null)
			ret = (Boolean)Common.defaultScalarValue(Common.TYPE_BOOLEAN);
		return ((java.lang.Boolean)ret).booleanValue();
	}

	// Return the number of properties
	public int sizeDefineProperty() {
		return this.size(DEFINE_PROPERTY);
	}

	// This attribute is an array, possibly empty
	public void setDefineProperty(boolean[] value) {
		Boolean[] values = null;
		if (value != null)
		{
			values = new Boolean[value.length];
			for (int i=0; i<value.length; i++)
				values[i] = (value[i] ? Boolean.TRUE : Boolean.FALSE);
		}
		this.setValue(DEFINE_PROPERTY, values);
	}

	//
	public boolean[] getDefineProperty() {
		boolean[] ret = null;
		Boolean[] values = (Boolean[])this.getValues(DEFINE_PROPERTY);
		if (values != null)
		{
			ret = new boolean[values.length];
			for (int i=0; i<values.length; i++)
				ret[i] = values[i].booleanValue();
		}
		return ret;
	}

	// Add a new element returning its index in the list
	public int addDefineProperty(boolean value) {
		int positionOfNewItem = this.addValue(DEFINE_PROPERTY, (value ? java.lang.Boolean.TRUE : java.lang.Boolean.FALSE));
		return positionOfNewItem;
	}

	//
	// Remove an element using its reference
	// Returns the index the element had in the list
	//
	public int removeDefineProperty(boolean value) {
		return this.removeValue(DEFINE_PROPERTY, (value ? java.lang.Boolean.TRUE : java.lang.Boolean.FALSE));
	}

	//
	// Remove an element using its index
	//
	public void removeDefineProperty(int index) {
		this.removeValue(DEFINE_PROPERTY, index);
	}

	// This attribute is an array, possibly empty
	public void setDefinePropertyName(int index, java.lang.String value) {
		// Make sure we've got a place to put this attribute.
		if (size(DEFINE_PROPERTY) == 0) {
			addValue(DEFINE_PROPERTY, java.lang.Boolean.TRUE);
		}
		setValue(DEFINE_PROPERTY, index, java.lang.Boolean.TRUE);
		setAttributeValue(DEFINE_PROPERTY, index, "Name", value);
	}

	//
	public java.lang.String getDefinePropertyName(int index) {
		// If our element does not exist, then the attribute does not exist.
		if (size(DEFINE_PROPERTY) == 0) {
			return null;
		} else {
			return getAttributeValue(DEFINE_PROPERTY, index, "Name");
		}
	}

	// Return the number of properties
	public int sizeDefinePropertyName() {
		return this.size(DEFINE_PROPERTY);
	}

	// This attribute is an array, possibly empty
	public void setDefinePropertyValues(int index, java.lang.String value) {
		// Make sure we've got a place to put this attribute.
		if (size(DEFINE_PROPERTY) == 0) {
			addValue(DEFINE_PROPERTY, java.lang.Boolean.TRUE);
		}
		setValue(DEFINE_PROPERTY, index, java.lang.Boolean.TRUE);
		setAttributeValue(DEFINE_PROPERTY, index, "Values", value);
	}

	//
	public java.lang.String getDefinePropertyValues(int index) {
		// If our element does not exist, then the attribute does not exist.
		if (size(DEFINE_PROPERTY) == 0) {
			return null;
		} else {
			return getAttributeValue(DEFINE_PROPERTY, index, "Values");
		}
	}

	// Return the number of properties
	public int sizeDefinePropertyValues() {
		return this.size(DEFINE_PROPERTY);
	}

	// This attribute is an array, possibly empty
	public void setExtendProperty(int index, boolean value) {
		this.setValue(EXTEND_PROPERTY, index, (value ? java.lang.Boolean.TRUE : java.lang.Boolean.FALSE));
	}

	//
	public boolean isExtendProperty(int index) {
		Boolean ret = (Boolean)this.getValue(EXTEND_PROPERTY, index);
		if (ret == null)
			ret = (Boolean)Common.defaultScalarValue(Common.TYPE_BOOLEAN);
		return ((java.lang.Boolean)ret).booleanValue();
	}

	// Return the number of properties
	public int sizeExtendProperty() {
		return this.size(EXTEND_PROPERTY);
	}

	// This attribute is an array, possibly empty
	public void setExtendProperty(boolean[] value) {
		Boolean[] values = null;
		if (value != null)
		{
			values = new Boolean[value.length];
			for (int i=0; i<value.length; i++)
				values[i] = (value[i] ? Boolean.TRUE : Boolean.FALSE);
		}
		this.setValue(EXTEND_PROPERTY, values);
	}

	//
	public boolean[] getExtendProperty() {
		boolean[] ret = null;
		Boolean[] values = (Boolean[])this.getValues(EXTEND_PROPERTY);
		if (values != null)
		{
			ret = new boolean[values.length];
			for (int i=0; i<values.length; i++)
				ret[i] = values[i].booleanValue();
		}
		return ret;
	}

	// Add a new element returning its index in the list
	public int addExtendProperty(boolean value) {
		int positionOfNewItem = this.addValue(EXTEND_PROPERTY, (value ? java.lang.Boolean.TRUE : java.lang.Boolean.FALSE));
		return positionOfNewItem;
	}

	//
	// Remove an element using its reference
	// Returns the index the element had in the list
	//
	public int removeExtendProperty(boolean value) {
		return this.removeValue(EXTEND_PROPERTY, (value ? java.lang.Boolean.TRUE : java.lang.Boolean.FALSE));
	}

	//
	// Remove an element using its index
	//
	public void removeExtendProperty(int index) {
		this.removeValue(EXTEND_PROPERTY, index);
	}

	// This attribute is an array, possibly empty
	public void setExtendPropertyName(int index, java.lang.String value) {
		// Make sure we've got a place to put this attribute.
		if (size(EXTEND_PROPERTY) == 0) {
			addValue(EXTEND_PROPERTY, java.lang.Boolean.TRUE);
		}
		setValue(EXTEND_PROPERTY, index, java.lang.Boolean.TRUE);
		setAttributeValue(EXTEND_PROPERTY, index, "Name", value);
	}

	//
	public java.lang.String getExtendPropertyName(int index) {
		// If our element does not exist, then the attribute does not exist.
		if (size(EXTEND_PROPERTY) == 0) {
			return null;
		} else {
			return getAttributeValue(EXTEND_PROPERTY, index, "Name");
		}
	}

	// Return the number of properties
	public int sizeExtendPropertyName() {
		return this.size(EXTEND_PROPERTY);
	}

	// This attribute is an array, possibly empty
	public void setExtendPropertyValues(int index, java.lang.String value) {
		// Make sure we've got a place to put this attribute.
		if (size(EXTEND_PROPERTY) == 0) {
			addValue(EXTEND_PROPERTY, java.lang.Boolean.TRUE);
		}
		setValue(EXTEND_PROPERTY, index, java.lang.Boolean.TRUE);
		setAttributeValue(EXTEND_PROPERTY, index, "Values", value);
	}

	//
	public java.lang.String getExtendPropertyValues(int index) {
		// If our element does not exist, then the attribute does not exist.
		if (size(EXTEND_PROPERTY) == 0) {
			return null;
		} else {
			return getAttributeValue(EXTEND_PROPERTY, index, "Values");
		}
	}

	// Return the number of properties
	public int sizeExtendPropertyValues() {
		return this.size(EXTEND_PROPERTY);
	}

	// This attribute is an array, possibly empty
	public void setSetProperty(int index, boolean value) {
		this.setValue(SET_PROPERTY, index, (value ? java.lang.Boolean.TRUE : java.lang.Boolean.FALSE));
	}

	//
	public boolean isSetProperty(int index) {
		Boolean ret = (Boolean)this.getValue(SET_PROPERTY, index);
		if (ret == null)
			ret = (Boolean)Common.defaultScalarValue(Common.TYPE_BOOLEAN);
		return ((java.lang.Boolean)ret).booleanValue();
	}

	// Return the number of properties
	public int sizeSetProperty() {
		return this.size(SET_PROPERTY);
	}

	// This attribute is an array, possibly empty
	public void setSetProperty(boolean[] value) {
		Boolean[] values = null;
		if (value != null)
		{
			values = new Boolean[value.length];
			for (int i=0; i<value.length; i++)
				values[i] = (value[i] ? Boolean.TRUE : Boolean.FALSE);
		}
		this.setValue(SET_PROPERTY, values);
	}

	//
	public boolean[] getSetProperty() {
		boolean[] ret = null;
		Boolean[] values = (Boolean[])this.getValues(SET_PROPERTY);
		if (values != null)
		{
			ret = new boolean[values.length];
			for (int i=0; i<values.length; i++)
				ret[i] = values[i].booleanValue();
		}
		return ret;
	}

	// Add a new element returning its index in the list
	public int addSetProperty(boolean value) {
		int positionOfNewItem = this.addValue(SET_PROPERTY, (value ? java.lang.Boolean.TRUE : java.lang.Boolean.FALSE));
		return positionOfNewItem;
	}

	//
	// Remove an element using its reference
	// Returns the index the element had in the list
	//
	public int removeSetProperty(boolean value) {
		return this.removeValue(SET_PROPERTY, (value ? java.lang.Boolean.TRUE : java.lang.Boolean.FALSE));
	}

	//
	// Remove an element using its index
	//
	public void removeSetProperty(int index) {
		this.removeValue(SET_PROPERTY, index);
	}

	// This attribute is an array, possibly empty
	public void setSetPropertyName(int index, java.lang.String value) {
		// Make sure we've got a place to put this attribute.
		if (size(SET_PROPERTY) == 0) {
			addValue(SET_PROPERTY, java.lang.Boolean.TRUE);
		}
		setValue(SET_PROPERTY, index, java.lang.Boolean.TRUE);
		setAttributeValue(SET_PROPERTY, index, "Name", value);
	}

	//
	public java.lang.String getSetPropertyName(int index) {
		// If our element does not exist, then the attribute does not exist.
		if (size(SET_PROPERTY) == 0) {
			return null;
		} else {
			return getAttributeValue(SET_PROPERTY, index, "Name");
		}
	}

	// Return the number of properties
	public int sizeSetPropertyName() {
		return this.size(SET_PROPERTY);
	}

	// This attribute is an array, possibly empty
	public void setSetPropertyValue(int index, java.lang.String value) {
		// Make sure we've got a place to put this attribute.
		if (size(SET_PROPERTY) == 0) {
			addValue(SET_PROPERTY, java.lang.Boolean.TRUE);
		}
		setValue(SET_PROPERTY, index, java.lang.Boolean.TRUE);
		setAttributeValue(SET_PROPERTY, index, "Value", value);
	}

	//
	public java.lang.String getSetPropertyValue(int index) {
		// If our element does not exist, then the attribute does not exist.
		if (size(SET_PROPERTY) == 0) {
			return null;
		} else {
			return getAttributeValue(SET_PROPERTY, index, "Value");
		}
	}

	// Return the number of properties
	public int sizeSetPropertyValue() {
		return this.size(SET_PROPERTY);
	}

	// This attribute is an array, possibly empty
	public void setSetConfigurationProperty(int index, boolean value) {
		this.setValue(SET_CONFIGURATION_PROPERTY, index, (value ? java.lang.Boolean.TRUE : java.lang.Boolean.FALSE));
	}

	//
	public boolean isSetConfigurationProperty(int index) {
		Boolean ret = (Boolean)this.getValue(SET_CONFIGURATION_PROPERTY, index);
		if (ret == null)
			ret = (Boolean)Common.defaultScalarValue(Common.TYPE_BOOLEAN);
		return ((java.lang.Boolean)ret).booleanValue();
	}

	// Return the number of properties
	public int sizeSetConfigurationProperty() {
		return this.size(SET_CONFIGURATION_PROPERTY);
	}

	// This attribute is an array, possibly empty
	public void setSetConfigurationProperty(boolean[] value) {
		Boolean[] values = null;
		if (value != null)
		{
			values = new Boolean[value.length];
			for (int i=0; i<value.length; i++)
				values[i] = (value[i] ? Boolean.TRUE : Boolean.FALSE);
		}
		this.setValue(SET_CONFIGURATION_PROPERTY, values);
	}

	//
	public boolean[] getSetConfigurationProperty() {
		boolean[] ret = null;
		Boolean[] values = (Boolean[])this.getValues(SET_CONFIGURATION_PROPERTY);
		if (values != null)
		{
			ret = new boolean[values.length];
			for (int i=0; i<values.length; i++)
				ret[i] = values[i].booleanValue();
		}
		return ret;
	}

	// Add a new element returning its index in the list
	public int addSetConfigurationProperty(boolean value) {
		int positionOfNewItem = this.addValue(SET_CONFIGURATION_PROPERTY, (value ? java.lang.Boolean.TRUE : java.lang.Boolean.FALSE));
		return positionOfNewItem;
	}

	//
	// Remove an element using its reference
	// Returns the index the element had in the list
	//
	public int removeSetConfigurationProperty(boolean value) {
		return this.removeValue(SET_CONFIGURATION_PROPERTY, (value ? java.lang.Boolean.TRUE : java.lang.Boolean.FALSE));
	}

	//
	// Remove an element using its index
	//
	public void removeSetConfigurationProperty(int index) {
		this.removeValue(SET_CONFIGURATION_PROPERTY, index);
	}

	// This attribute is an array, possibly empty
	public void setSetConfigurationPropertyName(int index, java.lang.String value) {
		// Make sure we've got a place to put this attribute.
		if (size(SET_CONFIGURATION_PROPERTY) == 0) {
			addValue(SET_CONFIGURATION_PROPERTY, java.lang.Boolean.TRUE);
		}
		setValue(SET_CONFIGURATION_PROPERTY, index, java.lang.Boolean.TRUE);
		setAttributeValue(SET_CONFIGURATION_PROPERTY, index, "Name", value);
	}

	//
	public java.lang.String getSetConfigurationPropertyName(int index) {
		// If our element does not exist, then the attribute does not exist.
		if (size(SET_CONFIGURATION_PROPERTY) == 0) {
			return null;
		} else {
			return getAttributeValue(SET_CONFIGURATION_PROPERTY, index, "Name");
		}
	}

	// Return the number of properties
	public int sizeSetConfigurationPropertyName() {
		return this.size(SET_CONFIGURATION_PROPERTY);
	}

	// This attribute is an array, possibly empty
	public void setSetConfigurationPropertyValue(int index, java.lang.String value) {
		// Make sure we've got a place to put this attribute.
		if (size(SET_CONFIGURATION_PROPERTY) == 0) {
			addValue(SET_CONFIGURATION_PROPERTY, java.lang.Boolean.TRUE);
		}
		setValue(SET_CONFIGURATION_PROPERTY, index, java.lang.Boolean.TRUE);
		setAttributeValue(SET_CONFIGURATION_PROPERTY, index, "Value", value);
	}

	//
	public java.lang.String getSetConfigurationPropertyValue(int index) {
		// If our element does not exist, then the attribute does not exist.
		if (size(SET_CONFIGURATION_PROPERTY) == 0) {
			return null;
		} else {
			return getAttributeValue(SET_CONFIGURATION_PROPERTY, index, "Value");
		}
	}

	// Return the number of properties
	public int sizeSetConfigurationPropertyValue() {
		return this.size(SET_CONFIGURATION_PROPERTY);
	}

	// This attribute is an array, possibly empty
	public void setPropertyProvider(int index, String value) {
		this.setValue(PROPERTY_PROVIDER, index, value);
	}

	//
	public String getPropertyProvider(int index) {
		return (String)this.getValue(PROPERTY_PROVIDER, index);
	}

	// Return the number of properties
	public int sizePropertyProvider() {
		return this.size(PROPERTY_PROVIDER);
	}

	// This attribute is an array, possibly empty
	public void setPropertyProvider(String[] value) {
		this.setValue(PROPERTY_PROVIDER, value);
	}

	//
	public String[] getPropertyProvider() {
		return (String[])this.getValues(PROPERTY_PROVIDER);
	}

	// Add a new element returning its index in the list
	public int addPropertyProvider(String value) {
		int positionOfNewItem = this.addValue(PROPERTY_PROVIDER, value);
		return positionOfNewItem;
	}

	//
	// Remove an element using its reference
	// Returns the index the element had in the list
	//
	public int removePropertyProvider(String value) {
		return this.removeValue(PROPERTY_PROVIDER, value);
	}

	// This attribute is an array, possibly empty
	public void setPropertyProviderName(int index, java.lang.String value) {
		// Make sure we've got a place to put this attribute.
		if (size(PROPERTY_PROVIDER) == 0) {
			addValue(PROPERTY_PROVIDER, "");
		}
		setAttributeValue(PROPERTY_PROVIDER, index, "Name", value);
	}

	//
	public java.lang.String getPropertyProviderName(int index) {
		// If our element does not exist, then the attribute does not exist.
		if (size(PROPERTY_PROVIDER) == 0) {
			return null;
		} else {
			return getAttributeValue(PROPERTY_PROVIDER, index, "Name");
		}
	}

	// Return the number of properties
	public int sizePropertyProviderName() {
		return this.size(PROPERTY_PROVIDER);
	}

	// This attribute is an array, possibly empty
	public void setDefineLinker(int index, boolean value) {
		this.setValue(DEFINE_LINKER, index, (value ? java.lang.Boolean.TRUE : java.lang.Boolean.FALSE));
	}

	//
	public boolean isDefineLinker(int index) {
		Boolean ret = (Boolean)this.getValue(DEFINE_LINKER, index);
		if (ret == null)
			ret = (Boolean)Common.defaultScalarValue(Common.TYPE_BOOLEAN);
		return ((java.lang.Boolean)ret).booleanValue();
	}

	// Return the number of properties
	public int sizeDefineLinker() {
		return this.size(DEFINE_LINKER);
	}

	// This attribute is an array, possibly empty
	public void setDefineLinker(boolean[] value) {
		Boolean[] values = null;
		if (value != null)
		{
			values = new Boolean[value.length];
			for (int i=0; i<value.length; i++)
				values[i] = (value[i] ? Boolean.TRUE : Boolean.FALSE);
		}
		this.setValue(DEFINE_LINKER, values);
	}

	//
	public boolean[] getDefineLinker() {
		boolean[] ret = null;
		Boolean[] values = (Boolean[])this.getValues(DEFINE_LINKER);
		if (values != null)
		{
			ret = new boolean[values.length];
			for (int i=0; i<values.length; i++)
				ret[i] = values[i].booleanValue();
		}
		return ret;
	}

	// Add a new element returning its index in the list
	public int addDefineLinker(boolean value) {
		int positionOfNewItem = this.addValue(DEFINE_LINKER, (value ? java.lang.Boolean.TRUE : java.lang.Boolean.FALSE));
		return positionOfNewItem;
	}

	//
	// Remove an element using its reference
	// Returns the index the element had in the list
	//
	public int removeDefineLinker(boolean value) {
		return this.removeValue(DEFINE_LINKER, (value ? java.lang.Boolean.TRUE : java.lang.Boolean.FALSE));
	}

	//
	// Remove an element using its index
	//
	public void removeDefineLinker(int index) {
		this.removeValue(DEFINE_LINKER, index);
	}

	// This attribute is an array, possibly empty
	public void setDefineLinkerClass(int index, java.lang.String value) {
		// Make sure we've got a place to put this attribute.
		if (size(DEFINE_LINKER) == 0) {
			addValue(DEFINE_LINKER, java.lang.Boolean.TRUE);
		}
		setValue(DEFINE_LINKER, index, java.lang.Boolean.TRUE);
		setAttributeValue(DEFINE_LINKER, index, "Class", value);
	}

	//
	public java.lang.String getDefineLinkerClass(int index) {
		// If our element does not exist, then the attribute does not exist.
		if (size(DEFINE_LINKER) == 0) {
			return null;
		} else {
			return getAttributeValue(DEFINE_LINKER, index, "Class");
		}
	}

	// Return the number of properties
	public int sizeDefineLinkerClass() {
		return this.size(DEFINE_LINKER);
	}

	// This attribute is an array, possibly empty
	public void setDefineLinkerName(int index, java.lang.String value) {
		// Make sure we've got a place to put this attribute.
		if (size(DEFINE_LINKER) == 0) {
			addValue(DEFINE_LINKER, java.lang.Boolean.TRUE);
		}
		setValue(DEFINE_LINKER, index, java.lang.Boolean.TRUE);
		setAttributeValue(DEFINE_LINKER, index, "Name", value);
	}

	//
	public java.lang.String getDefineLinkerName(int index) {
		// If our element does not exist, then the attribute does not exist.
		if (size(DEFINE_LINKER) == 0) {
			return null;
		} else {
			return getAttributeValue(DEFINE_LINKER, index, "Name");
		}
	}

	// Return the number of properties
	public int sizeDefineLinkerName() {
		return this.size(DEFINE_LINKER);
	}

	// This attribute is an array, possibly empty
	public void setAddLinker(int index, boolean value) {
		this.setValue(ADD_LINKER, index, (value ? java.lang.Boolean.TRUE : java.lang.Boolean.FALSE));
	}

	//
	public boolean isAddLinker(int index) {
		Boolean ret = (Boolean)this.getValue(ADD_LINKER, index);
		if (ret == null)
			ret = (Boolean)Common.defaultScalarValue(Common.TYPE_BOOLEAN);
		return ((java.lang.Boolean)ret).booleanValue();
	}

	// Return the number of properties
	public int sizeAddLinker() {
		return this.size(ADD_LINKER);
	}

	// This attribute is an array, possibly empty
	public void setAddLinker(boolean[] value) {
		Boolean[] values = null;
		if (value != null)
		{
			values = new Boolean[value.length];
			for (int i=0; i<value.length; i++)
				values[i] = (value[i] ? Boolean.TRUE : Boolean.FALSE);
		}
		this.setValue(ADD_LINKER, values);
	}

	//
	public boolean[] getAddLinker() {
		boolean[] ret = null;
		Boolean[] values = (Boolean[])this.getValues(ADD_LINKER);
		if (values != null)
		{
			ret = new boolean[values.length];
			for (int i=0; i<values.length; i++)
				ret[i] = values[i].booleanValue();
		}
		return ret;
	}

	// Add a new element returning its index in the list
	public int addAddLinker(boolean value) {
		int positionOfNewItem = this.addValue(ADD_LINKER, (value ? java.lang.Boolean.TRUE : java.lang.Boolean.FALSE));
		return positionOfNewItem;
	}

	//
	// Remove an element using its reference
	// Returns the index the element had in the list
	//
	public int removeAddLinker(boolean value) {
		return this.removeValue(ADD_LINKER, (value ? java.lang.Boolean.TRUE : java.lang.Boolean.FALSE));
	}

	//
	// Remove an element using its index
	//
	public void removeAddLinker(int index) {
		this.removeValue(ADD_LINKER, index);
	}

	// This attribute is an array, possibly empty
	public void setAddLinkerName(int index, java.lang.String value) {
		// Make sure we've got a place to put this attribute.
		if (size(ADD_LINKER) == 0) {
			addValue(ADD_LINKER, java.lang.Boolean.TRUE);
		}
		setValue(ADD_LINKER, index, java.lang.Boolean.TRUE);
		setAttributeValue(ADD_LINKER, index, "Name", value);
	}

	//
	public java.lang.String getAddLinkerName(int index) {
		// If our element does not exist, then the attribute does not exist.
		if (size(ADD_LINKER) == 0) {
			return null;
		} else {
			return getAttributeValue(ADD_LINKER, index, "Name");
		}
	}

	// Return the number of properties
	public int sizeAddLinkerName() {
		return this.size(ADD_LINKER);
	}

	/**
	 * Create a new bean using it's default constructor.
	 * This does not add it to any bean graph.
	 */
	public Source newSource() {
		return new Source();
	}

	/**
	 * Create a new bean using it's default constructor.
	 * This does not add it to any bean graph.
	 */
	public Public newPublic() {
		return new Public();
	}

	/**
	 * Create a new bean using it's default constructor.
	 * This does not add it to any bean graph.
	 */
	public SuperSource newSuperSource() {
		return new SuperSource();
	}

	/**
	 * Create a new bean using it's default constructor.
	 * This does not add it to any bean graph.
	 */
	public ReplaceWith newReplaceWith() {
		return new ReplaceWith();
	}

	/**
	 * Create a new bean using it's default constructor.
	 * This does not add it to any bean graph.
	 */
	public GenerateWith newGenerateWith() {
		return new GenerateWith();
	}

	//
	public static void addComparator(org.netbeans.modules.schema2beans.BeanComparator c) {
		comparators.add(c);
	}

	//
	public static void removeComparator(org.netbeans.modules.schema2beans.BeanComparator c) {
		comparators.remove(c);
	}
	//
	// This method returns the root of the bean graph
	// Each call creates a new bean graph from the specified DOM graph
	//
	public static Module createGraph(org.w3c.dom.Node doc) {
		return new Module(doc, Common.NO_DEFAULT_VALUES);
	}

	public static Module createGraph(java.io.File f) throws java.io.IOException {
		java.io.InputStream in = new java.io.FileInputStream(f);
		try {
			return createGraph(in, false);
		} finally {
			in.close();
		}
	}

	public static Module createGraph(java.io.InputStream in) {
		return createGraph(in, false);
	}

	public static Module createGraph(java.io.InputStream in, boolean validate) {
		try {
			Document doc = GraphManager.createXmlDocument(in, validate);
			return createGraph(doc);
		}
		catch (Exception t) {
			throw new RuntimeException(Common.getMessage(
				"DOMGraphCreateFailed_msg",
				t));
		}
	}

	//
	// This method returns the root for a new empty bean graph
	//
	public static Module createGraph() {
		return new Module();
	}

	public void validate() throws org.netbeans.modules.schema2beans.ValidateException {
	}

	// Special serializer: output XML as serialization
	private void writeObject(java.io.ObjectOutputStream out) throws java.io.IOException{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		write(baos);
		String str = baos.toString();;
		// System.out.println("str='"+str+"'");
		out.writeUTF(str);
	}
	// Special deserializer: read XML as deserialization
	private void readObject(java.io.ObjectInputStream in) throws java.io.IOException, ClassNotFoundException{
		try{
			init(comparators, runtimeVersion);
			String strDocument = in.readUTF();
			// System.out.println("strDocument='"+strDocument+"'");
			ByteArrayInputStream bais = new ByteArrayInputStream(strDocument.getBytes());
			Document doc = GraphManager.createXmlDocument(bais, false);
			initOptions(Common.NO_DEFAULT_VALUES);
			initFromNode(doc, Common.NO_DEFAULT_VALUES);
		}
		catch (Schema2BeansException e) {
			throw new RuntimeException(e);
		}
	}

	public void _setSchemaLocation(String location) {
		if (beanProp().getAttrProp("xsi:schemaLocation", true) == null) {
			createAttribute("xmlns:xsi", "xmlns:xsi", AttrProp.CDATA | AttrProp.IMPLIED, null, "http://www.w3.org/2001/XMLSchema-instance");
			setAttributeValue("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
			createAttribute("xsi:schemaLocation", "xsi:schemaLocation", AttrProp.CDATA | AttrProp.IMPLIED, null, location);
		}
		setAttributeValue("xsi:schemaLocation", location);
	}

	public String _getSchemaLocation() {
		if (beanProp().getAttrProp("xsi:schemaLocation", true) == null) {
			createAttribute("xmlns:xsi", "xmlns:xsi", AttrProp.CDATA | AttrProp.IMPLIED, null, "http://www.w3.org/2001/XMLSchema-instance");
			setAttributeValue("xmlns:xsi", "http://www.w3.org/2001/XMLSchema-instance");
			createAttribute("xsi:schemaLocation", "xsi:schemaLocation", AttrProp.CDATA | AttrProp.IMPLIED, null, null);
		}
		return getAttributeValue("xsi:schemaLocation");
	}

	// Dump the content of this bean returning it as a String
	public void dump(StringBuffer str, String indent){
		String s;
		Object o;
		org.netbeans.modules.schema2beans.BaseBean n;
		str.append(indent);
		str.append("Inherits["+this.sizeInherits()+"]");	// NOI18N
		for(int i=0; i<this.sizeInherits(); i++)
		{
			str.append(indent+"\t");
			str.append("#"+i+":");
			str.append(indent+"\t");	// NOI18N
			str.append((this.isInherits(i)?"true":"false"));
			this.dumpAttributes(INHERITS, i, str, indent);
		}

		str.append(indent);
		str.append("Source["+this.sizeSource()+"]");	// NOI18N
		for(int i=0; i<this.sizeSource(); i++)
		{
			str.append(indent+"\t");
			str.append("#"+i+":");
			n = (org.netbeans.modules.schema2beans.BaseBean) this.getSource(i);
			if (n != null)
				n.dump(str, indent + "\t");	// NOI18N
			else
				str.append(indent+"\tnull");	// NOI18N
			this.dumpAttributes(SOURCE, i, str, indent);
		}

		str.append(indent);
		str.append("Public["+this.sizePublic()+"]");	// NOI18N
		for(int i=0; i<this.sizePublic(); i++)
		{
			str.append(indent+"\t");
			str.append("#"+i+":");
			n = (org.netbeans.modules.schema2beans.BaseBean) this.getPublic(i);
			if (n != null)
				n.dump(str, indent + "\t");	// NOI18N
			else
				str.append(indent+"\tnull");	// NOI18N
			this.dumpAttributes(PUBLIC, i, str, indent);
		}

		str.append(indent);
		str.append("SuperSource["+this.sizeSuperSource()+"]");	// NOI18N
		for(int i=0; i<this.sizeSuperSource(); i++)
		{
			str.append(indent+"\t");
			str.append("#"+i+":");
			n = (org.netbeans.modules.schema2beans.BaseBean) this.getSuperSource(i);
			if (n != null)
				n.dump(str, indent + "\t");	// NOI18N
			else
				str.append(indent+"\tnull");	// NOI18N
			this.dumpAttributes(SUPER_SOURCE, i, str, indent);
		}

		str.append(indent);
		str.append("EntryPoint["+this.sizeEntryPoint()+"]");	// NOI18N
		for(int i=0; i<this.sizeEntryPoint(); i++)
		{
			str.append(indent+"\t");
			str.append("#"+i+":");
			str.append(indent+"\t");	// NOI18N
			str.append((this.isEntryPoint(i)?"true":"false"));
			this.dumpAttributes(ENTRY_POINT, i, str, indent);
		}

		str.append(indent);
		str.append("Stylesheet["+this.sizeStylesheet()+"]");	// NOI18N
		for(int i=0; i<this.sizeStylesheet(); i++)
		{
			str.append(indent+"\t");
			str.append("#"+i+":");
			str.append(indent+"\t");	// NOI18N
			str.append((this.isStylesheet(i)?"true":"false"));
			this.dumpAttributes(STYLESHEET, i, str, indent);
		}

		str.append(indent);
		str.append("Script["+this.sizeScript()+"]");	// NOI18N
		for(int i=0; i<this.sizeScript(); i++)
		{
			str.append(indent+"\t");
			str.append("#"+i+":");
			str.append(indent+"\t");	// NOI18N
			str.append("<");	// NOI18N
			o = this.getScript(i);
			str.append((o==null?"null":o.toString().trim()));	// NOI18N
			str.append(">\n");	// NOI18N
			this.dumpAttributes(SCRIPT, i, str, indent);
		}

		str.append(indent);
		str.append("Servlet["+this.sizeServlet()+"]");	// NOI18N
		for(int i=0; i<this.sizeServlet(); i++)
		{
			str.append(indent+"\t");
			str.append("#"+i+":");
			str.append(indent+"\t");	// NOI18N
			str.append((this.isServlet(i)?"true":"false"));
			this.dumpAttributes(SERVLET, i, str, indent);
		}

		str.append(indent);
		str.append("ReplaceWith["+this.sizeReplaceWith()+"]");	// NOI18N
		for(int i=0; i<this.sizeReplaceWith(); i++)
		{
			str.append(indent+"\t");
			str.append("#"+i+":");
			n = (org.netbeans.modules.schema2beans.BaseBean) this.getReplaceWith(i);
			if (n != null)
				n.dump(str, indent + "\t");	// NOI18N
			else
				str.append(indent+"\tnull");	// NOI18N
			this.dumpAttributes(REPLACE_WITH, i, str, indent);
		}

		str.append(indent);
		str.append("GenerateWith["+this.sizeGenerateWith()+"]");	// NOI18N
		for(int i=0; i<this.sizeGenerateWith(); i++)
		{
			str.append(indent+"\t");
			str.append("#"+i+":");
			n = (org.netbeans.modules.schema2beans.BaseBean) this.getGenerateWith(i);
			if (n != null)
				n.dump(str, indent + "\t");	// NOI18N
			else
				str.append(indent+"\tnull");	// NOI18N
			this.dumpAttributes(GENERATE_WITH, i, str, indent);
		}

		str.append(indent);
		str.append("DefineProperty["+this.sizeDefineProperty()+"]");	// NOI18N
		for(int i=0; i<this.sizeDefineProperty(); i++)
		{
			str.append(indent+"\t");
			str.append("#"+i+":");
			str.append(indent+"\t");	// NOI18N
			str.append((this.isDefineProperty(i)?"true":"false"));
			this.dumpAttributes(DEFINE_PROPERTY, i, str, indent);
		}

		str.append(indent);
		str.append("ExtendProperty["+this.sizeExtendProperty()+"]");	// NOI18N
		for(int i=0; i<this.sizeExtendProperty(); i++)
		{
			str.append(indent+"\t");
			str.append("#"+i+":");
			str.append(indent+"\t");	// NOI18N
			str.append((this.isExtendProperty(i)?"true":"false"));
			this.dumpAttributes(EXTEND_PROPERTY, i, str, indent);
		}

		str.append(indent);
		str.append("SetProperty["+this.sizeSetProperty()+"]");	// NOI18N
		for(int i=0; i<this.sizeSetProperty(); i++)
		{
			str.append(indent+"\t");
			str.append("#"+i+":");
			str.append(indent+"\t");	// NOI18N
			str.append((this.isSetProperty(i)?"true":"false"));
			this.dumpAttributes(SET_PROPERTY, i, str, indent);
		}

		str.append(indent);
		str.append("SetConfigurationProperty["+this.sizeSetConfigurationProperty()+"]");	// NOI18N
		for(int i=0; i<this.sizeSetConfigurationProperty(); i++)
		{
			str.append(indent+"\t");
			str.append("#"+i+":");
			str.append(indent+"\t");	// NOI18N
			str.append((this.isSetConfigurationProperty(i)?"true":"false"));
			this.dumpAttributes(SET_CONFIGURATION_PROPERTY, i, str, indent);
		}

		str.append(indent);
		str.append("PropertyProvider["+this.sizePropertyProvider()+"]");	// NOI18N
		for(int i=0; i<this.sizePropertyProvider(); i++)
		{
			str.append(indent+"\t");
			str.append("#"+i+":");
			str.append(indent+"\t");	// NOI18N
			str.append("<");	// NOI18N
			o = this.getPropertyProvider(i);
			str.append((o==null?"null":o.toString().trim()));	// NOI18N
			str.append(">\n");	// NOI18N
			this.dumpAttributes(PROPERTY_PROVIDER, i, str, indent);
		}

		str.append(indent);
		str.append("DefineLinker["+this.sizeDefineLinker()+"]");	// NOI18N
		for(int i=0; i<this.sizeDefineLinker(); i++)
		{
			str.append(indent+"\t");
			str.append("#"+i+":");
			str.append(indent+"\t");	// NOI18N
			str.append((this.isDefineLinker(i)?"true":"false"));
			this.dumpAttributes(DEFINE_LINKER, i, str, indent);
		}

		str.append(indent);
		str.append("AddLinker["+this.sizeAddLinker()+"]");	// NOI18N
		for(int i=0; i<this.sizeAddLinker(); i++)
		{
			str.append(indent+"\t");
			str.append("#"+i+":");
			str.append(indent+"\t");	// NOI18N
			str.append((this.isAddLinker(i)?"true":"false"));
			this.dumpAttributes(ADD_LINKER, i, str, indent);
		}

	}
	public String dumpBeanNode(){
		StringBuffer str = new StringBuffer();
		str.append("Module\n");	// NOI18N
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
