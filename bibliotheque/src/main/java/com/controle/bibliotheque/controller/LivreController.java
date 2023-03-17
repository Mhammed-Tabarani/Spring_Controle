package com.controle.bibliotheque.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.controle.bibliotheque.entities.Livre;
import com.controle.bibliotheque.service.LivreService;

@RestController
@RequestMapping("/emsi_api/livres")
public class LivreController {

	@Autowired
	LivreService livreService;

	@GetMapping("/List")
	public List<Livre> getAll() {
		return livreService.getAll_findbytitle();
	}

	@GetMapping("/{id}")
	public Livre getLivre(@PathVariable long id) {
		return livreService.getLivre(id);
	}

	@DeleteMapping("/{id}")
	public void deleteLivre(@PathVariable long id) {
		livreService.deleteLivre(id);
	}

	@PutMapping("/{id}")
	public void modifieLivre(@PathVariable long id, @RequestBody Livre l) {
		livreService.update(id, l);
	}

	@PostMapping
	public Livre create(@RequestBody Livre livre) {
		// TODO Auto-generated method stub
		return livreService.create(livre);

	}

}

//////////////////////////////
package org.sid.QuartzDemo.dataClass;

public class Destination {
    private String name;
    private int tarif;

    public Destination(String name, int tarif) {
        this.name = name;
        this.tarif = tarif;
    }

    public Destination() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTarif() {
        return tarif;
    }

    public void setTarif(int tarif) {
        this.tarif = tarif;
    }
}



/////////////////////////////////


package org.sid.QuartzDemo.dataClass;



import org.sid.QuartzDemo.enums.ClasseVoyage;
import org.sid.QuartzDemo.enums.CoefficientDate;

import java.time.LocalDate;

public class Voyage {
    private Destination destination;
    private LocalDate date;
    private ClasseVoyage classeVoyage;


    public Voyage(Destination destination, LocalDate date, ClasseVoyage classeVoyage) {
        this.destination = destination;
        this.date = date;
        this.classeVoyage = classeVoyage;

    }

    public float calculeCout(){
        return this.destination.getTarif()*this.classeVoyage.getCoeff()* CoefficientDate.coefficientDate(date).getCoefficientDate();
    }



    public Destination getDestination() {
        return destination;
    }

    public void setDestination(Destination destination) {
        this.destination = destination;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public ClasseVoyage getClasseVoyage() {
        return classeVoyage;
    }

    public void setClasseVoyage(ClasseVoyage classeVoyage) {
        this.classeVoyage = classeVoyage;
    }


}









///////////////////////

package org.sid.QuartzDemo.enums;

public enum ClasseVoyage {
    ECONOMIQUE(1),
    AFFAIRE(2),
    PREMIER(3);
    private int coeff;

    ClasseVoyage(int coeff) {
        this.coeff = coeff;
    }

    public int getCoeff() {
        return coeff;
    }

    public void setCoeff(int coeff) {
        this.coeff = coeff;
    }
    public static ClasseVoyage getClasseVoyage(int nbr){

        if (nbr==2) return AFFAIRE;
        if (nbr==3) return PREMIER;
        else return ECONOMIQUE;
    }

}







/////////////////


package org.sid.QuartzDemo.enums;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public enum CoefficientDate {
    PLUS_DE_30(0.8f),
    PLUS_DE_15_MOINS_DE_30(1.0f),
    PLUS_DE_7_MOINS_DE_15(1.2f),
    MOINS_DE_7(1.5f),
    ;
    private float coefficient;

    CoefficientDate(float coefficient) {
        this.coefficient = coefficient;
    }

    public float getCoefficientDate() {
        return coefficient;
    }

    public void setCoefficientDate(float coefficient) {
        this.coefficient = coefficient;
    }

    public static CoefficientDate coefficientDate(LocalDate dateReservation){
        long nbrJour = ChronoUnit.DAYS.between(LocalDate.now(),dateReservation);
/*
        System.out.println(nbrJour);
*/
        if (nbrJour>=15&&nbrJour<30) {
            return PLUS_DE_15_MOINS_DE_30;
        }
        if (nbrJour>=7&&nbrJour<15){
            return PLUS_DE_7_MOINS_DE_15;
        }
        if (nbrJour<7) {
            return MOINS_DE_7;
        }
        else{
            return PLUS_DE_30;
        }
    }
}



////////////////////////

package org.sid.QuartzDemo.jobsPackage.compute;

import org.sid.QuartzDemo.jobsPackage.info.TimerInfo;
import org.sid.QuartzDemo.jobsPackage.jobs.RunComputeDataJob;
import org.sid.QuartzDemo.jobsPackage.timerService.SchedulerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ComputeService {
	
	@Autowired
	private  SchedulerService scheduler;

	public void runComputeDataJob() {
		scheduler.schedule(RunComputeDataJob.class, new TimerInfo(5,false,5000,1000,"My callBack Data"));
	}
}




///////////////////////////////



package org.sid.QuartzDemo.jobsPackage.compute;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/timer")
public class Controller {

	@Autowired
	private ComputeService service;
	
