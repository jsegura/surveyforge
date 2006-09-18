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
package org.surveyforge.core.metadata;

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
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.IndexColumn;
import org.surveyforge.core.metadata.domain.AbstractValueDomain;

// TODO Elaborate on comments
/**
 * @author jsegura
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"identifier", "register_id"})})
public abstract class DataElement implements Serializable
  {
  private static final long        serialVersionUID  = 0L;

  @SuppressWarnings("unused")
  @Id
  @Column(length = 50)
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "uuid")
  private String                   id;
  /** Version for optimistic locking. */
  @SuppressWarnings("unused")
  @javax.persistence.Version
  private int                      lockingVersion;

  /**
   * This is a unique and language independent identifier for the data element. The identifier is unique among all other data elements
   * for an object variable (standard data element) or within the scope of a statistical activity.
   */
  @Column(length = 50)
  private String                   identifier;
  /**  */
  private boolean                  multiple          = false;
  /**  */
  private int                      maxResponses      = 1;
  /**  */
  @ManyToOne(cascade = {CascadeType.ALL})
  @JoinColumn(name = "valueDomain_id")
  private AbstractValueDomain       valueDomain;
  /**  */
  @ManyToOne(cascade = {CascadeType.ALL})
  @JoinColumn(name = "variableStructure_id", insertable = false, updatable = false)
  private DataElement              variableStructure;
  /**  */
  @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
  @IndexColumn(name = "componentElementsIndex")
  @JoinColumn(name = "variableStructure_id")
  private List<DataElement>        componentElements = new ArrayList<DataElement>( );
  @Transient
  private Map<String, DataElement> nameToElementMap  = new HashMap<String, DataElement>( );


  protected DataElement( )
    {}

  protected DataElement( String identifier )
    {
    this.setIdentifier( identifier );
    }

  /**
   * @param valueDomain
   * @param identifier
   */
  public DataElement( String identifier, AbstractValueDomain valueDomain )
    {
    this.setIdentifier( identifier );
    this.setValueDomain( valueDomain );
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
   * @return Returns the multiple.
   */
  public boolean isMultiple( )
    {
    return this.multiple;
    }

  /**
   * @param multiple The multiple to set.
   */
  public void setMultiple( boolean multiple )
    {
    this.multiple = multiple;
    }

  /**
   * @return Returns the maxResponses.
   */
  public int getMaxResponses( )
    {
    return this.maxResponses;
    }

  /**
   * @param maxResponses The maxResponses to set.
   */
  public void setMaxResponses( int maxResponses )
    {
    if( maxResponses >= 1 )
      this.maxResponses = maxResponses;
    else
      throw new IllegalArgumentException( );
    }

  /**
   * @return Returns the valueDomain.
   */
  public AbstractValueDomain getValueDomain( )
    {
    return this.valueDomain;
    }

  /**
   * @param valueDomain The valueDomain to set.
   */
  public void setValueDomain( AbstractValueDomain valueDomain )
    {
    if( valueDomain != null )
      this.valueDomain = valueDomain;
    else
      throw new NullPointerException( );
    }

  /**
   * @return Returns the variableStructure.
   */
  public DataElement getVariableStructure( )
    {
    return this.variableStructure;
    }

  /**
   * @param variableStructure The variableStructure to set.
   */
  public void setVariableStructure( DataElement variableStructure )
    {
    if( (variableStructure == null && this.getVariableStructure( ) != null)
        || !variableStructure.equals( this.getVariableStructure( ) ) )
      {
      if( this.getVariableStructure( ) != null ) this.getVariableStructure( ).removeElement( this );
      this.variableStructure = variableStructure;
      if( this.getVariableStructure( ) != null ) this.getVariableStructure( ).addElement( this );
      }
    }

  /**
   * @return Returns the componentElements.
   */
  public List<? extends DataElement> getComponentElements( )
    {
    return Collections.unmodifiableList( this.componentElements );
    }

  /**
   * @param componentElements
   */
  public void setComponentElements( List<? extends DataElement> componentElements )
    {
    this.clearComponentElements( );
    for( DataElement dataElement : componentElements )
      this.addComponentElement( dataElement );
    }

  /**
   * 
   */
  protected void clearComponentElements( )
    {
    Iterator<? extends DataElement> componentElementIterator = this.getComponentElements( ).iterator( );

    while( componentElementIterator.hasNext( ) )
      {
      DataElement dataElement = (DataElement) componentElementIterator.next( );
      dataElement.variableStructure = null;
      componentElementIterator.remove( );
      }
    }

  /**
   * @param componentElements The componentElements to set.
   */
  public void addComponentElement( DataElement componentElement )
    {
    if( componentElement != null )
      {
      if( !this.getComponentElements( ).contains( componentElement ) )
        {
        if( componentElement.getVariableStructure( ) != null && !componentElement.getVariableStructure( ).equals( this ) )
          {
          componentElement.getVariableStructure( ).removeElement( componentElement );
          componentElement.variableStructure = this;
          }
        this.componentElements.add( componentElement );
        }
      else
        throw new IllegalArgumentException( );
      }
    else
      throw new NullPointerException( );
    }

  /**
   * @param componentElement
   */
  protected void addElement( DataElement componentElement )
    {
    if( !this.getNameToElementMap( ).containsKey( componentElement.getIdentifier( ) ) )
      {
      this.componentElements.add( componentElement );
      this.getNameToElementMap( ).put( componentElement.getIdentifier( ), componentElement );
      }
    else
      throw new IllegalArgumentException( );
    }

  /**
   * @param componentElements The componentElements to set.
   */
  public void removeComponentElement( DataElement componentElement )
    {
    if( componentElement != null )
      {
      this.removeElement( componentElement );
      componentElement.variableStructure = null;
      }
    else
      throw new NullPointerException( );
    }

  /**
   * @param componentElement
   */
  protected void removeElement( DataElement componentElement )
    {
    if( this.getNameToElementMap( ).containsKey( componentElement.getIdentifier( ) ) )
      {
      this.componentElements.remove( componentElement );
      this.getNameToElementMap( ).remove( componentElement.getIdentifier( ) );
      }
    else
      throw new IllegalArgumentException( );
    }

  /**
   * @return
   */
  protected Map<String, DataElement> getNameToElementMap( )
    {
    if( this.nameToElementMap == null )
      {
      Map<String, DataElement> nameToElementMap = new HashMap<String, DataElement>( );

      for( DataElement dataElement : this.getComponentElements( ) )
        {
        nameToElementMap.put( dataElement.getIdentifier( ), dataElement );
        }

      this.nameToElementMap = nameToElementMap;
      }

    return this.nameToElementMap;
    }

  /**
   * @param identifier
   * @return
   */
  public DataElement getDataElement( String identifier )
    {
    return this.getNameToElementMap( ).get( identifier );
    }

  public DataElement getRootDataElement( )
    {
    if( this.getVariableStructure( ) == null )
      return this;
    else
      return this.getVariableStructure( ).getRootDataElement( );
    }

  @Override
  public boolean equals( Object object )
    {
    if( object instanceof DataElement )
      {
      DataElement otherDataElement = (DataElement) object;

      return this.getIdentifier( ).equals( otherDataElement.getIdentifier( ) )
          && (this.getVariableStructure( ) == null ? otherDataElement.getVariableStructure( ) == null : this.getVariableStructure( )
              .equals( otherDataElement.getVariableStructure( ) ));
      }
    else
      return false;
    }

  @Override
  public int hashCode( )
    {
    return this.getIdentifier( ).hashCode( ) ^ (this.getVariableStructure( ) == null ? 0 : this.getVariableStructure( ).hashCode( ));
    }
  }
