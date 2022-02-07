import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features={"src/test/resources/realroutes/ali-20211206-1928"}, ///ali-20211206-1928
        plugin={"json:target/cucumber-report/cucumber.json",
                "html:target/cucumber-report/cucumber.html"}
)
public class CucumberTest { }
