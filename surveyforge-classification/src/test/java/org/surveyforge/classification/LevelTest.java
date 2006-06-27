/* 
 * surveyforge-classification - Copyright (C) 2006 OPEN input - http://www.openinput.com/
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
package org.surveyforge.classification;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.testng.Assert;
import org.testng.annotations.ExpectedExceptions;
import org.testng.annotations.Test;

/**
 * Tests related to {@link org.surveyforge.classification.Level}.
 * 
 * @author jgonzalez
 */
public class LevelTest
  {
  @Test
  @ExpectedExceptions( {NullPointerException.class})
  public void levelCreationWithNullVersion( )
    {
    new Level( null, null );
    }

  @Test
  @ExpectedExceptions( {NullPointerException.class})
  public void levelCreationWithNullIdentifier( )
    {
    new Level( new Version( new Classification( new Family( "testFamily" ), "testFamily" ), "testVersion", new Date( ) ), null );
    }

  @Test
  @ExpectedExceptions( {UnsupportedOperationException.class})
  public void unmodifiableItems( )
    {
    new Level( new Version( new Classification( new Family( "testFamily" ), "testFamily" ), "testVersion", new Date( ) ), "testLevel" )
        .getItems( ).add( null );
    }

  @Test
  public void inclusionInVersions( )
    {
    Family family = new Family( "family" );
    Classification classification = new Classification( family, "classification" );
    Version version1 = new Version( classification, "version1", new Date( ) );
    Version version2 = new Version( classification, "version2", new Date( ) );

    Level level1 = new Level( version1, "level1" );
    Level level2 = new Level( version2, "level2" );

    Assert.assertTrue( version1.getLevels( ).contains( level1 ) );
    Assert.assertFalse( version1.getLevels( ).contains( level2 ) );
    Assert.assertFalse( version2.getLevels( ).contains( level1 ) );
    Assert.assertTrue( version2.getLevels( ).contains( level2 ) );
    }

  @Test
  @ExpectedExceptions( {IllegalArgumentException.class})
  public void unableToAddIncorrectLevel( )
    {
    Family family = new Family( "family" );
    Classification classification = new Classification( family, "classification" );
    Version version1 = new Version( classification, "version1", new Date( ) );
    Version version2 = new Version( classification, "version2", new Date( ) );

    Level level = new Level( version1, "level1" );
    version2.addLevel( level );
    }

  @Test
  public void orderOfLevels( )
    {
    Family family = new Family( "family" );
    Classification classification = new Classification( family, "classification" );
    Version version = new Version( classification, "version", new Date( ) );

    Level level1 = new Level( version, "level1" );
    Level level2 = new Level( version, "level2" );
    Level level3 = new Level( version, "level3" );

    List<Level> listOfLevels = Arrays.asList( new Level[] {level1, level2, level3} );

    Assert.assertEquals( version.getLevels( ), listOfLevels );
    }

  // TODO: Test the fullyCovered method
  }
