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

import org.surveyforge.core.metadata.StatisticalObjectType;
import org.testng.Assert;
import org.testng.annotations.ExpectedExceptions;
import org.testng.annotations.Test;

/**
 * @author jsegura
 */
public class StatisticalObjectTypeTest
  {

  @Test
  @ExpectedExceptions( {NullPointerException.class})
  public void statisticalObjectTypeCreationWithNullIdentifier( )
    {
    new StatisticalObjectType( null );
    }

  @Test
  @ExpectedExceptions( {NullPointerException.class})
  public void statisticalObjectTypeCreationWithEmptyIdentifier( )
    {
    new StatisticalObjectType( "" );
    }

  @Test
  public void statisticalObjectTypeGetIdentifier( )
    {
    String id = "id";
    StatisticalObjectType statisticalObjectType = new StatisticalObjectType( id );
    Assert.assertEquals( statisticalObjectType.getIdentifier( ), id );
    }

  @Test
  @ExpectedExceptions( {NullPointerException.class})
  public void statisticalObjectTypeSetNullName( )
    {
    new StatisticalObjectType( "id" ).setName( null );
    }


  @Test
  public void statisticalObjectTypeGetName( )
    {
    StatisticalObjectType statisticalObjectType = new StatisticalObjectType( "id" );
    statisticalObjectType.setName( "name" );
    Assert.assertEquals( statisticalObjectType.getName( ), "name" );
    }

  @Test
  @ExpectedExceptions( {NullPointerException.class})
  public void statisticalObjectTypeSetNullDescription( )
    {
    new StatisticalObjectType( "id" ).setDescription( null );
    }

  @Test
  public void statisticalObjectTypeGetDescription( )
    {
    String empty = "";
    String desc = "desc";
    StatisticalObjectType obj = new StatisticalObjectType( "id" );
    obj.setDescription( desc );
    Assert.assertTrue( obj.getDescription( ).equals( desc ) );
    Assert.assertFalse( obj.getDescription( ).equals( "test" ) );
    obj.setDescription( empty );
    Assert.assertTrue( obj.getDescription( ).equals( empty ) );
    Assert.assertFalse( obj.getDescription( ).equals( "test" ) );
    }


  }
