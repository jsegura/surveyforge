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
import java.util.List;

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
import org.surveyforge.core.survey.Study;

/**
 * An object variable defines the concept of a variable in connection with a defined statistical object (e.g. the income of a person).
 * The general description of the meaning of the variable is part of the global variable definition, which is linked to the object
 * variable. Several object variables can be defined for one global variable to define specific types of object variables.
 * 
 * @author jsegura
 */
@Entity
public class ObjectVariable implements Serializable
  {
  private static final long           serialVersionUID       = 4288089682729653747L;


  @Id
  @Column(length = 50)
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "uuid")
  private String                      id;
  /** Version for optimistic locking. */
  @javax.persistence.Version
  private int                         lockingVersion;

  /**
   * Unique and language independent identifier for the object variable. The identifier is unique among all object variables. The
   * identifier should contain identifications for the statistical object and the global variable it is based on.
   */
  @Column(unique = true, length = 50)
  private String                      identifier;
  /** The name is the official, language dependent name of the global variable, provided by the owner of the variable. */
  @Column(length = 250)
  private String                      name                   = "";
  /** Short general multilingual description of the object variable, including its purpose, its main subject areas etc. */
  @Column(length = 500)
  private String                      description            = "";
  /**
   * Each object variable belongs to a statistical object or unit. The object variable exists only in the context of the object
   * variable.
   */
  @ManyToOne
  @JoinColumn(name = "statisticalObjectType_id", insertable = false, updatable = false)
  private StatisticalObjectType       statisticalObjectType;
  /** An object variable should refer to a global variable definition that reflects the general concepts of the object variable. */
  @ManyToOne
  @JoinColumn(name = "globalVariable_id", insertable = false, updatable = false)
  private GlobalVariable              globalVariable;

  /** Based on an object variable a number of data elements may refer to this object variable. */
  @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
  @IndexColumn(name = "dataElementsIndex")
  @JoinColumn(name = "objectVariable_id")
  private List<ConceptualDataElement> conceptualDataElements = new ArrayList<ConceptualDataElement>( );


  private ObjectVariable( )
    {}

  /**
   * Creates a new instance of ObjectVariable identified by identifier.
   * 
   * @param statisticalObjectType
   * @param globalVariable
   * @param identifier The identifier to set.
   * @throws NullPointerException If the identifier is <code>null</code> or is empty.
   */
  public ObjectVariable( StatisticalObjectType statisticalObjectType, GlobalVariable globalVariable, String identifier )
    {
    this.setStatisticalObjectType( statisticalObjectType );
    this.setGlobalVariable( globalVariable );
    this.setIdentifier( identifier );
    }

  /**
   * Returns the identifier of the ObjectVariable.
   * 
   * @return Returns the identifier.
   */
  public String getIdentifier( )
    {
    return this.identifier;
    }

  /**
   * Sets the new identifier of the ObjectVariable.
   * 
   * @param identifier The identifier to set.
   * @throws NullPointerException If the identifier is <code>null</code> or is empty.
   */
  public void setIdentifier( String identifier )
    {
    if( identifier != null && !identifier.equals( "" ) )
      this.identifier = identifier;
    else
      throw new NullPointerException( );
    }

  /**
   * Returns the name of the ObjectVariable.
   * 
   * @return Returns the name.
   */
  public String getName( )
    {
    return this.name;
    }

  /**
   * Sets the name of the ObjectVariable.
   * 
   * @param name The name to set.
   * @throws NullPointerException If the name is <code>null</code>.
   */
  public void setName( String name )
    {
    if( name != null )
      this.name = name;
    else
      throw new NullPointerException( );
    }

  /**
   * Returns the description of the ObjectVariable.
   * 
   * @return Returns the description.
   */
  public String getDescription( )
    {
    return this.description;
    }

  /**
   * Sets a new description to the ObjectVariable.
   * 
   * @param description The description to set.
   * @throws NullPointerException If the description is <code>null</code>.
   */
  public void setDescription( String description )
    {
    if( description != null )
      this.description = description;
    else
      throw new NullPointerException( );
    }

  /**
   * Returns the {@link StatisticalObjectType} this ObjectVariable belongs to.
   * 
   * @return Returns the StatisticalObjectType.
   */
  public StatisticalObjectType getStatisticalObjectType( )
    {
    return this.statisticalObjectType;
    }

  /**
   * Sets a new {@link StatisticalObjectType} this ObjectVariable belongs to.
   * 
   * @param statisticalObjectType The statisticalObjectType to set.
   * @throws NullPointerException If the statisticalObjectType is <code>null</code>.
   */
  public void setStatisticalObjectType( StatisticalObjectType statisticalObjectType )
    {
    if( statisticalObjectType != null )
      {
      this.statisticalObjectType = statisticalObjectType;
      this.statisticalObjectType.addObjectVariable( this );
      }
    else
      throw new NullPointerException( );
    }

  /**
   * Returns the {@link GlobalVariable} this ObjectVariable is linked to.
   * 
   * @return Returns the globalVariable.
   */
  public GlobalVariable getGlobalVariable( )
    {
    return this.globalVariable;
    }

  /**
   * Returns the new {@link GlobalVariable} this ObjectVariable is linked to.
   * 
   * @param globalVariable The globalVariable to set.
   * @throws NullPointerException If the globalVariable is <code>null</code>.
   */
  public void setGlobalVariable( GlobalVariable globalVariable )
    {
    if( globalVariable != null )
      this.globalVariable = globalVariable;
    else
      throw new NullPointerException( );
    }

  /**
   * Returns the list of {@link ConceptualDataElement} linked to this ObjectVariable.
   * 
   * @return Returns the list of ConceptualDataElements.
   */
  public List<ConceptualDataElement> getConceptualDataElements( )
    {
    return Collections.unmodifiableList( conceptualDataElements );
    }

  /**
   * Adds a new {@link ConceptualDataElement} to the list.
   * 
   * @param dataElements The conceptualDataElement to add.
   */
  protected void addConceptualDataElement( ConceptualDataElement conceptualDataElement )
    {
    this.conceptualDataElements.add( conceptualDataElement );
    }

  /**
   * Removes a {@link ConceptualDataElement} from the list.
   * 
   * @param dataElements The conceptualDataElement to remove.
   */
  protected void removeConceptualDataElement( ConceptualDataElement conceptualDataElement )
    {
    this.conceptualDataElements.remove( conceptualDataElement );
    }

  @Override
  public boolean equals( Object object )
    {
    ObjectVariable other = (ObjectVariable) object;
    return this.getIdentifier( ).equals( other.getIdentifier( ) );
    }

  @Override
  public int hashCode( )
    {
    return this.getIdentifier( ).hashCode( );
    }
  }