	@PostMapping("/runcomputedata")
	public void runHelloWorld() {
		service.runComputeDataJob();
	}
}










///////////////////////////////////



package org.sid.QuartzDemo.jobsPackage.info;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @AllArgsConstructor @NoArgsConstructor
public class TimerInfo {

	private int totalFireCount;
	private boolean runForever;
	private long repeatIntervalMs;
	private long initialOffsetMs;
	private String callBackData;
}









////////////////////////



package org.sid.QuartzDemo.jobsPackage.jobs;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.sid.QuartzDemo.jobsPackage.info.TimerInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class HelloWorldJob implements Job{

	private static final Logger LOG = LoggerFactory.getLogger(HelloWorldJob.class);
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		JobDataMap dataMap=context.getJobDetail().getJobDataMap();
		TimerInfo info=(TimerInfo) dataMap.get(HelloWorldJob.class.getSimpleName());
		//LOG.info(info.getCallBackData());
		
		System.out.println("Hello World From syso");
	}

}









///////////////////

package org.sid.QuartzDemo.jobsPackage.jobs;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.sid.QuartzDemo.ComputeData;
import org.sid.QuartzDemo.jobsPackage.info.TimerInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class RunComputeDataJob implements Job{

	private static final Logger LOG = LoggerFactory.getLogger(RunComputeDataJob.class);
	
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		ComputeData data=new ComputeData();
		data.setData();
		data.display();

		System.out.println(" ");
	}

}





//////////////////////////////////



package org.sid.QuartzDemo.jobsPackage.timerService;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.sid.QuartzDemo.jobsPackage.info.TimerInfo;
import org.sid.QuartzDemo.jobsPackage.util.TimerUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SchedulerService {
	private static final Logger LOG = LoggerFactory.getLogger(SchedulerService.class);

	@Autowired
	private  Scheduler scheduler ;

	public void schedule(final Class jobClass, final TimerInfo info) {
		final JobDetail jobDetail = TimerUtil.buidJobDetail(jobClass, info);
		final Trigger trigger = TimerUtil.buildTrigger(jobClass, info);
		
		try {
			scheduler.scheduleJob(jobDetail, trigger);
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			LOG.error(e.getMessage());
		}
		
		try {
			scheduler.start();
		} catch (SchedulerException e) {
			// TODO Auto-generated catch block
			LOG.error(e.getMessage());
		}
		
		
		
		
	}
	
	

}



////////////////////////////

package org.sid.QuartzDemo.jobsPackage.util;

import java.util.Date;

import org.quartz.JobBuilder;
import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.sid.QuartzDemo.jobsPackage.info.TimerInfo;

public final class TimerUtil {

	private TimerUtil() {
		
	}
	
	public static JobDetail buidJobDetail( final Class jobClass, final TimerInfo info) {
		final JobDataMap jobDataMap= new JobDataMap();
		jobDataMap.put(jobClass.getSimpleName(), info);
		
		return JobBuilder
				.newJob(jobClass)
				.withIdentity(jobClass.getSimpleName())
				.setJobData(jobDataMap)
				.build();
	}
	
	public static Trigger buildTrigger(final Class jobClass, final TimerInfo info) {
		
		SimpleScheduleBuilder builder= SimpleScheduleBuilder.simpleSchedule().withIntervalInMilliseconds(info.getRepeatIntervalMs());
		
		if(info.isRunForever()) builder.repeatForever();
		else builder.withRepeatCount(info.getTotalFireCount()-1);
		
		return TriggerBuilder
				.newTrigger()
				.withIdentity(jobClass.getSimpleName())
				.withSchedule(builder)
				.startAt(new Date(System.currentTimeMillis()+info.getInitialOffsetMs()))
				.build();
	}
	
}



///////////////////////////////

package org.sid.QuartzDemo;



import org.sid.QuartzDemo.dataClass.Destination;
import org.sid.QuartzDemo.dataClass.Voyage;
import org.sid.QuartzDemo.enums.ClasseVoyage;

import java.time.LocalDate;
import java.util.*;

public class ComputeData {

   private Map<Integer, Destination> destinations =new HashMap<Integer,Destination>();
    private List<Voyage> voyages=new ArrayList<Voyage>();

    public ComputeData(Map<Integer, Destination> destinations, List<Voyage> voyages) {
        this.destinations = destinations;
        this.voyages = voyages;
    }

    public ComputeData() {
    }

    public void setData(){

        destinations.put(1,new Destination("New York",600));

        destinations.put(2,new Destination("Paris",400));

        destinations.put(3,new Destination("Tokyo",1000));

        destinations.put(4,new Destination("Dubai",800));

        destinations.put(5,new Destination("Sydney",1200));


        voyages.add(new Voyage(getRandomDestination(destinations),getRandomDate(),getRandomClasseVoyage()));
        voyages.add(new Voyage(getRandomDestination(destinations),getRandomDate(),getRandomClasseVoyage()));
        voyages.add(new Voyage(getRandomDestination(destinations),getRandomDate(),getRandomClasseVoyage()));
        voyages.add(new Voyage(getRandomDestination(destinations),getRandomDate(),getRandomClasseVoyage()));
        voyages.add(new Voyage(getRandomDestination(destinations),getRandomDate(),getRandomClasseVoyage()));
        voyages.add(new Voyage(getRandomDestination(destinations),getRandomDate(),getRandomClasseVoyage()));
        voyages.add(new Voyage(getRandomDestination(destinations),getRandomDate(),getRandomClasseVoyage()));

    }
    public LocalDate getRandomDate(){
        Random rand = new Random();
        int randomDay =  rand.nextInt(60);
        return LocalDate.now().plusDays(randomDay);
    }

