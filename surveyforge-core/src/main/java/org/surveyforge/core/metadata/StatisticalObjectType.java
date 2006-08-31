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

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.IndexColumn;

/**
 * Statistical objects or units may be real world objects (e.g. person, enterprise) or abstract objects like events or states (e.g.
 * accident, temperature). A statistical object or unit describes the type of object observed in a statistical survey or presented in a
 * statistical table. It is considered as an abstract set of objects to be observed, measured or estimated.
 * 
 * @author jsegura
 */
@Entity
public class StatisticalObjectType implements Serializable
  {
  private static final long           serialVersionUID = 3051156955695891963L;

  @SuppressWarnings("unused")
  @Id
  @Column(length = 50)
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "uuid")
  private String                      id;
  /** Version for optimistic locking. */
  @SuppressWarnings("unused")
  @javax.persistence.Version
  private int                         lockingVersion;

  /**
   * A statistical object is identified by a unique language independent identifier, which may typically be an abbreviation of its
   * name.
   */
  @Column(unique = true, length = 50)
  private String                      identifier;
  /** Unique name statistical unit. */
  @Column(length = 250)
  private String                      name             = "";
  /** Short general multilingual description of the statistical object/unit, including its purpose, its main subject areas etc. */
  @Column(length = 500)
  private String                      description      = "";
  /** A super-type is a generalisation of an object type. */
  @ManyToOne
  @JoinColumn(name = "superType_id", insertable = false, updatable = false)
  private StatisticalObjectType       superType;
  /** A sub type describes a specialisation of the type. Sub types are described as "IS A" relationships. */
  @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
  @IndexColumn(name = "subTypesIndex")
  @JoinColumn(name = "superType_id")
  private List<StatisticalObjectType> subTypes         = new ArrayList<StatisticalObjectType>( );
  /** Object variables defined for the statistical object/unit. */
  @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
  @IndexColumn(name = "objectVariablesIndex")
  @JoinColumn(name = "statisticalObjectType_id", nullable = false)
  private List<ObjectVariable>        objectVariables  = new ArrayList<ObjectVariable>( );


  protected StatisticalObjectType( )
    {}

  /**
   * Creates a new {@link StatisticalObjectType} identified by identifier.
   * 
   * @param identifier The identifier to set.
   */
  public StatisticalObjectType( String identifier )
    {
    this.setIdentifier( identifier );
    }

  /**
   * Returns the identifier of the StatisticalObjectType.
   * 
   * @return Returns the identifier.
   */
  public String getIdentifier( )
    {
    return this.identifier;
    }

  /**
   * Sets a new identifier for the StatisticalObjectType.
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
   * Returns the name of the StatisticalObjectType.
   * 
   * @return Returns the name.
   */
  public String getName( )
    {
    return this.name;
    }

  /**
   * Sets a new name to the StatisticalObjectType.
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
   * Returns the description of the StatisticalObjectType.
   * 
   * @return Returns the description.
   */
  public String getDescription( )
    {
    return this.description;
    }

  /**
   * Sets a new description for the StatisticalObjectType.
   * 
   * @param description The description to set.
   * @throws NullPointerException If the name is <code>null</code>.
   */
  public void setDescription( String description )
    {
    if( description != null )
      this.description = description;
    else
      throw new NullPointerException( );
    }

  /**
   * Return the super type of the StatisticalObjectType.
   * 
   * @return Returns the superType.
   */
  public StatisticalObjectType getSuperType( )
    {
    return superType;
    }

  /**
   * Sets the super type of the StatisticalObjectType.
   * 
   * @param superType The superType to set.
   */
  public void setSuperType( StatisticalObjectType superType )
    {
    if( this.superType != null ) this.superType.removeSubType( this );
    this.superType = superType;
    if( this.superType != null ) this.superType.addSubType( this );
    }

  /**
   * Returns the list of sub types of the StatisticalObjectType.
   * 
   * @return Returns the list of subtypes.
   */
  public List<StatisticalObjectType> getSubTypes( )
    {
    return Collections.unmodifiableList( this.subTypes );
    }

  /**
   * Adds a new subtype to the list of subtypes for the StatisticalObjectType
   * 
   * @param subtypes The subtypes to add.
   * @throws NullPointerException If the subtype is <code>null</code>.
   */
  protected void addSubType( StatisticalObjectType subType )
    {
    if( subType != null )
      this.subTypes.add( subType );
    else
      throw new NullPointerException( );
    }

  /**
   * Removes a subtype from the list of subtypes for the StatisticalObjectType
   * 
   * @param subtypes The subtypes to remove.
   * @throws NullPointerException If the subtype is <code>null</code>.
   */
  protected void removeSubType( StatisticalObjectType subType )
    {
    if( subType != null )
      this.subTypes.remove( subType );
    else
      throw new NullPointerException( );
    }

  /**
   * Returns the list of {@link ObjectVariable}s related to his StatisticalObjectType.
   * 
   * @return Returns the list of ObjectVariables.
   */
  public List<ObjectVariable> getObjectVariables( )
    {
    return Collections.unmodifiableList( this.objectVariables );
    }

  /**
   * Adds an object variable to the list of object variables for the StatisticalObjectType
   * 
   * @param subtypes The object variable to remove.
   * @throws NullPointerException If the objectVariable is <code>null</code>.
   */
  protected void addObjectVariable( ObjectVariable objectVariable )
    {
    if( objectVariable != null )
      this.objectVariables.add( objectVariable );
    else
      throw new NullPointerException( );
    }

  /**
   * Removes an object variable from the list of object variables for the StatisticalObjectType
   * 
   * @param subtypes The object variable to remove.
   * @throws NullPointerException If the objectVariable is <code>null</code>.
   */
  protected void removeObjectVariable( ObjectVariable objectVariable )
    {
    if( objectVariable != null )
      this.objectVariables.remove( objectVariable );
    else
      throw new NullPointerException( );
    }

  // TODO add subtypes to hashcode & equals
  @Override
  public boolean equals( Object object )
    {
    StatisticalObjectType other = (StatisticalObjectType) object;
    return this.getIdentifier( ).equals( other.getIdentifier( ) );
    }

  @Override
  public int hashCode( )
    {
    return this.getIdentifier( ).hashCode( );
    }

  }
