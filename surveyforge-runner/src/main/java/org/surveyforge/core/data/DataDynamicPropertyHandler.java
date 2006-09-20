/* 
 * surveyforge-runner - Copyright (C) 2006 OPEN input - http://www.openinput.com/
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
package org.surveyforge.core.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.jxpath.DynamicPropertyHandler;

/**
 * @author jgonzalez
 */
public class DataDynamicPropertyHandler implements DynamicPropertyHandler
  {
  /*
   * (non-Javadoc)
   * 
   * @see org.apache.commons.jxpath.DynamicPropertyHandler#getProperty(java.lang.Object, java.lang.String)
   */
  public Object getProperty( Object object, String propertyName )
    {
    Data data = (Data) object;
    return data.getComponentData( propertyName ).getData( );
    }

  /*
   * (non-Javadoc)
   * 
   * @see org.apache.commons.jxpath.DynamicPropertyHandler#getPropertyNames(java.lang.Object)
   */
  public String[] getPropertyNames( Object object )
    {
    Data data = (Data) object;
    List<String> propertyNames = new ArrayList<String>( );
    for( Data componentData : data.getComponentData( ) )
      {
      propertyNames.add( componentData.getIdentifier( ) );
      }
    return propertyNames.toArray( new String[] {} );
    }

  /*
   * (non-Javadoc)
   * 
   * @see org.apache.commons.jxpath.DynamicPropertyHandler#setProperty(java.lang.Object, java.lang.String, java.lang.Object)
   */
  public void setProperty( Object object, String propertyName, Object value )
    {
    Data data = (Data) object;
    data.getComponentData( propertyName ).setData( (Serializable) value );
    }
  }
