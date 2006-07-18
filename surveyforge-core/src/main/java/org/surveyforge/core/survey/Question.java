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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The question contain the text for the question, with the sub questions being used to provide further information about the question.
 * Alternatively, the question may be empty and only the sub questions used. Each sub element has a reference to its upper question.
 * 
 * @author jsegura
 */
public class Question
  {
  /** A question has a language independent identifier that identifies the question among all other globally defined questions. */
  private String         identifier;
  /**
   * The question contains the exact text of the question that has been asked to collect the data. The question text is language
   * dependent.
   */
  private String         text          = "";
  /** The description contains explanatory notes to the question and/or an extended definition of the question's meaning. */
  private String         description   = "";
  /** This is the question that acts as a frame for a number of sub questions. */
  private Question       upperQuestion = null;
  /** A question referring to a complex fact can be divided in a number of sub questions. */
  private List<Question> subQuestions  = new ArrayList<Question>( );

  /**
   * Creates a new Question with an identifier.
   * 
   * @param identifier The identifier of this Question.
   * @throws NullPointerException if the identifier or the question are <code>null</code>.
   */
  public Question( String identifier )
    {
    this.setIdentifier( identifier );
    }

  /**
   * Returns the identifier of the classification.
   * 
   * @return Returns the identifier.
   */
  public String getIdentifier( )
    {
    return this.identifier;
    }

  /**
   * Sets the identifier of the classification
   * 
   * @param identifier The identifier to set.
   * @throws NullPointerException if the identifier is <code>null</code> or is empty.
   */
  public void setIdentifier( String identifier )
    {
    if( identifier != null && !identifier.equals( "" ) )
      this.identifier = identifier;
    else
      throw new NullPointerException( );
    }

  /**
   * Returns the text of the question.
   * 
   * @return Returns the question.
   */
  public String getText( )
    {
    return this.text;
    }

  /**
   * Sets the text of the Question.
   * 
   * @param text The text to set.
   * @throws NullPointerException if the text is <code>null</code> or is empty.
   */
  public void setText( String text )
    {
    if( text != null )
      this.text = text;
    else
      throw new NullPointerException( );
    }

  /**
   * Returns the description of the Question.
   * 
   * @return Returns the description.
   */
  public String getDescription( )
    {
    return this.description;
    }

  /**
   * Sets the description text of the Question.
   * 
   * @param description The description to set.
   * @throws NullPointerException if the description text is <code>null</code>.
   */
  public void setDescription( String description )
    {
    if( description != null )
      this.description = description;
    else
      throw new NullPointerException( );
    }

  /**
   * Returns the Question that acts as a frame of the Question.
   * 
   * @return Returns the upperQuestion.
   */
  public Question getUpperQuestion( )
    {
    return this.upperQuestion;
    }

  /**
   * Sets a new Question to act as a frame of the Question removing this one from the list of subQuestions of the old upperQuestion.
   * 
   * @param upperQuestion The upperQuestion to set.
   */
  public void setUpperQuestion( Question upperQuestion )
    {
    if( this.upperQuestion != null ) this.upperQuestion.removeSubQuestion( this );
    this.upperQuestion = upperQuestion;
    if( this.upperQuestion != null ) this.upperQuestion.addSubQuestion( this );
    }

  /**
   * Returns the list of subQuestions of the Question.
   * 
   * @return Returns the subQuestions.
   */
  public List<Question> getSubQuestions( )
    {
    return Collections.unmodifiableList( this.subQuestions );
    }

  /**
   * Add a new Question to the list of subQuestions.
   * 
   * @param subQuestions The subQuestions to add.
   * @throws NullPointerException If the subquestion is <code>null</code>.
   */
  protected void addSubQuestion( Question subQuestion )
    {
    if( subQuestion != null )
      this.subQuestions.add( subQuestion );
    else
      throw new NullPointerException( );
    }

  /**
   * Remove a Question from the list of subQuestions.
   * 
   * @param subQuestion the subQuestion to remove.
   * @throws NullPointerException If the subquestion is <code>null</code>.
   */
  protected void removeSubQuestion( Question subQuestion )
    {
    if( subQuestion != null )
      this.subQuestions.remove( subQuestion );
    else
      throw new NullPointerException( );
    }
  }