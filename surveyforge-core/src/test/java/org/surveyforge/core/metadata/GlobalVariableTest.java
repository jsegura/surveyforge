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

import org.testng.Assert;
import org.testng.annotations.ExpectedExceptions;
import org.testng.annotations.Test;

/**
 * @author jsegura
 */
public class GlobalVariableTest
  {

  @Test
  @ExpectedExceptions( {NullPointerException.class})
  public void globalVariableCreationWithNullIdentifier( )
    {
    new GlobalVariable( null );
    }


  @Test
  @ExpectedExceptions( {NullPointerException.class})
  public void globalVariableCreationWithEmptyIdentifier( )
    {
    new GlobalVariable( "" );
    }

  @Test
  public void globalVariableCreation( )
    {
    new GlobalVariable( "GlobalVariable" );
    }

  @Test
  public void globalVariableGetIdentifier( )
    {
    String id = "id";
    GlobalVariable globalVariable = new GlobalVariable( id );
    Assert.assertTrue( globalVariable.getIdentifier( ).equals( id ) );
    Assert.assertFalse( globalVariable.getIdentifier( ).equals( "test" ) );
    }

  @Test
  @ExpectedExceptions( {NullPointerException.class})
  public void globalVariableSetNullName( )
    {
    new GlobalVariable( "id" ).setName( null );
    }

  @Test
  public void globalVariableSetName( )
    {
    String name = "name";
    GlobalVariable gb = new GlobalVariable( "id" );
    gb.setName( name );
    Assert.assertTrue( gb.getName( ).equals( name ) );
    }

  @Test
  @ExpectedExceptions( {NullPointerException.class})
  public void globalVariableSetNullDescription( )
    {
    new GlobalVariable( "id" ).setDescription( null );
    }

  @Test
  public void globalVariableGetDescription( )
    {
    String empty = "";
    String desc = "desc";
    GlobalVariable obj = new GlobalVariable( "id" );
    obj.setDescription( desc );
    Assert.assertTrue( obj.getDescription( ).equals( desc ) );
    Assert.assertFalse( obj.getDescription( ).equals( "test" ) );
    obj.setDescription( empty );
    Assert.assertTrue( obj.getDescription( ).equals( empty ) );
    Assert.assertFalse( obj.getDescription( ).equals( "test" ) );
    }

  @Test
  public void globalVariableGetFamily( )
    {
    GlobalVariable gb = new GlobalVariable( "id" );
    VariableFamily family = new VariableFamily( "family" );
    Assert.assertNull( gb.getVariableFamily( ) );
    gb.setVariableFamily( family );
    Assert.assertEquals( gb.getVariableFamily( ), family );
    }

  }
