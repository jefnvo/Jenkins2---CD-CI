package br.com.algartelecom.algarcrm.commons.repository.impl;


import java.util.List;

import br.com.algartelecom.toolbox.component.datagrid.Page;
import br.com.algartelecom.toolbox.component.datagrid.PageBuilder;
import br.com.algartelecom.toolbox.component.datagrid.Pageable;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Criteria;
import org.hibernate.NonUniqueResultException;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import br.com.algartelecom.algarcrm.commons.model.ListValues;
import br.com.algartelecom.algarcrm.commons.repository.ListValuesRepository;
import br.com.algartelecom.algarcrm.commons.repository.exception.ListValueRepositoryException;
import br.com.algartelecom.algarcrm.osgi.entity.interceptor.repository.AbstractBaseRepository;
import org.springframework.cache.annotation.Cacheable;


/**
 * @author Hermano Flavio ListValuesRepositoryImpl.java
 */
@Repository
@SuppressWarnings( "unchecked" )
public class ListValuesRepositoryImpl extends AbstractBaseRepository< ListValues, Long, ListValueRepositoryException > implements ListValuesRepository {

    private static final long serialVersionUID = -3112589905742768691L;


    @Override
    public List< ListValues > findByKey( String key ) {

        return (List< ListValues >) getCriteria().add( Restrictions.eq( "key", key ).ignoreCase() ).add( Restrictions.eq( "flagActive", true ) ).list();

    }


    @Override
    public List< ListValues > findByKeyAndValue( String key, String value ) {

        if ( StringUtils.isBlank( key ) ) {
            throw new IllegalArgumentException( "key can not be null" );
        }
        if ( StringUtils.isBlank( value ) ) {
            throw new IllegalArgumentException( "value can not be null" );
        }

        Criteria c = getCriteria();
        c.add( Restrictions.eq( "key", key ).ignoreCase() );
        c.add( Restrictions.eq( "value", value ).ignoreCase() );

        try {
            return c.list();
        } catch ( NonUniqueResultException e ) {
            throw new ListValueRepositoryException( String.format( "More than one Result - Find by key: %s, value: %s", key, value ), e );
        }
    }


    @Override
    public ListValues findByKeyAndValueAndParentId( String key, String value, Long parentId ) {

        if ( StringUtils.isBlank( key ) ) {
            throw new IllegalArgumentException( "key can not be null" );
        }
        if ( StringUtils.isBlank( value ) ) {
            throw new IllegalArgumentException( "value can not be null" );
        }
        if ( parentId == null ) {
            throw new IllegalArgumentException( "parentId can not be null" );
        }

        Criteria c = getCriteria();
        c.add( Restrictions.eq( "key", key ).ignoreCase() );
        c.add( Restrictions.eq( "value", value ).ignoreCase() );
        c.add( Restrictions.eq( "parentId", parentId ) );

        try {
            return (ListValues) c.uniqueResult();
        } catch ( NonUniqueResultException e ) {
            throw new ListValueRepositoryException( String.format( "More than one Result - Find by key: %s, value: %s", key, value ), e );
        }
    }


    @Override
    public List< ListValues > findParentByKey( String key ) {

        return (List< ListValues >) getCriteria().add( Restrictions.eq( "key", key ).ignoreCase() ).add( Restrictions.eq( "flagActive", true ) )
            .add( Restrictions.isNull( "parentId" ) ).list();

    }


    @Override
    public List< ListValues > findParentByKeyAndDisplayValue( String key, String displayValue, Integer maxResults ) {

        Criteria criteria = getCriteria().add( Restrictions.eq( "key", key ).ignoreCase() ).add( Restrictions.eq( "flagActive", true ) )
            .add( Restrictions.isNull( "parentId" ) );
        if ( displayValue != null && !displayValue.isEmpty() ) {
            criteria.add( Restrictions.like( "displayValue", displayValue ).ignoreCase() );
        }
        if ( maxResults != null ) {
            criteria.setMaxResults( maxResults );
        }
        return criteria.list();
    }


    @Override
    public List< ListValues > findByParentId( Long parentId ) {

        return (List< ListValues >) getCriteria().add( Restrictions.eq( "parentId", parentId ) ).add( Restrictions.eq( "flagActive", true ) ).list();

    }


    @Override
    public ListValues findById( Long id ) {

        return (ListValues) getCriteria( ListValues.class ).add( Restrictions.eq( "id", id ) ).uniqueResult();

    }


    @Override
    public List< ListValues > findByKeyAndParentId( String key, Long parentId ) {

        if ( StringUtils.isBlank( key ) ) {
            throw new IllegalArgumentException( "key can not be null" );
        }
        if ( parentId == null ) {
            throw new IllegalArgumentException( "parentId can not be null" );
        }

        Criteria c = getCriteria();
        c.add( Restrictions.eq( "key", key ) );
        c.add( Restrictions.eq( "parentId", parentId ) );
        c.addOrder( Order.asc( "displaySequence" ) );

        try {
            return (List< ListValues >) c.list();
        } catch ( Exception e ) {
            throw new ListValueRepositoryException( String.format( "Find by key: %s, parentId: %d", key, parentId ), e );
        }
    }


