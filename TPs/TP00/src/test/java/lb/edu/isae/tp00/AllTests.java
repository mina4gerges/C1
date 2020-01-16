package lb.edu.isae.tp00;

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

        suite.addTest(CustomerTest.suite());

        return suite;
    }

    public static void main(final String[] args) {
        junit.textui.TestRunner.run(suite());
    }

}
