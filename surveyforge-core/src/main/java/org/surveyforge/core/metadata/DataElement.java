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
import java.util.Collections;
import java.util.List;

// TODO Elaborate on comments
/**
 * @author jsegura
 */
public class DataElement
  {
  /**
   * This is a unique and language independent identifier for the data element. The identifier is unique among all other data elements
   * for an object variable (standard data element) or within the scope of a statistical activity.
   */
  private String            identifier;
  /**  */
  private boolean           multiple          = false;
  /**  */
  private int               maxResponses      = 1;
  /**  */
  private ValueDomain       valueDomain;
  /**  */
  private ObjectVariable    objectVariable;
  /**  */
  private DataElement       variableStructure;
  /**  */
  private List<DataElement> componentElements = new ArrayList<DataElement>( );

  /**
   * @param valueDomain
   * @param identifier
   */
  public DataElement( ValueDomain valueDomain, String identifier )
    {
    this.setValueDomain( valueDomain );
    this.setIdentifier( identifier );
    }

  /**
   * @return Returns the identifier.
   */
  public String getIdentifier( )
    {
    return this.identifier;
    }

  /**
   * @param identifier The identifier to set.
   */
  public void setIdentifier( String identifier )
    {
    if( identifier != null && !identifier.equals( "" ) )
      this.identifier = identifier;
    else
      throw new NullPointerException( );
    }

  /**
   * @return Returns the multiple.
   */
  public boolean isMultiple( )
    {
    return this.multiple;
    }

  /**
   * @param multiple The multiple to set.
   */
  public void setMultiple( boolean multiple )
    {
    this.multiple = multiple;
    }

  /**
   * @return Returns the maxResponses.
   */
  public int getMaxResponses( )
    {
    return this.maxResponses;
    }

  /**
   * @param maxResponses The maxResponses to set.
   */
  public void setMaxResponses( int maxResponses )
    {
    if( maxResponses >= 1 )
      this.maxResponses = maxResponses;
    else
      throw new IllegalArgumentException( );
    }

  /**
   * @return Returns the valueDomain.
   */
  public ValueDomain getValueDomain( )
    {
    return this.valueDomain;
    }

  /**
   * @param valueDomain The valueDomain to set.
   */
  public void setValueDomain( ValueDomain valueDomain )
    {
    if( valueDomain != null )
      this.valueDomain = valueDomain;
    else
      throw new NullPointerException( );
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
    if( this.objectVariable != null ) this.objectVariable.removeDataElement( this );
    this.objectVariable = objectVariable;
    if( this.objectVariable != null ) this.objectVariable.addDataElement( this );
    }

  /**
   * @return Returns the variableStructure.
   */
  public DataElement getVariableStructure( )
    {
    return this.variableStructure;
    }

  /**
   * @param variableStructure The variableStructure to set.
   */
  public void setVariableStructure( DataElement variableStructure )
    {
    if( this.variableStructure != null ) this.variableStructure.removeComponentElement( this );
    this.variableStructure = variableStructure;
    if( this.variableStructure != null ) this.variableStructure.addComponentElement( this );
    }

  /**
   * @return Returns the componentElements.
   */
  public List<DataElement> getComponentElements( )
    {
    return Collections.unmodifiableList( this.componentElements );
    }

  /**
   * @param componentElements The componentElements to set.
   */
  protected void addComponentElement( DataElement componentElement )
    {
    if( componentElement != null )
      this.componentElements.add( componentElement );
    else
      throw new NullPointerException( );
    }

  /**
   * @param componentElements The componentElements to set.
   */
  protected void removeComponentElement( DataElement componentElement )
    {
    if( componentElement != null )
      this.componentElements.remove( componentElement );
    else
      throw new NullPointerException( );
    }


  }
