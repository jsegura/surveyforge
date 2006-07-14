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
package org.surveyforge.core.survey;

import org.testng.Assert;
import org.testng.annotations.ExpectedExceptions;
import org.testng.annotations.Test;

/**
 * @author jsegura
 */
public class StudyTest
  {
  @Test
  @ExpectedExceptions( {NullPointerException.class})
  public void studyCreationWithNullIdentifier( )
    {
    new Study( null );
    }

  @Test
  @ExpectedExceptions( {NullPointerException.class})
  public void studyCreationWithEmptyIdentifier( )
    {
    new Study( "" );
    }

  @Test
  public void studyGetIdentifier( )
    {
    String id = "id";
    Study study = new Study( id );
    Assert.assertEquals( study.getIdentifier( ), id );
    }

  @Test
  @ExpectedExceptions( {NullPointerException.class})
  public void studySetNullTitle( )
    {
    new Study( "study" ).setTitle( null );
    }


  @Test
  public void studyGetTitle( )
    {
    Study study = new Study( "study" );
    study.setTitle( "title" );
    Assert.assertEquals( study.getTitle( ), "title" );
    }

  @Test
  @ExpectedExceptions( {NullPointerException.class})
  public void studySetNullDescription( )
    {
    new Study( "id" ).setDescription( null );
    }

  @Test
  public void studyGetDescription( )
    {
    String empty = "";
    String desc = "desc";
    Study obj = new Study( "id" );
    obj.setDescription( desc );
    Assert.assertTrue( obj.getDescription( ).equals( desc ) );
    Assert.assertFalse( obj.getDescription( ).equals( "test" ) );
    obj.setDescription( empty );
    Assert.assertTrue( obj.getDescription( ).equals( empty ) );
    Assert.assertFalse( obj.getDescription( ).equals( "test" ) );
    }

  
  
  

  }
