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

import java.util.Date;
import java.util.List;

/**
 * <p>
 * A classification variant is based on a classification version. In a variant, the categories of the classification version are split,
 * aggregated or regrouped to provide additions or alternatives (e.g. context-specific additions) to the standard structure of the base
 * version. A classification variant has two or more levels, which are either base version levels (i.e. levels of the classification
 * version on which the variant is based) or the new levels created for the variant. At least one of the levels must be (part of) a
 * base version level, which defines the relation of the variant to the base version. The term variant does not refer only to the
 * partial structure composed of the new variant levels. It refers to the whole new structure composed of both base version levels and
 * new variant levels.
 * </p>
 * <p>
 * Variants are commonly of three kinds. These have been named extension variants, aggregate variants or regrouping variants. There
 * could exist other types of variants.
 * <ul>
 * <li> Extension variant: An extension variant is a classification variant which extends the base classification version with one or
 * several new levels at the bottom of the base classification version, creating a new lowest level. An extension variant thus adds new
 * lower levels to the base classification version but does not otherwise alter its original structure. </li>
 * <li> Aggregate variant: An aggregate variant is a classification variant which groups the categories of a linear classification
 * version to create one or several aggregate level(s), thus creating a hierarchy. </li>
 * <li> Regrouping variant: A regrouping variant is a classification variant which introduces additional or alternative aggregate
 * levels by regrouping categories of the base classification version. Two types of regrouping variants have been identified:
 * <ul>
 * <li> Regrouping variants which do not violate the structure of the base version: This type of regrouping variant introduces a new
 * level or new levels on top of, or in between existing levels of a hierarchical classification version without otherwise altering the
 * original structure of the hierarchy. This regrouping variant consists of all classification levels of the base version plus the new
 * variant level(s). The parent level (if any) of the new variant level can be either another variant level or a base version level.
 * </li>
 * <li> Regrouping variants which violate the structure of the base version: This type of regrouping variant introduces a new level or
 * new levels on top of any but the topmost level of a hierarchical classification version by regrouping categories of the base
 * classification version in a way which violates its original order and structure. This regrouping variant consists of all
 * classification levels of the base version below the new variant level(s) plus the new variant level(s). In such a regrouping
 * variant, a new variant level cannot have a base version level as parent level. </li>
 * </ul>
 * </li>
 * </ul>
 * </p>
 * <p>
 * In all variants but regrouping variants which violate the structure of the base version, all levels of the base version are retained
 * and one or more new levels are inserted. In regrouping variants which violate the structure of the base version, one or more new
 * levels are inserted and only the base version levels below the new variant levels are retained.
 * </p>
 * 
 * @author jgonzalez
 */
public class Variant extends Version
  {
  private static final long serialVersionUID = -7083593360508439002L;

  /** A classification variant is based on one specific classification version. */
  private Version           baseVersion;
  /**
   * The levels inherited from the classification version on which the variant is based and which bind the variant to the originating
   * version.
   */
  private List<Level>       baseVersionLevels;

  /**
   * Creates a new variant belonging to a classification, with the given identifier and based on the given version. All the parameters
   * must be non null, otherwise this constructor will throw a {@link NullPointerException}.
   * 
   * @param classification the classification this variant will belong to.
   * @param baseVersion the version this variant is based on.
   * @param identifier the identifier of this variant.
   * @throws NullPointerException if classification or baseVersion is <code>null</code> or identifier is <code>null</code> or the
   *           empty string.
   */
  public Variant( Classification classification, Version baseVersion, String identifier, Date releaseDate )
    {
    super( classification, identifier, releaseDate );
    this.setBaseVersion( baseVersion );
    }

  /**
   * Returns the version this variant is based on.
   * 
   * @return the version this variant is based on.
   */
  public Version getBaseVersion( )
    {
    return this.baseVersion;
    }

  /**
   * Sets the version this variant is based on.
   * 
   * @param baseVersion the version this variant is based on.
   */
  public void setBaseVersion( Version baseVersion )
    {
    this.baseVersion = baseVersion;
    }

  /**
   * Returns the levels inherited from the base version.
   * 
   * @return the levels inherited from the base version.
   */
  public List<Level> getBaseVersionLevels( )
    {
    return this.baseVersionLevels;
    }

  /**
   * Sets the levels inherited from the base version.
   * 
   * @param baseVersionLevels the levels inherited from the base version.
   */
  public void setBaseVersionLevels( List<Level> baseVersionLevels )
    {
    this.baseVersionLevels = baseVersionLevels;
    }
  }