    public ClasseVoyage getRandomClasseVoyage(){
        Random rand=new Random();
        int randomClasse=rand.nextInt(3)+1;
        return ClasseVoyage.getClasseVoyage(randomClasse);
    }
    public Destination getRandomDestination(Map<Integer,Destination> destinations){
        Random rand=new Random();
        int randomIndex=rand.nextInt(destinations.size())+1;
        return destinations.get(randomIndex);
    }
    public String getMonth(int nbr) {
        switch (nbr){
            case 1:
                return "Janvier";
            case 2:
                return "Fevrier";
            case 3:
                return "Mars";
            case 4:
                return "Avril";
            case 5:
                return "Mai";
            case 6:
                return "Juin";
            case 7:
                return "Juillet";
            case 8:
                return "Aout";
            case 9:
                return "September";
            case 10:
                return "Octobre";
            case 11:
                return "Novembre";
        }
        return "";
    }
/*    public float calculeCout(int tarif,ClasseVoyage classeVoyage,LocalDate date){
        return tarif*classeVoyage.getCoeff()*CoefficientDate.coefficientDate(date).getCoefficientDate();
    }*/
    public void display(){
        this.voyages.forEach(voyage -> {
            System.out.println("Voyage vers "+voyage.getDestination().getName()+",départ le "+voyage.getDate().getDayOfMonth()+" "
                    +getMonth(voyage.getDate().getMonthValue())+" "+voyage.getDate().getYear()+", Classe "+voyage.getClasseVoyage().name()+", cout "+voyage.calculeCout()+" euros");
        });
    }
}



/////////////////////////////


package org.sid.QuartzDemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class QuartzDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(QuartzDemoApplication.class, args);
	}

}






///////////////////////////////
package org.sid.QuartzDemo.jobsPackage.jobs;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.sid.QuartzDemo.ComputeData;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class CreatePdfJob implements Job {
    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {

        ComputeData data=new ComputeData();
        data.setData();
        Document document =new Document();
        PdfPTable table = new PdfPTable(new float[] {1,1,1,1});
        table.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table.addCell("Voyage vers");
        table.addCell("départ le");
        table.addCell("Classe");
        table.addCell("Cout");
        
        table.setHeaderRows(1);

        PdfPCell[] cells = table.getRow(0).getCells();
        for (int j = 0; j < cells.length ; j++) {
            cells[j].setBackgroundColor(BaseColor.GRAY);
        }

        for (int i = 0; i < data.voyages.toArray().length; i++) {
            table.addCell(data.voyages.get(i).getDestination().getName());
            table.addCell(data.voyages.get(i).getDate().toString());
            table.addCell(data.voyages.get(i).getClasseVoyage().name());
            table.addCell(String.valueOf(data.voyages.get(i).calculeCout())+"euros");
        }
        try {
            PdfWriter.getInstance(document,new FileOutputStream("Voyages.pdf"));
            document.open();
            document.add(table);
            document.close();
            System.out.println("Done!!");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }


    }
}


///////////////////
To add in service
public void runCreatePdfJob() {
		scheduler.schedule(CreatePdfJob.class, new TimerInfo(1,false,5000,1000,"My callBack Data"));
	}
	





///////////
to add in controller


@GetMapping("/runcreatepdf")
	public void runCreatePdf() {
		service.runCreatePdfJob();
	}






<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd">
	<modelVersion>4.0.0</modelVersion>
	<parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>3.0.4</version>
		<relativePath /> <!-- lookup parent from repository -->
	</parent>
	<groupId>org.sid</groupId>
	<artifactId>QuartzDemo</artifactId>
	<version>0.0.1-SNAPSHOT</version>
	<name>QuartzDemo</name>
	<description>Demo project for Spring Boot</description>
	<properties>
		<java.version>17</java.version>
	</properties>
	<dependencies>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-quartz</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-web</artifactId>
		</dependency>
		<dependency>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-starter-test</artifactId>
			<scope>test</scope>
		</dependency>
		<dependency>
			<groupId>org.projectlombok</groupId>
			<artifactId>lombok</artifactId>
			<optional>true</optional>
		</dependency>

		<dependency>
			<groupId>com.itextpdf</groupId>
			<artifactId>itextpdf</artifactId>
			<version>5.5.13.3</version>
		</dependency>
	</dependencies>
	<build>
		<plugins>
			<plugin>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-maven-plugin</artifactId>
			</plugin>
		</plugins>
	</build>
</project>







