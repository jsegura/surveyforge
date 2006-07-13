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
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;
import org.surveyforge.util.InternationalizedString;

/**
 * A classification family comprises a number of {@link org.surveyforge.classification.Classification}s, which are related from a
 * certain point of view. The family may be based, for instance, on a common classification variable (e.g. economic activity) or on a
 * common type of object/unit. Different classification databases may use different types of classification families and have different
 * names for the families, as no standard has been agreed upon.
 * 
 * @author jgonzalez
 */
@Entity
// @Table(schema = "classification")
public class Family implements Serializable
  {
  private static final long       serialVersionUID = -2041101270919820504L;

  @Id
  @Column(length = 50)
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "uuid")
  private String                  id;
  /** Version for optimistic locking. */
  @javax.persistence.Version
  private int                     lockingVersion;
  /** A classification family is identified by a unique identifier. */
  @Basic(optional = false)
  @Column(length = 50, nullable = false, unique = true)
  private String                  identifier;
  /** A classification family has a title. */
  @ManyToOne
  private InternationalizedString title;
  /** A classification family refers to a number of classifications. */
  @OneToMany(mappedBy = "family", fetch = FetchType.LAZY)
  private Set<Classification>     classifications  = new HashSet<Classification>( );

  /**
   * Creates a new family with the given identifier.
   * 
   * @param identifier the identifier of the new family.
   */
  public Family( String identifier )
    {
    this.setIdentifier( identifier );
    }

  /**
   * Returns the identifier of this family.
   * 
   * @return the identifier of this family.
   */
  public String getIdentifier( )
    {
    return identifier;
    }

  /**
   * Sets the identifier of this family. This method throws a {@link NullPointerException} if the provided identifier is
   * <code>null</code> or the empty string.
   * 
   * @param identifier the new identifier of this family.
   * @throws NullPointerException if the identifier is <code>null</code> or the empty string.
   */
  public void setIdentifier( String identifier )
    {
    if( identifier != null && !identifier.equals( "" ) )
      this.identifier = identifier;
    else
      throw new NullPointerException( );
    }

  /**
   * Returns the title of this family.
   * 
   * @return the title of this family.
   */
  public InternationalizedString getTitle( )
    {
    return title;
    }

  /**
   * Sets the title of this family.
   * 
   * @param title the new title of this family.
   */
  public void setTitle( InternationalizedString title )
    {
    this.title = title;
    }

  /**
   * Returns the classifications included in this family. The returned set is unmodifiable.
   * 
   * @return the classifications included in this family.
   */
  public Set<Classification> getClassifications( )
    {
    return Collections.unmodifiableSet( classifications );
    }

  /**
   * Adds a classification to this family. The classification must have its family attribute set to this family, in other case this
   * method will throw a {@link IllegalArgumentException}. This method will throw a {@link NullPointerException} if the classification
   * is <code>null</code>.
   * 
   * @param classification the classification to be added to this family.
   * @throws NullPointerException if the classification is <code>null</code>.
   * @throws IllegalArgumentException if the classification has not its family attribute set to this family.
   */
  protected void addClassification( Classification classification )
    {
    if( classification.getFamily( ) == this )
      this.classifications.add( classification );
    else
      throw new IllegalArgumentException( );
    }

  /**
   * Removes a classification from this family. The classification must not have its family attribute set to this family, in other case
   * this method will throw a {@link IllegalArgumentException}. This method will throw a {@link NullPointerException} if the
   * classification is <code>null</code>.
   * 
   * @param classification the classification to be removed from this family.
   * @throws NullPointerException if the classification is <code>null</code>.
   * @throws IllegalArgumentException if the classification has its family attribute set to this family.
   */
  protected void removeClassification( Classification classification )
    {
    if( classification.getFamily( ) != this )
      this.classifications.remove( classification );
    else
      throw new IllegalArgumentException( );
    }
  }
