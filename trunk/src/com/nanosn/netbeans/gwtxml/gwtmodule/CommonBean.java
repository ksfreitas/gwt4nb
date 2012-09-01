/**
 * This interface is the intersection of all generated methods.
 * 
 * @Generated
 */

package com.nanosn.netbeans.gwtxml.gwtmodule;

public interface CommonBean {
	public org.w3c.dom.Comment addComment(String comment);

	public void addPropertyChangeListener(String n, java.beans.PropertyChangeListener l);

	public void addPropertyChangeListener(java.beans.PropertyChangeListener l);

	public int addValue(String name, Object value);

	public org.netbeans.modules.schema2beans.BaseBean[] childBeans(boolean recursive);

	public void childBeans(boolean recursive, java.util.List beans);

	public Object clone();

	public org.w3c.dom.Comment[] comments();

	public String dtdName();

	public void dump(StringBuffer str, String indent);

	public boolean equals(Object obj);

	public String fullName();

	public String[] getAttributeNames();

	public String[] getAttributeNames(String propName);

	public String getAttributeValue(String name);

	public String getAttributeValue(String propName, String name);

	public String getAttributeValue(String propName, int index, String name);

	public String getDefaultNamespace();

	public Object getValue(String name);

	public Object getValue(String name, int index);

	public Object[] getValues(String name);

	public int indexOf(String name, Object value);

	public boolean isChoiceProperty();

	public boolean isChoiceProperty(String name);

	public boolean isNull(String name);

	public boolean isNull(String name, int index);

	public boolean isRoot();

	public void merge(org.netbeans.modules.schema2beans.BaseBean bean);

	public void merge(org.netbeans.modules.schema2beans.BaseBean bean, int mode);

	public String name();

	public org.netbeans.modules.schema2beans.BaseBean parent();

	public void reindent();

	public void removeComment(org.w3c.dom.Comment comment);

	public void removePropertyChangeListener(String n, java.beans.PropertyChangeListener l);

	public void removePropertyChangeListener(java.beans.PropertyChangeListener l);

	public int removeValue(String name, Object value);

	public void removeValue(String name, int index);

	public void setAttributeValue(String propName, int index, String name, String value);

	public void setDefaultNamespace(String namespace);

	public void setValue(String name, Object value);

	public void setValue(String name, Object[] value);

	public void setValue(String name, int index, Object value);

	public int size(String name);

	public String toString();

	public void validate() throws org.netbeans.modules.schema2beans.ValidateException;

	public void write(java.io.OutputStream out) throws java.io.IOException, org.netbeans.modules.schema2beans.Schema2BeansRuntimeException;

	public void write(java.io.OutputStream out, String encoding) throws java.io.IOException, org.netbeans.modules.schema2beans.Schema2BeansException;

	public void write(java.io.Writer w) throws java.io.IOException, org.netbeans.modules.schema2beans.Schema2BeansException;

	public void write(java.io.Writer w, String encoding) throws java.io.IOException, org.netbeans.modules.schema2beans.Schema2BeansException;

	public void writeNoReindent(java.io.OutputStream out) throws java.io.IOException, org.netbeans.modules.schema2beans.Schema2BeansException;

}
