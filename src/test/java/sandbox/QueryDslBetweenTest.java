package sandbox;

import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.support.Expressions;
import com.mysema.query.types.Ops;
import com.mysema.query.types.expr.SimpleExpression;
import org.junit.After;
import org.junit.Before;
import querydsl.MyTable;
import querydsl.QMyTable;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import static org.junit.Assert.assertEquals;

public class QueryDslBetweenTest {

    private EntityManagerFactory emf;
    private EntityManager em;

    @Before
    public void setUp() throws Exception {
        emf = Persistence.createEntityManagerFactory("myPU");
        em = emf.createEntityManager();

        em.getTransaction().begin();
        {
            final MyTable expected = new MyTable();
            expected.setCol1(10);
            expected.setCol2(20);
            em.persist(expected);
        }
        {
            final MyTable unexpected = new MyTable();
            unexpected.setCol1(30);
            unexpected.setCol2(40);
            em.persist(unexpected);
        }
        em.getTransaction().commit();
    }

    // http://stackoverflow.com/a/7662029/3591946
    @org.junit.Test
    public void between() throws Exception {
        final int constant = 10;

        final QMyTable m = QMyTable.myTable;
        final SimpleExpression<Boolean> operation = Expressions.operation(Boolean.class, Ops.BETWEEN,
                Expressions.constant(constant), m.col1, m.col2);
        final MyTable actual = new JPAQuery(em).from(m).where(operation.eq(true)).uniqueResult(m);

        assert actual != null;
        assertEquals(10, (int) actual.getCol1());
        assertEquals(20, (int) actual.getCol2());
    }

    @After
    public void tearDown() throws Exception {
        em.close();
        emf.close();
    }
}
