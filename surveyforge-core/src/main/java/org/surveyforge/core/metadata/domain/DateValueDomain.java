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
import java.security.InvalidParameterException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;

/**
 * @author jsegura
 */
@Entity
public class DateValueDomain extends AbstractValueDomain
  {
  private static final long serialVersionUID = -7700790723958655600L;

  private SimpleDateFormat  simpleDateFormat;
  private String            pattern          = "dd/MM/yyyy";

  private Date              maxValue         = null;
  private Date              minValue         = null;

  public DateValueDomain( )
    {
    simpleDateFormat = new SimpleDateFormat( pattern );
    }

  public SimpleDateFormat getFormat( )
    {
    return simpleDateFormat;
    }

  public void setPattern( String pattern )
    {
    if( pattern != null )
      {
      /* This will throw an exception if pattern is invalid */
      simpleDateFormat.applyPattern( pattern );
      this.pattern = pattern;
      }
    }

  public String getPattern( )
    {
    return this.pattern;
    }

  public Date getMaxValue( )
    {
    return maxValue;
    }

  public void setMaxValue( Date maxValue )
    {
    if( maxValue != null && this.minValue != null )
      {
      if( this.minValue.before( maxValue ) )
        this.maxValue = maxValue;
      else
        throw new InvalidParameterException( );
      }
    else
      this.maxValue = maxValue;
    }

  public Date getMinValue( )
    {
    return minValue;
    }

  public void setMinValue( Date minValue )
    {
    if( this.maxValue != null && minValue != null )
      {
      if( minValue.before( this.maxValue ) )
        this.minValue = minValue;
      else
        throw new InvalidParameterException( );
      }
    else
      this.minValue = minValue;
    }

  public boolean isValid( Serializable object )
    {
    if( object instanceof Date )
      {
      Date date = (Date) object;
      if( this.getMaxValue( ) != null && date.after( this.getMaxValue( ) ) )
        return false;
      else if( this.getMinValue( ) != null && date.before( this.getMinValue( ) ) )
        return false;
      else
        return true;
      }
    else
      return false;
    }

  public DateValueDomain clone( )
    {
    return (DateValueDomain) super.clone( );
    }
  }