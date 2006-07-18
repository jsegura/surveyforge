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
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CollectionOfElements;
import org.hibernate.annotations.GenericGenerator;

/**
 * A classification describes the ensemble of one or several consecutive classification {@link org.surveyforge.classification.Version}s.
 * In the context of the classification database, there is no structured list of categories directly associated with the
 * classification. It is a "name" which serves as an umbrella for the classification version(s).
 * 
 * @author jgonzalez
 */
@Entity
// @Table(schema = "classification")
public class Classification implements Serializable
  {
  private static final long serialVersionUID = 4169537105933133532L;

  @Id
  @Column(length = 50)
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "uuid")
  private String            id;
  /** Version for optimistic locking. */
  @javax.persistence.Version
  private int               lockingVersion;
  /** A classification is identified by a unique identifier, which may typically be an abbreviation of its title. */
  @Column(unique = true, length = 50)
  private String            identifier;
  /** A classification has a title as provided by the owner. */
  @Column(unique = true, length = 250)
  private String            title;
  /** Short general description of the classification, including its purpose, its main subject areas etc. */
  @Column(length = 500)
  private String            description;
  /**
   * A classification can be designed in a specific context. Ex.: ISIC: international classification; NACE: EU classification;
   * NACE-BEL: Belgian classification.
   */
  @Column(length = 250)
  private String            context;
  /**
   * A classification is designed to classify a specific type of object/unit according to a specific attribute. Ex.: Enterprises by
   * economic activity, products by origin, persons by age.
   */
  @Column(length = 250)
  private String            objectsClassified;
  /** Areas of statistics in which the classification is implemented. Ex.: ISCO is used in employment and labour force statistics. */
  @Column(length = 250)
  private String            subjectAreas;
  /**
   * The statistical office or other authority, which created and maintains the version(s) of the classification. A classification may
   * have several owners. Ex.: ISIC is owned by UNSD; ISCO-COM is owned by ILO and Eurostat.
   */
  @CollectionOfElements
  @Column(name = "owner", length = 100)
  private Set<String>       owners           = new HashSet<String>( );
  /** A classification can be associated with one or a number of keywords. Ex.: For NACE: ”Economic Activity”; ”Industry”; ”Production”. */
  @CollectionOfElements
  @Column(name = "keyword", length = 100)
  private Set<String>       keywords         = new HashSet<String>( );
  /**
   * Classifications may be grouped into classification {@link Family families}. Shows to which family the classification belongs.
   * Ex.: ISIC and NACE belong to the family ”Classifications of economic activity”.
   */
  @ManyToOne(optional = false)
  private Family            family;
  /**
   * A classification has at least one {@link Version} (classification version). Ex.: ISIC: ISIC Rev.1, ISIC Rev.2, ISIC Rev.3; NACE:
   * NACE 70, NACE Rev.1.
   */
  @OneToMany(mappedBy = "classification", fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
  private Set<Version>      versions         = new HashSet<Version>( );
  /**
   * If there are several versions of a classification, one version is assigned as the currently valid version. Ex.: ISIC Rev. 3; NACE
   * Rev. 1.
   */
  @ManyToOne
  private Version           currentVersion;

  /**
   * Creates a new classification belonging to a family, with the given identifier. Both parameters must be non null, otherwise this
   * constructor will throw a {@link NullPointerException}.
   * 
   * @param family the family this classification will belong to.
   * @param identifier the identifier of this classification.
   * @throws NullPointerException if family is <code>null</code> or identifier is <code>null</code> or the empty string.
   */
  public Classification( Family family, String identifier )
    {
    this.setIdentifier( identifier );
    this.setFamily( family );
    }

  /**
   * Returns the identifier of this classification.
   * 
   * @return the identifier of this classification.
   */
  public String getIdentifier( )
    {
    return this.identifier;
    }

  /**
   * Sets the identifier of this classification. This method throws a {@link NullPointerException} if the provided identifier is
   * <code>null</code> or the empty string.
   * 
   * @param identifier the new identifier of this classification.
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
   * Returns the title of this classification.
   * 
   * @return the title of this classification.
   */
  public String getTitle( )
    {
    return this.title;
    }

  /**
   * Sets the title of this classification.
   * 
   * @param title the new title of this classification.
   */
  public void setTitle( String title )
    {
    this.title = title;
    }

  /**
   * Returns the description of this classification.
   * 
   * @return the description of this classification.
   */
  public String getDescription( )
    {
    return this.description;
    }

  /**
   * Sets the description of this classification.
   * 
   * @param description the new description of this classification.
   */
  public void setDescription( String description )
    {
    this.description = description;
    }

  /**
   * Returns the context this classification has been designed in.
   * 
   * @return the context this classification has been designed in
   */
  public String getContext( )
    {
    return this.context;
    }

  /**
   * Sets the context this classification has been designed in.
   * 
   * @param context the context this classification has been designed in.
   */
  public void setContext( String context )
    {
    this.context = context;
    }

  /**
   * Returns the type of objects this classification classifies.
   * 
   * @return the type of objects this classification classifies.
   */
  public String getObjectsClassified( )
    {
    return this.objectsClassified;
    }

  /**
   * Sets the type of objects this classification classifies.
   * 
   * @param objectsClassified the type of objects this classification classifies.
   */
  public void setObjectsClassified( String objectsClassified )
    {
    this.objectsClassified = objectsClassified;
    }

  /**
   * Returns the areas of statistics in which the classification is implemented.
   * 
   * @return the areas of statistics in which the classification is implemented.
   */
  public String getSubjectAreas( )
    {
    return this.subjectAreas;
    }

  /**
   * Sets the areas of statistics in which the classification is implemented.
   * 
   * @param subjectAreas the areas of statistics in which the classification is implemented.
   */
  public void setSubjectAreas( String subjectAreas )
    {
    this.subjectAreas = subjectAreas;
    }

  /**
   * Returns the statistical office or other authority, which created and maintains the version(s) of the classification.
   * 
   * @return the statistical office or other authority, which created and maintains the version(s) of the classification.
   */
  public Set<String> getOwners( )
    {
    return this.owners;
    }

  /**
   * Sets the statistical office or other authority, which created and maintains the version(s) of the classification. This method
   * throws a {@link NullPointerException} if the set of owners is <code>null</code>.
   * 
   * @param owners the statistical office or other authority, which created and maintains the version(s) of the classification.
   * @throws NullPointerException if the set of owners is <code>null</code>.
   */
  public void setOwners( Set<String> owners )
    {
    if( owners != null )
      this.owners = owners;
    else
      throw new NullPointerException( );
    }

  /**
   * Returns the keywords associated with this classification.
   * 
   * @return the keywords associated with this classification.
   */
  public Set<String> getKeywords( )
    {
    return this.keywords;
    }

  /**
   * Sets the keywords associated with this classification. This method throws a {@link NullPointerException} if the set of owners is
   * <code>null</code>.
   * 
   * @param keywords the keywords associated with this classification.
   * @throws NullPointerException if the set of keywords is <code>null</code>.
   */
  public void setKeywords( Set<String> keywords )
    {
    if( keywords != null )
      this.keywords = keywords;
    else
      throw new NullPointerException( );
    }

  /**
   * Returns the family this classification belongs to.
   * 
   * @return the family this classification belongs to.
   */
  public Family getFamily( )
    {
    return this.family;
    }

  /**
   * Sets the family this classification belongs to. A classification may only belong to a family, so this method automatically removes
   * this classification from the family it belonged to, and then adds itself to the new family. A classification must always belong to
   * a family, so calling this method with a <code>null</code> family will result in a {@link NullPointerException}.
   * 
   * @param family the family this classification belongs to.
   * @throws NullPointerException if the family is <code>null</code>.
   */
  public void setFamily( Family family )
    {
    if( family != this.family )
      {
      if( family != null )
        {
        Family oldFamily = this.family;
        this.family = family;
        if( oldFamily != null ) oldFamily.removeClassification( this );
        this.family.addClassification( this );
        }
      else
        throw new NullPointerException( );
      }
    }

  /**
   * Returns the versions of this classification.
   * 
   * @return the versions of this classification.
   */
  public List<Version> getVersions( )
    {
    List<Version> sortedList = new ArrayList<Version>( this.versions );
    Collections.sort( sortedList, new Classification.VersionsByDateComparator( ) );
    return Collections.unmodifiableList( sortedList );
    }

  /**
   * Adds a version to this classification. The version must have its classification attribute set to this classification, in other
   * case this method will throw a {@link IllegalArgumentException}. This method will throw a {@link NullPointerException} if the
   * version is <code>null</code>.
   * 
   * @param version the version to be added to this classification.
   * @throws NullPointerException if the version is <code>null</code>.
   * @throws IllegalArgumentException if the version has not its classification attribute set to this classification.
   */
  protected void addVersion( Version version )
    {
    if( version.getClassification( ) == this )
      {
      this.versions.add( version );
      if( this.getCurrentVersion( ) == null ) this.setCurrentVersion( version );
      }
    else
      throw new IllegalArgumentException( );
    }

  /**
   * Removes a version from this classification. The version must not have its classification attribute set to this classification, in
   * other case this method will throw a {@link IllegalArgumentException}. This method will throw a {@link NullPointerException} if
   * the version is <code>null</code>.
   * 
   * @param version the version to be removed from this classification.
   * @throws NullPointerException if the version is <code>null</code>.
   * @throws IllegalArgumentException if the version has its classification attribute set to this classification, or the version to be
   *           removed is the current version.
   */
  protected void removeVersion( Version version )
    {
    if( version.getClassification( ) != this && (!version.equals( this.getCurrentVersion( ) ) || this.versions.size( ) == 1) )
      {
      this.versions.remove( version );
      if( this.versions.isEmpty( ) ) this.currentVersion = null;
      }
    else
      throw new IllegalArgumentException( );
    }

  /**
   * Returns the current version of this classification.
   * 
   * @return the current version of this classification.
   */
  public Version getCurrentVersion( )
    {
    return this.currentVersion;
    }

  /**
   * Sets the current version of this classification. The current version must be included in the list of versions of this
   * classification, otherwise an {@link IllegalArgumentException} is thrown.
   * 
   * @param currentVersion the current version of this classification.
   * @throws IllegalArgumentException if the version is not included in the versions of this classification.
   */
  public void setCurrentVersion( Version currentVersion )
    {
    if( this.versions.contains( currentVersion ) )
      this.currentVersion = currentVersion;
    else
      throw new IllegalArgumentException( );
    }

  /**
   * Compares two verions ordering them by release date.
   * 
   * @author jgonzalez
   */
  private static class VersionsByDateComparator implements Comparator<Version>
    {
    public int compare( Version version1, Version version2 )
      {
      return version1.getReleaseDate( ).compareTo( version2.getReleaseDate( ) );
      }

    }
  }
