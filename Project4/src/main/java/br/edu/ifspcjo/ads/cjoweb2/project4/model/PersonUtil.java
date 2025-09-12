
package br.edu.ifspcjo.ads.cjoweb2.project4.model;

import java.util.ArrayList;
import java.util.List;

public class PersonUtil {

	public List<Person> getPeopleList(String people){
		List<Person> peopleList = new ArrayList<>();
		if(people.length() > 0) {
			String[] lines = people.split("\n");
			for(int i = 0; i < lines.length; i++) {
				String[] data = lines[i].split("\\s*;\\s*");
				if(data.length == 3){
					Person person = new Person();
					person.setName(data[0]);
					person.setCpf(data[1]);
					person.setEmail(data[2]);
					peopleList.add(person);
				}
			}
		}
		return peopleList;
	}
}