
import com.yaps.petstore.domain.CategoryDAOTest;
import com.yaps.petstore.domain.CustomerTest;
import com.yaps.petstore.domain.CustomerDAOTest;
import com.yaps.petstore.domain.ItemDAOTest;
import com.yaps.petstore.domain.ProductDAOTest;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * This class launches all the tests of the application
 */
public final class AllTests extends TestCase {

    public AllTests() {
        super();
    }

    public AllTests(final String s) {
        super(s);
    }

    //==================================
    //=            Test suite          =
    //==================================
    public static TestSuite suite() {

        final TestSuite suite = new TestSuite();

        // Domain
        suite.addTest(CustomerTest.suite());
        suite.addTest(CustomerDAOTest.suite());
        suite.addTest(CategoryDAOTest.suite());
        suite.addTest(ProductDAOTest.suite());
        suite.addTest(ItemDAOTest.suite());

        return suite;
    }

    public static void main(final String[] args) {
        junit.textui.TestRunner.run(suite());
    }

}
