import objects.CoinTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import util.JsonMachineConverterTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CoinTest.class,
        JsonMachineConverterTest.class,
})
public class TestSuit {
}
