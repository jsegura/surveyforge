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
import java.math.BigDecimal;

import javax.persistence.Entity;

import org.surveyforge.core.metadata.ValueDomain;

/**
 * @author jgonzalez
 */
@Entity
public class QuantityValueDomain extends ValueDomain
  {
  private static final long serialVersionUID = 475119088217988415L;

  private int               precision        = 1;
  private int               scale;

  private BigDecimal        minimum          = BigDecimal.valueOf( Integer.MIN_VALUE );
  private BigDecimal        maximum          = BigDecimal.valueOf( Integer.MAX_VALUE ); ;

  /**
   * 
   */
  public QuantityValueDomain( int precision, int scale )
    {
    this.setPrecision( precision );
    this.setScale( scale );
    }

  /**
   * @return Returns the precision.
   */
  public int getPrecision( )
    {
    return precision;
    }

  /**
   * @param precision The precision to set.
   */
  public void setPrecision( int precision )
    {
    if( precision > 0 && precision >= this.getScale( ) )
      this.precision = precision;
    else
      throw new IllegalArgumentException( );
    }

  /**
   * @return Returns the scale.
   */
  public int getScale( )
    {
    return scale;
    }

  /**
   * @param scale The scale to set.
   */
  public void setScale( int scale )
    {
    if( scale >= 0 && scale <= this.getPrecision( ) )
      this.scale = scale;
    else
      throw new IllegalArgumentException( );
    }

  /**
   * @return Returns the minimum.
   */
  public BigDecimal getMinimum( )
    {
    return this.minimum;
    }

  /**
   * @param minimum The minimum to set.
   */
  public void setMinimum( BigDecimal minimum )
    {
    if( minimum.compareTo( this.getMaximum( ) ) < 0 )
      this.minimum = minimum;
    else
      throw new IllegalArgumentException( );
    }

  /**
   * @return Returns the maximum.
   */
  public BigDecimal getMaximum( )
    {
    return this.maximum;
    }

  /**
   * @param maximum The maximum to set.
   */
  public void setMaximum( BigDecimal maximum )
    {
    if( this.getMinimum( ).compareTo( maximum ) < 0 )
      this.maximum = maximum;
    else
      throw new IllegalArgumentException( );
    }

  @Override
  public boolean isValid( Serializable object )
    {
    if( object instanceof BigDecimal )
      {
      BigDecimal quantity = (BigDecimal) object;
      return quantity.scale( ) <= this.getScale( )
          && (quantity.precision( ) - quantity.scale( )) <= (this.getPrecision( ) - this.getScale( ))
          && this.getMinimum( ).compareTo( quantity ) <= 0 && quantity.compareTo( this.getMaximum( ) ) <= 0;
      }
    else
      return false;
    }

  @Override
  public QuantityValueDomain clone( )
    {
    QuantityValueDomain copy = (QuantityValueDomain) super.clone( );
    return copy;
    }
  }
