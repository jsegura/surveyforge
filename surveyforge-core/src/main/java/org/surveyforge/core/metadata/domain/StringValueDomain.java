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
 * @author jgonzalez
 */
@Entity
public class StringValueDomain extends AbstractValueDomain
  {
  private static final long serialVersionUID = -5056062679246063821L;

  private int               minLength        = 0;
  private int               maxLength        = Integer.MAX_VALUE;

  protected StringValueDomain( )
    {};

  public StringValueDomain( int minLength, int maxLength )
    {
    this.setMinLength( minLength );
    this.setMaxLength( maxLength );
    }

  public int getMinLength( )
    {
    return this.minLength;
    }

  public void setMinLength( int minLength )
    {
    if( minLength >= 0 && minLength <= this.getMaxLength( ) )
      this.minLength = minLength;
    else
      throw new IllegalArgumentException( );
    }

  public int getMaxLength( )
    {
    return this.maxLength;
    }

  public void setMaxLength( int maxLength )
    {
    if( maxLength > 0 && maxLength >= this.getMinLength( ) )
      this.maxLength = maxLength;
    else
      throw new IllegalArgumentException( );
    }

  public boolean isValid( Serializable object )
    {
    if( object instanceof String )
      {
      String string = (String) object;
      return this.getMinLength( ) <= string.length( ) && string.length( ) <= this.getMaxLength( );
      }
    else
      return false;
    }


  
  @Override
  public StringValueDomain clone( )
    {
    StringValueDomain copy = (StringValueDomain) super.clone( );
    return copy;
    }
  }
