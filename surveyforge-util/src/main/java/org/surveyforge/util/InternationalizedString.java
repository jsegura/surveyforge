/* 
 * surveyforge-util - Copyright (C) 2006 OPEN input - http://www.openinput.com/
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
package org.surveyforge.util;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.CollectionOfElements;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.MapKey;

// TODO: Write javadoc comments
// TODO: This should be modeled as an embeddable object, but we have been unable to find a proper mapping using Hibernate annotations.
/**
 * @author jgonzalez
 */
@Entity
public class InternationalizedString implements Serializable
  {
  private static final long   serialVersionUID = 4441883710391359007L;

  @SuppressWarnings("unused")
  @Id
  @Column(length = 50)
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "uuid")
  private String              id;
  /** Version for optimistic locking. */
  @SuppressWarnings("unused")
  @javax.persistence.Version
  private int                 lockingVersion;
  @CollectionOfElements
  @MapKey(columns = {@Column(name = "locale", length = 25)})
  @Column(name = "string", length = 2500)
  private Map<Locale, String> strings          = new HashMap<Locale, String>( );
  @Column(length = 25)
  private Locale              defaultLocale    = Locale.getDefault( );

  public InternationalizedString( )
    {}

  public InternationalizedString( Locale defaultLocale )
    {
    this.setDefaultLocale( defaultLocale );
    }

  public InternationalizedString( InternationalizedString otherString )
    {
    this.strings = new HashMap<Locale, String>( otherString.strings );
    this.defaultLocale = otherString.defaultLocale;
    }

  public Locale getDefaultLocale( )
    {
    return this.defaultLocale;
    }

  public void setDefaultLocale( Locale defaultLocale )
    {
    if( defaultLocale != null )
      this.defaultLocale = defaultLocale;
    else
      this.defaultLocale = Locale.getDefault( );
    }

  public String getString( )
    {
    if( this.strings.containsKey( this.getDefaultLocale( ) ) )
      return this.strings.get( this.getDefaultLocale( ) );
    else if( this.strings.containsKey( new Locale( this.getDefaultLocale( ).getLanguage( ), this.getDefaultLocale( ).getCountry( ) ) ) )
      return this.strings.get( new Locale( this.getDefaultLocale( ).getLanguage( ), this.getDefaultLocale( ).getCountry( ) ) );
    else if( this.strings.containsKey( new Locale( this.getDefaultLocale( ).getLanguage( ) ) ) )
      return this.strings.get( new Locale( this.getDefaultLocale( ).getLanguage( ) ) );
    else
      return null;
    }

  public String getString( Locale locale )
    {
    if( this.strings.containsKey( locale ) )
      return this.strings.get( locale );
    else if( this.strings.containsKey( new Locale( locale.getLanguage( ), locale.getCountry( ) ) ) )
      return this.strings.get( new Locale( locale.getLanguage( ), locale.getCountry( ) ) );
    else if( this.strings.containsKey( new Locale( locale.getLanguage( ) ) ) )
      return this.strings.get( new Locale( locale.getLanguage( ) ) );
    else
      return this.getString( );
    }

  public void setString( String string )
    {
    this.strings.put( this.getDefaultLocale( ), string );
    }

  public void setString( Locale locale, String string )
    {
    if( locale != null )
      {
      if( string != null )
        this.strings.put( locale, string );
      else
        this.removeString( locale );
      }
    else
      throw new NullPointerException( );
    }

  public void removeString( Locale locale )
    {
    this.strings.remove( locale );
    }

  public Set<Locale> getLocales( )
    {
    return Collections.unmodifiableSet( this.strings.keySet( ) );
    }
  }
