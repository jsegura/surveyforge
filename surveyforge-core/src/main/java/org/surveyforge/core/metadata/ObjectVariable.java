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
import java.util.Locale;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;
import org.surveyforge.util.InternationalizedString;

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
  private static final long     serialVersionUID = 4288089682729653747L;

  @SuppressWarnings("unused")
  @Id
  @Column(length = 50)
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "uuid")
  private String                id;
  /** Version for optimistic locking. */
  @SuppressWarnings("unused")
  @javax.persistence.Version
  private int                   lockingVersion;

  /**
   * Unique and language independent identifier for the object variable. The identifier is unique among all object variables. The
   * identifier should contain identifications for the statistical object and the global variable it is based on.
   */
  @Column(unique = true, length = 50)
  private String                identifier;
  /** The name is the official, language dependent name of the global variable, provided by the owner of the variable. */
  @ManyToOne(cascade = {CascadeType.ALL})
  private InternationalizedString                name             = new InternationalizedString();
  /** Short general multilingual description of the object variable, including its purpose, its main subject areas etc. */
  @ManyToOne(cascade = {CascadeType.ALL})
  private InternationalizedString                description      = new InternationalizedString();
  /**
   * Each object variable belongs to a statistical object or unit. The object variable exists only in the context of the object
   * variable.
   */
  @ManyToOne
  @JoinColumn(name = "statisticalObjectType_id", insertable = false, updatable = false)
  private StatisticalObjectType statisticalObjectType;
  /** An object variable should refer to a global variable definition that reflects the general concepts of the object variable. */
  @ManyToOne
  @JoinColumn(name = "globalVariable_id", insertable = false, updatable = false)
  private GlobalVariable        globalVariable;

  protected ObjectVariable( )
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

  public InternationalizedString getInternationalizedName( )
    {
    return this.name;
    }

  /**
   * Returns the name of this Object Variable.
   * 
   * @return the name of this Object Variable for the default language.
   * @see InternationalizedString#getString()
   */
  public String getName( )
    {
    return this.name.getString( );
    }

  /**
   * Returns the name of this Object Variable for the given language.
   * 
   * @param locale the language of the Object Variable to be returned.
   * @return the name of this Object Variable for the given language.
   * @see InternationalizedString#getString(Locale)
   */
  public String getName( Locale locale )
    {
    return this.name.getString( locale );
    }

  /**
   * Sets the name of this Object Variable. The name must be non <code>null</code>, otherwise a {@link NullPointerException} is
   * thrown.
   * 
   * @param name the name of this Object Variable.
   * @throws NullPointerException if the name is <code>null</code>.
   */
  public void setName( String name )
    {
    if( name != null )
      this.name.setString( name );
    else
      throw new NullPointerException( );
    }

  /**
   * Sets the name of this Object Variable for the given language. The language and name must be non <code>null</code>, otherwise a
   * {@link NullPointerException} is thrown.
   * 
   * @param locale the language of the name to be set.
   * @param name the name of this Object Variable.
   * @throws NullPointerException if the language or name are <code>null</code>.
   */
  public void setName( Locale locale, String name )
    {
    if( name != null )
      this.name.setString( locale, name );
    else
      throw new NullPointerException( );
    }


  public InternationalizedString getInternationalizedDescription( )
    {
    return this.description;
    }

  /**
   * Returns the description of this Object Variable.
   * 
   * @return the description of this Object Variable for the default language.
   * @see InternationalizedString#getString()
   */
  public String getDescription( )
    {
    return this.description.getString( );
    }

  /**
   * Returns the description of this Object Variable for the given language.
   * 
   * @param locale the language of the Object Variable to be returned.
   * @return the description of this Object Variable for the given language.
   * @see InternationalizedString#getString(Locale)
   */
  public String getDescription( Locale locale )
    {
    return this.description.getString( locale );
    }

  /**
   * Sets the description of this Object Variable. The description must be non <code>null</code>, otherwise a
   * {@link NullPointerException} is thrown.
   * 
   * @param description the description of this Object Variable.
   * @throws NullPointerException if the description is <code>null</code>.
   */
  public void setDescription( String description )
    {
    if( description != null )
      this.description.setString( description );
    else
      throw new NullPointerException( );
    }

  /**
   * Sets the description of this Object Variable for the given language. The language and description must be non <code>null</code>,
   * otherwise a {@link NullPointerException} is thrown.
   * 
   * @param locale the language of the description to be set.
   * @param description the description of this Object Variable.
   * @throws NullPointerException if the language or description are <code>null</code>.
   */
  public void setDescription( Locale locale, String description )
    {
    if( description != null )
      this.description.setString( locale, description );
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
