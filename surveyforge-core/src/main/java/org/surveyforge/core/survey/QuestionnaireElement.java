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

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import org.hibernate.annotations.GenericGenerator;
import org.surveyforge.core.metadata.RegisterDataElement;


/**
 * A questionnaire element is the link between the {@link Question} of the {@link Questionnaire} and the metadata layer represented by
 * the {@link RegisterDataElement}.
 * 
 * @author jsegura
 */
@Entity
public class QuestionnaireElement implements Serializable
  {
  private static final long   serialVersionUID     = 0L;

  @Id
  @Column(length = 50)
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "uuid")
  private String              id;
  /** Version for optimistic locking. */
  @javax.persistence.Version
  private int                 lockingVersion;

  /** A questionnaire element is identified by a unique identifier. */
  @Column(unique = true, length = 50)
  private String              identifier;
  /** Each questionnaire element has a {@link Question} that has the text and the structure of the question. */
  @ManyToOne(optional = true, cascade = {CascadeType.ALL})
  @JoinColumn(name = "question_id", insertable = false, updatable = false)
  private Question            question             = null;
  /** A questionnaire element have a {@link RegisterDataElement} to have the info of the data. */
  @ManyToOne(optional = false)
  @JoinColumn(name = "registerDataElement_id", insertable = false, updatable = false)
  private RegisterDataElement registerDataElement;
  /** Default answer if the question is not applicable. */
  @Column(nullable = false, length = 50)
  private String              defaultNotApplicable = "";
  /** Default answer if the question is not answered. */
  @Column(nullable = false, length = 50)
  private String              defaultNotAnswered   = "";


  private QuestionnaireElement( )
    {}

  /**
   * Creates a new instance of a QuestionnaireElement linked to the registerDataElement and identified by identifier.
   * 
   * @param registerDataElement The {@link RegisterDataElement} the questionnaire element is linked to.
   * @param identifier The identifier of the questionnaire element.
   * @throws NullPointerException If the registerDataElement or the identifier are <code>null</code> and if the identifier is empty.
   */
  public QuestionnaireElement( RegisterDataElement registerDataElement, String identifier )
    {
    this.setRegisterDataElement( registerDataElement );
    this.setIdentifier( identifier );
    }

  /**
   * Returns the identifier of the questionnaire element.
   * 
   * @return Returns the identifier.
   */
  public String getIdentifier( )
    {
    return this.identifier;
    }

  /**
   * Sets a new identifier for the questionnaire element.
   * 
   * @param identifier The identifier to set.
   * @throws NullPointerException If the identifier is <code>null</code> and if the identifier is empty.
   */
  public void setIdentifier( String identifier )
    {
    if( identifier != null && !identifier.equals( "" ) )
      this.identifier = identifier;
    else
      throw new NullPointerException( );
    }

  /**
   * Returns the {@link Question} linked to the questionnaire element.
   * 
   * @return Returns the question.
   */
  public Question getQuestion( )
    {
    return this.question;
    }

  /**
   * Sets the new {@link Question} of the questionnaire element.
   * 
   * @param question The question to set.
   */
  public void setQuestion( Question question )
    {
    this.question = question;
    }

  /**
   * Return the {@link RegisterDataElement} this questionnaire element is linked to.
   * 
   * @return Returns the registerDataElement.
   */
  public RegisterDataElement getRegisterDataElement( )
    {
    return this.registerDataElement;
    }

  /**
   * Sets a the new {@link RegisterDataElement} this questionnaire element is linked to.
   * 
   * @param registerDataElement The registerDataElement to set.
   * @throws If the registerDataElement is <code>null</code>;
   */
  public void setRegisterDataElement( RegisterDataElement registerDataElement )
    {
    if( registerDataElement != null )
      this.registerDataElement = registerDataElement;
    else
      throw new NullPointerException( );
    }

  /**
   * Returns the default answer for not applicable questions.
   * 
   * @return Returns the default answer for not applicable questions.
   */
  public String getDefaultNotApplicable( )
    {
    return this.defaultNotApplicable;
    }

  /**
   * Sets the default answer for not applicable questions.
   * 
   * @param defaultNotApplicable The new default answer for not applicable questions.
   * @throws NullPointerException If the defaultNotApplicable is <code>null</code.
   */
  public void setDefaultNotApplicable( String defaultNotApplicable )
    {
    if( defaultNotApplicable != null )
      this.defaultNotApplicable = defaultNotApplicable;
    else
      throw new NullPointerException( );
    }

  /**
   * Returns the default answer for not answered questions.
   * 
   * @return Returns the default answer for not answered questions.
   */
  public String getDefaultNotAnswered( )
    {
    return this.defaultNotAnswered;
    }

  /**
   * Sets the new default answer for not answered questions.
   * 
   * @param defaultNotAnswered The new default answer for not answered questions.
   * @throws NullPointerException If the defaultNotAnswered is <code>null</code.
   */
  public void setDefaultNotAnswered( String defaultNotAnswered )
    {
    if( defaultNotAnswered != null )
      this.defaultNotAnswered = defaultNotAnswered;
    else
      throw new NullPointerException( );
    }

  // TODO : Equals+hashcode
  // @Override
  // public boolean equals( Object object )
  // {
  // Questionnaire otherQuestionnaire = (Questionnaire) object;
  // return this.getQuestion( )( ).equals( otherQuestionnaire.getStudy( ) )
  // && this.getIdentifier( ).equals( otherQuestionnaire.getIdentifier( ) );
  // }
  //
  // @Override
  // public int hashCode( )
  // {
  // return this.getStudy( ).hashCode( ) ^ this.getIdentifier( ).hashCode( );
  // }

  }
