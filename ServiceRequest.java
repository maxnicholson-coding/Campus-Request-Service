public abstract class ServiceRequest implements Assignable {
    private final int id;
    private String title;
    private int priority;
    private String status;
    private Technician assignedTech = null;
    protected ServiceRequest(int requestID, String requestTitle, int priority) {
        id = requestID;
        //title cannot be empty or completely full of whitespace
        if (requestTitle == null || requestTitle.trim().isEmpty()) {
            title = "Unnamed Request";
        } else {
            title = requestTitle;
        }
        setPriority(priority);
        status = "OPEN";
    }
    public void setPriority(int p) { //priority must be between 1 and 5 inclusive
        if (p < 1 || p > 5) {
            throw new IllegalArgumentException("Out of range.");
        }
        this.priority = p;
    }
    public void assignTech(Technician tech) { //assigns a new tech to the request
        assignedTech = tech;
    }
    public int getID() { //returns request ID
        return id;
    }
    public String getTitle() { //returns request title
        return title;
    }
    public int getPriority() { //returns request priority
        return priority;
    }
    public String getStatus() { //returns request status
        return status;
    }
     public Technician getTech() { //returns the request's technicain
        return assignedTech;
    }
    public void startWork() { //sets the request to in progress
        status = "IN_PROGRESS";
    }
    public void resolve() { //resolves the request
        status = "RESOLVED";
    }
    public String toString() {
        String workingTech;
        //shows the name of the assigned tech if one was assigned
        if(assignedTech == null) {
            workingTech = "None";
        } else {
            workingTech = assignedTech.getName();
        }
        return (title + "\nID: " + id + "\nPriority: " + priority + 
        "\nStatus: " + status + "\nTechnician: " + workingTech);
    }
    //all requests have their own unique methods
    public abstract int estimateMinutes(Technician tech);
    public abstract String getType();
    @Override
    public abstract void assignTo(Technician tech);
}
