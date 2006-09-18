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
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.IndexColumn;

// TODO: Elaborate on comments
/**
 * @author jsegura
 */
@Entity
public class Data implements Serializable
  {
  private static final long serialVersionUID = 0L;

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

  /**
   * This is a unique and language independent identifier for the data element. The identifier is unique among all other data elements
   * for an object variable (standard data element) or within the scope of a statistical activity.
   */
  @Column(length = 50)
  private String            identifier;

  private Serializable      data;

  private boolean           answered;
  private boolean           applicable;

  @ManyToOne(cascade = {CascadeType.ALL})
  @JoinColumn(name = "enclosingData_id", insertable = false, updatable = false)
  private Data              enclosingData;

  @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
  @IndexColumn(name = "componentDataIndex")
  @JoinColumn(name = "enclosingData_id")
  private List<Data>        componentData    = new ArrayList<Data>( );
  @Transient
  private Map<String, Data> nameToDataMap;

  protected Data( )
    {}

  /**
   * @param valueDomain
   * @param identifier
   */
  public Data( String identifier )
    {
    this.setIdentifier( identifier );
    }

  /**
   * @return Returns the identifier.
   */
  public String getIdentifier( )
    {
    return this.identifier;
    }

  /**
   * @param identifier The identifier to set.
   */
  public void setIdentifier( String identifier )
    {
    if( identifier != null && !identifier.trim( ).equals( "" ) )
      this.identifier = identifier;
    else
      throw new NullPointerException( );
    }

  /**
   * @return Returns the data.
   */
  public Serializable getData( )
    {
    if( this.getComponentData( ).isEmpty( ) )
      return this.data;
    else
      return this;
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

  /**
   * @return Returns the enclosingData.
   */
  public Data getEnclosingData( )
    {
    return this.enclosingData;
    }

  /**
   * @return
   */
  public Data getRootData( )
    {
    if( this.getEnclosingData( ) == null )
      return this;
    else
      return this.getEnclosingData( ).getRootData( );
    }

  /**
   * @param enclosingData The enclosingData to set.
   */
  public void setEnclosingData( Data enclosingData )
    {
    if( (enclosingData == null && this.getEnclosingData( ) != null) || !enclosingData.equals( this.getEnclosingData( ) ) )
      {
      if( this.getEnclosingData( ) != null ) this.getEnclosingData( ).removeElement( this );
      this.enclosingData = enclosingData;
      if( this.getEnclosingData( ) != null ) this.getEnclosingData( ).addElement( this );
      }
    }

  /**
   * @return Returns the componentData.
   */
  public List<? extends Data> getComponentData( )
    {
    return Collections.unmodifiableList( this.componentData );
    }

  /**
   * @param identifier
   * @return
   */
  public Data getComponentData( String identifier )
    {
    return this.getNameToDataMap( ).get( identifier );
    }

  /**
   * @param componentData
   */
  public void setComponentData( List<? extends Data> componentData )
    {
    this.clearComponentData( );
    for( Data data : componentData )
      this.addComponentData( data );
    }

  /**
   * 
   */
  protected void clearComponentData( )
    {
    Iterator<? extends Data> componentDataIterator = this.getComponentData( ).iterator( );

    while( componentDataIterator.hasNext( ) )
      {
      Data data = (Data) componentDataIterator.next( );
      data.enclosingData = null;
      componentDataIterator.remove( );
      }
    }

  /**
   * @param componentData The componentData to set.
   */
  public void addComponentData( Data componentData )
    {
    if( componentData != null )
      {
      if( !this.getComponentData( ).contains( componentData ) )
        {
        if( componentData.getEnclosingData( ) != null && !componentData.getEnclosingData( ).equals( this ) )
          {
          componentData.getEnclosingData( ).removeElement( componentData );
          componentData.enclosingData = this;
          }
        this.componentData.add( componentData );
        }
      else
        throw new IllegalArgumentException( );
      }
    else
      throw new NullPointerException( );
    }

  /**
   * @param componentData
   */
  protected void addElement( Data componentData )
    {
    if( !this.getNameToDataMap( ).containsKey( componentData.getIdentifier( ) ) )
      {
      this.componentData.add( componentData );
      this.getNameToDataMap( ).put( componentData.getIdentifier( ), componentData );
      }
    else
      throw new IllegalArgumentException( );
    }

  /**
   * @param componentData The componentData to set.
   */
  public void removeComponentData( Data componentData )
    {
    if( componentData != null )
      {
      this.removeElement( componentData );
      componentData.enclosingData = null;
      }
    else
      throw new NullPointerException( );
    }

  /**
   * @param componentData
   */
  protected void removeElement( Data componentData )
    {
    if( this.getNameToDataMap( ).containsKey( componentData.getIdentifier( ) ) )
      {
      this.componentData.remove( componentData );
      this.getNameToDataMap( ).remove( componentData.getIdentifier( ) );
      }
    else
      throw new IllegalArgumentException( );
    }

  /**
   * @return
   */
  protected Map<String, Data> getNameToDataMap( )
    {
    if( this.nameToDataMap == null )
      {
      Map<String, Data> nameToDataMap = new HashMap<String, Data>( );

      for( Data data : this.getComponentData( ) )
        {
        nameToDataMap.put( data.getIdentifier( ), data );
        }

      this.nameToDataMap = nameToDataMap;
      }

    return this.nameToDataMap;
    }

  @Override
  public String toString( )
    {
    StringBuffer dataString = new StringBuffer( );
    dataString.append( this.getIdentifier( ) );
    dataString.append( " => " );
    if( this.getComponentData( ).isEmpty( ) )
      {
      dataString.append( this.getData( ) );
      }
    else
      {
      dataString.append( "[" );
      boolean first = true;
      for( Data data : this.getComponentData( ) )
        {
        if( !first )
          dataString.append( ", " );
        else
          first = false;
        dataString.append( data.toString( ) );
        }
      dataString.append( "]" );
      }

    return dataString.toString( );
    }
  }
