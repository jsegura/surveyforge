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

import org.surveyforge.core.metadata.Register;

/**
 * A questionnaire is a list of {@link Question}s used to recollect the data into a register. The questionnaire is included in almost
 * one {@link Study}.
 * 
 * @author jsegura
 */
public class Questionnaire
  {
  /**
   * A questionnaire is identified by an identifier, which is unique in the context of a statistical activity. It may typically be an
   * abbreviation of its title or a systematic number.
   */
  private String                     identifier;
  /** A questionnaire has a title as provided by the owner or maintenance unit. */
  private String                     title       = "";
  /**
   * Detailed description of the questionnaire. The questionnaire description typically describes the underlying concept of the
   * questionnaire and basic principles.
   */
  private String                     description = "";
  /** A questionnaire consists of a number of questionnaire elements. Each element refers to a question. */
  private List<QuestionnaireElement> elements    = new ArrayList<QuestionnaireElement>( );
  /** A questionnaire corresponds logically to a register, which describes the content of the data collection. */
  private Register                   register;
  /** A questionnaire is included in a Study. */
  private Study                      study;


  /**
   * Creates a new Questionnaire included in a Study, corresponding to a Register and identified bu identifier.
   * 
   * @param study The {@link Study} this questionnaire belongs to.
   * @param register The {@link Register} this questionnarie belongs to.
   * @param identifier The identifier to set.
   * @throws NullPointerException If the study is <code>null</code> or if the register is <code>null</code> or if the identifier is
   *           <code>null</code> or is empty.
   */
  public Questionnaire( Study study, Register register, String identifier )
    {
    this.setStudy( study );
    this.setRegister( register );
    this.setIdentifier( identifier );
    }

  /**
   * Returns the identifier of the Questionnaire.
   * 
   * @return Returns the identifier.
   */
  public String getIdentifier( )
    {
    return this.identifier;
    }

  /**
   * Sets the identifier of the Questionnaire.
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
   * Returns the title of the questionnaire.
   * 
   * @return Returns the title.
   */
  public String getTitle( )
    {
    return this.title;
    }

  /**
   * Sets the title of the Questionnaire.
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
   * Returns the description of the questionnaire.
   * 
   * @return Returns the description.
   */
  public String getDescription( )
    {
    return this.description;
    }

  /**
   * Sets the description of the Questionnaire.
   * 
   * @param description The description to set.
   * @throws NullPointerException If the description is <code>null</code>
   */
  public void setDescription( String description )
    {
    if( description != null )
      this.description = description;
    else
      throw new NullPointerException( );
    }

  /**
   * Return the list of {@link QuestionnaireElement}s included in this questionnaire.
   * 
   * @return Returns the elements.
   */
  public List<QuestionnaireElement> getElements( )
    {
    return Collections.unmodifiableList( this.elements );
    }


  /**
   * Adds a new {@link Element} to he Questionnaire.
   * 
   * @param element The element to add.
   * @throws NullPointerException If the element is <code>null</code>
   */
  public void addElement( QuestionnaireElement element )
    {
    if( element != null )
      this.elements.add( element );
    else
      throw new NullPointerException( );
    }

  /**
   * Removes a new {@link Element} to the questionnaire.
   * 
   * @param element The element to remove.
   * @throws NullPointerException If the element is <code>null</code>
   */
  public void delElement( QuestionnaireElement element )
    {
    if( element != null )
      this.elements.remove( element );
    else
      throw new NullPointerException( );
    }

  /**
   * Returns the {@link Register} of the questionnaire.
   * 
   * @return Returns the register.
   */
  public Register getRegister( )
    {
    return this.register;
    }

  /**
   * Sets the register of the Questionnaire.
   * 
   * @param register The register to set.
   * @throws NullPointerException If the register is <code>null</code>
   */
  public void setRegister( Register register )
    {
    if( register != null )
      this.register = register;
    else
      throw new NullPointerException( );
    }

  /**
   * Return the study his questionnaire belongs to.
   * 
   * @return Returns the study.
   */
  public Study getStudy( )
    {
    return this.study;
    }

  /**
   * Sets the study of the Questionnaire.
   * 
   * @param register The register to set.
   * @throws NullPointerException If the study is <code>null</code>
   */
  public void setStudy( Study study )
    {
    if( study != null )
      this.study = study;
    else
      throw new NullPointerException( );
    }


  }
