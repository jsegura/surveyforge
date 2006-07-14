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

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ArrayList;

import org.surveyforge.core.data.RegisterData;

// TODO Elaborate on comments
/**
 * @author jsegura
 */
public class Register
  {
  /**
   * The name of the register is either a systematic name according to the naming conventions in the organisation or a human language
   * name that reflects the idea or concept of the cube or register.
   */
  private String                         identifier;
  /**
   * When defining hierarchical registers the hierarchy is expressed as master/detail relationship. The master register defines the
   * objects that consist of the details.
   */
  private Register                       masterRegister       = null;
  /**
   * When defining hierarchical registers the hierarchy is expressed as master/detail relationship. The detail registers defines the
   * objects that are owned by the master objects.
   */
  private List<Register>                 detailRegisters      = new ArrayList<Register>( );
  /** */
  private LinkedHashMap<String, Integer> indexes;
  /** */
  private List<RegisterDataElement>      registerDataElements = new ArrayList<RegisterDataElement>( );
  /** */
  private List<RegisterDataElement>      key                  = new ArrayList<RegisterDataElement>( );
  /** */
  private List<ValidationRule>           validationRules      = new ArrayList<ValidationRule>( );
  /** */
  private RegisterData                   registerData;

  /**
   * @param registerDataElements
   * @param key
   * @param identifier
   */
  public Register( String identifier )
    {
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
   * @return Returns the masterRegister.
   */
  public Register getMasterRegister( )
    {
    return this.masterRegister;
    }

  /**
   * @param masterRegister The masterRegister to set.
   */
  public void setMasterRegister( Register masterRegister )
    {
    if( this.masterRegister != null ) this.masterRegister.delDetailRegister( this );
    this.masterRegister = masterRegister;
    if( this.masterRegister != null ) this.masterRegister.addDetailRegister( this );

    }

  /**
   * @return Returns the detailRegister.
   */
  public List<Register> getDetailRegisters( )
    {
    return Collections.unmodifiableList( this.detailRegisters );
    }

  /**
   * @param detailRegister The detailRegister to set.
   */
  protected void addDetailRegister( Register detailRegister )
    {
    if( detailRegister != null ) this.detailRegisters.add( detailRegister );
    }

  /**
   * @param detailRegister The detailRegister to set.
   */
  protected void delDetailRegister( Register detailRegister )
    {
    if( detailRegister != null ) this.detailRegisters.remove( detailRegister );
    }

  /**
   * @return Returns the registerDataElements.
   */
  public List<RegisterDataElement> getRegisterDataElements( )
    {
    return Collections.unmodifiableList( this.registerDataElements );
    }

  /**
   * @param registerDataElements
   */
  public void setRegisterDataElements( List<RegisterDataElement> registerDataElements )
    {
    if( registerDataElements == null )
      throw new NullPointerException( );
    else if( registerData != null )
      throw new IllegalArgumentException( );
    else if( key != null && !registerDataElements.containsAll( key ) )
      throw new IllegalArgumentException( );
    else
      {
      this.registerDataElements = registerDataElements;
      this.updateElements( );
      }
    }


  /**
   * @return Returns the key.
   */
  public List<RegisterDataElement> getKey( )
    {
    return Collections.unmodifiableList( this.key );
    }

  /**
   * @param key The key to set.
   */
  public void setKey( List<RegisterDataElement> key )
    {
    if( key == null )
      throw new NullPointerException( );
    else if( this.registerDataElements.containsAll( key ) )
      this.key = key;
    else
      throw new IllegalArgumentException( );
    }

  /**
   * @return Returns the validationRules.
   */
  public List<ValidationRule> getValidationRules( )
    {
    return Collections.unmodifiableList( this.validationRules );
    }

  /**
   * @param validationRules The validationRules to set.
   */
  public void setValidationRules( List<ValidationRule> validationRules )
    {
    if( validationRules != null )
      this.validationRules = validationRules;
    else
      throw new NullPointerException( );
    }

  /**
   * @param validationRule
   */
  public void addValidationRule( ValidationRule validationRule )
    {
    if( validationRule != null )
      this.validationRules.add( validationRule );
    else
      throw new NullPointerException( );
    }

  /**
   * @param validationRule
   */
  public void delValidationRule( ValidationRule validationRule )
    {
    if( validationRule != null )
      this.validationRules.remove( validationRule );
    else
      throw new NullPointerException( );
    }

  /**
   * @return Returns the registerData.
   */
  public RegisterData getRegisterData( )
    {
    return this.registerData;
    }

  /**
   * @param registerData The registerData to set.
   */
  public void setRegisterData( RegisterData registerData )
    {
    if( registerData != null && this.registerDataElements.isEmpty( ) )
      throw new IllegalArgumentException( );
    else if( registerData == null && !this.registerDataElements.isEmpty( ) )
      throw new NullPointerException( );
    else
      this.registerData = registerData;

    }

  private void updateElements( )
    {
    LinkedHashMap<String, Integer> fieldIndexes = new LinkedHashMap<String, Integer>( );
    int index = 0;
    for( RegisterDataElement currentRegisterDataElement : this.registerDataElements )
      {
      fieldIndexes.put( currentRegisterDataElement.getIdentifier( ), index );
      index++;
      }

    if( this.registerDataElements.size( ) == fieldIndexes.size( ) )
      {
      this.indexes = fieldIndexes;
      }
    else
      {
      // There are duplicated field names
      // String errorMessage = RecordMetadata.messages.getString( "message.duplicated.names" );
      // if( RecordMetadata.log.isInfoEnabled( ) )
      // {
      // RecordMetadata.log.info( RecordMetadata.messages.getString( "exception.duplicated.names" ) + errorMessage );
      // }
      throw new IllegalArgumentException( );
      }
    }

  public int getElementIndex( String elementIdentifier )
    {
    Integer index = this.indexes.get( elementIdentifier );
    return (index != null) ? index : -1;
    }

  public List<String> getElementIdentifiers( )
    {
    return new ArrayList<String>( this.indexes.keySet( ) );
    }

  }
