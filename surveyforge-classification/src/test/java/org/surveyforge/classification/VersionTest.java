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

import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.ExpectedExceptions;
import org.testng.annotations.Test;

/**
 * Tests related to {@link org.surveyforge.classification.Version}.
 * 
 * @author jgonzalez
 */
public class VersionTest
  {
  @Test
  @ExpectedExceptions( {NullPointerException.class})
  public void versionCreationWithNullClassification( )
    {
    new Version( null, null, null );
    }

  @Test
  @ExpectedExceptions( {NullPointerException.class})
  public void versionCreationWithNullIdentifier( )
    {
    new Version( new Classification( new Family( "testFamily" ), "testClassification" ), null, null );
    }

  @Test
  @ExpectedExceptions( {NullPointerException.class})
  public void versionCreationWithNullReleaseDate( )
    {
    new Version( new Classification( new Family( "testFamily" ), "testClassification" ), "testVersion", null );
    }

  @Test
  @ExpectedExceptions( {UnsupportedOperationException.class})
  public void unmodifiableVersions( )
    {
    new Version( new Classification( new Family( "testFamily" ), "testClassification" ), "testVersion", new Date( ) ).getLevels( )
        .add( null );
    }

  @Test
  public void inclusionInClassifications( )
    {
    Family family = new Family( "family" );
    Classification classification1 = new Classification( family, "classification1" );
    Classification classification2 = new Classification( family, "classification2" );

    Version version1 = new Version( classification1, "version1", new Date( ) );
    Version version2 = new Version( classification2, "version2", new Date( ) );

    Assert.assertTrue( classification1.getVersions( ).contains( version1 ) );
    Assert.assertFalse( classification1.getVersions( ).contains( version2 ) );
    Assert.assertFalse( classification2.getVersions( ).contains( version1 ) );
    Assert.assertTrue( classification2.getVersions( ).contains( version2 ) );

    version1.setClassification( classification2 );

    Assert.assertFalse( classification1.getVersions( ).contains( version1 ) );
    Assert.assertFalse( classification1.getVersions( ).contains( version2 ) );
    Assert.assertTrue( classification2.getVersions( ).contains( version1 ) );
    Assert.assertTrue( classification2.getVersions( ).contains( version2 ) );
    }

  @Test
  @ExpectedExceptions( {IllegalArgumentException.class})
  public void unableToAddIncorrectVersion( )
    {
    Family family = new Family( "family" );
    Classification classification1 = new Classification( family, "classification1" );
    Classification classification2 = new Classification( family, "classification2" );

    Version version1 = new Version( classification1, "version1", new Date( ) );
    classification2.addVersion( version1 );
    }

  @Test
  public void predecessorsAndSuccessors( )
    {
    Family family = new Family( "family" );
    Classification classification1 = new Classification( family, "classification1" );
    Date now = new Date( );
    Date yesterday = new Date( );
    yesterday.setTime( now.getTime( ) - 86400000 );
    Date twoDaysAgo = new Date( );
    twoDaysAgo.setTime( yesterday.getTime( ) - 86400000 );

    Version version1 = new Version( classification1, "version1", yesterday );
    Version version2 = new Version( classification1, "version2", now );
    Version version3 = new Version( classification1, "version3", twoDaysAgo );

    Assert.assertEquals( version3.getPredecessor( ), null );
    Assert.assertEquals( version3.getSuccessor( ), version1 );
    Assert.assertEquals( version1.getPredecessor( ), version3 );
    Assert.assertEquals( version1.getSuccessor( ), version2 );
    Assert.assertEquals( version2.getPredecessor( ), version1 );
    Assert.assertEquals( version2.getSuccessor( ), null );
    }

  @Test
  public void computedTerminationDates( )
    {
    Family family = new Family( "family" );
    Classification classification1 = new Classification( family, "classification1" );
    Date now = new Date( );
    Date yesterday = new Date( );
    yesterday.setTime( now.getTime( ) - 86400000 );
    Date twoDaysAgo = new Date( );
    twoDaysAgo.setTime( yesterday.getTime( ) - 86400000 );

    Version version1 = new Version( classification1, "version1", yesterday );
    Version version2 = new Version( classification1, "version2", now );
    Version version3 = new Version( classification1, "version3", twoDaysAgo );

    Assert.assertEquals( version3.getTerminationDate( ), yesterday );
    Assert.assertEquals( version1.getTerminationDate( ), now );
    Assert.assertEquals( version2.getTerminationDate( ), null );
    }
  }
