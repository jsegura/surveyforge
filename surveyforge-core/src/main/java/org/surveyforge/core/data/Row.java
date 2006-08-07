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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.IndexColumn;

/**
 * @author jsegura
 */
@Entity
public class Row implements Serializable
  {
  private static final long serialVersionUID = 6601926722422919229L;
  
  @SuppressWarnings("unused")
  @Id
  @Column(length = 50)
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "uuid")
  private String        id;
  /** Version for optimistic locking. */
  @SuppressWarnings("unused")
  @javax.persistence.Version
  private int           lockingVersion;

  @ManyToOne(optional = true)
  @JoinColumn(name = "registerData_id", insertable = false, updatable = false)
  private RegisterData  registerData;

  @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
  @IndexColumn(name = "rowDatasIndex")
  @JoinColumn(name = "row_id", nullable = false)
  private List<RowData> rowDatas = new ArrayList<RowData>( );

  public Row( RegisterData registerData )
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
      this.registerData.addRow( this );
      }
    else
      throw new NullPointerException( );
    }

  public void setField( String elementIdentifier, RowData rowData )
    {
    int fieldIndex = this.registerData.getRegister( ).getElementIndex( elementIdentifier );
    if( fieldIndex != -1 )
      {
      this.rowDatas.set( fieldIndex, rowData );
      }
    else
      {
      // String errorMessage = MessageFormat.format( Record.messages.getString( "message.invalid.name" ), fieldName );
      // if( Record.log.isInfoEnabled( ) )
      // {
      // Record.log.info( Record.messages.getString( "exception.invalid.name" ) + errorMessage );
      // }
      throw new NoSuchElementException( );
      }
    }

  public List<RowData> getRowDatas( )
    {
    return Collections.unmodifiableList( this.rowDatas );
    }

  }
