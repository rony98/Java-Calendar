//Name: Rony Verch
//Student ID: 500761324

import jdk.nashorn.internal.runtime.Debug;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

/*
 * Class for the appointment frame
 */
public class AppointmentFrame extends JFrame
{
    /////////////////////////////////////////////////////////////
    ////////////////////////// CONSTANTS \\\\\\\\\\\\\\\\\\\\\\\\
    /////////////////////////////////////////////////////////////


    //Frame width and height
    private final int FRAME_WIDTH = 1100;
    private final int FRAME_HEIGHT = 1000;


    /////////////////////////////////////////////////////////////
    ////////////////////////// VARIABLES \\\\\\\\\\\\\\\\\\\\\\\\
    /////////////////////////////////////////////////////////////


    //Main panels
    private JPanel controlPanel;
    private JPanel datePanel;
    private JPanel appointmentsPanel;
    private JPanel mainCalendarPanel;
    private JPanel calendarPanel;

    //Calendar variables
    private JPanel calendarGridPanel;
    private JPanel calendarAlignment;
    private ArrayList<JButton> calendarBtns = new ArrayList<JButton>();

    //Appointment date and description area
    private JLabel date;
    private JLabel currMonth;
    private JLabel allDays;
    private JTextArea appointmentsText;
    private JTextArea descriptionText;
    private JScrollPane scrollPane;

    //Buttons
    private JButton prevDayBtn;
    private JButton nextDayBtn;
    private JButton prevMonthBtn;
    private JButton nextMonthBtn;
    private JButton showBtn;
    private JButton createBtn;
    private JButton cancelBtn;
    private JButton recallBtn;

    //Labels for the prev and next day/month buttons
    private JLabel dayBtnLabel;
    private JLabel monthBtnLabel;

    //Sub panels for the control panel
    private JPanel dateSubPanel;
    private JPanel actionSubPanel;
    private JPanel descriptionSubPanel;
    private JPanel contactSubPanel;

    //Label for information regarding blue in calendar
    private JLabel calendarInfoLabel;

    //Contact text fields
    private JTextField firstName;
    private JTextField lastName;
    private JTextField phone;
    private JTextField email;
    private JTextField address;

    //Contact labels
    private JLabel firstNameLabel;
    private JLabel lastNameLabel;
    private JLabel phoneLabel;
    private JLabel emailLabel;
    private JLabel addressLabel;

    //Contact buttons
    private JButton findBtn;
    private JButton clearBtn;

    //Contact sub panels
    private JPanel nameSubPanel;
    private JPanel phoneSubPanel;
    private JPanel addressPanel;
    private JPanel nameLabelSubPanel;
    private JPanel phoneLabelSubPanel;
    private JPanel addressLabelSubPanel;
    private JPanel btnContactSubPanel;

    //Sub panels for the date sub panel which is within the control panel
    private JPanel changeDateBtnPanel;
    private JPanel changeDatePanel;
    private JPanel showDateBtnPanel;
    private JPanel dateDayBtnSubPanel;
    private JPanel dateMonthBtnSubPanel;

    //Sub panels for the action sub panel which is within the control panel
    private JPanel createTimePanel;
    private JPanel createCancelPanel;

    //Panel which is used to fix alignment for the rest of the panels
    private JPanel alignmentFixingPanel;

    //Text fields for entering new appointment data
    private JTextField newYear;
    private JTextField newMonth;
    private JTextField newDay;
    private JTextField newHour;
    private JTextField newMinute;

    //Labels for entering new appointment data
    private JLabel yearLabel;
    private JLabel monthLabel;
    private JLabel dayLabel;
    private JLabel hourLabel;
    private JLabel minuteLabel;

    //Calendar and SDF for updating how the date is displayed
    private Calendar calendar;
    private SimpleDateFormat currDateSDF;
    private SimpleDateFormat currMonthSDF;

    //Action listener for the buttons
    private ActionListener actionListener;

    //List of appointments which holds all the appointments that are added
    private Stack<Appointment> appointments;

    //Contacts variable
    private Contacts contacts = new Contacts();


