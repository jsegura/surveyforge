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
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.IndexColumn;
import org.hibernate.annotations.MapKey;
import org.surveyforge.util.InternationalizedString;

/**
 * A classification item represents a category at a certain level within a classification version or variant. It defines the content
 * and the borders of the category. A statistical object/unit can be classified to one and only one item at each level of a
 * classification version or variant. An item is associated with a code and a title and may include explanatory notes.
 * 
 * @author jgonzalez
 */
@Entity
// @Table(schema = "classification")
public class Item implements Serializable
  {
  private static final long                    serialVersionUID  = -3965211400312532582L;

  @SuppressWarnings("unused")
  @Id
  @Column(length = 50)
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "uuid")
  private String                               id;
  /** Version for optimistic locking. */
  @SuppressWarnings("unused")
  @javax.persistence.Version
  private int                                  lockingVersion;
  /** The level this item belongs to. */
  @ManyToOne
  @JoinColumn(name = "level_id", insertable = false, updatable = false)
  private Level                                level;
  // TODO: Should we make any distinction among alphabetical, numericcal and alphanumerical codes?
  /**
   * A classification item is identified by an alphabetical, numerical or alphanumerical code, which is in line with the code structure
   * of the classification level. The code is unique within the classification version or variant to which the item belongs.
   */
  @Column(length = 50)
  private String                               code;
  /** A classification item has a title as provided by the owner or maintenance unit. The title describes the content of the category. */
  @ManyToOne(cascade = {CascadeType.ALL})
  private InternationalizedString              oficialTitle;
  /**
   * An item can be expressed in terms of one or several alternative titles. Each alternative title is associated with a title type.
   * Ex.: Short titles; Medium titles; Self-explanatory titles in CN; Titles in plural form (e.g. Men, Women) for dissemination
   * purposes; gender related titles.
   */
  @OneToMany(cascade = {CascadeType.ALL})
  @JoinTable(name = "item_alternativeTitles")
  @MapKey(columns = {@Column(name = "titleType", length = 100)})
  private Map<String, InternationalizedString> alternativeTitles = new HashMap<String, InternationalizedString>( );
  // TODO: Explanatory notes
  /**
   * Indicates whether or not the item has been generated to make the level to which it belongs complete. Ex.: In NACE Rev.1 one may
   * generate items AA, BB, FF etc. to make the subsection level complete.
   */
  private boolean                              generated;
  // TODO: currently valid and validity periods
  /** Describes the changes, which the item has been subject to from the previous to the actual classification version or variant. */
  @Column(length = 2500)
  private String                               changes;
  /** Describes the changes, which the item has been subject to during the life time of the actual classification version or variant. */
  @Column(length = 2500)
  private String                               updates;
  /**
   * The item at the next higher level of the classification version or variant of which the actual item is a sub item. Ex.: In NACE
   * Rev.1 item 10 is the parent of item 10.1.
   */
  @ManyToOne
  @JoinColumn(name = "parentItem_id", insertable = false, updatable = false)
  private Item                                 parentItem;
  /**
   * Each item, which is not at the lowest level of the classification version or variant, might contain one or a number of sub items,
   * i.e. items at the next lower level of the classification version or variant. Ex.: In NACE Rev.1, the Group level items 10.1, 10.2
   * and 10.3 are sub items of the Division level item 10.
   */
  @OneToMany(fetch = FetchType.LAZY)
  @IndexColumn(name = "subItemIndex")
  @JoinColumn(name = "parentItem_id")
  private List<Item>                           subItems          = new ArrayList<Item>( );

  // TODO: Linked items
  // TODO: Case laws
  // TODO: Index entries
  // TODO: Classification translations - Solved for the moment, we should change the way to access the strings to provide proper
  // control of languages

  /** Constructor provided for persistence engine. */
  private Item( )
    {}

  /**
   * Creates a new item. The item must be included in a level and have a parent item if the level is not the topmost one. Providing a
   * <code>null</code> level, code or oficial title would cause this constructor to throw a {@link NullPointerException}. This item
   * must be included exactly one level below the level of the parent item, or in the case that no parent item is provided, this item
   * must be included in the topmost level; otherwise this constructor will throw a {@link IllegalArgumentException}.
   * 
   * @param level the level this item belongs to.
   * @param parentItem the parent item of this item.
   * @param code the code that identifies this item.
   * @param oficialTitle the description of the category represented by this item.
   * @throws NullPointerException if the level, code or oficial title are <code>null</code>.
   * @throws IllegalArgumentException if the parent item is <code>null</code> and level is not the topmost one, or if the level of
   *           this item is not exactly one level below the level of the the parent item.
   */
  public Item( Level level, Item parentItem, String code, String oficialTitle )
    {
    if( (parentItem == null && level.getNumber( ) == 1) || (level.getNumber( ) == parentItem.getLevelNumber( ) + 1) )
      {
      this.level = level;
      this.parentItem = parentItem;
      this.setCode( code );
      this.oficialTitle = new InternationalizedString( this.getVersion( ).getDefaultLanguage( ) );
      this.setOficialTitle( oficialTitle );

      // Reverse relations
      this.level.addItem( this );
      if( this.parentItem != null ) this.parentItem.subItems.add( this );
      }
    else
      throw new IllegalArgumentException( );
    }

  /**
   * Returns the level this item belongs to.
   * 
   * @return the level this item belongs to.
   */
  public Level getLevel( )
    {
    return this.level;
    }

  /**
   * Returns the code the identifies this item.
   * 
   * @return the code the identifies this item.
   */
  public String getCode( )
    {
    return this.code;
    }

  /**
   * Sets the code the identifies this item. The code must be non <code>null</code>, otherwise a {@link NullPointerException} is
   * thrown.
   * 
   * @param code the code the identifies this item.
   * @throws NullPointerException if the code is <code>null</code>.
   */
  public void setCode( String code )
    {
    if( code != null )
      this.code = code;
    else
      throw new NullPointerException( );
    }

  /**
   * Returns the description of the category represented by this item for the default language.
   * 
   * @return the description of the category represented by this item for the default language.
   * @see InternationalizedString#getString()
   */
  public String getOficialTitle( )
    {
    return this.oficialTitle.getString( );
    }

  /**
   * Returns the description of the category represented by this item for the given language.
   * 
   * @param locale the language of the oficial title to be returned.
   * @return the description of the category represented by this item for the given language.
   * @see InternationalizedString#getString(Locale)
   */
  public String getOficialTitle( Locale locale )
    {
    return this.oficialTitle.getString( locale );
    }

  /**
   * Sets the description of the category represented by this item. The title must be non <code>null</code>, otherwise a
   * {@link NullPointerException} is thrown.
   * 
   * @param oficialTitle the description of the category represented by this item.
   * @throws NullPointerException if the title is <code>null</code>.
   */
  public void setOficialTitle( String oficialTitle )
    {
    if( oficialTitle != null )
      this.oficialTitle.setString( oficialTitle );
    else
      throw new NullPointerException( );
    }

  /**
   * Sets the description of the category represented by this item for the given language. The language and title must be non
   * <code>null</code>, otherwise a {@link NullPointerException} is thrown.
   * 
   * @param locale the language of the oficial title to be set.
   * @param oficialTitle the description of the category represented by this item.
   * @throws NullPointerException if the language or title are <code>null</code>.
   */
  public void setOficialTitle( Locale locale, String oficialTitle )
    {
    if( oficialTitle != null )
      this.oficialTitle.setString( locale, oficialTitle );
    else
      throw new NullPointerException( );
    }

  /**
   * Returns the alternative description of this category of the requested type for the default language. The requested title type must
   * be among the title types defined for the classification version this item belongs to ({@see Version#getTitleTypes()}), otherwise
   * this method throws a {@link IllegalArgumentException}. If no alternative title has been defined for the requested title type this
   * method returns <code>null</code>.
   * 
   * @param titleType the type of the requested alternative title.
   * @return the alternative description of this category of the requested type, <code>null</code> if no alternative title has been
   *         defined for the requested title type.
   * @throws IllegalArgumentException if the requested title type is not among the title types of the classification version this item
   *           belongs to.
   * @see InternationalizedString#getString()
   */
  public String getAlternativeTitle( String titleType )
    {
    if( this.getVersion( ).getTitleTypes( ).contains( titleType ) )
      {
      return this.alternativeTitles.get( titleType ).getString( );
      }
    else
      throw new IllegalArgumentException( );
    }

  /**
   * Sets the alternative description of this category of the requested type for the default language. The title type must be among the
   * title types defined for the classification version this item belongs to ({@see Version#getTitleTypes()}), otherwise this method
   * throws a {@link IllegalArgumentException}.
   * 
   * @param titleType the type of the alternative title to be set.
   * @param alternativeTitle the alternative description of this category of the requested type.
   * @throws IllegalArgumentException if the requested title type is not among the title types of the classification version this item
   *           belongs to.
   */
  public void setAlternativeTitle( String titleType, String alternativeTitle )
    {
    if( this.getVersion( ).getTitleTypes( ).contains( titleType ) )
      {
      InternationalizedString alternativeI15dString;
      if( this.alternativeTitles.containsKey( titleType ) )
        alternativeI15dString = this.alternativeTitles.get( titleType );
      else
        {
        alternativeI15dString = new InternationalizedString( this.getVersion( ).getDefaultLanguage( ) );
        this.alternativeTitles.put( alternativeTitle, alternativeI15dString );
        }
      alternativeI15dString.setString( alternativeTitle );
      }
    else
      throw new IllegalArgumentException( );
    }

  /**
   * Returns the alternative description of this category of the requested type for the given language. The requested title type must
   * be among the title types defined for the classification version this item belongs to ({@see Version#getTitleTypes()}), otherwise
   * this method throws a {@link IllegalArgumentException}. If no alternative title has been defined for the requested title type this
   * method returns <code>null</code>.
   * 
   * @param titleType the type of the requested alternative title.
   * @param locale the language of the requested alternative title.
   * @return the alternative description of this category of the requested type, <code>null</code> if no alternative title has been
   *         defined for the requested title type.
   * @throws IllegalArgumentException if the requested title type is not among the title types of the classification version this item
   *           belongs to.
   * @see InternationalizedString#getString(Locale)
   */
  public String getAlternativeTitle( String titleType, Locale locale )
    {
    if( this.getVersion( ).getTitleTypes( ).contains( titleType ) )
      {
      return this.alternativeTitles.get( titleType ).getString( locale );
      }
    else
      throw new IllegalArgumentException( );
    }

  /**
   * Sets the alternative description of this category of the requested type for the given language. The title type must be among the
   * title types defined for the classification version this item belongs to ({@see Version#getTitleTypes()}), otherwise this method
   * throws a {@link IllegalArgumentException}.
   * 
   * @param titleType the type of the alternative title to be set.
   * @param locale the language of the alternative title to be set.
   * @param alternativeTitle the alternative description of this category of the requested type.
   * @throws IllegalArgumentException if the requested title type is not among the title types of the classification version this item
   *           belongs to.
   */
  public void setAlternativeTitle( String titleType, Locale locale, String alternativeTitle )
    {
    if( this.getVersion( ).getTitleTypes( ).contains( titleType ) )
      {
      InternationalizedString alternativeI15dString;
      if( this.alternativeTitles.containsKey( titleType ) )
        alternativeI15dString = this.alternativeTitles.get( titleType );
      else
        {
        alternativeI15dString = new InternationalizedString( this.getVersion( ).getDefaultLanguage( ) );
        this.alternativeTitles.put( alternativeTitle, alternativeI15dString );
        }
      alternativeI15dString.setString( locale, alternativeTitle );
      }
    else
      throw new IllegalArgumentException( );
    }

  /**
   * Removes an alternative title.
   * 
   * @param titleType the title type of the alternative title to be removed.
   */
  public void removeAlternativeTitle( String titleType )
    {
    this.alternativeTitles.remove( titleType );
    }

  /**
   * Returns the number of the level to which the item belongs.
   * 
   * @return the number of the level to which the item belongs.
   * @see Level#getNumber()
   */
  public int getLevelNumber( )
    {
    return this.getLevel( ).getNumber( );
    }

  /**
   * Returns whether or not the item has been generated to make the level to which it belongs complete.
   * 
   * @return <code>true</code> if the item has been generated to make the level to which it belongs complete, <code>false</code>
   *         otherwise.
   */
  public boolean isGenerated( )
    {
    return this.generated;
    }

  /**
   * Set to <code>true</code> to indicate that the item has been generated to make the level to which it belongs complete.
   * 
   * @param generated <code>true</code> to indicate that the item has been generated to make the level to which it belongs complete.
   */
  public void setGenerated( boolean generated )
    {
    this.generated = generated;
    }

  /**
   * Returns the changes, which the item has been subject to from the previous to the actual classification version or variant.
   * 
   * @return the changes, which the item has been subject to from the previous to the actual classification version or variant.
   */
  public String getChanges( )
    {
    return this.changes;
    }

  /**
   * Sets the changes, which the item has been subject to from the previous to the actual classification version or variant.
   * 
   * @param changes the changes, which the item has been subject to from the previous to the actual classification version or variant.
   */
  public void setChanges( String changes )
    {
    this.changes = changes;
    }

  /**
   * Returns the changes, which the item has been subject to during the life time of the actual classification version or variant.
   * 
   * @return the changes, which the item has been subject to during the life time of the actual classification version or variant.
   */
  public String getUpdates( )
    {
    return this.updates;
    }

  /**
   * Sets the changes, which the item has been subject to during the life time of the actual classification version or variant.
   * 
   * @param updates the changes, which the item has been subject to during the life time of the actual classification version or
   *          variant.
   */
  public void setUpdates( String updates )
    {
    this.updates = updates;
    }

  /**
   * Returns the classification version or variant to which the item belongs.
   * 
   * @return he classification version or variant to which the item belongs.
   */
  public Version getVersion( )
    {
    return this.getLevel( ).getVersion( );
    }

  /**
   * Returns the parent item of this item.
   * 
   * @return the parent item of this item.
   */
  public Item getParentItem( )
    {
    return this.parentItem;
    }

  /**
   * Returns the items that further subdivide the category represented by this item.
   * 
   * @return the items that further subdivide the category represented by this item.
   */
  public List<Item> getSubItems( )
    {
    return Collections.unmodifiableList( this.subItems );
    }

  @Override
  public boolean equals( Object object )
    {
    Item otherItem = (Item) object;
    return this.getVersion( ).equals( otherItem.getVersion( ) ) && this.getCode( ).equals( otherItem.getCode( ) );
    }

  @Override
  public int hashCode( )
    {
    return this.getVersion( ).hashCode( ) ^ this.getCode( ).hashCode( );
    }
  }
