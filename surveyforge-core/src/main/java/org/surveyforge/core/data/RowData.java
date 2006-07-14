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
package org.surveyforge.core.data;

import java.io.Serializable;

// TODO: Elaborate on comments
/**
 * @author jsegura
 */
public class RowData
  {
  private Serializable data;
  private boolean      answered;
  private boolean      applicable;

  /**
   * @return Returns the data.
   */
  public Serializable getData( )
    {
    return this.data;
    }

  /**
   * @param data The data to set.
   */
  public void setData( Serializable data )
    {
    this.data = data;
    }

  /**
   * @return Returns the answered.
   */
  public boolean isAnswered( )
    {
    return this.answered;
    }

  /**
   * @param answered The answered to set.
   */
  public void setAnswered( boolean answered )
    {
    this.answered = answered;
    }

  /**
   * @return Returns the applicable.
   */
  public boolean isApplicable( )
    {
    return this.applicable;
    }

  /**
   * @param applicable The applicable to set.
   */
  public void setApplicable( boolean applicable )
    {
    this.applicable = applicable;
    }


  }
