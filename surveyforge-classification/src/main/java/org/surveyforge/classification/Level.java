/* 
 * surveyforge-classification - Copyright (C) 2006 OPEN input - http://www.openinput.com/
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
package org.surveyforge.classification;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
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
 * A classification structure (classification version or classification variant) is composed of one or several levels. In a
 * hierarchical classification the items of each level but the highest (most aggregated) level are aggregated to the nearest higher
 * level. A linear classification has only one level.
 * 
 * @author jgonzalez
 */
@Entity
// @Table(schema = "classification")
public class Level implements Serializable
  {
  private static final long serialVersionUID = -8542980068158519003L;

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
  /** The version this level belongs to. */
  @ManyToOne
  @JoinColumn(name = "version_id", insertable = false, updatable = false)
  private Version           version;
  /** The name given to the level. Ex.: Sections, Sub-sections, Divisions, Groups and Classes in NACE Rev.1. */
  @Column(length = 50)
  private String            name;
  /**
   * Text describing the content and particular purpose of the level. Ex.: NACE sub-sections were introduced to provide more detail at
   * an aggregate level.
   */
  @Column(length = 250)
  private String            description;
  // TODO: Should we make any distinctions here?
  // /** Indicates whether the item code at the level is alphabetical, numerical or alphanumerical. */
  // private int codeType;
  /**
   * Indicates how the code is constructed of numbers, letters and separators. Ex. In NACE Rev.1 the code structure at the Class level
   * is 99.99
   */
  @Column(length = 50)
  private String            codeStructure;
  /**
   * Rule for the construction of dummy codes from the codes of the next higher level when an incomplete level is made complete. Ex.:
   * The NACE Rev.1 subsection dummy codes may be created by doubling the section code letter, i.e. Section code: B, subsection dummy
   * code: BB
   */
  @Column(length = 50)
  private String            dummyCode;
  /**
   * A foreign level is a level that has been inherited from another classification version and is owned and maintained by the owners
   * and maintenance unit of that classification version.
   */
  @ManyToOne
  private Level             foreignLevel;
  /** An ordered list of the categories (classification items) that constitute the level. */
  @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
  @IndexColumn(name = "itemIndex")
  @JoinColumn(name = "level_id", nullable = false)
  private List<Item>        items            = new ArrayList<Item>( );

  /** Constructor provided for persistence engine. */
  private Level( )
    {}

  /**
   * Creates a new level belonging to a version, with the given identifier. Both parameters must be non null, otherwise this
   * constructor will throw a {@link NullPointerException}.
   * 
   * @param version the version this level will belong to.
   * @param name the name of this level.
   * @throws NullPointerException if version is <code>null</code> or name is <code>null</code> or the empty string.
   */
  public Level( Version version, String name )
    {
    this.setName( name );
    if( version != null )
      {
      this.version = version;
      this.version.addLevel( this );
      }
    else
      throw new NullPointerException( );
    }

  /**
   * Returns the version this level belongs to.
   * 
   * @return the version this level belongs to.
   */
  public Version getVersion( )
    {
    return this.version;
    }

  /**
   * Returns the number associated with the level. Levels are numbered consecutively starting with level 1 at the highest (most
   * aggregated) level.
   * 
   * @return the number associated with the level.
   */
  public int getNumber( )
    {
    return this.getVersion( ).getLevels( ).indexOf( this ) + 1;
    }

  /**
   * @return Returns the name.
   */
  public String getName( )
    {
    return this.name;
    }

  /**
   * @param name The name to set.
   */
  public void setName( String name )
    {
    if( name != null )
      this.name = name;
    else
      throw new NullPointerException( );
    }

  /**
   * @return Returns the description.
   */
  public String getDescription( )
    {
    return this.description;
    }

  /**
   * @param description The description to set.
   */
  public void setDescription( String description )
    {
    this.description = description;
    }

  /**
   * Returns the number of items (categories) at the level.
   * 
   * @return the number of items (categories) at the level.
   */
  public int getNumberOfItems( )
    {
    return this.getItems( ).size( );
    }

  // /**
  // * @return Returns the codeType.
  // */
  // public int getCodeType( )
  // {
  // return this.codeType;
  // }
  //
  // /**
  // * @param codeType The codeType to set.
  // */
  // public void setCodeType( int codeType )
  // {
  // this.codeType = codeType;
  // }
  //
  /**
   * @return Returns the codeStructure.
   */
  public String getCodeStructure( )
    {
    return this.codeStructure;
    }

  /**
   * @param codeStructure The codeStructure to set.
   */
  public void setCodeStructure( String codeStructure )
    {
    this.codeStructure = codeStructure;
    }

  /**
   * Returns <code>true</code> if this level covers the whole range of the classification version. Incomplete coverage means that one
   * or several items at the nearest higher level are not further subdivided. The topmost level is considered to always fully cover the
   * classification version, so for topmost levels this method always returns <code>true</code>.
   * 
   * @return <code>true</code> if this level fully covers the next higher level or if this level is the topmost level,
   *         <code>false</code> otherwise.
   */
  public boolean isFullyCovered( )
    {
    if( this.getNumber( ) == 1 )
      return true; // The topmost level always returns true, as there is no sense in covering a higher level.
    else
      {
      Level higherLevel = this.getVersion( ).getLevels( ).get( this.getNumber( ) - 2 ); // Level numbers are 1 based, while list
      // indexes are 0 based
      boolean fullyCovered = true;
      Iterator<Item> itemsInHigherLevel = higherLevel.getItems( ).iterator( );
      while( itemsInHigherLevel.hasNext( ) && fullyCovered )
        {
        fullyCovered &= !itemsInHigherLevel.next( ).getSubItems( ).isEmpty( );
        }
      return fullyCovered;
      }
    }

  /**
   * @return Returns the dummyCode.
   */
  public String getDummyCode( )
    {
    return this.dummyCode;
    }

  /**
   * @param dummyCode The dummyCode to set.
   */
  public void setDummyCode( String dummyCode )
    {
    this.dummyCode = dummyCode;
    }

  /**
   * @return Returns the foreignLevel.
   */
  public Level getForeignLevel( )
    {
    return this.foreignLevel;
    }

  /**
   * @param foreignLevel The foreignLevel to set.
   */
  public void setForeignLevel( Level foreignLevel )
    {
    this.foreignLevel = foreignLevel;
    }

  /**
   * Returns the items contained in this level.
   * 
   * @return the items contained in this level.
   */
  public List<Item> getItems( )
    {
    return Collections.unmodifiableList( this.items );
    }

  /**
   * Adds a item to this level. The item must have its level attribute set to this level, in other case this method will throw a
   * {@link IllegalArgumentException}. This method will throw a {@link NullPointerException} if the item is <code>null</code>.
   * 
   * @param item the item to be added to this level.
   * @throws NullPointerException if the item is <code>null</code>.
   * @throws IllegalArgumentException if the item has not its level attribute set to this level.
   */
  protected void addItem( Item item )
    {
    if( item.getLevel( ) == this )
      {
      this.items.add( item );
      }
    else
      throw new IllegalArgumentException( );
    }

  /**
   * Removes a item from this level. The item must not have its level attribute set to this level, in other case this method will throw
   * a {@link IllegalArgumentException}. This method will throw a {@link NullPointerException} if the item is <code>null</code>.
   * 
   * @param item the item to be removed from this level.
   * @throws NullPointerException if the item is <code>null</code>.
   * @throws IllegalArgumentException if the item has its level attribute set to this level.
   */
  protected void removeItem( Item item )
    {
    if( item.getLevel( ) != this )
      {
      this.items.remove( item );
      }
    else
      throw new IllegalArgumentException( );
    }

  @Override
  public boolean equals( Object object )
    {
    Level otherLevel = (Level) object;
    return this.getVersion( ).equals( otherLevel.getVersion( ) ) && this.getName( ) == otherLevel.getName( );
    }

  @Override
  public int hashCode( )
    {
    return this.getVersion( ).hashCode( ) ^ this.getNumber( );
    }
  }
