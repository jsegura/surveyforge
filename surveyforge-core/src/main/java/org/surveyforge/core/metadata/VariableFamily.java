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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A variable family groups a number of global variables that refer to a certain theme.
 * 
 * @author jsegura
 */
public class VariableFamily implements Serializable
  {
  private static final long    serialVersionUID = 7344092827765295413L;

  /** A VariableFamily is identified by a unique identifier. */
  private String               identifier;
  /**
   * Detailed description of the VariableFamily. The description describes the theme of the {@link GlobalVariable}s included in the
   * family.
   */
  private String               description      = "";
  /** The family have the list of all {@link GlobalVariable}s included in the theme. */
  private List<GlobalVariable> globalVariables  = new ArrayList<GlobalVariable>( );


  /**
   * Creates a new Family identified by the identifier.
   * 
   * @param identifier The identifier of the family.
   * @throws NullPointerException If the identifier is <code>null</code> or is empty.
   */
  public VariableFamily( String identifier )
    {
    this.setIdentifier( identifier );
    }

  /**
   * Returns the identifier of the family.
   * 
   * @return Returns the identifier.
   */
  public String getIdentifier( )
    {
    return this.identifier;
    }

  /**
   * Sets a new identifier to the family.
   * 
   * @param identifier The identifier to set.
   * @throws NullPointerException If the identifier is <code>null</code> or is empty.
   */
  public void setIdentifier( String identifier )
    {
    if( identifier != null && !identifier.equals( "" ) )
      this.identifier = identifier;
    else
      throw new NullPointerException( );
    }

  /**
   * Returns the description of the family.
   * 
   * @return Returns the description.
   */
  public String getDescription( )
    {
    return this.description;
    }

  /**
   * Sets a new description to the family.
   * 
   * @param description The description to set.
   * @throws NullPointerException If the description is <code>null</code>.
   */
  public void setDescription( String description )
    {
    if( description != null )
      this.description = description;
    else
      throw new NullPointerException( );
    }

  /**
   * Returns the list of {@link GlobalVariable}s of the family.
   * 
   * @return Returns the list of {@link GlobalVariable}s.
   */
  public List<GlobalVariable> getGlobalVariables( )
    {
    return Collections.unmodifiableList( this.globalVariables );
    }

  /**
   * Adds a {@link GlobalVariable} to the family.
   * 
   * @param globalVariable The {@link GlobalVariable} to add.
   * @throws NullPointerException If the {@link GlobalVariable} is <code>null</code>.
   */
  protected void addGlobalVariable( GlobalVariable globalVariable )
    {
    if( globalVariable != null )
      this.globalVariables.add( globalVariable );
    else
      throw new NullPointerException( );
    }

  /**
   * Removes a {@link GlobalVariable} belonging to the family.
   * 
   * @param globalVariable The {@link GlobalVariable} to remove.
   * @throws NullPointerException If the {@link GlobalVariable} is <code>null</code>.
   */
  protected void removeGlobalVariable( GlobalVariable globalVariable )
    {
    if( globalVariable != null )
      this.globalVariables.remove( globalVariable );
    else
      throw new NullPointerException( );
    }
  }
