//Name: Rony Verch
//Student ID: 500761324

import java.util.Comparator;

public class Person implements Comparable<Person>
{
    private String lastName;
    private String firstName;
    private String telephone;
    private String address;
    private String email;

    public Person(String lastName, String firstName, String address, String telephone, String email)
    {
        this.lastName = lastName;
        this.firstName = firstName;
        this.telephone = telephone;
        this.address = address;
        this.email = email;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getTelephone()
    {
        return telephone;
    }

    public void setTelephone(String telephone)
    {
        this.telephone = telephone;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String toString()
    {
        return "WITH: " + firstName + " " + lastName + " " + telephone + " " + email;
    }

    public int compareTo(Person other)
    {
        if (this.lastName.equals(other.getLastName()))
        {
            return this.firstName.compareTo(other.getFirstName());
        }
        else
        {
            return this.lastName.compareTo(other.getLastName());
        }
    }
}

class CompareEmail implements Comparator<Person>
{
    public int compare(Person firstPerson, Person secondPerson)
    {
        return firstPerson.getEmail().compareTo(secondPerson.getEmail());
    }
}

class ComparePhone implements Comparator<Person>
{
    public int compare(Person firstPerson, Person secondPerson)
    {
        return firstPerson.getTelephone().compareTo(secondPerson.getTelephone());
    }
}
