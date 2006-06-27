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

import org.testng.Assert;
import org.testng.annotations.ExpectedExceptions;
import org.testng.annotations.Test;

/**
 * Tests related to {@link org.surveyforge.classification.Classification}.
 * 
 * @author jgonzalez
 */
public class ClassificationTest
  {
  @Test
  @ExpectedExceptions( {NullPointerException.class})
  public void classificationCreationWithNullFamily( )
    {
    new Classification( null, null );
    }

  @Test
  @ExpectedExceptions( {NullPointerException.class})
  public void classificationCreationWithNullIdentifier( )
    {
    new Classification( new Family( "testFamily" ), null );
    }

  @Test
  @ExpectedExceptions( {UnsupportedOperationException.class})
  public void unmodifiableVersions( )
    {
    new Classification( new Family( "testFamily" ), "testClassification" ).getVersions( ).add( null );
    }

  @Test
  public void inclusionInFamilies( )
    {
    Family family1 = new Family( "family1" );
    Family family2 = new Family( "family2" );

    Classification classification1 = new Classification( family1, "classif1" );
    Classification classification2 = new Classification( family2, "classif2" );

    Assert.assertTrue( family1.getClassifications( ).contains( classification1 ) );
    Assert.assertFalse( family1.getClassifications( ).contains( classification2 ) );
    Assert.assertFalse( family2.getClassifications( ).contains( classification1 ) );
    Assert.assertTrue( family2.getClassifications( ).contains( classification2 ) );

    classification1.setFamily( family2 );

    Assert.assertFalse( family1.getClassifications( ).contains( classification1 ) );
    Assert.assertFalse( family1.getClassifications( ).contains( classification2 ) );
    Assert.assertTrue( family2.getClassifications( ).contains( classification1 ) );
    Assert.assertTrue( family2.getClassifications( ).contains( classification2 ) );
    }

  @Test
  @ExpectedExceptions( {IllegalArgumentException.class})
  public void unableToAddIncorrectClassification( )
    {
    Family family1 = new Family( "family1" );
    Family family2 = new Family( "family2" );
    Classification classification1 = new Classification( family1, "classif1" );
    family2.addClassification( classification1 );
    }
  }
