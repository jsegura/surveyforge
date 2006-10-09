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
 * The global variable defines the general concept of a statistical variable (e.g. income). More special definitions can be provided
 * when combining a global variable with a statistical object to an {@link ObjectVariable}. Variable families can group global
 * variables.
 * 
 * @author jsegura
 */
@Entity
public class GlobalVariable implements Serializable
  {
  private static final long       serialVersionUID = 1661678204467740737L;

  @SuppressWarnings("unused")
  @Id
  @Column(length = 50)
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "uuid")
  private String                  id;
  /** Version for optimistic locking. */
  @SuppressWarnings("unused")
  @javax.persistence.Version
  private int                     lockingVersion;

  /** A global variable is identified by a unique language independent identifier, which may typically be an abbreviation of its name. */
  @Column(unique = true, length = 50)
  private String                  identifier;
  /** The official name of the global variable is provided by the owner of the variable. */
  @ManyToOne(cascade = {CascadeType.ALL})
  private InternationalizedString name             = new InternationalizedString( );
  /**
   * Short general multilingual description of the global variable, including its purpose, its main subject areas etc.
   */
  @ManyToOne(cascade = {CascadeType.ALL})
  private InternationalizedString description      = new InternationalizedString( );

  /** Family to which the global variable belongs. */
  @ManyToOne
  @JoinColumn(name = "variableFamily_id", insertable = false, updatable = false)
  private VariableFamily          variableFamily;


  protected GlobalVariable( )
    {}

  /**
   * Creates a new GlobalVariable identified by the identifier and placed in the variableFamily.
   * 
   * @param identifier The identifier of the GlobalVariable.
   * @param variableFamily The Variable Family where the GlobalVariable will be placed.
   * @throws NullPointerException If the identifier is <code>null</code> or is empty.
   */
  public GlobalVariable( String identifier, VariableFamily variableFamily )
    {
    this.setIdentifier( identifier );
    this.setVariableFamily( variableFamily );
    }

  /**
   * Creates a new GlobalVariable identified by the identifier.
   * 
   * @param identifier The identifier of the GlobalVariable.
   * @throws NullPointerException If the identifier is <code>null</code> or is empty.
   */
  public GlobalVariable( String identifier )
    {
    this.setIdentifier( identifier );
    }


  /**
   * Returns the identifier of the Global Variable.
   * 
   * @return Returns the identifier.
   */
  public String getIdentifier( )
    {
    return this.identifier;
    }

  /**
   * Sets a new identifier for the GlobalVariable.
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
   * Returns the name of this Global Variable.
   * 
   * @return the name of this Global Variable for the default language.
   * @see InternationalizedString#getString()
   */
  public String getName( )
    {
    return this.name.getString( );
    }

  /**
   * Returns the name of this Global Variable for the given language.
   * 
   * @param locale the language of the Global Variable to be returned.
   * @return the name of this Global Variable for the given language.
   * @see InternationalizedString#getString(Locale)
   */
  public String getName( Locale locale )
    {
    return this.name.getString( locale );
    }

  /**
   * Sets the name of this Global Variable. The name must be non <code>null</code>, otherwise a {@link NullPointerException} is
   * thrown.
   * 
   * @param name the name of this Global Variable.
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
   * Sets the name of this Global Variable for the given language. The language and name must be non <code>null</code>, otherwise a
   * {@link NullPointerException} is thrown.
   * 
   * @param locale the language of the name to be set.
   * @param name the name of this Global Variable.
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
   * Returns the description of this Global Variable.
   * 
   * @return the description of this Global Variable for the default language.
   * @see InternationalizedString#getString()
   */
  public String getDescription( )
    {
    return this.description.getString( );
    }

  /**
   * Returns the description of this Global Variable for the given language.
   * 
   * @param locale the language of the Global Variable to be returned.
   * @return the description of this Global Variable for the given language.
   * @see InternationalizedString#getString(Locale)
   */
  public String getDescription( Locale locale )
    {
    return this.description.getString( locale );
    }

  /**
   * Sets the description of this Global Variable. The description must be non <code>null</code>, otherwise a
   * {@link NullPointerException} is thrown.
   * 
   * @param description the description of this Global Variable.
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
   * Sets the description of this Global Variable for the given language. The language and description must be non <code>null</code>,
   * otherwise a {@link NullPointerException} is thrown.
   * 
   * @param locale the language of the description to be set.
   * @param description the description of this Global Variable.
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
   * Returns the {@link VariableFamily} of the GlobalVariable.
   * 
   * @return Returns the variableFamily.
   */
  public VariableFamily getVariableFamily( )
    {
    return this.variableFamily;
    }

  /**
   * Sets the new {@link VariableFamily} of the GlobalVariable.
   * 
   * @param variableFamily The variableFamily to set.
   */
  public void setVariableFamily( VariableFamily variableFamily )
    {
    if( this.variableFamily != null ) this.variableFamily.removeGlobalVariable( this );
    this.variableFamily = variableFamily;
    if( this.variableFamily != null ) this.variableFamily.addGlobalVariable( this );
    }

  @Override
  public boolean equals( Object object )
    {
    GlobalVariable other = (GlobalVariable) object;
    return this.getIdentifier( ).equals( other.getIdentifier( ) ) && this.getVariableFamily( ).equals( other.getVariableFamily( ) );
    }

  @Override
  public int hashCode( )
    {
    return this.getIdentifier( ).hashCode( ) ^ (this.getVariableFamily( ) == null ? 0 : this.getVariableFamily( ).hashCode( ));
    }

  }
