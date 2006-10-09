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
import java.util.Locale;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.GenericGenerator;
import org.surveyforge.util.InternationalizedString;

/**
 * The question contain the text for the question, with the sub questions being used to provide further information about the question.
 * Alternatively, the question may be empty and only the sub questions used. Each sub element has a reference to its upper question.
 * 
 * @author jsegura
 */
@Entity
public class Question implements Serializable
  {
  private static final long       serialVersionUID = 0L;

  @SuppressWarnings("unused")
  @Id
  @Column(length = 50)
  @GeneratedValue(generator = "system-uuid")
  @GenericGenerator(name = "system-uuid", strategy = "uuid")
  private String                  id;
  /** Version for optimistic locking. */
  @SuppressWarnings("unused")
  @javax.persistence.Version
  private int                     lockingVersion;

  /** A question has a language independent identifier that identifies the question among all other globally defined questions. */
  @Column(unique = true, length = 50)
  private String                  identifier;
  /**
   * The question contains the exact text of the question that has been asked to collect the data. The question text is language
   * dependent.
   */
  @ManyToOne(cascade = {CascadeType.ALL})
  private InternationalizedString text             = new InternationalizedString( );
  /** The description contains explanatory notes to the question and/or an extended definition of the question's meaning. */
  @ManyToOne(cascade = {CascadeType.ALL})
  private InternationalizedString description      = new InternationalizedString( );


  protected Question( )
    {}

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
   * Creates a new Question with an identifier and a text.
   * 
   * @param identifier The identifier of this Question.
   * @param text The text of this question
   * @throws NullPointerException if the identifier or the question are <code>null</code>.
   */
  public Question( String identifier, String text )
    {
    this( identifier );
    this.setText( text );
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

  public InternationalizedString getInternationalizedText( )
    {
    return this.text;
    }

  /**
   * Returns the text of this Question.
   * 
   * @return the text of this Question for the default language.
   * @see InternationalizedString#getString()
   */
  public String getText( )
    {
    return this.text.getString( );
    }

  /**
   * Returns the text of this Question for the given language.
   * 
   * @param locale the language of the Question to be returned.
   * @return the text of this Question for the given language.
   * @see InternationalizedString#getString(Locale)
   */
  public String getText( Locale locale )
    {
    return this.text.getString( locale );
    }

  /**
   * Sets the text of this Question. The text must be non <code>null</code>, otherwise a {@link NullPointerException} is thrown.
   * 
   * @param text the text of this Question.
   * @throws NullPointerException if the text is <code>null</code>.
   */
  public void setText( String text )
    {
    if( text != null )
      this.text.setString( text );
    else
      throw new NullPointerException( );
    }

  /**
   * Sets the text of this Question for the given language. The language and text must be non <code>null</code>, otherwise a
   * {@link NullPointerException} is thrown.
   * 
   * @param locale the language of the text to be set.
   * @param text the text of this Question.
   * @throws NullPointerException if the language or text are <code>null</code>.
   */
  public void setText( Locale locale, String text )
    {
    if( text != null )
      this.text.setString( locale, text );
    else
      throw new NullPointerException( );
    }


  public InternationalizedString getInternationalizedDescription( )
    {
    return this.description;
    }

  /**
   * Returns the description of this Question.
   * 
   * @return the description of this Question for the default language.
   * @see InternationalizedString#getString()
   */
  public String getDescription( )
    {
    return this.description.getString( );
    }

  /**
   * Returns the description of this Question for the given language.
   * 
   * @param locale the language of the Question to be returned.
   * @return the description of this Question for the given language.
   * @see InternationalizedString#getString(Locale)
   */
  public String getDescription( Locale locale )
    {
    return this.description.getString( locale );
    }

  /**
   * Sets the description of this Question. The description must be non <code>null</code>, otherwise a {@link NullPointerException}
   * is thrown.
   * 
   * @param description the description of this Question.
   * @throws NullPointerException if the description is <code>null</code>.
   */
  public void setDescription( String description )
    {
    if( description != null )
      this.description.setString( description );
    else
      throw new NullPointerException( );
    }

  /**
   * Sets the description of this Question for the given language. The language and description must be non <code>null</code>,
   * otherwise a {@link NullPointerException} is thrown.
   * 
   * @param locale the language of the description to be set.
   * @param description the description of this Question.
   * @throws NullPointerException if the language or description are <code>null</code>.
   */
  public void setDescription( Locale locale, String description )
    {
    if( description != null )
      this.description.setString( locale, description );
    else
      throw new NullPointerException( );
    }


  @Override
  public boolean equals( Object object )
    {
    Question other = (Question) object;
    return this.getIdentifier( ).equals( other.getIdentifier( ) );
    }

  @Override
  public int hashCode( )
    {
    return this.getIdentifier( ).hashCode( );
    }

  }
