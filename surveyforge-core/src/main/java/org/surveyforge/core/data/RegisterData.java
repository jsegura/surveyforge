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
package org.surveyforge.core.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.surveyforge.core.metadata.Register;

/**
 * @author jsegura
 */
public class RegisterData implements Serializable
  {
  private static final long serialVersionUID = 4300393468288029803L;

  private String            identifier;
  private Register          register;
  private List<Row>         rows             = new ArrayList<Row>( );

  public RegisterData( Register register, String identifier )
    {
    this.setRegister( register );
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
   * @return Returns the register.
   */
  public Register getRegister( )
    {
    return this.register;
    }

  /**
   * @param register The register to set.
   */
  public void setRegister( Register register )
    {
    if( register == null )
      throw new NullPointerException( );
    else if( this.register != null && this.rows.size( ) != 0 ) throw new IllegalArgumentException( );
    this.register = register;
    }

  /**
   * @return Returns the rows.
   */
  public List<Row> getRows( )
    {
    return Collections.unmodifiableList( this.rows );
    }

  /**
   * @param rows The rows to .
   */
  protected void addRows( Row row )
    {
    if( row != null )
      {
      if( this.register.getKey( ).size( ) == 0 ) throw new IllegalArgumentException( );
      ArrayList<Integer> illegalList = new ArrayList<Integer>( );
      // TODO: Check valiadation rules

      if( this.register.getRegisterDataElements( ).size( ) != row.getRowDatas( ).size( ) ) throw new IllegalArgumentException( );
      int index = 0;
      for( RowData rowData : row.getRowDatas( ) )
        {
        if( !this.register.getRegisterDataElements( ).get( index ).getValueDomain( ).isValid( rowData.getData( ) ) )
          illegalList.add( index ); // TODO: Elaborate on this Exception
        index++;
        }
      if( illegalList.size( ) == 0 ) this.rows.add( row );
      // TODO: else throw Exception
      }

    else
      throw new NullPointerException( );
    }

  /**
   * @param rows The rows to .
   */
  protected void removeRows( Row row )
    {
    if( row != null )
      this.rows.remove( row );
    else
      throw new NullPointerException( );
    }
  }
