package ar.edu.tp.domain.processor;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import ar.edu.tp.domain.Bike;
import ar.edu.tp.domain.Travel;
import ar.edu.tp.domain.exporter.FileFormatExporter;
import ar.edu.tp.domain.exporter.YamlExporter;
import ar.edu.tp.domain.parser.ParserZipOnDemand;
import ar.edu.tp.domain.parser.TimeAndQuantityBike;


public class StatisticalProcessorOnDemandStrategy implements StatisticalProcessorStrategy {
	private long startTime;
	private File folderOutput;
	private final String  fileName="salida";
	@Override
	public void processStatistics(String folderInput,File folderOutput) throws Exception {
		this.startTime = System.currentTimeMillis();
		FileManager fileManager = new FileManager(folderInput);
		fileManager.validateFolder();
		this.folderOutput=folderOutput;

		List<String> paths = fileManager.findPaths();
		
		if(!paths.isEmpty()){
			ParserZipOnDemand parserZipOnDemand = new ParserZipOnDemand(paths);
			parserZipOnDemand.parse();
			HashMap<Bike, TimeAndQuantityBike> mapBike =parserZipOnDemand.getDeamon().getMapBike();
			HashMap<Travel, Integer> mapTravel=parserZipOnDemand.getDeamon().getMapTravel() ;
			StatisticalProcessor processor = new StatisticalProcessor(mapBike,mapTravel);
			
			generateStatistics(processor, fileName);
		
		}else
			System.out.println("The folder is empty");

	}
	

	private  void generateStatistics(StatisticalProcessor processor, String fileName) throws IOException {
		List<Bike> bikesUsedMoreTimes = processor.getBikesUsedMoreTimes();
		List<Bike> bikesUsedLessTimes = processor.getBikesUsedLessTimes();
		List<Travel> travelsMoreDone = processor.getTravelMoreDone();
		List<Bike> bikeLongerUsed = processor.getBikeLongerUsed();
		float averageUseTime = processor.getAverageUseTime();
		float valueMaxTimeUsedBike=processor.getValueMaxTimeUsedBike();

		FileFormatExporter yamlExporter = new YamlExporter(this.folderOutput,fileName, bikesUsedMoreTimes, bikesUsedLessTimes, bikeLongerUsed,travelsMoreDone, averageUseTime,valueMaxTimeUsedBike);
		long endTime = System.currentTimeMillis() - this.startTime;
		yamlExporter.export(endTime);
	}
}