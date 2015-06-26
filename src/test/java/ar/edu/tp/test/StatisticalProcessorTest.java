package ar.edu.tp.test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import ar.edu.tp.domain.Bike;
import ar.edu.tp.domain.Location;
import ar.edu.tp.domain.Travel;
import ar.edu.tp.domain.parser.ParserZipOnDemand;
import ar.edu.tp.domain.processor.StatisticalProcessor;

public class StatisticalProcessorTest {

	private static final List<String> RECORRIDOS_2013_ZIP = Arrays.asList("resources/recorrido-2013.zip");
	private List<Travel> travels;

	@Before
	public void init() throws IOException {
		ParserZipOnDemand parserZipOnDemand = new ParserZipOnDemand(RECORRIDOS_2013_ZIP);
		travels = parserZipOnDemand.parse();
	}

	@Test
	public void getBikeUsedMoreTimesShouldGetBikeWithTwoUseTest() {
		StatisticalProcessor processor = new StatisticalProcessor(travels);
		List<Bike> bikeUsedMoreTimes = processor.getBikesUsedMoreTimes();
		Assert.assertEquals(1, bikeUsedMoreTimes.size());
		Assert.assertEquals("986", bikeUsedMoreTimes.get(0).getBikeId());
	}

	@Test
	public void getBikesUsedLessTimesShouldGetBikesWithOneUseTest() {
		StatisticalProcessor processor = new StatisticalProcessor(travels);
		List<Bike> bikeUsedLessTimes = processor.getBikesUsedLessTimes();
		Assert.assertEquals(7, bikeUsedLessTimes.size());
		Assert.assertTrue(bikeUsedLessTimes.contains(new Bike("1205")));
		Assert.assertTrue(bikeUsedLessTimes.contains(new Bike("1524")));
		Assert.assertTrue(bikeUsedLessTimes.contains(new Bike("1274")));
		Assert.assertTrue(bikeUsedLessTimes.contains(new Bike("1433")));
		Assert.assertTrue(bikeUsedLessTimes.contains(new Bike("1035")));
		Assert.assertTrue(bikeUsedLessTimes.contains(new Bike("1522")));
		Assert.assertTrue(bikeUsedLessTimes.contains(new Bike("1442")));
	}

	@Test
	public void getTravelMoreDoneShouldGetPacificoAduanaTest() {
		StatisticalProcessor processor = new StatisticalProcessor(travels);
		List<Travel> travelMoreDone = processor.getTravelMoreDone();
		Location origin = new Location("21", "PACIFICO", null);
		Location destiny = new Location("5", "ADUANA", null);

		Assert.assertEquals(origin, travelMoreDone.get(0).getOrigin());
		Assert.assertEquals(destiny, travelMoreDone.get(0).getDestination());
	}

	@Test
	public void getAverageUseTimeShouldGetAverageUseTest() {
		StatisticalProcessor processor = new StatisticalProcessor(travels);
		Double averageUseTime = processor.getAverageUseTime();
		Assert.assertEquals(25.8888888889, averageUseTime, 0.0001);
	}

}