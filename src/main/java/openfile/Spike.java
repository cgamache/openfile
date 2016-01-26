package openfile;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;

public class Spike {

	public static final int REPS = 5;
	
	public static void main(String[] args) {

		if (args.length != 1) {
			System.out.println("USAGE: $ java -jar openfile.jar openfile.Spike /path/to/phantomjs");
			System.exit(1);
		}
		
		DesiredCapabilities caps = new DesiredCapabilities();
		caps.setCapability(PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY, args[0]);
		
		System.out.println("To examine,\n$ lsof -p " + getPID() + " | grep CLOSE_WAIT | wc -l");
		int i = 0;
		for (; i < REPS; i++) {
			WebDriver driver = new PhantomJSDriver(caps);
			driver.get("http://www.apache.org");
			driver.quit();
		}

		System.out.println("Sleeping for 30 seconds so you can examine file handles... you should see " + REPS);
		
		try {
			Thread.sleep(30000);
		} catch (InterruptedException ignore) {
		}

	}

	public static long getPID() {
		String pid = java.lang.management.ManagementFactory.getRuntimeMXBean().getName();
		return Long.parseLong(pid.split("@")[0]);
	}

}
