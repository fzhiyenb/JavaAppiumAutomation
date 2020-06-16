import org.junit.Assert;
import org.junit.Test;

public class MainClassTest {
    @Test
    public void testGetLocalNumber() {
        Assert.assertTrue("The local number is " + MainClass.getLocalNumber() +
                ". But must be 14", MainClass.getLocalNumber() == 14);
    }
}
