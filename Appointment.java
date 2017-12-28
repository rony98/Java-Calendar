//Name: Rony Verch
//Student ID: 500761324

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;

/*
 * Class for the appointment
 */
public class Appointment implements Comparable<Appointment>
{
    //Variables for setting up the appointment date, description, format, and person
    private Calendar date;
    private String description;
    private SimpleDateFormat timeSDF;
    private Person person;


    /*
         * Constructor for the Appointment class
         *
         * @param year (required) Year of the current appointment
         * @param month (required) Month of the current appointment
         * @param day (required) Day of the current appointment
         * @param hour (required) Hour of the current appointment
         * @param minute (required) Minute of the current appointment
         * @param description (required) Description of the current appointment
         */
    public Appointment(int year, int month, int day, int hour, int minute, String description, Person person)
    {
        //Initializes the date, description, and time format variables
        date = new GregorianCalendar(year, month, day, hour, minute);
        this.description = description;
        timeSDF = new SimpleDateFormat("HH:mm");
        this.person = person;

    }

    /*
    * Get method for getting the person
    */
    public Person getPerson() { return person; }


    /*
    * Set method for setting the person
    */
    public void setPerson(Person person) { this.person = person; }


    /*
     * Get method for getting the date
     */
    public Calendar getDate()
    {
        return date;
    }


    /*
     *Get method for getting the description
     */
    public String getDescription()
    {
        return description;
    }


    /*
     * Method for printing out the current appointment, it's date and description
     */
    public String print()
    {
        if (!person.getTelephone().equals("") && !person.getEmail().equals(""))
        {
            return timeSDF.format(date.getTime()) + ": " + description + " " + person.toString();
        }

        return timeSDF.format(date.getTime()) + ": " + description;
    }


    /*
     * Method for checking whether this appointment occurs on a certain date
     *
     * @param year (required) Year of the current appointment
     * @param month (required) Month of the current appointment
     * @param day (required) Day of the current appointment
     */
    public boolean occursOn(int year, int month, int day)
    {
        //If the dates match, return true representing that this appointment is on the same day
        if (this.date.get(Calendar.YEAR) == year && this.date.get(Calendar.MONTH) == month && this.date.get(Calendar.DAY_OF_MONTH) == day)
        {
            return true;
        }

        //If the dates don't match, return false representing that this appointment is on a different way
        return false;
    }


    /*
     * Method for comparing the dates of two appointments to see which one comes first
     *
     * @param other (required) The appointment the current appointment is being compared to
     */
    public int compareTo(Appointment other)
    {
        //Depending on which of the appointments come first, either a positive, negative, or zero number is returned
        if (this.date.getTime().before(other.date.getTime()))
        {
            return -1;
        }
        else if(this.date.getTime().after(other.date.getTime()))
        {
            return 1;
        }
        else
        {
            return 0;
        }
    }
}
