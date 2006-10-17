/**
 * 
 */
package org.surveyforge.core.metadata.domain;

import java.security.InvalidParameterException;
import java.util.Calendar;
import java.util.Date;

import org.testng.Assert;
import org.testng.annotations.ExpectedExceptions;
import org.testng.annotations.Test;

/**
 * @author jsegura
 */
public class DateValueDomainTest
  {

  @Test
  public void DateValueDomainGettersSetters( )
    {
    DateValueDomain domain = new DateValueDomain( );
    domain.getFormat( );
    Assert.assertNull( domain.getMaxValue( ) );
    Assert.assertNull( domain.getMinValue( ) );

    Date before = new Date( Calendar.getInstance( ).getTimeInMillis( ) );
    Date later = new Date( Calendar.getInstance( ).getTimeInMillis( ) + 10000 );


    String pattern = "yyyy";
    domain.setPattern( pattern );
    Assert.assertEquals( pattern, domain.getPattern( ) );

    domain.setMaxValue( later );
    Assert.assertNotNull( domain.getMaxValue( ) );
    Assert.assertEquals( later, domain.getMaxValue( ) );

    domain.setMinValue( before );
    Assert.assertNotNull( domain.getMinValue( ) );
    Assert.assertEquals( before, domain.getMinValue( ) );

    domain.setMaxValue( later );
    Assert.assertNotNull( domain.getMaxValue( ) );
    Assert.assertEquals( later, domain.getMaxValue( ) );

    }

  @Test
  public void DateValueDomainCreation( )
    {
    new DateValueDomain( );
    }

  @Test
  @ExpectedExceptions( {InvalidParameterException.class})
  public void DateValueDomainSetMaxValue( )
    {
    DateValueDomain domain = new DateValueDomain( );
    Date before = new Date( Calendar.getInstance( ).getTimeInMillis( ) );
    Date later = new Date( Calendar.getInstance( ).getTimeInMillis( ) + 10000 );

    domain.setMinValue( later );
    domain.setMaxValue( before );
    }

  @Test
  @ExpectedExceptions( {InvalidParameterException.class})
  public void DateValueDomainSetMinValue( )
    {
    DateValueDomain domain = new DateValueDomain( );
    Date before = new Date( Calendar.getInstance( ).getTimeInMillis( ) );
    Date later = new Date( Calendar.getInstance( ).getTimeInMillis( ) + 10000 );

    domain.setMaxValue( before );
    domain.setMinValue( later );
    }

  @Test
  public void validationInRange( )
    {

    Assert.assertTrue( new DateValueDomain( ).isValid( new Date( ) ) );
    Assert.assertFalse( new DateValueDomain( ).isValid( new String( ) ) );

    DateValueDomain domain = new DateValueDomain( );
    Date now = new Date( Calendar.getInstance( ).getTimeInMillis( ) );
    Date later = new Date( Calendar.getInstance( ).getTimeInMillis( ) + 100000 );
    domain.setMinValue( later );
    Assert.assertFalse( domain.isValid( now ) );

    domain = new DateValueDomain( );
    domain.setMaxValue( now );
    Assert.assertFalse( domain.isValid( later ) );
    }

  @Test
  public void DateValueDomainClone( )
    {
    new DateValueDomain( ).clone( );
    }


  }