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
 * $Id$
 */
package org.surveyforge.util.jgoodies;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyVetoException;

import com.jgoodies.binding.PresentationModel;
import com.jgoodies.binding.value.AbstractValueModel;
import com.jgoodies.binding.value.Trigger;
import com.jgoodies.binding.value.ValueHolder;
import com.jgoodies.binding.value.ValueModel;

/**
 * @author jgonzalez
 */
public class JXPathPresentationModel extends PresentationModel
  {
  private static final long       serialVersionUID = 316651705706991364L;
  
  private final JXPathBeanAdapter beanAdapter;

  public JXPathPresentationModel( Object bean )
    {
    this( new ValueHolder( bean, true ) );
    }

  public JXPathPresentationModel( Object bean, ValueModel triggerChannel )
    {
    this( new ValueHolder( bean, true ), triggerChannel );
    }

  public JXPathPresentationModel( ValueModel beanChannel )
    {
    this( beanChannel, new Trigger( ) );
    }

  public JXPathPresentationModel( ValueModel beanChannel, ValueModel triggerChannel )
    {
    super( beanChannel, triggerChannel );
    this.beanAdapter = new JXPathBeanAdapter( beanChannel, true );
    this.beanAdapter.addPropertyChangeListener( new BeanChangeHandler( ) );
    observeChanged( this.beanAdapter, JXPathBeanAdapter.PROPERTYNAME_CHANGED );
    }

  public Object getValue( String propertyName )
    {
    return this.beanAdapter.getValue( propertyName );
    }

  public void setValue( String propertyName, Object newValue )
    {
    this.beanAdapter.setValue( propertyName, newValue );
    }

  public void setVetoableValue( String propertyName, Object newValue ) throws PropertyVetoException
    {
    this.beanAdapter.setVetoableValue( propertyName, newValue );
    }

  public AbstractValueModel getModel( String propertyName )
    {
    return this.beanAdapter.getValueModel( propertyName );
    }

  public AbstractValueModel getModel( String propertyName, String getterName, String setterName )
    {
    return this.beanAdapter.getValueModel( propertyName, getterName, setterName );
    }

  public void resetChanged( )
    {
    setChanged( false );
    this.beanAdapter.resetChanged( );
    }

  public synchronized void addBeanPropertyChangeListener( PropertyChangeListener listener )
    {
    this.beanAdapter.addBeanPropertyChangeListener( listener );
    }

  public synchronized void removeBeanPropertyChangeListener( PropertyChangeListener listener )
    {
    this.beanAdapter.removeBeanPropertyChangeListener( listener );
    }

  public synchronized void addBeanPropertyChangeListener( String propertyName, PropertyChangeListener listener )
    {
    this.beanAdapter.addBeanPropertyChangeListener( propertyName, listener );
    }

  public synchronized void removeBeanPropertyChangeListener( String propertyName, PropertyChangeListener listener )
    {
    this.beanAdapter.removeBeanPropertyChangeListener( propertyName, listener );
    }

  public synchronized PropertyChangeListener[] getBeanPropertyChangeListeners( )
    {
    return this.beanAdapter.getBeanPropertyChangeListeners( );
    }

  public synchronized PropertyChangeListener[] getBeanPropertyChangeListeners( String propertyName )
    {
    return this.beanAdapter.getBeanPropertyChangeListeners( propertyName );
    }

  private class BeanChangeHandler implements PropertyChangeListener
    {
    public void propertyChange( PropertyChangeEvent evt )
      {
      Object oldBean = evt.getOldValue( );
      Object newBean = evt.getNewValue( );
      String propertyName = evt.getPropertyName( );
      if( JXPathBeanAdapter.PROPERTYNAME_BEFORE_BEAN.equals( propertyName ) )
        {
        beforeBeanChange( oldBean, newBean );
        }
      else if( JXPathBeanAdapter.PROPERTYNAME_BEAN.equals( propertyName ) )
        {
        firePropertyChange( PROPERTYNAME_BEAN, oldBean, newBean, true );
        }
      else if( JXPathBeanAdapter.PROPERTYNAME_AFTER_BEAN.equals( propertyName ) )
        {
        afterBeanChange( oldBean, newBean );
        }
      }
    }
  }
