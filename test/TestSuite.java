import objects.*;
import observer.*;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import util.JsonMachineConverterTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        CoinTest.class,
        DoorTest.class,
        DrinkTest.class,
        SlotTest.class,
        MachineTest.class,
        JsonMachineConverterTest.class,
        ObservableTest.class,
        CoinObservableTest.class,
        DoorObservableTest.class,
        AuthorizationObservableTest.class,
        SlotObservableTest.class
})
public class TestSuite {
}