    /////////////////////////////////////////////////////////////
    //////////////////////// CONSTRUCTOR \\\\\\\\\\\\\\\\\\\\\\\\
    /////////////////////////////////////////////////////////////


    /*
     * Constructor
     */
    public AppointmentFrame()
    {
        //Initializes the calendar, and SDF
        calendar = Calendar.getInstance();
        currDateSDF = new SimpleDateFormat("EEE, MMM dd, yyyy");
        currMonthSDF = new SimpleDateFormat("MMM");

        //Initializes the appointments list
        appointments = new Stack<Appointment>();

        //Initializes the main panels
        controlPanel = new JPanel();
        datePanel = new JPanel();
        appointmentsPanel = new JPanel();
        calendarPanel = new JPanel();
        mainCalendarPanel = new JPanel();

        //Creates the text area for the description
        descriptionText = new JTextArea(7, 30);

        //Attempts to read the contacts
        try
        {
            contacts.readContactsFile();
        }
        catch (IOException e)
        {
            descriptionText.setText(e.getMessage());
        }

        //Sets the current date to the label and creates the text area for the descriptions
        date = new JLabel(currDateSDF.format(calendar.getTime()));
        appointmentsText = new JTextArea(20, 47);
        appointmentsText.setEditable(false);

        //Initializes the scroll pane and sets its attributes
        scrollPane = new JScrollPane(appointmentsText);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        //Setting up the calendar
        calendarGridPanel = new JPanel();
        calendarGridPanel.setPreferredSize(new Dimension(560, 300));

        //Adding information to calendar
        currMonth = new JLabel(currMonthSDF.format(calendar.getTime()) + "                                                       " +
                "                                                                                                          ");
        allDays = new JLabel("SUN                 MON                 TUE                 WED                 THU                 FRI                 SAT            ");
        calendarInfoLabel = new JLabel("     The blue buttons in the calendar represent days that have at least one appointment              ");

        //Adds the current month and different week day labels to the calendar
        calendarPanel.add(currMonth, BorderLayout.NORTH);
        calendarPanel.add(allDays, BorderLayout.NORTH);

        //Creates the alignment panel for the calendar
        calendarAlignment = new JPanel();
        calendarAlignment.setPreferredSize(new Dimension(450, 0));

        //Adds the different panels/labels with the alignment panel
        datePanel.add(date, BorderLayout.WEST);
        datePanel.add(calendarAlignment, BorderLayout.WEST);
        datePanel.add(calendarInfoLabel, BorderLayout.WEST);
        datePanel.setPreferredSize(new Dimension(0, 20));

        //Adds the scroll pane to the appointments panel
        appointmentsPanel.add(scrollPane);

        //Initializes the action listener
        actionListener = new ChoiceListener();

        //Calls the method for creating the control panel
        createControlPanel();

        //Calls the method for creating the calendar panel
        createCalendarPanel();

        //Panel required to align the appointment box and calendar
        mainCalendarPanel.add(appointmentsPanel, BorderLayout.WEST);
        mainCalendarPanel.add(calendarPanel, BorderLayout.EAST);
        mainCalendarPanel.setPreferredSize(new Dimension(1100, 500));

        //Adds all the different parts to the window
        add(datePanel, BorderLayout.NORTH);
        add(mainCalendarPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);

        //Sets the size of the window and restricts it from being changed
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setResizable(false);
    }


