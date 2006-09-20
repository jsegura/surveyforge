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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.IndexColumn;
import org.surveyforge.core.metadata.RegisterDataElement;


/**
 * A questionnaire element is the link between the {@link Question} of the {@link Questionnaire} and the metadata layer represented by
 * the {@link RegisterDataElement}.
 * 
 * @author jsegura
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"identifier", "questionnaire_id"})})
public class QuestionnaireElement implements Serializable
  {
  private static final long          serialVersionUID          = -5920656314181190011L;


  @SuppressWarnings("unused")
  @Id
  @Column(length = 50)
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "uuid")
  private String                     id;
  /** Version for optimistic locking. */
  @SuppressWarnings("unused")
  @javax.persistence.Version
  private int                        lockingVersion;

  /** A questionnaire element is identified by a unique identifier. */
  @Column(unique = true, length = 50)
  private String                     identifier;
  /** Each questionnaire element has a {@link Question} that has the text and the structure of the question. */
  @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
  private Question                   question                  = null;
  /** A questionnaire element have a {@link RegisterDataElement} to have the info of the data. */
  @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
  private RegisterDataElement        registerDataElement;
  /** Default answer if the question is not applicable. */
  @Column(length = 50)
  private String                     defaultNotApplicable      = "";
  /** Default answer if the question is not answered. */
  @Column(length = 50)
  private String                     defaultNotAnswered        = "";
  /** This is the question that acts as a frame for a number of sub questions. */
  @ManyToOne
  @JoinColumn(name = "upperQuestionnaireElement_id", insertable = false, updatable = false)
  private QuestionnaireElement       upperQuestionnaireElement = null;
  /** A question referring to a complex fact can be divided in a number of sub questions. */
  @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
  @IndexColumn(name = "subQuestionnaireElementsIndex")
  @JoinColumn(name = "upperQuestionnaireElement_id")
  private List<QuestionnaireElement> subQuestionnaireElements  = new ArrayList<QuestionnaireElement>( );


  protected QuestionnaireElement( )
    {}

  /**
   * Creates a new instance of a QuestionnaireElement linked to the registerDataElement and identified by identifier.
   * 
   * @param registerDataElement The {@link RegisterDataElement} the questionnaire element is linked to.
   * @param identifier The identifier of the questionnaire element.
   * @throws NullPointerException If the registerDataElement or the identifier are <code>null</code> and if the identifier is empty.
   */
  public QuestionnaireElement( RegisterDataElement registerDataElement )
    {
    this.setRegisterDataElement( registerDataElement );
    this.setIdentifier( registerDataElement.getIdentifier( ) );
    }

  public QuestionnaireElement( RegisterDataElement registerDataElement, QuestionnaireElement upperElement )
    {
    this( registerDataElement );
    this.setUpperQuestionnaireElement( upperElement );
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
  protected void setIdentifier( String identifier )
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
  protected void setRegisterDataElement( RegisterDataElement registerDataElement )
    {
    if( registerDataElement != null )
      {
      // TODO remove old elements
      this.registerDataElement = registerDataElement;
      registerDataElement.setQuestionnaireElement( this );
      for( int i = 0; i < registerDataElement.getComponentElements( ).size( ); i++ )
        {
        this.addSubQuestionnaireElement( new QuestionnaireElement( (RegisterDataElement) (registerDataElement.getComponentElements( )
            .get( i )) ) );
        }
      }
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


  public QuestionnaireElement getUpperQuestionnaireElement( )
    {
    return this.upperQuestionnaireElement;
    }

  public void setUpperQuestionnaireElement( QuestionnaireElement upperQuestionnaireElement )
    {
    if( this.upperQuestionnaireElement != null ) this.upperQuestionnaireElement.removeSubQuestionnaireElement( this );
    this.upperQuestionnaireElement = upperQuestionnaireElement;
    if( this.upperQuestionnaireElement != null ) this.upperQuestionnaireElement.addSubQuestionnaireElement( this );
    }


  private void removeSubQuestionnaireElement( QuestionnaireElement subQuestionnaireElement )
    {
    if( subQuestionnaireElement != null )
      this.subQuestionnaireElements.remove( subQuestionnaireElement );
    else
      throw new NullPointerException( );
    }

  public List<QuestionnaireElement> getSubQuestionnaireElementss( )
    {
    return Collections.unmodifiableList( this.subQuestionnaireElements );
    }


  private void addSubQuestionnaireElement( QuestionnaireElement subQuestionnaireElement )
    {
    if( subQuestionnaireElement != null )
      this.subQuestionnaireElements.add( subQuestionnaireElement );
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
