import org.hamcrest.CoreMatchers;
import org.junit.Assert;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.either;

public class MainClassTest {
    @Test
    public void testGetLocalNumber() {
        Assert.assertTrue("The local number is " + MainClass.getLocalNumber() +
                ". But must be 14", MainClass.getLocalNumber() == 14);
    }

    @Test
    public void testGetClassNumber() {
        Assert.assertTrue("Class number must be greater than 45.", MainClass.getClassNumber() > 45);
    }

    @Test
    public void testGetClassString() {
        Assert.assertThat("The string \"" + MainClass.getClassString() + "\" doesn't contain words: hello or Hello", MainClass.getClassString(),
                either(CoreMatchers.containsString("hello")).or(CoreMatchers.containsString("Hello")));
    }
}