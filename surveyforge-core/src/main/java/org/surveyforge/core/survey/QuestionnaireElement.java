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
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.IndexColumn;
import org.surveyforge.core.metadata.RegisterDataElement;
import org.surveyforge.core.metadata.domain.AbstractValueDomain;
import org.surveyforge.core.metadata.domain.StructuredValueDomain;

/**
 * A questionnaire element is the link between the {@link Question} of the {@link Questionnaire} and the metadata layer represented by
 * the {@link RegisterDataElement}.
 * 
 * @author jsegura
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"identifier", "upperElement_id"})})
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public class QuestionnaireElement implements Serializable
  {
  private static final long          serialVersionUID     = -5920656314181190011L;


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

  /** A SurveyElement is identified by a unique identifier. */
  @Column(unique = true, length = 50)
  private String                     identifier;

  /** Each questionnaire element has a {@link Question} that has the text and the structure of the question. */
  @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
  private Question                   question             = null;
  /** A questionnaire element have a {@link RegisterDataElement} to have the info of the data. */
  @OneToOne(cascade = {CascadeType.ALL})
  protected RegisterDataElement      registerDataElement;
  /** Default answer if the question is not applicable. */
  @Column(length = 50)
  private String                     defaultNotApplicable = "";
  /** Default answer if the question is not answered. */
  @Column(length = 50)
  private String                     defaultNotAnswered   = "";

  @ManyToOne
  @JoinColumn(name = "upperElement_id", insertable = false, updatable = false)
  private QuestionnaireElement       upperElement         = null;

  @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
  @IndexColumn(name = "componentElementsIndex")
  @JoinColumn(name = "upperElement_id")
  private List<QuestionnaireElement> componentElements    = new ArrayList<QuestionnaireElement>( );


  protected QuestionnaireElement( )
    {}

  public QuestionnaireElement( String identifier, AbstractValueDomain valueDomain )
    {
    this.setIdentifier( identifier );
    this.registerDataElement = new RegisterDataElement( identifier, valueDomain );
    }

  /*
   * Returns the identifier of the SurveyElement. @return Returns the identifier.
   */
  public String getIdentifier( )
    {
    return this.identifier;
    }

  /**
   * Sets the identifier of the SurveyElement.
   * 
   * @param identifier The identifier to set.
   * @throws NullPointerException If the register is <code>null</code> or if the identifier is <code>null</code> or is empty.
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
   * / * Return the {@link RegisterDataElement} this questionnaire element is linked to.
   * 
   * @return Returns the registerDataElement.
   */
  public RegisterDataElement getRegisterDataElement( )
    {
    return this.registerDataElement;
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

  protected void setUpperElement( QuestionnaireElement upperElement )
    {
    if( this.getUpperElement( ) != null ) this.getUpperElement( ).removeElement( this );
    this.upperElement = upperElement;
    if( this.getUpperElement( ) != null ) this.getUpperElement( ).addElement( this );
    }

  public QuestionnaireElement getUpperElement( )
    {
    return this.upperElement;
    }


  public List<QuestionnaireElement> getComponentElements( )
    {
    return Collections.unmodifiableList( this.componentElements );
    }

  /**
   * @param componentElements The componentElements to set.
   */
  public void addComponentElement( QuestionnaireElement componentElement )
    {
    if( componentElement != null )
      {
      if( !this.getComponentElements( ).contains( componentElement )
          && this.getRegisterDataElement( ).getValueDomain( ) instanceof StructuredValueDomain )
        {
        componentElement.setUpperElement( this );
        // ((StructuredValueDomain) this.getValueDomain( )).addSubDomain( componentElement.getValueDomain( ) );
        }
      else
        throw new IllegalArgumentException( );
      }
    else
      throw new NullPointerException( );
    }


  /**
   * @param componentElement
   */
  protected void addElement( QuestionnaireElement componentElement )
    {
    if( componentElement == null ) throw new NullPointerException( );
    if( this.getRegisterDataElement( ).getValueDomain( ) instanceof StructuredValueDomain )
      {
      this.componentElements.add( componentElement );
      componentElement.upperElement = this;
      this.getRegisterDataElement( ).addComponentElement( componentElement.getRegisterDataElement( ) );
      }
    else
      throw new IllegalArgumentException( );
    }

  /**
   * @param componentElements The componentElements to set.
   */
  public void removeComponentElement( QuestionnaireElement componentElement )
    {
    if( componentElement != null )
      {
      componentElement.setUpperElement( null );
      }
    else
      throw new NullPointerException( );
    }

  /**
   * @param componentElement
   */
  protected void removeElement( QuestionnaireElement componentElement )
    {
    if( componentElement == null ) throw new NullPointerException( );
    if( this.getRegisterDataElement( ).getValueDomain( ) instanceof StructuredValueDomain )
      {
      this.componentElements.remove( componentElement );
      componentElement.upperElement = null;
      this.getRegisterDataElement( ).removeComponentElement( componentElement.getRegisterDataElement( ) );

      }
    else
      throw new IllegalArgumentException( );
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
