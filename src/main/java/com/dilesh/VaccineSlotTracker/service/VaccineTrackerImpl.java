package com.dilesh.VaccineSlotTracker.service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.dilesh.VaccineSlotTracker.helper.SlotDetailHelper;
import com.dilesh.VaccineSlotTracker.model.Response;
import com.dilesh.VaccineSlotTracker.model.Sessions;

@Service
@PropertySource("classpath:application.properties")
public class VaccineTrackerImpl {

	@Autowired
	private RestTemplate restTemplate;

	@Value("${district_id}")
	private String district_id;

	@Value("${browserLocation}")
	private String browserLocation;

	@Value("${browserReadiness}")
	private String browserReadiness;

	@Value("${cowinUrl}")
	private String cowinUrl;

	@Value("${statusReportFilePath}")
	private String statusReportFilePath;

	@Value("${mediaPlayerExecutablePath}")
	private String mediaPlayerExecutablePath;

	@Value("${alarmFilePath}")
	private String alarmFilePath;

	@Value("${toEmail}")
	private String toEmail;

	@Value("${fromEmail}")
	private String fromEmail;

	@Value("${fromEmailPassword}")
	private String fromEmailPassword;

	@Value("#{'${fee.type.list}'.split(',')}")
	private List<String> vaccineCost;

	@Value("#{'${age.list}'.split(',')}")
	private List<Integer> ageList;

	@Value("#{'${vaccine.type}'.split(',')}")
	private List<String> vaccineType;

	@Value("${pin.codes:}#{T(java.util.Collections).emptyList()}")
	private List<String> pinCodes;

	@Value("${eighteen.plus.dose.One}")
	private String eighteenPlusDoseOne;

	@Value("${eighteen.plus.dose.Two}")
	private String eighteenPlusDoseTwo;

	@Value("${fortyFive.plus.dose.One}")
	private String fortyFivePlusDoseOne;

	@Value("${fortyFive.plus.dose.Two}")
	private String fortyFivePlusDoseTwo;

	public void trackerOrchestrator(String[] args) throws MalformedURLException, IOException, InterruptedException {

		openWebsite(browserReadiness);
		String date = args[0];
		statusReportFilePath = statusReportFilePath + "status_" + date + ".html";
		String webPageReportReadiness = "file:"
				.concat(statusReportFilePath.replaceAll("\\\\", "/").replaceAll(" ", "%20"));
		com.dilesh.VaccineSlotTracker.model.Response response = null;

		StringBuilder finalResponse = getVaccineAvailabiltyFromCowin(response, date, pinCodes, vaccineCost, vaccineType,
				ageList);

		System.out.println(finalResponse);

		int cowinBrowserTabsNeeded = generateWebPageAndCounts(finalResponse.toString(), statusReportFilePath);

		playAlarmOnMusicPlayer(mediaPlayerExecutablePath, alarmFilePath);

		if (cowinBrowserTabsNeeded == 45) {
			//openAutomatedCowinWebPage("ur phoneno");
			openWebsite(cowinUrl);
		} else if (cowinBrowserTabsNeeded == 18) {
			//openAutomatedCowinWebPage("ur phoneno");
			openWebsite(cowinUrl);
		} else {
			openWebsite(cowinUrl);
			openWebsite(cowinUrl);
		}
		openWebsite(webPageReportReadiness);

		//Uncomment below line if need email alert

		if (!toEmail.trim().isEmpty() && !fromEmail.trim().isEmpty() && !fromEmailPassword.trim().isEmpty())
			sendSlotAvailabilityAlertEmail(toEmail, fromEmail, fromEmailPassword, finalResponse.toString());

	}

