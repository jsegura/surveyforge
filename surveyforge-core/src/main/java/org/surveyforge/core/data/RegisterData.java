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

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.IndexColumn;
import org.surveyforge.core.metadata.Register;
import org.surveyforge.core.metadata.RegisterDataElement;

/**
 * @author jsegura
 */
@Entity
public class RegisterData implements Serializable
  {
  private static final long serialVersionUID = 4300393468288029803L;

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

  @OneToOne(optional = true)
  private Register          register;

  @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
  @IndexColumn(name = "objectDataIndex")
  @JoinColumn(name = "registerData_id")
  private List<ObjectData>  objectData       = new ArrayList<ObjectData>( );

  protected RegisterData( )
    {}

  public RegisterData( Register register )
    {
    if( register == null )
      throw new NullPointerException( );
    else if( register.getRegisterData( ) != null )
      throw new IllegalArgumentException( );
    else
      this.register = register;
    }

  /**
   * @return Returns the register.
   */
  public Register getRegister( )
    {
    return this.register;
    }

  /**
   * @return Returns the objectData.
   */
  public List<ObjectData> getObjectData( )
    {
    return Collections.unmodifiableList( this.objectData );
    }

  /**
   * @param objectData The objectData to .
   */
  protected void addObjectData( ObjectData objectData )
    {
    if( objectData != null )
      {
      if( this.register.getKey( ).size( ) == 0 ) throw new IllegalArgumentException( );
      ArrayList<Integer> illegalList = new ArrayList<Integer>( );
      // TODO: Check validation rules and throw Exceptions if needed

      if( this.register.getComponentElements( ).size( ) != objectData.getComponentData( ).size( ) )
        throw new IllegalArgumentException( );
      int index = 0;
      for( Data data : objectData.getComponentData( ) )
        {
        if( !this.register.getComponentElements( ).get( index ).getValueDomain( ).isValid( data.getData( ) ) )
          illegalList.add( index ); // TODO: Elaborate on this Exception
        index++;
        }
      if( illegalList.size( ) == 0 ) this.objectData.add( objectData );
      }

    else
      throw new NullPointerException( );
    }

  /**
   * @param objectData The objectData to .
   */
  protected void removeObjectData( ObjectData objectData )
    {
    if( objectData != null )
      this.objectData.remove( objectData );
    else
      throw new NullPointerException( );
    }

  // TODO: Maybe this could be joined in only a method?
  public ObjectData createEmptyObjectData( )
    {
    ObjectData objectData = new ObjectData( );
    objectData.setIdentifier( this.getRegister( ).getIdentifier( ) );
    for( RegisterDataElement componentDataElement : this.getRegister( ).getComponentElements( ) )
      objectData.addComponentData( RegisterData.createEmptyData( componentDataElement ) );
    return objectData;
    }

  protected static Data createEmptyData( RegisterDataElement registerDataElement )
    {
    Data data = new Data( registerDataElement.getIdentifier( ) );
    for( RegisterDataElement componentDataElement : registerDataElement.getComponentElements( ) )
      data.addComponentData( RegisterData.createEmptyData( componentDataElement ) );
    return data;
    }
  }