    /*
     *Method for creating the control panel
     */
    private void createControlPanel()
    {
        ///////////////////////////////////////// Date Sub Panel \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

        //Initializes the date sub panel
        dateSubPanel = new JPanel();

        //Initializes the sub panels within the date sub panel
        changeDateBtnPanel = new JPanel();
        changeDateBtnPanel.setLayout(new GridLayout(1, 2));
        changeDatePanel = new JPanel();
        showDateBtnPanel = new JPanel();
        dateDayBtnSubPanel = new JPanel();
        dateMonthBtnSubPanel = new JPanel();

        //Sets the buttons for the date panel
        prevDayBtn = new JButton("<");
        prevDayBtn.addActionListener(actionListener);
        nextDayBtn = new JButton(">");
        nextDayBtn.addActionListener(actionListener);
        prevMonthBtn = new JButton("<");
        prevMonthBtn.addActionListener(actionListener);
        nextMonthBtn = new JButton(">");
        nextMonthBtn.addActionListener(actionListener);
        showBtn = new JButton("Show");
        showBtn.addActionListener(actionListener);

        //Labels for the prev/next buttons
        dayBtnLabel = new JLabel("Day:");
        monthBtnLabel = new JLabel("Month:");

        //Sets the text fields within the date panel
        newYear = new JTextField("", 4);
        newMonth = new JTextField("", 2);
        newDay = new JTextField("", 2);

        //Sets the labels within the date panel
        yearLabel = new JLabel("Year");
        monthLabel = new JLabel("Month");
        dayLabel = new JLabel("Day");

        //Adds the buttons and labels to the panel
        dateDayBtnSubPanel.add(dayBtnLabel, BorderLayout.WEST);
        dateDayBtnSubPanel.add(prevDayBtn, BorderLayout.WEST);
        dateDayBtnSubPanel.add(nextDayBtn, BorderLayout.EAST);
        dateMonthBtnSubPanel.add(monthBtnLabel, BorderLayout.WEST);
        dateMonthBtnSubPanel.add(prevMonthBtn, BorderLayout.WEST);
        dateMonthBtnSubPanel.add(nextMonthBtn, BorderLayout.EAST);
        changeDateBtnPanel.add(dateDayBtnSubPanel, BorderLayout.NORTH);
        changeDateBtnPanel.add(dateMonthBtnSubPanel, BorderLayout.SOUTH);
        changeDateBtnPanel.setPreferredSize(new Dimension(500, 95));

        //Adds the labels and text fields to the sub panels
        changeDatePanel.add(dayLabel, BorderLayout.WEST);
        changeDatePanel.add(newDay, BorderLayout.WEST);
        changeDatePanel.add(monthLabel, BorderLayout.CENTER);
        changeDatePanel.add(newMonth, BorderLayout.CENTER);
        changeDatePanel.add(yearLabel, BorderLayout.EAST);
        changeDatePanel.add(newYear, BorderLayout.EAST);
        changeDatePanel.setPreferredSize(new Dimension(300, 90));

        showDateBtnPanel.add(showBtn, BorderLayout.CENTER);
        showDateBtnPanel.setPreferredSize(new Dimension(300, 50));

        dateSubPanel.setBorder(new TitledBorder(new EtchedBorder(), "Date"));
        dateSubPanel.add(changeDateBtnPanel, BorderLayout.NORTH);
        dateSubPanel.add(changeDatePanel, BorderLayout.CENTER);
        dateSubPanel.add(showDateBtnPanel, BorderLayout.SOUTH);
        dateSubPanel.setPreferredSize(new Dimension(540, 290));

        /////////////////////////////////////// Contact Sub Panel \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

        //Initializes the action sub panel
        contactSubPanel = new JPanel();

        //Initializes the sub panels within the action panel
        nameSubPanel = new JPanel();
        phoneSubPanel = new JPanel();
        addressPanel = new JPanel();
        nameLabelSubPanel = new JPanel();
        phoneLabelSubPanel = new JPanel();
        addressLabelSubPanel = new JPanel();
        btnContactSubPanel = new JPanel();

        //Initializes the buttons within the action panel
        findBtn = new JButton("Find");
        findBtn.addActionListener(actionListener);
        clearBtn = new JButton("Clear");
        clearBtn.addActionListener(actionListener);

        //Initializes the text fields within the action panel
        firstName = new JTextField("", 20);
        lastName = new JTextField("", 20);
        phone = new JTextField("", 20);
        email = new JTextField("", 20);
        address = new JTextField("", 40);

        //Initializes the labels within the action panel
        firstNameLabel = new JLabel("First Name:");
        lastNameLabel = new JLabel("Last Name:                                                      ");
        phoneLabel = new JLabel("Phone:                                                             ");
        emailLabel = new JLabel("Email:");
        addressLabel = new JLabel("Address:");

        //Adds the labels and text fields to the sub panels
        nameLabelSubPanel.add(lastNameLabel, BorderLayout.CENTER);
        nameLabelSubPanel.add(firstNameLabel, BorderLayout.CENTER);
        nameLabelSubPanel.setPreferredSize(new Dimension(500, 30));
        nameSubPanel.add(lastName, BorderLayout.WEST);
        nameSubPanel.add(firstName, BorderLayout.EAST);
        nameSubPanel.setPreferredSize(new Dimension(500, 30));

        phoneLabelSubPanel.add(phoneLabel, BorderLayout.WEST);
        phoneLabelSubPanel.add(emailLabel, BorderLayout.EAST);
        phoneLabelSubPanel.setPreferredSize(new Dimension(500, 30));
        phoneSubPanel.add(phone, BorderLayout.WEST);
        phoneSubPanel.add(email, BorderLayout.EAST);
        phoneSubPanel.setPreferredSize(new Dimension(500, 30));

        addressLabelSubPanel.add(addressLabel, BorderLayout.WEST);
        addressLabelSubPanel.setPreferredSize(new Dimension(500, 30));
        addressPanel.add(address, BorderLayout.WEST);
        addressPanel.setPreferredSize(new Dimension(500, 30));

        btnContactSubPanel.add(findBtn, BorderLayout.WEST);
        btnContactSubPanel.add(clearBtn, BorderLayout.EAST);

        contactSubPanel.setBorder(new TitledBorder(new EtchedBorder(), "Contact"));
        contactSubPanel.add(nameLabelSubPanel, BorderLayout.NORTH);
        contactSubPanel.add(nameSubPanel, BorderLayout.NORTH);
        contactSubPanel.add(phoneLabelSubPanel, BorderLayout.NORTH);
        contactSubPanel.add(phoneSubPanel, BorderLayout.NORTH);
        contactSubPanel.add(addressLabelSubPanel, BorderLayout.NORTH);
        contactSubPanel.add(addressPanel, BorderLayout.NORTH);
        contactSubPanel.add(btnContactSubPanel, BorderLayout.SOUTH);
        contactSubPanel.setPreferredSize(new Dimension(540, 290));

        //////////////////////////////////// Description Sub Panel \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

        //Initializes the description sub panel
        descriptionSubPanel = new JPanel();

        //Initializes the alignment fixing panel which is required for the GUI to be aligned properly
        alignmentFixingPanel = new JPanel();

        //Adds the labels and text fields to the sub panels
        descriptionSubPanel.setBorder(new TitledBorder(new EtchedBorder(), "Description"));
        descriptionSubPanel.add(descriptionText, BorderLayout.CENTER);
        descriptionSubPanel.setPreferredSize(new Dimension(540, 290));
        alignmentFixingPanel.setPreferredSize(new Dimension(315, 580));

        /////////////////////////////////// Appointment Sub Panel \\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\

        //Initializes the action sub panel
        actionSubPanel = new JPanel();

        //Initializes the sub panels within the action panel
        createTimePanel = new JPanel();
        createCancelPanel = new JPanel();

        //Initializes the buttons within the action panel
        createBtn = new JButton("CREATE");
        createBtn.addActionListener(actionListener);
        cancelBtn = new JButton("CANCEL");
        cancelBtn.addActionListener(actionListener);
        recallBtn = new JButton("RECALL");
        recallBtn.addActionListener(actionListener);

        //Initializes the text fields within the action panel
        newHour = new JTextField("", 4);
        newMinute = new JTextField("", 4);

        //Initializes the labels within the action panel
        hourLabel = new JLabel("Hour");
        minuteLabel = new JLabel("Minute");

        //Adds the labels and text fields to the sub panels
        createTimePanel.add(hourLabel, BorderLayout.WEST);
        createTimePanel.add(newHour, BorderLayout.WEST);
        createTimePanel.add(minuteLabel, BorderLayout.EAST);
        createTimePanel.add(newMinute, BorderLayout.EAST);
        createTimePanel.setPreferredSize(new Dimension(215, 120));

        createCancelPanel.add(createBtn, BorderLayout.WEST);
        createCancelPanel.add(cancelBtn, BorderLayout.WEST);
        createCancelPanel.add(recallBtn, BorderLayout.EAST);
        createCancelPanel.setPreferredSize(new Dimension(500, 50));

        actionSubPanel.setBorder(new TitledBorder(new EtchedBorder(), "Appointment"));
        actionSubPanel.add(createTimePanel, BorderLayout.NORTH);
        actionSubPanel.add(createCancelPanel, BorderLayout.SOUTH);
        actionSubPanel.setPreferredSize(new Dimension(540, 290));

        controlPanel.add(dateSubPanel, BorderLayout.NORTH);
        controlPanel.add(contactSubPanel, BorderLayout.NORTH);
        controlPanel.add(actionSubPanel, BorderLayout.NORTH);
        controlPanel.add(descriptionSubPanel, BorderLayout.NORTH);
        controlPanel.add(alignmentFixingPanel, BorderLayout.SOUTH);
    }


