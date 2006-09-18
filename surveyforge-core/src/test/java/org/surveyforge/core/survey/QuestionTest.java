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
public class QuestionTest
  {

  @Test
  @ExpectedExceptions( {NullPointerException.class})
  public void questionCreationWithNullIdentifier( )
    {
    new Question( null );
    }

  @Test
  @ExpectedExceptions( {NullPointerException.class})
  public void questionCreationWithEmptyIdentifier( )
    {
    new Question( "" );
    }

  @Test
  public void questionGetIdentifier( )
    {
    String id = "id";
    Question question = new Question( id );
    Assert.assertEquals( question.getIdentifier( ), id );
    }

  @Test
  @ExpectedExceptions( {NullPointerException.class})
  public void questionSetNullText( )
    {
    new Question( "question" ).setText( null );
    }


  @Test
  public void questionGetText( )
    {
    Question question = new Question( "question" );
    question.setText( "text" );
    Assert.assertEquals( question.getText( ), "text" );
    }

  @Test
  @ExpectedExceptions( {NullPointerException.class})
  public void questionSetNullDescription( )
    {
    new Question( "id" ).setDescription( null );
    }

  @Test
  public void questionGetDescription( )
    {
    String empty = "";
    String desc = "desc";
    Question obj = new Question( "id" );
    obj.setDescription( desc );
    Assert.assertTrue( obj.getDescription( ).equals( desc ) );
    Assert.assertFalse( obj.getDescription( ).equals( "test" ) );
    obj.setDescription( empty );
    Assert.assertTrue( obj.getDescription( ).equals( empty ) );
    Assert.assertFalse( obj.getDescription( ).equals( "test" ) );
    }
  }
