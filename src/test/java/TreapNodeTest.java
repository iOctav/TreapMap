import org.junit.BeforeClass;
import org.junit.Test;
import static junit.framework.Assert.*;

public class TreapNodeTest {
    @Test
    public void testConstructor() throws Exception
    {
        TreapNode<String> str = new TreapNode<String>("OSA", 8);
        assertEquals("Check value, must be string - OSA", str.getValue(), "OSA");
    }
    @BeforeClass
    public static void testDisplay() throws Exception
    {
        TreapNode<String> str = new TreapNode<String>("OSA", 8);
        str.displayNode();
    }

}