    /*
    *Method for creating the calendar panel
    */
    private void createCalendarPanel()
    {
        //Variables required to draw the calendar
        Calendar tempCalendar = new GregorianCalendar(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), 1, 0, 0, 0);
        int start = tempCalendar.get(Calendar.DAY_OF_WEEK) - 1;
        int dayAmount = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        for (int i = 0; i < calendarBtns.size(); i++)
        {
            calendarGridPanel.remove(calendarBtns.get(i));
            calendarBtns.remove(i);
        }

        for (int i = 0; i < 42; i++)
        {
            calendarBtns.add(new JButton());
            calendarBtns.get(i).setPreferredSize(new Dimension(71, 45));
            calendarBtns.get(i).setText("");
            calendarBtns.get(i).setOpaque(false);
            calendarBtns.get(i).setContentAreaFilled(false);
            calendarBtns.get(i).setBorderPainted(false);
            calendarBtns.get(i).setEnabled(false);
            calendarBtns.get(i).setBackground(null);
            calendarBtns.get(i).setFocusPainted(false);
            calendarGridPanel.add(calendarBtns.get(i));
        }

        int currDay = 1;

        for (int i = start; i < dayAmount + start; i++)
        {
            for (int j = 0; j < appointments.size(); j++)
            {
                if (appointments.get(j).occursOn(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), currDay))
                {
                    calendarBtns.get(i).setBackground(Color.BLUE);
                }
            }

