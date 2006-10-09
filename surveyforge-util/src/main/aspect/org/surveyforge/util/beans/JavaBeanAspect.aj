/* 
 * surveyforge-util - Copyright (C) 2006 OPEN input - http://www.openinput.com/
 *
 * This program is free software; you can redistribute it and/or modify it 
 * under the terms of the GNU General Public License as published by the 
 * Free Software Foundation; either version 2 of the License, or (at your 
 * option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT 
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or 
 * FITNESS FOR A PARTICULAR PURPOSE. 
 * See the GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along 
 * with this program; if not, write to 
 *   the Free Software Foundation, Inc., 
 *   59 Temple Place, Suite 330, 
 *   Boston, MA 02111-1307 USA
 *   
 * $Id: InternationalizedString.java 40 2006-08-07 10:11:36Z jgongo $
 */
package org.surveyforge.util.beans;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.beans.PropertyDescriptor;
import java.beans.VetoableChangeSupport;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.aspectj.lang.reflect.MethodSignature;

public aspect JavaBeanAspect
  {
  private interface JavaBean {}
  declare parents: (@Observable *) implements JavaBean;
  
  private PropertyChangeSupport JavaBean.pcs;
  private VetoableChangeSupport JavaBean.vcs;
  
  public void JavaBean.addPropertyChangeListener( PropertyChangeListener listener )
    {
    this.pcs.addPropertyChangeListener( listener );
    }

  public void JavaBean.addPropertyChangeListener( String propertyName, PropertyChangeListener listener )
    {
    this.pcs.addPropertyChangeListener( propertyName, listener );
    }

  private void JavaBean.firePropertyChange( String propertyName, Object oldValue, Object newValue )
    {
    this.pcs.firePropertyChange( propertyName, oldValue, newValue );
    }

  public PropertyChangeListener[] JavaBean.getPropertyChangeListeners( )
    {
    return this.pcs.getPropertyChangeListeners( );
    }

  public PropertyChangeListener[] JavaBean.getPropertyChangeListeners( String propertyName )
    {
    return this.pcs.getPropertyChangeListeners( propertyName );
    }

  public boolean JavaBean.hasListeners( String propertyName )
    {
    return this.pcs.hasListeners( propertyName );
    }

  public void JavaBean.removePropertyChangeListener( PropertyChangeListener listener )
    {
    this.pcs.removePropertyChangeListener( listener );
    }

  public void JavaBean.removePropertyChangeListener( String propertyName, PropertyChangeListener listener )
    {
    this.pcs.removePropertyChangeListener( propertyName, listener );
    }

  pointcut javaBeanCreation( JavaBean bean ) : target( bean ) && execution( (@Observable *).new(..) );
  before( JavaBean bean ) : javaBeanCreation( bean )
    {
    bean.pcs = new PropertyChangeSupport( bean );
    bean.vcs = new VetoableChangeSupport( bean );
    }
  
  pointcut methodInvocation( JavaBean bean ) : target( bean ) && execution( * (@Observable *).*(..) );
  Object around( JavaBean bean ) : methodInvocation( bean )
    {
    try
      {
      Method advicedMethod = ((MethodSignature) thisJoinPointStaticPart.getSignature( )).getMethod( );
      
      BeanInfo beanInfo = Introspector.getBeanInfo( bean.getClass( ) );
      PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors( );
      
      for( PropertyDescriptor propertyDescriptor : propertyDescriptors )
        {
        if( advicedMethod.equals( propertyDescriptor.getWriteMethod( ) ) )
          {
          Object oldValue = propertyDescriptor.getReadMethod( ).invoke( bean );
          Object returnValue = proceed( bean );
          Object newValue = propertyDescriptor.getReadMethod( ).invoke( bean );
          bean.firePropertyChange( propertyDescriptor.getName( ), oldValue, newValue );
          return returnValue;
          }
        }
      }
    catch( IntrospectionException exc )
      {}
    catch( InvocationTargetException exc )
      {}
    catch( IllegalAccessException exc )
      {}
    
    return proceed( bean );
    }
  }
