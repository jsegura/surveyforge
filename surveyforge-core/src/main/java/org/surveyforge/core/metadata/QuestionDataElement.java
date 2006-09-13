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
package org.surveyforge.core.metadata;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.surveyforge.core.metadata.domain.AbstractValueDomain;
import org.surveyforge.core.survey.Question;

/**
 * @author jsegura
 */
@Entity
public class QuestionDataElement extends DataElement
  {
  /**
   * 
   */
  private static final long serialVersionUID = 5831283455803703349L;


  @ManyToOne(optional = true)
  @JoinColumn(name = "question_id")
  private Question          question;


  protected QuestionDataElement( )
    {}

  public QuestionDataElement( AbstractValueDomain valueDomain, String identifier )
    {
    super( valueDomain, identifier );
    }

  /**
   * @return Returns the question.
   */
  public Question getQuestion( )
    {
    return this.question;
    }

  /**
   * @param question The question to set.
   */
  public void setQuestion( Question question )
    {
    this.question = question;
    }

  @Override
  public boolean equals( Object object )
    {
    QuestionDataElement other = (QuestionDataElement) object;
    return this.getIdentifier( ).equals( other.getIdentifier( ) );
    }

  @Override
  public int hashCode( )
    {
    return this.getIdentifier( ).hashCode( );
    }
  }
