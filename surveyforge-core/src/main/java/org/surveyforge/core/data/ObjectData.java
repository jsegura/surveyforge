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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;

/**
 * @author jsegura
 */
@Entity
public class ObjectData extends Data
  {
  private static final long serialVersionUID = 6601926722422919229L;

  @SuppressWarnings("unused")
  @Id
  @Column(length = 50)
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "uuid")
  private String            id;
  /** Version for optimistic locking. */
  @SuppressWarnings("unused")
  @javax.persistence.Version
  private int               lockingVersion;

  @ManyToOne(optional = true)
  @JoinColumn(name = "registerData_id", insertable = false, updatable = false)
  private RegisterData      registerData;

  protected ObjectData( )
    {};

  public ObjectData( RegisterData registerData )
    {
    this.setRegisterData( registerData );
    }

  /**
   * @return Returns the registerData.
   */
  public RegisterData getRegisterData( )
    {
    return this.registerData;
    }

  /**
   * @param registerData The registerData to set.
   */
  public void setRegisterData( RegisterData registerData )
    {
    if( registerData != null )
      {
      this.registerData = registerData;
      this.registerData.addObjectData( this );
      }
    else
      throw new NullPointerException( );
    }
  }