

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class BTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class BTest
{
    /**
     * Default constructor for test class BTest
     */
    public BTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
    }

    @Test
    public void testAdd()
    {
        B b1 = new B();
        assertEquals(4, b1.exempleDeMethode(4));
    }
}

