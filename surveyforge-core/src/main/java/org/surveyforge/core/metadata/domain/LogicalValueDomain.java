/* 
 * surveyforge-core - Copyright (C) 2006 OPEN input - http://www.openinput.com/
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
package org.surveyforge.core.metadata.domain;

import java.io.Serializable;

import javax.persistence.Entity;

/**
 * @author jsegura
 */
@Entity
public class LogicalValueDomain extends AbstractValueDomain
  {
  private static final long serialVersionUID = 2066579378848047283L;

  public LogicalValueDomain( )
    {}

  public boolean isValid( Serializable object )
    {
    return (Boolean.class.isInstance( object ));
    }


  public LogicalValueDomain clone( )
    {
    return (LogicalValueDomain) super.clone( );
    }
  }
