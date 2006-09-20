/**
 * 
 */
package org.surveyforge.core.metadata;

import org.surveyforge.core.metadata.domain.LogicalValueDomain;
import org.surveyforge.core.metadata.domain.StringValueDomain;
import org.surveyforge.core.metadata.domain.StructuredValueDomain;
import org.testng.Assert;
import org.testng.annotations.ExpectedExceptions;
import org.testng.annotations.Test;

/**
 * @author jsegura
 */
public class DataElementTest
  {

  @Test
  public void RegisterDataElementWithEmptyKey( )
    {
    Register register = new Register( "register" );
    StructuredValueDomain upperVD = new StructuredValueDomain( );
    RegisterDataElement upperRDE = new RegisterDataElement( "upperRDE", upperVD );
    register.addComponentElement( upperRDE );

    Assert.assertEquals( 1, register.getComponentElements( ).size( ) );

    StringValueDomain svd = new StringValueDomain( 0, 10 );
    RegisterDataElement subRDE1 = new RegisterDataElement( "sub1", svd );
    upperRDE.addComponentElement( subRDE1 );

    Assert.assertEquals( 1, upperRDE.getComponentElements( ).size( ) );
    Assert.assertEquals( 1, ((StructuredValueDomain) upperRDE.getValueDomain( )).getSubDomains( ).size( ) );
    RegisterDataElement subRDE2 = new RegisterDataElement( "sub2", new LogicalValueDomain( ) );
    upperRDE.addComponentElement( subRDE2 );
    Assert.assertEquals( 2, upperRDE.getComponentElements( ).size( ) );
    Assert.assertEquals( 2, ((StructuredValueDomain) upperRDE.getValueDomain( )).getSubDomains( ).size( ) );
    Assert.assertEquals( subRDE1.getValueDomain( ), ((StructuredValueDomain) upperRDE.getValueDomain( )).getSubDomains( ).get( 0 ) );
    Assert.assertEquals( subRDE2.getValueDomain( ), ((StructuredValueDomain) upperRDE.getValueDomain( )).getSubDomains( ).get( 1 ) );
    Assert.assertEquals( 2, upperRDE.getComponentElements( ).size( ) );

    upperRDE.removeComponentElement( subRDE1 ); //
    Assert.assertEquals( 1, upperRDE.getComponentElements( ).size( ) );
    Assert.assertEquals( 1, ((StructuredValueDomain) upperRDE.getValueDomain( )).getSubDomains( ).size( ) );
    Assert.assertEquals( subRDE2.getValueDomain( ), ((StructuredValueDomain) upperRDE.getValueDomain( )).getSubDomains( ).get( 0 ) );
    upperRDE.removeComponentElement( subRDE2 );
    Assert.assertEquals( 0, upperRDE.getComponentElements( ).size( ) );
    Assert.assertEquals( 0, ((StructuredValueDomain) upperRDE.getValueDomain( )).getSubDomains( ).size( ) );

    }

  @Test
  public void RegisterDataElementCreation( )
    {
    String id = "id";
    LogicalValueDomain lvd = new LogicalValueDomain( );
    RegisterDataElement rde = new RegisterDataElement( id, lvd );
    Assert.assertTrue( rde.getIdentifier( ).compareTo( id ) == 0 );
    Assert.assertEquals( lvd, rde.getValueDomain( ) );

    }

  @Test
  @ExpectedExceptions( {NullPointerException.class})
  public void RegisterDataElementCreationWithNullIdentifier( )
    {
    LogicalValueDomain lvd = new LogicalValueDomain( );
    RegisterDataElement rde = new RegisterDataElement( null, lvd );

    }

  @Test
  @ExpectedExceptions( {NullPointerException.class})
  public void RegisterCreationWithNullDomain( )
    {
    LogicalValueDomain lvd = null;
    RegisterDataElement rde = new RegisterDataElement( "id", lvd );

    }


  }
