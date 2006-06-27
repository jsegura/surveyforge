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
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

/**
 * A classification version is a list of mutually exclusive categories representing the version-specific values of the classification
 * variable. If the version is hierarchical, each level in the hierarchy is a set of mutually exclusive categories. A classification
 * version has a certain normative status and is valid for a given period of time. A new version of a classification differs in
 * essential ways from the previous version. Essential changes are changes that alter the borders between categories, i.e. a
 * statistical object/unit may belong to different categories in the new and the older version. Border changes may be caused by
 * creating or deleting categories, or moving a part of a category to another. The addition of case law, changes in explanatory notes
 * or in the titles do not lead to a new version.
 * 
 * @author jgonzalez
 */
public class Version implements Serializable
  {
  private static final long serialVersionUID   = -6949734640328443272L;

  /**
   * A classification version is identified by a unique identifier, which may typically be an abbreviation of its title. It is often
   * distinguished from other versions of the same classification by reference to a revision number or to the year of the version's
   * coming into force. Ex.: NACE Rev.1, CPA 96.
   */
  private String            identifier;
  /**
   * A classification version has a title as provided by the owner or maintenance unit. Ex.: Classification of Products by Activity,
   * 1996.
   */
  private String            title;
  /** Short general description of the classification version. */
  private String            description;
  /** Date on which the classification version was released. */
  private Date              releaseDate;
  /**
   * The introduction provides a detailed description of the classification version, the background for its creation, the
   * classification variable and objects/units classified, classification rules etc.
   */
  private String            introduction;
  /**
   * The unit or group of persons within the organisation who are responsible for the classification version, i.e. for maintaining,
   * updating and changing it.
   */
  private String            maintenanceUnit;
  /** Person(s) who may be contacted for additional information about the classification version. */
  private Set<String>       contactPersons     = new HashSet<String>( );
  /**
   * Indicates that the classification version is covered by a legal act or by some other formal agreement. Ex.: The legal base for
   * NACE Rev.1 is: 1. Council of the European Communities Regulation (EEC) No. 3037/90 of 9 October 1990 (Official Journal of the
   * European Communities No. L 293, 24.10.1990) 2. Commission of the European Communities Regulation (EEC) No. 761/93 of 24 March 1993
   * (Official Journal of the European Communities No. L 83, 3.4.1993)
   */
  private String            legalBase;
  /** A list of the publications in which the classification version has been published. */
  private List<String>      publications       = new ArrayList<String>( );
  /**
   * A list of the defined types of alternative item titles available for the version. Each title type refers to a list of alternative
   * item titles. Ex.: Short titles; Medium titles.
   */
  private List<String>      titleTypes;
  /** A classification version can exist in one or several languages. */
  private Set<Locale>       availableLanguages = new HashSet<Locale>( );
  /**
   * Indicates whether or not updates are allowed within the classification version, i.e. without leading to a new version. Such
   * updates will usually be changes, which add items to the structure and/or revalidates/invalidates classification items but which do
   * not alter the borders within the structure of the classification versions.
   */
  private boolean           updatesPossible;
  /** Verbal summary description of changes which have occurred from the previous classification version to the actual version. */
  private String            changes;
  /** Verbal summary description of changes which have occurred within the classification version. */
  private String            updates;
  /**
   * Classification versions may have restricted copyrights. Such versions might be excluded from downloading and should be displayed
   * in official publications (e.g. WEB) indicating the copyright owner.
   */
  private String            copyright;
  /** Indicates whether or not the classification version may be published or disseminated (e.g. on the Web). */
  private boolean           disseminationAllowed;
  /** A classification version is a version of one specific classification. Ex.: CPA 96 is a version of CPA. */
  private Classification    classification;
  /**
   * A classification version can be derived from one of the classification versions of another classification. The derived version can
   * either inherit the structure of the classification version from which it is derived, usually adding more detail, or use a large
   * part of the items of the classification version from which it is derived and rearrange them in a different structure. Indicates
   * the classification version from which the actual version is derived. Ex.: NACE Rev.1 is derived from ISIC Rev.3; CPA 93 is derived
   * from the Provisional CPC (1991).
   */
  private Version           derivedFrom;
  /** The structure of a classification version is defined by its levels. */
  private List<Level>       levels             = new ArrayList<Level>( );

  // /** A list of all case laws associated with the classification version. */
  // private List<CaseLaw> caseLaws;

  /**
   * Creates a new version belonging to a classification, with the given identifier and release date. All the parameters must be non
   * null, otherwise this constructor will throw a {@link NullPointerException}.
   * 
   * @param classification the classification this version will belong to.
   * @param identifier the identifier of this version.
   * @param releaseDate the date on which this version was released.
   * @throws NullPointerException if classification is <code>null</code> or identifier is <code>null</code> or the empty string.
   */
  public Version( Classification classification, String identifier, Date releaseDate )
    {
    this.setIdentifier( identifier );
    this.setClassification( classification );
    this.setReleaseDate( releaseDate );
    }

  /**
   * Returns the identifier of this version.
   * 
   * @return the identifier of this version.
   */
  public String getIdentifier( )
    {
    return this.identifier;
    }

  /**
   * Sets the identifier of this version. This method throws a {@link NullPointerException} if the provided identifier is
   * <code>null</code> or the empty string.
   * 
   * @param identifier the new identifier of this version.
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
   * Returns the title of this version.
   * 
   * @return the title of this version.
   */
  public String getTitle( )
    {
    return this.title;
    }

  /**
   * Sets the title of this version.
   * 
   * @param title the new title of this version.
   */
  public void setTitle( String title )
    {
    this.title = title;
    }

  /**
   * Returns the description of this version.
   * 
   * @return the description of this version.
   */
  public String getDescription( )
    {
    return this.description;
    }

  /**
   * Sets the description of this version.
   * 
   * @param description the new description of this version.
   */
  public void setDescription( String description )
    {
    this.description = description;
    }

  /**
   * Returns the date on which this version was released.
   * 
   * @return the date on which this version was released.
   */
  public Date getReleaseDate( )
    {
    return this.releaseDate;
    }

  /**
   * Sets the date on which this version was released. The date must be non null, otherwise a {@link NullPointerException} is thrown.
   * 
   * @param releaseDate the date on which this version was released.
   * @throws NullPointerException if the date is <code>null</code>.
   */
  public void setReleaseDate( Date releaseDate )
    {
    if( releaseDate != null )
      this.releaseDate = releaseDate;
    else
      throw new NullPointerException( );
    }

  /**
   * Returns the date on which the successor version of this version was released. If no successor version has been released this
   * method returns <code>null</code>.
   * 
   * @return the date on which the successor version of this version was released, <code>null</code> if no successor has been
   *         released.
   */
  public Date getTerminationDate( )
    {
    Version successor = this.getSuccessor( );
    if( successor != null )
      return successor.releaseDate;
    else
      return null;
    }

  /**
   * Returns <code>true</code> if this version is the current valid version.
   * 
   * @return <code>true</code> if this version is the current valid version, <code>false</code> otherwise.
   */
  public boolean isCurrentVersion( )
    {
    return this.getClassification( ).getCurrentVersion( ).equals( this );
    }

  /**
   * Returns the detailed description of this version.
   * 
   * @return the detailed description of this version.
   */
  public String getIntroduction( )
    {
    return this.introduction;
    }

  /**
   * Sets the detailed description of this version.
   * 
   * @param introduction the detailed description of this version.
   */
  public void setIntroduction( String introduction )
    {
    this.introduction = introduction;
    }

  /**
   * Returns the unit or group of persons withing the organisation who are responsible for this version.
   * 
   * @return the unit or group of persons withing the organisation who are responsible for this version.
   */
  public String getMaintenanceUnit( )
    {
    return this.maintenanceUnit;
    }

  /**
   * Sets the unit or group of persons withing the organisation who are responsible for this version.
   * 
   * @param maintenanceUnit the unit or group of persons withing the organisation who are responsible for this version.
   */
  public void setMaintenanceUnit( String maintenanceUnit )
    {
    this.maintenanceUnit = maintenanceUnit;
    }

  /**
   * Returns the persons who may be contacted for additional information about this version.
   * 
   * @return the persons who may be contacted for additional information about this version.
   */
  public Set<String> getContactPersons( )
    {
    return this.contactPersons;
    }

  /**
   * Sets the persons who may be contacted for additional information about this version.
   * 
   * @param contactPersons the persons who may be contacted for additional information about this version.
   */
  public void setContactPersons( Set<String> contactPersons )
    {
    this.contactPersons = contactPersons;
    }

  /**
   * Returns the legal base of this version.
   * 
   * @return the legal base of this version.
   */
  public String getLegalBase( )
    {
    return this.legalBase;
    }

  /**
   * Sets the legal base of this version.
   * 
   * @param legalBase the legal base of this version.
   */
  public void setLegalBase( String legalBase )
    {
    this.legalBase = legalBase;
    }

  /**
   * Returns the list of publications in which this version has been published.
   * 
   * @return the list of publications in which this version has been published.
   */
  public List<String> getPublications( )
    {
    return this.publications;
    }

  /**
   * Sets the list of publications in which this version has been published. This method throws {@link NullPointerException} if
   * publications is <code>null</code>.
   * 
   * @param publications the list of publications in which this version has been published.
   * @throws NullPointerException if publications is <code>null</code>.
   */
  public void setPublications( List<String> publications )
    {
    if( publications != null )
      this.publications = publications;
    else
      throw new NullPointerException( );
    }

  /**
   * Returns the list of the defined types of alternative item titles available for this version.
   * 
   * @return the list of the defined types of alternative item titles available for this version.
   */
  public List<String> getTitleTypes( )
    {
    return this.titleTypes;
    }

  /**
   * Sets the list of the defined types of alternative item titles available for this version. This method throws
   * {@link NullPointerException} if the list of title types is <code>null</code>.
   * 
   * @param titleTypes the list of the defined types of alternative item titles available for this version.
   * @throws NullPointerException if the list of title types is <code>null</code>.
   */
  public void setTitleTypes( List<String> titleTypes )
    {
    if( titleTypes != null )
      this.titleTypes = titleTypes;
    else
      throw new NullPointerException( );
    }

  /**
   * Returns the set of languages this version is available in.
   * 
   * @return the set of languages this version is available in.
   */
  public Set<Locale> getAvailableLanguages( )
    {
    return this.availableLanguages;
    }

  /**
   * Sets the set of languages this version is available in. This method throws {@link NullPointerException} if the set of languages is
   * <code>null</code>.
   * 
   * @param availableLanguages the set of languages this version is available in.
   */
  public void setAvailableLanguages( Set<Locale> availableLanguages )
    {
    if( availableLanguages != null )
      this.availableLanguages = availableLanguages;
    else
      throw new NullPointerException( );
    }

  /**
   * Returns <code>true</code> if updates are allowed within this version without leading to a new version.
   * 
   * @return <code>true</code> if updates are allowed within this version without leading to a new version.
   */
  public boolean isUpdatesPossible( )
    {
    return this.updatesPossible;
    }

  /**
   * Sets wether or not updates are allowed within this version without leading to a new version.
   * 
   * @param updatesPossible <code>true</code> if updates are allowed within this version without leading to a new version.
   */
  public void setUpdatesPossible( boolean updatesPossible )
    {
    this.updatesPossible = updatesPossible;
    }

  /**
   * Returns the verbal summary description of changes which have occurred from the previous classification version to the actual
   * version.
   * 
   * @return the verbal summary description of changes from the previous version.
   */
  public String getChanges( )
    {
    return this.changes;
    }

  /**
   * Sets the verbal summary description of changes which have occurred from the previous classification version to the actual version.
   * 
   * @param changes the verbal summary description of changes from the previous version.
   */
  public void setChanges( String changes )
    {
    this.changes = changes;
    }

  /**
   * Returns the verbal summary description of changes which have occurred within the classification version.
   * 
   * @return the verbal summary description of changes which have occurred within the classification version.
   */
  public String getUpdates( )
    {
    return this.updates;
    }

  /**
   * Sets the verbal summary description of changes which have occurred within the classification version.
   * 
   * @param updates the verbal summary description of changes which have occurred within the classification version.
   */
  public void setUpdates( String updates )
    {
    this.updates = updates;
    }

  /**
   * Returns the copyright of this version.
   * 
   * @return the copyright of this version.
   */
  public String getCopyright( )
    {
    return this.copyright;
    }

  /**
   * Sets the copyright of this version.
   * 
   * @param copyright the copyright of this version.
   */
  public void setCopyright( String copyright )
    {
    this.copyright = copyright;
    }

  /**
   * Returns <code>true</code> if this version may be published or disseminated.
   * 
   * @return <code>true</code> if this version may be published or disseminated.
   */
  public boolean isDisseminationAllowed( )
    {
    return this.disseminationAllowed;
    }

  /**
   * Sets wether or not this version may be published or disseminated.
   * 
   * @param disseminationAllowed <code>true</code> if this version may be published or disseminated.
   */
  public void setDisseminationAllowed( boolean disseminationAllowed )
    {
    this.disseminationAllowed = disseminationAllowed;
    }

  /**
   * Returns the classification this version belongs to.
   * 
   * @return the classification this version belongs to.
   */
  public Classification getClassification( )
    {
    return this.classification;
    }

  /**
   * Sets the classification this version belongs to. A version may only belong to a classification, so this method automatically
   * removes this version from the classification it belonged to, and then adds itself to the new classification. A version must always
   * belong to a classification, so calling this method with a <code>null</code> classification will result in a
   * {@link NullPointerException}.
   * 
   * @param classification the classification this version belongs to.
   * @throws NullPointerException if the classification is <code>null</code>.
   */
  public void setClassification( Classification classification )
    {
    if( classification != this.classification )
      {
      if( classification != null )
        {
        Classification oldClassification = this.classification;
        this.classification = classification;
        if( oldClassification != null ) oldClassification.removeVersion( this );
        this.classification.addVersion( this );
        }
      else
        throw new NullPointerException( );
      }
    }

  /**
   * Returns the predecessor version within the classification this version belongs to. If no predecessor is found this method returns
   * <code>null</code>.
   * 
   * @return the predecessor version, <code>null</code> is none is found.
   */
  public Version getPredecessor( )
    {
    List<Version> versions = this.getClassification( ).getVersions( );
    int position = versions.indexOf( this );
    if( position > 0 )
      return versions.get( position - 1 );
    else
      return null;
    }

  /**
   * Returns the successor version within the classification this version belongs to. If no successor is found this method returns
   * <code>null</code>.
   * 
   * @return the successor version, <code>null</code> is none is found.
   */
  public Version getSuccessor( )
    {
    List<Version> versions = this.getClassification( ).getVersions( );
    int position = versions.indexOf( this );
    if( position < versions.size( ) - 1 )
      return versions.get( position + 1 );
    else
      return null;
    }

  /**
   * Returns the classification version this version is derived from.
   * 
   * @return the classification version this version is derived from.
   */
  public Version getDerivedFrom( )
    {
    return this.derivedFrom;
    }

  /**
   * Sets the classification version this version is derived from.
   * 
   * @param derivedFrom the classification version this version is derived from.
   */
  public void setDerivedFrom( Version derivedFrom )
    {
    this.derivedFrom = derivedFrom;
    }

  /**
   * Returns the levels of this version.
   * 
   * @return the levels of this version.
   */
  public List<Level> getLevels( )
    {
    return Collections.unmodifiableList( this.levels );
    }

  /**
   * Adds a level to this version. The level must have its version attribute set to this version, in other case this method will throw
   * a {@link IllegalArgumentException}. This method will throw a {@link NullPointerException} if the level is <code>null</code>.
   * 
   * @param level the level to be added to this version.
   * @throws NullPointerException if the level is <code>null</code>.
   * @throws IllegalArgumentException if the level has not its version attribute set to this version.
   */
  protected void addLevel( Level level )
    {
    if( level.getVersion( ) == this )
      {
      this.levels.add( level );
      }
    else
      throw new IllegalArgumentException( );
    }

  /**
   * Removes a level from this version. The level must not have its version attribute set to this version, in other case this method
   * will throw a {@link IllegalArgumentException}. This method will throw a {@link NullPointerException} if the level is
   * <code>null</code>.
   * 
   * @param level the level to be removed from this version.
   * @throws NullPointerException if the level is <code>null</code>.
   * @throws IllegalArgumentException if the level has its version attribute set to this version.
   */
  protected void removeLevel( Level level )
    {
    if( level.getVersion( ) != this )
      {
      this.levels.remove( level );
      }
    else
      throw new IllegalArgumentException( );
    }

  // /**
  // * @return Returns the caseLaws.
  // */
  // public List<CaseLaw> getCaseLaws( )
  // {
  // return this.caseLaws;
  // }
  //
  // /**
  // * @param caseLaws The caseLaws to set.
  // */
  // public void setCaseLaws( List<CaseLaw> caseLaws )
  // {
  // this.caseLaws = caseLaws;
  // }
  }