    @Override
    public List< ListValues > findActivesByKeyAndParentId( String key, Long parentId ) {

        if ( StringUtils.isBlank( key ) ) {
            throw new IllegalArgumentException( "key can not be null" );
        }
        if ( parentId == null ) {
            throw new IllegalArgumentException( "parentId can not be null" );
        }

        Criteria c = getCriteria();
        c.add( Restrictions.eq( "key", key ) );
        c.add( Restrictions.eq( "parentId", parentId ) );
        c.add( Restrictions.eq( "flagActive", Boolean.TRUE ) );
        c.addOrder( Order.asc( "displaySequence" ) );

        try {
            return (List< ListValues >) c.list();
        } catch ( Exception e ) {
            throw new ListValueRepositoryException( String.format( "Find by key: %s, parentId: %d", key, parentId ), e );
        }
    }


    @Override
    public List< ListValues > findByKeyAndLabel( String key, String label ) {

        if ( StringUtils.isBlank( key ) ) {
            throw new IllegalArgumentException( "key can not be null" );
        }
        if ( StringUtils.isBlank( label ) ) {
            throw new IllegalArgumentException( "key can not be null" );
        }

        Criteria c = getCriteria();
        c.add( Restrictions.like( "key", key ) );
        c.add( Restrictions.eq( "label", label ) );

        try {
            return (List< ListValues >) c.list();
        } catch ( Exception e ) {
            throw new ListValueRepositoryException( String.format( "Find by key: %s, label: %d", key, label ), e );
        }

    }


    @Override
    public List< ListValues > findActivesByKeysAndDisplayValue( List< String > keys, String displayValue, Integer maxResults ) {

        Criteria c = getCriteria();
        c.add( Restrictions.in( "key", keys ) );
        c.add( Restrictions.eq( "flagActive", true ) );
        if ( displayValue != null && !displayValue.isEmpty() ) {
            c.add( Restrictions.ilike( "displayValue", displayValue, MatchMode.START ) );
        }

        if ( maxResults != null ) {
            c.setMaxResults( maxResults );
        }
        c.addOrder( Order.asc( "displaySequence" ) );
        return (List< ListValues >) c.list();

    }


    @Override
    public Page< ListValues > findByPageable( Pageable pageable ) {

        try {

            PageBuilder< ListValues > pb = new PageBuilder<>( ListValues.class, this.getManager(), pageable );
            return pb.create();

        } catch ( Exception e ) {
            throw new ListValueRepositoryException( "findByPageable ERROR", e );
        }

    }


    @Override
    public List< ListValues > findActiveListValuesByKeyAndDescription( String key, String description ) {

        if ( StringUtils.isBlank( key ) ) {
            throw new IllegalArgumentException( "key can not be null" );
        }
        if ( StringUtils.isBlank( description ) ) {
            throw new IllegalArgumentException( "description can not be null" );
        }

        Criteria c = getCriteria();
        c.add( Restrictions.eq( "key", key ).ignoreCase() );
        c.add( Restrictions.ilike( "description", description, MatchMode.ANYWHERE ) );
        c.add( Restrictions.eq( "flagActive", Boolean.TRUE ) );

        try {
            return (List< ListValues >) c.list();
        } catch ( Exception e ) {
            throw new ListValueRepositoryException( String.format( "Find by key: %s, description: %s", key, description ), e );
        }
    }


    @Override
    public Long countActivesByKeyAndParentId( String key, Long parentId ) {

        Criteria c = getCriteria();
        c.add( Restrictions.eq( "key", key ) );
        c.add( Restrictions.eq( "parentId", parentId ) );
        c.add( Restrictions.eq( "flagActive", Boolean.TRUE ) );
        c.setProjection( Projections.rowCount() );

        try {
            return ( (Number) c.uniqueResult() ).longValue();
        } catch ( Exception e ) {
            throw new ListValueRepositoryException( String.format( "Find by key: %s, parentId: %d", key, parentId ), e );
        }
    }


    @Override
    @Cacheable( "listCache" )
    public ListValues findByKeyAndValueAndDisplaySequence( String key, String value, Long displaySequence ) {

        if ( StringUtils.isBlank( key ) ) {
            throw new IllegalArgumentException( "key can not be null" );
        }
        if ( StringUtils.isBlank( value ) ) {
            throw new IllegalArgumentException( "value can not be null" );
        }
        if ( displaySequence == null ) {
            throw new IllegalArgumentException( "displaySequence can not be null" );
        }

        Criteria c = getCriteria();
        c.add( Restrictions.eq( "key", key ).ignoreCase() );
        c.add( Restrictions.eq( "value", value ).ignoreCase() );
        c.add( Restrictions.eq( "displaySequence", displaySequence ) );

        try {
            return (ListValues) c.uniqueResult();
        } catch ( NonUniqueResultException e ) {
            throw new ListValueRepositoryException(
                String.format( "More than one Result - Find by key: %s, value: %s, displaySequence: %d", key, value, displaySequence ), e );
        }
    }

}
