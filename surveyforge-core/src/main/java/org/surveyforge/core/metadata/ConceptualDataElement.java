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

import org.surveyforge.core.survey.Question;

// TODO: Measure unit?
// TODO: Elaborate on comments
/**
 * Conceptual data elements define conceptual standards for data elements. Conceptual data elements have a context independent
 * definition and are associated with a value set and a measure unit describing how the values are measured. A standard question used
 * for collecting data for those variables can be added.
 * 
 * @author jsegura
 */
public class ConceptualDataElement extends DataElement
  {
  private static final long serialVersionUID = -6880246451318487216L;

  /** */
  private Question          question;

  /**
   * Creates a new ConceptualDataElement based on the params of a DataElement
   * 
   * @param valueDomain
   * @param identifier
   */
  public ConceptualDataElement( ValueDomain valueDomain, String identifier )
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


  }
