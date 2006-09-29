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

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.hibernate.annotations.IndexColumn;
import org.surveyforge.core.data.RegisterData;
import org.surveyforge.core.metadata.domain.StructuredValueDomain;

// TODO Elaborate on comments
/**
 * @author jsegura
 */
@Entity
public class Register extends RegisterDataElement
  {


  /**
   * 
   */
  private static final long         serialVersionUID = -7481816596643587493L;

  /**
   * When defining hierarchical registers the hierarchy is expressed as master/detail relationship. The master register defines the
   * objects that consist of the details.
   */
  @ManyToOne
  @JoinColumn(name = "masterRegister_id", insertable = false, updatable = false)
  private Register                  masterRegister   = null;

  /**
   * When defining hierarchical registers the hierarchy is expressed as master/detail relationship. The detail registers defines the
   * objects that are owned by the master objects.
   */
  @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
  @IndexColumn(name = "detailRegistersIndex")
  @JoinColumn(name = "masterRegister_id")
  private List<Register>            detailRegisters  = new ArrayList<Register>( );
  /** */

  /** */
  @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
  @IndexColumn(name = "keyIndex")
  @JoinColumn(name = "registerKey_id")
  private List<RegisterDataElement> key              = new ArrayList<RegisterDataElement>( );

  /** */
  @OneToOne(cascade = {CascadeType.ALL})
  private RegisterData              registerData;

//  /** */
//  @OneToOne(mappedBy = "register", fetch = FetchType.LAZY, optional = true)
//  // @OneToOne(fetch = FetchType.LAZY)
//  private Questionnaire             questionnaire;


  protected Register( )
    {}

  /**
   * @param registerDataElements
   * @param key
   * @param identifier
   */
  public Register( String identifier )
    {
    super( identifier, new StructuredValueDomain( ) );
    this.registerData = new RegisterData( this );
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
    if( this.masterRegister != null ) this.masterRegister.removeDetailRegister( this );
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
  protected void removeDetailRegister( Register detailRegister )
    {
    if( detailRegister != null ) this.detailRegisters.remove( detailRegister );
    }

  // @Override
  // public List<? extends RegisterDataElement> getComponentElements( )
  // {
  // List<RegisterDataElement> componentElements = new ArrayList<RegisterDataElement>( );
  // for( DataElement dataElement : super.getComponentElements( ) )
  // componentElements.add( (RegisterDataElement) dataElement );
  // return componentElements;
  // }

  // @Override
  // public void addComponentElement( DataElement componentElement )
  // {
  // if( !(componentElement instanceof RegisterDataElement) )
  // throw new IllegalArgumentException( );
  // else if( this.registerData.getObjectData( ).size( ) > 0 )
  // throw new IllegalStateException( );
  // else
  // super.addComponentElement( componentElement );
  // }
  //
  // @Override
  // public void removeComponentElement( DataElement componentElement )
  // {
  // if( this.registerData.getObjectData( ).size( ) > 0 )
  // throw new IllegalStateException( );
  // else
  // super.removeComponentElement( componentElement );
  // }
  //
  // @Override
  // public void setComponentElements( List<? extends DataElement> componentElements )
  // {
  // if( componentElements == null )
  // throw new NullPointerException( );
  // else if( this.registerData.getObjectData( ).size( ) > 0 )
  // throw new IllegalStateException( );
  // else if( this.key != null && !componentElements.containsAll( this.key ) )
  // throw new IllegalArgumentException( );
  // else
  // super.setComponentElements( componentElements );
  // }

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
    else if( this.getComponentElements( ).containsAll( key ) )
      this.key = key;
    else
      throw new IllegalArgumentException( );
    }

  /**
   * @return Returns the registerData.
   */
  public RegisterData getRegisterData( )
    {
    return this.registerData;
    }

//  public Questionnaire getQuestionnaire( )
//    {
//    return (Questionnaire) super.getQuestionnaireElement( );
//    }

//  public void setQuestionnaire( Questionnaire questionnaire )
//    {
//    this.questionnaire = questionnaire;
//    }

  @Override
  public boolean equals( Object object )
    {
    if( object instanceof Register )
      {
      Register otherRegister = (Register) object;
      return this.getIdentifier( ).equals( otherRegister.getIdentifier( ) );
      }
    else
      return false;
    }

  @Override
  public int hashCode( )
    {
    return this.getIdentifier( ).hashCode( );
    }
  }
