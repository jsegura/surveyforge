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
package org.surveyforge.core.survey;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.IndexColumn;

/**
 * Studies are used to document the concepts and production rules for statistical data. A statistical activity might describe
 * activities organized in a traditional 'stove pipe system' as well as describing statistical surveys (input) and statistical products
 * (output).
 * 
 * @author jsegura
 */
@Entity
public class Study implements Serializable
  {
  private static final long   serialVersionUID = -4089931270976596457L;

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

  /**
   * A statistical activity is identified by a unique identifier, which may typically be an abbreviation of its title or a systematic
   * number.
   */
  @Column(unique = true, nullable = false, length = 50)
  private String              identifier;
  /** A statistical activity has a title as provided by the owner or maintenance unit. */
  @Column(length = 250)
  private String              title            = "";
  /**
   * Detailed description of the statistical activity. The activity description describes the actions performed in the frame of the
   * activity.
   */
  @Column(length = 500)
  private String              description      = "";

  /** When the statistical activity includes a survey one or more questionnaires can be defined in the context of the activity. */
  @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
  @IndexColumn(name = "questionnairesIndex")
  @JoinColumn(name = "study_id", nullable = false)
  private List<Questionnaire> questionnaires   = new ArrayList<Questionnaire>( );

  protected Study( )
    {}

  /**
   * Creates a new Study identified by the identifier.
   * 
   * @param identifier The identifier to set.
   * @throws NullPointerException If the identifier is <code>null</code> or is empty.
   */
  public Study( String identifier )
    {
    this.setIdentifier( identifier );
    }

  /**
   * Returns the identifier of the Study.
   * 
   * @return Returns the identifier.
   */
  public String getIdentifier( )
    {
    return this.identifier;
    }

  /**
   * Sets a new identifier to the Study.
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

  /**
   * Returns the title of the Study.
   * 
   * @return Returns the title.
   */
  public String getTitle( )
    {
    return this.title;
    }

  /**
   * Sets a new title to the Study.
   * 
   * @param title The title to set.
   * @throws NullPointerException If the title is <code>null</code>.
   */
  public void setTitle( String title )
    {
    if( title != null )
      this.title = title;
    else
      throw new NullPointerException( );
    }

  /**
   * Returns the description of the Study.
   * 
   * @return Returns the description.
   */
  public String getDescription( )
    {
    return this.description;
    }

  /**
   * Sets a new description to the Study.
   * 
   * @param description The description to set.
   * @throws NullPointerException If the description is <code>null</code>.
   */
  public void setDescription( String description )
    {
    if( description != null )
      this.description = description;
    else
      throw new NullPointerException( );
    }

  /**
   * Return the list of {@link Questionnaire}s included in the Study.
   * 
   * @return Returns the questionnaires.
   */
  public List<Questionnaire> getQuestionnaires( )
    {
    return Collections.unmodifiableList( this.questionnaires );
    }

  /**
   * Add a new {@link Questionnaire}to the Study.
   * 
   * @param questionnaires The questionnaires to add.
   * @throws NullPointerException If the questionnaire is <code>null</code>.
   */
  public void addQuestionnaire( Questionnaire questionnaire )
    {
    if( questionnaire != null )
      this.questionnaires.add( questionnaire );
    else
      throw new NullPointerException( );
    }

  /**
   * Remove a {@link Questionnaire} from the Study.
   * 
   * @param questionnaire The questionnaire to remove.
   * @throws NullPointerException If the questionnaire is <code>null</code>.
   */
  public void removeQuestionnaire( Questionnaire questionnaire )
    {
    if( questionnaire != null )
      this.questionnaires.remove( questionnaire );
    else
      throw new NullPointerException( );
    }

  @Override
  public boolean equals( Object object )
    {
    Study other = (Study) object;
    return this.getIdentifier( ).equals( other.getIdentifier( ) );
    }

  @Override
  public int hashCode( )
    {
    return this.getIdentifier( ).hashCode( );
    }
  }