	private void openAutomatedCowinWebPage(String phone) throws InterruptedException {
		System.setProperty("webdriver.chrome.driver",
				"C:\\Users\\Dilesh Solanki\\Downloads\\chromedriver_win32\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.get("https://selfregistration.cowin.gov.in");
		driver.findElement(By.id("mat-input-0")).sendKeys(phone);
		Thread.sleep(500);
		driver.findElement(By.xpath(
				"/html/body/app-root/ion-app/ion-router-outlet/app-login/ion-content/div/ion-grid/ion-row/ion-col/ion-grid/ion-row/ion-col[1]/ion-grid/form/ion-row/ion-col[2]/div/ion-button"))
				.click();
	}

	private int generateWebPageAndCounts(String finalResponse, String statusReportFilePath) throws IOException {

		int fortyFiveCount = countWordsUsingSplit(finalResponse.toString(), "45plus");
		int eighteenCount = countWordsUsingSplit(finalResponse.toString(), "18plus");

		File statusFile = new File(statusReportFilePath);
		FileWriter fileWriter = new FileWriter(statusFile);
		String replacedResponse = finalResponse.replaceAll("45plus", "45+").replaceAll("18plus", "18+");

		String[] splitNewLineResponse = replacedResponse.split("\\n");

		String htmlFormatted = "";
		htmlFormatted = htmlFormatted.concat("<table border=1>");
		htmlFormatted = htmlFormatted.concat(
				"<tr><th> Pin Code </th><th> Center Name </th><th> Fee Type </th><th> Applicable Age </th><th> Date </th><th> Dose-1 Slots </th><th> Dose-2 Slots </th><th> Vaccine Type </th></tr>");

		for (int i = 0; i < splitNewLineResponse.length; i++) {
			String s = splitNewLineResponse[i];
			String[] split = s.split("->");
			htmlFormatted = htmlFormatted.concat("<tr>");
			for (int j = 0; j < split.length; j++) {
				htmlFormatted = htmlFormatted.concat("<td style=\"text-align:center\">" + split[j] + "</td>");
			}
			htmlFormatted = htmlFormatted.concat("</tr>");
		}

		htmlFormatted = htmlFormatted.concat("</table>");

		fileWriter.write("<H1>45+ slots " + fortyFiveCount + "</H1>" + "<H1>18+ slots " + eighteenCount + "</H1>"
				+ "<br><H2>" + htmlFormatted + "</h2>");
		fileWriter.close();

		if (fortyFiveCount > 0 && eighteenCount > 0)
			return 2;
		else if (fortyFiveCount > 0) {
			return 45;
		} else {
			return 18;
		}
	}

	private String addNumberOfDaysToCurrentDate(int daysToAddToCurrent) {
		Date currentDate = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		String currentFormattedDate = sdf.format(currentDate);
		Calendar c = Calendar.getInstance();
		try {
			c.setTime(sdf.parse(currentFormattedDate));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		c.add(Calendar.DAY_OF_MONTH, daysToAddToCurrent);
		return sdf.format(c.getTime());
	}

	private StringBuilder getVaccineAvailabiltyFromCowin(com.dilesh.VaccineSlotTracker.model.Response response,
			String date, List<String> pinCodes, List<String> vaccineCost, List<String> vaccineType,
			List<Integer> ageList) throws MalformedURLException, IOException, InterruptedException {
		boolean failure = true;
		StringBuilder processedResponse = null;
		while (failure) {
			System.out.println("attempting connection findByDistrict api..");
			try {
				HttpHeaders headers = new HttpHeaders();
				headers.set("User-Agent",
						"Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.93 Safari/537.36 Edg/90.0.818.51");
				HttpEntity<HttpHeaders> entity = new HttpEntity<>(headers);

				ResponseEntity<com.dilesh.VaccineSlotTracker.model.Response> apiResponseEntity = restTemplate.exchange(
						"https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/findByDistrict?district_id="
								+ district_id + "&date=" + date,
						HttpMethod.GET, entity, com.dilesh.VaccineSlotTracker.model.Response.class);
				response = apiResponseEntity.getBody();
			} catch (Exception e) {
				System.out.println("connecting failed..reattempting in 10 sec..");
				Thread.sleep(10000);
				continue;
			}
			processedResponse = processResponse(response, pinCodes, vaccineCost, vaccineType, ageList);
			if (processedResponse.length() == 0) {
				System.out.println("connected but vaccine not available...reattempting in 5 sec..");
				Thread.sleep(5000);
				continue;
			}
			failure = false;
		}
		return processedResponse;
	}

	private StringBuilder processResponse(com.dilesh.VaccineSlotTracker.model.Response response, List<String> pinCodes,
			List<String> vaccineCost, List<String> vaccineType, List<Integer> ageList) {
		Map<String, List<SlotDetailHelper>> finalList = new HashMap<>();

		List<Sessions> sessions = response.getSessions();
		sessions.stream()
				.filter(z -> vaccineType.contains(z.getVaccine()) && z.getAvailable_capacity() > 0
						&& ageList.contains(z.getMin_age_limit()) && vaccineCost.contains(z.getFee_type())
						&& ((pinCodes.isEmpty() || pinCodes == null) ? true : pinCodes.contains(z.getPincode()))
						&& (z.getMin_age_limit() == 45
								? (fortyFivePlusDoseOne.equals("Y") && fortyFivePlusDoseTwo.equals("Y")
										? (z.getAvailable_capacity_dose1() > 0 || z.getAvailable_capacity_dose2() > 0)
										: (fortyFivePlusDoseOne.equals("Y") ? z.getAvailable_capacity_dose1() > 0
												: z.getAvailable_capacity_dose2() > 0))
								: (eighteenPlusDoseOne.equals("Y") && eighteenPlusDoseTwo.equals("Y")
										? (z.getAvailable_capacity_dose1() > 0 || z.getAvailable_capacity_dose2() > 0)
										: (eighteenPlusDoseOne.equals("Y") ? z.getAvailable_capacity_dose1() > 0
												: z.getAvailable_capacity_dose2() > 0))))
				.forEach(e -> {
					List<SlotDetailHelper> listOfAvailableDatesAndCapacity = new ArrayList<>();
					String vaccineCenterName = e.getPincode() + " -> " + e.getName() + " -> " + e.getFee_type() + " -> "
							+ e.getMin_age_limit();
					finalList.put(vaccineCenterName, null);

					int cap_dose_1 = e.getAvailable_capacity_dose1();
					int cap_dose_2 = e.getAvailable_capacity_dose2();
					String date = e.getDate();
					int age = e.getMin_age_limit();
					SlotDetailHelper slotDetailHelper = new SlotDetailHelper();
					slotDetailHelper.setCapacity_dose_1(cap_dose_1);
					slotDetailHelper.setCapacity_dose_2(cap_dose_2);
					slotDetailHelper.setDate(date);
					slotDetailHelper.setAge(age);
					slotDetailHelper.setVaccineType(e.getVaccine());
					listOfAvailableDatesAndCapacity.add(slotDetailHelper);
					finalList.put(vaccineCenterName, listOfAvailableDatesAndCapacity);
				});
		StringBuilder availabilityDetails = new StringBuilder();
		finalList.forEach((k, v) -> {
			if (v != null) {
				for (SlotDetailHelper dc : v) {
					availabilityDetails.append(k + "plus -> " + dc.getDate() + " -> " + dc.getCapacity_dose_1() + " -> "
							+ dc.getCapacity_dose_2() + " ->" + dc.getVaccineType() + "\n");
				}
			}

		});
		return availabilityDetails;
	}

	private void openWebsite(String website) throws IOException {

		// if default browser needed - uncomment below and comment existing

		/* try {
		
		URI uri = new URI(website);
		uri.normalize();
		Desktop.getDesktop().browse(uri);
		
		} catch (Exception e) {
		
		e.printStackTrace();
		}*/

		Runtime.getRuntime().exec(new String[] { browserLocation, website });
	}

	private void playAlarmOnMusicPlayer(String mediaPlayerExecutablePath, String alarmFilePath) throws IOException {

		ProcessBuilder pb = new ProcessBuilder(mediaPlayerExecutablePath, alarmFilePath);
		pb.start();
	}

	private void sendSlotAvailabilityAlertEmail(String toEmail, String fromEmail, String fromEmailPassword,
			String body) {
		String to = toEmail;

		String from = fromEmail;

		String host = "smtp.gmail.com";

		Properties properties = System.getProperties();

		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.ssl.enable", "true");
		properties.put("mail.smtp.auth", "true");

		Session session = Session.getInstance(properties, new Authenticator() {

			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				//put your from email and pswd
				return new PasswordAuthentication(fromEmail, fromEmailPassword);
			}
		});
		session.setDebug(true);
		try

		{
			MimeMessage message = new MimeMessage(session);

			message.setFrom(from);

			message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

			message.setSubject("Vaccine Availability Alert !!");

			message.setText(body);

			Transport.send(message);
			System.out.println("Mail Sent successfully at " + new Date());
		} catch (MessagingException mex) {
			mex.printStackTrace();
		}
	}

	private int countWordsUsingSplit(String input, String word) {
		if (input == null || input.isEmpty()) {
			return 0;
		}
		String[] count = input.split(word);

		return count.length - 1;
	}

}
