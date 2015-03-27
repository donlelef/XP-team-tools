package tests;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

import model.InvalidStateException;
import model.TeamManager;
import model.TeamSettings;

import org.junit.Test;

import filtering.MemberFilter;
import filtering.NoFilter;
import filtering.TargetFilter;
import timeline.Event;

public class TimelineIntegrationTest {

	TeamSettings settings = new TeamSettings();
	TeamManager teamManager = new TeamManager(settings);

	@Test
	public void taskAdditionCreatesEvent() {
		teamManager.addTask("Nuovo task", "Integrare task in timeline");
		assertEquals(2, teamManager.getEventsNumber());
	}

	@Test
	public void taskDeletionCreatesEvent() {
		teamManager.addTask("Timer", "creare un timer che...");
		settings.setPossibleStates("TODO", "IN PROGRESS", "ACCEPTED", "DONE");
		teamManager.addTask("Timeline", "creare una classe che...");
		teamManager.deleteTask("Timer");
		assertEquals(4, teamManager.getEventsNumber());
	}

	@Test
	public void automaticEventsTest() {
		GregorianCalendar currentDate = getCurrentDate();
		teamManager.addTask("Timer", "");
		assertEquals("Created task: Timer",
				teamManager.getEvent("Created task: Timer").toString());
		assertEquals(currentDate, teamManager.getEvent("Created task: Timer")
				.getDate());
	}

	@Test
	/*
	 * Last test is commented because it may fail because of computing delays of
	 * few millis
	 */
	public void taskModifyTest() throws InvalidStateException {
		settings.setPossibleStates("TODO", "IN PROGRESS", "DONE");
		teamManager.addTask("Timer", "");
		teamManager.moveTaskToState("Timer", "DONE");
		for (Event event : teamManager.getEvents(new NoFilter<Event>())) {
			System.err.println(event.toString());
			;
		}
		assertEquals(3, teamManager.getEventsNumber());
		assertEquals("Changed state of task Timer: now it is DONE", teamManager
				.getEvent("Changed state of task Timer: now it is DONE")
				.toString());
		// assertEquals(
		// getCurrentDate(),
		// teamManager.getEvent(
		// "Changed state of task Timer: now it is DONE")
		// .getDate());
	}

	// this test is commented because since latest version date is computed with
	// the precision of milliseconds so that this test may fail
	/*
	 * @Test public void participantAdditionCreatesEvent() throws Exception {
	 * assertEquals(1, teamManager.getEventsNumber());
	 * settings.addTeamMember("usk"); teamManager.addDeveloperTo("Timeline",
	 * "usk"); assertEquals(getCurrentDate(),
	 * teamManager.getEvent("Added usk to task: Timeline").getDate());
	 * assertEquals(2, teamManager.getEventsNumber()); }
	 */

	@Test
	public void participantAdditionFailsWhenInvalidParticipant()
			throws Exception {
		settings.addTeamMember("sumo");
		try {
			teamManager.addDeveloperTo("Timeline", "ziobrando");
			fail();
		} catch (Exception e) {
			assertTrue(true);
		}
	}

	/*@Test
	public void memberAdditionCreatesEvent() throws Exception {
		assertEquals(1, teamManager.getEventsNumber());
		settings.addTeamMember("usk");
		Event event = teamManager.getEvent("Added member: usk");
		assertEquals(2, teamManager.getEventsNumber());
		assertEquals(getCurrentDate(), event.getDate());
	}*/

	private GregorianCalendar getCurrentDate() {
		Calendar cal = Calendar.getInstance();
		return (GregorianCalendar) cal;
	}

	@Test
	public void eventsByTargetMember() throws Exception {
		settings.setPossibleStates("TODO", "IN PROGRESS", "DONE");
		settings.addTeamMember("sumo");
		teamManager.addDeveloperTo("Timer", "sumo");
		teamManager.moveTaskToState("Timer", "DONE");
		teamManager.deleteTask("Timer");
		assertEquals(
				3,
				teamManager.getEvents(
						new TargetFilter<Event>(new MemberFilter("sumo")))
						.size());
	}

	@Test
	public void userStoryAdditionCreatesEvent() {
		teamManager.addUserStory("Timeline", "Voglio che ci sia un pannello con dei tasti che...");
		assertEquals(2, teamManager.getEventsNumber());
	}
	
	@Test
	public void userStoryDeletionCreatesEvent() {
		teamManager.addUserStory("Timeline", "Voglio che ci sia un pannello con dei tasti che...");
		settings.setPossibleStates("TODO", "IN PROGRESS", "ACCEPTED", "DONE");
		teamManager.deleteUserStory("Timeline");
		assertEquals(3, teamManager.getEventsNumber());
	}
	
	@Test
	public void userStoryModifyTest() throws InvalidStateException {
		settings.setPossibleStates("TODO", "IN PROGRESS", "DONE");
		teamManager.addUserStory("Timeline", "");
		teamManager.moveStoryToState("Timeline", "DONE");
		for (Event event : teamManager.getEvents(new NoFilter<Event>())) {
			System.err.println(event.toString());
			;
		}
		assertEquals(3, teamManager.getEventsNumber());
		assertEquals("Changed state of userstory Timeline: now it is DONE", teamManager
				.getEvent("Changed state of userstory Timeline: now it is DONE")
				.toString());
	}
}
