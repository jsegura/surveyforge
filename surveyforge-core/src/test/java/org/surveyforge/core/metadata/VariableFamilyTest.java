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

import org.testng.annotations.ExpectedExceptions;
import org.testng.annotations.Test;
import org.testng.Assert;

/**
 * @author jsegura
 */
public class VariableFamilyTest
  {
  @Test
  @ExpectedExceptions( {NullPointerException.class})
  public void variableFamilyCreationWithNullIdentifier( )
    {
    new VariableFamily( null );
    }


  @Test
  @ExpectedExceptions( {NullPointerException.class})
  public void variableFamilyCreationWithEmptyIdentifier( )
    {
    new VariableFamily( "" );
    }

  @Test
  public void variableFamilyCreation( )
    {
    new VariableFamily( "VariableFamily" );
    }

  @Test
  public void variableFamilyGetIdentifier( )
    {
    String id = "id";
    VariableFamily variableFamily = new VariableFamily( id );
    Assert.assertTrue( variableFamily.getIdentifier( ).equals( id ) );
    Assert.assertFalse( variableFamily.getIdentifier( ).equals( "test" ) );
    }

  @Test
  @ExpectedExceptions( {NullPointerException.class})
  public void variableFamilySetNullDescription( )
    {
    new VariableFamily( "variableFamily" ).setDescription( null );
    }

  @Test
  public void variableFamilyGetDescription( )
    {
    String desc = "desc";
    String empty = "";
    VariableFamily variableFamily = new VariableFamily( "id" );
    variableFamily.setDescription( desc );
    Assert.assertTrue( variableFamily.getDescription( ).equals( desc ) );
    Assert.assertFalse( variableFamily.getDescription( ).equals( "test" ) );
    variableFamily.setDescription( empty );
    Assert.assertTrue( variableFamily.getDescription( ).equals( empty ) );
    Assert.assertFalse( variableFamily.getDescription( ).equals( "test" ) );
    }

  @Test
  public void variableFamilyGetObjectVariables( )
    {
    Assert.assertEquals( new ArrayList<GlobalVariable>( ), new VariableFamily( "id" ).getGlobalVariables( ) );
    GlobalVariable gb1 = new GlobalVariable( "gb1" );
    GlobalVariable gb2 = new GlobalVariable( "gb2" );
    VariableFamily family = new VariableFamily( "vf" );
    gb1.setVariableFamily( family );
    Assert.assertTrue( family.getGlobalVariables( ).contains( gb1 ) );
    gb2.setVariableFamily( family );
    Assert.assertTrue( family.getGlobalVariables( ).contains( gb1 ) );
    Assert.assertTrue( family.getGlobalVariables( ).contains( gb2 ) );
    family.removeGlobalVariable( gb1 );
    Assert.assertFalse( family.getGlobalVariables( ).contains( gb1 ) );
    Assert.assertTrue( family.getGlobalVariables( ).contains( gb2 ) );
    }

  @Test
  @ExpectedExceptions( {NullPointerException.class})
  public void variableFamilyAddNullGlobalVariable( )
    {
    new VariableFamily( "family" ).addGlobalVariable( null );
    }

  @Test
  @ExpectedExceptions( {NullPointerException.class})
  public void variableFamilyRemoveAddNullGlobalVariable( )
    {
    new VariableFamily( "family" ).removeGlobalVariable( null );
    }

  }
