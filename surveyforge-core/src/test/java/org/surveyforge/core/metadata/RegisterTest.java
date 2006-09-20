/**
 * 
 */
package org.surveyforge.core.metadata;

import org.surveyforge.core.metadata.domain.StructuredValueDomain;
import org.testng.Assert;
import org.testng.annotations.ExpectedExceptions;
import org.testng.annotations.Test;

/**
 * @author jsegura
 */
public class RegisterTest
  {
  @Test
  public void RegisterCreation( )
    {
    Register register = new Register( "register" );
    Assert.assertTrue( register.getValueDomain( ) instanceof StructuredValueDomain );
    Assert.assertEquals( 0, register.getComponentElements( ).size( ) );
    Assert.assertEquals( 0, register.getComponentElements( ).size( ) );
    Assert.assertEquals( 0, register.getKey( ).size( ) );
    }

  @Test
  @ExpectedExceptions( {NullPointerException.class})
  public void RegisterCreationWithNullIdentifier( )
    {
    Register register = new Register( null );
    }

  @Test
  @ExpectedExceptions( {NullPointerException.class})
  public void RegisterCreationWithEmptyIdentifier( )
    {
    Register register = new Register( "" );
    }


  }
