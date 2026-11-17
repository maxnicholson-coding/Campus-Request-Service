import java.util.Scanner;
import java.util.ArrayList;
import java.util.InputMismatchException;
public class CampusServiceRequest
{
    public static ArrayList<ServiceRequest> allRequests = new ArrayList<ServiceRequest>();
    public static ArrayList<Technician> allTechs = new ArrayList<Technician>();
    public static Scanner input = new Scanner(System.in);
    public static void main(String[] args)
    {
        System.out.println("Welcome to the Campus Service Request.\n");
        displayMenu();
        //create technicians and add them to list
        allTechs.add(new Technician("Marty", 4));
        allTechs.add(new Technician("Sammy", 7));
        while(true) { //asks for a command and preforms the cooresponding method
            System.out.println("\nInput a number.");
            int command = safeInputInt();
            if (command == 0) {
                System.out.println("Goodbye.");
                break;
            } else if (command == 1) {
                createRequest(true);
            } else if (command == 2) {
                createRequest(false);
            } else if (command == 3) {
                hireTech();
            } else if (command == 4) {
                displayAllRequests();
            } else if (command == 5) {
                displayTechs();
            } else if (command == 6) {
                assignTechnician();
            } else if (command == 7) {
                finishRequest();
            } else if (command == 8) {
                displayMenu();
            } else {
                System.out.println("Input the number in front of what you want to do.");
                System.out.println("To end your session, type \"0\".");
            }
        }
    }
    public static void createRequest(boolean isTech) { //creates a request
        //take ID input
        int inputID;
        System.out.println("What will be the request's ID?");
        while (true) { //loops until valid input
            boolean valid = true;
            inputID = safeInputInt();
            //check if inputted id is positive
            if (inputID <= 0) {
                System.out.println("The ID must be positive.");
                valid = false;
            }
            //check if inputted id is unused
            for (int i = 0; i < allRequests.size(); i++) {
                if (inputID == (allRequests.get(i)).getID()) {
                    System.out.println("That ID is already used.");
                    valid = false;
                }
            }
            if (valid) { //breaks the loop if the inputted id is valid
                break;
            }
        }
        //take title input
        System.out.println("Title the request.");
        String inputTitle = input.nextLine();
        //take priority input
        System.out.println("What's the priority of the request?");
        int inputPriority;
        while(true) { //loops until successfully creates request
            inputPriority = safeInputInt();
            if (isTech) { //attempts to create the request object
                try {
                    TechnologyRequest r = new TechnologyRequest(inputID, inputTitle, inputPriority);
                    //adds to list and ends loop
                    allRequests.add(r);
                    break;
                } catch (IllegalArgumentException e) { //catches is priority is out of range
                    System.out.println("Inputted priority must be between 1 and 5.");
                }
            } else {
                try {
                    MaintenanceRequest r = new MaintenanceRequest(inputID, inputTitle, inputPriority);
                    //adds to list and ends loop
                    allRequests.add(r);
                    break;
                } catch (IllegalArgumentException e) { //catches if priority is out of range
                    System.out.println("Inputted priority must be between 1 and 5.");
                }
            }
        }
        System.out.println("Request " + allRequests.get(allRequests.size()-1).getTitle() + " successfully created.");
    }
    public static void hireTech() { //hires a new technician
        //take input of name
        System.out.println("What is the new technician's name?");
        String techName;
        while(true) { //loops until the inputted name is different from all hired techs
            boolean validName = true;
            techName = input.nextLine();
            for(Technician t : allTechs) {
                if(t.getName().equals(techName)) {
                    System.out.println("There is already a technician with that name.");
                    validName = false;
                }
            }
            if(validName) { //ends loop if name doesnt match any other techs
                break;
            }
        }
        //take input of experience
        System.out.println("On a scale of 1 to 10, how experienced are they?");
        int techExp;
        Technician newTech;
        while(true) { // loops until successful object creation
            techExp = safeInputInt();
            try {
                newTech = new Technician(techName, techExp);
                allTechs.add(newTech);
                break;
            } catch (IllegalArgumentException e) { //catches if experience is out of range
                System.out.println("Inputted experience must be between 1 and 10.");
            }
        }
        System.out.println(newTech.getName() + " was successfully hired.");
    }
    public static void displayAllRequests() { //displays all requests
        //checks if there are any requests
        if(allRequests.size() == 0) {
            System.out.println("There are currently no requests.");
        }
        //displays all requests
        for(ServiceRequest r : allRequests) {
            System.out.println("-- " + r.getType() + " Request --");
            System.out.println(r);
            if (r.getStatus() == "IN_PROGRESS") {
                System.out.println("Estimated time to completion: " + r.estimateMinutes(r.getTech()) + " minutes.");
            }
            if (!r.equals(allRequests.get(allRequests.size()-1))) {
                System.out.println(); //adds a line break after every entry except the last one
            }
        }
    }
    public static void displayTechs() { //displays all technicians
        for(Technician t : allTechs) {
            System.out.println(t);
        }
    }
    public static void assignTechnician() { //assigns a technician to deal with a request
        //checks to see if any request is open
        boolean noOpenRequest = true;
        for(ServiceRequest r : allRequests) {
            if (r.getStatus().equals("OPEN")) {
                noOpenRequest = false;
            }
        }
        //cancels the method if there are no open requests
        if(allRequests.size() == 0 || noOpenRequest) {
            System.out.println("There are currently no open requests.");
            return;
        }
        //takes request id input
        System.out.println("What request should start being worked on? (Input its ID)");
        ServiceRequest currentRequest = null;
        boolean gotRequest = false;
        while(!gotRequest) { //loops until a valid input is made
            int inputRequest = safeInputInt();
            if (inputRequest == -1) { //cancels if -1 is inputted
                return;
            }
            for(ServiceRequest r : allRequests) { //identifies when a valid ID is inputted
                if(inputRequest == r.getID() && r.getStatus().equals("OPEN")) {
                    currentRequest = r;
                    gotRequest = true;
                    break;
                }
            }
            if(!gotRequest) {
                System.out.println("Input invalid. If you wish to cancel this, type \"-1\".");
            }
        }
        //take technician input
        System.out.println("Which Technician should work on the request?");
        Technician currentTech = null;
        boolean noTech = true;
        while(noTech) { //loops until a correct name is inputted
            String inputName = input.nextLine();
            for(Technician t : allTechs) {
                if(inputName.equals(t.getName())) {
                    currentTech = t;
                    noTech = false;
                    break;
                }
            }
            if(noTech) {
                System.out.println("Unable to find a technician of that name.");
            }
        }
        //assign and set in progress
        currentRequest.startWork();
        currentRequest.assignTo(currentTech);
    }
    public static void finishRequest() { //finishes a request
        //check if there is an in progress request
        boolean hasInProgress = false;
        for(ServiceRequest r : allRequests) {
            if(r.getStatus().equals("IN_PROGRESS")) {
                hasInProgress = true;
            }
        }
        if (!hasInProgress) { //cancels the method if no in progress requests
            System.out.println("There are currently no requests in progress.");
            return;
        }
        //take request input
        System.out.println("What request has been finished? (Input its ID)");
        int finished = safeInputInt();
        //check if the request input has a request associated with it
        for(ServiceRequest r : allRequests) {
            if (finished == r.getID()) {
                //check the request is in progress
                if (r.getStatus().equals("IN_PROGRESS")) {
                    r.resolve();
                    System.out.println(r.getTitle() + " has been resolved.");
                    return;
                } else {
                    System.out.println(r.getTitle() + " cannot be finished.");
                    return;
                }
            }
        }
        System.out.println("Unable to find request ID.");
    }
    public static void displayMenu() { //displays the command menu
        System.out.println("1. Create a technology request.\n2. Create a maintenance request.\n3. Hire a technician.");
        System.out.println("4. See all requests.\n5. See all technicians.\n6. Assign a technician to work on a request.");
        System.out.println("7. Finish a request.\n8. Redisplay the menu.");
    }
    public static int safeInputInt() { //takes an inputted integer without erroring at non-ints
        while(true) {
            try {
                int userInput = input.nextInt();
                input.nextLine();
                return userInput;
            } catch (InputMismatchException e) { //catch if input is not an int and continues the loop
                System.out.println("Please input an integer.");
                input.nextLine();
            }
        }
    }
}
