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

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;

import org.surveyforge.core.metadata.domain.AbstractValueDomain;
import org.surveyforge.core.survey.Question;
import org.surveyforge.core.survey.QuestionnaireElement;

/**
 * Register data elements define data elements for a register. In contrast to an abstract data element a register data element is
 * defined in the context of a register and carries process information besides conceptual one.
 * 
 * @author jsegura
 */
@Entity
// TODO: Improve javadocs
public class RegisterDataElement extends DataElement
  {
  private static final long     serialVersionUID      = -8986375207717119033L;

  /** The context data element describes the basic properties of the register data element. */
  @OneToOne(fetch = FetchType.LAZY)
  private ConceptualDataElement conceptualDataElement = null;
  /** */
  private boolean               optional              = true;
  /** */
  @ManyToOne(optional = true)
  private Question              question;

  @OneToOne
  private QuestionnaireElement  questionnaireElement;

  protected RegisterDataElement( )
    {};

  /**
   * Creates a new instance of ConceptualDataElement linked with a {@link ConceptualDataElement}.
   * 
   * @param conceptualDataElement The conceptualDataElement that describes the {@link RegisterDataElement}.
   * @throws NullPointerException If the ValueDomain or the identifier or the ConceptualDataElements are <code>null</code> or the
   *           identifier is empty.
   */
  public RegisterDataElement( String identifier, ConceptualDataElement conceptualDataElement )
    {
    super( identifier );
    this.setConceptualDataElement( conceptualDataElement );
    }

  /**
   * Creates a new instance of ConceptualDataElement linked with a {@link ConceptualDataElement}.
   */
  public RegisterDataElement( String identifier, AbstractValueDomain valueDomain )
    {
    super( identifier, valueDomain );
    }

  /**
   * Returns the ConceptualDataElement that describes the RegisterDataElement.
   * 
   * @return Returns the conceptualDataElement.
   */
  public ConceptualDataElement getConceptualDataElement( )
    {
    return this.conceptualDataElement;
    }

  /**
   * Sets the ConceptualDataElement that describes the RegisterDataElement.
   * 
   * @param conceptualDataElement The conceptualDataElements to set.
   * @throws NullPointerException If the conceptualDataElement is <code>null</code>.
   */
  private void setConceptualDataElement( ConceptualDataElement conceptualDataElement )
    {
    this.conceptualDataElement = conceptualDataElement;
    }

  @Override
  public List<? extends RegisterDataElement> getComponentElements( )
    {
    List<RegisterDataElement> componentElements = new ArrayList<RegisterDataElement>( );
    for( DataElement dataElement : super.getComponentElements( ) )
      componentElements.add( (RegisterDataElement) dataElement );
    return componentElements;
    }

  @Override
  public void addComponentElement( DataElement componentElement )
    {
    if( !(componentElement instanceof RegisterDataElement) )
      throw new IllegalArgumentException( );
    else
      {
      DataElement root = this.getRootDataElement( );
      if( root instanceof Register && ((Register) root).getRegisterData( ).getObjectData( ).size( ) > 0 )
        throw new IllegalStateException( );
      else
        super.addComponentElement( componentElement );
      }
    }

  @Override
  public void removeComponentElement( DataElement componentElement )
    {
    if( !(this.getRootDataElement( ) instanceof Register)
        || ((Register) this.getRootDataElement( )).getRegisterData( ).getObjectData( ).size( ) > 0 )
      throw new IllegalStateException( );
    else
      super.removeComponentElement( componentElement );
    }

  @Override
  public void setComponentElements( List<? extends DataElement> componentElements )
    {
    if( componentElements == null )
      throw new NullPointerException( );
    else if( ((Register) this.getRootDataElement( )).getRegisterData( ).getObjectData( ).size( ) > 0 )
      throw new IllegalStateException( );
    else
      super.setComponentElements( componentElements );
    }

  /**
   * @return the optional
   */
  public boolean isOptional( )
    {
    return this.optional;
    }

  /**
   * @param optional the optional to set
   */
  public void setOptional( boolean optional )
    {
    this.optional = optional;
    }

  /**
   * @return the question
   */
  public Question getQuestion( )
    {
    return this.question;
    }

  /**
   * @param question the question to set
   */
  public void setQuestion( Question question )
    {
    this.question = question;
    }

  /**
   * @return the questionnaireElement
   */
  public QuestionnaireElement getQuestionnaireElement( )
    {
    return questionnaireElement;
    }

  /**
   * @param questionnaireElement the questionnaireElement to set
   */
  public void setQuestionnaireElement( QuestionnaireElement questionnaireElement )
    {
    this.questionnaireElement = questionnaireElement;
    }

  public AbstractValueDomain getValueDomain( )
    {
    return (super.getValueDomain( ) != null ? super.getValueDomain( ) : this.getConceptualDataElement( ).getValueDomain( ));
    }
  }