            if (calendar.get(Calendar.DAY_OF_MONTH) == currDay)
            {
                calendarBtns.get(i).setBackground(Color.RED);
            }

            calendarBtns.get(i).setText(currDay + "");
            calendarBtns.get(i).setOpaque(true);
            calendarBtns.get(i).setContentAreaFilled(true);
            calendarBtns.get(i).setBorderPainted(true);
            calendarBtns.get(i).setEnabled(true);
            calendarBtns.get(i).addActionListener(actionListener);
            currDay++;
        }

        calendarPanel.add(allDays, BorderLayout.NORTH);
        calendarPanel.add(calendarGridPanel, BorderLayout.NORTH);
        calendarPanel.setPreferredSize(new Dimension(539, 347));
    }


    /*
     *Method for printing the appointments for the current day
     */
    private void printAppointments()
    {
        //Resets the appointments text
        appointmentsText.setText("");

        //Loops through each appointment, checks if it's on the current date, displays it if it is
        for (int i = 0; i < appointments.size(); i++)
        {
            if (appointments.get(i).occursOn(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)))
            {
                appointmentsText.append(appointments.get(i).print() + "\n\n");
            }
        }
    }


    /*
     * Method for finding an appointment
     *
     * @param year (required) year of the appointment
     * @param month (required) month of the appointment
     * @param day (required) day of the appointment
     * @param hour (required) hour of the appointment
     * @param minute (required) minute of the appointment
     */
    private Appointment findAppointment(int year, int month, int day, int hour, int minute)
    {
        //Creates the time of the appointment to be found
        Calendar time = new GregorianCalendar(year, month, day, hour, minute);

        //Loops through each appointment, if one is found it is returned
        for (int i = 0; i < appointments.size(); i++)
        {
            if (appointments.get(i).getDate().compareTo(time) == 0)
            {
                return appointments.get(i);
            }
        }

        //If no appointment is found, null is returned
        return null;
    }


    /*
     * Method for creating an appointment
     */
    private void createAppointment()
    {
        //Time variables for the appointment
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = Integer.parseInt(newHour.getText());
        int minute;

        //Tries to get the new minute if it's written in, otherwise it sets it to 0
        try
        {
            minute = Integer.parseInt(newMinute.getText());
        }
        catch (Exception e)
        {
            minute = 0;
        }

        //Boolean for whether a new appointment should be created
        boolean create = true;

        //Checks the date entered
        Calendar tempCalendar =  new GregorianCalendar(year, month - 1, 1);

        if (day > tempCalendar.getActualMaximum(Calendar.DAY_OF_MONTH) || day < 0)
        {
            descriptionText.setText("CONFLICT!!");
            create = false;
        }

        //If the appointment exists, don't create it and display that it's a conflict
        if (findAppointment(year, month, day, hour, minute) != null)
        {
            descriptionText.setText("CONFLICT!!");
            create = false;
        }

        //Checks the hour and minute are possible
        if (hour > 24 || hour < 0 || minute > 59 || minute < 0)
        {
            descriptionText.setText("CONFLICT!!");
            create = false;
        }

        //If the appointment needs to be created
        if (create)
        {
            //Creates the appointment and adds it to the list
            appointments.add(new Appointment(year, month, day, hour, minute, descriptionText.getText(),
                    new Person(lastName.getText(), firstName.getText(), address.getText(), phone.getText(), email.getText())));

            //Resets the text for the appointment
            descriptionText.setText("");
            newHour.setText("");
            newMinute.setText("");

            //Sorts the appointments
            Collections.sort(appointments);

            //Prints the new appointments
            printAppointments();
        }
    }


    /*
     * Method for cancelling the appointment
     */
    private void cancelAppointment()
    {
        //Creates all the variable for the data of the appointment to cancel
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = Integer.parseInt(newHour.getText());
        int minute;

        //Updates the minutes if a minute was typed in, otherwise it's set to 0
        try
        {
            minute = Integer.parseInt(newMinute.getText());
        }
        catch (Exception e)
        {
            minute = 0;
        }

        //Looks if the appointment currently exists
        Appointment tempAppointment = findAppointment(year, month, day, hour, minute);

        //If the appointment exists, it is removed and the appointments are re-printed
        if (tempAppointment != null)
        {
            appointments.remove(tempAppointment);
            Collections.sort(appointments);

            newHour.setText("");
            newMinute.setText("");

            printAppointments();
        }
    }


    /*
     * Method for finding a contact
     */
    private void findContact()
    {
        Person person = null;

        if (!firstName.getText().equals("") && !lastName.getText().equals(""))
        {
            person = contacts.findPersonName(lastName.getText(), firstName.getText());
        }
        else if (!email.getText().equals(""))
        {
            person = contacts.findPersonEmail(email.getText());
        }
        else if (!phone.getText().equals(""))
        {
            person = contacts.findPersonPhone(phone.getText());
        }

        if (person != null)
        {
            firstName.setText(person.getFirstName());
            lastName.setText(person.getLastName());
            email.setText(person.getEmail());
            phone.setText(person.getTelephone());
            address.setText(person.getAddress());
        }
        else
        {
            resetPersonField();
            descriptionText.setText("Unable to find contact");
        }
    }


    /*
     * Reset Person fields
     */
    private void resetPersonField()
    {
        firstName.setText("");
        lastName.setText("");
        email.setText("");
        phone.setText("");
        address.setText("");
    }


    /*
     * Action Listener class
     */
    class ChoiceListener implements ActionListener
    {

        /*
         * Method for when an action is performed (button is clicked)
         *
         * @param event (required) the event handler which stores information such as what button was clicked
         */
        public void actionPerformed(ActionEvent event)
        {
            //If the prev day button was clicked, the date is updated
            if (event.getSource() == prevDayBtn)
            {
                calendar.add(Calendar.DATE, -1);
                createCalendarPanel();
            }

            //If the next day button was clicked, the date is updated
            if (event.getSource() == nextDayBtn)
            {
                calendar.add(Calendar.DATE, 1);
                createCalendarPanel();
            }

            //If the prev day button was clicked, the date is updated
            if (event.getSource() == prevMonthBtn)
            {
                calendar.add(Calendar.MONTH, -1);
                createCalendarPanel();
            }

            //If the next day button was clicked, the date is updated
            if (event.getSource() == nextMonthBtn)
            {
                calendar.add(Calendar.MONTH, 1);
                createCalendarPanel();
            }

            //If the show button is clicked, checks if all the information is given, if it is a new date is shown
            if (event.getSource() == showBtn)
            {
                if (newYear.getText() != "" && newMonth.getText() != "" && newDay.getText() != "")
                {
                    Calendar tempCalendar =  new GregorianCalendar(Integer.parseInt(newYear.getText()),
                            Integer.parseInt(newMonth.getText()) - 1, 1);

                    if (Integer.parseInt(newDay.getText()) > tempCalendar.getActualMaximum(Calendar.DAY_OF_MONTH) ||
                            Integer.parseInt(newDay.getText()) < 0)
                    {
                        descriptionText.setText("CONFLICT!!");
                    }
                    else
                    {
                        calendar.set(Integer.parseInt(newYear.getText()), (Integer.parseInt(newMonth.getText()) - 1),
                                Integer.parseInt(newDay.getText()));

                        newYear.setText("");
                        newMonth.setText("");
                        newDay.setText("");
                    }
                }

                createCalendarPanel();
            }

            //If the create button is clicked, an appointment is created if possible
            if (event.getSource() == createBtn)
            {
                createAppointment();
            }

            //If the cancel button is clicked, an appointment is cancelled if possible
            if (event.getSource() == cancelBtn)
            {
                cancelAppointment();
            }

            //If the calendar buttons are clicked
            for (int i = 0; i < calendarBtns.size(); i++)
            {
                if (event.getSource() == calendarBtns.get(i))
                {
                    String temp  = calendarBtns.get(i).getText();
                    calendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(temp));

                    for (int j = 0; j < calendarBtns.size(); j++)
                    {
                        calendarBtns.get(j).setBackground(null);
                    }

                    for (int j = 0; j < calendarBtns.size(); j++)
                    {
                        for (int k = 0; k < appointments.size(); k++)
                        {
                            temp  = calendarBtns.get(j).getText();

                            if (!temp.equals(""))
                            {
                                if (appointments.get(k).occursOn(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), Integer.parseInt(temp)))
                                {
                                    calendarBtns.get(j).setBackground(Color.BLUE);
                                }
                            }
                        }
                    }

                    calendarBtns.get(i).setBackground(Color.RED);
                }
            }

            //If the find button is clicked
            if (event.getSource() == findBtn)
            {
                findContact();
            }

            //If the clear button is clicked
            if (event.getSource() == clearBtn)
            {
                resetPersonField();
                descriptionText.setText("Contact information cleared");
            }

            //If the recall button is clicked
            if (event.getSource() == recallBtn)
            {
                if (appointments.size() > 0)
                {
                    Calendar tempCalendar = appointments.peek().getDate();
                    calendar.set(tempCalendar.get(Calendar.YEAR), tempCalendar.get(Calendar.MONTH), tempCalendar.get(Calendar.DAY_OF_MONTH));
                    newHour.setText("" + tempCalendar.get(Calendar.HOUR_OF_DAY));
                    newMinute.setText("" + tempCalendar.get(Calendar.MINUTE));
                    createCalendarPanel();
                }
            }

            //The appointments are printed, and the new date is set (if changed)
            printAppointments();
            date.setText(currDateSDF.format(calendar.getTime()));
            currMonth.setText(currMonthSDF.format(calendar.getTime()) + "                                                       " +
                    "                                                                                                          ");
        }
    }
}

