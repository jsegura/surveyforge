package org.surveyforge.core.metadata.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.hibernate.annotations.IndexColumn;
import org.surveyforge.core.data.RowData;
import org.surveyforge.core.metadata.ValueDomain;

@Entity
public class StructuredValueDomain extends AbstractValueDomain
  {
  private static final long serialVersionUID = -3340195650321504136L;

  @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
  @IndexColumn(name = "subDomainsIndex")
  @JoinColumn(name = "upperDomain_id")
  private List<ValueDomain> subDomains       = new ArrayList<ValueDomain>( );


  public StructuredValueDomain( )
    {};


  /**
   * For validating an object with a StructuredValueDomain, the object has to be an instance of RowData.
   * 
   * @param object The object to validate
   */
  public boolean isValid( Serializable object )
    {
    // In this case "object" is a RowData

    if( object != null )
      {

      if( !(object instanceof RowData) ) throw new IllegalArgumentException( );
      if( this.getSubDomains( ).size( ) != ((RowData) object).getSubRowDatas( ).size( ) ) { return false; }

      for( int i = 0; i < this.getSubDomains( ).size( ); i++ )
        {
        if( this.getSubDomains( ).get( i ) instanceof StructuredValueDomain )
          {
          if( !this.getSubDomains( ).get( i ).isValid( ((RowData) object).getSubRowDatas( ).get( i ) ) ) return false;
          }
        else
          {
          if( !this.getSubDomains( ).get( i ).isValid( ((RowData) object).getSubRowDatas( ).get( i ).getData( ) ) ) return false;
          }
        }

      return true;
      }
    else
      throw new NullPointerException( );
    }

  /**
   * @return the subDomains
   */
  public List<ValueDomain> getSubDomains( )
    {
    return Collections.unmodifiableList( this.subDomains );
    }

  public void addSubDomain( ValueDomain domain )
    {
    if( domain != null )
      {
      this.subDomains.add( domain );
      }
    else
      throw new NullPointerException( );
    }

  public void removeSubDomain( ValueDomain domain )
    {
    if( domain != null )
      {
      this.subDomains.remove( domain );
      }
    else
      throw new NullPointerException( );
    }
  }
