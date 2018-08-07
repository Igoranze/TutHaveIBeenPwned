package nl.igor.www.mvn.tut;

import java.util.ArrayList;
import java.util.List;

import me.legrange.haveibeenpwned.Breach;
import me.legrange.haveibeenpwned.HaveIBeenPwndApi;
import me.legrange.haveibeenpwned.HaveIBeenPwndException;
import nl.igor.www.models.Person;

public class App 
{
	private HaveIBeenPwndApi hibp = new HaveIBeenPwndApi();
	
	public static void main(String[] args)
	{
		App app = new App();
		app.run();
	}


	public void run() {
		for (Person person : getPersonsList()) {
			try {
				Thread.sleep(600);
				
				boolean pwned = hibp.isAccountPwned(person.getEmail());
				System.out.printf("This email: %s, account %s pwned!\n", person.getEmail(), (pwned ? "is" : "isn't"));
				
				if (pwned) {
					Thread.sleep(600);
					showAllBreaches(person);
				}
				
			} catch (Exception e) {
				System.out.println(e);
			}
		}
	}
	
	private void showAllBreaches(Person breachedEmailPerson) throws HaveIBeenPwndException {
		List<Breach> breaches = hibp.getAllBreachesForAccount(breachedEmailPerson.getEmail());
		System.out.printf("You've been breached %d times\n", breaches.size());
		
		for (Breach breach : breaches) {
			System.out.printf("Breach is: %s\nDescription: %s\n", breach.getName(), breach.getDescription());
		}
	}

	private List<Person> getPersonsList() {
		// Create list of persons
		List<Person> persons = new ArrayList<Person>();

		Person p1 = new Person("Igor");
		p1.setAge(29);
		p1.setEmail("");

		persons.add(p1);
		
		return persons;
	}
}
