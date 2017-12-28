//Name: Rony Verch
//Student ID: 500761324

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

public class Contacts
{
    LinkedList<Person> personLinkedList;

    public Contacts()
    {
        personLinkedList = new LinkedList<Person>();
    }

    public Person findPersonEmail(String email)
    {
        for (int i = 0; i < personLinkedList.size(); i++)
        {
            if (new CompareEmail().compare(personLinkedList.get(i), new Person("", "", "", "", email)) == 0)
            {
                return personLinkedList.get(i);
            }
        }

        return null;
    }

    public Person findPersonName(String lastName, String firstName)
    {
        for (int i = 0; i < personLinkedList.size(); i++)
        {
            if (personLinkedList.get(i).compareTo(new Person(lastName, firstName, "", "", "")) == 0)
            {
                return personLinkedList.get(i);
            }
        }

        return null;
    }

    public Person findPersonPhone(String telephone)
    {
        for (int i = 0; i < personLinkedList.size(); i++)
        {
            if (new ComparePhone().compare(personLinkedList.get(i), new Person("", "", "", telephone, "")) == 0)
            {
                return personLinkedList.get(i);
            }
        }

        return null;
    }

    public void readContactsFile() throws IOException
    {
        Scanner scanner = new Scanner(new File("src/Contacts.txt"));
        int amount = 0;
        int count = -1;

        while (scanner.hasNext())
        {
            scanner.nextLine();
            count++;
        }

        if (count % 5 != 0)
        {
            throw new IOException("UNABLE TO READ FILE: Not enough lines in file");
        }

        scanner.close();
        scanner = new Scanner(new File("src/Contacts.txt"));

        personLinkedList.clear();

        try
        {
            amount = scanner.nextInt();
            scanner.nextLine();
        }
        catch (Exception e)
        {
            throw new IOException("UNABLE TO READ FILE: Unable to read the amount of people");
        }

        for (int i = 0; i < amount; i++)
        {
            personLinkedList.add(new Person(scanner.nextLine(), scanner.nextLine(), scanner.nextLine(), scanner.nextLine(), scanner.nextLine()));

            if (personLinkedList.get(i).getFirstName().equals("") || personLinkedList.get(i).getLastName().equals("") ||
                    personLinkedList.get(i).getAddress().equals("") || personLinkedList.get(i).getTelephone().equals("")
                    || personLinkedList.get(i).getEmail().equals(""))
            {
                throw new IOException("UNABLE TO READ FILE: Missing line within file");
            }
        }
    }
}

