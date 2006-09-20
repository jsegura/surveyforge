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
package org.surveyforge.runner;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeListenerProxy;

import org.apache.commons.jxpath.JXPathContext;
import org.surveyforge.core.data.ObjectData;
import org.surveyforge.util.beans.Observable;

public aspect ObjectDataBeanAspect
  {
  declare @type : ObjectData : @Observable;

  pointcut valueChange( JXPathContext jxpathContext, String propertyName, Object newValue ) : call( void JXPathContext.setValue(String, Object) ) && target( jxpathContext ) && args(propertyName, newValue);

  void around( JXPathContext jxpathContext, String propertyName, Object newValue ) : valueChange( jxpathContext, propertyName, newValue )
    {
    ObjectData objectData = (ObjectData) jxpathContext.getContextBean( );
    Object oldValue = jxpathContext.getValue( propertyName );
    proceed( jxpathContext, propertyName, newValue );
    
    PropertyChangeEvent event = new PropertyChangeEvent( objectData, propertyName, oldValue, newValue );
    for( PropertyChangeListener propertyChangeListener : objectData.getPropertyChangeListeners( ) )
      {
      if( propertyChangeListener instanceof PropertyChangeListenerProxy )
        {
        if( ((PropertyChangeListenerProxy) propertyChangeListener).getPropertyName( ).equals( propertyName ) )
          propertyChangeListener.propertyChange( event );
        }
      else
        propertyChangeListener.propertyChange( event );
      }
    }
  }
