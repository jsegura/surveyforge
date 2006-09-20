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

import java.beans.PropertyDescriptor;

import org.apache.commons.jxpath.JXPathBeanInfo;

/**
 * @author jgonzalez
 */
public class ObjectDataXBeanInfo implements JXPathBeanInfo
  {
  public Class getDynamicPropertyHandlerClass( )
    {
    return DataDynamicPropertyHandler.class;
    }

  public PropertyDescriptor getPropertyDescriptor( String propertyName )
    {
    return null;
    }

  public PropertyDescriptor[] getPropertyDescriptors( )
    {
    return null;
    }

  public boolean isAtomic( )
    {
    return false;
    }

  public boolean isDynamic( )
    {
    return true;
    }
  }
