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
import javax.persistence.ManyToOne;

import org.surveyforge.core.metadata.domain.AbstractValueDomain;

// TODO: Elaborate on comments
/**
 * Conceptual data elements define conceptual standards for data elements. Conceptual data elements have a context independent
 * definition and are associated with a value set and a measure unit describing how the values are measured. A standard question used
 * for collecting data for those variables can be added.
 * 
 * @author jsegura
 */
@Entity
public class ConceptualDataElement extends QuestionDataElement
  {
  private static final long serialVersionUID = -6880246451318487216L;

  /**  */
  @ManyToOne
  private ObjectVariable    objectVariable;


  protected ConceptualDataElement( )
    {}

  /**
   * Creates a new ConceptualDataElement based on the params of a DataElement
   * 
   * @param valueDomain
   * @param identifier
   */
  public ConceptualDataElement( String identifier, AbstractValueDomain valueDomain, ObjectVariable objectVariable )
    {
    super( identifier, valueDomain );
    this.setObjectVariable( objectVariable );
    }


  /**
   * @return Returns the objectVariable.
   */
  public ObjectVariable getObjectVariable( )
    {
    return this.objectVariable;
    }

  /**
   * @param objectVariable The objectVariable to set.
   */
  public void setObjectVariable( ObjectVariable objectVariable )
    {
    if( objectVariable != null )
      this.objectVariable = objectVariable;
    else
      throw new NullPointerException( );
    }

  @Override
  public boolean equals( Object object )
    {
    ConceptualDataElement other = (ConceptualDataElement) object;
    return this.getIdentifier( ).equals( other.getIdentifier( ) );
    }

  @Override
  public int hashCode( )
    {
    return this.getIdentifier( ).hashCode( );
    }
  }