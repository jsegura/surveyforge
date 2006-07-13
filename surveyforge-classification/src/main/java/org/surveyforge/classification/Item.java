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
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.IndexColumn;

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
  private static final long serialVersionUID = -3965211400312532582L;

  @Id
  @Column(length = 50)
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "uuid")
  private String            id;
  /** Version for optimistic locking. */
  @javax.persistence.Version
  private int               lockingVersion;
  /** The level this item belongs to. */
  @ManyToOne(optional = false)
  private Level             level;
  // TODO: Should we make any distinction among alphabetical, numericcal and alphanumerical codes?
  /**
   * A classification item is identified by an alphabetical, numerical or alphanumerical code, which is in line with the code structure
   * of the classification level. The code is unique within the classification version or variant to which the item belongs.
   */
  @Column(length = 50)
  private String            code;
  /** A classification item has a title as provided by the owner or maintenance unit. The title describes the content of the category. */
  @Column(length = 250)
  private String            oficialTitle;
  /**
   * An item can be expressed in terms of one or several alternative titles. Each alternative title is associated with a title type.
   * Ex.: Short titles; Medium titles; Self-explanatory titles in CN; Titles in plural form (e.g. Men, Women) for dissemination
   * purposes; gender related titles.
   */
  // @CollectionOfElements
  // @MapKey(columns = {@Column(name = "titleType", length = 100)})
  // @Column(name = "alternativeTitle", length = 250)
  // private Map<String, String> alternativeTitles = new HashMap<String, String>( );
  // TODO: Explanatory notes
  /**
   * Indicates whether or not the item has been generated to make the level to which it belongs complete. Ex.: In NACE Rev.1 one may
   * generate items AA, BB, FF etc. to make the subsection level complete.
   */
  private boolean           generated;
  // TODO: currently valid and validity periods
  /** Describes the changes, which the item has been subject to from the previous to the actual classification version or variant. */
  @Column(length = 2500)
  private String            changes;
  /** Describes the changes, which the item has been subject to during the life time of the actual classification version or variant. */
  @Column(length = 2500)
  private String            updates;
  /**
   * The item at the next higher level of the classification version or variant of which the actual item is a sub item. Ex.: In NACE
   * Rev.1 item 10 is the parent of item 10.1.
   */
  @ManyToOne
  private Item              parentItem;
  /**
   * Each item, which is not at the lowest level of the classification version or variant, might contain one or a number of sub items,
   * i.e. items at the next lower level of the classification version or variant. Ex.: In NACE Rev.1, the Group level items 10.1, 10.2
   * and 10.3 are sub items of the Division level item 10.
   */
  @OneToMany(mappedBy = "parentItem", fetch = FetchType.LAZY)
  @IndexColumn(name = "subItemIndex")
  private List<Item>        subItems         = new ArrayList<Item>( );

  // TODO: Linked items
  // TODO: Case laws
  // TODO: Index entries
  // TODO: What about classification translations?

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
   * Returns the description of the category represented by this item.
   * 
   * @return the description of the category represented by this item.
   */
  public String getOficialTitle( )
    {
    return this.oficialTitle;
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
      this.oficialTitle = oficialTitle;
    else
      throw new NullPointerException( );
    }

  /**
   * Returns the alternative description of this category of the requested type. The requested title type must be among the title types
   * defined for the classification version this item belongs to ({@see Version#getTitleTypes()}), otherwise this method throws a
   * {@link IllegalArgumentException}. If no alternative title has been defined for the requested title type this method returns
   * <code>null</code>.
   * 
   * @param titleType the type of the requested alternative title.
   * @return the alternative description of this category of the requested type, <code>null</code> if no alternative title has been
   *         defined for the requested title type.
   * @throws IllegalArgumentException if the requested title type is not among the title types of the classification version this item
   *           belongs to.
   */
  // public String getAlternativeTitle( String titleType )
  // {
  // if( this.getVersion( ).getTitleTypes( ).contains( titleType ) )
  // {
  // return this.alternativeTitles.get( titleType );
  // }
  // else
  // throw new IllegalArgumentException( );
  // }
  /**
   * Sets the alternative description of this category of the requested type. The title type must be among the title types defined for
   * the classification version this item belongs to ({@see Version#getTitleTypes()}), otherwise this method throws a
   * {@link IllegalArgumentException}.
   * 
   * @param titleType the type of the alternative title to be set.
   * @param alternativeTitle the alternative description of this category of the requested type.
   * @throws IllegalArgumentException if the requested title type is not among the title types of the classification version this item
   *           belongs to.
   */
  // public void setAlternativeTitle( String titleType, String alternativeTitle )
  // {
  // if( this.getVersion( ).getTitleTypes( ).contains( titleType ) )
  // {
  // this.alternativeTitles.put( titleType, alternativeTitle );
  // }
  // else
  // throw new IllegalArgumentException( );
  // }
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
  }
